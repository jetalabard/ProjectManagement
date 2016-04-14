/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Jérémy
 */
public class ManagerShowDiagram 
{
    
    private static ManagerShowDiagram instance = null;
    
    private boolean ganttTabShow; 
    private boolean pertTabShow; 
    
    private TabPane tabDiagram;
    
    private ManagerShowDiagram(){
        this.pertTabShow = true;
        this.ganttTabShow = true;
    }
    
    public void removeTabDiagram(Tab diagram){
        this.tabDiagram.getTabs().remove(diagram);
    }
    
    public void addTabDiagram(Tab diagram){
        this.tabDiagram.getTabs().add(diagram);
    }
    
    public void setTabPaneDiagram(TabPane tabDiagram){
        this.tabDiagram = tabDiagram;
    }
    
    public static ManagerShowDiagram getInstance(){
        if(instance == null){
            instance = new ManagerShowDiagram();
        }
        return instance;
    }

    public boolean isGanttTabShow() {
        return ganttTabShow;
    }

    public void setGanttTabShow(boolean ganttTabShow) {
        this.ganttTabShow = ganttTabShow;
    }

    public boolean isPertTabShow() {
        return pertTabShow;
    }

    public void setPertTabShow(boolean pertTabShow) {
        this.pertTabShow = pertTabShow;
    }
   
    
}
