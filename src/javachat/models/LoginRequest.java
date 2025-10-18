/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.models;

/**
 *
 * @author thebe
 */
// Login Request POJO
public class LoginRequest {

    private String password;
    private String username;

    public LoginRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }
    
    public LoginRequest() {} // No args constructor for serializing by jackson

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
