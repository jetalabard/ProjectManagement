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
public class MyDate extends Date {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    
    public static final long DAY = (1000*60*60*24);

    public MyDate(Date parsed) {
        super(parsed.getTime());
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(DATE_FORMAT).format(this);
    }

    public MyDate(long date) {
        super(date);
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
    
    public static long diff(MyDate date1,MyDate date2){
        return date2.getTime() - date1.getTime();
    }
    
    public static long days(long number){
        return number / DAY;
    }
    
    public static long diffDays(MyDate date1,MyDate date2){
        long number = (date2.getTime() - date1.getTime() )/ DAY;
        return number > 0 ? number : 0;
    }
    
    public boolean after(MyDate date){
        return this.after(new Date(date.getTime()));
    }
    
    public boolean before(MyDate date){
        return this.before(new Date(date.getTime()));
    }
    
    
    
}