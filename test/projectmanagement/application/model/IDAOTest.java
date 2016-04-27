/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class IDAOTest {
    
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
        DAO.getInstance().deleteAll();
    }

    /**
     * Test of getAllTasks method, of class IDAO.
     */
    @Test
    public void testGetAllTasks() {
        System.out.println("getAllTasks");
        int id = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "note");
        int id2 = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "note");
        List<Task> list = DAO.getInstance().getAllTasks();
        assertEquals(list.size(), 2);
        assertTrue(list.get(0).getId() ==id);
        assertTrue(list.get(1).getId() == id2);
    }

    /**
     * Test of getTask method, of class IDAO.
     */
    @Test
    public void testGetTask() {
        System.out.println("getTask");
        int idTask = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "");
       
        DAO.getInstance().insertPredecessor("type", 0, "constraint", idTask, 0);
         DAO.getInstance().insertPredecessor("type", 0, "constraint", idTask, 0);
         DAO.getInstance().insertRessource(idTask, 0.0f, "name", "firstname", "role", "reference", 0);
         DAO.getInstance().insertRessource(idTask, 0.0f, "name", "firstname", "role", "reference", 0);
         Task t = DAO.getInstance().getTask(idTask);
         assertTrue(t.getId() ==  idTask);
         assertTrue(t.getRessources().size() ==  2);
         assertTrue(t.getPredecessor().size() ==  2);
        
    }

    /**
     * Test of updateTask method, of class IDAO.
     */
    @Test
    public void testUpdateTask() {
        System.out.println("updateTask");
        MyDate now = MyDate.now();
         int idTask = DAO.getInstance().insertTask("name", 0, now, now, 0, "");
         DAO.getInstance().updateTask(idTask, "newname", 3, MyDate.now(), MyDate.now(), 0, "note");
         Task t = DAO.getInstance().getTask(idTask);
         assertTrue(idTask ==  t.getId());
         assertTrue(t.getNote().equals("note"));
         assertTrue(t.getName().equals("newname"));
    }

    /**
     * Test of deleteTask method, of class IDAO.
     */
    @Test
    public void testDeleteTask() {
        System.out.println("deleteTask");
        int idTask = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "");
        DAO.getInstance().deleteTask(idTask);
        assertEquals(DAO.getInstance().getAllTasks().size(), 0);
    }

    /**
     * Test of getAllTasksByIdProject method, of class IDAO.
     */
    @Test
    public void testGetAllTasksByIdProject() {
        System.out.println("getAllTasksByIdProject");
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        int id = DAO.getInstance().insertTask("name", p.getId(), MyDate.now(), MyDate.now(), 0, "note");
        List<Task> list = DAO.getInstance().getAllTasksByIdProject(p.getId());
        assertEquals(list.size(), 1);
        assertTrue(id == list.get(0).getId());
    }

    /**
     * Test of getAllIdTask method, of class IDAO.
     */
    @Test
    public void testGetAllIdTask() {
        System.out.println("getAllIdTask");
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        int id = DAO.getInstance().insertTask("name", p.getId(), MyDate.now(), MyDate.now(), 0, "note");
        List<Integer> list = DAO.getInstance().getAllIdTask(p.getId());
        assertEquals(list.size(), 1);
        assertTrue(id == list.get(0));

    }

    /**
     * Test of getAllPredecessorByIdTask method, of class IDAO.
     */
    @Test
    public void testGetAllPredecessorByIdTask() {
        System.out.println("getAllPredecessorByIdTask");
         int id = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "note");
        DAO.getInstance().insertPredecessor("type", 0, "constraint", id, 0);
         DAO.getInstance().insertPredecessor("type", 0, "constraint", id, 0);
         assertTrue(DAO.getInstance().getAllPredecessorByIdTask(id).size() == 2);
    }

    /**
     * Test of getAllRessourceByIdTask method, of class IDAO.
     */
    @Test
    public void testGetAllRessourceByIdTask() {
        System.out.println("getAllRessourceByIdTask");
        int id = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "note");
        DAO.getInstance().insertRessource(id, 0.0f, "name", "firstname", "role", "reference", 0);
         DAO.getInstance().insertRessource(id, 0.0f, "name", "firstname", "role", "reference", 0);
         assertTrue(DAO.getInstance().getAllRessourceByIdTask(id).size() == 2);
    }

    /**
     * Test of getAllInformationProject method, of class IDAO.
     */
    @Test
    public void testGetAllInformationProject() {
        System.out.println("getAllInformationProject");
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
        List<Task> task = DAO.getInstance().getAllInformationProject(p.getId());
        assertEquals(p.getTasks().get(0), task.get(0));
        assertEquals(p.getTasks().get(1), task.get(1));
        assertEquals(p.getTasks().get(2), task.get(2));
        
        assertEquals(p.getTasks().get(0).getPredecessor().size(), task.get(0).getPredecessor().size());
        assertEquals(p.getTasks().get(1).getPredecessor().size(), task.get(1).getPredecessor().size());
        
        assertEquals(p.getTasks().get(0).getRessources().size(), task.get(0).getRessources().size());
        assertEquals(p.getTasks().get(1).getRessources().size(), task.get(1).getRessources().size());
    }

    /**
     * Test of insertTask method, of class IDAO.
     */
    @Test
    public void testInsertTask() {
        System.out.println("insertTask");
        int id = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "note");
        DAO.getInstance().insertRessource(id, 0.0f, "name", "firstname", "role", "reference", 0);
        DAO.getInstance().insertPredecessor("type", 0, "constraint", id, 0);
        Task task =  DAO.getInstance().getTask(id);
        assertEquals(task.getName(), "name");
        assertTrue(task.getPriority() == 0);
        assertTrue(task.getNote().equals("note"));
        
        assertEquals(task.getPredecessor().size(), 1);
        assertEquals(task.getRessources().size(), 1);
    }

    /**
     * Test of insertPredecessor method, of class IDAO.
     */
    @Test
    public void testInsertPredecessor() {
        System.out.println("insertPredecessor");
        int id = DAO.getInstance().insertPredecessor("type", 0, "constraint", 0, 0);
        Predecessor pred =  DAO.getInstance().getPredecessor(id);
        assertEquals(pred.getType(), "type");
        assertEquals(pred.getConstraint(), "constraint");
        assertTrue(pred.getGap().equals(0));
    }

    /**
     * Test of updatePredecessor method, of class IDAO.
     */
    @Test
    public void testUpdatePredecessor() {
        System.out.println("updatePredecessor");
        int id = DAO.getInstance().insertPredecessor("type", 0, "constraint", 0, 0);
        DAO.getInstance().updatePredecessor(id,"newtype", 6, "newconstraint", 0, 0);
        Predecessor pred =  DAO.getInstance().getPredecessor(id);
        assertEquals(pred.getType(), "newtype");
        assertEquals(pred.getConstraint(), "newconstraint");
        assertTrue(pred.getGap().equals(6));
    }

    /**
     * Test of deleteRessource method, of class IDAO.
     */
    @Test
    public void testDeleteRessource() {
        System.out.println("deleteRessource");
        int idTask = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "");
        int id = DAO.getInstance().insertRessource(idTask, 0.0f, "name", "firstname", "role", "reference", 0);
        DAO.getInstance().deleteRessource(id);
        assertEquals(DAO.getInstance().getAllRessourceByIdTask(idTask).size(), 0);
    }

    /**
     * Test of deletePredecessor method, of class IDAO.
     */
    @Test
    public void testDeletePredecessor() {
        System.out.println("deletePredecessor");
        int idTask = DAO.getInstance().insertTask("name", 0, MyDate.now(), MyDate.now(), 0, "");
        int id = DAO.getInstance().insertPredecessor("type", 0, "constraint", 0, 0);
        DAO.getInstance().deleteRessource(id);
        assertEquals(DAO.getInstance().getAllPredecessorByIdTask(idTask).size(), 0);
        
    }

    /**
     * Test of updateRessource method, of class IDAO.
     */
    @Test
    public void testUpdateRessource() {
        System.out.println("updateRessource");
        int id = DAO.getInstance().insertRessource(0, 0.0f, "name", "firstname", "role", "reference", 0);
        DAO.getInstance().updateRessource(id,0, 0.0f, "Newname", "Newfirstname", "NEwrole", "reference", 0);
        Ressource ress =  DAO.getInstance().getRessource(id);
        assertEquals(((Human)ress).getFirstname(), "Newfirstname");
        assertEquals(((Human)ress).getName(), "Newname");
        assertEquals(((Human)ress).getRole(), "NEwrole");
    }

    /**
     * Test of insertRessource method, of class IDAO.
     */
    @Test
    public void testInsertRessource() {
        System.out.println("insertRessource");
        int id = DAO.getInstance().insertRessource(0, 0.0f, "name", "firstname", "role", "reference", 0);
        Ressource ress =  DAO.getInstance().getRessource(id);
        assertEquals(((Human)ress).getFirstname(), "firstname");
        assertEquals(((Human)ress).getName(), "name");
        assertEquals(((Human)ress).getRole(), "role");
    }

    /**
     * Test of getProject method, of class IDAO.
     */
    @Test
    public void testGetProject() {
        System.out.println("testGetProject");
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
        
        Project newProj = DAO.getInstance().getProject(p.getId());
        assertEquals(newProj, p);
        
        assertEquals(newProj.getTasks().size(), p.getTasks().size());
        
        assertEquals(newProj.getTasks().get(0).getRessources().size(), p.getTasks().get(0).getRessources().size());
        assertEquals(newProj.getTasks().get(0).getPredecessor().size(), p.getTasks().get(0).getPredecessor().size());
        
         assertEquals(newProj.getTasks().get(1).getRessources().size(), p.getTasks().get(1).getRessources().size());
        assertEquals(newProj.getTasks().get(1).getPredecessor().size(), p.getTasks().get(1).getPredecessor().size());
        
    }

    /**
     * Test of getAllProject method, of class IDAO.
     */
    @Test
    public void testGetAllProject() {
        System.out.println("testGetAllProject");
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        Project p2 = DAO.getInstance().insertProject("Test", MyDate.now());
        List<Project> result = DAO.getInstance().getAllProject();
        assertEquals(result.size(), 2);
    }

    /**
     * Test of updateProject method, of class IDAO.
     */
    @Test
    public void testUpdateProject() {
        MyDate now = MyDate.now();
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        Project result = DAO.getInstance().updateProject(p.getId(), "NewValue", now);
        assertEquals(result.getLastUse(), now);
        assertEquals(result.getTitle(), "NewValue");
    }

    /**
     * Test of removeProject method, of class IDAO.
     */
    @Test
    public void testRemoveProject() {
        System.out.println("testRemoveProject");
        Project p = DAO.getInstance().insertProject("Test", MyDate.now());
        assertEquals(DAO.getInstance().getAllProject().size(), 1);
        DAO.getInstance().removeProject(p.getId());
        assertEquals(DAO.getInstance().getAllProject().size(), 0);
    }

    /**
     * Test of insertProject method, of class IDAO.
     */
    @Test
    public void testInsertProject() {
        System.out.println("testInsertProject");
        MyDate now = MyDate.now();
        Project p = DAO.getInstance().insertProject("Test", now);
        p = DAO.getInstance().getProject(p.getId());
        assertEquals("Test", p.getTitle());
    }
    
}
