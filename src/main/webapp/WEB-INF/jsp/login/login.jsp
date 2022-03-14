<jsp:include page="../include/header.jsp"/>

<main>
	<div class="container text-center">
		<div class="bg-dark p-5 rounded">
			<h1 class="display-4 fw-bold px-2">Login</h1>
			
			<div class="py-3">
				<h2>You are logging in as:</h2>
				<img src="${twitchPFP}" height="100" width="100" style="border-radius: 25%"
				     alt="${username} Profile Picture">
				<h4 class="m-3" style="display: inline">${username}</h4>
			</div>
			<%-- TODO test after security setup --%>
			<form class="my-3" method="POST" action="/loginSubmit">
				<input type="hidden" name="username" value="${twitchID}">
				<label for="password"><h5>Password:</h5></label><br>
				<input  class="form-control-sm" type="password" name="password" id="password" size="30%"><br>
				<input class="btn btn-vm m-2" type="submit" value="Login">
			</form>
		</div>
	</div>
</main>

<jsp:include page="../include/footer.jsp"/>