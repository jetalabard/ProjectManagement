/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import javafx.util.StringConverter;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.DAOTask;
import projectmanagement.application.dataloader.ProjectDAO;

/**
 *
 * @author Jérémy
 */
public class PredecessorConverter extends StringConverter<Integer> {
    
     @Override
    public String toString(Integer integer) {
        for (Task t : ProjectDAO.getInstance().getCurrentProject().getTasks()) {
            if (t.getId().equals(integer)) {
                return t.getId()+" - "+ t.getName();
            }
        }
        return null;
    }

    @Override
    public Integer fromString(String string) {
        String[] item = string.split(" - ");
        return DAOTask.getInstance().getTask(Integer.valueOf(item[0])).getId();
    }
}