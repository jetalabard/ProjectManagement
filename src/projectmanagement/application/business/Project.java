/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.List;

/**
 *
 * @author Jérémy
 */
public class Project {
    
    private String path;
    
    private String pathDiagrammes;
    
    private Diagram pert;
    
    private Diagram Gantt;
    
    private List<Task> tasks = null;
    private String title;
    
    
    
    public Project(String path,String pathDiagramme,String title){
        this.path = path;
        this.pathDiagrammes = pathDiagrammes;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
    
    
}
