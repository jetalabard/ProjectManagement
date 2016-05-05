/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagement.application.business.Equipment;
import projectmanagement.application.business.Human;
import projectmanagement.application.business.Ressource;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class RessourceDAO {

    public List<Ressource> getAllRessourceByIdTask(int idTask) {
        List<Ressource> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from RESSOURCE where " + Tags.ID_TASK + "=" + idTask + ";");
            while (result.next()) {
                if (result.getInt(Tags.TYPE) == 0) {
                    list.add(new Human(result.getInt(Tags.ID), result.getFloat(Tags.COST),
                            result.getString(Tags.NAME), result.getString(Tags.FIRSTNAME), result.getString(Tags.ROLE), idTask));
                } else {
                    list.add(new Equipment(result.getInt(Tags.ID), result.getFloat(Tags.COST), result.getString(Tags.REFERENCE), result.getString(Tags.NAME), idTask));
                }
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return list;
    }

    public void deleteRessource(int id) {
        Database.getInstance().delete("DELETE from RESSOURCE where " + Tags.ID + "=" + id + ";");
    }

    public void updateRessource(Integer id, Integer idTask, float cost, String name, String firstname, String role, String reference, int type) {
        Database.getInstance().update("UPDATE RESSOURCE set " + Tags.TYPE + "=" + type + ", "
                + "" + Tags.COST + "=" + cost + ", "
                + "" + Tags.ID_TASK + "==" + idTask + ", "
                + "" + Tags.NAME + "='" + name + "', "
                + "" + Tags.FIRSTNAME + "='" + firstname + "', "
                + "" + Tags.ROLE + "='" + role + "', "
                + "" + Tags.REFERENCE + "='" + reference + "' "
                + "WHERE " + Tags.ID + "=" + id + ";");
    }

    public int insertRessource(Integer idTask, float cost, String name, String firstname, String role, String reference, int type) {
        return Database.getInstance().insert("INSERT INTO RESSOURCE (" + Tags.TYPE + "," + Tags.COST + "," + Tags.ID_TASK + "," + Tags.NAME + "," + Tags.FIRSTNAME + "," + Tags.ROLE + "," + Tags.REFERENCE + ") "
                + "VALUES(" + type + "," + cost + "," + idTask
                + ",'" + name + "','" + firstname + "','" + role + "','" + reference + "');");
    }

    Ressource getRessource(int id) {
        Ressource p = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from Ressource where " + Tags.ID + "=" + id + ";");
            while (result.next()) {
                if(result.getInt(Tags.TYPE) == 0){
                    p = new Human(result.getInt(Tags.ID),result.getFloat(Tags.COST), result.getString(Tags.NAME),
                            result.getString(Tags.FIRSTNAME), result.getString(Tags.ROLE), result.getInt(Tags.ID_TASK));
                }
                else{
                    p = new Equipment(result.getInt(Tags.ID),result.getFloat(Tags.COST), result.getString(Tags.REFERENCE),
                            result.getString(Tags.NAME), result.getInt(Tags.ID_TASK));
               
                }
                 
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return p;
    }

    void deleteAll() {
        Database.getInstance().delete("DELETE from Ressource;");
    }

}
