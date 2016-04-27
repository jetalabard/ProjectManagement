/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class DAO_SQL extends DAO{
    
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;
    private final RessourceDAO ressourceDAO;
    private final PredecessorDAO predecessorDAO;

    public DAO_SQL() {
        super();
        this.projectDAO = new ProjectDAO();
        this.taskDAO = new TaskDAO();
        this.ressourceDAO = new RessourceDAO();
        this.predecessorDAO = new PredecessorDAO();
    }

    
    @Override
    public List<Task> getAllInformationProject(int idProject) {
        List<Task> tasks = getAllTasksByIdProject(idProject);
        for (int i=0; i< tasks.size();i++){
            tasks.get(i).setPredecessor(getAllPredecessorByIdTask(tasks.get(i).getId()));
            tasks.get(i).setRessources(getAllRessourceByIdTask(tasks.get(i).getId()));
        }
        return tasks;
    }
    
    @Override
      public void removeProject(int id) {
        Database.getInstance().delete("DELETE from PROJECT where "+Tags.ID+"=" + id + ";");
        List<Task> tasks =  getAllTasksByIdProject(id);
        for(Task t:tasks){
            deleteTask(t.getId());
        }
        
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @Override
    public Task getTask(int id) {
        Task t = taskDAO.getTask(id);
        t.setPredecessor(getAllPredecessorByIdTask(id));
        t.setRessources(getAllRessourceByIdTask(id));
        return t;
    }

    @Override
    public void updateTask(int id, String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note) {
        taskDAO.updateTask(id, name, id_project, date_Begin, date_End, priority, note);
    }

    @Override
    public void deleteTask(int id) {
        taskDAO.deleteTask(id);
    }

    @Override
    public List<Task> getAllTasksByIdProject(int idProject) {
        return taskDAO.getAllTasksByIdProject(idProject);
    }

    @Override
    public List<Integer> getAllIdTask(int idProject) {
        return taskDAO.getAllIdTask(idProject);
    }

    @Override
    public List<Predecessor> getAllPredecessorByIdTask(int idTask) {
        return predecessorDAO.getAllPredecessorByIdTask(idTask);
    }

    @Override
    public List<Ressource> getAllRessourceByIdTask(int idTask) {
        return ressourceDAO.getAllRessourceByIdTask(idTask);
    }

    @Override
    public int insertTask(String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note) {
        return taskDAO.insertTask(name, id_project, date_Begin, date_End, priority, note);
    }

    @Override
    public int insertPredecessor(String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent) {
        return predecessorDAO.insertPredecessor(type, gap, constraint, idTask, idTaskParent);
    }

    @Override
    public void updatePredecessor(Integer id, String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent) {
        predecessorDAO.updatePredecessor(id, type, gap, constraint, idTask, idTaskParent);
    }

    @Override
    public void deleteRessource(int id) {
        ressourceDAO.deleteRessource(id);
    }

    @Override
    public void deletePredecessor(int id) {
        predecessorDAO.deletePredecessor(id);
    }

    @Override
    public void updateRessource(Integer id, Integer idTask, float cost, String name, String firstname, String role, String reference, int type) {
        ressourceDAO.updateRessource(id, idTask, cost, name, firstname, role, reference, type);
    }

    @Override
    public int insertRessource(Integer idTask, float cost, String name, String firstname, String role, String reference, int type) {
          return ressourceDAO.insertRessource(idTask, cost, name, firstname, role, reference, type);
    }

    @Override
    public Project getProject(int id) {
        Project proj= projectDAO.getProject(id);
        if(proj!=null){
            proj.setTasks(getAllTasksByIdProject(id));
            for(Task t:proj.getTasks()){
                t.setRessources(getAllRessourceByIdTask(t.getId()));
                t.setPredecessor(getAllPredecessorByIdTask(t.getId()));
            }
        }
        return proj;
    }

    @Override
    public List<Project> getAllProject() {
        return projectDAO.getAllProject();
    }

    @Override
    public Project updateProject(int id, String name, MyDate lastUse) {
        return projectDAO.updateProject(id, name, lastUse);
    }

    @Override
    public Project insertProject(String name, MyDate lastUse) {
        return projectDAO.insertProject(name, lastUse);
    }

    @Override
    public Predecessor getPredecessor(int id) {
        return predecessorDAO.getPredecessor(id);
    }

    @Override
    public Ressource getRessource(int id) {
        return ressourceDAO.getRessource(id);
    }

    @Override
    public void deleteAll() {
        ressourceDAO.deleteAll();
        predecessorDAO.deleteAll();
        taskDAO.deleteAll();
        projectDAO.deleteAll();
    }

}

