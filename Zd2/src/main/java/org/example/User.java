package org.example;

public class User {
    private static User instance;

    private String login;
    private String hashedPassword;
    private String role;
    private int vehicleID;

    private User() {
    }

    public User(final String login, final String hashedPassword, final String role, final int vehicleID) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.vehicleID = vehicleID;
    }

    public static void overwriteInstance(User user){
        instance = user;
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(final String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(final int vehicleID) {
        this.vehicleID = vehicleID;
    }

    @Override
    public String toString() {
        return "User{" +
               "login='" + login + '\'' +
               ", hashedPassword='" + hashedPassword + '\'' +
               ", role='" + role + '\'' +
               ", ID=" + vehicleID +
               '}';
    }
}