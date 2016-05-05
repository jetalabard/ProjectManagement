/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class DialogChangePropertiesController extends Controller implements EventHandler<ActionEvent> {

    private final String what;
    private final TextField FieldName;
    private final Label labelError;
    private final Stage stage;
    private final Stage stageParent;

    public DialogChangePropertiesController(String what, TextField field, Label labelError, Stage stage, Stage stageParent) {
        super();
        this.what = what;
        this.FieldName = field;
        this.labelError = labelError;
        this.stage = stage;
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
            case Tags.CHANGE_NAME:
                boolean exist = false;
                for (Project proj : DAO.getInstance().getAllProject()) {
                    if (proj.getTitle().equals(((TextField) this.FieldName).getText())) {
                        exist = true;
                    }
                }
                if (((TextField) this.FieldName).getText().equals("") || exist == true) {
                    labelError.setText(getManagerLanguage().getLocalizedTexte("ErrorNameExist"));
                } else {
                    String text = ((TextField) this.FieldName).getText();
                    DAO.getInstance().updateProject(DAO.getInstance().getCurrentProject().getId(), text, MyDate.now());
                    DAO.getInstance().getCurrentProject().setTitle(text);
                    stageParent.setTitle(text + " - " +getManagerLanguage().getLocalizedTexte("AppTitle"));
                    stage.close();

                }
                break;
            default:
                break;
        }
    }

}
