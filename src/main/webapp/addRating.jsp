<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String bookId = (String) request.getAttribute("bookId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Kitaba Puan Ver</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h3>Kitaba Puan Ver</h3>

<form action="AddRatingServlet" method="post" accept-charset="UTF-8">
    <input type="hidden" name="bookId" value="<%= bookId %>" />
    <div class="mb-3">
        <label for="rating" class="form-label">Puanınız (1-5)</label>
        <select id="rating" name="rating" class="form-select" required>
            <option value="">Seçiniz</option>
            <% for (int i=1; i<=5; i++) { %>
                <option value="<%= i %>"><%= i %> Yıldız</option>
            <% } %>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">Puanı Gönder</button>
    <a href="home" class="btn btn-secondary">Geri Dön</a>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
