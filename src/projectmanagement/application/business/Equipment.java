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
public class Equipment extends Ressource {

    private String reference;

    private String name;

    public Equipment(int id, float cost, String reference, String name,Integer idTask) {
        super(id, cost,idTask);
        this.reference = reference;
        this.name = name;

    }
    public Equipment( float cost, String reference, String name,Integer idTask) {
        super();
        this.setCost(cost);
        this.setId(null);
        this.setIdTask(idTask);
        this.reference = reference;
        this.name = name;

    }

    @Override
    public String toString() {
        return "Equipment{" + "reference=" + reference + ", name=" + name + '}';
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if(obj instanceof Human) {
            return false;
        }
        else{
            Equipment h = (Equipment) obj;
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
        int hash = 7;
        return hash;
    }

    

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
