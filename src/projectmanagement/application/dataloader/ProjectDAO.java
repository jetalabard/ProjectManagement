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
import projectmanagement.application.business.Project;
import projectmanagement.application.business.StateSave;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class ProjectDAO  {
    
   
    public Project getProject(int id) {
        Project p = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from PROJECT where "+Tags.ID+"=" + id+";");
            while (result.next()) {
                p = new Project(result.getInt(Tags.ID), result.getString(Tags.NAME),MyDate.valueOf(result.getString(Tags.LASTUSE)));
            }
             result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return p;

    }
    
    public List<Project> getAllProject() {
        List<Project> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from PROJECT;");
            while (result.next()) {
                list.add(new Project(result.getInt(Tags.ID), result.getString(Tags.NAME), MyDate.valueOf(result.getString(Tags.LASTUSE))));
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
    
   
    public Project insertProject(String name,MyDate lastUse) {
        int id = Database.getInstance().insert("INSERT INTO PROJECT ("+Tags.NAME+","+Tags.LASTUSE+") VALUES('"+ name +"','"+MyDate.valueOf(lastUse)+"');");
        return getProject(id);
    }

    public Project updateProject(int id, String name,MyDate lastUse) {
        Database.getInstance().update("UPDATE PROJECT set "+Tags.NAME+"='" + name + "', "+Tags.LASTUSE+"='" + MyDate.valueOf(lastUse) + "' WHERE "+Tags.ID+"=" + id + ";");
        return new Project(id,name, lastUse);
    }

    void deleteAll() {
        Database.getInstance().delete("DELETE from Project;");
    }

  

}
