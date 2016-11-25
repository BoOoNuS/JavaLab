package ua.nure.model;

/**
 * Created by Stanislav on 30.10.2016.
 */
public enum Admins {

    STANISLAV("hecvin", new User("hecvin", "Stanislav Vorozhka", "stanislav.vorozhka@nure.ua", "619916"));

    private String login;
    private User user;

    Admins(String login, User user) {
        this.login = login;
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public User getUser() {
        return user;
    }


}
