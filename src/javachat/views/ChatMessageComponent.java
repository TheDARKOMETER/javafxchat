/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.views;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javachat.models.ChatMessage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


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
        this.setBorder(new Border(new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(1))));
        this.getChildren().addAll(username, messageContent);
    }

    public String getMessage() {
        return data.getContent();
    }
}
