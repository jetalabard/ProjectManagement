/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.ihm.view.DialogProject;
import projectmanagement.ihm.view.Home;
import projectmanagement.ihm.view.MainWindow;

/**
 *
 * @author Jérémy
 */
public class ClickController implements EventHandler<MouseEvent> {

    private final boolean projectIsCreate;
    private final Project project;
    private final Stage mainstage;

    public ClickController(boolean isopen, Project p, Stage mainstage) {
        this.projectIsCreate = isopen;
        this.project = p;
        this.mainstage = mainstage;
    }

    private void OpenProject(Project p) {
        mainstage.setTitle(p.getTitle());
        mainstage.getIcons().add(new Image(PMApplication.SPLASH_IMAGE));
        MainWindow home = new MainWindow(mainstage);
        Scene mainScene = new Scene(home);
        mainstage.setScene(mainScene);
        mainstage.setMaximized(true);
        mainstage.show();
    }

    private void CreateProject(MouseEvent event) {
        DialogProject dialog = new DialogProject();
        Stage stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Create Project");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    private void OpenChoiceProject() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Project");
        fileChooser.showOpenDialog(mainstage);
    }

    @Override
    public void handle(MouseEvent event) {
        if (projectIsCreate == true && project != null) {
            OpenProject(project);
        } else if (projectIsCreate == false && project == null) {
            CreateProject(event);
        } else {
            OpenChoiceProject();
        }
    }
}
