<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../include/header.jsp"/>

<main>
	<div class="container">
		<div class="bg-dark p-5 rounded">
			<h1 class="display-4 fw-bold">Level Leaderboard for ${username}</h1>
		</div>
		
		<table class="table table-vm table-striped mb-5">
			<thead>
				<tr>
					<th>Level</th>
					<th>Experience</th>
					<th>User</th>
				</tr>
			</thead>
			<tbody class="accordion">
				<c:forEach items="${levelListKey}" var="level">
					<tr>
						<td>
							<span>${level.level}</span>
						</td>
						<td>
							<span>${level.experience}</span>
						</td>
						<%-- TODO this won't work --%>
						<c:forEach items="${viewerListKey}" var="viewer">
							<td>
								<img src="${viewer.twitchPFP}" alt="${viewer.twitchDisplayName}" height="40" width="40"
								     style="border-radius: 30%"/>
								<span>${viewer.twitchDisplayName}</span>
							</td>
						</c:forEach>
<%--  						<td> --%>
<%--  							<img src="${level.user.twitchPFP}" alt="${level.user.twitchDisplayName}" height="40" --%>
<%--  							     width="40" --%>
<%--  							     style="border-radius: 30%"/> --%>
<%--  							<span>${level.user.twitchDisplayName}</span> --%>
<%--  						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</main>

<jsp:include page="../include/footer.jsp"/>