/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class DialogPreference extends Dialog {

    public DialogPreference(Stage dialog, Stage stageParent) {
        super(dialog, stageParent,0);
    }

    @Override
    public void createDialog() {
        Style.getStyle("dialog.css", this);
        HBox header = createHeaderDialog(LoaderImage.getImage("Settings-48.png"), getManagerLang().getLocalizedTexte("TextDialogPreference"));
        this.getChildren().addAll(header);
    }
    
}
