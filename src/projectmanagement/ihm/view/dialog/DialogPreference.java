/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.dialog;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.DialogPreferenceControler;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.Style;

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
        ColorPicker colorPicker1 = new ColorPicker();
        colorPicker1.setValue(DAO.getInstance().getBACKGROUND_GANTT());
        HBox BACKGROUND_GANTT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("BACKGROUND_GANTT"),colorPicker1);
        ColorPicker colorPicker2 = new ColorPicker();
        colorPicker2.setValue(DAO.getInstance().getOBJECT_GANTT());
        HBox OBJECT_GANTT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("OBJECT_GANTT"),colorPicker2);
        ColorPicker colorPicker3 = new ColorPicker();
        colorPicker3.setValue(DAO.getInstance().getTEXT_GANTT());
        HBox TEXT_GANTT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("TEXT_GANTT"),colorPicker3);
        ColorPicker colorPicker4 = new ColorPicker();
        colorPicker4.setValue(DAO.getInstance().getBACKGROUND_PERT());
        HBox BACKGROUND_PERT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("BACKGROUND_PERT"),colorPicker4);
        ColorPicker colorPicker5 = new ColorPicker();
        colorPicker5.setValue(DAO.getInstance().getOBJECT_PERT());
        HBox OBJECT_PERT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("OBJECT_PERT"),colorPicker5);
        ColorPicker colorPicker6 = new ColorPicker();
        colorPicker6.setValue(DAO.getInstance().getTEXT_PERT());
        HBox TEXT_PERT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("TEXT_PERT"),colorPicker6);
        ColorPicker colorPicker7 = new ColorPicker();
        colorPicker7.setValue(DAO.getInstance().getTEXT_CRITICAL_PERT());
        HBox TEXT_CRITICAL_PERT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("TEXT_CRITICAL_PERT"),colorPicker7);
        ColorPicker colorPicker8 = new ColorPicker();
        colorPicker8.setValue(DAO.getInstance().getOBJECT_CRITICAL_PERT());
        HBox OBJECT_CRITICAL_PERT = 
                createHeaderDialogChooseColor(getManagerLang().getLocalizedTexte("OBJECT_CRITICAL_PERT"),colorPicker8);
        
         HBox box2 = createLignDialogButtonValidation(
                getManagerLang().getLocalizedTexte("Create"),
                getManagerLang().getLocalizedTexte("Close"), 
                new DialogPreferenceControler(Tags.APPLY_PREFERENCE,colorPicker1,colorPicker2,colorPicker3,colorPicker4,
                        colorPicker5,colorPicker6,colorPicker7,colorPicker8,getStage(), getStageParent()),
                new DialogPreferenceControler(Tags.CLOSE_DIALOG, null, null,null, null,null, null,null, null, getStage(), getStageParent()));
         
        this.getChildren().addAll(header,BACKGROUND_GANTT,
                OBJECT_GANTT,TEXT_GANTT,BACKGROUND_PERT,OBJECT_PERT,TEXT_PERT,TEXT_CRITICAL_PERT,
                OBJECT_CRITICAL_PERT,box2);
    }

   
}
