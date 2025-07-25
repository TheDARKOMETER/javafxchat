/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXSwingMain.java to edit this template
 */
package javachat.views;

import impl.BCrypt;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.scene.control.TextField;
import java.util.List;
import javachat.services.StompClient;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javafx.animation.TranslateTransition;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javachat.views.SignUp;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javachat.controller.DataController;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;

import javachat.dao.TempChatMessageDAO;
import javachat.models.ChatMessage;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import org.springframework.messaging.simp.stomp.StompSession;
import javachat.models.User;
import javachat.services.UserService;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 *
 * @author thebe
 */
public class ChatFrame extends JApplet {

    private static final int JFXPANEL_WIDTH_INT = 900;
    private static final int JFXPANEL_HEIGHT_INT = 750;
    private static ArrayList<ChatMessageComponent> allChatMessages;
    private TempChatMessageDAO tcmd = TempChatMessageDAO.getInstance();
    private UserService userService = UserService.getInstance();
    private DataController dataController = new DataController(tcmd, userService);
    private VBox chatStack;
    private VBox slidingMenu;
    private ScrollPane chatScrollPane;
    private static Logger dataLogger = Logger.getLogger(ChatFrame.class.getName());
    private StompSession stompSession;
    private User tempUser = new User("Vonchez", System.currentTimeMillis(), "kek@kek.com");
    private static StompClient client = new StompClient("ws://localhost:8080/jsocketapi/javafxchat");
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }

                JFrame frame = new JFrame("JavaFX 2 in Swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JApplet applet = new ChatFrame();
                applet.init();

                frame.setContentPane(applet.getContentPane());

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                applet.start();
                System.out.println("Application Started, intiializing connection for client");
            }
        });
    }

    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                createScene();
                new Thread(() -> {
                    setStompSession(initializeSession(chatStack));
                }).start();
            }
        });
    }

    private void createScene() {

        Desktop desktop = Desktop.getDesktop();
        BorderPane root = new BorderPane();
        SplitPane middlePane = new SplitPane();
        MenuBar userMenuBar = new MenuBar();
        Menu menuAuthentication = new Menu("Settings");
        MenuItem loginMenuItem = new MenuItem("Login");
        loginMenuItem.setOnAction(e -> {
            dataLogger.info("Login option clicked");
            SignUp signUp = new SignUp();
            signUp.init();
        });
        MenuItem signupMenuItem = new MenuItem("Sign Up");
        menuAuthentication.getItems().addAll(loginMenuItem, signupMenuItem);
        userMenuBar.getMenus().add(menuAuthentication);

        VBox menuAndHeaderVBox = new VBox();
        HBox headerHBox = new HBox();
        headerHBox.setPadding(new Insets(15, 12, 15, 12));
        headerHBox.setSpacing(15);
        headerHBox.setStyle("-fx-background-color:#336699;");

        Text title = new Text("JSocketChat");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setFill(Color.WHITE);
        menuAndHeaderVBox.getChildren().addAll(userMenuBar, headerHBox);

        Region spacer = new Region();
        StackPane settingsStack = new StackPane();
        Text settingsUnicode = new Text("☰");
        settingsUnicode.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        settingsUnicode.setFill(Color.WHITE);
        settingsStack.setAlignment(Pos.CENTER_RIGHT);
        StackPane settingsPane = new StackPane();
        settingsPane.setMaxSize(30, 30);
        Rectangle settingsPaneShape = new Rectangle(30, 30);
        settingsPaneShape.setArcHeight(10);
        settingsPaneShape.setArcWidth(10);
        settingsPaneShape.setFill(Color.web("#102E50"));
        settingsPane.getChildren().add(settingsPaneShape);
        settingsStack.getChildren().add(settingsPane);
        settingsPane.getChildren().add(settingsUnicode);
        Button settingsButton = new Button();
        settingsButton.setGraphic(settingsStack);
        settingsButton.setStyle("-fx-background-color: transparent;");
        settingsButton.setOnMouseEntered(e -> {
            Effect shadow = new DropShadow();
            settingsButton.setEffect(shadow);
        });

        settingsButton.setOnMouseExited(e -> {
            settingsButton.setEffect(null);
        });

        headerHBox.setAlignment(Pos.CENTER_LEFT);
        headerHBox.getChildren().addAll(title, spacer, settingsButton);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox userList = new VBox();
        userList.setAlignment(Pos.BASELINE_CENTER);
        userList.setPadding(new Insets(15, 12, 15, 12));
        Text ulLabel = new Text("Users:");
        ulLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        userList.getChildren().add(ulLabel);
        userList.setMaxWidth(120);
        userList.setStyle("-fx-background-color:white;");

        // Demo Code to see if retrieving is possible
        //allChatMessages = dataController.getChatMessagesToRender();
        // Chat Messages
        chatScrollPane = new ScrollPane();
        chatScrollPane.setFitToWidth(true);
        chatStack = new VBox();
        chatScrollPane.setContent(chatStack);
        chatStack.heightProperty().addListener((obs, oldVal, newVal) -> {
            chatScrollPane.setVvalue(1.0);
        });
//        if (allChatMessages.size() > 0) {
//            dataLogger.info("First chat message content is: " + allChatMessages.get(0).getMessage());
//            chatStack.getChildren().addAll(allChatMessages);
//        }

        // Add ChatMessage and userList to SplitPane
        middlePane.getItems().addAll(userList, chatScrollPane);
        middlePane.setOrientation(Orientation.HORIZONTAL);
        middlePane.setDividerPositions(1d);

        // User Controls
        HBox userControls = new HBox();
        TextField chatInputTxt = new TextField();
        chatInputTxt.setPromptText("Enter a message");
        HBox.setHgrow(chatInputTxt, Priority.ALWAYS);
        Button sendBtn = new Button("Send");
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stompSession.send("/app/globalchat", new ChatMessage(chatInputTxt.getText(), System.currentTimeMillis(), tempUser, 0, 0, UUID.randomUUID()));
                chatInputTxt.clear();
            }
        });

        chatInputTxt.setOnAction(e -> {
            sendBtn.fire();
        });

        userControls.getChildren().addAll(chatInputTxt, sendBtn);

        // Set BorderPane Elements (Top, Left, Center, Right, Bottom)
        root.setTop(menuAndHeaderVBox);
        //root.setLeft(userList);
        root.setCenter(middlePane);
        root.setBottom(userControls);

        // Stackpane as new root in order to add sliding menu
        StackPane newRoot = new StackPane(root);

        // Sliding menu
        slidingMenu = new VBox();
        StackPane.setAlignment(slidingMenu, Pos.CENTER_RIGHT); // align it to the right
        slidingMenu.setMaxHeight(Region.USE_COMPUTED_SIZE);
        slidingMenu.setPrefWidth(200);
        slidingMenu.setMaxWidth(Region.USE_PREF_SIZE);
        slidingMenu.setTranslateX(200);
        slidingMenu.setStyle("-fx-background-color: #333; -fx-padding: 10;");
        settingsButton.setOnAction((e) -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), slidingMenu);
            tt.setByX(-200);
            tt.play();
        });

        HBox slidingMenuItem = new HBox();
        Button closeSlidingMenuButton = new Button("Close");
        closeSlidingMenuButton.setOnAction((evt) -> {
            closeSlidingMenu();
        });
        closeSlidingMenuButton.setTextFill(Color.WHITE);
        closeSlidingMenuButton.setStyle("-fx-background-color: transparent;");
        slidingMenuItem.setAlignment(Pos.CENTER);
        slidingMenuItem.getChildren().add(closeSlidingMenuButton);

        HBox slidingMenuItem2 = new HBox();
        Button slidingMenuButton2 = new Button("Report an Issue");
        slidingMenuButton2.setOnAction((evt) -> {
            dataLogger.info("Clicking uri");
            try {
                URI uri = new URI("http://localhost:8080/jsocketapi/index.htm");
                desktop.browse(uri);
            } catch (IOException ie) {
                ie.printStackTrace();
            } catch (URISyntaxException use) {
                use.printStackTrace();
            }
        });
        slidingMenuItem2.setAlignment(Pos.CENTER);
        slidingMenuButton2.setTextFill(Color.WHITE);
        slidingMenuButton2.setStyle("-fx-background-color: transparent");
        slidingMenuItem2.getChildren().add(slidingMenuButton2);

        slidingMenu.setSpacing(10);

        HBox slidingMenuItem3 = new HBox();
        Button slidingMenuButton3 = new Button("Contact");
        slidingMenuButton3.setOnAction(e -> {
            dataLogger.info("Clicking uri");
            try {
                URI uri = new URI("http://localhost:8080/jsocketapi/index.htm");
                desktop.browse(uri);
            } catch (IOException ie) {
                ie.printStackTrace();
            } catch (URISyntaxException use) {
                use.printStackTrace();
            }
        });
        slidingMenuItem3.setAlignment(Pos.CENTER);
        slidingMenuButton3.setTextFill(Color.WHITE);
        slidingMenuButton3.setStyle("-fx-background-color: transparent");
        slidingMenuItem3.getChildren().add(slidingMenuButton3);
        slidingMenu.setSpacing(16);

        slidingMenu.getChildren().addAll(slidingMenuItem, slidingMenuItem2, slidingMenuItem3);

        root.setOnMouseClicked(e -> {
            closeSlidingMenu();
        });

        for (Node child : root.getChildrenUnmodifiable()) {
            child.setOnMouseClicked(e -> {
                closeSlidingMenu();
            });
        }

        HBox slidingMenuItemFooterHBox = new HBox();
        Label footer = new Label("© 2025 JSocketChat.");
        slidingMenuItemFooterHBox.getChildren().add(footer);
        slidingMenuItemFooterHBox.setAlignment(Pos.BOTTOM_CENTER);
        slidingMenu.getChildren().add(slidingMenuItemFooterHBox);
        VBox.setVgrow(slidingMenuItemFooterHBox, Priority.ALWAYS);
        footer.setTextFill(Color.WHITE);

        newRoot.getChildren().add(slidingMenu);
        fxContainer.setScene(new Scene(newRoot));

    }

    private StompSession initializeSession(Node sharedComponent) {
        StompSession session = null;
        System.out.println("Creating client");
        try {
            session = client.createClient(sharedComponent, chatScrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    private void setStompSession(StompSession ss) {
        this.stompSession = ss;
    }

    private void closeSlidingMenu() {
        dataLogger.info("Closing sliding menu");
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), slidingMenu);
        tt.setByX(200);
        tt.play();
    }

}
