/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.views;
import java.util.Date;
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
import javafx.scene.layout.VBox;


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
        Date date = new Date(data.getTimestamp());
        Label timestamp = new Label(date.toString());
        username.setStyle("-fx-font-weight:bold;");
        VBox messageVBox = new VBox();
        
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
