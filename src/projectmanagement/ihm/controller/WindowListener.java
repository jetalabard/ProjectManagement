/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import projectmanagement.application.model.DAO;

/**
 *
 * @author Jérémy
 */
public class WindowListener extends Controller implements EventHandler<WindowEvent> {
    private final String tags;
    private final Stage stage;

    public WindowListener(String tags,Stage stage) {
        this.tags = tags;
        this.stage = stage;
    }

    @Override
    public void handle(WindowEvent event) {
        event.consume();
        if (this.tags.equals(Tags.QUIT)) 
        {
            CreateDialogConfirmationSave(stage);
        }
    }

    
}
