/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.util.List;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
interface IDAOTask {

    public List<Task> getAllTasks();

    public Task getTask(int id);

    public void updateTask(int id,String name,int id_project,MyDate date_Begin,MyDate date_End,int priority,String note);

    public void deleteTask(int id);

    public List<Task> getAllTasksByIdProject(int idProject);

    public List<Integer> getAllIdTask(int idTask) ;
    
    public List<Predecessor> getAllPredecessorByIdTask(int idTask);

    public List<Ressource> getAllRessourceByIdTask(int idTask);

    public List<Task> getAllInformationProject(int idProject);

    public int insertTask(String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note);

    public int insertPredecessor(String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent);

    public void updatePredecessor(Integer id, String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent);

    public void deleteRessource(int id);

    public void deletePredecessor(int id);
    
     public void updateRessource(Integer id, Integer idTask, float cost, String name, String firstname, String role, String reference,int type);

    public int insertRessource(Integer idTask, float cost, String name, String firstname, String role, String reference,int type) ;

}
