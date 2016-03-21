/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.ManagerLanguage;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.DialogProject;
import projectmanagement.ihm.view.MainWindow;

/**
 *
 * @author Jérémy
 */
public abstract class Controller {

    public void OpenProject(Project p, Stage mainstage) {
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
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String createFileChooser(String text, Stage mainstage) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        File selectedDirectory = chooser.showDialog(mainstage);
        return selectedDirectory.getAbsolutePath();
    }

    public void CreateProject(Stage stageParent) {

        DialogProject dialog = new DialogProject(stageParent);
        Stage stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setResizable(false);
        stage.getIcons().add(new Image(SPLASH_IMAGE));
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
