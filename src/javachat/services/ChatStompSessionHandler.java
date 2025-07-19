/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.ParameterizedType;
import java.util.stream.Collectors;
import javachat.shared.Message;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import java.util.logging.Level;
import org.springframework.lang.Nullable;
import javachat.shared.HelloMessage;
import java.util.logging.Logger;
import javachat.shared.Greeting;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javachat.controller.DataController;
import javachat.dao.TempChatMessageDAO;
import javachat.models.ChatMessage;
import javachat.models.User;
import javachat.views.ChatMessageComponent;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

/**
 *
 * @author User
 */
public class ChatStompSessionHandler implements StompSessionHandler {

    private static Logger logger = Logger.getLogger(ChatStompSessionHandler.class.getName());
    private TempChatMessageDAO tcmd = TempChatMessageDAO.getInstance();
    private UserService userService = UserService.getInstance();
    private DataController dataController = new DataController(tcmd, userService);
    private Node sharedComponent;

    public ChatStompSessionHandler(Node sharedComponent) {
        this.sharedComponent = sharedComponent;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

        /* Subscribing to /topic/messages will trigger an event to STOMP server to ensure that connection works and that STOMP server can
         receive and format data from client */
        session.subscribe("/topic/messages", this);

        /* Subscribing to /topic/greetings will trigger an event to STOMP server to send you a greeting */
        session.subscribe("/topic/greetings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Greeting.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Greeting greeting = (Greeting) payload;
                System.out.println("Received: " + greeting.getContent());
            }
        });

        logger.info("Subscribed to /topic/greetings, sending a message with content " + getSampleMessage().getText());
        session.send("/app/chat", getHelloMessage());

        /* Subscribe to global chat */
        StompHeaders subscribeGlobalChatHeaders = new StompHeaders();
        subscribeGlobalChatHeaders.setDestination("/topic/globalchat");
        subscribeGlobalChatHeaders.add("username", dataController.getUser().getUsername());
        session.subscribe(subscribeGlobalChatHeaders, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                ChatMessage chatMessage = (ChatMessage) payload;
                logger.info("Received chat message: " + chatMessage.getContent());
                dataController.handleIncomingChatMessage(chatMessage, (VBox) sharedComponent);

            }
        });

        session.subscribe("/user/queue/specific-user", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ParameterizedTypeReference<ArrayList<ChatMessage>>() {
                }.getType();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                ObjectMapper mapper = new ObjectMapper();
//
//                    List<ChatMessage> chatMessageList = rawList.stream()
//                            .map(item -> mapper.convertValue(item, ChatMessage.class))
//                            .collect(Collectors.toList());
                List<ChatMessage> chatMessageList = mapper.convertValue(payload, new TypeReference<List<ChatMessage>>() {
                });
                logger.info("Received chat message history of size: " + chatMessageList.size());
                dataController.handleChatMessageHistory((ArrayList<ChatMessage>) chatMessageList, (VBox) sharedComponent);
            }
        });
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());

    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        String sessionId = (session != null && session.getSessionId() != null) ? session.getSessionId() : "no-session";
        logger.severe("Exception occured at session: " + sessionId + " | Exception message " + exception.getLocalizedMessage() + " caused by: " + exception.getCause());
        logger.log(Level.SEVERE, "Exception stack trace:", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.severe("Transport error in STOMP Session " + exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    private Message getSampleMessage() {
        Message msg = new Message();
        msg.setFrom("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }

    private HelloMessage getHelloMessage() {
        HelloMessage hmsg = new HelloMessage();
        hmsg.setName("vonchez");
        return hmsg;
    }

}
