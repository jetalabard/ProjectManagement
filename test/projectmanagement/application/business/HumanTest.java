/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Jérémy
 */
public class HumanTest {
    
    /**
     * Test of equals method, of class Human.
     */
    @Test
    public void testEquals() {
        Human instance = new Human(0, 0.0f, "name", "firstname", "role", 0);
        assertTrue(instance.equals( new Human(0, 0.0f, "name", "firstname", "role", 0)));
    }

    /**
     * Test of getName method, of class Human.
     */
    @Test
    public void testName() {
        Human instance = new Human(0, 0.0f, "name", "firstname", "role", 0);
        assertEquals(instance.getName(), "name");
        instance.setName("newname");
        assertEquals(instance.getName(), "newname");
    }

    /**
     * Test of getFirstname method, of class Human.
     */
    @Test
    public void testFirstname() {
        Human instance = new Human(0, 0.0f, "name", "firstname", "role", 0);
        assertEquals(instance.getFirstname(), "firstname");
        instance.setFirstname("newfirstname");
        assertEquals(instance.getFirstname(), "newfirstname");
    }

    /**
     * Test of getRole method, of class Human.
     */
    @Test
    public void testRole() {
        Human instance = new Human(0, 0.0f, "name", "firstname", "role", 0);
        assertEquals(instance.getRole(), "role");
        instance.setRole("newrole");
        assertEquals(instance.getRole(), "newrole");
    }

    
}
