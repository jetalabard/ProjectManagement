/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projectmanagement.ihm.controller.Controller;

/**
 *
 * @author Jérémy
 */
public abstract class Dialog extends VBox {

    private final Stage stage;
    private final Stage stageParent;
    private final ManagerLanguage managerLang;

    public Dialog(Stage dialog, Stage stageParent,int type) {
        this.stage = dialog;
        this.stageParent = stageParent;
        managerLang = ManagerLanguage.getInstance();
        if(type == 0){
            createDialog();
        }
    }
    
    public ManagerLanguage getManagerLang() {
        return managerLang;
    }
    
    public abstract void createDialog();

    public Stage getStage() {
        return stage;
    }

    public Stage getStageParent() {
        return stageParent;
    }

    
    /**
     * retourne une ligne composé d'un label et d'un textfield
     * Le textfield doit être initialisé au préalable
     * @param text
     * @param textField
     * @return 
     */
    public HBox createLignDialog(String text, Node textField) {
        HBox box1 = new HBox();
        Label lab = new Label(text);
        box1.setAlignment(Pos.CENTER);
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        box1.getChildren().addAll(lab, textField);
        return box1;
    }
    
    /**
     * créé une ligne avec un label et une combobox remplit par le nom des projets
     * la combo box doit être initilisé au préalable
     * @param text
     * @param comboBox
     * @return 
     */
    public HBox createLignDialogComboBox(String text, ComboBox comboBox) {
        HBox box1 = new HBox();
        Label lab = new Label(text);
        box1.setAlignment(Pos.CENTER);
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        DAO.getInstance().getAllProject().stream().forEach((p) -> {
            comboBox.getItems().add(p);
        });
        comboBox.setValue(DAO.getInstance().getAllProject().get(0));
        box1.getChildren().addAll(lab, comboBox);
        return box1;
    }
    
     public HBox createHeaderDialogChooseColor(String localizedTexte, ColorPicker colorPicker) {
         HBox box1 = new HBox();
        Label lab = new Label(localizedTexte);
        box1.setAlignment(Pos.CENTER);
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        box1.getChildren().addAll(lab, colorPicker);
        return box1;
    }
    

    public HBox createLignDialogButtonValidation(String localizedTexte, String localizedTexte2
            ,Controller ctrlOK,Controller ctrlClose) {
        HBox box1 = new HBox();
        box1.setPadding(new Insets(15, 12, 15, 12));
        box1.setSpacing(10);
        box1.setAlignment(Pos.CENTER);
        Button bCreate = new Button(localizedTexte);
        Button bClose = new Button(localizedTexte2);
        bCreate.setOnAction((EventHandler<ActionEvent>) ctrlOK);
        bClose.setOnAction((EventHandler<ActionEvent>) ctrlClose);
        box1.getChildren().addAll(bCreate, bClose);
        return box1;
    }

    public HBox createHeaderDialog(String image, String text) {
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
