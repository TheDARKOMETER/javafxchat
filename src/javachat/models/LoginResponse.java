/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.models;

/*
 * DTO for sending user info to the client after login/signup.
 * Does NOT expose sensitive fields like password.
 */
public class LoginResponse {

    private Long id;
    private String username;
    private long createdAt;
    private String email;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(String username, long createdAt, String email, String role) {
        this.username = username;
        this.createdAt = createdAt;
        this.email = email;
        this.role = role;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
