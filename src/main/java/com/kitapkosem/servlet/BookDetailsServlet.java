package com.kitapkosem.servlet;

import com.kitapkosem.model.Book;
import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/BookDetailsServlet")
public class BookDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Türkçe karakter desteği
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String bookIdStr = request.getParameter("id");
        if (bookIdStr == null) {
            response.sendRedirect("home");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        Book book = null;
        List<Map<String, String>> comments = new ArrayList<>();
        double averageRating = 0.0;
        int totalVotes = 0;

        try (Connection conn = DBUtil.getConnection()) {
            String sqlBook = "SELECT * FROM books WHERE id = ?";
            try (PreparedStatement psBook = conn.prepareStatement(sqlBook)) {
                psBook.setInt(1, bookId);
                try (ResultSet rs = psBook.executeQuery()) {
                    if (rs.next()) {
                        book = new Book();
                        book.setId(rs.getInt("id"));
                        book.setTitle(rs.getString("title"));
                        book.setAuthor(rs.getString("author"));
                        book.setDescription(rs.getString("description"));
                    }
                }
            }

            if (book == null) {
                response.sendRedirect("home");
                return;
            }

            String sqlComments = "SELECT c.comment, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.book_id = ?";
            try (PreparedStatement psComments = conn.prepareStatement(sqlComments)) {
                psComments.setInt(1, bookId);
                try (ResultSet rs = psComments.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> comment = new HashMap<>();
                        comment.put("username", rs.getString("username"));
                        comment.put("comment", rs.getString("comment"));
                        comments.add(comment);
                    }
                }
            }

            String sqlRating = "SELECT AVG(rating) AS avgRating, COUNT(*) AS totalVotes FROM ratings WHERE book_id = ?";
            try (PreparedStatement psRating = conn.prepareStatement(sqlRating)) {
                psRating.setInt(1, bookId);
                try (ResultSet rs = psRating.executeQuery()) {
                    if (rs.next()) {
                        averageRating = rs.getDouble("avgRating");
                        totalVotes = rs.getInt("totalVotes");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("book", book);
        request.setAttribute("comments", comments);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("totalVotes", totalVotes);

        request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
    }
}
