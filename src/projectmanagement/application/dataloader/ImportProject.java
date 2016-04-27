/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.MyDate;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class ImportProject {
    private static Element racine = null;
    private static Document document = null;

    public ImportProject() {
    }

    public Project lecture(String filename) {
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        racine = document.getRootElement();
        Project prj = readAndCreateProject();

        prj = readProjectTask(prj);
        return prj;
    }

    private Project readProjectTask(Project proj) throws NumberFormatException {
        List listTask = racine.getChildren(Tags.TASK);
        Iterator i = listTask.iterator();
        while (i.hasNext()) {
            Element courant = (Element) i.next();

            int idtask = Integer.parseInt(courant.getChild(Tags.ID).getText());
            String name = courant.getChild(Tags.NAME).getText();
            MyDate dateb = MyDate.valueOf(courant.getChild(Tags.DATE_BEGIN).getText());
            MyDate datee = MyDate.valueOf(courant.getChild(Tags.DATE_END).getText());
            int priority = Integer.parseInt(courant.getChild(Tags.PRIORITY).getText());
            int id_project = Integer.parseInt(courant.getChild(Tags.ID_PROJECT).getText());
            String note = courant.getChild(Tags.NOTE).getText();
            Task task = new Task(idtask, name, dateb, datee, priority, note,id_project);

            task = readTaskRessource(courant, task);
            task = readTaskPredecessor(courant, task);
            proj.getTasks().add(task);
        }
        return proj;
    }

    private Task readTaskRessource(Element courant, Task task) {
        List listRes = courant.getChildren(Tags.RESSOURCE);
        Iterator j = listRes.iterator();
        while (j.hasNext()) {
            Element res = (Element) j.next();
            int type = Integer.valueOf(res.getAttribute(Tags.TYPE).getValue());
            int id = Integer.valueOf(res.getChild(Tags.ID).getValue());
            float cost = Float.valueOf(res.getChild(Tags.COST).getValue());
            
            String name = res.getChild(Tags.NAME).toString();
            Ressource ressource = null;
            if (type == 0) {//human{
                
                String firstname = res.getChild(Tags.FIRSTNAME).getValue();
                String role = res.getChild(Tags.ROLE).getValue();
                ressource = new Human(id,cost, name,firstname , role,task.getId());
            } else {
                String reference = res.getChild(Tags.REFERENCE).getValue();
                ressource = new Equipment(id, cost, reference, name,task.getId());
            }

            task.getRessources().add(ressource);
        }
        return task;
    }
    
     private Task readTaskPredecessor(Element courant, Task task)
    {
        List listRes = courant.getChildren(Tags.PREDECESSOR);
        Iterator j = listRes.iterator();
        while (j.hasNext()) {
            Element res = (Element) j.next();
            int gap = Integer.valueOf(res.getChild(Tags.GAP).getValue());
            int id = Integer.valueOf(res.getChild(Tags.ID).getValue());
            String constraint = res.getChild(Tags.CONSTRAINT).getValue();
            String name = res.getChild(Tags.TYPE).getValue();
            int id_task =Integer.valueOf(res.getChild(Tags.ID_TASK).getValue());
            
            Predecessor predecessor = new Predecessor(id,name, gap, constraint,id_task,task.getId());
            task.getPredecessor().add(predecessor);
        }
        return task;
    }

    private Project readAndCreateProject() throws NumberFormatException {
        //extraire id et title de la racine
        int id = Integer.valueOf(racine.getAttribute(Tags.ID).getValue());
        String title = racine.getAttribute(Tags.NAME).getValue();
        MyDate lastuse = MyDate.valueOf(racine.getAttribute(Tags.LASTUSE).getValue());
        return new Project(id, title, lastuse);
    }

    
}
