/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.List;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
interface IDAOProject {
    public List<Project> getAllProjects();
    public Project getProject(int id);
    public void updateProject(int id);
    public void deleteProject(int id);
}
