/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jérémy
 */
public class MyDate extends Date{

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private MyDate(Date parsed) {
        super(parsed.getTime());
    }

    private MyDate() {
        super();
    }
    
    public static String valueOf(MyDate date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public static MyDate valueOf(String date) {
        Date parsed = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            parsed = format.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(MyDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyDate returnDate = new MyDate(parsed);
        return returnDate;
    }

    public static MyDate now() {
        return new MyDate();
    }
    public static String toString(MyDate date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
    
    public MyDate moreLittleDate(MyDate d1, MyDate d2){
        if(d1.before(d2)){
            return d1;
        }
        else{
            return d2;
        }
    }
    
    
    public MyDate moreBiggerDate(MyDate d1, MyDate d2){
        if(d1.after(d2)){
            return d1;
        }
        else{
            return d2;
        }
    }
}
