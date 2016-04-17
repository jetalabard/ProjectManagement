/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.List;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.StateSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.DAOTask;
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
            if (currentProject.getTasks() != null && !currentProject.getTasks().isEmpty()) {
                List tasks = DAOTask.getInstance().getAllTasksByIdProject(currentProject.getId());
                for (Task t : currentProject.getTasks()) {
                    if (tasks != null && !tasks.isEmpty() && tasks.contains(t)) {
                        DAOTask.getInstance().updateTask(t.getId(), t.getName(), t.getIdProject(), t.getDatebegin(), t.getDateend(), t.getPriority(), t.getNote());
                        savePredecessors(t);
                        saveRessources(t);
                    } else {
                        int id = DAOTask.getInstance().insertTask(t.getName(), t.getIdProject(), t.getDatebegin(), t.getDateend(), t.getPriority(), t.getNote());
                        t.setId(id);
                        savePredecessors(t);
                        saveRessources(t);
                    }

                }
            }
            currentProject.setState(new StateSave());
        } 
    }

    private void saveRessources(Task t) {
        if (t.getRessources() != null && !t.getRessources().isEmpty()) {
            List ressources = DAOTask.getInstance().getAllRessourceByIdTask(t.getId());
            for (Ressource ress : t.getRessources()) {
                if (ressources != null && !ressources.isEmpty() && ressources.contains(ress)) {
                    if (ress instanceof Human) {
                        Human h = (Human) ress;
                        DAOTask.getInstance().updateRessource(h.getId(), h.getIdTask(), h.getCost(), h.getName(), h.getFirstname(), h.getRole(), null, 0);
                    } else {
                        Equipment equip = (Equipment) ress;
                        DAOTask.getInstance().updateRessource(equip.getId(), equip.getIdTask(), equip.getCost(), equip.getName(), null, null, equip.getReference(), 1);
                    }

                } else {
                    if (ress instanceof Human) {
                        Human h = (Human) ress;
                        int id = DAOTask.getInstance().insertRessource(t.getId(), h.getCost(), h.getName(), h.getFirstname(), h.getRole(), null, 0);
                        h.setId(id);
                    } else {
                        Equipment equip = (Equipment) ress;
                        int id = DAOTask.getInstance().insertRessource(t.getId(), equip.getCost(), equip.getName(), null, null, equip.getReference(), 1);
                        equip.setId(id);
                    }
                }
            }

        }
    }

    private void savePredecessors(Task t) {
        if (t.getPredecessor() != null && !t.getPredecessor().isEmpty()) {
            List predecessors = DAOTask.getInstance().getAllPredecessorByIdTask(t.getId());
            for (Predecessor pred : t.getPredecessor()) {
                if (predecessors != null && !predecessors.isEmpty() && predecessors.contains(t)) {
                    DAOTask.getInstance().updatePredecessor(pred.getId(), pred.getType(), pred.getGap(), pred.getConstraint(), pred.getIdTask(), t.getId());
                } else {
                    int id = DAOTask.getInstance().insertPredecessor(pred.getType(), pred.getGap(), pred.getConstraint(), pred.getIdTask(), t.getId());
                    pred.setId(id);
                }
            }

        }
    }

}
