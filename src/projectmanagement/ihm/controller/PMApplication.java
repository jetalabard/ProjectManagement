/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.animation.FadeTransition;
import projectmanagement.ihm.view.SplashScreen;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import projectmanagement.application.dataloader.Database;
import projectmanagement.application.model.LoaderImage;

/**
 *
 * @author Jérémy
 */
public class PMApplication extends Application {
    
    private SplashScreen splash;
    public final static String SPLASH_IMAGE = LoaderImage.getImage("logo_PM_2.png");

    @Override
    public void init() throws Exception {
        splash = new SplashScreen();
    }

    
    @Override
    public void start(Stage primaryStage) throws Exception {
       asyncTask(primaryStage);
       primaryStage.getIcons().add(new Image(SPLASH_IMAGE));
    }
    
    /**
     * methode asynchrone gérant la barre de progression et qui lance la page d'accueil
     * @param primaryStage 
     */
     private void asyncTask(Stage primaryStage) {
        final Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws InterruptedException {
                Database.getInstance();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(100);
                    updateProgress(i + 1, 10);
                }
                return 0;
            }
        };

        showSplash(
                primaryStage,
                task,
                () -> HomeController.showHome()
        );
        new Thread(task).start();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
   
    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {

        splash.getProgressBar().progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                splash.getProgressBar().progressProperty().unbind();
                splash.getProgressBar().setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.8), splash);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                initCompletionHandler.complete();
            }
        });

        Scene scene = new Scene(splash);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.centerOnScreen();
        initStage.setScene(scene);
        initStage.show();
    }

    public interface InitCompletionHandler {
        public void complete();
    }
}
