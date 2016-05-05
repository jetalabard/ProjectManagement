/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.ihm.view.cell;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.ComboBoxTableCell;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.PredecessorConverter;
import projectmanagement.ihm.view.dialog.DialogUpdateTask;

/**
 *
 * @author Jérémy
 */
public class ComboBoxTableCellPredecessor extends ComboBoxTableCell<Predecessor,Integer>
{
    private final DialogUpdateTask dialog;

    public ComboBoxTableCellPredecessor(PredecessorConverter converter, DialogUpdateTask dialog) {
        super(converter);
        this.dialog = dialog;
    }

    @Override
    public void startEdit() {
        super.startEdit(); //To change body of generated methods, choose Tools | Templates.
        createList();
    }

    
    private void createList() {
        ArrayList<Integer> ListRefCurrentTask = new ArrayList<>();
        Task end = DAO.getInstance().getCurrentProject().getEnd();
        parcours(dialog.getTask(), end, ListRefCurrentTask);
        List<Integer> listId = DAO.getInstance().getAllIdTask(dialog.getTask().getIdProject());
        listId.removeAll(ListRefCurrentTask);
        listId.remove(dialog.getTask().getId());
        
        getItems().addAll(FXCollections.observableArrayList(listId));
        if (getItems().isEmpty()){
            setEditable(false);
        }
    }
     private void parcours(Task start, Task end, List<Integer> critical) {
        Task temp = null;
        for (Predecessor link :end.getPredecessor()) 
        {
            for (Task t6 : DAO.getInstance().getCurrentProject().getTasks()) 
            {
                if (t6.getId().equals(link.getIdTaskParent())) 
                {
                    temp = t6;
                }
            }
            parcours(start, temp, critical);
        }
        if(!critical.contains(end.getId())){
            critical.add(end.getId());
        }
    }

    
    

}
