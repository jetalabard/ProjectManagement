/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import projectmanagement.application.model.DAO;

/**
 *
 * @author Jérémy
 */
public class DialogConfirmationSaveController extends Controller implements EventHandler<ActionEvent> {

    private final String what;
    private final Stage stage;
    private final Stage stageParent;
    private final String tags;

    public DialogConfirmationSaveController(String what, Stage stage, Stage stageParent, String tags) {
        this.what = what;
        this.stage = stage;
        this.stageParent = stageParent;
        this.tags = tags;
    }

    @Override
    public void handle(ActionEvent event) {
        switch (what) {
            case Tags.CLOSE_DIALOG:
                if (stage != null) {
                    stage.close();
                } 
                break;
            case Tags.CONFIRMATION_NO_SAVE:
                Quit();
                break;
            case Tags.CONFIRMATION_YES_SAVE://sauvegarde et quitte
                SaveProject(DAO.getInstance().getCurrentProject());
                Quit();
                break;
            case Tags.CONFIRMATION_NO_SAVE_NOT_QUIT:
                if (tags.equals(Tags.OPEN_PROJECT)) {
                    createDialogCreateOrOpenProjectAndQuitPrecedentProject(tags, stageParent);
                    stage.close();
                } else {//retour à l'accueil
                    stage.close();
                    stageParent.close();
                    HomeController.showHome();
                }
                break;
            case Tags.CONFIRMATION_YES_SAVE_NOT_QUIT://sauvegarde mais ne quitte pas
                SaveProject(DAO.getInstance().getCurrentProject());
                if (tags.equals(Tags.OPEN_PROJECT)) {
                    stage.close();
                    createDialogCreateOrOpenProjectAndQuitPrecedentProject(tags, stageParent);
                } 
                else {//retour à l'accueil
                    stage.close();
                    stageParent.close();
                    HomeController.showHome();
                }
                break;
            default:
                break;
        }
    }

}
