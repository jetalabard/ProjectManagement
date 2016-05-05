/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.awt.Color;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import projectmanagement.application.model.DAO;

/**
 *
 * @author Jérémy
 */
public class PreferenceDAOTest {
    
    /**
     * Test of insertPreference method, of class PreferenceDAO.
     */
    /**
     * Test of updatePreference method, of class PreferenceDAO.
     */
    @Test
    public void testColor() {
        javafx.scene.paint.Color c = javafx.scene.paint.Color.RED;
        String col = c.toString();
        javafx.scene.paint.Color c2 = javafx.scene.paint.Color.web(col);
        Assert.assertEquals(c2, c);
        
    }

    /**
     * Test of getBACKGROUND_GANTT method, of class PreferenceDAO.
     */
    @Test
    public void testCount() {
        int count = DAO.getInstance().countPreference();
        assertEquals(count, 0);
        DAO.getInstance().insertPreferenceByDefault();
        assertEquals(DAO.getInstance().countPreference(), 1);
        
    }

}
