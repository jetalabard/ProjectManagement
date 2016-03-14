/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.List;
import javafx.concurrent.Task;
import projectmanagement.application.business.Project;

/**
 *
 * @author Jérémy
 */
interface IDAOTask {
    public List<Task> getAllTasks();
    public Task getTask(int id);
    public void updateTask(int id);
    public void deleteTask(int id);
}
