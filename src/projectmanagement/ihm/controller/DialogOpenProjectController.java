/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
public class DialogOpenProjectController extends Controller implements EventHandler<ActionEvent> {

    private final String what;
    private final Stage stage;
    private final ComboBox<Project> FieldName;
    private final Stage stageParent;

    public DialogOpenProjectController(String what, Stage stage, ComboBox<Project> FieldName, Stage stageParent) {
        this.what = what;
        this.stage = stage;
        this.FieldName = FieldName;
        this.stageParent = stageParent;
    }

    @Override
    public void handle(ActionEvent event) {
        switch (what) {
            case Tags.CLOSE_DIALOG:
                if (stage != null) {
                    stage.close();
                }
                break;
            case Tags.OPEN_PROJECT:
                Project p = this.FieldName.getSelectionModel().getSelectedItem();
                OpenProject(p, stage, stageParent);
                break;
            default:
                break;
        }
    }

}
