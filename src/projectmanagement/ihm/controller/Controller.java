/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import java.awt.Desktop;
import java.net.URI;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.view.DialogProject;
import projectmanagement.ihm.view.MainWindow;

/**
 *
 * @author Jérémy
 */
public abstract class Controller {
    
     public void OpenProject(Project p,Stage mainstage) {
        mainstage.setTitle(p.getTitle());
        mainstage.getIcons().add(new Image(PMApplication.SPLASH_IMAGE));
        MainWindow home = new MainWindow(mainstage);
        Scene mainScene = new Scene(home);
        mainstage.setScene(mainScene);
        mainstage.setMaximized(true);
        mainstage.show();
    }
     
     public void redirectTo(String url) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void CreateProject(Stage stageParent) {
        
        DialogProject dialog = new DialogProject();
        Stage stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("NewProject"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stageParent);
        stage.show();
    }

    public void OpenChoiceProject(Stage mainstage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        fileChooser.showOpenDialog(mainstage);
    }
}
