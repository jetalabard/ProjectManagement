/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectmanagement.application.model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import projectmanagement.application.business.Task;

/**
 *
 * @author Mahon--Puget
 */
public class TaskPert extends AnchorPane{
    
    private final Color criticalobjet;
    private final Color criticaltext;
    private final Color normalobjet;
    private final Color normaltext;

    public TaskPert(Task task,int width,int height,boolean critique) {
        criticalobjet=DAO.getInstance().getOBJECT_CRITICAL_PERT();
        criticaltext=DAO.getInstance().getTEXT_CRITICAL_PERT();
        normalobjet = DAO.getInstance().getOBJECT_PERT();
        normaltext= DAO.getInstance().getTEXT_PERT();
        Color colortext;
        if(critique==true){
            colortext=criticaltext;
        }else{
            colortext=normaltext;
        }
        this.setWidth(width);
        this.setHeight(height);
        Rectangle r =new Rectangle();
        r.setHeight(height);
        r.setWidth(width);
        r.setX(0);
        r.setY(0);
        if(critique==true){
           r.setFill(criticalobjet); 
        }else{
            r.setFill(normalobjet);
        }
        r.setStroke(Color.BLACK);
        r.setArcHeight(height/10);
        r.setArcWidth(width/10);
        this.getChildren().add(r);
        Line l=new Line();
        l.setStartX(0);
        l.setStartY(height/3);
        l.setEndX(width);
        l.setEndY(height/3);
        l.setFill(colortext);
        this.getChildren().add(l);
        l=new Line();
        l.setStartX(0);
        l.setStartY(height*2/3);
        l.setEndX(width);
        l.setEndY(height*2/3);
        l.setFill(colortext);
        this.getChildren().add(l);
        l=new Line();
        l.setStartX(width/2);
        l.setStartY(height*2/3);
        l.setEndX(width/2);
        l.setEndY(height);
        l.setFill(colortext);
        this.getChildren().add(l);
        Text t = new Text();
        t.setText(task.getName());
        t.setX(width/2-(task.getName().length()*height/10)/2);
        t.setY(height/3-height/12);
        t.setStyle("-fx-font: "+height/5+" arial;");
        t.setFill(colortext);
        this.getChildren().add(t);
        t = new Text();
        String text=Long.toString(MyDate.diffDays(task.getDatebegin(), task.getLastdate()));
        t.setText(text);
        t.setX(width/2-(text.length()*height/5)/3.5);
        t.setY(height*2/3-height/12);
        t.setStyle("-fx-font: "+height/5+" arial;");
        t.setFill(colortext);
        this.getChildren().add(t);
        t = new Text();
        text=task.getDatebegin().toString();
        t.setText(text);
        t.setX(width/4-(text.length()*height/5)/5);
        t.setY(height-height/10);
        t.setStyle("-fx-font: "+height/7+" arial;");
        t.setFill(colortext);
        this.getChildren().add(t);
        t = new Text();
        text=task.getLastdate().toString();
        t.setText(text);
        t.setX(width*3/4-(text.length()*height/5)/5);
        t.setY(height-height/10);
        t.setStyle("-fx-font: "+height/7+" arial;");
        t.setFill(colortext);
        this.getChildren().add(t);
    }
    
    
    
    
    
    
}
