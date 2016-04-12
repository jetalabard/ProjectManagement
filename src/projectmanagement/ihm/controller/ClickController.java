/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.DialogCreateProject;
import projectmanagement.ihm.view.DialogOpenProject;

/**
 *
 * @author Jérémy
 */
public class ClickController extends Controller implements EventHandler<ActionEvent> {
    
    private final Stage stage;
    private String what;
    private final Node FieldName;
    private Stage stageParent;
    private String tags;
    
    public ClickController(String what, Stage dialogStage, Stage stageParent, Node name) {
        this.what = what;
        this.stage = dialogStage;
        this.FieldName = name;
        this.stageParent = stageParent;
    }
    
    public ClickController(String what, Stage stage) {
        this.what = what;
        this.stage = stage;
        this.FieldName = null;
    }
    
    @Override
    public void handle(ActionEvent event) {
        if (what != null && what.equals(Tags.BROWSE_FILE_TO_CREATE_PROJECT)) 
        {
            /*String path = createFileChooser(ManagerLanguage.getInstance().getLocalizedTexte("ProjectLocation"),mainstage);
             FieldPath.setText(path);*/
        } else if (what != null && what.equals(Tags.CLOSE_DIALOG)) {
            if (stage != null) {
                stage.close();
            }
        } else if (what != null && what.equals(Tags.CREATE_PROJECT)) {
            if (((TextField) this.FieldName).getText().equals("")) {
                this.FieldName.setStyle("-fx-text-box-border: red ;\n"
                        + "  -fx-focus-color: red ;");
            } else {
                String text = ((TextField) this.FieldName).getText();
                Project p = ProjectDAO.getInstance().insertProject(text, MyDate.now());
                OpenProject(p, stage, stageParent);
                
            }
        } else if (what != null && what.equals(Tags.OPEN_PROJECT)) {
            if (((ComboBox) this.FieldName).getSelectionModel().getSelectedItem() == null) {
                this.FieldName.setStyle("-fx-text-box-border: red ;\n"
                        + "  -fx-focus-color: red ;");
            } else {
                Project p = (Project) ((ComboBox) this.FieldName).getSelectionModel().getSelectedItem();
                OpenProject(p, stage, stageParent);
                
            }
        } else if (what != null && what.equals(Tags.CONFIRMATION_NO_SAVE)) {
            Quit();
        } else if (what != null && what.equals(Tags.CONFIRMATION_YES_SAVE)) {
            SaveProject(ProjectDAO.getInstance().getCurrentProject());
            Quit();
        }
        else if (what != null && what.equals(Tags.CONFIRMATION_NO_SAVE_NOT_QUIT)) 
        {
            createDialogCreateOrOpenProjectAndQuitPrecedentProject();
            stage.close();
        } else if (what != null && what.equals(Tags.CONFIRMATION_YES_SAVE_NOT_QUIT)) 
        {
            SaveProject(ProjectDAO.getInstance().getCurrentProject());
            stage.close();
            createDialogCreateOrOpenProjectAndQuitPrecedentProject();
        } else {
            
        }
    }

    private void createDialogCreateOrOpenProjectAndQuitPrecedentProject() {
        Stage stage = new Stage();
        Dialog dialog=null;
        if (this.tags.equals(Tags.NEW)) {
            dialog = new DialogCreateProject(stage, stageParent);
            stage.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("NewProject"));
        }
        else{
            dialog = new DialogOpenProject(stage, stageParent);
            stage.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        }
        stage.setScene(new Scene(dialog));
        stage.setResizable(false);
        stage.getIcons().add(new Image(SPLASH_IMAGE));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stageParent);
        stage.show();
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
