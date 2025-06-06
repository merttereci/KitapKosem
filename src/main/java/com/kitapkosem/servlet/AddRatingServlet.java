package com.kitapkosem.servlet;

import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AddRatingServlet")
public class AddRatingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String bookIdStr = request.getParameter("bookId");
        String ratingStr = request.getParameter("rating");

        if (bookIdStr == null || ratingStr == null) {
            response.sendRedirect("home");
            return;
        }

        int bookId, rating;
        try {
            bookId = Integer.parseInt(bookIdStr);
            rating = Integer.parseInt(ratingStr);
            if (rating < 1 || rating > 5) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            response.sendRedirect("BookDetailsServlet?id=" + bookIdStr);
            return;
        }

        int userId = -1;

        try (Connection conn = DBUtil.getConnection()) {
            // Kullanıcı id'sini bul
            String sqlUser = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, username);
                try (ResultSet rs = psUser.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");
                    } else {
                        response.sendRedirect("login.jsp");
                        return;
                    }
                }
            }

            // Eğer kullanıcı zaten puan vermişse güncelle, yoksa ekle
            String sqlCheck = "SELECT id FROM ratings WHERE user_id = ? AND book_id = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, userId);
                psCheck.setInt(2, bookId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        // Güncelle
                        String sqlUpdate = "UPDATE ratings SET rating = ? WHERE user_id = ? AND book_id = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                            psUpdate.setInt(1, rating);
                            psUpdate.setInt(2, userId);
                            psUpdate.setInt(3, bookId);
                            psUpdate.executeUpdate();
                        }
                    } else {
                        // Yeni kayıt ekle
                        String sqlInsert = "INSERT INTO ratings (user_id, book_id, rating) VALUES (?, ?, ?)";
                        try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                            psInsert.setInt(1, userId);
                            psInsert.setInt(2, bookId);
                            psInsert.setInt(3, rating);
                            psInsert.executeUpdate();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("BookDetailsServlet?id=" + bookId);
    }

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Form için
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String bookId = request.getParameter("bookId");
        if (bookId == null) {
            response.sendRedirect("home");
            return;
        }

        request.setAttribute("bookId", bookId);
        request.getRequestDispatcher("addRating.jsp").forward(request, response);
    }
}
