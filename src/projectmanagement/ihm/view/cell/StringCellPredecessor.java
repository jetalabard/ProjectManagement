/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.cell;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class StringCellPredecessor extends TableCell<Predecessor, String> {

    private TextField textField;
    private String column;
    private int mode;

    public StringCellPredecessor(String column,int mode) {
        textField = new TextField();
        this.mode = mode;
        this.column = column;
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                processEdit(0);
            }
        });
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        textField.setOnAction(event -> processEdit(1));
        setAlignment(Pos.CENTER);
    }

    private void processEdit(int mode) {
        if (mode == 0) {
            commitEdit(textField.getText());
        } else {
            commitEdit(textField.getText());
            if (this.mode == 0) {
                ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
                DAO.getInstance().getCurrentProject().setState(new StateNotSave());
            }
        }

    }

    @Override
    public void updateItem(String value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
            textField.setText(value);
            setGraphic(textField);
        } else {
            setText(value);
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        String value = getItem();
        if (value != null) {
            textField.setText(value);
            setGraphic(textField);
            setText(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);

    }

    // This seems necessary to persist the edit on loss of focus; not sure why:
    @Override
    public void commitEdit(String value) {
        super.commitEdit(value);

        if (this.column.equals(Tags.TYPE)) {
            ((Predecessor) this.getTableRow().getItem()).setType(value);
        }
        else if (this.column.equals(Tags.CONSTRAINT)) {
            ((Predecessor) this.getTableRow().getItem()).setConstraint(value);
        }
        else {
        }

    }
}
