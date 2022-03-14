<jsp:include page="../include/header.jsp"/>

<main>
	<div class="container">
		<div class="bg-dark p-5 rounded">
			<h1 class="display-4 fw-bold">${username}'s Dashboard</h1>
			<p class="lead">Your channel is currently connected to VendyMachine</p>
		</div>
	</div>
	
	<%-- TODO add the bot enabled button here (adds STREAMER role) --%>
	
	<%-- TODO maybe have a channel album and recent stream album --%>
	<%-- Stats and Info from Twitch --%>
	<div class="album pb-5">
		<div class="container">
			<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
				<div class="col">
					<div class="card">
						<div class="card-header">
							<p class="display-3">Current Title</p>
						</div>
						<div class="card-body">
							<p class="display-6">${title}</p>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="card">
						<div class="card-header">
							<p class="display-3">Current Category</p>
						</div>
						<div class="card-body">
							<p class="display-3">${category}</p>
						</div>
					</div>
				</div>
				<%--<div class="col">--%>
				<%--	<div class="card">--%>
				<%--		<div class="card-header">--%>
				<%--			<p class="display-3">Current Viewers</p>--%>
				<%--		</div>--%>
				<%--		<div class="card-body">--%>
				<%--			<p class="display-3">${viewers}</p>--%>
				<%--		</div>--%>
				<%--	</div>--%>
				<%--</div>--%>
				<div class="col">
					<div class="card">
						<div class="card-header">
							<p class="display-3">Followers</p>
						</div>
						<div class="card-body">
							<p class="display-3">${followers}</p>
						</div>
					</div>
				</div>
				<%--<div class="col">--%>
				<%--	<div class="card">--%>
				<%--		<div class="card-header">--%>
				<%--			<p class="display-3">Subscribers</p>--%>
				<%--		</div>--%>
				<%--		<div class="card-body">--%>
				<%--			<p class="display-3">${subscribers}</p>--%>
				<%--		</div>--%>
				<%--	</div>--%>
				<%--</div>--%>
				<%--<div class="col">--%>
				<%--	<div class="card">--%>
				<%--		<div class="card-header">--%>
				<%--			<p class="display-3">Total Views</p>--%>
				<%--		</div>--%>
				<%--		<div class="card-body">--%>
				<%--			<p class="display-3">${views}</p>--%>
				<%--		</div>--%>
				<%--	</div>--%>
				<%--</div>--%>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../include/footer.jsp"/>