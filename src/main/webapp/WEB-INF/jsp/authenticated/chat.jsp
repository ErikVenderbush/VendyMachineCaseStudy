<jsp:include page="../include/header.jsp"/>

	<%-- Body of this page taken up by Twitch chat embed; plans to be customizable CSS for messages --%>
	<main style="background-image: url('../../../pub/images/Tiling.png');">
		<iframe src="https://www.twitch.tv/embed/${username}/chat?parent=localhost&darkpopout" id="chat"></iframe>
	</main>
	
	<%-- Script to have chat fill page height without being covered by navbar --%>
	<script>
		function setChatHeight() {
			let nav = document.querySelector('#navbar');
			let navh = nav.offsetHeight;
			let winh = window.innerHeight;
			let chath = winh - navh + 1;
			let chat = document.querySelector('#chat');
			chat.setAttribute('height', chath);
		}
		
		window.onload = setChatHeight;
		window.onresize = setChatHeight;
	</script>
</body>

</html>