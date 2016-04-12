/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.ArrayList;
import java.util.List;
import projectmanagement.application.business.Task;

/**
 *
 * @author Jérémy
 */
public class ManageUndoRedo 
{
    private List<List<Task>> listTasks = null;
    
    private int indexCurrentList=0;
    
    private static ManageUndoRedo instance = null;
    
    public static ManageUndoRedo getInstance(){
        if(instance ==null){
            instance = new ManageUndoRedo();
        }
        return instance;
    }

    private ManageUndoRedo() {
        listTasks = new ArrayList<>();
    }
   
    
    public void add(List<Task> tasks){
        listTasks.add(tasks);
    }
    
    public void undo(){
        if(indexCurrentList-1 >=0){
            indexCurrentList--;
        }
    }
    
    public void redo(){
        if(indexCurrentList+1 <listTasks.size()){
            indexCurrentList++;
        }
    }
    
    public List<Task> getCurrentList(){
        return listTasks.get(indexCurrentList);
    }
    
    
    
    
    
}
