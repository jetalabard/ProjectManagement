/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import projectmanagement.application.model.DAO;

/**
 *
 * @author Jérémy
 */
public class DialogPreferenceControler extends Controller implements EventHandler<ActionEvent>{

    private final String what;
    private final ColorPicker BACKGROUND_GANTT ;
    private final ColorPicker OBJECT_GANTT  ;
    private final ColorPicker TEXT_GANTT  ;
    private final ColorPicker BACKGROUND_PERT ;
    private final ColorPicker OBJECT_PERT  ;
    private final ColorPicker TEXT_PERT  ;
    private final ColorPicker TEXT_CRITICAL_PERT  ;
    private final ColorPicker OBJECT_CRITICAL_PERT ;
    private final Stage stage;
    private final Stage stageParent;


    public DialogPreferenceControler(String what, ColorPicker colorPicker1, ColorPicker colorPicker2, ColorPicker colorPicker3, ColorPicker colorPicker4, ColorPicker colorPicker5, ColorPicker colorPicker6, ColorPicker colorPicker7, ColorPicker colorPicker8, Stage stage, Stage stageParent) {
        super();
        this.what = what;
        this.stage = stage;
        this.stageParent = stageParent;
        this.BACKGROUND_GANTT = colorPicker1;
        this.OBJECT_GANTT = colorPicker2;
        this.TEXT_GANTT = colorPicker3;
        this.BACKGROUND_PERT = colorPicker4;
        this.OBJECT_PERT = colorPicker5;
        this.TEXT_PERT = colorPicker6;
        this.TEXT_CRITICAL_PERT = colorPicker7;
        this.OBJECT_CRITICAL_PERT = colorPicker8;
    }

    @Override
    public void handle(ActionEvent event) {
        switch (what) {
            case Tags.CLOSE_DIALOG:
                if (stage != null) {
                    stage.close();
                }
                break;
            case Tags.APPLY_PREFERENCE:
                DAO.getInstance().updatePreference(BACKGROUND_GANTT.getValue(), OBJECT_GANTT.getValue(),
                        TEXT_GANTT.getValue(), BACKGROUND_PERT.getValue(), OBJECT_PERT.getValue(),
                        TEXT_PERT.getValue(), TEXT_CRITICAL_PERT.getValue(), OBJECT_CRITICAL_PERT.getValue());
                stage.close();
                break;
            default:
                break;
        }
    }
}
