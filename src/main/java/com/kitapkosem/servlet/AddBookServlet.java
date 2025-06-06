package com.kitapkosem.servlet;

import com.utils.DBUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 request.setCharacterEncoding("UTF-8");
    	 response.setContentType("text/html;charset=UTF-8");
    	// Kitap ekleme sayfasını göstermek için yönlendirme
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            request.getRequestDispatcher("addBook.jsp").forward(request, response);
        } else {
            // Giriş yapılmamışsa login sayfasına yönlendir
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 request.setCharacterEncoding("UTF-8");
    	 response.setContentType("text/html;charset=UTF-8");
    	// Giriş yapılmış mı kontrol et
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO books (title, author, description) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, description);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                // Başarılı, ana sayfaya yönlendir
                response.sendRedirect("home");
            } else {
                // Başarısız, hata mesajı ile form sayfasına geri dön
                request.setAttribute("errorMessage", "Kitap eklenirken bir hata oluştu.");
                request.getRequestDispatcher("addBook.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
