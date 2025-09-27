/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import javachat.models.User;

/**
 *
 * @author thebe
 */
public class RESTClient {

    RestTemplate rt = new RestTemplate();
    String userResourceUrl = "http://localhost:8080/jsocketapi/user";

    public User signUp(User user) {
        HttpEntity<User> request = new HttpEntity<User>(user);
        return rt.postForObject(userResourceUrl + "/signup", request, User.class);
    }

    public User login(User user) {
        String userResourceUrl = "http://localhost:8080/jsocketapi/user";
        HttpEntity<User> request = new HttpEntity<User>(user);
        return rt.postForObject(userResourceUrl + "/login", request, User.class);
    }
}
