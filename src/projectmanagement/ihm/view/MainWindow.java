/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
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
        TableView<Task> table = new TableView<Task>();
        table.setEditable(true);
        TableColumn col = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Name"));
        col.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        TableColumn col2 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateBegin"));
        col2.setCellValueFactory(new PropertyValueFactory<Task, MyDate>("datebegin"));
        TableColumn col3 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("DateEnd"));
        col3.setCellValueFactory(new PropertyValueFactory<Task, MyDate>("dateend"));
        TableColumn col4 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Priority"));
        col4.setCellValueFactory(new PropertyValueFactory<Task, Integer>("priority"));
        TableColumn col5 = new TableColumn(ManagerLanguage.getInstance().getLocalizedTexte("Note"));
        col5.setCellValueFactory(new PropertyValueFactory<Task, String>("note"));
        table.getColumns().addAll(col,col2,col3,col4,col5);
        
        table.setItems(FXCollections.observableArrayList(ProjectDAO.getInstance().getCurrentProject().getTasks()));

        return table;
    }
    
    public void zoomP(){
        this.getCenter().setScaleX(this.getCenter().getScaleX() * scaleFactor);
        this.getCenter().setScaleY(this.getCenter().getScaleX() * scaleFactor);
    }
    

}
