/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat;

import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import java.util.logging.Level;
import org.springframework.lang.Nullable;
import java.util.logging.Logger;

import java.lang.reflect.Type;

/**
 *
 * @author User
 */
public class MyStompSessionHandler implements StompSessionHandler {

    private static Logger logger = Logger.getLogger(MyStompSessionHandler.class.getName());

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/messages", this);
        /* TODO
        session.subscribe("/topic/greetings", new StompFrameHandler() {
            @override
            public Type getPayLoadType(StompHeaders headers) {
                Greeting.class;
            }
        });*/
        session.send("/app/chat", getSampleMessage());
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());

    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        String sessionId = (session != null  && session.getSessionId() != null) ? session.getSessionId()  : "no-session"; 
        logger.severe("Exception occured at session: " + sessionId + " | Exception message " + exception.getLocalizedMessage() + " caused by: " + exception.getCause()); 
        logger.log(Level.SEVERE, "Exception stack trace:", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.severe("Transport error in STOMP Session " + exception.getMessage());
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

}
