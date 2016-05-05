/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.cell;

import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.TaskController;

/**
 *
 * @author Jérémy
 */
public class IntegerEditingCellPredecessor extends TableCell<Predecessor, Integer> {

    private final TextField textField = new TextField();
    private final Pattern intPattern = Pattern.compile("-?\\d+");
    private final String tags;
    private final int mode;

    public IntegerEditingCellPredecessor(String tags,int mode) {
        this.tags = tags;
        this.mode = mode;
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                processEdit();
            }
        });
        textField.setOnAction(event -> processEdit());
        setAlignment(Pos.CENTER);
    }

    private void processEdit() {
        String text = textField.getText();
        if (intPattern.matcher(text).matches()) {
                commitEdit(Integer.parseInt(text));

        } else {
            cancelEdit();
        }
    }

    @Override
    public void updateItem(Integer value, boolean empty) {
        
        super.updateItem(value, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
            if(value<0){
                value =0;
            }
            textField.setText(value.toString());
            setGraphic(textField);
        } else {
            setText(value.toString());
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        Number value = getItem();
        if (value != null) {
            textField.setText(value.toString());
            setGraphic(textField);
            setText(null);
            
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }

    // This seems necessary to persist the edit on loss of focus; not sure why:
    @Override
    public void commitEdit(Integer value) {
        super.commitEdit(value);
        if(tags.equals(Tags.GAP)){
            ((Predecessor) this.getTableRow().getItem()).setGap(value);
        }
    }
}
