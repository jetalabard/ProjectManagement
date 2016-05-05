/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.ManagerShowDiagram;
import projectmanagement.ihm.controller.KeyListener;
import projectmanagement.ihm.controller.MainWindowController;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.WindowListener;

/**
 *
 * @author Jérémy
 */
public final class MainWindow extends Page {

    private final Stage mainStage;

    private MyTableView table;
    private MyTabPane tabpane;

    public MainWindow(Stage mainstage) {
        super();
        this.mainStage = mainstage;
        ManageUndoRedo.getInstance().setWindows(MainWindow.this);
        createView();
    }

    @Override
    public void createView() {
        this.mainStage.setMinHeight(600);
        this.mainStage.setMinWidth(550);
        table = new MyTableView(mainStage,this);
        
        if(ManagerShowDiagram.getInstance().canShowTabPane()){
            tabpane = new MyTabPane(this);
        }
        SplitPane splitPane = createSplitPane();
        Slider slider = new Slider(0.5, 2, 1);
        ZoomingPane zoomingPane = new ZoomingPane(splitPane);
        zoomingPane.zoomFactorProperty().bind(slider.valueProperty());
        addListener(slider);
        this.setTop(new MenuPM(this, mainStage, table, slider));
        this.setCenter(zoomingPane);
    }

    private SplitPane createSplitPane() {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        if(ManagerShowDiagram.getInstance().canShowTabPane()){
            splitPane.getItems().addAll(table, tabpane);
        }else{
            splitPane.getItems().add(table);
        }
        return splitPane;
    }

    private void addListener(Slider slider) {
        this.mainStage.setOnCloseRequest(new WindowListener(Tags.QUIT, mainStage));
        this.setOnKeyPressed(new KeyListener(slider, table));
        this.setOnScroll(new MainWindowController(slider));
    }

    public void reloadTable() {
        if (table != null) {
            table.reload();
        }
    }
    
    public Stage getStage(){
        return mainStage;
    }
}
