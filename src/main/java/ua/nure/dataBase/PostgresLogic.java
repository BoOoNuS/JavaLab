package ua.nure.dataBase;

import com.sun.istack.internal.Nullable;
import ua.nure.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 17.11.2016.
 */
public class PostgresLogic {

    public static final String SELECT_BY_LOGIN_SQL_QUERY = "SELECT * FROM \"users\" WHERE login = ?";
    public static final String SELECT_BY_LOGIN_AND_PASS_SQL_QUERY = "SELECT * FROM \"users\" WHERE login = ? AND password = ?";
    public static final String INSERT_USER_SQL_QUERY = "INSERT INTO \"users\" VALUES (? , ?, ?, ?)";
    public static final String DELETE_USER_SQL_QUERY = "DELETE FROM \"users\" WHERE login = ?";
    public static final String UPDATE_USER_SQL_QUERY = "UPDATE \"users\" SET full_name = ?, mail = ?, password = ? WHERE login = ?";
    public static final String SELECT_ALL_USERS_SQL_QUERY = "SELECT * FROM \"users\"";

    @Nullable
    public User selectUserByLogin(Connection connection, String login) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        User user = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_LOGIN_SQL_QUERY);
            statement.setString(1, login);
            result = statement.executeQuery();
            if (result.next()) {
                user = new User(login,
                        result.getString("full_name"),
                        result.getString("mail"),
                        result.getString("password"));
            }
        } finally {
            close(statement, result);
        }
        return user;
    }

    @Nullable
    public User selectByLoginAndPass(Connection connection, String login, String password) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        User user = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_LOGIN_AND_PASS_SQL_QUERY);
            int counter = 1;
            statement.setString(counter++, login);
            statement.setString(counter++, password);
            result = statement.executeQuery();
            if (result.next()) {
                user = new User(login,
                        result.getString("full_name"),
                        result.getString("mail"),
                        result.getString("password"));
            }
        } finally {
            close(statement, result);
        }
        return user;
    }

    public boolean insertUser(Connection connection, User user) throws SQLException {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_USER_SQL_QUERY);
            int counter = 1;
            statement.setString(counter++, user.getLogin());
            statement.setString(counter++, user.getFullName());
            statement.setString(counter++, user.getMail());
            statement.setString(counter++, user.getPassword());
            result = statement.execute();
        } finally {
            close(statement);
        }
        return result;
    }

    public boolean deleteUserByLogin(Connection connection, String login) throws SQLException {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_USER_SQL_QUERY);
            statement.setString(1, login);
            result = statement.execute();
        } finally {
            close(statement);
        }
        return result;
    }

    public int updateUser(Connection connection, User user) throws SQLException {
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_USER_SQL_QUERY);
            int counter = 1;
            statement.setString(counter++, user.getFullName());
            statement.setString(counter++, user.getMail());
            statement.setString(counter++, user.getPassword());
            statement.setString(counter++, user.getLogin());
            result = statement.executeUpdate();
        } finally {
            close(statement);
        }
        return result;
    }

    public List<User> selectAllUsers(Connection connection) throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SELECT_ALL_USERS_SQL_QUERY);
            while (result.next()) {
                users.add(new User(result.getString("login"),
                        result.getString("full_name"),
                        result.getString("mail"),
                        result.getString("password")));
            }
        } finally {
            close(statement, result);
        }
        return users;
    }

    private void close(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    public void close(Statement statement, ResultSet result) throws SQLException {
        if (result != null && statement != null) {
            result.close();
            statement.close();
        }
    }
}
