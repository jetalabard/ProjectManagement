/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.MyDate;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.view.MyTableView;

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
    private Task task;
    private MyTableView table;
    private List<RessourcesTable> listRessource;
    private List<Predecessor> listPredecessor;
    private int indexTask;

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
        if (what != null && what.equals(Tags.CLOSE_DIALOG)) {
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
        } else if (what != null && what.equals(Tags.CONFIRMATION_NO_SAVE_NOT_QUIT)) {
            createDialogCreateOrOpenProjectAndQuitPrecedentProject(tags, stageParent);
            stage.close();
        } else if (what != null && what.equals(Tags.CONFIRMATION_YES_SAVE_NOT_QUIT)) {
            SaveProject(ProjectDAO.getInstance().getCurrentProject());
            stage.close();
            createDialogCreateOrOpenProjectAndQuitPrecedentProject(tags, stageParent);
        } else if (what != null && what.equals(Tags.PREVIOUS_TASK))
        {
            //retour à la tâche que l'on avait précédemment 
            int index = 0;
            for (int i = 0; i < ProjectDAO.getInstance().getCurrentProject().getTasks().size(); i++) {
                if (ProjectDAO.getInstance().getCurrentProject().getTasks().get(i).equals(task)) {
                    index = i;
                }
            }
            table.getItems().set(index, task);
            ProjectDAO.getInstance().getCurrentProject().getTasks().set(index, task);
            table.reload();
            stage.close();
        } else if (what != null && what.equals(Tags.APPLY_TASK)) {
            //actualisation du tableau avec nouvelle tâche saisie
            task.setPredecessor(listPredecessor);
            task.setRessources(RessourcesTable.transformRessourceTableToResssource(listRessource));
            table.getItems().set(indexTask, task);
            ProjectDAO.getInstance().getCurrentProject().getTasks().set(indexTask, task);
            
            ManageUndoRedo.getInstance().add(ProjectDAO.getInstance().getCurrentProject().getTasks());
            ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
            table.reload();
            stage.close();
        } else {

        }
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setTask(Task task,int indexTask,  List<RessourcesTable> ressource, List<Predecessor> listPredecessor,MyTableView table) {
        this.task = task;
        this.table = table;
        this.listRessource = ressource;
        this.listPredecessor = listPredecessor;
        this.indexTask = indexTask;
    }
}
