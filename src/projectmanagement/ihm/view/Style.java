/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.layout.Pane;

/**
 *
 * @author Jérémy
 */
public class Style {

    public static void getStyle(String nameStyle,Pane layout){
        layout.getStylesheets().add(
                layout.getClass().getResource("/ressources/"+nameStyle).toExternalForm()
        );
    }
}
