/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.DialogConfirmationDeleteController;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.Style;

/**
 *
 * @author Jérémy
 */
public final class DialogConfirmationDelete extends Dialog 
{
    private final int type;

    public DialogConfirmationDelete(Stage dialog, Stage stageParent,int type) {
        super(dialog, stageParent,1);
        this.type = type;
        createDialog();
    }


    @Override
    public void createDialog()
    {
        Style.getStyle("dialog.css", this);
        HBox header ;
        if(type == 0){
            header =  createHeaderDialog(LoaderImage.getImage("Error-52.png"), getManagerLang().getLocalizedTexte("TextDialogConfirmationDelete"));
        }
        else{
            header =  createHeaderDialog(LoaderImage.getImage("Error-52.png"), getManagerLang().getLocalizedTexte("TextDialogConfirmationDeleteDatabase"));
 
        }
        HBox box1 = new HBox();
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        box1.setAlignment(Pos.CENTER);

        Button bYes = new Button(getManagerLang().getLocalizedTexte("YES"));
        Button bClose = new Button(getManagerLang().getLocalizedTexte("NO"));
        if(type == 0){
            bYes.setOnAction(new DialogConfirmationDeleteController(Tags.CONFIRMATION_YES_DELETE, getStage(), getStageParent()));
        }else{
             bYes.setOnAction(new DialogConfirmationDeleteController(Tags.CONFIRMATION_YES_INIT_DATABASE, getStage(), getStageParent()));
        }
        bClose.setOnAction(new DialogConfirmationDeleteController(Tags.CLOSE_DIALOG, getStage(),null));

        box1.getChildren().addAll(bYes, bClose);
        this.getChildren().addAll(header, box1);
    }
    
}
