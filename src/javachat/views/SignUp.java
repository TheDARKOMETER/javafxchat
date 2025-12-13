/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
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
import javachat.exceptions.ConfirmPasswordException;
import javachat.interfaces.UserIdentifiable;
import javachat.models.LoginRequest;
import javachat.models.LoginResponse;
import javachat.models.SignUpRequest;
import javachat.models.User;
import javachat.services.RESTClient;
import javachat.services.UIPublisher;
import javachat.services.UserAuthStore;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import org.apache.http.cookie.Cookie;

/**
 *
 * @author thebe
 */
public class SignUp extends JApplet {

    private static JFXPanel jfxPanel;
    private static final int JFXPANEL_WIDTH_INT = 380;
    private static final int JFXPANEL_HEIGHT_INT = 230;
    private RESTClient restClient = RESTClient.getInstance();
    private ChatFrame instance;
    private UserAuthStore userService = UserAuthStore.getInstance();
    private Logger signUpLogger = Logger.getLogger(SignUp.class.getName());
    private UIPublisher uiPublisher = UIPublisher.getUIPublisherInstance();
    private JFrame frame;
    private PasswordField passwordInput;
    private PasswordField confirmPasswordInput;
    private TextField emailInput;
    private TextField usernameInput;
    private Button signUpBtn;

    @Override
    public void init() {
        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(jfxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            createScene();
        });
    }

    // Called by Main Frame
    public void initSignUpPage() {
        SignUp applet = this;
        applet.init();
        frame = new JFrame("Sign Up");
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
        usernameInput = new TextField();
        loginForm.add(usernameLabel, 0, 0);
        loginForm.add(usernameInput, 1, 0);

        Label emailLabel = new Label("Email: ");
        emailInput = new TextField();
        loginForm.add(emailLabel, 0, 1);
        loginForm.add(emailInput, 1, 1);

        Label passwordLabel = new Label("Password: ");
        passwordInput = new PasswordField();
        loginForm.add(passwordLabel, 0, 2);
        loginForm.add(passwordInput, 1, 2);

        Label confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordInput = new PasswordField();
        loginForm.add(confirmPasswordLabel, 0, 3);
        loginForm.add(confirmPasswordInput, 1, 3);

        signUpBtn = new Button("Sign Up");
        signUpBtn.setOnAction(e -> {
            signUpLogger.info("Attempting to sign up: " + usernameInput.getText() + " " + passwordInput.getText() + " " + emailInput.getText());

            try {
                if (!passwordInput.getText().equals(confirmPasswordInput.getText())) {
                    throw new ConfirmPasswordException("Passwords must match");
                }
            } catch (ConfirmPasswordException ex) {
                showError(ex.getMessage());
                ex.printStackTrace();
                setInputState(false);
                return;
            }
            try {
                setInputState(true);
                String signUpResponse = restClient.signUp(new SignUpRequest(usernameInput.getText(), passwordInput.getText(), emailInput.getText()));
                if (signUpResponse != null) {
                    signUpLogger.info(">>> Signup Response: " + signUpResponse);
                    LoginResponse loginResponse = restClient.login(new LoginRequest(usernameInput.getText(), passwordInput.getText()));
                    userService.login((UserIdentifiable) loginResponse);
                    uiPublisher.notifySubscribers();
                    ButtonType signUpButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    Dialog<String> successDialog = new Dialog<>();
                    successDialog.getDialogPane().setContentText("You have successfully signed up.");
                    for (Cookie cookie : restClient.getCookieStore().getCookies()) {
                        if ("JSESSIONID".equals(cookie.getName())) {
                            signUpLogger.info(">>> JSESSIONID: " + cookie.getValue());
                        }
                    }
                    successDialog.getDialogPane().getButtonTypes().add(signUpButtonType);
                    successDialog.getDialogPane().lookupButton(signUpButtonType).setDisable(false);
                    successDialog.show();
                    successDialog.setResultConverter(button -> {
                        if (button == signUpButtonType) {
                            successDialog.close();
                            frame.dispose();
                        }
                        return null;
                    });
                }
            } catch (Exception ex) {
                showError(ex.getMessage());
                ex.printStackTrace();
                setInputState(false);
            }
        });

        HBox centerHBox = new HBox();
        centerHBox.getChildren().add(signUpBtn);
        centerHBox.setAlignment(Pos.CENTER);
        loginForm.add(centerHBox, 0, 4, 2, 1);

        jfxPanel.setScene(new Scene(loginForm));
    }

    private void setInputState(boolean state) {
        usernameInput.setDisable(state);
        passwordInput.setDisable(state);
        emailInput.setDisable(state);
        confirmPasswordInput.setDisable(state);
        signUpBtn.setDisable(state);
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        signUpLogger.severe(message);
        alert.showAndWait();
    }
}
