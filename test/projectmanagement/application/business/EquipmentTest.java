/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jérémy
 */
public class EquipmentTest {
    

    /**
     * Test of equals method, of class Equipment.
     */
    @Test
    public void testEquals() {
        Equipment instance = new Equipment(0,0.0f, "reference","equipment" , 0);
        assertTrue(instance.equals(new Equipment(0,0.0f, "reference","equipment" , 0)));
    }

    /**
     * Test of getReference method, of class Equipment.
     */
    @Test
    public void testReference() {
        Equipment instance = new Equipment(0.0f, "reference","equipment" , 0);
        assertEquals(instance.getReference(), "reference");
        instance.setReference("newReference");
        assertEquals(instance.getReference(), "newReference");
        
    }


    /**
     * Test of getName method, of class Equipment.
     */
    @Test
    public void testName() {
        Equipment instance = new Equipment(0.0f, "reference","equipment" , 0);
        assertEquals(instance.getName(), "equipment");
        instance.setName("newEquipment");
        assertEquals(instance.getName(), "newEquipment");
    }

    
}
