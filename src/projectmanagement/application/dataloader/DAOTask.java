/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;

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
        List<Task> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from Task;");
            while (result.next()) {
                list.add(new Task(result.getInt(Tags.ID), result.getString(Tags.NAME), 
                        MyDate.valueOf(result.getString(Tags.DATE_BEGIN)), MyDate.valueOf(result.getString(Tags.DATE_END))
                        ,result.getInt(Tags.PRIORITY) , result.getString(Tags.NOTE),result.getInt(Tags.ID_PROJECT)));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return list;
    }
    
    @Override
    public List<Task> getAllTasksByIdProject(int idProject) {
        List<Task> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from Task where "+Tags.ID_PROJECT+"="+idProject+";");
            while (result.next()) {
                list.add(new Task(result.getInt(Tags.ID), result.getString(Tags.NAME), 
                        MyDate.valueOf(result.getString(Tags.DATE_BEGIN)), MyDate.valueOf(result.getString(Tags.DATE_END))
                        ,result.getInt(Tags.PRIORITY) , result.getString(Tags.NOTE),idProject));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return list;
    }
    
    @Override
    public List<Ressource> getAllRessourceByIdTask(int idTask) {
        List<Ressource> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from RESSOURCE where "+Tags.ID_TASK+"="+idTask+";");
            while (result.next()) {
                if(result.getInt(Tags.TYPE) == 0){
                    list.add(new Human(result.getInt(Tags.ID), result.getFloat(Tags.COST), 
                            result.getString(Tags.NAME), result.getString(Tags.FIRSTNAME), result.getString(Tags.ROLE),idTask));
                }
                else{
                     list.add(new Equipment(result.getInt(Tags.ID), result.getFloat(Tags.COST), result.getString(Tags.REFERENCE), result.getString(Tags.NAME),idTask));
                }
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return list;
    }
    
    @Override
    public List<Predecessor> getAllPredecessorByIdTask(int idTask) {
        List<Predecessor> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from PREDECESSOR where "+Tags.ID_TASK+"="+idTask+";");
            while (result.next()) {
                list.add(new Predecessor(result.getInt(Tags.ID),result.getString(Tags.TYPE) ,
                        MyDate.valueOf(result.getString(Tags.GAP)),result.getString(Tags.CONSTRAINT),result.getInt(Tags.ID_TASK),idTask ));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return list;
    }

    @Override
    public Task getTask(int id) {
        Task p = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from Task where "+Tags.ID+"=" + id+";");
            result.first();
            p = new Task(result.getInt(Tags.ID), result.getString(Tags.NAME), 
                        MyDate.valueOf(result.getString(Tags.DATE_BEGIN)), MyDate.valueOf(result.getString(Tags.DATE_END))
                        ,result.getInt(Tags.PRIORITY) , result.getString(Tags.NOTE),result.getInt(Tags.ID_PROJECT));
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return p;
    }
    
    
    @Override
    public List<Task> getAllInformationProject(int idProject) {
        List<Task> tasks = getAllTasksByIdProject(idProject);
        for(Task t: tasks){
            t.setPredecessor(getAllPredecessorByIdTask(t.getId()));
            t.setRessources(getAllRessourceByIdTask(t.getId()));
        }
        return tasks;
    }
    

    @Override
    public void updateTask(int id,String name,int id_project,MyDate date_Begin,MyDate date_End,int priority,String note) {
        Database.getInstance().update("UPDATE Task set "+Tags.NAME+"='"+name+"', "
                +""+Tags.ID_PROJECT+"="+id_project+", "
                +""+Tags.DATE_BEGIN+"='"+MyDate.valueOf(date_Begin)+"', "
                +""+Tags.DATE_END+"='"+MyDate.valueOf(date_End)+"', "
                +""+Tags.PRIORITY+"="+priority+", "
                +""+Tags.NOTE+"='"+note+"' "
                + "WHERE "+Tags.ID+"=" + id + ";");
    }

    @Override
    public void deleteTask(int id) {
        Database.getInstance().delete("DELETE from Task where "+Tags.ID+"=" + id + ";");
        Database.getInstance().delete("DELETE from PREDECESSOR where "+Tags.ID_TASK+"=" + id + ";");
        Database.getInstance().delete("DELETE from RESSOURCE where "+Tags.ID_TASK+"=" + id + ";");
    }

    @Override
    public void insertTask(String name,int id_project,MyDate date_Begin,MyDate date_End,int priority,String note) {
        Database.getInstance().insert("INSERT INTO Task ("+Tags.NAME+","+Tags.ID_PROJECT+","+Tags.DATE_BEGIN+","+Tags.DATE_END+","+Tags.PRIORITY+","+Tags.NOTE+") "
                + "VALUES('"+name+"',"+id_project+",'"+MyDate.valueOf(date_Begin)
                +"','"+MyDate.valueOf(date_End)+"',"+priority+",'"+note+"');");
    }

    @Override
    public void insertPredecessor(String type, MyDate gap, String constraint, Integer idTask,Integer idTaskParent) {
        Database.getInstance().insert("INSERT INTO PREDECESSOR ("+Tags.TYPE+","+Tags.ID_TASK+","+Tags.GAP+","+Tags.ID_TASK_PARENT+","+Tags.CONSTRAINT+") "
                + "VALUES('"+type+"',"+idTask+",'"+MyDate.valueOf(gap)
                +"',"+idTaskParent+",'"+constraint+"');");
    }
    @Override
    public void updatePredecessor(Integer id, String type, MyDate gap, String constraint, Integer idTask,Integer idTaskParent) {
        Database.getInstance().update("UPDATE PREDECESSOR set "+Tags.TYPE+"='"+type+"', "
                +""+Tags.ID_TASK+"="+idTask+", "
                +""+Tags.GAP+"='"+MyDate.valueOf(gap)+"', "
                +""+Tags.ID_TASK_PARENT+"="+idTaskParent+", "
                +""+Tags.CONSTRAINT+"='"+constraint+"' "
                + "WHERE "+Tags.ID+"=" + id + ";");
    }

    public void updateRessource(Integer id, Integer idTask, float cost, String name, String firstname, String role, String reference,int type) {
        Database.getInstance().update("UPDATE RESSOURCE set "+Tags.TYPE+"="+type+", "
                +""+Tags.COST+"="+cost+", "
                +""+Tags.ID_TASK+"=="+idTask+", "
                +""+Tags.NAME+"='"+name+"', "
                +""+Tags.FIRSTNAME+"='"+firstname+"', "
                +""+Tags.ROLE+"='"+role+"', "
                +""+Tags.REFERENCE+"='"+reference+"' "
                + "WHERE "+Tags.ID+"=" + id + ";");
    }

    public void insertRessource(Integer idTask, float cost, String name, String firstname, String role, String reference,int type) 
    {
        Database.getInstance().insert("INSERT INTO RESSOURCE ("+Tags.TYPE+","+Tags.COST+","+Tags.ID_TASK+","+Tags.NAME+","+Tags.FIRSTNAME+","+Tags.ROLE+","+Tags.REFERENCE+") "
                + "VALUES("+type+","+cost+","+idTask
                +",'"+name+"','"+firstname+"','"+role+"','"+reference+"');");
    }

}
