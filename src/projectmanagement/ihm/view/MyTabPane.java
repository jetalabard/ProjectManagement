/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jérémy
 */
public class MyTabPane extends TabPane {

    public MyTabPane() {
        
        Tab tab = new Tab("Gantt");
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefHeight(200);
        tab.setContent(anchor);
        Tab tab2 = new Tab("Pert");
        getTabs().add(tab);
        getTabs().add(tab2);
    }
    
    
    
}
