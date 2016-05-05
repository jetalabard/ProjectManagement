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
import javafx.stage.Stage;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.TaskController;
import projectmanagement.ihm.view.MyPopup;
import projectmanagement.ihm.view.MyTableView;

/**
 *
 * @author Jérémy
 */
public class IntegerEditingCell extends TableCell<Task, Integer> {

    private final TextField textField = new TextField();
    private final Pattern intPattern = Pattern.compile("-?\\d+");
    private final String tags;
    private final int mode;
    private final Stage stage;
    private final MyTableView table;
    
    public IntegerEditingCell(String tags,int mode,Stage mainstage,MyTableView table) {
        this.tags = tags;
        this.mode = mode;
        this.table = table;
        this.stage = mainstage;
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                processEdit(0);
            }
        });
        textField.setOnAction(event -> processEdit(1));
        setAlignment(Pos.CENTER);
    }

    private void processEdit(int mode) {
        String text = textField.getText();
        if (intPattern.matcher(text).matches()) {
            if (mode == 0) {
                commitEdit(Integer.parseInt(text));
            } else {
                commitEdit(Integer.parseInt(text));
                if (this.mode == 0) {
                    new TaskController(table).updateListTask(DAO.getInstance().getCurrentProject().getTasks());
                }
            }

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
            textField.setText(value.toString());
            setGraphic(textField);
        } else {
             if(value<0 || value >10){
                value =0;
                MyPopup.showPopupMessage(ManagerLanguage.getInstance().getLocalizedTexte("TextPriority"), stage);
            }
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
        Number value = getItem();
        if (value != null) {
            setText(String.valueOf(value));
            setGraphic(null);
        }
        
    }

    // This seems necessary to persist the edit on loss of focus; not sure why:
    @Override
    public void commitEdit(Integer value) {
        super.commitEdit(value);
        if(tags.equals(Tags.PRIORITY)){
            for (Task t : DAO.getInstance().getCurrentProject().getTasks()) {
                if (t.equals(this.getTableRow().getItem())) {
                    if(value>0 && value <= 10){
                        t.setPriority(value);
                        ((Task) this.getTableRow().getItem()).setPriority(value);
                    }
                }
            }
        }
    }
}
