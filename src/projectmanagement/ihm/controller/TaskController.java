/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.controller;

import java.util.Iterator;
import java.util.List;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.ManageUndoRedo;
import projectmanagement.application.model.ManagerLanguage;
import projectmanagement.application.model.ManagerShowDiagram;
import projectmanagement.application.model.MyDate;
import projectmanagement.application.model.Save;
import projectmanagement.ihm.view.MyTableView;

/**
 *
 * @author Jérémy
 */
public class TaskController {

    private final ManagerLanguage managerLang;
    private final MyTableView table;

    public TaskController(MyTableView table) {
        this.table = table;
        managerLang = ManagerLanguage.getInstance();
    }

    public void updateListTaskAfterDragAndDrop(Task task, int index) {
        DAO.getInstance().getCurrentProject().getTasks().remove(task);
        DAO.getInstance().getCurrentProject().getTasks().add(index, task);
        DAO.getInstance().getCurrentProject().setState(new StateNotSave());
        ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
        ManagerShowDiagram.getInstance().getTabDiagram().init();
    }

    public void deleteTask(Task task) {
        if (task.getId() != null) {
            //en base sinon si == null pas encore eu le insert
            DAO.getInstance().deleteTask(task.getId());
        }

        suppressPredecessorreferToTask(task);
        DAO.getInstance().getCurrentProject().getTasks().remove(task);
        DAO.getInstance().getCurrentProject().setState(new StateNotSave());
        ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
        ManagerShowDiagram.getInstance().getTabDiagram().init();
    }

    private void suppressPredecessorreferToTask(Task task) {
        for (Task t : DAO.getInstance().getCurrentProject().getTasks()) {
            Iterator itr = t.getPredecessor().iterator();
            while (itr.hasNext()) {
                Predecessor element = (Predecessor) itr.next();
                if (element.getIdTaskParent().equals(task.getId())) {
                    itr.remove();
                }
            }
        }
    }

    public void pasteTask(Task task) {
        DAO.getInstance().insertTask(task.getName(), task.getIdProject(), task.getDatebegin(), task.getDateend(), task.getPriority(), task.getNote());
        DAO.getInstance().getCurrentProject().getTasks().add(task);
        DAO.getInstance().getCurrentProject().setState(new StateNotSave());
        ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
        ManagerShowDiagram.getInstance().getTabDiagram().init();
    }

    public void updateListTask(List<Task> tasks) {
        DAO.getInstance().getCurrentProject().setTasks(tasks);
        DAO.getInstance().getCurrentProject().setState(new StateNotSave());
        ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
        ManagerShowDiagram.getInstance().getTabDiagram().init();
    }

    public void addTask() {
        Task task = new Task(managerLang.getLocalizedTexte("Name"),
                MyDate.now(), new MyDate(MyDate.now().getTime() + MyDate.DAY), 0, "", DAO.getInstance().getCurrentProject().getId());

        int id = DAO.getInstance().insertTask(task.getName(), task.getIdProject(),
                task.getDatebegin(), task.getDateend(), task.getPriority(), task.getNote());
        task.setId(id);
        addToListIfNecessary(id, task, table.getItems());
        addToListIfNecessary(id, task, DAO.getInstance().getCurrentProject().getTasks());
        ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
        if (table.getItems().size() == 1) {
            table.getSelectionModel().selectFirst();
        }
        ManagerShowDiagram.getInstance().getTabDiagram().init();

    }

    private void addToListIfNecessary(int id, Task task, List<Task> list) {
        boolean ok = true;
        for (Task t : list) {
            if (t.getId().equals(id)) {
                ok = false;
            }
        }
        if (ok) {
            list.add(task);
        }
    }

    public Task changeDateBeginComparedToPredecessor(Task task) {
        for (Predecessor pred : task.getPredecessor()) {
            Task taskPred = DAO.getInstance().getTask(pred.getIdTaskParent());
            if (taskPred != null) {
                if (taskPred.getDateend().after(task.getDatebegin())) {
                    task.setDatebegin(new MyDate(taskPred.getDateend().getTime() + MyDate.DAY));
                    if (task.getDatebegin().after(task.getDateend())) {
                        task.setDateend(new MyDate(task.getDatebegin().getTime() + MyDate.DAY));
                    }
                }
            }
        }
        return task;

    }

    void updateListTaskAfterUpdate(int indexTask, Task task) {
        DAO.getInstance().getCurrentProject().setState(new StateNotSave());
        DAO.getInstance().getCurrentProject().getTasks().set(indexTask, task);
        task = changeDateBeginComparedToPredecessor(task);
        DAO.getInstance().getCurrentProject().getTasks().set(indexTask, task);
        
        ManageUndoRedo.getInstance().add(DAO.getInstance().getCurrentProject().getTasks());
        DAO.getInstance().setCurrentProject(new Save(DAO.getInstance().getCurrentProject()).execute());
        
        ManagerShowDiagram.getInstance().getTabDiagram().init();

    }

    public boolean dateIsValidateByThisPredecessor(Task task, MyDate newValue) {
        Task t = getLastPredecessor(task);
        if (t != null) {
            if (t.getDateend().after(newValue)) {
                return false;
            }
        }
        return true;
    }

    private Task getLastPredecessor(Task task) {
        Task last = null;
        for (Predecessor pred : task.getPredecessor()) {
            if (last != null) {
                Task t1 = DAO.getInstance().getTask(pred.getIdTaskParent());
                if (t1.getDateend().after(last.getDateend())) {
                    last = t1;
                }
            } else {
                last = DAO.getInstance().getTask(pred.getIdTaskParent());
            }
        }
        return last;
    }

    public void dateIsValidateIfItsAnPredecessorOfOtherTask(Task task, MyDate newvalue) {
        for (int i = 0; i < DAO.getInstance().getCurrentProject().getTasks().size(); i++) {
            if (!DAO.getInstance().getCurrentProject().getTasks().get(i).equals(task)) {
                for (Predecessor pred : DAO.getInstance().getCurrentProject().getTasks().get(i).getPredecessor()) {
                    if (pred.getIdTaskParent().equals(task.getId())) {
                        DAO.getInstance().getCurrentProject().getTasks().get(i).setDatebegin(newvalue);
                        if (DAO.getInstance().getCurrentProject().getTasks().get(i).getDatebegin().after(DAO.getInstance().getCurrentProject().getTasks().get(i).getDateend())) {
                            DAO.getInstance().getCurrentProject().getTasks().get(i).setDateend(new MyDate(newvalue.getTime()));
                        }
                        table.reload();
                        ManagerShowDiagram.getInstance().getTabDiagram().init();
                    }

                }
            }
        }

    }
}
