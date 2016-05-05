/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.UpdateDialogController;
import projectmanagement.ihm.view.MyTableView;
import projectmanagement.ihm.view.Style;

/**
 *
 * @author Jérémy
 */
public class DialogUpdateTask extends Dialog {

    private Task initialtask;
    private Task task;
    private final int indexTask;
    private final MyTableView table;
    private final List<RessourcesTable> listeRessource;
    private final List<Predecessor> listePredecessor;
   

    public DialogUpdateTask(Stage dialog, Stage stageParent,Task task,MyTableView table,int index) {
        super(dialog, stageParent, 1);
        this.table = table;
        this.indexTask = index;
        this.setTask(task);
        this.listeRessource = new ArrayList<>(RessourcesTable.transformRessourceToResssourceTable(this.task.getRessources()));
        this.listePredecessor = new ArrayList<>(this.task.getPredecessor());
    }

    @Override
    public void createDialog() {
        Style.getStyle("dialog.css", this);
       
        HBox header = createHeaderDialog(LoaderImage.getImage("Calendar-48.png"), getManagerLang().getLocalizedTexte("TextDialogUpdateTask"));
        TabPane tab = new TabPane();

        Tab general = new TabGeneralUpdateDialog(task, this);
        Tab ressources = createTabRessource();
        Tab predecessors = createTabPredecessor();
        
        tab.getTabs().add(general);
        tab.getTabs().add(ressources);
        tab.getTabs().add(predecessors);
        
        HBox hbox = addControllerToButton();
        this.getChildren().addAll(header, tab, hbox);
    }

    private HBox addControllerToButton() {
        UpdateDialogController apply = new UpdateDialogController(
                Tags.APPLY_TASK, 
                getStage(),
                this);
        UpdateDialogController previous = new UpdateDialogController(
                Tags.PREVIOUS_TASK,
                getStage(),
                this);
        HBox hbox = createLignDialogButtonValidation(
                getManagerLang().getLocalizedTexte("Aply"),
                getManagerLang().getLocalizedTexte("Cancel"),
                apply,previous);
        return hbox;
    }
    
     private Tab createTabRessource() {
        Tab ressources = new Tab(getManagerLang().getLocalizedTexte("RessourcesInformation"));
        ressources.setContent(new MyTableViewRessource(task, this));
        return ressources;
    }

    private Tab createTabPredecessor() {
        Tab predecessors = new Tab(getManagerLang().getLocalizedTexte("PredecessorsInformation"));
        predecessors.setContent( new MyTableViewPredecessor(task, this));
        return predecessors;
    }

    private void setTask(Task task) {
        this.task = new Task(task.getId(), task.getName(), task.getDatebegin(), task.getDateend(), task.getPriority(), task.getNote(), task.getIdProject(), task.getRessources(), task.getPredecessor());
        this.initialtask = new Task(task.getId(), task.getName(), task.getDatebegin(), task.getDateend(), task.getPriority(), task.getNote(), task.getIdProject(), task.getRessources(), task.getPredecessor());
    }

    public List<RessourcesTable> getListeRessource() {
        return listeRessource;
    }

    public List<Predecessor> getListePredecessor() {
        return listePredecessor;
    }

    public Task getInitialtask() {
        return initialtask;
    }

    public Task getTask() {
        return task;
    }

    public int getIndexTask() {
        return indexTask;
    }

    public MyTableView getTable() {
        return table;
    }
    
    
    
    
}
