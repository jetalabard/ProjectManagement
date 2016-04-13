/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.Objects;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class Predecessor {
    
    private Integer id;
    
    private Integer idTask;
    
     private Integer idTaskParent;
    
    private String type;
    
    private MyDate gap;
    
    private String constraint;

    public Predecessor(int id,String type, MyDate gap, String constraint,int idTask,int idTaskParent) {
        this.id = id;
        this.type = type;
        this.gap = gap;
        this.constraint = constraint;
        this.idTask = idTask;
         this.idTaskParent = idTaskParent;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }
    
    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public MyDate getGap() {
        return gap;
    }

    public String getConstraint() {
        return constraint;
    }

    public Integer getIdTaskParent() {
        return idTaskParent;
    }

    public void setIdTaskParent(Integer idTaskParent) {
        this.idTaskParent = idTaskParent;
    }
    
     

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            Predecessor pred = (Predecessor) obj;
            if (id != null && pred.getId() != null && id.equals(pred.getId())) {
                if (idTaskParent != null && pred.getIdTaskParent() != null && idTaskParent.equals(pred.getIdTaskParent())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.idTaskParent);
        return hash;
    }

    
    
    
    
    
}
