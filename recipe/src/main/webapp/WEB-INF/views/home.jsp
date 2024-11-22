<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
</head>
<body>

<%@ include file="elements/navbar.jsp" %>

<h1>Welcome, ${username}!</h1>
<a href="logout">Logout</a>

</body>
</html>
