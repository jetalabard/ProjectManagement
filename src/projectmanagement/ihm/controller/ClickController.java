/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
public class ClickController extends Controller implements EventHandler<MouseEvent> {

    private final boolean projectIsCreate;
    private final Project project;
    private final Stage mainstage;

    public ClickController(boolean isopen, Project p, Stage mainstage) {
        this.projectIsCreate = isopen;
        this.project = p;
        this.mainstage = mainstage;
    }

    @Override
    public void handle(MouseEvent event) {
        if (projectIsCreate == true && project != null) {
            OpenProject(project,mainstage);
        } else if (projectIsCreate == false && project == null) {
            CreateProject(mainstage);
        } else {
            OpenChoiceProject(mainstage);
        }
    }
}
