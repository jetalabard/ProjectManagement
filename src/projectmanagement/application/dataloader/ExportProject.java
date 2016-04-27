/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.io.FileOutputStream;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
public class ExportProject {

    private static Element racine = null;
    private static Document document = null;

    public ExportProject() {
        //Nous allons commencer notre arborescence en créant la racine XML
        racine = new Element(Tags.PROJECT);
        //On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
        document = new Document(racine);
    }

    public void export(Project prj, String path) {
        exportProject(prj);
        if (prj.getTasks() != null) {
            exportTaskProject(prj);
        }
        enregistre(path + "/" + prj.getTitle() + ".xml");
    }
    
    private void enregistre(String fichier) {
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
            if (task.getNote() != null) {
                info.setText(task.getNote());
            }
            tache.addContent(info);
            if (task.getPredecessor() != null) {
                exportPredecessorTask(task, tache);
            }
            if (task.getRessources() != null) {
                exportRessourceTask(task, tache);
            }
        }
    }

    private static void exportRessourceTask(Task task, Element tache) {
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
                if (hum.getFirstname() != null) {
                    attr.setText(hum.getFirstname());
                }
                info.addContent(attr);

                attr = new Element(Tags.ROLE);
                if (hum.getRole() != null) {
                    attr.setText(hum.getRole());
                }
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
                if (equi.getReference() != null) {
                    attr.setText(equi.getReference());
                }
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

    private static void exportPredecessorTask(Task task, Element tache) {
        for (Predecessor pred : task.getPredecessor()) {
            Element predecessor = new Element(Tags.PREDECESSOR);
            Element attr;

            attr = new Element(Tags.ID);
            attr.setText(String.valueOf(pred.getId()));
            predecessor.addContent(attr);

            attr = new Element(Tags.ID_TASK);
            attr.setText(String.valueOf(pred.getIdTask()));
            predecessor.addContent(attr);

            attr = new Element(Tags.ID_TASK_PARENT);
            attr.setText(String.valueOf(pred.getIdTaskParent()));
            predecessor.addContent(attr);

            attr = new Element(Tags.CONSTRAINT);
            if (pred.getConstraint() != null) {
                attr.setText(pred.getConstraint());
            }
            predecessor.addContent(attr);

            attr = new Element(Tags.TYPE);
            if (pred.getType() != null) {
                attr.setText(pred.getType());
            }
            predecessor.addContent(attr);

            attr = new Element(Tags.GAP);
            if (pred.getGap() != null) {
                attr.setText(String.valueOf(pred.getGap()));
            }
            predecessor.addContent(attr);

            tache.addContent(predecessor);

        }
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
}
