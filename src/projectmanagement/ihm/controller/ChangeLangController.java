package projectmanagement.ihm.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.ihm.view.MenuPM;
import projectmanagement.ihm.view.Page;

public class ChangeLangController implements ChangeListener<Toggle>{

    /**
     *
     */
    private ManagerLanguage managerL = null;
    private ToggleGroup group;
    private Page layout;

    public ChangeLangController(ToggleGroup group, Page layout) {
        this.group = group;
        this.layout = layout;
        managerL = ManagerLanguage.getInstance();
    }

    /**
     *
     */
    private void changerLangue() {
        gereChangementLangue(MenuPM.lang);
        layout.reload();
        
    }

    /**
     *
     * @param nomBouton
     */
    private void gereChangementLangue(String nomBouton) {
        switch (nomBouton) {
            case "fr":
                managerL.changerLangue("fr", "FR");
                break;
            case "en":
                managerL.changerLangue("en", "US");
                break;
            case "es":
                managerL.changerLangue("es", "ES");
                break;
            default:
                managerL.changerLangue("en", "US");
                break;
        }

    }

    @Override
    public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        RadioMenuItem rb = null;
                if (group.getSelectedToggle() != null) {
                    rb = (RadioMenuItem) group.getSelectedToggle();
                    MenuPM.lang = rb.getUserData().toString();
                    changerLangue();
                }
    }

}
