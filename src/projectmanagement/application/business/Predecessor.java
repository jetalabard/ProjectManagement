/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.business;

import projectmanagement.application.model.MyDate;

/**
 *
 * @author Jérémy
 */
public class Predecessor {
    
    private Integer id;
    
    private String type;
    
    private MyDate gap;
    
    private String constraint;

    public Predecessor(int id,String type, MyDate gap, String constraint) {
        this.id = id;
        this.type = type;
        this.gap = gap;
        this.constraint = constraint;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public MyDate getGap() {
        return gap;
    }

    public String getConstraint() {
        return constraint;
    }
    
    
    
    
}
