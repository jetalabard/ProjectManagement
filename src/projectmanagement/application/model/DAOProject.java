/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.ArrayList;
import java.util.List;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
public class DAOProject implements IDAOProject{
    
    private static DAOProject instance = null;

    private DAOProject() {
    }
    public static DAOProject getInstance(){
        if(instance == null){
            instance = new DAOProject();
        }
        return instance;
    }

    @Override
    public List<Project> getAllProjects() {
        Project p1 = new Project("", "", "projet1");
        Project p2 =new Project("", "", "projet2");
        Project p3 =new Project("", "", "projet3");
        Project p4 =new Project("", "", "projet4");
        List<Project> list = new ArrayList<Project>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        return list;
    }

    @Override
    public Project getProject(int id) {
        return null;
    }

    @Override
    public void updateProject(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteProject(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
