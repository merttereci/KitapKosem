package com.test;

import com.utils.DBUtil;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            System.out.println("Bağlantı başarılı!");
        } catch (Exception e) {
            System.out.println("Bağlantı Hatası: " + e.getMessage());
        }
    }
}
