/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.ManagerLanguage;

/**
 *
 * @author Jérémy
 */
public class ClickController extends Controller implements EventHandler<ActionEvent> {

    private final Stage mainstage;
    private String what;
    private final TextField FieldName;
    private final TextField FieldPath;
    private Dialog dialog;

    public ClickController(String what, Stage mainstage,TextField name,TextField path) {
        this.what = what;
        this.mainstage = mainstage;
        this.FieldName = name;
        this.FieldPath = path;
    }
    
    public ClickController(String what, Stage mainstage,TextField path) {
        this.what = what;
        this.mainstage = mainstage;
        this.FieldName = null;
        this.FieldPath = path;
    }

    public ClickController(String what, Stage mainStage) {
        this.what = what;
        this.mainstage = mainStage;
        this.FieldName = null;
        this.FieldPath = null;
    }

    @Override
    public void handle(ActionEvent event) {
        if(what != null && what.equals(Tags.BROWSE_FILE_TO_CREATE_PROJECT)){
            String path = createFileChooser(ManagerLanguage.getInstance().getLocalizedTexte("ProjectLocation"),mainstage);
            FieldPath.setText(path);
        } else if(what != null && what.equals(Tags.CLOSE_DIALOG)){
            if(dialog != null){
                mainstage.close();
            }
        }
    }
}
