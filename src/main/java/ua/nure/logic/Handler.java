package ua.nure.logic;

import ua.nure.model.Admins;
import ua.nure.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Stanislav on 31.10.2016.
 */
public abstract class Handler {
    public abstract User getUserByLogin(String login) throws SQLException;

    public abstract void addUser(User user) throws SQLException;

    public abstract boolean removeUser(User user) throws SQLException;

    public abstract void editUser(User editingUser) throws SQLException;

    public abstract Collection<User> getUsers() throws SQLException;

    public abstract boolean identify(User user) throws SQLException;

    public abstract boolean isExist(String login) throws SQLException;

    public static boolean isAdmin(String login) {
        for (Admins admin : Admins.values()) {
            if (admin.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void close(Connection connection) throws SQLException {
        if(connection != null){
            connection.close();
        }
    }
}
