/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectmanagement.application.model.DAO;
import projectmanagement.ihm.controller.Tags;

/**
 *
 * @author Jérémy
 */
public final class Database {

    private static Database instance;
    private Connection c = null;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {
        try {
            getConnection();
            createTable();
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createTable() throws SQLException {
        Statement stmt;
        stmt = c.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS PROJECT"
                + "(" + Tags.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " " + Tags.NAME + " TEXT NOT NULL, "
                + " " + Tags.LASTUSE + " TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE IF NOT EXISTS TASK"
                + "(" + Tags.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "" + Tags.NAME + " TEXT NOT NULL,"
                + "" + Tags.ID_PROJECT + " INTEGER NOT NULL,"
                + "" + Tags.DATE_BEGIN + " TEXT NOT NULL,"
                + "" + Tags.DATE_END + " TEXT NOT NULL,"
                + "" + Tags.PRIORITY + " INTEGER NOT NULL,"
                + "" + Tags.NOTE + " TEXT)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS RESSOURCE"
                + "(" + Tags.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "" + Tags.NAME + " TEXT NOT NULL,"//name person or name equipment
                + "" + Tags.FIRSTNAME + " TEXT,"
                + "" + Tags.ROLE + " TEXT,"
                + "" + Tags.REFERENCE + " TEXT,"
                + "" + Tags.TYPE + " INTEGER NOT NULL,"//Human 0 Equipment 1
                + "" + Tags.ID_TASK + " INTEGER NOT NULL,"
                + "" + Tags.COST + " FLOAT NOT NULL)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS PREDECESSOR"
                + "(" + Tags.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + " " + Tags.TYPE + " TEXT,"
                + " " + Tags.ID_TASK + " INTEGER NOT NULL,"
                + " " + Tags.ID_TASK_PARENT + " INTEGER NOT NULL,"
                + " " + Tags.GAP + " INTEGER NOT NULL,"
                + " " + Tags.CONSTRAINT + " TEXT)";
        stmt.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS PREFERENCE"
                + "(" + Tags.ID + " INTEGER PRIMARY KEY NOT NULL,"    
                + " " + Tags.BACKGROUND_GANTT + " TEXT NOT NULL,"
                + " " + Tags.OBJECT_GANTT + " TEXT NOT NULL,"
                + " " + Tags.TEXT_GANTT + " TEXT NOT NULL,"
                + " " + Tags.BACKGROUND_PERT + " TEXT NOT NULL,"
                + " " + Tags.OBJECT_PERT + " TEXT NOT NULL,"
                + " " + Tags.TEXT_PERT + " TEXT NOT NULL,"
                + " " + Tags.OBJECT_CRITICAL_PERT + " TEXT NOT NULL,"
                + " " + Tags.TEXT_CRITICAL_PERT + " TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        
        
        stmt.close();
    }

    public Connection getConnection() {
        if (c != null) {
            return c;
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:projectmanagement.sqlite");
                c.setAutoCommit(false);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            return c;
        }
    }

    public int insert(String requete) {
        PreparedStatement stmt;
        long returnValue = -1;
        try {
            stmt = getConnection().prepareStatement(requete,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                returnValue = generatedKeys.getLong(1);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return (int) returnValue;

    }

    public void update(String requete) {
        try {
            try (Statement stmt = getConnection().createStatement()) {
                stmt.executeUpdate(requete);
            }
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

    }

    public void delete(String requete) {
        try {
            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(requete);
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

    }

    public void closeConnection() {
        try {
            getConnection().commit();
            c.setAutoCommit(true);
            c.close();
            c = null;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
