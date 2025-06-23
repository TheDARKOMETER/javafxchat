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

    TempChatService tcs;
    
    public TempChatMessageDAO() {
        tcs = TempChatService.getInstance();
    }

    
    @Override
    public ChatMessage getChatMessage(UUID uuid) throws ChatMessageException {
        return tcs.getTempMsg();
    }

    @Override
    public ArrayList<ChatMessage> getAllChatMessages() throws ChatMessageException {
        return tcs.getChatMessages();
    }
    
}
