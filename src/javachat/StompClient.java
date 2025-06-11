/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class StompClient {
    private String brokerURL;
    
    public StompClient(String brokerURL) { 
        this.brokerURL = brokerURL;
    }
    
    public void createClient() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(brokerURL, sessionHandler);
        System.out.println("connected to " + brokerURL);
        //new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}
