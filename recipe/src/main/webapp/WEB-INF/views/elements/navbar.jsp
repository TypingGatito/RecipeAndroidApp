<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navigation Panel</title>
    <!-- Подключение Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <!-- Ник пользователя -->
        <span class="navbar-brand mb-0 h1">${nickname}</span>

        <!-- Кнопка переключения для мобильных устройств -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Навигационные элементы -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Страница</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Блюда</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Избранные блюда</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Профиль</a>
                </li>
                <!-- Только для администратора -->
                <c:if test="${role == 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="#">Админ панель</a>
                    </li>
                </c:if>
            </ul>
            <!-- Кнопка выхода -->
            <form method="post" action="/logout">
                <button class="btn btn-outline-danger" type="submit">Выход</button>
            </form>
        </div>
    </div>
</nav>

<!-- Подключение Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
