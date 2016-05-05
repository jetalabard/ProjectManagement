/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import javafx.scene.paint.Color;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.DAO_SQL;

/**
 *
 * @author Jérémy
 */
public abstract class DAO {
    
    private Project currentProject;
    
    private static DAO instance = null;

    public final static DAO getInstance() {
        if (instance == null) {
            //enregistrement par défaut = SQL
            instance = new DAO_SQL();
        }
        return instance;
    }
    
    public void setSaveWay(DAO newSAveWay)
    {
        instance = newSAveWay;
    }

     public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
    
    public abstract void deleteAll();
    public abstract List<Task> getAllTasks();

    public abstract Task getTask(int id);

    public abstract Predecessor getPredecessor(int id);

    public abstract Ressource getRessource(int id);

    public abstract void updateTask(int id, String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note);

    public abstract void deleteTask(int id);

    public  abstract List<Task> getAllTasksByIdProject(int idProject);

    public  abstract List<Integer> getAllIdTask(int idProject);

    public  abstract List<Predecessor> getAllPredecessorByIdTask(int idTask);

    public  abstract List<Ressource> getAllRessourceByIdTask(int idTask);

    public  abstract List<Task> getAllInformationProject(int idProject);

    public  abstract int insertTask(String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note);

    public  abstract int insertPredecessor(String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent);

    public  abstract void updatePredecessor(Integer id, String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent);

    public  abstract void deleteRessource(int id);

    public  abstract void deletePredecessor(int id);

    public  abstract void updateRessource(Integer id, Integer idTask, float cost, String name, String firstname, String role, String reference, int type);

    public  abstract int insertRessource(Integer idTask, float cost, String name, String firstname, String role, String reference, int type);

    public  abstract Project getProject(int id);

    public  abstract List<Project> getAllProject();

    public abstract  Project updateProject(int id, String name, MyDate lastUse);

    public abstract  void removeProject(int id);

    public abstract  Project insertProject(String name, MyDate lastUse);
    
     public abstract  void deleteProject(int id);
            
    
    public abstract Color getBACKGROUND_GANTT();
    public abstract Color getOBJECT_GANTT();
    public abstract Color getTEXT_GANTT();
    public abstract Color getBACKGROUND_PERT();
    public abstract Color getOBJECT_PERT();
    public abstract Color getTEXT_PERT();
    public abstract Color getOBJECT_CRITICAL_PERT();
    public abstract Color getTEXT_CRITICAL_PERT();

    public abstract void insertPreference(Color BACKGROUND_GANTT,Color OBJECT_GANTT,
            Color TEXT_GANTT,Color BACKGROUND_PERT,
            Color OBJECT_PERT,Color TEXT_PERT,
            Color OBJECT_CRITICAL_PERT,Color TEXT_CRITICAL_PERT );
    
    public abstract void insertPreferenceByDefault();
    
    public abstract int countPreference();
    
    public abstract void updatePreference(Color BACKGROUND_GANTT,Color OBJECT_GANTT,
            Color TEXT_GANTT,Color BACKGROUND_PERT,
            Color OBJECT_PERT,Color TEXT_PERT,
            Color OBJECT_CRITICAL_PERT,Color TEXT_CRITICAL_PERT );

}

