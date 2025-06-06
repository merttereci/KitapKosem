<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="com.kitapkosem.model.Book" %>
<%@ page import="java.util.*" %>

<%
    Book book = (Book) request.getAttribute("book");
    List<Map<String, String>> comments = (List<Map<String, String>>) request.getAttribute("comments");
    Double averageRating = (Double) request.getAttribute("averageRating");
    Integer totalVotes = (Integer) request.getAttribute("totalVotes");
%>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title><%= book.getTitle() %> - Kitap Detayı</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h2><%= book.getTitle() %></h2>
<p><strong>Yazar:</strong> <%= book.getAuthor() %></p>
<p><strong>Açıklama:</strong> <%= book.getDescription() %></p>

<hr>
<h4>Ortalama Puan: <%= String.format("%.2f", averageRating) %> ⭐ (<%= totalVotes %> oy)</h4>

<hr>
<h4>Yorumlar:</h4>
<% if (comments.isEmpty()) { %>
    <p>Henüz yorum yapılmamış.</p>
<% } else { %>
    <ul class="list-group">
    <% for (Map<String, String> comment : comments) { %>
        <li class="list-group-item">
            <strong><%= comment.get("username") %>:</strong> <%= comment.get("comment") %>
        </li>
    <% } %>
    </ul>
<% } %>

<a href="home" class="btn btn-secondary mt-3">Anasayfaya Dön</a>

</body>
</html>
