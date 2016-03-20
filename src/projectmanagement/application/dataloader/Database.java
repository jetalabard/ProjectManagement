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

    private void createTable() throws SQLException {
        Statement stmt;
        stmt = c.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS PROJECT"
                + "(ID INT PRIMARY KEY NOT NULL,"
                + " NAME TEXT NOT NULL, "
                + " PATH TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        stmt.close();
        stmt = c.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS TASK"
                + "(ID INT PRIMARY KEY NOT NULL,"
                + "NAME TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        stmt.close();
        stmt = c.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS RESSOURCE"
                + "(ID INT PRIMARY KEY NOT NULL,"
                + "NAME TEXT NOT NULL)";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public Connection getConnection() {
        if(c != null){
            return c;
        }
        else{
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:projectmanagement.db");
                c.setAutoCommit(false);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            return c;
        }
    }
    
    public void insert(String requete){
        try {
            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(requete);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public ResultSet select(String requete) {
        ResultSet rs = null;
        try {
            Statement stmt = getConnection().createStatement();
            rs = stmt.executeQuery(requete);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;

    }
    
      public void update(String requete){
        try {
            try (Statement stmt = getConnection().createStatement()) {
                stmt.executeUpdate(requete);
            }
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
      public void delete(String requete){
        try {
            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(requete);
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void closeConnection() {
        try {
            c.commit();
            c.setAutoCommit(true);
            c.close();
            c = null;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
