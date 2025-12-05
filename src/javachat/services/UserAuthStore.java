/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import java.util.UUID;
import javachat.interfaces.UserIdentifiable;
import javachat.models.User;

/**
 *
 * @author thebe
 */
public class UserAuthStore {

    private static UserAuthStore instance;
    private User user = new User("", 0, "");
    private boolean isLoggedIn = false;
// sessionUUID may be treated as JWT in the fture
    private static String sessionUUIDString;

    private UserAuthStore() {
    }

    public static synchronized UserAuthStore getInstance() {
        if (instance == null) {
            instance = new UserAuthStore();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public String getSessionUUID() {
        return sessionUUIDString;
    }

    public void setSessionUUID(String sessionUUID) {
        this.sessionUUIDString = sessionUUIDString;
    }

    public void setUser(UserIdentifiable user) {
        this.user = new User(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void login(UserIdentifiable user) {
        isLoggedIn = true;
        this.user = new User(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public void logout() {
        this.user = new User("", 0, "");
        this.sessionUUIDString = null;
        this.isLoggedIn = false;
    }

}
