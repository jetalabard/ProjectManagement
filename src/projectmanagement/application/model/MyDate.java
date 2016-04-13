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

    public MyDate(Date parsed) {
        super(parsed.getTime());
    }

    @Override
    public String toString() {
        return new SimpleDateFormat(DATE_FORMAT).format(this);
    }

    private MyDate() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getTime() == ((MyDate)obj).getTime();
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

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
    
}