/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import javachat.models.User;
/**
 *
 * @author thebe
 */
public class UserService {
    //private User currentUser;
    private static UserService instance;
    private User tempUser = new User("vonchez", 0, "abcd@abc.com");

    
    private UserService() {}
    
    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    public User getUser() {
        return tempUser;
    }
    
    public void signUp(String username, String password) {
        // TODO
    }
    
}

