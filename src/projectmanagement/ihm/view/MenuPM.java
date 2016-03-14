/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Jérémy
 */
public class MenuPM extends BorderPane {

    private Menu menuFile;
    private Menu menuEdit;
    private Menu menuView;
    private Menu menuHelp;
    private Menu menuAbout;
    private MenuBar menubar;
    private ToolBar menuIcon;

    
    public MenuPM() {
        super();
        createMenu();
    }

    private void createMenu() {

        menubar = createMenuFirst();
        menuIcon = createMenuIcon();
        this.setTop(menubar);
        this.setBottom(menuIcon);
        
        getStylesheets().add(
                getClass().getResource(
                        "/ressources/menu.css"
                ).toExternalForm()
        );

    }

    private void createMenuFile() {
        menuFile = new Menu("File");
        MenuItem newp = new MenuItem("New Project", new ImageView(new Image("ressources/Folder Filled-50.png")));

        newp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

            }
        });
        MenuItem openp = new MenuItem("Open Project", new ImageView(new Image("ressources/Open Folder Filled-50.png")));

        openp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

            }
        });
        MenuItem save = new MenuItem("Save Project", new ImageView(new Image("ressources/Save-52.png")));

        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

            }
        });

        MenuItem saveAs = new MenuItem("Save As", new ImageView(new Image("ressources/Save as-52.png")));

        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

            }
        });
        MenuItem setting = new MenuItem("Settings", new ImageView(new Image("ressources/Settings-48.png")));

        setting.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

            }
        });

        MenuItem exit = new MenuItem("Exit", new ImageView(new Image("ressources/Exit-64.png")));

        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

            }
        });

        menuFile.getItems().addAll(newp, openp, save, saveAs, setting, exit);
    }

    private void createMenuEdit() {
        menuEdit = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo", new ImageView(new Image("ressources/Undo-64 (1).png")));
        MenuItem redo = new MenuItem("redo", new ImageView(new Image("ressources/Redo-64 (1).png")));
        menuEdit.getItems().addAll(undo, redo);
    }

    private void createMenuView() {
        menuView = new Menu("View");
        MenuItem zommp = new MenuItem("Zoom +", new ImageView(new Image("ressources/Plus-52.png")));
        MenuItem zommm = new MenuItem("Zoom -", new ImageView(new Image("ressources/Minus-52.png")));
       
         Menu language = new Menu("Language");
        language.getItems().add(new CheckMenuItem("French", new ImageView(new Image("ressources/France-Flag-Icon.png"))));
        language.getItems().add(new CheckMenuItem("Germain", new ImageView(new Image("ressources/Germany-Flag-Icon.png"))));
        language.getItems().add(new CheckMenuItem("Spanish", new ImageView(new Image("ressources/Spain-Flag-Icon.png"))));
        language.getItems().add(new CheckMenuItem("USA", new ImageView(new Image("ressources/USA-Flag-Icon.png"))));
        language.getItems().add(new CheckMenuItem("Italian", new ImageView(new Image("ressources/Italy-Flag-Icon.png"))));
        
         menuView.getItems().addAll(zommp, zommm,language);
    }

    private void createMenuHelp() {
        menuHelp = new Menu("Help");
        MenuItem questions = new MenuItem("Questions", new ImageView(new Image("ressources/Questions-52.png")));
        MenuItem comments = new MenuItem("Comments", new ImageView(new Image("ressources/Comments-48.png")));
        menuHelp.getItems().addAll(questions, comments);
    }

    private void createMenuAbout() {
        menuAbout = new Menu("About");
        MenuItem facebook = new MenuItem("Facebook", new ImageView(new Image("ressources/Facebook-48.png")));
        MenuItem twitter = new MenuItem("Twitter", new ImageView(new Image("ressources/Twitter-48.png")));
        menuAbout.getItems().addAll(facebook, twitter);
    }

    private MenuBar createMenuFirst() {
        MenuBar bar = new MenuBar();
        createMenuFile();
        createMenuEdit();
        createMenuView();
        createMenuHelp();
        createMenuAbout();

        bar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp, menuAbout);
        return bar;
    }

    private ToolBar createMenuIcon() {
        ToolBar toolBar = new ToolBar();
        Button createProject = new Button();
        Button OpenProject = new Button();
        Button SaveProject = new Button();
        Button undo = new Button();
        Button redo = new Button();
        

        //Set the icon/graphic for the ToolBar Buttons.
        createProject.setGraphic(new ImageView("/ressources/Folder Filled-20.png"));
        OpenProject.setGraphic(new ImageView("/ressources/Open Folder Filled-20.png"));
        SaveProject.setGraphic(new ImageView("/ressources/Save-20.png"));
        undo.setGraphic(new ImageView("/ressources/undo-20.png"));
        redo.setGraphic(new ImageView("/ressources/redo-20.png"));

        //Add the Buttons to the ToolBar.
        toolBar.getItems().addAll(createProject,OpenProject, SaveProject, undo,redo);
        return toolBar;
    }

}
