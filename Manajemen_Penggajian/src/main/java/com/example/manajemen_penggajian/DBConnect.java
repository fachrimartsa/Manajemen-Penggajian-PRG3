package com.example.manajemen_penggajian;

import java.sql.*;

public class DBConnect {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;

    public DBConnect() {
        try{
            String url = "jdbc:sqlserver://localhost;databaseName=Manajemen_Penggajian;user=sa;password=polman";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        } catch (Exception ex) {
            System.out.println("Error saat connect database: " + ex);
        }
    }
    public static void main (String args[]){
        try{
            DBConnect connect = new DBConnect();
            System.out.println("Connect Berhasil");
        }catch (Exception ex){
            System.out.println("Connect Gagal :" +ex);
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
