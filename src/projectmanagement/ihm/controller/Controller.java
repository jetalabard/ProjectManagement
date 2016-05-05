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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.StateSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ExportProject;
import projectmanagement.application.dataloader.ImportProject;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.ManagerShowDiagram;
import projectmanagement.application.model.Save;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.dialog.DialogConfirmationSave;
import projectmanagement.ihm.view.dialog.DialogCreateProject;
import projectmanagement.ihm.view.dialog.DialogOpenProject;
import projectmanagement.ihm.view.dialog.DialogPreference;
import projectmanagement.ihm.view.dialog.DialogUpdateTask;
import projectmanagement.ihm.view.MyPopup;
import projectmanagement.ihm.view.MyTableView;
import projectmanagement.ihm.view.dialog.DialogChangePropertiesProject;
import projectmanagement.ihm.view.dialog.DialogConfirmationDelete;

/**
 *
 * @author Jérémy
 */
public abstract class Controller {
    private final ManagerLanguage managerLang;

    public Controller() {
        managerLang = ManagerLanguage.getInstance();
    }
    
    public ManagerLanguage getManagerLanguage(){
        return ManagerLanguage.getInstance();
    }

    /**
     * ouvre la page principale du logiciel en chargeant
     *
     * @param p
     * @param dialoStage
     * @param parentStage
     */
    public void OpenProject(Project p, Stage dialoStage, Stage parentStage) {
        if (dialoStage != null) {
            dialoStage.close();
        }
        parentStage.close();
        if(DAO.getInstance().countPreference() == 0){
            DAO.getInstance().insertPreferenceByDefault();
        }
        p.setTasks(DAO.getInstance().getAllInformationProject(p.getId()));
        p.setState(new StateSave());
        DAO.getInstance().setCurrentProject(p);
        MainWindowController.showMainWindow(p);
    }

    /**
     * affiche le dialogue pour réinitialiser la base de donnée
     *
     * @param stageParent
     */
    public void deleteAllAndReloadHomePage(Stage stageParent) {
        Stage stage = new Stage();
        makeDialog(
                new DialogConfirmationDelete(stage, stageParent, 1),
                getManagerLanguage().getLocalizedTexte("Confirmation"),
                stage,
                stageParent
        );
    }

    /**
     * retour à la page d'accueil si le projet est sauvegardé
     *
     * @param stageParent
     */
    public void returnHome(Stage stageParent) {
        if (DAO.getInstance().getCurrentProject() != null && !DAO.getInstance().getCurrentProject().getState().isSave()) {
            CreateDialogConfirmationSaveButNotQuit(stageParent, Tags.RETURN_HOME);
        } else {
            stageParent.close();
            HomeController.showHome();
        }

    }
    
