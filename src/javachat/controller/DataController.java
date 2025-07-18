/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.controller;

import java.util.ArrayList;
import java.util.List;
import javachat.models.ChatMessage;
import javachat.dao.TempChatMessageDAO;
import javachat.dao.ChatMessageDAO;
import javachat.services.UserService;
import javachat.models.User;
import javachat.views.ChatMessageComponent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

/**
 *
 * @author thebe
 */
public class DataController {

    private ChatMessageDAO cmd;
    private ArrayList<ChatMessageComponent> chatMessageComponents = new ArrayList<>();
    private UserService userService;
    
    public DataController(ChatMessageDAO cmd, UserService userService) {
        this.cmd = cmd;
        this.userService = userService;
    }

    private void createChatMessageComponents(ArrayList<ChatMessage> cmsgs) {
        for (ChatMessage cmsg : cmsgs) {
            chatMessageComponents.add(new ChatMessageComponent(cmsg));
        }
    }
    // Render all chat messages


    public void handleIncomingChatMessage(ChatMessage cmsg, VBox chatStack) {
        cmd.addChatMessage(cmsg);
        ChatMessageComponent cmsgcmp = new ChatMessageComponent(cmsg);
        Platform.runLater(() -> {
            chatStack.getChildren().add(cmsgcmp);
        });
    }

    public void handleChatMessageHistory(ArrayList<ChatMessage> history, VBox chatStack) {
        createChatMessageComponents(history);
        Platform.runLater(() -> {
            chatStack.getChildren().addAll(chatMessageComponents);
        });
    }

    public User getUser() {
        return userService.getUser();
    }

}
