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
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.util.concurrent.ListenableFuture;

/**
 *
 * @author User
 */
public class StompClient {
    private String brokerURL;
    private UserAuthStore userAuthStore = UserAuthStore.getInstance();
    private String clientUUID = userAuthStore.getSessionUUID();
    private Logger logger = Logger.getLogger(StompClient.class.getName());
    
    public StompClient(String brokerURL) { 
        this.brokerURL = brokerURL;
    }
    
    public StompSession createClient(Node sharedComponent, ScrollPane chatScrollPane) throws InterruptedException, ExecutionException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new ChatStompSessionHandler(sharedComponent);
        ListenableFuture<StompSession> session = stompClient.connect(brokerURL, sessionHandler);
        System.out.println("connected to " + brokerURL);
        return session.get();
        //new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}
