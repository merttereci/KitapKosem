package com.kitapkosem.servlet;

import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        
        
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); 
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("login.jsp"); // Kayıt başarılıysa giriş sayfasına yönlendir
            } else {
                response.getWriter().write("Kayıt başarısız oldu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Hata: " + e.getMessage());
        }
    }
}
