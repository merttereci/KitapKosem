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
    <title>Yorum Ekle</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h3>Kitaba Yorum Yap</h3>

<form action="AddCommentServlet" method="post" accept-charset="UTF-8">
    <input type="hidden" name="bookId" value="<%= bookId %>"/>
    <div class="mb-3">
        <label for="comment" class="form-label">Yorumunuz</label>
        <textarea class="form-control" id="comment" name="comment" rows="4" required></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Yorum Gönder</button>
    <a href="home" class="btn btn-secondary">Geri Dön</a>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
