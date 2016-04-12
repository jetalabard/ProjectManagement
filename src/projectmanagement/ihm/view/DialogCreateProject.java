/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class DialogCreateProject extends Dialog {

    private TextField name;

    public DialogCreateProject(Stage dialog, Stage stageParent) {
        super(dialog, stageParent,0);
    }


    
    @Override
    public void createDialog() {
        Style.getStyle("dialog.css", this);
        HBox header = createHeaderDialog(LoaderImage.getImage("Folder Filled-50.png"), getManagerLang().getLocalizedTexte("TextDialogCreateProject"));
        name = new TextField();
        HBox box1 = createLignDialog(getManagerLang().getLocalizedTexte("ProjectName"), name);
        HBox box2 = createLignDialogButtonValidation(getManagerLang().getLocalizedTexte("Create"),
                getManagerLang().getLocalizedTexte("Close"), getStage(),getStageParent(), name,Tags.CREATE_PROJECT);
        this.getChildren().addAll(header, box1, box2);
    }

}
