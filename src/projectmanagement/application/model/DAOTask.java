/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author Jérémy
 */
public class DAOTask implements IDAOTask{
    
    private static DAOTask instance = null;

    private DAOTask() {
    }
    public static DAOTask getInstance(){
        if(instance == null){
            instance = new DAOTask();
        }
        return instance;
    }
    

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public Task getTask(int id) {
        return null;
    }

    @Override
    public void updateTask(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTask(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
