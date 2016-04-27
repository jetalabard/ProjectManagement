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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Task;

/**
 *
 * @author Jérémy
 */
public class ManageUndoRedoTest {
    
    private List<Task> list;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    private Project project;
    
    @Before
    public void setUp() {
        list=new ArrayList<>();
        project = DAO.getInstance().insertProject("name", MyDate.now());
        DAO.getInstance().setCurrentProject(project);
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of undo method, of class ManageUndoRedo.
     */
    @Test
    public void testUndo() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        ManageUndoRedo.getInstance().add(tasks);
        ManageUndoRedo.getInstance().undo();
        assertEquals(DAO.getInstance().getCurrentProject().getTasks().size(),0);
    }

    /**
     * Test of redo method, of class ManageUndoRedo.
     */
    @Test
    public void testRedo() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("name", MyDate.now(), MyDate.now(), 0, "note", project.getId()));
        ManageUndoRedo.getInstance().add(tasks);
        ManageUndoRedo.getInstance().undo();
        ManageUndoRedo.getInstance().redo();
        assertEquals(DAO.getInstance().getCurrentProject().getTasks().size(),1);
    }

    
}
