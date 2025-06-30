/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.controller;

import java.util.ArrayList;
import javachat.models.ChatMessage;
import javachat.dao.TempChatMessageDAO;
import javachat.dao.ChatMessageDAO;
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
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();

    public DataController(ChatMessageDAO cmd) {
        this.cmd = cmd;
    }

    private void createChatMessageComponents(ArrayList<ChatMessage> cmsgs) {
        for (ChatMessage cmsg : cmsgs) {
            chatMessageComponents.add(new ChatMessageComponent(cmsg));
        }
    }

    private void createChatMessageComponent(ChatMessage cmsg) {
        chatMessageComponents.add(new ChatMessageComponent(cmsg));
    }

    // Render all chat messages
    public ArrayList<ChatMessageComponent> getChatMessagesToRender() {
        chatMessages = cmd.getAllChatMessages();
        this.createChatMessageComponents(chatMessages);
        return this.chatMessageComponents;
    }

    public void handleIncomingChatMessage(ChatMessage cmsg, VBox chatStack) {
        cmd.addChatMessage(cmsg);
        ChatMessageComponent cmsgcmp = new ChatMessageComponent(cmsg);
        Platform.runLater(() -> {
            chatStack.getChildren().add(cmsgcmp);
        });
    }

}
