/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;

/**
 *
 * @author Jérémy
 */
public class IntegerEditingCell extends TableCell<Task, Number> {

    private final TextField textField = new TextField();
    private final Pattern intPattern = Pattern.compile("-?\\d+");

    public IntegerEditingCell() {
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
    public void updateItem(Number value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
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
    public void commitEdit(Number value) {
        super.commitEdit(value);
        ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
        for(Task t: ProjectDAO.getInstance().getCurrentProject().getTasks()){
                if(t.equals(this.getTableRow().getItem())){
                    t.setPriority(value.intValue());
                }
            }
        ((Task) this.getTableRow().getItem()).setPriority(value.intValue());
    }
}
