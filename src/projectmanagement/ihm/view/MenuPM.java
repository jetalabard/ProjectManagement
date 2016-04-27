/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.LoaderImage;
import projectmanagement.ihm.controller.ChangeLangController;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.ManagerShowDiagram;
import projectmanagement.ihm.controller.Controller;
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
    private final TableView<Task> table;
    private final Slider slider;

    public MenuPM(Page parent,Stage stage,TableView<Task> table,Slider slider) {
        super();
        this.parent = parent;
        managerLang = ManagerLanguage.getInstance();
        this.mainStage =stage;
        this.table = table;
        this.slider = slider;
        
        if(parent instanceof Home){
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
        Style.getStyle("menu.css", this);

    }

    private void createMenuFile(boolean disableAllButton) {
        menuFile = new Menu(managerLang.getLocalizedTexte("File"));
        MenuItem newp = createMenuItem(managerLang.getLocalizedTexte("NewProject"),LoaderImage.getImage("Folder Filled.png"),new MenuController(Tags.PROJECT,Tags.NEW,mainStage),false);
        MenuItem openp = createMenuItem(managerLang.getLocalizedTexte("OpenProject"),LoaderImage.getImage("Open Folder Filled.png"),new MenuController(Tags.PROJECT,Tags.OPEN,mainStage),false);
        MenuItem openExterior = createMenuItem(managerLang.getLocalizedTexte("OpenExterior"),LoaderImage.getImage("Open Folder Filled.png"),new MenuController(Tags.PROJECT,Tags.OPEN_EXTERIOR,mainStage),false);
        MenuItem save = createMenuItem(managerLang.getLocalizedTexte("SaveProject"),LoaderImage.getImage("Save.png"),new MenuController(Tags.PROJECT,Tags.SAVE,mainStage),disableAllButton);
        MenuItem saveAs = createMenuItem(managerLang.getLocalizedTexte("SaveAs"),LoaderImage.getImage("Save as.png"),new MenuController(Tags.PROJECT,Tags.SAVEAS,mainStage),disableAllButton);
        MenuItem exportImage = createMenuItem(managerLang.getLocalizedTexte("ExportImage"),LoaderImage.getImage("Share.png"),new MenuController(Tags.PROJECT,Tags.EXPORT_IMAGE,mainStage),disableAllButton);
        MenuItem exit = createMenuItem(managerLang.getLocalizedTexte("Exit"),LoaderImage.getImage("Exit.png"),new MenuController(Tags.APP,Tags.QUIT,mainStage),false);
        
        menuFile.getItems().addAll(newp, openp,openExterior, save, saveAs,exportImage, exit);
    }

    private MenuItem createMenuItem(String text,String image,MenuController event,boolean disable) {
        MenuItem item = new MenuItem(text, new ImageView(new Image(image)));
        item.setOnAction(event);
        item.setDisable(disable);
        return item;
    }

    private void createMenuEdit(boolean disableAllButton) {
        menuEdit = new Menu(managerLang.getLocalizedTexte("Edit"));
        MenuItem undo = createMenuItem(managerLang.getLocalizedTexte("Undo"),LoaderImage.getImage("Undo.png"),new MenuController(Tags.PROJECT,Tags.UNDO,mainStage),disableAllButton);
        MenuItem redo = createMenuItem(managerLang.getLocalizedTexte("Redo"),LoaderImage.getImage("Redo.png"),new MenuController(Tags.PROJECT,Tags.REDO,mainStage),disableAllButton);
        MenuItem delete = createMenuItem(managerLang.getLocalizedTexte("delete"),LoaderImage.getImage("Trash.png"),new MenuController(Tags.PROJECT,Tags.DELETE,mainStage),disableAllButton);
        MenuItem deleteAll = createMenuItem(managerLang.getLocalizedTexte("deleteAll"),LoaderImage.getImage("Refresh.png"),new MenuController(Tags.APP,Tags.DELETE_ALL,mainStage),!disableAllButton);
        MenuItem properties = createMenuItem(managerLang.getLocalizedTexte("Properties"),LoaderImage.getImage("Support.png"),new MenuController(Tags.PROJECT,Tags.CHANGE_NAME,mainStage),disableAllButton);

        menuEdit.getItems().addAll(undo, redo,delete,deleteAll,properties);
    }

    private void createMenuView(boolean disableAllButton) {
        menuView = new Menu(managerLang.getLocalizedTexte("View"));
        MenuController menuCtrlZoom =new MenuController(Tags.APP,Tags.ZOOM,mainStage);
        menuCtrlZoom.setSlider(slider);
        MenuController menuCtrlZoom_l =new MenuController(Tags.APP,Tags.ZOOM_L,mainStage);
         menuCtrlZoom_l.setSlider(slider);
        MenuItem zommp = createMenuItem(managerLang.getLocalizedTexte("ZoomP"),LoaderImage.getImage("Plus.png"),menuCtrlZoom,disableAllButton);
        MenuItem zommm = createMenuItem(managerLang.getLocalizedTexte("ZoomL"),LoaderImage.getImage("Minus.png"),menuCtrlZoom_l,disableAllButton);
        MenuItem showGantt = createRadioMenuItemSimple(managerLang.getLocalizedTexte("showGantt"), new MenuController(Tags.APP, Tags.SHOW_GANTT, mainStage),disableAllButton,ManagerShowDiagram.getInstance().isGanttTabShow());
        MenuItem showPert= createRadioMenuItemSimple(managerLang.getLocalizedTexte("showPert"), new MenuController(Tags.APP, Tags.SHOW_PERT, mainStage),disableAllButton,ManagerShowDiagram.getInstance().isPertTabShow());
        MenuItem preferences = createMenuItem(managerLang.getLocalizedTexte("Preference"),LoaderImage.getImage("Settings.png"),new MenuController(Tags.APP,Tags.PREFERENCE,mainStage),disableAllButton);
        Menu language = createMenuLanguage();
        menuView.getItems().addAll(zommp, zommm, language,preferences,showGantt,showPert);
    }

    private Menu createMenuLanguage() {
        ToggleGroup group = new ToggleGroup();
        Menu language = new Menu(managerLang.getLocalizedTexte("Language"));
        language = createRadioMenuItem(managerLang.getLocalizedTexte("English"), LoaderImage.getImage("USA-Flag-Icon.png"), group, language, "en");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("French"), LoaderImage.getImage("France-Flag-Icon.png"), group, language, "fr");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("Germany"), LoaderImage.getImage("Germany-Flag-Icon.png"), group, language, "de");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("Spanish"), LoaderImage.getImage("Spain-Flag-Icon.png"), group, language, "es");
        language = createRadioMenuItem(managerLang.getLocalizedTexte("Italian"), LoaderImage.getImage("Italy-Flag-Icon.png"), group, language, "it");
        
        group.selectedToggleProperty().addListener(new ChangeLangController(group,parent));
        return language;
    }

    private void createMenuHelp() {
        menuHelp = new Menu(managerLang.getLocalizedTexte("Help"));
        MenuItem questions = createMenuItem(managerLang.getLocalizedTexte("Questions"),LoaderImage.getImage("Questions.png"),new MenuController(Tags.REDIRECTION,Tags.QUESTIONS,mainStage),false);
        MenuItem comments = createMenuItem(managerLang.getLocalizedTexte("Comments"),LoaderImage.getImage("Comments.png"),new MenuController(Tags.REDIRECTION,Tags.COMMENTS,mainStage),false);
        
        menuHelp.getItems().addAll(questions, comments);
    }

    private void createMenuAbout() {
        menuAbout = new Menu(managerLang.getLocalizedTexte("About"));
        MenuItem facebook = createMenuItem(managerLang.getLocalizedTexte("Facebook"),LoaderImage.getImage("Facebook.png"),new MenuController(Tags.REDIRECTION,Tags.FACEBOOK,mainStage),false);
        MenuItem twitter = createMenuItem(managerLang.getLocalizedTexte("Twitter"),LoaderImage.getImage("Twitter.png"),new MenuController(Tags.REDIRECTION,Tags.TWITTER,mainStage),false);
        
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
        Button createProject =createToolBarButton(LoaderImage.getImage("Folder Filled-20.png") ,new MenuController(Tags.PROJECT,Tags.NEW,mainStage),false);
        Button OpenProject =createToolBarButton(LoaderImage.getImage("Open Folder Filled-20.png") ,new MenuController(Tags.PROJECT,Tags.OPEN,mainStage),false);
        Button SaveProject =createToolBarButton(LoaderImage.getImage("Save-20.png") ,new MenuController(Tags.PROJECT,Tags.SAVE,mainStage),disableAllButton);
        
        Button undo =createToolBarButton(LoaderImage.getImage("undo-20.png") ,new MenuController(Tags.PROJECT,Tags.UNDO,mainStage),disableAllButton);
        Button redo =createToolBarButton(LoaderImage.getImage("redo-20.png") ,new MenuController(Tags.PROJECT,Tags.REDO,mainStage),disableAllButton);
        
        MenuController ctrl = new MenuController(Tags.PROJECT,Tags.ADD_TASK,mainStage);
        ctrl.setTableView(table);
        Button addTask =createToolBarButton(LoaderImage.getImage("Edit Row-20.png") ,ctrl,disableAllButton);
         if(disableAllButton == false)
         {
            Separator separator1 = new Separator(Orientation.VERTICAL);
            Separator separator2 = new Separator(Orientation.VERTICAL);
            Separator separator3 = new Separator(Orientation.VERTICAL);
            toolBar.getItems().addAll(createProject, OpenProject, SaveProject, separator1, undo, redo, separator2, addTask,separator3,slider);
        } else {
            toolBar.getItems().addAll(createProject, OpenProject, SaveProject, undo, redo, addTask);
        }
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
    
    private RadioMenuItem createRadioMenuItemSimple(String localizedTexte,MenuController ctrl,boolean disable,boolean active) {
        RadioMenuItem rb2 = new RadioMenuItem(localizedTexte);
        rb2.setSelected(active);
        rb2.setDisable(disable);
        rb2.setOnAction(ctrl);
        return rb2;
    }

}
