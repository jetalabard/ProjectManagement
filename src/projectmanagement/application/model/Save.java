/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import projectmanagement.application.business.Project;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.ProjectDAO;

/**
 *
 * @author Jérémy
 */
public class Save {

    private Project currentProject;

    public Save(Project currentProject) {
        this.currentProject = currentProject;
    }

    public void execute() {
        if (!this.currentProject.getState().isSave()) {
            ProjectDAO.getInstance().updateProject(currentProject.getId(), currentProject.getTitle(), MyDate.now());
            if (currentProject.getTasks() != null) {
                for (Task t : currentProject.getTasks()) {
                    // DAOTask.getInstance().updateTask(id);
                }
            }
            currentProject.changeState();
        }

    }

}
