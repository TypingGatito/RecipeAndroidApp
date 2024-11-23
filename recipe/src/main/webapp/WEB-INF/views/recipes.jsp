<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style><%@include file="/css/sections.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEJ6b+DDc1j9/s7S5Ajo94maGGm5Ghb9v5YnxpZT0hdfotB3A6uA0NKZ0I0jG" crossorigin="anonymous">
</head>
<body>

<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h1 class="text-center">${headerText} recipes</h1>

    <div class="container mt-5">
        <div class="row">
            <c:forEach var="recipe" items="${recipes}">
                <div class="col-md-4">
                    <a href="recipe?id=${recipe.id}" class="text-decoration-none">
                        <div class="section-box text-center">
                            <h5 style="color: black;">${recipe.name}</h5>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pzjw8f+ua7Kw1TIq0f5c3bZY6dd+ojd9p9zSAIWzV0FtrZED6jHXb3g3kh5jF+6v" crossorigin="anonymous"></script>

</body>
</html>
