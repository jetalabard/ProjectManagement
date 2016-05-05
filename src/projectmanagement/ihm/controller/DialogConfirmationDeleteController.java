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
public class DialogConfirmationDeleteController extends Controller implements EventHandler<ActionEvent>  {
    private final String what;
    private final Stage stage;
    private final Stage stageParent;

    public DialogConfirmationDeleteController(String what,Stage stage, Stage stageParent) {
        this.what = what;
        this.stage = stage;
        this.stageParent =stageParent;
    }
    
    @Override
    public void handle(ActionEvent event) 
    {
        switch (what) {
            case Tags.CLOSE_DIALOG:
                if (stage != null) {
                    stage.close();
                }   break;
            case Tags.CONFIRMATION_YES_DELETE:
                int idProject = DAO.getInstance().getCurrentProject().getId();
                DAO.getInstance().removeProject(idProject);
                DAO.getInstance().setCurrentProject(null);
                stage.close();
                stageParent.close();
                HomeController.showHome();
                break;
            case Tags.CONFIRMATION_YES_INIT_DATABASE:
                DAO.getInstance().deleteAll();
                stage.close();
                stageParent.close();
                HomeController.showHome();
                break;
            default:
                break;
        }
    }
    
}
