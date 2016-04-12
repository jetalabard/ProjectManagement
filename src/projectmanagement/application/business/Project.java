/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class Project {
    private int id;
    
    private Diagram pert;
    
    private Diagram Gantt;
    
    private List<Task> tasks = null;
    
    private String title;
    
    private MyDate lastUse = null;
    
    private State state = null;
    
    public Project(int id,String title,MyDate lastUse){
        this.id = id;
        this.title = title;
        this.lastUse = lastUse;
        state = new StateSave();
        tasks = new ArrayList<Task>();
    }
    

    public String getTitle() {
        return this.title;
    }

    public int getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public MyDate getLastUse() {
        return lastUse;
    }

    @Override
    public String toString() {
        return title;
    }

    public State getState() {
        return state;
    }

    public void changeState() {
        if(this.state.isSave()){
            this.state = new StateNotSave();
        }
        else{
            this.state = new StateSave();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
