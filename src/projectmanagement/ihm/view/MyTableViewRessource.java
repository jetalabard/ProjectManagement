/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import projectmanagement.ihm.view.cell.IntegerEditingCell;
import projectmanagement.ihm.view.cell.StringCellRessource;
import projectmanagement.ihm.view.cell.DatePickerCell;
import projectmanagement.ihm.view.dialog.DialogUpdateTask;
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
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.dataloader.TaskDAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.RessourceConverter;
import projectmanagement.application.model.RessourcesTable;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.view.cell.IntegerEditingCellRessource;

/**
 *
 * @author Jérémy
 */
public class MyTableViewRessource extends TableView<RessourcesTable> {

    private Task task =null;
    
    private RessourcesTable copyRessource;
    private final DialogUpdateTask dialogParent;
    
    public MyTableViewRessource(Task task, DialogUpdateTask dialogParent) {
        this.task = task;
        this.dialogParent = dialogParent;
        createTableView();
    }

    private void createTableView() {
        
        setItems(FXCollections.observableArrayList( this.dialogParent.getListeRessource()));
        setEditable(true);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn col = createColumnType();
        TableColumn col2 = createColumnName();
        TableColumn col3 = createColumnFirstName();
        TableColumn col4 = createColumnCost();
        TableColumn col5 = createColumnReference();
        TableColumn col6 = createColumnRole();

        getColumns().addAll(col, col2, col3, col4, col5,col6);

        setContextMenu(new ContextMenu(createContextMenuAddTask(), createContextMenuDelete(),
                createContextMenuCopy()));
        setRowFactory(tv -> {
            TableRow<RessourcesTable> row = new TableRow<>();
           
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
                    if (row.getIndex() != ((Integer)db.getContent(MyTableView.SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(MyTableView.SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(MyTableView.SERIALIZED_MIME_TYPE);
                    RessourcesTable draggedtask = getItems().remove(draggedIndex);
                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    getItems().add(dropIndex, draggedtask);
                    this.dialogParent.getListeRessource().remove(draggedtask);
                    this.dialogParent.getListeRessource().add(dropIndex, draggedtask);
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
                    RessourcesTable item = getItems().get(getSelectionModel().getSelectedIndex());
                    if (item != null) {
                        if (item.getId() != null) {
                            //en base sinon si == null pas encore eu le insert
                            DAO.getInstance().deleteRessource(item.getId());
                        }

                        dialogParent.getListeRessource().remove(item);
                        getItems().remove(item);
                    }  
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuAddTask()
    {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("AddRessource"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Human human = new Human(0.0f, ManagerLanguage.getInstance().getLocalizedTexte("NameRessource"), "", "", task.getId());
                RessourcesTable ressource = new RessourcesTable(human);
                dialogParent.getListeRessource().add(ressource);
                getItems().add(ressource);
            }
        });
        return mnuAdd;
    }
    
    private MenuItem createContextMenuCopy() {
        
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("CopyRessource"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
           
            @Override
            public void handle(ActionEvent t) {
                if (!getItems().isEmpty()) {
                    RessourcesTable item = getItems().get(getSelectionModel().getSelectedIndex());
                    if (item != null) {
                        copyRessource = new RessourcesTable(item.getReference(),item.getName(),item.getFirstname(),item.getRole(),item.getId(),item.getType(),item.getIdTask(),item.getCost());
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
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("PasteRessource"));
        mnuAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                getItems().add(copyRessource);
                dialogParent.getListeRessource().add(copyRessource);
                getContextMenu().getItems().remove(getContextMenu().getItems().size() - 1);
                copyRessource = null;
            }
        });
        return mnuAdd;
    }
    private TableColumn createColumnType() {
        TableColumn<RessourcesTable,Integer> col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Type"));
        col.setCellValueFactory(new PropertyValueFactory<>("type"));
        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RessourcesTable, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<RessourcesTable, Integer> event) {
                RessourcesTable ressource = getSelectionModel().getSelectedItem();
                ressource.setType(event.getNewValue());
                if(ressource.getType() == 0){
                    //human
                    ressource.setFirstname("");
                    ressource.setRole("");
                }
                else{
                    ressource.setReference("");
                }
                
            }
        });
        col.setCellFactory(new Callback<TableColumn<RessourcesTable, Integer>, TableCell<RessourcesTable, Integer>>() {
            @Override
            public TableCell<RessourcesTable, Integer> call(TableColumn<RessourcesTable, Integer> roomPropertyBooleanTableColumn) {
                return new ComboBoxTableCell<RessourcesTable, Integer>(new RessourceConverter(),0, 1);
            }
        });
        return col;
    }
     private TableColumn createColumnName() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("NameRessource"));
        col.setCellValueFactory(new PropertyValueFactory<>("name"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCellRessource(Tags.RESSOURCE_NAME,1);
            }
        });

        return col;
    }


    private TableColumn createColumnFirstName() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("FirstameRessource"));
        col.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                StringCellRessource cell = new StringCellRessource(Tags.RESSOURCE_FIRSTNAME,1);
                return cell;
            }
        });

        return col;
    }

    private TableColumn createColumnCost() {
        
        TableColumn col2 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Cost"));
        col2.setCellValueFactory(new PropertyValueFactory<>("cost"));
        col2.setCellFactory((Object col1) -> new IntegerEditingCellRessource(Tags.COST,1));
        return col2;
    }

    private TableColumn createColumnReference() {
         TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("ReferenceRessource"));
         col.setCellValueFactory(new PropertyValueFactory<>("reference"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                StringCellRessource ref = new StringCellRessource(Tags.REFERENCE,1);
                return ref;
            }
        });

        return col;
    }

    private TableColumn createColumnRole() {
         TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("RoleRessource"));
         col.setCellValueFactory(new PropertyValueFactory<>("role"));
        col.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCellRessource(Tags.ROLE,1);
            }
        });

        return col;
    }
}
