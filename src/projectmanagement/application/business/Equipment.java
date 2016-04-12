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
public class Equipment extends Ressource{
    private String reference;
    
    private String name;

    public Equipment(int id, float cost, String reference, String name) {
        super(id, cost);
        this.reference =reference;
        this.name=name;
        
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
