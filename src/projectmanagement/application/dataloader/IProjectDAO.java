/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.util.List;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
public interface IProjectDAO {
    
    public Project getProject(int id);
    public List<Project> getAllProject();
    public void updateProject(int id,String name, String path);
    public void removeProject(int id);
}
