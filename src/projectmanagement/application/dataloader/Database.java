/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.dataloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jérémy
 */
public class Database {

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

    public int getLastInsertId() {
        int value = 0;
        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet result = stmt.executeQuery("SELECT last_insert_rowid();");
            while (result.next()) {
                value = result.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return value;
    }

    private void createTable() throws SQLException {
        Statement stmt;
        stmt = c.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS PROJECT"
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " NAME TEXT NOT NULL, "
                + " LASTUSE TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        sql = "CREATE TABLE IF NOT EXISTS TASK"
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "NAME TEXT NOT NULL,"
                + "ID_PROJECT INTEGER NOT NULL,"
                + "DATEB TEXT NOT NULL,"
                + "DATEE TEXT NOT NULL,"
                + "PRIORITY INTEGER NOT NULL,"
                + "NOTE TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS RESSOURCE"
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "NAME TEXT NOT NULL,"//name person or name equipment
                + "FIRSTNAME TEXT,"
                + "ROLE TEXT,"
                + "REFERENCE TEXT,"
                + "TYPE INTEGER NOT NULL,"//Human 0 Equipment 1
                + "IDTASK INTEGER NOT NULL,"
                + "COST FLOAT NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS PREDECESSOR"
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "TYPE TEXT,"
                + "IDTASK INTEGER NOT NULL,"
                + "GAP TEXT,"
                + "CONSTRAINT TEXT)";
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

    public void insert(String requete) {
        Statement stmt = null;
        try {
            System.out.println(requete);
            stmt = getConnection().createStatement();
            stmt.executeUpdate(requete);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

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
