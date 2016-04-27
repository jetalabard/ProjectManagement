/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagement.application.business.Predecessor;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class PredecessorDAO
{

    public List<Predecessor> getAllPredecessorByIdTask(int idTask) {
        List<Predecessor> list = new ArrayList<>();
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT * from PREDECESSOR where " + Tags.ID_TASK + "=" + idTask + ";");
            while (result.next()) {
                list.add(new Predecessor(result.getInt(Tags.ID), result.getString(Tags.TYPE),
                        Integer.valueOf(result.getString(Tags.GAP)), result.getString(Tags.CONSTRAINT), result.getInt(Tags.ID_TASK), idTask));
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

    public void deletePredecessor(int id) {
        Database.getInstance().delete("DELETE from PREDECESSOR where " + Tags.ID + "=" + id + ";");
    }

    public int insertPredecessor(String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent) {
        return Database.getInstance().insert("INSERT INTO PREDECESSOR (" + Tags.TYPE + "," + Tags.ID_TASK + ", " + Tags.GAP + ", " + Tags.ID_TASK_PARENT + ", " + Tags.CONSTRAINT + ") "
                + "VALUES('" + type + "', " + idTask + ", " + gap+ ", "
                + idTaskParent + ", '" + constraint + "');");
        
    }

    public void updatePredecessor(Integer id, String type, Integer gap, String constraint, Integer idTask, Integer idTaskParent) {
        Database.getInstance().update("UPDATE PREDECESSOR set " + Tags.TYPE + "='" + type + "', "
                + "" + Tags.ID_TASK + "=" + idTask + ", "
                + "" + Tags.GAP + "=" + gap + ", "
                + "" + Tags.ID_TASK_PARENT + "=" + idTaskParent + ", "
                + "" + Tags.CONSTRAINT + "='" + constraint + "' "
                + "WHERE " + Tags.ID + "=" + id + ";");
    }

    Predecessor getPredecessor(int id) {
        Predecessor p = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from Predecessor where " + Tags.ID + "=" + id + ";");
            while (result.next()) {
                 p = new Predecessor(result.getInt(Tags.ID), result.getString(Tags.TYPE), 
                         result.getInt(Tags.GAP), result.getString(Tags.CONSTRAINT)
                         , result.getInt(Tags.ID_TASK), result.getInt(Tags.ID_TASK_PARENT));
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
        Database.getInstance().delete("DELETE from Predecessor;");
    }


}

