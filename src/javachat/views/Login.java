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
import javachat.interfaces.UserIdentifiable;
import javachat.models.LoginRequest;
import javachat.models.LoginResponse;
import javachat.models.User;
import javachat.services.RESTClient;
import javachat.services.UIPublisher;
import javachat.services.UserAuthStore;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;

/**
 *
 * @author thebe
 */
public class Login extends JApplet {

    private static JFXPanel jfxPanel;
    private static final int JFXPANEL_WIDTH_INT = 380;
    private static final int JFXPANEL_HEIGHT_INT = 210;
    private RESTClient restClient = new RESTClient();
    private UserAuthStore userService = UserAuthStore.getInstance();
    private UIPublisher uiPublisher = UIPublisher.getUIPublisherInstance();
    private JFrame frame;
    Logger loginLogger = Logger.getLogger(SignUp.class.getName());

    @Override
    public void init() {
        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(jfxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            createScene();
        });
    }

    public void initLoginPage() {
        JApplet applet = this;
        applet.init();
        frame = new JFrame("Login");
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

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordInput = new PasswordField();
        loginForm.add(passwordLabel, 0, 1);
        loginForm.add(passwordInput, 1, 1);

        Button loginBtn = new Button("Log in");
        loginBtn.setOnAction(e -> {
            try {
                loginBtn.setDisable(true);
                usernameInput.setDisable(true);
                passwordInput.setDisable(true);
                LoginResponse loginUser = restClient.login(new LoginRequest(usernameInput.getText(), passwordInput.getText()));
                if (loginUser != null) {

                    userService.login((UserIdentifiable) loginUser);
                    uiPublisher.notifySubscribers();
                    ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    Dialog<String> successDialog = new Dialog<>();
                    successDialog.getDialogPane().getButtonTypes().add(loginButtonType);
                    successDialog.getDialogPane().lookupButton(loginButtonType).setDisable(false);
                    successDialog.getDialogPane().setContentText("You are now logged in");
                    successDialog.show();
                    successDialog.setResultConverter(button -> {
                        if (button == loginButtonType) {
                            successDialog.close();
                            frame.dispose();
                        }
                        return null;
                    });

                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Problem with Login");
                alert.setHeaderText("Something went wrong");
                loginLogger.severe(ex.getMessage());
                ex.printStackTrace();
                alert.showAndWait();
                loginBtn.setDisable(false);
                usernameInput.setDisable(false);
                passwordInput.setDisable(false);

            }
        });

        HBox centerHBox = new HBox();
        centerHBox.getChildren().add(loginBtn);
        centerHBox.setAlignment(Pos.CENTER);
        loginForm.add(centerHBox, 0, 4, 2, 1);

        jfxPanel.setScene(new Scene(loginForm, JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
    }
}
