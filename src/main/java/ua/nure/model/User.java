package ua.nure.model;

/**
 * Created by Stanislav on 30.10.2016.
 */
public class User {

    private String login;
    private String fullName;
    private String mail;
    private String password;

    public User(String login, String fullName, String mail, String password) {
        this.login = login;
        this.fullName = fullName;
        this.mail = mail;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
