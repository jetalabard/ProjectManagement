/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.application.model.ManagerLanguage;

/**
 *
 * @author Jérémy
 */
public class DialogUpdateTask extends Dialog
{
    private Task task;

    public DialogUpdateTask(Stage dialog, Stage stageParent) {
        super(dialog, stageParent,0);
    }

    @Override
    public void createDialog() {
            Style.getStyle("dialog.css", this);
        HBox header = createHeaderDialog(LoaderImage.getImage("Calendar-48.png"), getManagerLang().getLocalizedTexte("TextDialogUpdateTask"));
        TabPane tab = new TabPane();
        Tab general = new Tab(ManagerLanguage.getInstance().getLocalizedTexte("GeneralInformation"));
        Tab ressources = new Tab(ManagerLanguage.getInstance().getLocalizedTexte("RessourcesInformation"));
        Tab predecessors = new Tab(ManagerLanguage.getInstance().getLocalizedTexte("PredecessorsInformation"));
        tab.getTabs().add(general);
        tab.getTabs().add(ressources);
        tab.getTabs().add(predecessors);
        this.getChildren().addAll(header,tab);
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
}
