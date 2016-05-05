/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagement.application.business.Predecessor;
import projectmanagement.ihm.view.MyTableView;
import projectmanagement.ihm.view.dialog.DialogUpdateTask;
import projectmanagement.ihm.view.dialog.MyTableViewPredecessor;

/**
 *
 * @author Jérémy
 */
public class RowControllerTablePredecessor implements Callback<TableView<Predecessor>, TableRow<Predecessor>> {
    private final MyTableViewPredecessor table;
    private final Stage stage;
    private final DialogUpdateTask dialog;

     public RowControllerTablePredecessor(MyTableViewPredecessor table,DialogUpdateTask dialog) {
        this.table = table;
        this.stage = dialog.getStage();
        this.dialog = dialog;
    }
    
    @Override
    public TableRow<Predecessor> call(TableView<Predecessor> param) {
        TableRow<Predecessor> row = new TableRow<>();
           
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(MyTableView.SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(MyTableView.SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(MyTableView.SERIALIZED_MIME_TYPE))) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(MyTableView.SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(MyTableView.SERIALIZED_MIME_TYPE);
                    Predecessor draggedtask = table.getItems().remove(draggedIndex);
                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = table.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    table.getItems().add(dropIndex, draggedtask);
                    this.dialog.getListePredecessor().remove(draggedtask);
                    this.dialog.getListePredecessor().add(dropIndex, draggedtask);
                    event.setDropCompleted(true);
                    table.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row;
    }
    
}
