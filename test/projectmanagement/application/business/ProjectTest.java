/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import java.util.Objects;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import projectmanagement.application.model.DAO;
import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class ProjectTest {
    

    /**
     * Test of getTitle method, of class Project.
     */
    @Test
    public void testTitle() {
        Project proj = new Project(0, "project", MyDate.now());
        proj.setTitle("new");
        assertEquals(proj.getTitle(),"new");
    }

    /**
     * Test of getId method, of class Project.
     */
    @Test
    public void testId() {
        Project proj = DAO.getInstance().insertProject("name", MyDate.now());
        Project other = DAO.getInstance().getProject(proj.getId());
        
        assertTrue(Objects.equals(other.getId(), proj.getId()));
    }

    /**
     * Test of getTasks method, of class Project.
     */
    @Test
    public void testTasks() {
    }

    /**
     * Test of getLastUse method, of class Project.
     */
    @Test
    public void testLastUse() {
    }


    /**
     * Test of getState method, of class Project.
     */
    @Test
    public void testState() {
    }

    @Test
    public void testChangeState() {
    }
    
}
