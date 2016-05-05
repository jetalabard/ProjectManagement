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
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Ressource;

/**
 *
 * @author Jérémy
 */
public class RessourcesTableTest {
    
    public RessourcesTableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of transformRessourceToResssourceTable method, of class RessourcesTable.
     */
    @Test
    public void testTransformRessourceToResssourceTable() {
        List<Ressource> list = new ArrayList<>();
        list.add(new Human(0.0f, "name", "firstname", "role", 0));
        list.add(new Equipment(0.0f, "reference", "name", 0));
        
        List<RessourcesTable> listTable =  RessourcesTable.transformRessourceToResssourceTable(list);
        assertEquals(listTable.size(), 2);
        RessourcesTable res1 = listTable.get(0);
        RessourcesTable res2 = listTable.get(1);
        assertTrue(res1.getCost() == 0.0f);
        assertTrue(res2.getCost() == 0.0f);
        
        assertTrue(res1.getName().equals("name"));
        assertTrue(res2.getName().equals("name"));
        
        assertTrue(res1.getFirstname().equals("firstname"));
        assertTrue(res2.getFirstname().equals(""));
        
        assertTrue(res1.getReference().equals(""));
        assertTrue(res2.getReference().equals("reference"));
        
        assertTrue(res1.getRole().equals("role"));
        assertTrue(res2.getRole().equals(""));
        
        assertTrue(res1.getType().equals(0));
        assertTrue(res2.getType().equals(1));
        
        assertTrue(res1.getId() == null);
        assertTrue(res2.getId() == null);
        
        assertTrue(res1.getIdTask().equals(0));
        assertTrue(res2.getIdTask().equals(0));
        
    }

    /**
     * Test of transformRessourceTableToResssource method, of class RessourcesTable.
     */
    @Test
    public void testTransformRessourceTableToResssource() {
        
        List<RessourcesTable> listTable = new ArrayList<>();
        listTable.add(new RessourcesTable("reference", "name", "firstname", "role", null, 0, null, 0.0f));//human
        listTable.add(new RessourcesTable("reference", "name", "firstname", "role", null, 1, null, 0.0f));//equipment
        
        List<Ressource> list =  RessourcesTable.transformRessourceTableToResssource(listTable);
        assertEquals(list.size(), 2);
        
        Human res1 = (Human) list.get(0);
        Equipment res2 = (Equipment) list.get(1);
        
        assertTrue(res1.getCost() == 0.0f);
        assertTrue(res2.getCost() == 0.0f);
        
        assertTrue(res1.getName().equals("name"));
        assertTrue(res2.getName().equals("name"));
        
        assertTrue(res1.getFirstname().equals("firstname"));
        
        assertTrue(res2.getReference().equals("reference"));
        
        assertTrue(res1.getRole().equals("role"));
        
        assertTrue(res1.getId() == null);
        assertTrue(res2.getId() == null);
        
        assertTrue(res1.getIdTask() == null);
        assertTrue(res2.getIdTask()== null);
        
    }
    
}
