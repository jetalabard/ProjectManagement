/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public class PreferenceDAO {

    void insertPreference(Color BACKGROUND_GANTT, Color OBJECT_GANTT, Color TEXT_GANTT,
            Color BACKGROUND_PERT, Color OBJECT_PERT, Color TEXT_PERT, Color OBJECT_CRITICAL_PERT,
            Color TEXT_CRITICAL_PERT) {
        String requete = "INSERT INTO PREFERENCE (" + Tags.BACKGROUND_GANTT + ","
                + Tags.OBJECT_GANTT + ", " + Tags.TEXT_GANTT + ", " + Tags.BACKGROUND_PERT + ", "
                + Tags.OBJECT_PERT + ", " + Tags.TEXT_PERT + ", " + Tags.OBJECT_CRITICAL_PERT + ", "
                + Tags.TEXT_CRITICAL_PERT + ") "
                + "VALUES('" + BACKGROUND_GANTT.toString() + "', '" + OBJECT_GANTT.toString() + "', '"
                + TEXT_GANTT.toString() + "', '" + BACKGROUND_PERT.toString() + "', '"
                + OBJECT_PERT.toString() + "', '" + TEXT_PERT.toString() + "', '"
                + OBJECT_CRITICAL_PERT.toString() + "', '" + TEXT_CRITICAL_PERT.toString() + "');";

        Database.getInstance().insert(requete);
    }

    void updatePreference(int id, Color BACKGROUND_GANTT, Color OBJECT_GANTT, Color TEXT_GANTT, Color BACKGROUND_PERT, Color OBJECT_PERT, Color TEXT_PERT, Color OBJECT_CRITICAL_PERT, Color TEXT_CRITICAL_PERT) {
        Database.getInstance().update("UPDATE PREFERENCE set "
                + Tags.BACKGROUND_GANTT + "='" + BACKGROUND_GANTT.toString() + "', "
                + Tags.OBJECT_GANTT + "='" + OBJECT_GANTT.toString() + "', "
                + Tags.TEXT_GANTT + "='" + TEXT_GANTT.toString() + "', "
                + Tags.BACKGROUND_PERT + "='" + BACKGROUND_PERT.toString() + "', "
                + Tags.OBJECT_PERT + "='" + OBJECT_PERT.toString() + "', "
                + Tags.TEXT_PERT + "='" + TEXT_PERT.toString() + "', "
                + Tags.OBJECT_CRITICAL_PERT + "='" + OBJECT_CRITICAL_PERT.toString() + "', "
                + Tags.TEXT_CRITICAL_PERT + "='" + TEXT_CRITICAL_PERT.toString() + "' "
                + "WHERE " + Tags.ID + "=" + id + ";");

    }
    

    public Color getBACKGROUND_GANTT(int id) {
        return findResultColumn(id, Tags.BACKGROUND_GANTT);
    }

    private Color findResultColumn(int id, String tags) {
        Color color = null;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select * from PREFERENCE where " + Tags.ID + "=" + id + ";");
            while (result.next()) {
                String col = result.getString(tags);
                return Color.web(col);
            }
            result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Database.getInstance().closeConnection();
        }
        return color;
    }

    public Color getOBJECT_GANTT(int id) {
        return findResultColumn(id, Tags.OBJECT_GANTT);
    }

    public Color getOBJECT_CRITICAL_PERT(int id) {
        return findResultColumn(id, Tags.OBJECT_CRITICAL_PERT);
    }

    public Color getTEXT_CRITICAL_PERT(int id) {
        return findResultColumn(id, Tags.TEXT_CRITICAL_PERT);
    }

    public Color getTEXT_PERT(int id) {
        return findResultColumn(id, Tags.TEXT_PERT);
    }

    public Color getOBJECT_PERT(int id) {
        return findResultColumn(id, Tags.OBJECT_PERT);
    }

    public Color getBACKGROUND_PERT(int id) {
        return findResultColumn(id, Tags.BACKGROUND_PERT);
    }

    public Color getTEXT_GANTT(int id) {
        return findResultColumn(id, Tags.TEXT_GANTT);
    }

    int countPreference() {
        int count =0;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("Select count(*) from PREFERENCE;");
            while (result.next()) {
                count = result.getInt(1);
            }
             result.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Database.getInstance().closeConnection();
        }
        return count;

    }

}
