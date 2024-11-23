<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>


<div class="container mt-5">
    <h1 class="text-center">Login</h1>

    <c:if test="${not empty requestScope.errorMessage}">
        <div class="alert alert-danger text-center w-50 mx-auto" role="alert">
                ${requestScope.errorMessage}
        </div>
    </c:if>

    <div class="d-flex flex-column align-items-center mt-4">
        <form action="login" method="post" class="w-50 mb-3">
            <div class="mb-3">
                <label for="email" class="form-label">Username:</label>
                <input type="text" class="form-control" id="email" name="email" value="${email}" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Email:</label>
                <input type="password" class="form-control" id="password" name="password" value="${password}" required>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary w-100">Login</button>
            </div>
        </form>

        <form method="get" action="register" class="w-50">
            <button class="btn btn-primary w-100" type="submit">Registration</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pzjw8f+ua7Kw1TIq0f5c3bZY6dd+ojd9p9zSAIWzV0FtrZED6jHXb3g3kh5jF+6v" crossorigin="anonymous"></script>

</body>
</html>
