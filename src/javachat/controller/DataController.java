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
    
    private void createChatMessagecomponents(ArrayList<ChatMessage> cmsgs) {
        for (ChatMessage cmsg : cmsgs) {
            chatMessageComponents.add(new ChatMessageComponent(cmsg));
        }
    }

    public ArrayList<ChatMessageComponent> getChatMessagesToRender() {
        chatMessages = cmd.getAllChatMessages();
        this.createChatMessagecomponents(chatMessages);
        return this.chatMessageComponents;
    }
}
