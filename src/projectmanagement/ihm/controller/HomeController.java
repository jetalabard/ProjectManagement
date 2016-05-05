/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.ManagerLanguage;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.Home;

/**
 *
 * @author Jérémy
 */
public class HomeController extends Controller implements EventHandler<MouseEvent>{
    
    private final Project project;
    private final Stage mainstage;
    private final String tag;


    public HomeController(Project p, Stage mainstage,String tags) {
        this.project = p;
        this.mainstage = mainstage;
        this.tag = tags;
    }
    
     public static void showHome() {

        Stage mainStage = new Stage();
        mainStage.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("AppTitle"));
        mainStage.getIcons().add(new Image(SPLASH_IMAGE));
        Home home = new Home(mainStage);
        Scene mainScene = new Scene(home);
        mainStage.setScene(mainScene);
        mainStage.setMaximized(true);
        mainStage.show();
    }

    @Override
    public void handle(MouseEvent event) {
        switch (tag) 
        {
            case Tags.OPEN_PROJECT:
                if(project != null){
                    OpenProject(project, null, mainstage);
                }
                break;
            case Tags.CREATE_PROJECT:
                CreateDialogProject(mainstage);
                break;
            case Tags.OPEN:
                //affiche la liste des projets existant
                CreateDialogOpenProject(mainstage);
                break;
            default:
                break;
        }
    }
    
}
