/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jérémy
 */
public class PredecessorTest {
    
    /**
     * Test of getIdTask method, of class Predecessor.
     */
    @Test
    public void testIdTask() {
        Predecessor pred = new Predecessor("type", 0, "constraint", 0, 0);
        pred.setIdTask(8);
        assertTrue(pred.getIdTask() == 8);
    }


    /**
     * Test of getId method, of class Predecessor.
     */
    @Test
    public void testId() {
        Predecessor pred = new Predecessor("type", 0, "constraint", 0, 0);
        pred.setId(8);
        assertTrue(pred.getId()== 8);
    }


    /**
     * Test of getGap method, of class Predecessor.
     */
    @Test
    public void testGap() {
        Predecessor pred = new Predecessor("type", 0, "constraint", 0, 0);
        pred.setGap(5);
        assertTrue(pred.getGap()== 5);
    }

    /**
     * Test of getConstraint method, of class Predecessor.
     */
    @Test
    public void testConstraint() {
        Predecessor pred = new Predecessor("type", 0, "constraint", 0, 0);
        pred.setConstraint("new");
        assertEquals(pred.getConstraint(), "new");
    }

    /**
     * Test of getIdTaskParent method, of class Predecessor.
     */
    @Test
    public void testIdTaskParent() {
        Predecessor pred = new Predecessor("type", 0, "constraint", 0, 0);
        pred.setIdTaskParent(8);
        assertTrue(pred.getIdTaskParent()== 8);
    }

    /**
     * Test of setType method, of class Predecessor.
     */
    @Test
    public void testType() {
        Predecessor pred = new Predecessor("type", 0, "constraint", 0, 0);
        pred.setType("new");
        assertEquals(pred.getType(), "new");
    }

    /**
     * Test of equals method, of class Predecessor.
     */
    @Test
    public void testEquals() {
        Predecessor pred = new Predecessor(0,"type", 0, "constraint", 0, 0);
        assertTrue(pred.equals(new Predecessor(0,"type", 0, "constraint", 0, 0)));
    }

    
}
