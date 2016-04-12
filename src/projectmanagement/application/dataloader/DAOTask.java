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
            ResultSet result = stmt.executeQuery("SELECT * from Task where IDPROJECT="+idProject+";");
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
            ResultSet result = stmt.executeQuery("SELECT * from RESSOURCE where IDTASK="+idTask+";");
            while (result.next()) {
                if(result.getInt(Tags.TYPE) == 0){
                    list.add(new Human(result.getInt(Tags.ID), result.getFloat(Tags.COST), result.getString(Tags.NAME), result.getString(Tags.FIRSTNAME), result.getString(Tags.ROLE)));
                }
                else{
                     list.add(new Equipment(result.getInt(Tags.ID), result.getFloat(Tags.COST), result.getString(Tags.REFERENCE), result.getString(Tags.NAME)));
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
            ResultSet result = stmt.executeQuery("SELECT * from PREDECESSOR where IDTASK="+idTask+";");
            while (result.next()) {
                list.add(new Predecessor(result.getInt(Tags.ID),result.getString(Tags.TYPE) , MyDate.valueOf(result.getString(Tags.GAP)),result.getString(Tags.CONSTRAINT) ));
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
            ResultSet result = stmt.executeQuery("Select * from Task where ID=" + id+";");
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
        Database.getInstance().update("UPDATE Task set NAME='"+name+"' "
                +"ID_PROJECT="+id_project+" "
                +"DATEB='"+MyDate.valueOf(date_Begin)+"' "
                +"DATEE='"+MyDate.valueOf(date_End)+"' "
                +"PRIORITY="+priority+" "
                +"NOTE='"+note+"' "
                + "WHERE ID=" + id + ";");
    }

    @Override
    public void deleteTask(int id) {
        Database.getInstance().delete("DELETE from Task where ID=" + id + ";");
    }

    @Override
    public void insertTask(String name,int id_project,MyDate date_Begin,MyDate date_End,int priority,String note) {
        Database.getInstance().insert("INSERT INTO Task (NAME,IDPROJECT,DATEB,DATEE,PRIORITY,NOTE) "
                + "VALUES('"+name+"',"+id_project+",'"+MyDate.valueOf(date_Begin)
                +"','"+MyDate.valueOf(date_End)+"',"+priority+",'"+note+"');");
    }

}
