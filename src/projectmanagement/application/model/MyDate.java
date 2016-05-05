/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    
    public Date convertToDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(this);
        return new GregorianCalendar(cal.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH).getTime();
    }
    
    

    public MyDate() {
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
        Date my =  new Date(this.getTime());
        return my.before(new Date(date.getTime()));
    }
     public static int numberDayOfMonth(MyDate date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH);
        
        Calendar mycal = new GregorianCalendar(year,month,1);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    public static MyDate addMonth(MyDate temp){
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        int year =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        if(month==12){
            year =cal.get(Calendar.YEAR)+1;
            month=0;
        }
        Calendar calendar = new GregorianCalendar(year,month,1);
        return new MyDate(calendar.getTime());
    }
    
    @Override
    public int getMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(this);
        return cal.get(Calendar.MONTH);
    }
    public int day(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(this);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }
    
    
    
}