/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jérémy
 */
public class MyDateTest {
    
    public MyDateTest() {
    }

    /**
     * Test of diff method, of class MyDate.
     */
    @Test
    public void testDiff() {
        
        assertEquals(MyDate.diff(MyDate.now(), MyDate.now()),0);
    }
    
    @Test
    public void testDiff2(){
        MyDate date1 = new MyDate(new Date(1000));
        MyDate date2 = new MyDate(new Date(2000));
        assertEquals(MyDate.diff(date1,date2),1000);
    }
    
    @Test
    public void testDiff3(){
        MyDate date1 = new MyDate(new Date(1000*60*60*24));
        MyDate date2 = new MyDate(new Date(1000*60*60*24*2));
        assertEquals(MyDate.diff(date1,date2) / (1000*60*60*24),1);
    }
    @Test
    public void testAfter(){
        MyDate date1 = new MyDate(new Date(1000*60*60*24));
        MyDate date2 = new MyDate(new Date(1000*60*60*24*2));
        assertEquals(date1.after(date2),false);
        assertEquals(date2.after(date1),true);
    }
    @Test
    public void testBefore(){
        MyDate date1 = new MyDate(new Date(1000*60*60*24));
        MyDate date2 = new MyDate(new Date(1000*60*60*24*2));
        assertEquals(date1.before(date2),true);
        assertEquals(date2.before(date1),false);
    }
    
}
