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
        Label username = new Label(data.getAuthor().getUsername() + ":");
        username.setStyle("-fx-font-weight:bold;");
        Label messageContent = new Label(data.getContent());
        
        // CMC Styling
        this.setPadding(new Insets(15, 25, 15, 25));
        this.setSpacing(10);
        this.setPrefHeight(this.computeMinWidth(USE_PREF_SIZE));
        this.setStyle("-fx-background-color:#F6F6F6;-fx-border-color:black;-fx-border-width:1;-fx-border-style:solid;");
        this.getChildren().addAll(username, messageContent);
    }

    public String getMessage() {
        return data.getContent();
    }
}
