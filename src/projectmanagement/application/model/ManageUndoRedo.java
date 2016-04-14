/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;
import projectmanagement.ihm.view.MainWindow;

/**
 *
 * @author Jérémy
 */
public class ManageUndoRedo 
{
    private List<List<Task>> listTasks = null;
     private List<List<Task>> listTasksCancel = null;
    
    private static ManageUndoRedo instance = null;
    
    public static ManageUndoRedo getInstance(){
        if(instance ==null){
            instance = new ManageUndoRedo();
        }
        return instance;
    }
    private MainWindow window;
   

    private ManageUndoRedo() {
        listTasks = new ArrayList<>();
        listTasksCancel = new ArrayList<>();
    }
   
    public void add(List<Task> tasks) {
        List<Task> b =  new ArrayList<>(tasks);
        listTasks.add(b);
        listTasksCancel.clear();
    }

    public void undo() {
        if (this.listTasks.size() > 1) {
            this.listTasksCancel.add(this.listTasks.get(this.listTasks.size() - 1));
            this.listTasks.remove(this.listTasks.size() - 1);
            reloadList(this.listTasks.get(this.listTasks.size() - 1));
        }
        
    }

    public void redo() {
        if (!this.listTasksCancel.isEmpty()) {
            this.listTasks.add(this.listTasksCancel.get(this.listTasksCancel.size() - 1));
            this.listTasksCancel.remove(this.listTasksCancel.size() - 1);
            reloadList(this.listTasks.get(listTasks.size()-1));
        }
       
    }

    private void reloadList(List<Task> list){
        ProjectDAO.getInstance().getCurrentProject().setTasks(list);
        window.reloadTable();
        
    }
    
    public void setWindows(MainWindow window) {
        this.window = window;
        add(ProjectDAO.getInstance().getCurrentProject().getTasks());
    }
    
    
    
    
    
    
    
}
