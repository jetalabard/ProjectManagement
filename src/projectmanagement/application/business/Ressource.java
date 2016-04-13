/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectmanagement.application.business;

/**
 *
 * @author Mahon--Puget
 */
public class Ressource{
    private Integer id;
    
    private Integer idTask;
    
    private float cost;
    
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    
    
    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }
    
    


    public Ressource(int id, float cost, int idTask) {
        this.id = id;
        this.cost = cost;
        this.idTask = idTask;
    }
    
    
    
    
    
}
