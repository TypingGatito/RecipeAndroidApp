<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navigation Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="/">Sections</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="recipes">Dishes</a>
                </li>

                <c:if test="${not empty username}">
                    <li class="nav-item">
                        <a class="nav-link" href="/recipes?type=favourite">Favourite dishes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="account">Account</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.role == 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="admin">Admin</a>
                    </li>
                </c:if>
            </ul>


            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <span class="navbar-brand mb-0 h1">${sessionScope.username}</span>
                    <form method="get" action="logout">
                        <button class="btn btn-outline-danger" type="submit">Checkout</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="get" action="login">
                        <button class="btn btn-outline-primary" type="submit">Login</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
