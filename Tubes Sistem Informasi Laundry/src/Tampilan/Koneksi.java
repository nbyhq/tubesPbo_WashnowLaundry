/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tampilan;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nawa
 */
public class Koneksi {
    private Connection koneksi;
    public static void main(String[] args) {
        Connection conn = new Koneksi().Connect();
    }
    
    public Connection Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Koneksi Driver Berhasil");
        } catch (ClassNotFoundException e) {
            System.out.println("Koneksi Driver Gagal");
        }
        
        String url = "jdbc:mysql://localhost:3306/db_washnow_laundry";
        try {
            koneksi = DriverManager.getConnection(url, "root","");
            System.out.println("Database Berhasil Terkoneksi");
        } catch (SQLException e) {
            System.out.println("Database Gagal Terkoneksi");
        }
        
        return koneksi;
    }
}
