/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projectmanagement.ihm.controller.ClickController;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public abstract class Dialog extends VBox
{
    public HBox createLignDialog(String text,TextField textField) {
        HBox box1 = new HBox();
        Label lab = new Label(text);
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        textField = new TextField();
        box1.getChildren().addAll(lab, textField);
        return box1;
    }

    public HBox createLignDialogBrowse(String text, Stage mainStage,TextField textField) {
        HBox box1 = new HBox();
        Label lab = new Label(text);
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        textField = new TextField();

        Button b = new Button("...");
        b.setOnAction(new ClickController(Tags.BROWSE_FILE_TO_CREATE_PROJECT, mainStage,textField));
        box1.getChildren().addAll(lab, textField, b);
        return box1;
    }
    
    public HBox createLignDialogButtonValidation(String localizedTexte, String localizedTexte0,Stage stage,TextField name, TextField path) {
        HBox box1 = new HBox();
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);

        Button bCreate = new Button(localizedTexte);
        Button bClose = new Button(localizedTexte);
        bCreate.setOnAction(new ClickController(Tags.CREATE_PROJECT, stage,name,path));
        bClose.setOnAction(new ClickController(Tags.CLOSE_DIALOG, stage));
        box1.getChildren().addAll(bCreate, bClose);
        return box1;
    }
    
    public HBox createHeaderDialog(String image,String text) {
        HBox header = new HBox();
        header.setPadding(new Insets(15, 12, 15, 12));
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);
        ImageView open = new ImageView(image);
        Text t2 = new Text();
        t2.setText(text);
        header.getChildren().addAll(t2, open);
        header.getStyleClass().add("header");
        return header;
    }
}
