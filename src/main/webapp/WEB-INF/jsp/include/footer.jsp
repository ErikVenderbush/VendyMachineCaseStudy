	<div class="b-example-divider"></div>
	
	<%-- Footer --%>
	<div class="container">
		<footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
			<div class="col-md-4 d-flex align-items-center">
				<span class="text-muted">Made by Vendy with love</span>
			</div>
			
			<ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
				<li class="ms-3"><a class="text-muted" href="https://github.com/Vendy13/VendyMachine" target="_blank">
					<svg class="bi" width="24" height="24">
						<use xlink:href="#github"></use>
					</svg>
				</a></li>
				<li class="ms-3"><a class="text-muted" href="https://www.twitch.tv/vendydev" target="_blank">
					<svg class="bi" width="24" height="24">
						<use xlink:href="#twitch"></use>
					</svg>
				</a></li>
			</ul>
		</footer>
	</div>
	
	<%-- Enables tooltips --%>
	<script>
		var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
		var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
			return new bootstrap.Tooltip(tooltipTriggerEl)
		})
	</script>
	
	<%-- Adds text-light class to specific elements --%>
	<script>
		let selectors =
			document.querySelectorAll('div>h1, div>h2, h4, div>h5, div>p, form>p, label, .form-select-sm, .form-control-sm');
		for (s in selectors) {
			if (!selectors[s].matches('.text-light')) {
				selectors[s].setAttribute('class', selectors[s].getAttribute('class') + ' text-light');
			}
		}
	</script>
</body>

</html>