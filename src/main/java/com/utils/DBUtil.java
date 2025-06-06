package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/kitap_kosem?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root"; 
    private static final String PASSWORD = "1234"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC sürücüsü
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver bulunamadı.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
