/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javachat.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author thebe
 */
public class SignUp extends JApplet {

    private static JFXPanel jFXPanel;
    private static final int JFXPANEL_WIDTH_INT = 200;
    private static final int JFXPANEL_HEIGHT_INT = 100;

    @Override
    public void init() {
        JApplet applet = new SignUp();
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(applet.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        applet.start();
        jFXPanel = new JFXPanel();
        jFXPanel.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(jFXPanel, BorderLayout.CENTER);
        Platform.runLater(() -> {
            createScene();
        });
    }

    private void createScene() {

        VBox contentBox = new VBox();
        jFXPanel.setScene(new Scene(contentBox));
    }
}
