/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;

/**
 *
 * @author Jérémy
 */
public class SplashScreen extends BorderPane {

    private ProgressBar loadProgress;
    private final String SPLASH_IMAGE = "/ressources/logo_PM_2.png";

    public SplashScreen() {
        super();
        createView();
    }

    public ProgressBar getProgressBar() {
        return this.loadProgress;
    }

    private void createView() {
        setPrefHeight(200);
        setPrefWidth(300);

        //add some borders to visualise the element' locations
        setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null)));

        setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
            //(ii) set the scene fill to transparent
        //(iii) set the stage background to transparent

        ImageView splash = new ImageView(new Image(
                SPLASH_IMAGE
        ));
        getStylesheets().add(
                getClass().getResource(
                        "/ressources/splash.css"
                ).toExternalForm()
        );
        splash.setFitHeight(200);
        splash.setPreserveRatio(true);
        this.setCenter(splash);
        BorderPane.setAlignment(splash, Pos.TOP_CENTER);
        loadProgress = new ProgressBar();
        loadProgress.setProgress(10.0);
        loadProgress.setPrefWidth(300);
        this.setBottom(loadProgress);
        BorderPane.setAlignment(loadProgress, Pos.BOTTOM_CENTER);
        setEffect(new DropShadow());
    }

}
