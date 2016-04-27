/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import projectmanagement.application.model.ManagerLanguage;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.Home;

/**
 *
 * @author Jérémy
 */
public class StartController extends Controller {
    
    public void showMainStage() {

        Stage mainStage = new Stage();
        mainStage.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("AppTitle"));
        mainStage.getIcons().add(new Image(SPLASH_IMAGE));
        Home home = new Home(mainStage);
        Scene mainScene = new Scene(home);
        mainStage.setScene(mainScene);
        mainStage.setMaximized(true);
        mainStage.show();
    }
    
}
