/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectmanagement.application.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import projectmanagement.application.business.Task;

/**
 *
 * @author Mahon--Puget
 */
public class TaskGantt extends AnchorPane{
    private List<String> listMounth = null;
    private final ManagerLanguage managerLang;
    private List<String> days = null;
    private final int space = 5;
    
    private final Color objet;
    private final Color texte;
    
    public TaskGantt(Task task,int width,int height) {
        objet= DAO.getInstance().getOBJECT_GANTT();
        texte= DAO.getInstance().getTEXT_GANTT();
        managerLang = ManagerLanguage.getInstance();
        listMounth = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            listMounth.add(managerLang.getLocalizedTexte("month" + i));
        }
        days = new ArrayList<>();
        days.add(managerLang.getLocalizedTexte("day"));
        days.add(managerLang.getLocalizedTexte("days"));
        
        this.setWidth(width);
        this.setHeight(height);
        String font="-fx-font: "+height/1.5+" arial;";
        Rectangle taskrect = new Rectangle();
        taskrect.setX(0);
        taskrect.setY(0);
        taskrect.setWidth(width);
        taskrect.setHeight(height);
        taskrect.setFill(objet);
        
        taskrect.setStroke(Color.BLACK);
        taskrect.setArcHeight(10);
        taskrect.setArcWidth(10);
        this.getChildren().add(taskrect);
        Text t = new Text();
        String text;
        if (task.getDuring() > 1) {
            text=task.getName() + " (" + task.getDuring() + " " + days.get(1) + ")";
        } else {
            text=task.getName() + " (" + task.getDuring() + " " + days.get(0) + ")";
        }
        t.setText(text);
        t.setX(3);
        t.setY(height*3/4);
        t.setStyle(font);
        t.setFill(texte);
        this.getChildren().add(t);
        
        
    }
    
    
    
    
    
    
}
