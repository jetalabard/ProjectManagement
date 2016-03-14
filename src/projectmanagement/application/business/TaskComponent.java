/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jérémy
 */
public class TaskComponent {
    
    private List<TaskComponent> taskComposite;
    
    public Date  getDuration(){
        return new Date ();
    }
    
    public void add(TaskComponent task){
        
    }
    
    public void remove(TaskComponent task){
        
    }
    public List<TaskComponent> getChildren(){
        return taskComposite;
    }
}
