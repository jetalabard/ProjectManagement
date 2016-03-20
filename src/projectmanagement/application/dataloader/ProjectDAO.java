/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
public class ProjectDAO implements IProjectDAO {

    @Override
    public Project getProject(int id) {
        Project p = null;
        try {
            ResultSet result = Database.getInstance().select("Select * from PROJECT where ID=" + id+";");
            result.first();
            p = new Project(result.getString("PATH"), result.getString("NAME"));
            result.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;

    }

    @Override
    public List<Project> getAllProject() {
        List<Project> list = new ArrayList<>();
        try {
            ResultSet result = null;
            result = Database.getInstance().select("Select * from PROJECT;");
            while (result.next()) {
                list.add(new Project(result.getString("PATH"), result.getString("NAME")));
            }
            result.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void updateProject(int id, String name, String path) {
        Database.getInstance().update("UPDATE PROJECT set NAME=" + name + ", PATH=" + path + " WHERE ID=" + id + ";");
    }

    @Override
    public void removeProject(int id) {
        Database.getInstance().delete("DELETE from PROJECT where ID=" + id + ";");
    }

}
