/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.ClickController;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class DialogConfirmationSave extends Dialog {
    
    //type 0 -> quitter
    //type1 -> change projet
    private int type;
    private final String tags;

    public DialogConfirmationSave(Stage dialog, Stage stageParent,int type,String tags) {
        super(dialog, stageParent,1);
        this.type = type;
        this.tags = tags;
        createDialog();
    }


    @Override
    public void createDialog()
    {
        Style.getStyle("dialog.css", this);
        HBox header = createHeaderDialog(LoaderImage.getImage("Error-52.png"), getManagerLang().getLocalizedTexte("TextDialogConfirmationSave"));
        HBox box1 = new HBox();
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        box1.setAlignment(Pos.CENTER);

        Button bYes = new Button(getManagerLang().getLocalizedTexte("YES"));
        Button bNo = new Button(getManagerLang().getLocalizedTexte("NO"));
        Button bClose = new Button(getManagerLang().getLocalizedTexte("Cancel"));
        if (type == 0) 
        {
            bYes.setOnAction(new ClickController(Tags.CONFIRMATION_YES_SAVE, getStage(), getStageParent(), null));
            bNo.setOnAction(new ClickController(Tags.CONFIRMATION_NO_SAVE, getStage(), getStageParent(), null));
        } else {
            ClickController click = new ClickController(Tags.CONFIRMATION_YES_SAVE_NOT_QUIT, getStage(), getStageParent(), null);
            click.setTags(tags);
            ClickController click2 = new ClickController(Tags.CONFIRMATION_NO_SAVE_NOT_QUIT, getStage(), getStageParent(), null);
            click2.setTags(tags);
            bYes.setOnAction(click);
            bNo.setOnAction(click2);
        }
         bClose.setOnAction(new ClickController(Tags.CLOSE_DIALOG, getStage()));

        box1.getChildren().addAll(bYes, bNo, bClose);
        this.getChildren().addAll(header, box1);
    }
    
}
