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
public class Human extends Ressource{
    
    private String name;
    
    private String firstname;
    
    private String role;
   
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } 
        else if(obj instanceof Equipment) {
            return false;
        }else {
            Human h = (Human) obj;
            if (getId() != null && h.getId() != null && getId().equals(h.getId())) {
                if (getIdTask() != null && h.getIdTask() != null && getIdTask().equals(h.getIdTask())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Human{" + "name=" + name + ", firstname=" + firstname + ", role=" + role + '}';
    }
    
    

    public Human(Integer id, float cost,String name, String firstname, String role,Integer idTask) {
        super(id, cost,idTask);
        this.name = name;
        this.firstname = firstname;
        this.role = role;
    }
    
     public Human(float cost,String name, String firstname, String role,Integer idTask) {
        super();
         this.setCost(cost);
         this.setIdTask(idTask);
        this.name = name;
        this.firstname = firstname;
        this.role = role;
    }
    
    
    
}
