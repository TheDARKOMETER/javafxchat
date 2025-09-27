/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.controller;

import java.util.ArrayList;
import java.util.List;
import javachat.models.ChatMessage;
import javachat.dao.ChatMessageHandler;
import javachat.services.UserAuthStore;
import javachat.models.User;
import javachat.views.ChatMessageComponent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javachat.dao.IChatMessageHandler;

/**
 *
 * @author thebe
 */
public class DataController {

    private IChatMessageHandler cmd;
    private ArrayList<ChatMessageComponent> chatMessageComponents = new ArrayList<>();
    
    public DataController(IChatMessageHandler cmd) {
        this.cmd = cmd;
    }

    private void createChatMessageComponents(ArrayList<ChatMessage> cmsgs) {
        for (ChatMessage cmsg : cmsgs) {
            chatMessageComponents.add(new ChatMessageComponent(cmsg));
        }
    }

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


}
