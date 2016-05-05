/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import javafx.util.StringConverter;

/**
 *
 * @author Jérémy
 */
public class RessourceConverter extends StringConverter<Integer> {

    
    @Override
    public String toString(Integer integer) {
        if (integer == 1) {
            return ManagerLanguage.getInstance().getLocalizedTexte("Equipment");
        } else {
            return ManagerLanguage.getInstance().getLocalizedTexte("Human");
        }
    }

    @Override
    public Integer fromString(String string) {
        if (string.equals(ManagerLanguage.getInstance().getLocalizedTexte("Human"))) {
            return 0;
        } else {
            return 1;
        }
    }

}
