package entity;

public class User {

    private int userId;
    private String login;
    private String name;
    private String surname;
    private Role userRole;
    private String email;
    private boolean banned;

    public User(int userId, String login,
                String name, String surname,
                Role userRole,
                String email, boolean banned) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.userRole = userRole;
        this.email = email;
        this.banned = banned;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return name;
    }

    public void setFirstName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return surname;
    }

    public void setLastName(String surname) {
        this.surname = surname;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserStatus(Role userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + login + '\'' +
                ", firstName='" + name + '\'' +
                ", lastName='" + surname + '\'' +
                ", userRole=" + userRole +
                ", email='" + email + '\'' +
                ", banned=" + banned +
                '}';
    }
}