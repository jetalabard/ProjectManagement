/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class Task extends TaskComponent{

    private Integer id;
    
    private Integer idProject;

    private String name;

    private MyDate datebegin;

    private MyDate dateend;

    private int priority;

    private String note;

    private List<Predecessor> predecessor = null;
    private List<Ressource> ressources = null;

    public Task() {
        predecessor = new ArrayList<>();
        ressources = new ArrayList<>();
    }

    public Task(int id, String name, MyDate datebegin, MyDate dateend, int priority, String note,int idProject) {
        this.id = id;
        this.name = name;
        this.datebegin = datebegin;
        this.dateend = dateend;
        this.priority = priority;
        this.note = note;
        predecessor = new ArrayList<>();
        ressources = new ArrayList<>();
        this.idProject = idProject;
    }
    public Task(String name, MyDate datebegin, MyDate dateend, int priority, String note,int idProject) {
        this.name = name;
        this.datebegin = datebegin;
        this.dateend = dateend;
        this.priority = priority;
        this.note = note;
        predecessor = new ArrayList<>();
        ressources = new ArrayList<>();
        this.idProject = idProject;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }
    
    
    

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyDate getDatebegin() {
        return datebegin;
    }

    public void setDatebegin(MyDate datebegin) {
        this.datebegin = datebegin;
    }

    public MyDate getDateend() {
        return dateend;
    }

    public void setDateend(MyDate dateend) {
        this.dateend = dateend;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Predecessor> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(List<Predecessor> predecessor) {
        this.predecessor = predecessor;
    }

    public List<Ressource> getRessources() {
        return ressources;
    }

    public void setRessources(List<Ressource> ressources) {
        this.ressources = ressources;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else {
            Task t = (Task) other;
            boolean returnValue = false;
            if (this.idProject.equals(t.getIdProject())) {
                if (this.id != null && t.getId() != null && this.id.equals(t.getId())) {
                    returnValue = true;
                }
            }
            return returnValue;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.idProject);
        return hash;
    }


}
