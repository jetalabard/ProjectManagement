/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.ArrayList;
import java.util.List;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Ressource;

/**
 *
 * @author Jérémy
 */
public class RessourcesTable 
{
    private String reference;

    private String name;
    
    private String firstname;
    
    private String role;
    
    private Integer id;
    
    private Integer type;
    
    private Integer idTask;
    
    private float cost;

    public RessourcesTable(Human hum) {
        this.reference ="";
        this.type = 0;
        this.name = hum.getName();
        this.firstname = hum.getFirstname();
        this.role = hum.getRole();
        this.id = hum.getId();
        this.idTask = hum.getIdTask();
        this.cost = hum.getCost();
    }
    
    public RessourcesTable(Equipment equip) {
        this.reference =equip.getReference();
        this.name = equip.getName();
        this.firstname = "";
        this.type = 1;
        this.role = "";
        this.id = equip.getId();
        this.idTask = equip.getIdTask();
        this.cost = equip.getCost();
    }

    public RessourcesTable(String reference, String name, String firstname, String role, Integer id, Integer type, Integer idTask, float cost) {
        this.reference = reference;
        this.name = name;
        this.firstname = firstname;
        this.role = role;
        this.id = id;
        this.type = type;
        this.idTask = idTask;
        this.cost = cost;
    }
    
    
    
    public static List<RessourcesTable> transformRessourceToResssourceTable(List<Ressource> list){
        List<RessourcesTable> returnList = new ArrayList();
        for(Ressource ress : list){
            if(ress instanceof Human){
                returnList.add(new RessourcesTable((Human)ress));
            }
            else{
                returnList.add(new RessourcesTable((Equipment)ress));
            }
        }
        return returnList;
    }
    
    public static List<Ressource> transformRessourceTableToResssource(List<RessourcesTable> list){
        List<Ressource> returnList = new ArrayList();
        for(RessourcesTable ress : list){
            if(ress.getType() == 0){
                returnList.add(new Human(ress.getCost(), ress.getName(), ress.getFirstname(), ress.getRole(), ress.getIdTask()));
            }
            else{
                returnList.add(new Equipment(ress.getCost(), ress.getReference(), ress.getName(), ress.getIdTask()));
            }
        }
        return returnList;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    
    
    
}
