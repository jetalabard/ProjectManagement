/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

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
import projectmanagement.application.business.Ressource;
import projectmanagement.application.business.StateNotSave;
import projectmanagement.application.business.Task;
import projectmanagement.application.dataloader.Database;

/**
 *
 * @author Jérémy
 */
public class SaveTest {
    
    private Project project;
    
    
    @BeforeClass
    public static void setUpClass() {
        Database.getInstance();
        DAO.getInstance().deleteAll();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        List<Project> list = DAO.getInstance().getAllProject();
        DAO.getInstance().deleteAll();
    }

    /**
     * Test of execute method, of class Save.
     */
    @Test
    public void testExecute() {
        //sauvegarde d'un nouveau projet seulement
        project = new Project(null,"project", MyDate.now());//projet de base état sauvegarder
        project.setState(new StateNotSave());
        project = new Save(project).execute();
        Project proj = DAO.getInstance().getProject(project.getId());
        assertEquals(proj, project);
        
    }
    
    /**
     * Test of execute method, of class Save.
     */
    @Test
    public void testExecute2() {
        //sauvegarde d'un nouveau projet seulement
        project = DAO.getInstance().insertProject("name", MyDate.now());
        project = new Save(project).execute();
        Project proj = DAO.getInstance().getProject(project.getId());
        assertEquals(proj, project);
        
    }
    
    /**
     * Test of execute method, of class Save.
     */
    @Test
    public void testExecuteAddTask() {
        //sauvegarde d'un nouveau projet seulement
        project = DAO.getInstance().insertProject("name", MyDate.now());
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.setState(new StateNotSave());
        project = new Save(project).execute();
        
        Project proj = DAO.getInstance().getProject(project.getId());
        assertEquals(proj.getTasks().size(), 3);
        
    }
    
    
    /**
     * Test of execute method, of class Save.
     */
    @Test
    public void testExecuteAddTaskAddPredecessor() {
        //sauvegarde d'un nouveau projet seulement
        project = DAO.getInstance().insertProject("name", MyDate.now());
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.setState(new StateNotSave());
        
        project = new Save(project).execute();
        //obligé de sauvegardé après l'ajout des tâches sinon pas ID disponible pour les prédecesseurs
        Task t = project.getTasks().get(0);
        Task t2 = project.getTasks().get(1);
        t.getPredecessor().add(new Predecessor("type", 0, "constraint", t.getId(), 0));
        t2.getPredecessor().add(new Predecessor("type", 0, "constraint", t2.getId(), 0));
        
        project.setState(new StateNotSave());
        project = new Save(project).execute();
        
        Project proj = DAO.getInstance().getProject(project.getId());
        assertEquals(proj.getTasks().get(0).getPredecessor().size(), 1);
        assertEquals(proj.getTasks().get(1).getPredecessor().size(), 1);
        
        List<Predecessor> list = DAO.getInstance().getAllPredecessorByIdTask(t.getId());
        List<Predecessor> list2 = DAO.getInstance().getAllPredecessorByIdTask(t2.getId());
        assertEquals(list.get(0), proj.getTasks().get(0).getPredecessor().get(0));
        assertEquals(list2.get(0), proj.getTasks().get(1).getPredecessor().get(0));
        
    }
    
    /**
     * Test of execute method, of class Save.
     */
    @Test
    public void testExecuteAddTaskAddRessource() {
        //sauvegarde d'un nouveau projet seulement
        project = DAO.getInstance().insertProject("name", MyDate.now());
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.getTasks().add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        project.setState(new StateNotSave());
        
        project = new Save(project).execute();
        //obligé de sauvegardé après l'ajout des tâches sinon pas ID disponible pour les prédecesseurs
        Task t = project.getTasks().get(0);
        Task t2 = project.getTasks().get(1);
        t.getRessources().add(new Human(0.0f, "name", "firstname", "role", t.getId()));
        t2.getRessources().add(new Equipment(0.0f, "reference", "name", t2.getId()));
        
        project.setState(new StateNotSave());
        project = new Save(project).execute();
        
        Project proj = DAO.getInstance().getProject(project.getId());
        assertEquals(proj.getTasks().get(0).getRessources().size(), 1);
        assertEquals(proj.getTasks().get(1).getRessources().size(), 1);
        
        List<Ressource> list = DAO.getInstance().getAllRessourceByIdTask(t.getId());
        List<Ressource> list2 = DAO.getInstance().getAllRessourceByIdTask(t2.getId());
        assertEquals(list.get(0), proj.getTasks().get(0).getRessources().get(0));
        assertEquals(list2.get(0), proj.getTasks().get(1).getRessources().get(0));
        
    }
    
}
