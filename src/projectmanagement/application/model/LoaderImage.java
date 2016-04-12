/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

/**
 *
 * @author Jérémy
 */
public class LoaderImage 
{
    private static final String folder ="ressources/";
    
    public static String getImage(String name){
        return folder +name;
    }
}
