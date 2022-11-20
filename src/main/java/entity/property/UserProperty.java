package entity.property;

import entity.User;
import entity.Role;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProperty {

    private IntegerProperty userId;
    private StringProperty login;
    private StringProperty name;
    private StringProperty surname;
    private Role userRole;
    private StringProperty email;
    private boolean banned;

    public UserProperty(User user) {
        this.userId = new SimpleIntegerProperty(user.getUserId());
        this.login = new SimpleStringProperty(user.getUsername());
        this.name = new SimpleStringProperty(user.getFirstName());
        this.surname = new SimpleStringProperty(user.getLastName());
        this.userRole = user.getUserRole();
        this.email = new SimpleStringProperty(user.getEmail());
        this.banned = user.isBanned();
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getUsername() {
        return login.get();
    }

    public StringProperty usernameProperty() {
        return login;
    }

    public void setUsername(String login) {
        this.login.set(login);
    }

    public String getFirstName() {
        return name.get();
    }

    public StringProperty firstNameProperty() {
        return name;
    }

    public void setFirstName(String name) {
        this.name.set(name);
    }

    public String getLastName() {
        return surname.get();
    }

    public StringProperty lastNameProperty() {
        return surname;
    }

    public void setLastName(String surname) {
        this.surname.set(surname);
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserStatus(Role userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

}