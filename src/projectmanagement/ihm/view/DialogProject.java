/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import projectmanagement.application.model.Dialog;
import projectmanagement.application.model.ManagerLanguage;

/**
 *
 * @author Jérémy
 */
public class DialogProject extends Dialog {
    
    private TextField name;
    private TextField path;
    private ManagerLanguage managerLang;
    private final Stage stage;

    public DialogProject(Stage mainStage) {
        super();
        this.stage = mainStage;
        Style.getStyle("/ressources/dialog.css", this);
        managerLang = ManagerLanguage.getInstance();
        createDialog(mainStage);
        
    }

    private void createDialog(Stage mainStage) {
        HBox header = createHeaderDialog("/ressources/Folder Filled-50.png",managerLang.getLocalizedTexte("TextDialogCreateProject"));

        HBox box1 = createLignDialog(managerLang.getLocalizedTexte("ProjectName"),name);
        HBox box2 = createLignDialogBrowse(managerLang.getLocalizedTexte("ProjectPath"),mainStage,path);
        HBox box3= createLignDialogButtonValidation(managerLang.getLocalizedTexte("Create"),
                managerLang.getLocalizedTexte("Close"),this.stage,name,path);

        this.getChildren().addAll(header, box1, box2);
    }

    

    

    
}
