/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class DialogCreateProjectController extends Controller implements EventHandler<ActionEvent> {

    private final Stage stage;
    private final String what;
    private final Stage stageParent;
    private final TextField text;
    private final Label error;

    public DialogCreateProjectController(String what, Stage stage, Stage stageParent,
            TextField text, Label error) {
        this.stage = stage;
        this.what = what;
        this.stageParent = stageParent;
        this.text = text;
        this.error = error;
    }

    @Override
    public void handle(ActionEvent event) {
        switch (what) {
            case Tags.CLOSE_DIALOG:
                if (stage != null) {
                    stage.close();
                }   break;
            case Tags.CREATE_PROJECT:
                createproject();
                break;
            default:
                break;
        }
    }

    private void createproject() {
        boolean exist = false;
        for (Project proj : DAO.getInstance().getAllProject()) {
            if (proj.getTitle().equals(((TextField) this.text).getText())) {
                exist = true;
            }
        }
        if (((TextField) this.text).getText().equals("") || exist == true) {
            error.setText(ManagerLanguage.getInstance().getLocalizedTexte("ErrorNameExist"));
        } else {
            Project p = DAO.getInstance().insertProject(((TextField) this.text).getText(), MyDate.now());
            OpenProject(p, stage, stageParent);

        }
    }

}
