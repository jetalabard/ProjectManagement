/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import projectmanagement.ihm.view.cell.IntegerEditingCell;
import projectmanagement.ihm.view.cell.StringCell;
import projectmanagement.ihm.view.cell.DatePickerCell;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.DAOTask;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.ClickController;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class MyTableView extends TableView<Task> {

    private final Stage mainStage;
    
    public static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    
    private Task copyTask =null;
    
    private MainWindow main ;

    public MyTableView(Stage mainstage,MainWindow main) {
        this.mainStage = mainstage;
        this.main = main;
        createTableView();
    }

    private void createTableView() {
        setItems(FXCollections.observableArrayList(ProjectDAO.getInstance().getCurrentProject().getTasks()));
        setEditable(true);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DatePickerCell.UninitializedInLayoutChildrenFunction(true);
        TableColumn col = createColumnName();
        TableColumn col2 = createColumnDateBegin();
        TableColumn col3 = createColumnDateEnd();
        TableColumn col4 = createColumnPriority();
        TableColumn col5 = createColumnNote();

        getColumns().addAll(col, col2, col3, col4, col5);

        setContextMenu(new ContextMenu(createContextMenuAddTask(), createContextMenuUpdate(), createContextMenuDelete(),
                createContextMenuCopy()));
        setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Task rowData = ProjectDAO.getInstance().getCurrentProject().getTasks().get(row.getIndex());
                    if (rowData != null) {
                        new ClickController(Tags.UPDATE_TASK, mainStage).CreateDialogUpdateTask(rowData, mainStage,row.getIndex(),this);
                    }
                }
            });
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

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Task draggedtask = getItems().remove(draggedIndex);

                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    getItems().add(dropIndex, draggedtask);
                    ProjectDAO.getInstance().getCurrentProject().getTasks().remove(draggedtask);
                    ProjectDAO.getInstance().getCurrentProject().getTasks().add(dropIndex, draggedtask);
                    ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
                    ManageUndoRedo.getInstance().add(ProjectDAO.getInstance().getCurrentProject().getTasks());
                    event.setDropCompleted(true);
                    getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row;
        });
    }

    private TableColumn createColumnNote() {
        TableColumn col5 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Note"));
        col5.setCellValueFactory(new PropertyValueFactory<>("note"));
        col5.setMinWidth(300);
        col5.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCell(Tags.NOTE,0);
            }
        });

        return col5;
    }

    private TableColumn createColumnPriority() {
        TableColumn col4 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Priority"));
        col4.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col4.setCellFactory(col -> new IntegerEditingCell(Tags.PRIORITY,0));
        col4.setMinWidth(50);

        return col4;
    }

    private TableColumn createColumnDateEnd() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateEnd"));
        col.setCellValueFactory(new PropertyValueFactory<Task, MyDate>("dateend"));
        col.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DatePickerCell datePick = new DatePickerCell(getItems(), Tags.DATE_END);
                return datePick;
            }
        });
        col.setMinWidth(150);
        return col;
    }

    private TableColumn createColumnDateBegin() {
        TableColumn col2 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateBegin"));
        col2.setCellValueFactory(new PropertyValueFactory<Task, MyDate>("datebegin"));
        col2.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new DatePickerCell(getItems(), Tags.DATE_BEGIN);
            }
        });
        col2.setMinWidth(150);
        return col2;
    }

    private TableColumn createColumnName() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Name"));
        col.setMinWidth(300);
        col.setCellValueFactory(new PropertyValueFactory<>("name"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCell(Tags.NAME,0);
            }
        });

        return col;
    }

    private MenuItem createContextMenuDelete() {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Delete"));
        mnuDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!getItems().isEmpty()) {
                    Task item = getItems().get(getSelectionModel().getSelectedIndex());
                    if (item != null) {
                        if (item.getId() != null) {
                            //en base sinon si == null pas encore eu le insert
                            DAOTask.getInstance().deleteTask(item.getId());
                        }

                        ProjectDAO.getInstance().getCurrentProject().getTasks().remove(item);
                        getItems().remove(item);
                        ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
                        ManageUndoRedo.getInstance().add(ProjectDAO.getInstance().getCurrentProject().getTasks());
                    }
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuUpdate() {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Update"));
        mnuDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!getItems().isEmpty()) {
                    Task item = ProjectDAO.getInstance().getCurrentProject().getTasks().get(getSelectionModel().getSelectedIndex());
                    if (item != null) {
                        new ClickController(Tags.UPDATE_TASK, mainStage).CreateDialogUpdateTask(item, mainStage, getSelectionModel().getSelectedIndex(), MyTableView.this);
                    }
                }
            }
        });
        return mnuDel;
    }

    
    private MenuItem createContextMenuAddTask() {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("AddTask"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                new ClickController(Tags.ADD_TASK, mainStage).addTask(MyTableView.this);
            }
        });
        return mnuAdd;
    }
    private MenuItem createContextMenuCopy() {
        
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("CopyTask"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Task item = getItems().get(getSelectionModel().getSelectedIndex());
                if (item != null) {
                    copyTask = new Task(item.getName(), item.getDatebegin(), item.getDateend(), item.getPriority(), item.getNote(), item.getIdProject());
                    if(getContextMenu().getItems().size() == 4){
                        getContextMenu().getItems().add(createContextMenuPaste());
                    }
                }
            }
        });
        return mnuAdd;
    }
    
    public MenuItem createContextMenuPaste() 
    {
            MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("PasteTask"));
            mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    getItems().add(copyTask);
                    ProjectDAO.getInstance().getCurrentProject().getTasks().add(copyTask);
                    ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
                    ManageUndoRedo.getInstance().add(ProjectDAO.getInstance().getCurrentProject().getTasks());
                    getContextMenu().getItems().remove(getContextMenu().getItems().size()-1);
                    copyTask =null;
                }
            });
            return mnuAdd;
    }
    public void reload(){
        getItems().clear();
        main.reload();
        /*DatePickerCell.UninitializedInLayoutChildrenFunction(false);
        setItems(null); 
        layout(); 
        setItems(FXCollections.observableList(ProjectDAO.getInstance().getCurrentProject().getTasks())); 
        DatePickerCell.UninitializedInLayoutChildrenFunction(true);*/
    }

}
