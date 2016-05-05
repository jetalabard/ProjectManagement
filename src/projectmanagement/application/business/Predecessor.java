/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.Objects;

/**
 *
 * @author Jérémy
 */
public class Predecessor {
    
    private Integer id;
    
    private Integer idTask;
    
    private Integer idTaskParent;
    
    private String type; 
    
    private Integer gap;
    
    private String constraint;

    public Predecessor(int id,String type, Integer gap, String constraint,Integer idTask,Integer idTaskParent) {
        this.id = id;
        this.type = type;
        this.gap = gap;
        this.constraint = constraint;
        this.idTask = idTask;
         this.idTaskParent = idTaskParent;
    }

    @Override
    public String toString() {
        return "Predecessor{" + "id=" + id + ", idTask=" + idTask + ", idTaskParent=" + idTaskParent + ", type=" + type + ", gap=" + gap + ", constraint=" + constraint + '}';
    }
    
    
    public Predecessor(String type, Integer gap, String constraint,Integer idTask,Integer idTaskParent) {
        this.type = type;
        this.gap = gap;
        this.id = null;
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

    public void setId(Integer id) {
        this.id = id;
    }
    
    

    public Integer getGap() {
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

    public void setType(String type) {
        this.type = type;
    }

    public void setGap(Integer gap) {
        this.gap = gap;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
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
