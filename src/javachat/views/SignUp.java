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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javax.swing.JApplet;
import javax.swing.JFrame;
import java.util.logging.Logger;
import javachat.models.User;
import javachat.services.RESTClient;
import javachat.services.UserAuthStore;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;

/**
 *
 * @author thebe
 */
public class SignUp extends JApplet {
    
    private static JFXPanel jfxPanel;
    private static final int JFXPANEL_WIDTH_INT = 380;
    private static final int JFXPANEL_HEIGHT_INT = 230;
    private RESTClient restClient = new RESTClient();
    private ChatFrame instance;
    private UserAuthStore userService = UserAuthStore.getInstance();
    private Logger signUpLogger = Logger.getLogger(SignUp.class.getName());
    
    public SignUp(ChatFrame instance) {
        this.instance = instance;
    }
    
    @Override
    public void init() {
        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(jfxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            createScene();
        });
    }
    
    public void initSignUpPage() {
        JApplet applet = new SignUp(instance);
        applet.init();
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(applet.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        applet.start();
    }
    
    private void createScene() {
        
        GridPane loginForm = new GridPane();
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setHgap(10);
        loginForm.setVgap(10);
        loginForm.setPadding(new Insets(25, 25, 25, 25));
        
        Label usernameLabel = new Label("Username: ");
        TextField usernameInput = new TextField();
        loginForm.add(usernameLabel, 0, 0);
        loginForm.add(usernameInput, 1, 0);
        
        Label emailLabel = new Label("Email: ");
        TextField emailInput = new TextField();
        loginForm.add(emailLabel, 0, 1);
        loginForm.add(emailInput, 1, 1);
        
        Label passwordLabel = new Label("Password: ");
        PasswordField passwordInput = new PasswordField();
        loginForm.add(passwordLabel, 0, 2);
        loginForm.add(passwordInput, 1, 2);
        
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        PasswordField confirmPasswordInput = new PasswordField();
        loginForm.add(confirmPasswordLabel, 0, 3);
        loginForm.add(confirmPasswordInput, 1, 3);
        
        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(e -> {
            User user = new User(usernameInput.getText(), System.currentTimeMillis(), emailInput.getText(), passwordInput.getText());
            signUpLogger.info("Attempting to sign up: " + usernameInput.getText() + " " + passwordInput.getText());
            User response = restClient.signUp(user);
            signUpLogger.info("User posted: " + response.getUsername());
            userService.setUser(response);
            instance.updateUI();
            if (response != null) {
                ButtonType signUpButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                Dialog<String> successDialog = new Dialog<>();
                successDialog.getDialogPane().setContentText("You have successfully signed up.");
                successDialog.getDialogPane().getButtonTypes().add(signUpButtonType);
                successDialog.getDialogPane().lookupButton(signUpButtonType).setDisable(false);
                successDialog.show();
            }
            
        });
        
        HBox centerHBox = new HBox();
        centerHBox.getChildren().add(signUpBtn);
        centerHBox.setAlignment(Pos.CENTER);
        loginForm.add(centerHBox, 0, 4, 2, 1);
        
        jfxPanel.setScene(new Scene(loginForm, JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
    }
}
