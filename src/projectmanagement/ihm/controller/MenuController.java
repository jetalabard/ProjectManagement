/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.view.Page;

/**
 *
 * @author Jérémy
 */
public class MenuController extends Controller  implements EventHandler<ActionEvent>{
    private String what;
    private String where;
    private Stage stage;
    private TableView<Task> table;
    private Slider slider;

    public MenuController(String what, String where,Stage mainStage) {
        this.what = what;
        this.where = where;
        this.stage = mainStage;
    }
    
    
    @Override
    public void handle(ActionEvent event) {
        switch (this.what) {
            case Tags.REDIRECTION:
                redirection();
                break;
            case Tags.PROJECT:
                project(event);
                break;
            case Tags.APP:
                application(event);
                break;
            default:
                break;
        }
    }

    private void project(ActionEvent event) {
        switch (this.where) {
            case Tags.NEW:
                CreateDialogProject(stage);
                break;
            case Tags.OPEN:
                CreateDialogOpenProject(stage);
                break;
            case Tags.SAVE:
                SaveProject(ProjectDAO.getInstance().getCurrentProject());
                break;
            case Tags.SAVEAS:
                CreateDialogSaveProjectAs(stage);
                break;
             case Tags.ADD_TASK:
                addTask(table);
                break;
            case Tags.OPEN_EXTERIOR:
                CreateDialogSaveProject(stage);
                break;
            case Tags.EXPORT_IMAGE:
                break;
            default:
                break;
        }
    }

   

    private void redirection() {
        if(this.where.equals(Tags.FACEBOOK)){
            redirectTo(Tags.FACEBOOK_URL);
        }
        if(this.where.equals(Tags.TWITTER)){
            redirectTo(Tags.TWITTER_URL);
        }
        if(this.where.equals(Tags.QUESTIONS)){
            redirectTo(Tags.QUESTIONS_URL);
        }
        if(this.where.equals(Tags.COMMENTS)){
            redirectTo(Tags.QUESTIONS_URL);
        }
    }

    private void application(ActionEvent event) {
        switch (this.where) {
            case Tags.QUIT:
                CreateDialogConfirmationSave(stage);
                break;
            case Tags.ZOOM:
                slider.adjustValue(slider.getValue()+0.25);
                break;
            case Tags.ZOOM_L:
                slider.adjustValue(slider.getValue()-0.25);
                break;
            case Tags.UNDO:
                ManageUndoRedo.getInstance().undo();
                break;
            case Tags.REDO:
                ManageUndoRedo.getInstance().redo();
                break;
            case Tags.PREFERENCE:
                CreateDialogPreference(stage);
                break;
            default:
                break;
        }
    }

    public void setTableView(TableView<Task> table) {
        this.table = table;
    }


    public void setSlider(Slider slider) {
        this.slider = slider;
    }


    
}
