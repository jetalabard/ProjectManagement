/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import java.util.List;
import projectmanagement.ihm.view.dialog.DialogUpdateTask;
import projectmanagement.application.model.PredecessorConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.dataloader.TaskDAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.cell.IntegerEditingCellPredecessor;
import projectmanagement.ihm.view.cell.StringCellPredecessor;

/**
 *
 * @author Jérémy
 */
public class MyTableViewPredecessor extends TableView<Predecessor> {

    private Task task = null;

    private Predecessor copyPredecessor;
    private final DialogUpdateTask dialogParent;

    public MyTableViewPredecessor(Task task, DialogUpdateTask dialogParent) {
        this.task = task;
        this.dialogParent = dialogParent;
        createTableView();
    }

    private void createTableView() {

        setItems(FXCollections.observableArrayList(this.dialogParent.getListePredecessor()));
        setEditable(true);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn col2 = createColumnType();
        TableColumn col = createColumnTaskParent();
        TableColumn col3 = createColumnGap();
        TableColumn col4 = createColumnConstraint();

        getColumns().addAll(col, col2, col3, col4);

        setContextMenu(new ContextMenu(createContextMenuAddTask(), createContextMenuDelete(),
                createContextMenuCopy()));
        setRowFactory(tv -> {
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
                    if (row.getIndex() != ((Integer) db.getContent(MyTableView.SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(MyTableView.SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(MyTableView.SERIALIZED_MIME_TYPE);
                    Predecessor draggedtask = getItems().remove(draggedIndex);
                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    getItems().add(dropIndex, draggedtask);
                    this.dialogParent.getListePredecessor().remove(draggedtask);
                    this.dialogParent.getListePredecessor().add(dropIndex, draggedtask);
                    event.setDropCompleted(true);
                    getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row;
        });
    }

    private MenuItem createContextMenuDelete() {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Delete"));
        mnuDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!getItems().isEmpty()) {
                    Predecessor item = getItems().get(getSelectionModel().getSelectedIndex());
                    if (item != null) {
                        if (item.getId() != null) {
                            //en base sinon si == null pas encore eu le insert
                            DAO.getInstance().deletePredecessor(item.getId());
                        }

                        dialogParent.getListePredecessor().remove(item);
                        getItems().remove(item);
                    }
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuAddTask() {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("AddPredecessor"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Predecessor pred = new Predecessor("", 0, "", task.getId(), 0);
                dialogParent.getListePredecessor().add(pred);
                getItems().add(pred);
            }
        });
        return mnuAdd;
    }

    private MenuItem createContextMenuCopy() {

        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("CopyTask"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (!getItems().isEmpty()) {
                    Predecessor item = getItems().get(getSelectionModel().getSelectedIndex());
                    if (item != null) {
                        copyPredecessor = new Predecessor(item.getId(), item.getType(), item.getGap(), item.getConstraint(), item.getIdTask(), item.getIdTaskParent());
                        if (getContextMenu().getItems().size() == 4) {
                            getContextMenu().getItems().add(createContextMenuPaste());
                        }
                    }
                }
            }
        });
        return mnuAdd;
    }

    public MenuItem createContextMenuPaste() {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("PasteTask"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                getItems().add(copyPredecessor);
                dialogParent.getListePredecessor().add(copyPredecessor);
                getContextMenu().getItems().remove(getContextMenu().getItems().size() - 1);
                copyPredecessor = null;
            }
        });
        return mnuAdd;
    }

    private TableColumn createColumnType() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Type"));
        col.setCellValueFactory(new PropertyValueFactory<>("type"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCellPredecessor(Tags.TYPE, 1);
            }
        });
        return col;
    }

    private TableColumn createColumnTaskParent() {
        TableColumn<Predecessor,Integer> col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Predecessor"));
        col.setCellValueFactory(new PropertyValueFactory<>("idTaskParent"));
        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Predecessor, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Predecessor, Integer> event) {
                Predecessor pred = getSelectionModel().getSelectedItem();
                pred.setIdTaskParent(event.getNewValue());
            }
        });
        col.setCellFactory(new Callback<TableColumn<Predecessor, Integer>, TableCell<Predecessor, Integer>>() 
        {
            @Override
            public TableCell<Predecessor, Integer> call(TableColumn<Predecessor, Integer> roomPropertyBooleanTableColumn) {
                List<Integer> listId = DAO.getInstance().getAllIdTask(task.getIdProject());
                listId.remove(task.getId());
                return new ComboBoxTableCell<Predecessor, Integer>(new PredecessorConverter(), FXCollections.observableArrayList(listId) );
            }
        });
        return col;
    }

    private TableColumn createColumnGap() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Gap"));
        col.setCellValueFactory(new PropertyValueFactory<>("gap"));
        col.setCellFactory((Object col1) -> new IntegerEditingCellPredecessor(Tags.GAP, 1));
        return col;
    }

    private TableColumn createColumnConstraint() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Constraint"));
        col.setCellValueFactory(new PropertyValueFactory<>("constraint"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCellPredecessor(Tags.CONSTRAINT, 1);
            }
        });
        return col;
    }
}
