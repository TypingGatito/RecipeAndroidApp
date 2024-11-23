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
                                    <li>
                                            ${commentary.text}
                                        <c:if test="${not empty sessionScope.username}">
                                            <button class="btn btn-link" data-bs-toggle="modal" data-bs-target="#editCommentaryModal"
                                                    data-step-id="${step.id}"
                                                    data-commentary-text="${commentary.text}">Edit</button>
                                        </c:if>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:forEach>

            </ol>

            <div class="modal fade" id="addCommentaryModal" tabindex="-1" aria-labelledby="addCommentaryModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="addCommentary" method="post">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addCommentaryModalLabel">Add a Commentary</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="commentaryText" class="form-label">Commentary:</label>
                                    <textarea class="form-control" id="commentaryText" name="text" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="stepId" id="addStepId">
                                <button type="submit" class="btn btn-primary">Save</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="editCommentaryModal" tabindex="-1" aria-labelledby="editCommentaryModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="updateCommentary" method="post">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editCommentaryModalLabel">Edit Commentary</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="editCommentaryText" class="form-label">Commentary:</label>
                                    <textarea class="form-control" id="editCommentaryText" name="text" rows="3"></textarea>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="commentaryId" id="editCommentaryId">
                                <input type="hidden" name="stepId" id="editStepId">
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <script>

                $('#addCommentaryModal').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var stepId = button.data('step-id');
                    var modal = $(this);
                    modal.find('#addStepId').val(stepId);
                });

                $('#editCommentaryModal').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var stepId = button.data('step-id');
                    var commentaryId = button.data('commentary-id');
                    var commentaryText = button.data('commentary-text');
                    var modal = $(this);
                    modal.find('#editStepId').val(stepId);
                    modal.find('#editCommentaryId').val(commentaryId);
                    modal.find('#editCommentaryText').val(commentaryText);
                });
            </script>

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
                                    <input type="radio" name="rating" value="${i}" ${i == myRating ? 'checked' : ''} hidden>
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
</body>
</html>
