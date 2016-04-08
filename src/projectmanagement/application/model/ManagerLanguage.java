/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ManagerLanguage {

    /**
     *
     */
    private static ManagerLanguage instance = null;
    /**
     *
     */
    private Locale localeCourante = null;
    /**
     *
     */
    private ResourceBundle bundle = null;

    /**
     *
     */
    private ManagerLanguage() {
        changerLangue("fr", "FR");
    }

    /**
     *
     * @return
     */
    public static ManagerLanguage getInstance() {
        if (instance == null) {
            instance = new ManagerLanguage();
        }
        return instance;
    }

    /**
     *
     * @param clef
     * @return
     */
    public String getLocalizedTexte(String clef) {
        try {
            return bundle.getString(clef);
        } catch (MissingResourceException e) {
            return "";
        }
    }

    /**
     *
     * @param langue
     * @param pays
     */
    public void changerLangue(String langue, String pays) {
        localeCourante = new Locale(langue, pays);
        bundle = ResourceBundle.getBundle("_filelanguage.PM", localeCourante);
    }

    /**
     *
     * @return
     */
    public Locale getLocaleCourante() {
        return localeCourante;
    }
}
