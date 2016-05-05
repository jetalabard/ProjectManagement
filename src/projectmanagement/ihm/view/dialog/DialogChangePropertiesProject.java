/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.DialogChangePropertiesController;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.Style;

/**
 *
 * @author Jérémy
 */
public class DialogChangePropertiesProject extends Dialog{

    private TextField name;
    private Label error;

    public DialogChangePropertiesProject(Stage dialog, Stage stageParent) {
        super(dialog, stageParent,0);
    }
    @Override
    public void createDialog() {
        Style.getStyle("dialog.css", this);
        HBox header = createHeaderDialog(LoaderImage.getImage("Folder Filled-50.png"), getManagerLang().getLocalizedTexte("TextDialogChangeProject"));
        name = new TextField();
        error = new Label();
        error.setStyle("-fx-text-fill: red;");
        HBox box1 = createLignDialog(getManagerLang().getLocalizedTexte("ProjectName"), name);
        HBox box2 = createLignDialogButtonValidation(
                getManagerLang().getLocalizedTexte("Create"),
                getManagerLang().getLocalizedTexte("Close"), 
                new DialogChangePropertiesController(Tags.CHANGE_NAME, name, error, getStage(), getStageParent()),
                new DialogChangePropertiesController(Tags.CLOSE_DIALOG, name, error, getStage(), getStageParent()));
        
        this.getChildren().addAll(header, box1,error, box2);
    }
    
}
