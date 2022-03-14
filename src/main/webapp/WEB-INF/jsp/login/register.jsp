<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../include/header.jsp"/>

<main>
	<div class="container text-center">
		<div class="bg-dark p-5 rounded">
			<h1 class="display-4 fw-bold px-2">Register</h1>
			
			<%-- Info from Twitch OAuth --%>
			<div class="py-3">
				<h2>You are registering as:</h2>
				<img src="${twitchPFP}" height="100" width="100" style="border-radius: 25%"
				     alt="${username} Profile Picture">
				<h4 class="m-3" style="display: inline">${username}</h4>
			</div>
			
			<%-- Registration Form --%>
			<form class="my-3" method="POST" action="/registerSubmit">
				<input type="hidden" name="username" value="${twitchID}">
				<label for="password"><h5>Password:</h5></label><br>
				<input  class="form-control-sm" type="password" name="password" id="password" size="30%"><br>
				<label for="confirmPass"><h5 class="mt-2">Confirm Password:</h5></label><br>
				<input  class="form-control-sm" type="password" name="confirmPass" id="confirmPass" size="30%"><br>
				<input class="btn btn-vm m-2" type="submit" value="Register">
			</form>
			
			<%-- Error Printout --%>
			<div>
				<c:forEach items="${formBeanKey.errorMessages}" var="message">
					<span style="color: red">${message}</span><br>
				</c:forEach>
			</div>
		</div>
	</div>
	
</main>

<jsp:include page="../include/footer.jsp"/>