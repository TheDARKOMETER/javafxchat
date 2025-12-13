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
import javachat.models.LoginResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.http.cookie.Cookie;

/**
 *
 * @author thebe
 */
public class RESTClient {

    private RestTemplate rt = new RestTemplate();
    private CookieStore cookieStore = new BasicCookieStore();
    private static RESTClient instance;
    private String userResourceUrl = "http://localhost:8080/jsocketapi/user";
    private Logger logger = Logger.getLogger(RESTClient.class.getName());

    private RESTClient() {
        cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient)); // plain RestTemplate
    }

    public static RESTClient getInstance() {
        if (instance == null) {
            instance = new RESTClient();
        }
        return instance;
    }

    public String signUp(SignUpRequest signUpRequest) throws Exception {
        HttpEntity<SignUpRequest> request = new HttpEntity<SignUpRequest>(signUpRequest);
        return rt.postForObject(userResourceUrl + "/signup", request, String.class);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        HttpEntity<LoginRequest> request = new HttpEntity<LoginRequest>(loginRequest);
        logger.info("Posting login request");
        return rt.postForObject(userResourceUrl + "/login", request, LoginResponse.class);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
    
    public Cookie getJSESSIONIDCookie() {
        for (Cookie cookie : cookieStore.getCookies()) {
            if ("JSESSIONID".equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
    
    public void logout() {
        cookieStore.clear();
        UserAuthStore userAuthStore = UserAuthStore.getInstance();
        userAuthStore.logout();
        try {
            rt.postForObject(userResourceUrl + "/logout", null, String.class);
        } catch(Exception ex) {
            logger.warning("Server logout endpoint failed: " +  ex.getMessage());
        }
        logger.info("Logged out: cleared JSESSIONID from CookieStore");
    }
}
