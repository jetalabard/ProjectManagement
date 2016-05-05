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
import projectmanagement.application.model.DAO;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.TaskController;

/**
 *
 * @author Jérémy
 */
public final class StringCellPredecessor extends TableCell<Predecessor, String> {

    private final TextField textField;
    private final String column;
    private final int mode;
    

    public StringCellPredecessor(String column,int mode) {
        textField = new TextField();
        this.mode = mode;
        this.column = column;
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEdit(textField.getText());
            }
        });
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        textField.setOnAction(event -> commitEdit(textField.getText()));
        setAlignment(Pos.CENTER);
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

        switch (this.column) {
            case Tags.TYPE:
                ((Predecessor) this.getTableRow().getItem()).setType(value);
                break;
            case Tags.CONSTRAINT:
                ((Predecessor) this.getTableRow().getItem()).setConstraint(value);
                break;
            default:
                break;
        }

    }
}
