/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.ManagerShowDiagram;
import projectmanagement.ihm.controller.KeyListener;
import projectmanagement.ihm.controller.Tags;
import projectmanagement.ihm.controller.WindowListener;

/**
 *
 * @author Jérémy
 */
public class MainWindow extends Page {

    private final Stage mainStage;

    private MyTableView table;
    private MyTabPane tabpane;

    public MainWindow(Stage mainstage) {
        super();
        this.mainStage = mainstage;
        ManageUndoRedo.getInstance().setWindows(this);
        createView();
    }

    @Override
    public void createView() {
        table = new MyTableView(mainStage,this);
        if(ManagerShowDiagram.getInstance().canShowTabPane()){
            tabpane = new MyTabPane(DAO.getInstance().getCurrentProject().getTasks(),this);
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
        addListenerZoomWithMouseScroll(slider);
    }

    private void addListenerZoomWithMouseScroll(Slider slider) {
        this.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        double deltaY = event.getDeltaY();
                        if (deltaY < 0) {
                            slider.adjustValue(slider.getValue() - 0.25);
                        } else {
                            slider.adjustValue(slider.getValue() + 0.25);

                        }
                    }
                });
    }

    public void reloadTable() {
        if (table != null) {
            table.getItems().clear();
            reload();
        }
    }
}
