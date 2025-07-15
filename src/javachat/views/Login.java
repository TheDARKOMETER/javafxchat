/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author thebe
 */
public class Login extends JApplet {

    private static JFXPanel jFXPanel;
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 140;

    @Override
    public void init() {
        jFXPanel = new JFXPanel();
        jFXPanel.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(jFXPanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            createScene();
        });
    }

    public void initLoginPage() {
        JApplet applet = new Login();
        applet.init();
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(applet.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        applet.start();
    }

    private void createScene() {
        VBox contentBox = new VBox();

        HBox usernameBox = new HBox();
        Label usernameLabel = new Label("Username: ");
        TextField usernameInput = new TextField();
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.getChildren().addAll(usernameLabel, usernameInput);

        HBox passwordBox = new HBox();
        Label passwordLabel = new Label("Password: ");
        TextField passwordInput = new TextField();
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.getChildren().addAll(passwordLabel, passwordInput);

        Button loginBtn = new Button();
        loginBtn.setOnAction(e -> {
            // TODO: Encrypt first, then perisst to User class
        });
        
        // Layout Constraints
        HBox.setHgrow(usernameInput, Priority.ALWAYS);
        HBox.setHgrow(passwordInput, Priority.ALWAYS);

        contentBox.getChildren().addAll(usernameBox, passwordBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setSpacing(10);
        contentBox.setPadding(new Insets(2, 2, 2, 2));

        jFXPanel.setScene(new Scene(contentBox));
    }
}
