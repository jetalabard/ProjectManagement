/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.layout.BorderPane;


/**
 *
 * @author Jérémy
 */
public abstract class Page extends BorderPane{
    
    public void reload(){
        this.getChildren().clear();
        createView();
    }

    public abstract void createView();
    
}
