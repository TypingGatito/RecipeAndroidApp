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
                <c:choose>
                    <c:when test="${not empty sessionScope.username}">
                        <span class="rating-stars" role="button" data-bs-toggle="modal" data-bs-target="#ratingModal">
                            <c:forEach var="i" begin="1" end="${rating}">
                                <i class="fas fa-star text-warning"></i>
                            </c:forEach>
                            <c:forEach var="i" begin="${rating + 1}" end="5">
                                <i class="fas fa-star text-muted"></i>
                            </c:forEach>
                        </span>
                        <small class="text-muted">(Click to rate)</small>
                    </c:when>

                    <c:otherwise>
                        <span class="rating-stars">
                            <c:forEach var="i" begin="1" end="${rating}">
                                <i class="fas fa-star text-warning"></i>
                            </c:forEach>
                            <c:forEach var="i" begin="${rating + 1}" end="5">
                                <i class="fas fa-star text-muted"></i>
                            </c:forEach>
                        </span>
                        <small class="text-muted">(Login to rate)</small>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>

        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <p><strong>Calories per 100g:</strong> ${recipe.caloriesOnHundG}</p>
                    <p><strong>Time to cook:</strong> ${recipe.timeToCook.toHours()}
                        hours ${(recipe.timeToCook.toMinutes() % 60)} minutes</p>
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
                            <li>
                                    ${commentary.text}

                                <c:if test="${not empty sessionScope.username}">
                                <button class="btn btn-primary btn-delete-commentary"
                                        data-step-id="${step.id}"
                                        data-order-num="${commentary.orderNum}"
                                        onclick="deleteCommentary(this)">
                                    Delete
                                </button>

                                </c:if>
                                <c:if test="${not empty sessionScope.username}">
                                <button class="btn btn-primary btn-edit-commentary" data-step-id="${step.id}"
                                        data-order-num="${commentary.orderNum}" data-commentary-text="${commentary.text}" data-bs-toggle="modal"
                                        data-bs-target="#addCommentaryModal">
                                    Edit
                                </button>
                                </c:if>
                                </c:if>
                                </c:forEach>
                                <c:if test="${not empty sessionScope.username}">
                                <button class="btn btn-primary btn-add-commentary" data-step-id="${step.id}"
                                        data-bs-toggle="modal" data-bs-target="#addCommentaryModal">
                                    Add Commentary
                                </button>
                                </c:if>
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

<c:if test="${not empty sessionScope.username}">
    <div class="modal fade" id="ratingModal" tabindex="-1" aria-labelledby="ratingModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="updateRating" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="ratingModalLabel">Rate Recipe</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Your current rating:</p>
                        <div class="rating-stars">
                            <c:forEach var="i" begin="1" end="5">
                                <label>
                                    <input type="radio" name="rating" value="${i}" ${i == myRating ? 'checked' : ''}
                                           hidden>
                                    <i class="fas fa-star ${i <= myRating ? 'text-warning' : 'text-muted'}"></i>
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" name="recipeId" value="${recipe.id}">
                        <button type="submit" class="btn btn-primary">Save Rating</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>

<div class="modal fade" id="addCommentaryModal" tabindex="-1" aria-labelledby="addCommentaryModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="addCommentary" method="post" onsubmit="handleCommentarySubmit(event)">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCommentaryModalLabel">Add Commentary</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="addStepId" name="stepId">
                    <input type="hidden" id="commentaryOrderNum" name="orderNum">
                    <div class="mb-3">
                        <label for="commentaryText" class="form-label">Your Commentary</label>
                        <textarea class="form-control" id="commentaryText" name="commentaryText" rows="4"
                                  required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Save Commentary</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelectorAll('.rating-stars i').forEach(star => {
        star.addEventListener('click', function () {
            const stars = this.parentElement.parentElement.querySelectorAll('i');
            stars.forEach((s, index) => {
                s.classList.toggle('text-warning', index <= Array.from(stars).indexOf(this));
                s.classList.toggle('text-muted', index > Array.from(stars).indexOf(this));
            });
        });
    });
</script>


<script>
    document.addEventListener("DOMContentLoaded", function () {
        const addCommentaryModal = document.getElementById("addCommentaryModal");

        const addModalInstance = new bootstrap.Modal(addCommentaryModal);

        document.querySelectorAll(".btn-add-commentary").forEach(button => {
            button.addEventListener("click", event => {
                const stepId = button.getAttribute("data-step-id");

                document.getElementById("addStepId").value = stepId;
                document.getElementById("commentaryOrderNum").value = "";
                document.getElementById("commentaryText").value = "";

                addModalInstance.show();
            });
        });

        document.querySelectorAll(".btn-delete-commentary").forEach(button => {
            button.addEventListener("click", event => {
                const stepId = button.getAttribute("data-step-id");

                document.getElementById("addStepId").value = stepId;

                addModalInstance.show();
            });
        });

        document.querySelectorAll(".btn-edit-commentary").forEach(button => {
            button.addEventListener("click", event => {
                const stepId = button.getAttribute("data-step-id");
                const orderNum = button.getAttribute("data-order-num");
                const text = button.getAttribute("data-commentary-text");

                document.getElementById("addStepId").value = stepId;
                document.getElementById("commentaryOrderNum").value = orderNum;
                document.getElementById("commentaryText").value = text;

                addModalInstance.show();
            });
        });

    });

</script>

<script>
    function deleteCommentary(button) {
        var stepId = button.getAttribute("data-step-id");
        var orderNum = button.getAttribute("data-order-num");

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", "/addCommentary?stepId=" + stepId + "&stepOrderNum=" + orderNum, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function () {
            if (xhr.status === 200) {
                location.reload();
            } else {
                alert("Error deleting commentary.");
            }
        };

        xhr.onerror = function () {
            alert("Request failed.");
        };

        xhr.send();
    }

</script>>


</body>
</html>
