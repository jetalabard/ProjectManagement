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
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.view.dialog.DialogUpdateTask;

/**
 *
 * @author Jérémy
 */
public class UpdateDialogController extends Controller implements EventHandler<ActionEvent> {

    private final Stage stage;
    private final String what;
    private final DialogUpdateTask dialogUpdate;

    public UpdateDialogController() {
        this.stage = null;
        this.what = null;
        this.dialogUpdate = null;
    }

    public UpdateDialogController(String what, Stage stage, DialogUpdateTask dialogUpdate) {
        this.dialogUpdate = dialogUpdate;
        this.what = what;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        switch (what) {
            case Tags.CLOSE_DIALOG:
                if (stage != null) {
                    stage.close();
                }
                break;
            case Tags.PREVIOUS_TASK:
                //retour à la tâche que l'on avait précédemment
                stage.close();
                break;
            case Tags.APPLY_TASK:
                dialogUpdate.getTask().setPredecessor(dialogUpdate.getListePredecessor());
                dialogUpdate.getTask().setRessources(RessourcesTable.transformRessourceTableToResssource(dialogUpdate.getListeRessource()));
                new TaskController(dialogUpdate.getTable()).updateListTaskAfterUpdate(dialogUpdate.getIndexTask(), dialogUpdate.getTask());
                dialogUpdate.getTable().getItems().set(dialogUpdate.getIndexTask(),dialogUpdate.getTask());
                dialogUpdate.getTable().reload();
                stage.close();
                break;
            default:
                break;
        }
    }

    
}
