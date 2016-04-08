/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Jérémy
 */
public class MainWindow extends Page {

    private final Stage mainStage;

    public MainWindow(Stage mainstage) {
        super();
        this.mainStage = mainstage;
        createView();
    }

    @Override
    public void createView() {
        this.setTop(new MenuPM(this,mainStage));
        this.setCenter(new TableView());

        TabPane tabpane = new TabPane();
        Tab tab = new Tab("Gantt");
        Tab tab2 = new Tab("Pert");
        tabpane.getTabs().add(tab);
        tabpane.getTabs().add(tab2);
        this.setBottom(tabpane);
    }

}
