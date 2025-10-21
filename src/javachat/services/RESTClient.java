/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import java.util.logging.Logger;
import javachat.models.LoginRequest;
import javachat.models.SignUpRequest;
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
    Logger logger = Logger.getLogger(RESTClient.class.getName());
    
    
    public User signUp(SignUpRequest signUpRequest) {
        HttpEntity<SignUpRequest> request = new HttpEntity<SignUpRequest>(signUpRequest);
        return rt.postForObject(userResourceUrl + "/signup", request, User.class);
    }

    public User login(LoginRequest loginRequest) {
        HttpEntity<LoginRequest> request = new HttpEntity<LoginRequest>(loginRequest);
        logger.info("Posting login request");
        return rt.postForObject(userResourceUrl + "/login", request, User.class);
    }
}
