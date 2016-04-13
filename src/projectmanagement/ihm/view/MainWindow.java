/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.DAOTask;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.ClickController;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.WindowListener;

/**
 *
 * @author Jérémy
 */
public class MainWindow extends Page {

    private final Stage mainStage;
    
    private final double scaleFactor =1.1;
    private TableView<Task> table;

    public MainWindow(Stage mainstage) {
        super();
        this.mainStage = mainstage;
        this.mainStage.setOnCloseRequest(new WindowListener(Tags.QUIT,mainstage));
        createView();
    }

    @Override
    public void createView() {
        table = createTableView();
        
        this.setTop(new MenuPM(this,mainStage,table));
        this.setCenter(table);
        //zoomP();
        TabPane tabpane = new TabPane();
        Tab tab = new Tab("Gantt");
        Tab tab2 = new Tab("Pert");
        tabpane.getTabs().add(tab);
        tabpane.getTabs().add(tab2);
        this.setBottom(tabpane);
    }
    

    private TableView createTableView() {
        TableView<Task> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(ProjectDAO.getInstance().getCurrentProject().getTasks()));
        table.setEditable(true);
        
        TableColumn col = createColumnName();
        TableColumn col2 = createColumnDateBegin(table);
        TableColumn col3 = createColumnDateEnd();
        TableColumn col4 = createColumnPriority();
        TableColumn col5 = createColumnNote();
        
        table.getColumns().addAll(col, col2, col3, col4, col5);
        
        table.setContextMenu(new ContextMenu(createContextMenuUpdate(),createContextMenuDelete()));
        table.setRowFactory( tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Task rowData = row.getItem();
                     new ClickController(Tags.UPDATE_TASK, mainStage).CreateDialogUpdateTask(rowData,mainStage);
                }
            });
            return row;
        });
        return table;
    }

    private TableColumn createColumnNote() {
        TableColumn col5 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Note"));
        col5.setCellValueFactory(new PropertyValueFactory<>("note"));
        col5.setMinWidth(300);
        col5.setCellFactory(new Callback() {

            public Cell call(Object col) {
                return new StringCell(Tags.NOTE);
            }
        });
        
        return col5;
    }

    private TableColumn createColumnPriority() {
        TableColumn col4 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Priority"));
        col4.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col4.setCellFactory(col -> new IntegerEditingCell());
        col4.setMinWidth(50);
       
        return col4;
    }

    private TableColumn createColumnDateEnd() {
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateEnd"));
        col.setCellValueFactory(new PropertyValueFactory<Task, MyDate>("dateend"));
        col.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DatePickerCell datePick = new DatePickerCell(table.getItems(), Tags.DATE_END);
                return datePick;
            }
        });
        col.setMinWidth(150);
        return col;
    }

    private TableColumn createColumnDateBegin(TableView<Task> table1) {
        TableColumn col2 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateBegin"));
        col2.setCellValueFactory(new PropertyValueFactory<Task, MyDate>("datebegin"));
        col2.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DatePickerCell datePick = new DatePickerCell(table1.getItems(), Tags.DATE_BEGIN);
                return datePick;
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
                return new StringCell(Tags.NAME);
            }
        });
        
        return col;
    }

    public void zoomP() {
        this.getCenter().setScaleX(this.getCenter().getScaleX() * scaleFactor);
        this.getCenter().setScaleY(this.getCenter().getScaleX() * scaleFactor);
    }

    private MenuItem createContextMenuDelete() 
    {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Delete"));
        mnuDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Task item = table.getItems().get(table.getSelectionModel().getSelectedIndex());
                if (item != null) {
                    DAOTask.getInstance().deleteTask(item.getId());
                    ProjectDAO.getInstance().getCurrentProject().getTasks().remove(item);
                    table.getItems().remove(item);
                    ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
                }
            }
        });
        return mnuDel;
    }
    
    private MenuItem createContextMenuUpdate() 
    {
        MenuItem mnuDel = new MenuItem(ManagerLanguage.getInstance().getLocalizedTexte("Update"));
        mnuDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Task item = table.getItems().get(table.getSelectionModel().getSelectedIndex());
                if (item != null) {
                    new ClickController(Tags.UPDATE_TASK, mainStage).CreateDialogUpdateTask(item,mainStage);
                }
            }
        });
        return mnuDel;
    }


}
