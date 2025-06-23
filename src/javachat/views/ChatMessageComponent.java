/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.views;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javachat.models.ChatMessage;


/**
 *
 * @author thebe
 */



public class ChatMessageComponent extends HBox {
    private final ChatMessage data;

    public ChatMessageComponent(ChatMessage data) {
        this.data = data;
        // Components
        Label username = new Label(data.getAuthor().getUsername());
        Label messageContent = new Label(data.getContent());
        
        // Styling
        this.setPadding(new Insets(15, 25, 15, 25));
        this.setSpacing(10);
        this.getChildren().addAll(username, messageContent);
    }

    public String getMessage() {
        return data.getContent();
    }
}
