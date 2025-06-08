 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXSwingMain.java to edit this template
 */
package javachat;

import impl.BCrypt;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author thebe
 */
public class ChatFrame extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 900;
    private static final int JFXPANEL_HEIGHT_INT = 750;
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
            }
        });
    }
    
    private void createScene() {

        BorderPane root = new BorderPane();
        
        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(15, 12, 15, 12));
        headerBox.setSpacing(15);
        headerBox.setStyle("-fx-background-color:#336699;");
        
        Text title = new Text("JSocketChat");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setFill(Color.WHITE);
        
        StackPane settingsStack = new StackPane();
        
        Text settingsUnicode = new Text("☰");
        settingsUnicode.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        settingsUnicode.setFill(Color.WHITE);
        settingsStack.setAlignment(Pos.CENTER_RIGHT);
        
        
        StackPane settingsPane = new StackPane();
        settingsPane.setMaxSize(30, 30);
        
        Rectangle settingsPaneShape = new Rectangle(30,30);
        settingsPaneShape.setArcHeight(10);
        settingsPaneShape.setArcWidth(10);
        settingsPaneShape.setFill(Color.web("#102E50"));
            
        settingsPane.getChildren().add(settingsPaneShape);
        
        settingsStack.getChildren().add(settingsPane);
        settingsPane.getChildren().add(settingsUnicode);

        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.getChildren().add(title);
        headerBox.getChildren().add(settingsStack);
        HBox.setHgrow(settingsStack, Priority.ALWAYS);
        
        VBox userList = new VBox();
        userList.setPadding(new Insets(15, 12, 15, 12));
        Text ulLabel = new Text("Users:");
        ulLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        userList.getChildren().add(ulLabel);
        userList.setStyle("-fx-background-color:white;");
        
        VBox chatWindow = new VBox();
        ScrollPane chatScrollPane = new ScrollPane();
        chatScrollPane.setPrefSize(15, 150);
        Rectangle testItem = new Rectangle(200,200,Color.RED);
        chatScrollPane.setContent(testItem);
        chatWindow.getChildren().add(chatScrollPane);
        
        root.setTop(headerBox);
        root.setLeft(userList);
        root.setCenter(chatWindow);
        fxContainer.setScene(new Scene(root));
    }
    
}
