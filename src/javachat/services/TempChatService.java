/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.services;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javachat.models.User;
import javachat.models.ChatMessage;

/**
 * 
 *
 * @author thebe
 */
public class TempChatService {
    private static TempChatService instance;
    private static ArrayList<ChatMessage> chatMessages;
    
    private static User tempUser = new User("vonchez", 0, "abcd@abc.com");
    private static ChatMessage tempMsg = new ChatMessage("Hello world", 0, tempUser, 0, 0, UUID.randomUUID());

    public ChatMessage getTempMsg() {
        return tempMsg;
    }

    public void setTempMsg(ChatMessage tempMsg) {
        TempChatService.tempMsg = tempMsg;
    }

    
    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        TempChatService.chatMessages = chatMessages;
    }
    
    public void addChatMessage(ChatMessage chatMessage) {
        TempChatService.chatMessages.add(chatMessage);
    }
    
    private TempChatService() {
    }
    
    
    public static TempChatService getInstance() {
        if (instance == null) {
            chatMessages = new ArrayList<>();
            chatMessages.add(tempMsg);
            return new TempChatService();
        } else {
            return instance;
        }
    }
     
}
