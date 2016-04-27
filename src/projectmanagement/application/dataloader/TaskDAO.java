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
import projectmanagement.application.business.Task;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class TaskDAO{
    
    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from Task;");
            while (result.next()) {
                list.add(new Task(result.getInt(Tags.ID), result.getString(Tags.NAME),
                        MyDate.valueOf(result.getString(Tags.DATE_BEGIN)), MyDate.valueOf(result.getString(Tags.DATE_END)), result.getInt(Tags.PRIORITY), result.getString(Tags.NOTE), result.getInt(Tags.ID_PROJECT)));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return list;
    }
    
    public List<Integer> getAllIdTask(int idProject) 
    {
        List<Integer> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT "+Tags.ID+" from TASK where " + Tags.ID_PROJECT+ "=" + idProject + ";");
            while (result.next()) {
                list.add(result.getInt(Tags.ID));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return list;
    }

    public List<Task> getAllTasksByIdProject(int idProject) {
        List<Task> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from Task where " + Tags.ID_PROJECT + "=" + idProject + ";");
            while (result.next()) {
                list.add(new Task(result.getInt(Tags.ID), result.getString(Tags.NAME),
                        MyDate.valueOf(result.getString(Tags.DATE_BEGIN)), MyDate.valueOf(result.getString(Tags.DATE_END)), result.getInt(Tags.PRIORITY), result.getString(Tags.NOTE), idProject));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return list;
    }

    public Task getTask(int id) {
        Task p = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from Task where " + Tags.ID + "=" + id + ";");
            while (result.next()) {
                p = new Task(result.getInt(Tags.ID), result.getString(Tags.NAME),
                        MyDate.valueOf(result.getString(Tags.DATE_BEGIN)), MyDate.valueOf(result.getString(Tags.DATE_END)), result.getInt(Tags.PRIORITY), result.getString(Tags.NOTE), result.getInt(Tags.ID_PROJECT));
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return p;
    }

    
    public void updateTask(int id, String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note) {
        Database.getInstance().update("UPDATE Task set " + Tags.NAME + "='" + name + "', "
                + "" + Tags.ID_PROJECT + "=" + id_project + ", "
                + "" + Tags.DATE_BEGIN + "='" + MyDate.valueOf(date_Begin) + "', "
                + "" + Tags.DATE_END + "='" + MyDate.valueOf(date_End) + "', "
                + "" + Tags.PRIORITY + "=" + priority + ", "
                + "" + Tags.NOTE + "='" + note + "' "
                + "WHERE " + Tags.ID + "=" + id + ";");
    }

    public void deleteTask(int id) {
        Database.getInstance().delete("DELETE from Task where " + Tags.ID + "=" + id + ";");
        Database.getInstance().delete("DELETE from PREDECESSOR where " + Tags.ID_TASK + "=" + id + ";");
        Database.getInstance().delete("DELETE from RESSOURCE where " + Tags.ID_TASK + "=" + id + ";");
    }

    public int insertTask(String name, int id_project, MyDate date_Begin, MyDate date_End, int priority, String note) {
        return Database.getInstance().insert("INSERT INTO Task (" + Tags.NAME + "," + Tags.ID_PROJECT + "," + Tags.DATE_BEGIN + "," + Tags.DATE_END + "," + Tags.PRIORITY + "," + Tags.NOTE + ") "
                + "VALUES('" + name + "'," + id_project + ",'" + MyDate.valueOf(date_Begin)
                + "','" + MyDate.valueOf(date_End) + "'," + priority + ",'" + note + "');");
    }

    void deleteAll() {
        Database.getInstance().delete("DELETE from Task;");
    }

}
