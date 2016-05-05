/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.cell;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public final class StringCellRessource extends TableCell<RessourcesTable, String> {

    private final TextField textField;
    private final String column;

    public StringCellRessource(String column) {
        textField = new TextField();
        this.column = column;
        
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEdit(textField.getText());
                cancelEdit();
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                commitEdit(textField.getText());
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
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
            setStyle("");
            setEditable(true);
            setDisable(false);
        } else if (isEditing()) {
            setText(null);
            setStyle("");
            setEditable(true);
            setDisable(false);
            textField.setText(value);
            setGraphic(textField);
        } else 
        {
            if (this.getTableRow() != null &&  this.getTableRow().getItem() != null) {
                setText(value);
                int type = ((RessourcesTable) this.getTableRow().getItem()).getType();
                if (type == 0 && column.equals(Tags.REFERENCE)) {
                    disable();
                } else if (type == 1) {
                    if (column.equals(Tags.RESSOURCE_FIRSTNAME) || column.equals(Tags.ROLE)) {
                        disable();
                    }
                }
            }
            
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
        RessourcesTable ress;
        switch (this.column) {
            case Tags.RESSOURCE_FIRSTNAME:
                ress = ((RessourcesTable) this.getTableRow().getItem());
                if(ress.getType() == 0){
                    ress.setFirstname(value);
                }
                break;
            case Tags.RESSOURCE_NAME:
                ((RessourcesTable) this.getTableRow().getItem()).setName(value);
                break;
            case Tags.ROLE:
                ress = ((RessourcesTable) this.getTableRow().getItem());
                if (ress.getType() == 0) {
                    ress.setRole(value);
                }
                break;
            case Tags.REFERENCE:
                ress = ((RessourcesTable) this.getTableRow().getItem());
                if (ress.getType() == 1) {
                    ress.setReference(value);
                }
                break;
            default:
                break;
        }
        

    }

    private void disable() {
       setEditable(false);
       setDisable(true);
            setStyle("-fx-background-color: black;");
    }

}
