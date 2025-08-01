/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.dao;

import java.util.ArrayList;
import java.util.UUID;
import javachat.exceptions.ChatMessageException;
import javachat.models.ChatMessage;
import javachat.services.TempChatService;

/**
 *
 * @author thebe
 */
public class TempChatMessageDAO implements ChatMessageDAO {

    private TempChatService tcs = TempChatService.getInstance();
    private static TempChatMessageDAO instance;
    
    private TempChatMessageDAO() {}

    @Override
    public ChatMessage getChatMessage(UUID uuid) throws ChatMessageException {
        return tcs.getChatMessage(uuid);
    }

    @Override
    public ArrayList<ChatMessage> getAllChatMessages() throws ChatMessageException {
        return tcs.getChatMessages();
    }
    
    @Override
    public void addChatMessage(ChatMessage msg) {
        tcs.addChatMessage(msg);
    }
    
    @Override
    public void setChatHistory(ArrayList<ChatMessage> chatMessages) throws ChatMessageException {
        tcs.setChatMessages(chatMessages);
    }
    
    
    public static synchronized TempChatMessageDAO getInstance() {
        if (instance == null) {
            instance = new TempChatMessageDAO();
        }
        return instance;
    }
    
    
}
