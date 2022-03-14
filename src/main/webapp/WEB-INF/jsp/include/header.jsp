<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="author" content="Vendy">
	<title>VendyMachine</title>
	
	<%-- Favicon --%>
	<link rel="icon" type="image/x-icon" href="../../../pub/images/OhNo.png">
	
	<%-- Bootstrap CSS import --%>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
	      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	
	<%-- Custom CSS import --%>
	<link href="../../../pub/css/vendymachine.css" rel="stylesheet">
	
	<%-- jQuery JS import --%>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	        crossorigin="anonymous"></script>
	
	<%-- Popper & Bootstrap JS import --%>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	        crossorigin="anonymous"></script>
</head>

<body class="bg-dark">
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	
	<%-- SVG imports --%>
	<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
		<symbol id="github" viewBox="0 0 16 16">
			<path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.012 8.012 0 0 0 16 8c0-4.42-3.58-8-8-8z"></path>
		</symbol>
		<symbol id="twitch" viewBox="0 0 16 16">
			<path d="M3.857 0 1 2.857v10.286h3.429V16l2.857-2.857H9.57L14.714 8V0H3.857zm9.714 7.429-2.285 2.285H9l-2 2v-2H4.429V1.143h9.142v6.286z"></path>
			<path d="M11.857 3.143h-1.143V6.57h1.143V3.143zm-3.143 0H7.571V6.57h1.143V3.143z"></path>
		</symbol>
	</svg>
	
	<%-- Creates fixed navigation bar --%>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top" id="navbar">
		<%-- 846dcf 765ec4 --%>
		<div class="container-fluid">
			<%-- Chatbot Branding Logo --%>
			<a class="navbar-brand" href="/index"><h2>VM</h2></a>
			
			<%-- TODO add "active" class & aria-current="page" to current pages --%>
			<%-- Collapsible navigation items --%>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
			        aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="navbar-nav me-auto mb-2 mb-md-0">
					<sec:authorize access="isAuthenticated()">
						<li class="nav-item">
							<a class="nav-link" href="/dashboard"><h5>Dashboard</h5></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/commands"><h5>Commands</h5></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/levels/${username}"><h5>Levels</h5></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/chat"><h5>Chat</h5></a>
						</li>
					</sec:authorize>
					<li class="nav-item">
						<a class="nav-link" href="/feedback"><h5>Feedback</h5></a>
					</li>
					<%--<li class="nav-item">--%>
					<%--	<a class="nav-link" href="/guide"><h5>Guide</h5></a>--%>
					<%--</li>--%>
					<%--<li class="nav-item">--%>
					<%--	<a class="nav-link" href="/about"><h5>About</h5></a>--%>
					<%--</li>--%>
				</ul>
			</div>
			
			<%-- Twitch Login --%>
			<sec:authorize access="!isAuthenticated()">
				<div class="navbar-nav" data-bs-toggle="tooltip" data-bs-placement="left" title="Login with Twitch">
					<a class="btn btn-default login" role="button" id="login"
					   href="/auth/twitch">
						<svg class="bi text-light" width="40" height="40">
							<use xlink:href="#twitch"></use>
						</svg>
					</a>
				</div>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<div class="dropdown text-end">
					<a class="btn btn-default dropdown-toggle login" data-bs-toggle="dropdown" href="#"
					   aria-expanded="false">
						<img src="${twitchPFP}" width="40" height="40" style="border-radius: 100%" alt="Profile Picture">
					</a>
					<ul class="dropdown-menu dropdown-menu-end bg-dark text-small shadow-vm-sm" aria-labelledby="login">
						<li><p class="dropdown-item disabled text-light">Logged in as <br><strong
								style="color: #a291ed;">${username}</strong>
						</p></li>
						<li>
							<hr class="dropdown-divider text-light">
						</li>
						<li><a class="dropdown-item text-light" href="#">Settings</a></li>
						<li>
							<hr class="dropdown-divider text-light">
						</li>
						<li><a class="dropdown-item text-light" href="/logout">Sign out</a></li>
					</ul>
				</div>
			</sec:authorize>
		</div>
	</nav>