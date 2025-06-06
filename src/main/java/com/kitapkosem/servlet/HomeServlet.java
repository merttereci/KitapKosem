package com.kitapkosem.servlet;

import com.kitapkosem.model.Book;
import com.utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        String search = request.getParameter("search");

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt;

            if (search != null && !search.trim().isEmpty()) {
                stmt = conn.prepareStatement("SELECT id, title, author, description FROM books WHERE LOWER(title) LIKE ?");
                stmt.setString(1, "%" + search.toLowerCase() + "%");
            } else {
                stmt = conn.prepareStatement("SELECT id, title, author, description FROM books");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setDescription(rs.getString("description"));
                books.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("bookList", books);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
