package ua.nure.logic;

import com.sun.istack.internal.Nullable;
import ua.nure.dataBase.PostgresConnector;
import ua.nure.model.Admins;
import ua.nure.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Stanislav on 30.10.2016.
 */
public class Verifier {

    public static final String NOT_SUPPORTED_METHOD_CLOSE_EXCEPTION = "Not supported method close on this structure";
    private static final PostgresConnector logic = PostgresConnector.getInstance();

    private static Verifier verifier;
    private static Map<String, User> users = new Hashtable<>();

    private Verifier() {}

    public static Verifier initVerifier() {
        if (verifier == null) {
            synchronized (Verifier.class) {
                if (verifier == null) {
                    verifier = new Verifier();
                }
            }
        }
        return verifier;
    }

    public static Handler getHandler() {
        return new UserDBHandler();
    }

    public boolean identifyAdmin(String login, User user) {
        if (Handler.isAdmin(login)) {
            for (Admins admin : Admins.values()) {
                if (admin.getUser().equals(user)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    public Admins getAdminByLogin(String login) {
        for (Admins admin : Admins.values()) {
            if (admin.getLogin().equals(login)) {
                return admin;
            }
        }
        return null;
    }

    public static Map<String, User> getUsers() {
        return users;
    }

    private static class UserHandler extends Handler {

        @Override
        public User getUserByLogin(String login) {
            return users.get(login);
        }

        @Override
        public void addUser(User user) {
            users.put(user.getLogin(), user);
        }

        @Override
        public boolean removeUser(User user) {
            return users.remove(user.getLogin(), user);
        }

        @Override
        public void editUser(User editingUser) {
            users.put(editingUser.getLogin(), editingUser);
        }

        @Override
        public Collection<User> getUsers() {
            return users.values();
        }

        @Override
        public boolean identify(User user) throws SQLException {
            if (isExist(user.getLogin())) {
                for (User hashedUser : users.values()) {
                    if (hashedUser.equals(user)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean isExist(String login) throws SQLException {
            for (String hashedLogin : users.keySet()) {
                if (hashedLogin.equals(login)) {
                    return true;
                }
            }
            return false || isAdmin(login);
        }

        @Override
        public void close(Connection connection) throws SQLException {
            throw new SQLFeatureNotSupportedException(NOT_SUPPORTED_METHOD_CLOSE_EXCEPTION);
        }
    }

    private static class UserDBHandler extends Handler {

        @Override
        public User getUserByLogin(String login) throws SQLException {
            return logic.getUserByLogin(login);
        }

        @Override
        public void addUser(User user) throws SQLException {
            logic.addUser(user);
        }

        @Override
        public boolean removeUser(User user) throws SQLException {
            return logic.removeUser(user);
        }

        @Override
        public void editUser(User editingUser) throws SQLException {
            logic.editUser(editingUser);
        }

        @Override
        public Collection<User> getUsers() throws SQLException {
            return logic.getUsers();
        }

        @Override
        public boolean isExist(String login) throws SQLException {
            boolean result = false;
            if(logic.isExist(login)){
                result = true;
            }
            return result || isAdmin(login);
        }

        @Override
        public boolean identify(User user) throws SQLException {
            return logic.identify(user.getLogin(), user.getPassword());
        }
    }
}
