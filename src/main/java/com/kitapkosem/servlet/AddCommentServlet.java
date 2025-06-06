package com.kitapkosem.servlet;

import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {

    // GET isteği geldiğinde yorum ekleme formunu göster (örneğin addComment.jsp)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset=UTF-8");
    	
    	// Yorum yapılacak kitabın id'si URL parametre olarak gelir
        String bookId = request.getParameter("bookId");
        if (bookId == null) {
            response.sendRedirect("home");
            return;
        }
        request.setAttribute("bookId", bookId);
        request.getRequestDispatcher("addComment.jsp").forward(request, response);
    }

    // POST isteği ile formdan gelen yorumu ekle
    @Override
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
        String commentText = request.getParameter("comment");

        if (bookIdStr == null || commentText == null || commentText.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        int userId = -1;

        try (Connection conn = DBUtil.getConnection()) {
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

            String sqlInsert = "INSERT INTO comments (user_id, book_id, comment) VALUES (?, ?, ?)";
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, userId);
                psInsert.setInt(2, bookId);
                psInsert.setString(3, commentText);
                psInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("BookDetailsServlet?id=" + bookId);
    }
}
