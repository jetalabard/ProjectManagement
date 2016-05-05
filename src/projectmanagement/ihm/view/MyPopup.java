/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author Jérémy
 */ 
public class MyPopup {
    
    private static boolean  messageIsShow = false;

    private static Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased((MouseEvent e) -> {
            popup.hide();
        });
        label.getStylesheets().add("/ressources/popup.css");
        label.getStyleClass().add("popup");
        popup.getContent().add(label);
        return popup;
    }

    public static void showPopupMessage(final String message, final Stage stage) {
        if (messageIsShow == false) {
            messageIsShow = true;
            final Popup popup = createPopup(message);
            popup.setOnShown((WindowEvent e) -> {
                popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
                popup.setY((stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2) - 100);
            });
            popup.show(stage);

            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished((ActionEvent event) -> {
                popup.hide();
                messageIsShow = false;
            });
            delay.play();
        }
       
    }

}
