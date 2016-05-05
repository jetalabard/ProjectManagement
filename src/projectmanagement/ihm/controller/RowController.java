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
import projectmanagement.application.business.Task;
import projectmanagement.ihm.view.MyTableView;
import static projectmanagement.ihm.view.MyTableView.SERIALIZED_MIME_TYPE;

/**
 *
 * @author Jérémy
 */
public class RowController implements Callback<TableView<Task>, TableRow<Task>> 
{
    private final MyTableView table;
    private final Stage stage;

    public RowController(MyTableView table, Stage mainstages) {
        this.table = table;
        this.stage = mainstages;
    }

    @Override
    public TableRow<Task> call(TableView<Task> param) {
        TableRow<Task> row = new TableRow<>();
        row = doubleClickOnLign(row);
        row = dragDetected(row);
        row = dragOver(row);
        row = dragDropped(row);
        return row;
    }

    private TableRow<Task> dragDropped(TableRow<Task> row) {
        row.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                Task draggedtask = table.getItems().remove(draggedIndex);
                int dropIndex;
                if (row.isEmpty()) {
                    dropIndex = table.getItems().size();
                } else {
                    dropIndex = row.getIndex();
                }
                table.getItems().add(dropIndex, draggedtask);
                new TaskController(table).updateListTaskAfterDragAndDrop(draggedtask, dropIndex);
                event.setDropCompleted(true);
                table.getSelectionModel().select(dropIndex);
                event.consume();
            }
        });
        return row;
    }

    private TableRow<Task> dragOver(TableRow<Task> row) {
        row.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    event.consume();
                }
            }
        });
        return row;
    }

    private TableRow<Task> dragDetected(TableRow<Task> row) {
        row.setOnDragDetected(event -> {
            if (!row.isEmpty()) {
                Integer index = row.getIndex();
                Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(row.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();
                cc.put(SERIALIZED_MIME_TYPE, index);
                db.setContent(cc);
                event.consume();
            }
        });
        return row;
    }

    private TableRow<Task> doubleClickOnLign(TableRow<Task> row) {

        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) 
            {
                Task rowData = table.getSelectionModel().getSelectedItem();
                if (rowData != null) {
                    new UpdateDialogController()
                            .CreateDialogUpdateTask(rowData, stage, row.getIndex(), table);
                }
            }
        });
        return row;
    }

}
