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
    private int id;
    
    private float cost;
    
    public int getId() {
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


    public Ressource(int id, float cost) {
        this.id = id;
        this.cost = cost;
    }
    
    
    
    
    
}
