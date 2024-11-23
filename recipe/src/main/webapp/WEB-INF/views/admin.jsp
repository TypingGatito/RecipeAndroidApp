<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Page - User Management</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
  <h1 class="text-center mb-4">User Management</h1>

  <table class="table">
    <thead>
    <tr>
      <th>Login</th>
      <th>Email</th>
      <th>Status</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
      <tr>
        <td>${user.login}</td>
        <td>${user.email}</td>
        <td>
            <span class="badge ${user.isActive ? 'bg-success' : 'bg-danger'}">
                ${user.isActive ? 'Active' : 'Inactive'}
            </span>
        </td>
        <td>
          <form method="POST" action="/admin" style="display: inline;">
            <input type="hidden" name="userId" value="${user.id}">
            <input type="hidden" name="isActive" value="${user.isActive ? 'false' : 'true'}">
            <button type="submit" class="btn ${user.isActive ? 'btn-danger' : 'btn-success'}">
                ${user.isActive ? 'Deactivate' : 'Activate'}
            </button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
