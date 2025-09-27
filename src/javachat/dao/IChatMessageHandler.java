/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javachat.models.ChatMessage;
import javachat.exceptions.ChatMessageException;
/**
 *
 * @author thebe
 */

// Strictly for testing only, not for actual use.
public interface IChatMessageHandler {
    public ChatMessage getChatMessage(UUID uuid) throws ChatMessageException;
    public ArrayList<ChatMessage> getAllChatMessages() throws ChatMessageException;
    public void addChatMessage(ChatMessage msg) throws ChatMessageException;
    public void setChatHistory(ArrayList<ChatMessage> chatMessages) throws ChatMessageException;
}
