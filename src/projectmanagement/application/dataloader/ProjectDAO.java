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
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class ProjectDAO implements IProjectDAO {
    
    private static ProjectDAO instance = null;
    
    private Project currentProject;
    
    private ProjectDAO(){
        
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
    
    
    public static ProjectDAO getInstance(){
        if(instance == null){
            instance = new ProjectDAO();
        }
        return instance;
    } 

    @Override
    public Project getProject(int id) {
        Project p = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from PROJECT where ID=" + id+";");
            result.first();
            p = new Project(result.getInt("ID"), result.getString("NAME"),MyDate.valueOf(result.getString("LASTUSE")));
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
    public List<Project> getAllProject() {
        List<Project> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from PROJECT;");
            while (result.next()) {
                list.add(new Project(result.getInt("ID"), result.getString("NAME"), MyDate.valueOf(result.getString("LASTUSE"))));
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
    public Project insertProject(String name,MyDate lastUse) {
        Database.getInstance().insert("INSERT INTO PROJECT (NAME,LASTUSE) VALUES('"+ name +"','"+MyDate.valueOf(lastUse)+"');");
        return new Project(Database.getInstance().getLastInsertId(),name,lastUse);
    }

    @Override
    public Project updateProject(int id, String name,MyDate lastUse) {
        Database.getInstance().update("UPDATE PROJECT set NAME='" + name + "', LASTUSE='" + MyDate.valueOf(lastUse) + "' WHERE ID=" + id + ";");
        return new Project(id,name, lastUse);
    }

    @Override
    public void removeProject(int id) {
        Database.getInstance().delete("DELETE from PROJECT where ID=" + id + ";");
    }

}
