/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class Project {
    private Integer id;
    
    private List<Task> tasks = null;
    
    private String title;
    
    private MyDate lastUse = null;
    
    private State state = null;

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }else{
            if(obj instanceof Project){
                Project proj = (Project)obj;
                if(id.equals(proj.getId())){
                    return true;
                }
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    public Project(Integer id,String title,MyDate lastUse){
        this.id = id;
        this.title = title;
        this.lastUse = lastUse;
        state = new StateSave();
        tasks = new ArrayList<Task>();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.state = new StateNotSave();
    }
    
    

    public Integer getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        this.state = new StateNotSave();
    }

    public MyDate getLastUse() {
        return lastUse;
    }

    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder(title);
        sb.append(" ");
        sb.append(id+" "+ lastUse.toString() +"\n");
        for(Task t : this.tasks){
            sb.append(t.toString());
        }
        return sb.toString();
    }

    public State getState() {
        return state;
    }
    
    public void setState(State state){
        this.state = state;
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
