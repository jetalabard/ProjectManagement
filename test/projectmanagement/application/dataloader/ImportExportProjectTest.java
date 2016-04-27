/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.MyDate;
import projectmanagement.application.model.Save;

/**
 *
 * @author Jérémy
 */
public class ImportExportProjectTest {
    
    public ImportExportProjectTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Database.getInstance();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        DAO.getInstance().deleteAll();
    }

    /**
     * Test of lecture method, of class ImportProject.
     */
    @Test
    public void test() {
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        List<Task> list = new ArrayList<>();
        list.add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", p.getId()));
        list.add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", p.getId()));
        list.add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", p.getId()));
        p.setTasks(list);
        p = new Save(p).execute();
        //obligé de sauvegardé après l'ajout des tâches sinon pas ID disponible pour les prédecesseurs
        Task t = p.getTasks().get(0);
        Task t2 = p.getTasks().get(1);
        t.getRessources().add(new Human(0.0f, "name", "firstname", "role", t.getId()));
        t2.getRessources().add(new Equipment(0.0f, "reference", "name", t2.getId()));
        t.getPredecessor().add(new Predecessor("type", 0, "constraint", t.getId(), 0));
        t2.getPredecessor().add(new Predecessor("type", 0, "constraint", t2.getId(), 0));
        p.setState(new StateNotSave());
        p = new Save(p).execute();
        
        
        new ExportProject().export(p, ".");
        Project project = new ImportProject().lecture("./"+p.getTitle()+".xml");
        
        assertEquals(project, p);
    }
    
    @Test
    public void test2() {
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        List<Task> list = new ArrayList<>();
        list.add(new Task("name", MyDate.now(), MyDate.now(), 0, null, p.getId()));
        list.add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", p.getId()));
        list.add(new Task("name", MyDate.now(), MyDate.now(), 0, null, p.getId()));
        p.setTasks(list);
        p = new Save(p).execute();
        //obligé de sauvegardé après l'ajout des tâches sinon pas ID disponible pour les prédecesseurs
        Task t = p.getTasks().get(0);
        Task t2 = p.getTasks().get(1);
        t.getRessources().add(new Human(0.0f, "name", "firstname", null, t.getId()));
        t2.getRessources().add(new Equipment(0.0f, null, "name", t2.getId()));
        t2.getRessources().add(new Equipment(0.0f, "reference", "name", t2.getId()));
        t.getPredecessor().add(new Predecessor(null, 0, null, t.getId(), 0));
        t2.getPredecessor().add(new Predecessor("type", 0, "constraint", t2.getId(), 0));
        p.setState(new StateNotSave());
        p = new Save(p).execute();
        
        
        new ExportProject().export(p, ".");
        Project project = new ImportProject().lecture("./"+p.getTitle()+".xml");
        
        assertEquals(project, p);
    }
    
}
