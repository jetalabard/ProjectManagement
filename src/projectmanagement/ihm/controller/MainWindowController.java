/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import projectmanagement.application.business.Project;
import projectmanagement.application.model.ManagerLanguage;
import static projectmanagement.ihm.controller.PMApplication.SPLASH_IMAGE;
import projectmanagement.ihm.view.MainWindow;

/**
 *
 * @author Jérémy
 */
public class MainWindowController extends Controller implements EventHandler<ScrollEvent> 
{
    private final Slider slider;

    public MainWindowController(Slider slider) {
        this.slider = slider;
    }
    
    public static void showMainWindow(Project proj) {
        Stage parentStage = new Stage();
        parentStage.setTitle(proj.getTitle() + " - " + ManagerLanguage.getInstance().getLocalizedTexte("AppTitle"));
        parentStage.getIcons().add(new Image(SPLASH_IMAGE));
        MainWindow home = new MainWindow(parentStage);
        Scene mainScene = new Scene(home);
        parentStage.setScene(mainScene);
        parentStage.setMaximized(true);
        parentStage.show();
    }

    @Override
    public void handle(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        if (deltaY < 0) {
            slider.adjustValue(slider.getValue() - 0.25);
        } else {
            slider.adjustValue(slider.getValue() + 0.25);
        }
    }
}
