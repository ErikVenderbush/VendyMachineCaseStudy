<jsp:include page="../include/header.jsp"/>

<%-- SVG imports --%>
<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
	<symbol id="chatdotsfill" viewBox="0 0 16 16">
		<path d="M16 8c0 3.866-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.584.296-1.925.864-4.181 1.234-.2.032-.352-.176-.273-.362.354-.836.674-1.95.77-2.966C.744 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7zM5 8a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"></path>
	</symbol>
	<symbol id="excsquare" viewBox="0 0 16 16">
		<path d="M10.067.87a2.89 2.89 0 0 0-4.134 0l-.622.638-.89-.011a2.89 2.89 0 0 0-2.924 2.924l.01.89-.636.622a2.89 2.89 0 0 0 0 4.134l.637.622-.011.89a2.89 2.89 0 0 0 2.924 2.924l.89-.01.622.636a2.89 2.89 0 0 0 4.134 0l.622-.637.89.011a2.89 2.89 0 0 0 2.924-2.924l-.01-.89.636-.622a2.89 2.89 0 0 0 0-4.134l-.637-.622.011-.89a2.89 2.89 0 0 0-2.924-2.924l-.89.01-.622-.636zM8 4c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995A.905.905 0 0 1 8 4zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"></path>
	</symbol>
	<symbol id="speedometer" viewBox="192.6 134.157 12.975 16">
		<path fill-rule="evenodd"
		      d="M 191.1 143.902 C 191.105 137.743 197.776 133.9 203.106 136.984 C 206.436 138.911 207.923 142.934 206.647 146.563 C 206.205 147.816 204.802 148.165 203.715 147.813 C 202.409 147.39 200.575 146.902 199.1 146.902 C 197.626 146.902 195.79 147.39 194.485 147.813 C 193.398 148.165 191.995 147.816 191.553 146.563 C 191.252 145.708 191.099 144.808 191.1 143.902 Z M 198.599 138.394 L 198.599 139.894 C 198.599 140.072 198.694 140.237 198.849 140.327 C 199.182 140.519 199.599 140.279 199.599 139.894 L 199.599 138.394 C 199.599 138.117 199.375 137.894 199.099 137.894 C 198.823 137.894 198.599 138.117 198.599 138.394 Z M 194.831 140.333 L 195.745 141.248 C 195.871 141.374 196.056 141.423 196.228 141.377 C 196.601 141.277 196.725 140.812 196.453 140.54 L 195.538 139.626 C 195.343 139.43 195.026 139.43 194.831 139.626 C 194.636 139.821 194.636 140.137 194.831 140.333 Z M 193.599 144.394 L 195.185 144.394 C 195.363 144.394 195.529 144.298 195.618 144.144 C 195.81 143.81 195.57 143.394 195.185 143.394 L 193.599 143.394 C 193.323 143.394 193.099 143.617 193.099 143.894 C 193.099 144.17 193.323 144.394 193.599 144.394 Z M 203.099 144.394 L 204.599 144.394 C 204.777 144.394 204.943 144.298 205.032 144.144 C 205.224 143.81 204.984 143.394 204.599 143.394 L 203.099 143.394 C 202.823 143.394 202.599 143.617 202.599 143.894 C 202.599 144.17 202.823 144.394 203.099 144.394 Z M 203.353 139.648 C 203.209 139.505 202.98 139.496 202.826 139.628 L 198.646 143.204 C 198.11 143.655 198.264 144.517 198.923 144.755 C 199.292 144.889 199.705 144.77 199.948 144.462 L 203.382 140.165 C 203.506 140.01 203.493 139.787 203.353 139.647 Z"
		      style="paint-order: fill; stroke-opacity: 0; filter: none;"></path>
	</symbol>
</svg>

<main>
	<%-- Summary Hero --%>
	<div class="px-4 pt-5 my-5 text-center border-bottom">
		<h1 class="display-4 fw-bold text-light">VendyMachine</h1>
		<div class="col-lg-6 mx-auto">
			<p class="lead mb-4">A simple Twitch Chatbot for handling commands, loyalty, and other services
			relating to
				your channel. Primary features include an info dashboard, custom commands, an external chat feed, and a
				loyalty system. Plans to implement game plugins for Twitch-to-game communication are in the works!
			</p>
			<div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-5">
				<a href="/auth/twitch"
				   type="button" class="btn btn-vm btn-lg px-4 me-sm-3">Connect with Twitch
				</a>
				<a type="button" class="btn btn-outline-secondary btn-lg px-4" href="#features">Learn More</a>
			</div>
		</div>
		<div class="overflow-hidden" style="max-height: 40vh;">
			<div class="container px-5 pt-5">
				<img src="../../../pub/images/HeroPreview.png" class="img-fluid border rounded-3 shadow-vm mb-4"
				     alt="Preview image" width="900" height="800" loading="lazy">
			</div>
		</div>
	</div>
	
	<div class="b-example-divider"></div>
	
	<%-- Feature List --%>
	<div class="container px-4 py-5" id="features">
		<h2 class="pb-2 border-bottom">Features</h2>
		<div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
			<div class="col d-flex align-items-start">
				<div class="icon-square text-light flex-shrink-0 me-3">
					<svg class="bi" width="1.2em" height="1.2em">
						<use xlink:href="#speedometer"></use>
					</svg>
				</div>
				<div>
					<h2>Dashboard</h2>
					<p>A simple dashboard displaying some info and stats of your channel and livestream at a glance.
						Use
						it to monitor your stream or to check out those sweet, sweet numbers.</p>
					<a href="/dashboard" class="btn btn-vm">Dashboard</a>
				</div>
			</div>
			<div class="col d-flex align-items-start">
				<div class="icon-square text-light flex-shrink-0 me-3">
					<svg class="bi" width="1.2em" height="1.2em">
						<use xlink:href="#excsquare"></use>
					</svg>
				</div>
				<div>
					<h2>Commands</h2>
					<p>Create, delete, and edit your commands all in one place. We give you a couple of default
						examples
						to get you started (don't worry, you can delete them).</p>
					<a href="/commands" class="btn btn-vm">Commands</a>
				</div>
			</div>
			<div class="col d-flex align-items-start">
				<div class="icon-square text-light flex-shrink-0 me-3">
					<svg class="bi" width="1.2em" height="1.2em">
						<use xlink:href="#chatdotsfill"></use>
					</svg>
				</div>
				<div>
					<h2>Chat</h2>
					<p>A live chat mirrored from Twitch. Soon to feature customizable HTML/CSS/JS for use as a
						browser
						source.</p>
					<a href="/chat" class="btn btn-vm">Chat</a>
				</div>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../include/footer.jsp"/>