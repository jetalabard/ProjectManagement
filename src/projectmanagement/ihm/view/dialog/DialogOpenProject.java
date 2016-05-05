/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.DialogOpenProjectController;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.Style;

/**
 *
 * @author Jérémy
 */
public class DialogOpenProject extends Dialog{
    private ComboBox<Project> comboBox;

    public DialogOpenProject(Stage dialog, Stage stageParent) {
        super(dialog, stageParent,0);
    }

    @Override
    public void createDialog() {
        Style.getStyle("dialog.css", this);
        HBox header = createHeaderDialog(LoaderImage.getImage("Folder Filled-50.png"), getManagerLang().getLocalizedTexte("TextDialogOpenProject"));
        comboBox = new ComboBox<>();
        HBox box1 = createLignDialogComboBox(getManagerLang().getLocalizedTexte("ProjectName"), comboBox);
        HBox box2 = createLignDialogButtonValidation(
                getManagerLang().getLocalizedTexte("Open"),
                getManagerLang().getLocalizedTexte("Close"), 
                new DialogOpenProjectController(Tags.OPEN_PROJECT, getStage(), comboBox, getStageParent()),
                new DialogOpenProjectController(Tags.CLOSE_DIALOG, getStage(), null,null));
        this.getChildren().addAll(header,box1,box2);
    }

   
    
}
