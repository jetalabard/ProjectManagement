/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projectmanagement.ihm.controller.ChangeLangController;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.controller.MenuController;
import projectmanagement.ihm.controller.Tags;

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
    private final ManagerLanguage managerLang;
    private final Page parent;
    
    public static String lang = "fr";
    private final Stage mainStage;

    public MenuPM(Page parent,Stage stage) {
        super();
        this.parent = parent;
        managerLang = ManagerLanguage.getInstance();
        this.mainStage =stage;
        
        if(parent instanceof Home){
            System.out.println("home");
            createMenu(true);
        }else{
            createMenu(false);
        }
        
    }

    private void createMenu(boolean disableAllButton) {

        menubar = createMenuFirst(disableAllButton);
        menuIcon = createToolBar(disableAllButton);
        this.setTop(menubar);
        this.setBottom(menuIcon);
        Style.getStyle("/ressources/menu.css", this);

    }

    private void createMenuFile(boolean disableAllButton) {
        menuFile = new Menu(managerLang.getLocalizedTexte("File"));
        MenuItem newp = createMenuItem(managerLang.getLocalizedTexte("NewProject"),"ressources/Folder Filled-50.png",new MenuController(Tags.PROJECT,Tags.NEW,mainStage),false);
        MenuItem openp = createMenuItem(managerLang.getLocalizedTexte("OpenProject"),"ressources/Open Folder Filled-50.png",new MenuController(Tags.PROJECT,Tags.OPEN,mainStage),false);
        MenuItem save = createMenuItem(managerLang.getLocalizedTexte("SaveProject"),"ressources/Save-52.png",new MenuController(Tags.PROJECT,Tags.SAVE,mainStage),disableAllButton);
        MenuItem saveAs = createMenuItem(managerLang.getLocalizedTexte("SaveAs"),"ressources/Save as-52.png",new MenuController(Tags.PROJECT,Tags.SAVEAS,mainStage),disableAllButton);
        MenuItem setting = createMenuItem(managerLang.getLocalizedTexte("Settings"),"ressources/Settings-48.png",new MenuController(Tags.APP,Tags.SETTINGS,mainStage),false);
        MenuItem exit = createMenuItem(managerLang.getLocalizedTexte("Exit"),"ressources/Exit-64.png",new MenuController(Tags.APP,Tags.QUIT,mainStage),false);
        
        menuFile.getItems().addAll(newp, openp, save, saveAs, setting, exit);
    }

    private MenuItem createMenuItem(String text,String image,MenuController event,boolean disable) {
        MenuItem item = new MenuItem(text, new ImageView(new Image(image)));
        item.setOnAction(event);
        item.setDisable(disable);
        return item;
    }

    private void createMenuEdit(boolean disableAllButton) {
        menuEdit = new Menu(managerLang.getLocalizedTexte("Edit"));
        MenuItem undo = createMenuItem(managerLang.getLocalizedTexte("Undo"),"ressources/Undo-64 (1).png",new MenuController(),disableAllButton);
        MenuItem redo = createMenuItem(managerLang.getLocalizedTexte("Redo"),"ressources/Redo-64 (1).png",new MenuController(),disableAllButton);
        menuEdit.getItems().addAll(undo, redo);
    }

    private void createMenuView(boolean disableAllButton) {
        menuView = new Menu(managerLang.getLocalizedTexte("View"));
        MenuItem zommp = createMenuItem(managerLang.getLocalizedTexte("ZoomP"),"ressources/Plus-52.png",new MenuController(),disableAllButton);
        MenuItem zommm = createMenuItem(managerLang.getLocalizedTexte("ZoomL"),"ressources/Minus-52.png",new MenuController(),disableAllButton);
        Menu language = createMenuLanguage();
        menuView.getItems().addAll(zommp, zommm, language);
    }

    private Menu createMenuLanguage() {
        ToggleGroup group = new ToggleGroup();
        Menu language = new Menu(managerLang.getLocalizedTexte("Language"));
        language = createRadioMenuItem(managerLang.getLocalizedTexte("English"), "ressources/USA-Flag-Icon.png", group, language, "en");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("French"), "ressources/France-Flag-Icon.png", group, language, "fr");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("Germany"), "ressources/Germany-Flag-Icon.png", group, language, "de");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("Spanish"), "ressources/Spain-Flag-Icon.png", group, language, "es");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("Italian"), "ressources/Italy-Flag-Icon.png", group, language, "it");
        
        group.selectedToggleProperty().addListener(new ChangeLangController(group,parent));
        return language;
    }

    private void createMenuHelp() {
        menuHelp = new Menu(managerLang.getLocalizedTexte("Help"));
        MenuItem questions = createMenuItem(managerLang.getLocalizedTexte("Questions"),"ressources/Questions-52.png",new MenuController(),false);
        MenuItem comments = createMenuItem(managerLang.getLocalizedTexte("Comments"),"ressources/Comments-48.png",new MenuController(),false);
        
        menuHelp.getItems().addAll(questions, comments);
    }

    private void createMenuAbout() {
        menuAbout = new Menu(managerLang.getLocalizedTexte("About"));
        MenuItem facebook = createMenuItem(managerLang.getLocalizedTexte("Facebook"),"ressources/Facebook-48.png",new MenuController(Tags.REDIRECTION,Tags.FACEBOOK,mainStage),false);
        MenuItem twitter = createMenuItem(managerLang.getLocalizedTexte("Twitter"),"ressources/Twitter-48.png",new MenuController(Tags.REDIRECTION,Tags.TWITTER,mainStage),false);
        
        menuAbout.getItems().addAll(facebook, twitter);
    }

    private MenuBar createMenuFirst(boolean disableAllButton) {
        MenuBar bar = new MenuBar();
        createMenuFile(disableAllButton);
        createMenuEdit(disableAllButton);
        createMenuView(disableAllButton);
        createMenuHelp();
        createMenuAbout();

        bar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp, menuAbout);
        return bar;
    }

    private ToolBar createToolBar(boolean disableAllButton) {
        ToolBar toolBar = new ToolBar();
        Button createProject =createToolBarButton("/ressources/Folder Filled-20.png" ,new MenuController(),false);
        Button OpenProject =createToolBarButton("/ressources/Open Folder Filled-20.png" ,new MenuController(),false);
        Button SaveProject =createToolBarButton("/ressources/Save-20.png" ,new MenuController(),disableAllButton);
        Button undo =createToolBarButton("/ressources/undo-20.png" ,new MenuController(),disableAllButton);
        Button redo =createToolBarButton("/ressources/redo-20.png" ,new MenuController(),disableAllButton);

        toolBar.getItems().addAll(createProject, OpenProject, SaveProject, undo, redo);
        return toolBar;
    }
 
    private Button createToolBarButton(String image, MenuController event,boolean disable) {
        Button b = new Button();
        b.setGraphic(new ImageView(image));
        b.setOnAction(event);
        b.setDisable(disable);
        return b;
    }

    private Menu createRadioMenuItem(String localizedTexte, String ressources, ToggleGroup group, Menu language, String memoLang) {
        RadioMenuItem rb2 = new RadioMenuItem(localizedTexte);
        rb2.setGraphic(new ImageView(new Image(ressources)));
        rb2.setUserData(memoLang);
        language.getItems().add(rb2);
        rb2.setToggleGroup(group);
        if(lang.equals(memoLang)){
            rb2.setSelected(true);
        }
        return language;
    }

}
