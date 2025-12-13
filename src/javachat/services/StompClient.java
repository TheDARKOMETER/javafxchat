/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import javachat.services.ChatStompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import java.util.Scanner;
import java.util.UUID;
import javafx.scene.Node;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ScrollPane;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;

/**
 *
 * @author User
 */
public class StompClient {

    private String brokerURL;
    private UserAuthStore userAuthStore = UserAuthStore.getInstance();
    private String clientUUID = userAuthStore.getSessionUUID();
    private Logger logger = Logger.getLogger(StompClient.class.getName());
    private StompSession session;
    private RESTClient restClient = RESTClient.getInstance();
    private static StompClient instance;

    private StompClient(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public static StompClient getInstance() {
        if (instance == null) {
            instance = new StompClient("ws://localhost:8080/jsocketapi/javafxchat");
        }
        return instance;
    }

    public StompSession createClient(Node sharedComponent, ScrollPane chatScrollPane) throws InterruptedException, ExecutionException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new ChatStompSessionHandler(sharedComponent);
        WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
        if (restClient.getJSESSIONIDCookie() != null) {
            logger.info("Adding cookie JSESSIONID " + restClient.getJSESSIONIDCookie().getValue());
            httpHeaders.add("Cookie", "JSESSIONID=" + restClient.getJSESSIONIDCookie().getValue());
        }

        ListenableFuture<StompSession> futureSession = stompClient.connect(brokerURL + "?isLoggedin=" + String.valueOf(userAuthStore.getIsLoggedIn()), httpHeaders, sessionHandler);
        session = futureSession.get();
        logger.info("connected to " + brokerURL);
        return session;
    }

    public void disconnectClient() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
