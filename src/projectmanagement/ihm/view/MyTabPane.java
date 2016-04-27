/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.Diagram;
import projectmanagement.application.model.ManagerShowDiagram;

/**
 *
 * @author Jérémy
 */
public class MyTabPane extends TabPane 
{
    private Tab tabGantt;
    private Tab tabPert;
    private final List<Task> tasks;
    private final MainWindow mainWindow;

    public MyTabPane(List<Task> tasks,MainWindow main) {
        super();
        this.tasks = tasks;
        this.mainWindow = main;
        ManagerShowDiagram.getInstance().setTabDiagram(this);
        
        if (ManagerShowDiagram.getInstance().isGanttTabShow() == true) {
            tabGantt = createTabGantt();
        }
        if (ManagerShowDiagram.getInstance().isPertTabShow()== true) {
            tabPert = createTabPert();
        }
    }

    public Tab createTabPert() {
        tabPert = new Tab("Pert");
        tabPert.setOnCloseRequest((Event event) -> {
            ManagerShowDiagram.getInstance().setPertTabShow(false);
            mainWindow.reload();
        });
        
        tabPert.setContent(createPert());
        getTabs().add(tabPert);
        return tabPert;
    }

    public Tab createTabGantt() {
        tabGantt = new Tab("Gantt");
        tabGantt.setOnCloseRequest((Event event) -> {
            ManagerShowDiagram.getInstance().setGanttTabShow(false);
            mainWindow.reload();
        });
        tabGantt.setContent(createGantt());
        
        getTabs().add(tabGantt);
        return tabGantt;
    }

    private Node createGantt() {
        AnchorPane anchor = new AnchorPane();
        new Diagram().showGantt(anchor, tasks);
        return anchor;
    }
    
    
    public void closeGantt(){
        if(this.getTabs() != null && this.getTabs().contains(tabGantt)){
            this.getTabs().remove(tabGantt);
        }
        reload();
    }
    
    public void closePert(){
        if(this.getTabs() != null && this.getTabs().contains(tabPert)){
            this.getTabs().remove(tabPert);
        }
        reload();
    }

    private Node createPert() {
        AnchorPane anchor = new AnchorPane();
        new Diagram().showPert(anchor, tasks);
        return anchor;
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
    
    public void reload(){
        if(!ManagerShowDiagram.getInstance().canShowTabPane()){
            mainWindow.reload();
        }
    }
    
    
    
}
