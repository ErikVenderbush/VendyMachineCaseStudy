<jsp:include page="../include/header.jsp"/>

<main>
	<div class="container">
		<div class="bg-dark p-5 rounded">
			<h1 class="display-4 fw-bold">Send Feedback</h1>
		</div>
		
		<form class="my-3" method="POST" action="/sendFeedback">
			<input type="hidden" name="username" value="${username}">
			<label for="category"><h5>Category:</h5></label>
			<select class="form-select-sm" name="category" id="category" width="100%">
				<option value="Bug/Issue">Bug/Issue</option>
				<option value="Feedback">Feedback</option>
				<option value="Suggestion">Suggestion</option>
			</select><br>
			<label for="subject"><h5>Subject:</h5></label><br>
			<input  class="form-control-sm" type="text" name="subject" id="subject" style="width: 52%"><br>
			<label for="body"><h5>Body:</h5></label><br>
			<textarea class="form-control-sm" name="body" id="body" style="width: 52%"></textarea><br>
			<input class="btn btn-vm m-2" type="submit" value="Send Feedback">
		</form>
		<div>
			<p class="lead mb-4">${feedbackSuccess}</p>
		</div>
	</div>
</main>

<jsp:include page="../include/footer.jsp"/>