    public void CreateFileChooserToExportDiagramToImage(Stage stage){
        Locale.setDefault(ManagerLanguage.getInstance().getLocaleCourante());
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        File selectedDirectory = chooser.showDialog(stage);
        if (selectedDirectory != null) {
            try {
                String name = DAO.getInstance().getCurrentProject().getTitle();
                File file = new File(selectedDirectory.getAbsolutePath()+"/"+name+".png");
                ManagerShowDiagram.getInstance().export(file);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            MyPopup.showPopupMessage(managerLang.getLocalizedTexte("SelectDirectory"), stage);
        }
        
    }
    
   
    public void deleteProjectAndRunHomePage(Stage stageParent) {
        Stage stage = new Stage();
        makeDialog(
                new DialogConfirmationDelete(stage, stageParent, 0),
                getManagerLanguage().getLocalizedTexte("Confirmation"),
                stage,
                stageParent
        );

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

   

    public void SaveProject(Project proj) {
        if (proj != null) 
        {
            DAO.getInstance().setCurrentProject(new Save(proj).execute());
        }
    }

    public void Quit() {
        Platform.exit();
    }

    public void CreateDialogPreference(Stage stageParent) {
        Stage stage = new Stage();
        makeDialog(
                new DialogPreference(stage, stageParent),
                getManagerLanguage().getLocalizedTexte("Preference"),
                stage,
                stageParent
        );
    }

    public void CreateDialogChangeName(Stage stageParent) {
        Stage stage = new Stage();
        makeDialog(
                new DialogChangePropertiesProject(stage, stageParent),
                getManagerLanguage().getLocalizedTexte("Project"),
                stage,
                stageParent
        );
    }

    public void CreateDialogConfirmationSave(Stage stageParent) {
        if (DAO.getInstance().getCurrentProject() != null && !DAO.getInstance().getCurrentProject().getState().isSave()) {
            Stage stage = new Stage();
            makeDialog(
                    new DialogConfirmationSave(stage, stageParent, 0, null),
                    getManagerLanguage().getLocalizedTexte("Confirmation"),
                    stage,
                    stageParent
            );
        } else {
            Quit();
        }

    }

    public void CreateDialogConfirmationSaveButNotQuit(Stage stageParent, String tags) {
        if (DAO.getInstance().getCurrentProject() != null && !DAO.getInstance().getCurrentProject().getState().isSave()) {
            Stage stage = new Stage();
            makeDialog(
                    new DialogConfirmationSave(stage, stageParent, 1, tags),
                   getManagerLanguage().getLocalizedTexte("Confirmation"),
                    stage,
                    stageParent
            );
        }

    }

    public void createDialogCreateOrOpenProjectAndQuitPrecedentProject(String tags, Stage parent) {
        Stage stage = new Stage();
        Dialog dialog;
        if (tags.equals(Tags.NEW)) {
            dialog = new DialogCreateProject(stage, parent);
            stage.setTitle(getManagerLanguage().getLocalizedTexte("NewProject"));
        } else {
            dialog = new DialogOpenProject(stage, parent);
            stage.setTitle(getManagerLanguage().getLocalizedTexte("OpenProject"));
        }
        stage.setScene(new Scene(dialog));
        stage.setResizable(false);
        stage.getIcons().add(new Image(SPLASH_IMAGE));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parent);
        stage.show();
    }

    public void CreateDialogUpdateTask(Task task, Stage stageParent, int index, MyTableView table) {
        
        Stage stage = new Stage();
        DialogUpdateTask update = new DialogUpdateTask(stage, stageParent,task,table,index);
        update.createDialog();
        makeDialog(
                update,
                getManagerLanguage().getLocalizedTexte("UpdateTask") + " - " + task.getName(),
                stage,
                stageParent
        );
    }

    public void CreateDialogSaveProjectAs(Stage stageParent) {
        String path = createFileChooser(stageParent);
        ExportProject exp = new ExportProject();
        exp.export(DAO.getInstance().getCurrentProject(), path);
    }

    public void CreateDialogSaveProject(Stage stageParent) {
        Locale.setDefault(ManagerLanguage.getInstance().getLocaleCourante());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(getManagerLanguage().getLocalizedTexte("OpenProject"));
        File file = fileChooser.showOpenDialog(stageParent);
        ImportProject exp = new ImportProject();
        Project p = exp.lecture(file.getAbsolutePath());
        if (p != null) {
            OpenProject(p, null, stageParent);
        }
    }

    /**
     * Si il y a un projet en cours non sauvegardé on demande à l'utilisateur de la sauvergarder ou non 
     * sinon s'il y a des projets d'enregistré on ouvre le dialogue de saisie d'un projet
     *       sinon on affiche message erreur
     * @param stageParent 
     */
    public void CreateDialogOpenProject(Stage stageParent) {
        if (DAO.getInstance().getCurrentProject() != null && !DAO.getInstance().getCurrentProject().getState().isSave()) {
            CreateDialogConfirmationSaveButNotQuit(stageParent, Tags.OPEN_PROJECT);
        } else {
            if(!DAO.getInstance().getAllProject().isEmpty()){
                Stage stage = new Stage();
                makeDialog(
                        new DialogOpenProject(stage, stageParent),
                        getManagerLanguage().getLocalizedTexte("OpenProject"),
                        stage,
                        stageParent
                );
            }else{
                MyPopup.showPopupMessage(getManagerLanguage().getLocalizedTexte("MessageNoProject"), stageParent);
            }
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
        Locale.setDefault(ManagerLanguage.getInstance().getLocaleCourante());
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(ManagerLanguage.getInstance().getLocalizedTexte("OpenProject"));
        File selectedDirectory = chooser.showDialog(mainstage);
        if (selectedDirectory != null) {
            return selectedDirectory.getAbsolutePath();
        } else {
            return new File(System.getProperty("user.home"), "Desktop").getAbsolutePath();
        }
    }
    
    public void CreateDialogProject(Stage stageParent) {
        if (DAO.getInstance().getCurrentProject() != null && !DAO.getInstance().getCurrentProject().getState().isSave()) {
            CreateDialogConfirmationSaveButNotQuit(stageParent, Tags.NEW);
        } else {
            Stage stage = new Stage();
            makeDialog(
                    new DialogCreateProject(stage, stageParent),
                    getManagerLanguage().getLocalizedTexte("NewProject"),
                    stage,
                    stageParent
            );
        }
    }
    

}
