/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.Diagram;
import projectmanagement.application.model.ManagerShowDiagram;

/**
 *
 * @author Jérémy
 */
public final class MyTabPane extends TabPane {

    private Tab tabGantt;
    private Tab tabPert;
    private final MainWindow mainWindow;
    private AnchorPane anchorGantt;
    private AnchorPane anchorPert;
    private double heightTab = 0;
    private double widthTab = 0;

    public MyTabPane(MainWindow main) {
        super();
        this.mainWindow = main;
        
        ManagerShowDiagram.getInstance().setTabDiagram(this);
        widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (widthTab + 50 <= newValue.doubleValue() || widthTab - 50 >= newValue.doubleValue()) {
                widthTab = newValue.doubleValue();
                init();
            }
        });
        heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (heightTab + 40 <= newValue.doubleValue() || heightTab - 40 >= newValue.doubleValue()) {
                heightTab = newValue.doubleValue();
                init();
            }

        });
        
    }
    
    public void init() {
        int tabShow;
        if( tabPert != null && tabPert.isSelected()){
            tabShow = 1;
        }else   {
            tabShow = 0;
        }
        
        getTabs().clear();
        if (ManagerShowDiagram.getInstance().isGanttTabShow() == true) {
            tabGantt = createTabGantt();
        }
        if (ManagerShowDiagram.getInstance().isPertTabShow() == true) {
            tabPert = createTabPert();
        }
        getSelectionModel().select(tabShow);
    }

    public Tab createTabPert() {
        tabPert = new Tab("Pert");
        tabPert.setOnCloseRequest((Event event) -> {
            ManagerShowDiagram.getInstance().setPertTabShow(false);
            init();
        });

        getTabs().add(tabPert);
        tabPert.setContent(createPert());

        return tabPert;
    }

    public Tab createTabGantt() {
        tabGantt = new Tab("Gantt");
        tabGantt.setOnCloseRequest((Event event) -> {
            ManagerShowDiagram.getInstance().setGanttTabShow(false);
            init();
        });
        getTabs().add(tabGantt);
        tabGantt.setContent(createGantt());

        return tabGantt;
    }

    private Node createGantt() {
        anchorGantt = new AnchorPane();
        ScrollPane slide = new ScrollPane(anchorGantt);
        anchorGantt = new Diagram().showGantt(anchorGantt, DAO.getInstance().getCurrentProject(), this);
        return slide;
    }

    public void closeGantt() {
        if (this.getTabs() != null && this.getTabs().contains(tabGantt)) {
            this.getTabs().remove(tabGantt);
        }
        init();
    }

    public void closePert() {
        if (this.getTabs() != null && this.getTabs().contains(tabPert)) {
            this.getTabs().remove(tabPert);
        }
        init();
    }

    private Node createPert() {
        anchorPert = new AnchorPane();
        ScrollPane slide = new ScrollPane(anchorPert);
       anchorPert = new Diagram().showPert(anchorPert, DAO.getInstance().getCurrentProject(), this, mainWindow.getStage());
        return slide;
    }

    public Tab getTabGantt() {
        return tabGantt;
    }

    public Tab getTabPert() {
        return tabPert;
    }

    public void setTabGantt(Tab tabGantt) {
        this.tabGantt = tabGantt;
    }

    public void setTabPert(Tab tabPert) {
        this.tabPert = tabPert;
    }

    public AnchorPane getAnchorGantt() {
        return anchorGantt;
    }

    public AnchorPane getAnchorPert() {
        return anchorPert;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

   /* public void reload() {
        if (!ManagerShowDiagram.getInstance().canShowTabPane()) {
            mainWindow.reload();
        }
    }*/
}
