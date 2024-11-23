<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Recipe Details</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">

  <div class="card">
    <div class="card-header bg-primary text-white">
      <h2 class="card-title">${recipe.name}</h2>
    </div>

    <div class="card-body">
    <p>
      <strong>Rating:</strong>
      <span class="rating-stars">
        <c:forEach var="i" begin="1" end="${rating}">
          <i class="fas fa-star text-warning"></i>
        </c:forEach>
        <c:forEach var="i" begin="${rating + 1}" end="5">
          <i class="fas fa-star text-muted"></i>
        </c:forEach>
      </span>
    </p>
    </div>

    <div class="card-body">
      <div class="row">
        <div class="col-md-6">
          <p><strong>Calories per 100g:</strong> ${recipe.caloriesOnHundG}</p>
          <p><strong>Time to cook:</strong> ${recipe.timeToCook.toHours()} hours ${(recipe.timeToCook.toMinutes() % 60)} minutes</p>
          <p><strong>Servings:</strong> ${recipe.doseNum}</p>
        </div>
        <div class="col-md-6">
          <p><strong>Description:</strong> ${recipe.shortDescription}</p>
          <p><strong>Created at:</strong> ${recipe.createdAt}</p>
        </div>
      </div>

      <hr>

      <h3>Ingredients</h3>
      <table class="table">
        <thead>
        <tr>
          <th>Name</th>
          <th>Amount</th>
          <th>Unit</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ingredient" items="${ingredients}">
          <tr>
            <td>${ingredient.name}</td>
            <td>${ingredient.amount}</td>
            <td>${ingredient.unit}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>

      <hr>

      <h3>Steps</h3>
      <ol>
        <c:forEach var="step" items="${steps}">
          <li>
              ${step.text}
            <h5>Commentaries:</h5>
            <ul>
              <c:forEach var="commentary" items="${commentaries}">
                <c:if test="${commentary.stepId == step.id}">
                  <li> ${commentary.text}</li>
                </c:if>
              </c:forEach>
            </ul>
          </li>
        </c:forEach>
      </ol>

      <hr>

      <div class="text-center mt-3">
        <a href="recipes" class="btn btn-secondary">Back to Recipes List</a>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
