package com.example.manajemen_penggajian;

import java.sql.*;

public class DBConnect {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;

    public DBConnect() {
        try{
            String url = "jdbc:sqlserver://localhost;databaseName=Apotek;user=sa;password=polman";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        } catch (Exception ex) {
            System.out.println("Error saat connect database: " + ex);
        }
    }
}
