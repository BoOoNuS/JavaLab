package ua.nure.dataBase;

import ua.nure.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Stanislav on 17.11.2016.
 */
public class PostgresConnector {

    private static PostgresLogic logic = new PostgresLogic();

    private Connection connection;

    private PostgresConnector(Connection connection) {
        this.connection = connection;
    }

    public static PostgresConnector getInstance() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Java_labs", "postgres", "619916");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgresConnector#getInstance - " +
                    e.getMessage());
        } catch (SQLException e) {
            System.out.println("PostgresConnector#getInstance - " +
                    e.getMessage());
        }
        return new PostgresConnector(connection);
    }

    public boolean isExist(String login) throws SQLException {
        return logic.selectUserByLogin(connection, login) != null;
    }

    public boolean identify(String login, String password) throws SQLException {
        return logic.selectByLoginAndPass(connection, login, password) != null;
    }

    public User getUserByLogin(String login) throws SQLException {
        return logic.selectUserByLogin(connection, login);
    }

    public void addUser(User user) throws SQLException {
        logic.insertUser(connection, user);
    }

    public boolean removeUser(User user) throws SQLException {
        return logic.deleteUserByLogin(connection, user.getLogin());
    }

    public void editUser(User user) throws SQLException {
        logic.updateUser(connection, user);
    }

    public Collection<User> getUsers() throws SQLException {
        List<User> users = logic.selectAllUsers(connection);
        return users;
    }

    public Connection getConnection() {
        return connection;
    }
}
