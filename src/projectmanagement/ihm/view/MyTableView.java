/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import projectmanagement.ihm.view.cell.IntegerEditingCell;
import projectmanagement.ihm.view.cell.StringCell;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.RowController;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.TaskController;
import projectmanagement.ihm.controller.UpdateDialogController;
import projectmanagement.ihm.view.cell.MyDatePickerCell;

/**
 *
 * @author Jérémy
 */
public class MyTableView extends TableView<Task> {

    private final Stage mainStage;
    public static final DataFormat SERIALIZED_MIME_TYPE
            = new DataFormat("application/x-java-serialized-object");
    private Task copyTask = null;
    private final MainWindow main;
    private final TaskController taskCtrl;

    public MyTableView(Stage mainstage, MainWindow main) {
        this.mainStage = mainstage;
        this.main = main;
        this.taskCtrl = new TaskController(this);
        createTableView();
    }

    private void createTableView() {
        
        setPlaceholder(new Text(ManagerLanguage.getInstance().getLocalizedTexte("NoContent")));
        setItems(FXCollections.observableArrayList(DAO.getInstance().getCurrentProject().getTasks()));
        setEditable(true);
        TableColumn col = createColumnName();
        TableColumn col2 = createColumnDateBegin();
        TableColumn col3 = createColumnDateEnd();
        TableColumn col4 = createColumnPriority();
        TableColumn col5 = createColumnNote();

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(col, col2, col3, col4, col5);

        createContextMenu();
        setRowFactory(new RowController(this, mainStage));
        this.getSelectionModel().selectFirst();

    }

    private void createContextMenu() {
        setContextMenu(
                new ContextMenu(
                        createContextMenuAddTask(),
                        createContextMenuUpdate(),
                        createContextMenuDelete(),
                        createContextMenuCopy()));
    }

    private TableColumn createColumnNote() {
        TableColumn col5 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Note"));
        col5.setCellValueFactory(new PropertyValueFactory<>("note"));
        col5.setCellFactory((Object col) -> new StringCell(Tags.NOTE, 0,this));

        return col5;
    }

    private TableColumn createColumnPriority() {
        TableColumn col4 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Priority"));
        col4.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col4.setCellFactory(col -> new IntegerEditingCell(Tags.PRIORITY, 0, mainStage,this));
        return col4;
    }

    private TableColumn createColumnDateEnd() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateEnd"));
        col.setCellValueFactory(new PropertyValueFactory<>("dateend"));
        col.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new MyDatePickerCell(getItems(), Tags.DATE_END,mainStage,MyTableView.this);
            }
        });

        return col;
    }

    private TableColumn createColumnDateBegin() {
        TableColumn col2 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateBegin"));
        col2.setCellValueFactory(new PropertyValueFactory<>("datebegin"));
        col2.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new MyDatePickerCell(getItems(), Tags.DATE_BEGIN,mainStage,MyTableView.this);
            }
        });
        return col2;
    }

    private TableColumn createColumnName() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Name"));
        col.setCellValueFactory(new PropertyValueFactory<>("name"));
        col.setCellFactory((Object col1) -> new StringCell(Tags.NAME, 0,this));
        return col;
    }

    private MenuItem createContextMenuDelete() {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Delete"));
        mnuDel.setOnAction((ActionEvent t) -> {
            if (!getItems().isEmpty()) {
                Task item = getItems().get(getSelectionModel().getSelectedIndex());
                if (item != null) {
                    taskCtrl.deleteTask(item);
                    getItems().remove(item);
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuUpdate() {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Update"));
        mnuDel.setOnAction((ActionEvent t) -> {
            if (!getItems().isEmpty()) {
                Task item = DAO.getInstance().getCurrentProject().getTasks().get(getSelectionModel().getSelectedIndex());
                if (item != null) {
                    new UpdateDialogController()
                            .CreateDialogUpdateTask(item, mainStage, getSelectionModel().getSelectedIndex(), this);
                }
            }
        });
        return mnuDel;
    }

    private MenuItem createContextMenuAddTask() {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("AddTask"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            new TaskController(MyTableView.this).addTask();
        });
        return mnuAdd;
    }

    private MenuItem createContextMenuCopy() {

        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("CopyTask"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            Task item = getItems().get(getSelectionModel().getSelectedIndex());
            if (item != null) {
                copyTask = new Task(item.getName(), item.getDatebegin(), item.getDateend(), item.getPriority(), item.getNote(), item.getIdProject());
                if (getContextMenu().getItems().size() == 4) {
                    getContextMenu().getItems().add(createContextMenuPaste());
                }
            }
        });
        return mnuAdd;
    }

    public MenuItem createContextMenuPaste() {
        MenuItem mnuAdd = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("PasteTask"));
        mnuAdd.setOnAction((ActionEvent t) -> {
            getItems().add(copyTask);
            taskCtrl.pasteTask(copyTask);
            getContextMenu().getItems().remove(getContextMenu().getItems().size() - 1);
            copyTask = null;
        });
        return mnuAdd;
    }

    public void reload() {
        getColumns().get(0).setVisible(false);
        getColumns().get(0).setVisible(true);
    }

    public MainWindow getMain() {
        return main;
    }

    
}
