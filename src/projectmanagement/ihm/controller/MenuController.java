/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 *
 * @author Jérémy
 */
public class MenuController extends Controller  implements EventHandler<ActionEvent>{
    private String what;
    private String where;
    private Stage stage;

    public MenuController() {
        this.what="";
        this.where = "";
    }

    public MenuController(String REDIRECTION, String where,Stage mainStage) {
        this.what = REDIRECTION;
        this.where = where;
        this.stage = mainStage;
    }

    
    
    @Override
    public void handle(ActionEvent event) {
        switch (this.what) {
            case Tags.REDIRECTION:
                redirection();
                break;
            case Tags.PROJECT:
                project(event);
                break;
            default:
                break;
        }
    }

    private void project(ActionEvent event) {
        switch (this.where) {
            case Tags.NEW:
                CreateProject(stage);
                break;
            case Tags.OPEN:
                break;
            case Tags.SAVE:
                break;
            case Tags.SAVEAS:
                break;
            default:
                break;
        }
    }

    private void redirection() {
        if(this.where.equals(Tags.FACEBOOK)){
            redirectTo(Tags.FACEBOOK_URL);
        }
        if(this.where.equals(Tags.TWITTER)){
            redirectTo(Tags.TWITTER_URL);
        }
    }
    
}
