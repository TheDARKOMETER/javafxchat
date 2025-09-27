/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javachat.exceptions.ChatMessageException;
import javachat.models.User;
import javachat.models.ChatMessage;

/**
 *
 *
 * @author thebe
 */
public class ChatService {

    private static ChatService instance;
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public ChatMessage getChatMessage(UUID uuid) throws ChatMessageException {
        for (ChatMessage cmsg : chatMessages) {
            if (cmsg.getUuid().equals(uuid)) {
                return cmsg;
            }
        }
        throw new ChatMessageException("Chat message not found");
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

    private ChatService() {
    }

    public static ChatService getInstance() {
        if (instance == null) {
            return new ChatService();
        } else {
            return instance;
        }
    }

}
