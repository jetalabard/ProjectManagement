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
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.DAOTask;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.ManageExportImportXML;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.MyDate;
import projectmanagement.application.model.Save;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.DialogConfirmationSave;
import projectmanagement.ihm.view.DialogCreateProject;
import projectmanagement.ihm.view.DialogOpenProject;
import projectmanagement.ihm.view.DialogPreference;
import projectmanagement.ihm.view.DialogUpdateTask;
import projectmanagement.ihm.view.MainWindow;

/**
 *
 * @author Jérémy
 */
public abstract class Controller {

    public void OpenProject(Project p, Stage dialoStage, Stage parentStage) {
        if (dialoStage != null) {
            dialoStage.close();
        }
        parentStage.close();
        parentStage = new Stage();
        parentStage.setTitle(p.getTitle() + " - " + ManagerLanguage.getInstance().getLocalizedTexte("AppTitle"));
        parentStage.getIcons().add(new Image(SPLASH_IMAGE));
        p.setTasks(DAOTask.getInstance().getAllInformationProject(p.getId()));
        ProjectDAO.getInstance().setCurrentProject(p);
        MainWindow home = new MainWindow(parentStage);
        Scene mainScene = new Scene(home);
        parentStage.setScene(mainScene);
        parentStage.setMaximized(true);
        parentStage.show();
    }

    private void makeDialog(Dialog dialog, String title, Stage stage, Stage stageParent) {
        stage.setScene(new Scene(dialog));
        stage.setResizable(false);
        stage.getIcons().add(new Image(SPLASH_IMAGE));
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stageParent);
        stage.show();
    }
    
     public void addTask(TableView table) {
        Task task = new Task("",MyDate.now(),MyDate.now(),0,"",ProjectDAO.getInstance().getCurrentProject().getId());
        table.getItems().add(task);
        ProjectDAO.getInstance().getCurrentProject().setState(new StateNotSave());
        ProjectDAO.getInstance().getCurrentProject().getTasks().add(task);
        ManageUndoRedo.getInstance().add( ProjectDAO.getInstance().getCurrentProject().getTasks());
    }

    public void SaveProject(Project proj) {
        if (proj != null) {
            new Save(proj).execute();
        }
    }

    public void Quit() {
        Platform.exit();
    }

    public void CreateDialogPreference(Stage stageParent) {
        Stage stage = new Stage();
        makeDialog(
                new DialogPreference(stage, stageParent),
                ManagerLanguage.getInstance().getLocalizedTexte("Preference"),
                stage,
                stageParent
        );
    }

    public void CreateDialogConfirmationSave(Stage stageParent) {
        if (ProjectDAO.getInstance().getCurrentProject() != null && !ProjectDAO.getInstance().getCurrentProject().getState().isSave()) {
            Stage stage = new Stage();
            makeDialog(
                    new DialogConfirmationSave(stage, stageParent, 0, null),
                    ManagerLanguage.getInstance().getLocalizedTexte("Confirmation"),
                    stage,
                    stageParent
            );
        } else {
            Quit();
        }

    }

    public void CreateDialogConfirmationSaveButNotQuit(Stage stageParent, String tags) {
        if (ProjectDAO.getInstance().getCurrentProject() != null && !ProjectDAO.getInstance().getCurrentProject().getState().isSave()) {
            Stage stage = new Stage();
            makeDialog(
                    new DialogConfirmationSave(stage, stageParent, 1, tags),
                    ManagerLanguage.getInstance().getLocalizedTexte("Confirmation"),
                    stage,
                    stageParent
            );
        }

    }

    public void CreateDialogUpdateTask(Task task, Stage stageParent) {
        Stage stage = new Stage();
        makeDialog(
                new DialogUpdateTask(stage, stageParent),
                ManagerLanguage.getInstance().getLocalizedTexte("UpdateTask") + " - " + task.getName(),
                stage,
                stageParent
        );
    }

    public void CreateDialogSaveProjectAs(Stage stageParent) {
        String path = createFileChooser(stageParent);
        ManageExportImportXML exp = new ManageExportImportXML();
        exp.export(ProjectDAO.getInstance().getCurrentProject(), path);
    }

    public void CreateDialogSaveProject(Stage stageParent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        File file = fileChooser.showOpenDialog(stageParent);
        ManageExportImportXML exp = new ManageExportImportXML();
        Project p = exp.lecture(file.getAbsolutePath());
        OpenProject(p, null, stageParent);
    }

    public void CreateDialogOpenProject(Stage stageParent) {
        if (ProjectDAO.getInstance().getCurrentProject() != null && !ProjectDAO.getInstance().getCurrentProject().getState().isSave()) {
            CreateDialogConfirmationSaveButNotQuit(stageParent, Tags.OPEN_PROJECT);
        } else {
            Stage stage = new Stage();
            makeDialog(
                    new DialogOpenProject(stage, stageParent),
                    ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"),
                    stage,
                    stageParent
            );
        }
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

    public String createFileChooser(Stage mainstage) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        File selectedDirectory = chooser.showDialog(mainstage);
        return selectedDirectory.getAbsolutePath();
    }

    public void CreateDialogProject(Stage stageParent) {
        if (ProjectDAO.getInstance().getCurrentProject() != null && !ProjectDAO.getInstance().getCurrentProject().getState().isSave()) {
            CreateDialogConfirmationSaveButNotQuit(stageParent, Tags.NEW);
        } else {
            Stage stage = new Stage();
            makeDialog(
                    new DialogCreateProject(stage, stageParent),
                    ManagerLanguage.getInstance().getLocalizedTexte("NewProject"),
                    stage,
                    stageParent
            );
        }
    }

}
