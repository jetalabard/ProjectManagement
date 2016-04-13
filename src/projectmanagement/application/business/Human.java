/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectmanagement.application.business;

import java.util.Objects;

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
        } else {
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

    public Human(int id, float cost,String name, String firstname, String role,int idTask) {
        super(id, cost,idTask);
        this.name = name;
        this.firstname = firstname;
        this.role = role;
    }
    
    
    
}
