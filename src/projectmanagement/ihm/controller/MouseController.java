/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Jérémy
 */
public class MouseController implements EventHandler<MouseEvent> {
    private final BorderPane pane;
    private final String type;

    public MouseController(BorderPane pane,String type) {
        this.pane = pane;
        this.type = type;
    }

    
    
    @Override
    public void handle(MouseEvent event) {
        if(type.equals(String.valueOf(MouseEvent.MOUSE_ENTERED)) ){
             focusGained(pane);
        }
        else if(type.equals(String.valueOf(MouseEvent.MOUSE_EXITED)) ){
             focusLost(pane);
        }
        else{
            
        }
    }
    
     private void focusGained(BorderPane pane) {
        pane.setStyle("-fx-background-color: darkgray;"
                + "-fx-background-radius: 5.0; "
                + "-fx-padding: 8;"
                + "-fx-background-insets: 0.0 5.0 0.0 5.0");
    }

    private void focusLost(BorderPane pane) {
        pane.setStyle("-fx-background-color: #d7d7c1;"
                + "-fx-background-radius: 5.0; "
                + "-fx-padding: 8;"
                + "-fx-background-insets: 0.0 5.0 0.0 5.0");
    }
}
