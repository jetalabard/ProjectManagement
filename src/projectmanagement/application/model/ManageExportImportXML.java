/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.Task;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Mahon--Puget
 */
public class ManageExportImportXML {

    private static Element racine = null;
    private static Document document = null;

    public ManageExportImportXML() {
        //Nous allons commencer notre arborescence en créant la racine XML
        racine = new Element(Tags.PROJECT);
        //On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
        document = new Document(racine);
    }

    public static void export(Project prj, String path) {
        exportProject(prj);
        if (prj.getTasks() != null) {
            exportTaskProject(prj);
        }
        //affiche();
        enregistre(path + "/" + prj.getTitle() + ".xml");
    }

    private static void exportTaskProject(Project prj) {
        for (Task task : prj.getTasks()) {
            Element tache = new Element(Tags.TASK);
            racine.addContent(tache);

            Element info = new Element(Tags.ID);
            info.setText(String.valueOf(task.getId()));
            tache.addContent(info);
            info = new Element(Tags.NAME);
            info.setText(task.getName());
            tache.addContent(info);
            info = new Element(Tags.DATE_BEGIN);
            info.setText(task.getDatebegin().toString());
            tache.addContent(info);
            info = new Element(Tags.DATE_END);
            info.setText(task.getDateend().toString());
            tache.addContent(info);
            info = new Element(Tags.PRIORITY);
            info.setText(String.valueOf(task.getPriority()));
            tache.addContent(info);
            info = new Element(Tags.ID_PROJECT);
            info.setText(String.valueOf(task.getIdProject()));
            tache.addContent(info);
            info = new Element(Tags.NOTE);
            info.setText(task.getNote());
            tache.addContent(info);
            if (task.getPredecessor() != null) {
                exportPredecessorTask(task, tache);
            }
            if (task.getRessources() != null) {
                exportRessourceTask(task, tache);
            }
        }
    }

    private static void exportRessourceTask( Task task, Element tache) {
        Element info;
        for (Ressource res : task.getRessources()) {
            Element attr;
            info = new Element(Tags.RESSOURCE);
            Attribute classe;
            if (res instanceof Human) {
                classe = new Attribute(Tags.TYPE, "0");//HUMAN
            } else {
                classe = new Attribute(Tags.TYPE, "1");
            }
            info.setAttribute(classe);
            
            if (res instanceof Human) {
                Human hum = (Human) res;
                attr = new Element(Tags.ID);
                attr.setText(String.valueOf(res.getId()));
                info.addContent(attr);
                attr = new Element(Tags.COST);
                attr.setText(String.valueOf(res.getCost()));
                info.addContent(attr);
                attr = new Element(Tags.NAME);
                attr.setText(hum.getName());
                info.addContent(attr);
                attr = new Element(Tags.FIRSTNAME);
                attr.setText(hum.getFirstname());
                info.addContent(attr);
                attr = new Element(Tags.ROLE);
                attr.setText(hum.getRole());
                info.addContent(attr);
            } else {
                Equipment equi = (Equipment) res;
                attr = new Element(Tags.ID);
                attr.setText(String.valueOf(res.getId()));
                info.addContent(attr);
                attr = new Element(Tags.COST);
                attr.setText(String.valueOf(res.getCost()));
                info.addContent(attr);
                attr = new Element(Tags.REFERENCE);
                attr.setText(equi.getReference());
                info.addContent(attr);
                attr = new Element(Tags.NAME);
                attr.setText(equi.getName());
                info.addContent(attr);
            }
            tache.addContent(info);

        }
    }

    private static void exportProject(Project prj) {
        Attribute classe = new Attribute(Tags.ID, Integer.toString(prj.getId()));
        racine.setAttribute(classe);
        classe = new Attribute(Tags.NAME, prj.getTitle());
        racine.setAttribute(classe);
        classe = new Attribute(Tags.LASTUSE, MyDate.valueOf(prj.getLastUse()));
        racine.setAttribute(classe);
    }

    private static void exportPredecessorTask( Task task, Element tache) {
        for (Predecessor pred : task.getPredecessor()) {
            Element predecessor = new Element(Tags.PREDECESSOR);
            Element attr;

            attr = new Element(Tags.ID);
            attr.setText(String.valueOf(pred.getId()));
            predecessor.addContent(attr);

            attr = new Element(Tags.CONSTRAINT);
            attr.setText(pred.getConstraint());
            predecessor.addContent(attr);

            attr = new Element(Tags.TYPE);
            attr.setText(pred.getType());
            predecessor.addContent(attr);

            attr = new Element(Tags.GAP);
            attr.setText(MyDate.valueOf(pred.getGap()));
            predecessor.addContent(attr);
            
            tache.addContent(predecessor);

        }
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
                ressource = new Human(id,cost, name,firstname , role);
            } else {
                String reference = res.getChild(Tags.REFERENCE).getValue();
                ressource = new Equipment(id, cost, reference, name);
            }

            task.getRessources().add(ressource);
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

    private static void affiche() {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    static void enregistre(String fichier) {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private Task readTaskPredecessor(Element courant, Task task)
    {
        List listRes = courant.getChildren(Tags.RESSOURCE);
        Iterator j = listRes.iterator();
        while (j.hasNext()) {
            Element res = (Element) j.next();
            String gap = res.getChild(Tags.GAP).getValue();
            int id = Integer.valueOf(res.getChild(Tags.ID).getValue());
            String constraint = res.getChild(Tags.CONSTRAINT).getValue();
            String name = res.getChild(Tags.NAME).getValue();
            
            Predecessor predecessor = new Predecessor(id,name, MyDate.valueOf(gap), constraint);
            task.getPredecessor().add(predecessor);
        }
        return task;
    }
}
