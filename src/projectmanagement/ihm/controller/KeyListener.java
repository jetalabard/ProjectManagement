/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.Save;
import projectmanagement.ihm.view.MyTableView;

/**
 *
 * @author Jérémy
 */
public class KeyListener extends Controller implements EventHandler<KeyEvent> {
    
    KeyCombination ctrlS = KeyCodeCombination.keyCombination("Ctrl+s");
    KeyCombination ctrlY = KeyCodeCombination.keyCombination("Ctrl+y");
    KeyCombination ctrlZ = KeyCodeCombination.keyCombination("Ctrl+z");
    KeyCombination ctrlZoom = KeyCodeCombination.keyCombination("Ctrl+"+KeyCode.SUBTRACT);
    KeyCombination ctrlZoomP = KeyCodeCombination.keyCombination("Ctrl+"+KeyCode.ADD);
    KeyCombination ctrlT = KeyCodeCombination.keyCombination("Ctrl+"+KeyCode.T);
    
    KeyCombination ctrlC = KeyCodeCombination.keyCombination("Ctrl+"+KeyCode.C);
    KeyCombination ctrlV = KeyCodeCombination.keyCombination("Ctrl+"+KeyCode.V);
    
    private final Slider slider;
    private final MyTableView table;
    
    private Task copyTask;

    public KeyListener(Slider slider,MyTableView table) {
        this.slider = slider;
        this.table = table;
    }

    
    
    @Override
    public void handle(KeyEvent event) {
        if (ctrlS.match(event)) {
            DAO.getInstance().setCurrentProject(new Save(DAO.getInstance().getCurrentProject()).execute());
        }
        if (ctrlY.match(event)) {
            ManageUndoRedo.getInstance().redo();
        }
        if (ctrlZ.match(event)) {
            ManageUndoRedo.getInstance().undo();
        }
        if (ctrlZoom.match(event)) {
            slider.adjustValue(slider.getValue()-0.25);
        }
        if (ctrlZoomP.match(event)) {
            slider.adjustValue(slider.getValue()+0.25);
        }
        if (ctrlT.match(event)) {
            addTask(table);
        }
        if (ctrlC.match(event)) {
            Task item = (Task) table.getItems().get(table.getSelectionModel().getSelectedIndex());
                if (item != null) {
                    copyTask = new Task(item.getName(), item.getDatebegin(), item.getDateend(), item.getPriority(), item.getNote(), item.getIdProject());
                    if(table.getContextMenu().getItems().size() == 4){
                        table.getContextMenu().getItems().add(table.createContextMenuPaste());
                    }
                }
        }
        if (ctrlV.match(event) && copyTask != null) {
            table.getItems().add(copyTask);
            DAO.getInstance().getCurrentProject().getTasks().add(copyTask);
            DAO.getInstance().getCurrentProject().setState(new StateNotSave());
            ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
            table.getContextMenu().getItems().remove(table.getContextMenu().getItems().size() - 1);
            copyTask = null;
        }

    }

}
