<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kitapkosem.model.Book" %>
<%
    String username = (String) session.getAttribute("username");
    String search = request.getParameter("search");
%>
<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title>KitapKöşem - Ana Sayfa</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .card-text {
            max-height: 80px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
    </style>
</head>
<body>


<nav class="navbar navbar-light bg-light px-4">
    <a class="navbar-brand" href="home">📚 KitapKöşem</a>
    <div class="d-flex">
        <% if (username != null) { %>
            <span class="navbar-text me-3">Hoş geldiniz, <strong><%= username %></strong></span>
            <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Çıkış Yap</a>
        <% } else { %>
            <a href="login.jsp" class="btn btn-outline-primary btn-sm me-2">Giriş Yap</a>
            <a href="register.jsp" class="btn btn-primary btn-sm">Kayıt Ol</a>
        <% } %>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="mb-4">📖 Kitap Listesi</h2>

    
    <form action="home" method="get" class="mb-4">
        <div class="input-group">
            <input type="text" name="search" class="form-control" placeholder="Kitap adına göre ara..." 
                   value="<%= search != null ? search : "" %>">
            <button type="submit" class="btn btn-primary">🔍 Ara</button>
            <% if (search != null && !search.isEmpty()) { %>
                <a href="home" class="btn btn-outline-secondary">Temizle</a>
            <% } %>
        </div>
    </form>

   
    <div class="row">
        <%
            List<Book> books = (List<Book>) request.getAttribute("bookList");
            if (books != null && !books.isEmpty()) {
                for (Book b : books) {
        %>
        <div class="col-md-4 mb-4">
            <div class="card h-100 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title"><%= b.getTitle() %></h5>
                    <h6 class="card-subtitle mb-2 text-muted">Yazar: <%= b.getAuthor() %></h6>
                    <p class="card-text"><%= b.getDescription().length() > 100 ? b.getDescription().substring(0, 100) + "..." : b.getDescription() %></p>
                </div>
                <div class="card-footer bg-transparent border-top-0 d-flex justify-content-between">
                    <a href="BookDetailsServlet?id=<%= b.getId() %>" class="btn btn-sm btn-outline-primary">Detaylar</a>
                    <% if (username != null) { %>
                        <div>
                            <a href="AddCommentServlet?bookId=<%= b.getId() %>" class="btn btn-sm btn-outline-success">Yorum</a>
                            <a href="AddRatingServlet?bookId=<%= b.getId() %>" class="btn btn-sm btn-outline-warning">Puan</a>
                        </div>
                    <% } else { %>
                        <div>
                            <a href="register.jsp" class="btn btn-sm btn-outline-secondary">Yorum (Giriş)</a>
                            <a href="register.jsp" class="btn btn-sm btn-outline-secondary">Puan (Giriş)</a>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
        <%
                }
            } else {
        %>
        <div class="col-12">
            <div class="alert alert-info">Kitap bulunamadı.</div>
        </div>
        <% } %>
    </div>

    <!-- Yeni Kitap Ekle Butonu -->
    <div class="text-end mt-4">
        <% if (username != null) { %>
            <a href="AddBookServlet" class="btn btn-lg btn-success">+ Yeni Kitap Ekle</a>
        <% } else { %>
            <a href="register.jsp" class="btn btn-lg btn-success">+ Yeni Kitap Ekle (Giriş gerekli)</a>
        <% } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
