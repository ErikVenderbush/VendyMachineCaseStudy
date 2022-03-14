<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../include/header.jsp"/>

<main>
	<div class="container">
		<div class="bg-dark p-5 rounded">
			<h1 class="display-4 fw-bold">Commands</h1>
		</div>
		
		<%-- Add New Command Form --%>
		<form method="POST" action="/commandAdd">
			<h4><i>Add New Command</i></h4>
			<table width="70%">
				<tbody>
					<tr>
						<td class="pb-1" colspan="1" width="10%">
							<label for="command">Command: </label>
						</td>
						<td class="pb-1" colspan="7" width="70%">
							<input type="text" class="form-control-sm" name="command" id="command" style="width: 100%">
						</td>
					</tr>
					<tr>
						<td class="pb-1" colspan="1" width="10%">
							<label for="response">Response: </label>
						</td>
						<td class="pb-1" colspan="7" width="70%">
							<textarea class="form-control-sm" name="response" id="response"
							          style="width: 100%"></textarea>
						</td>
					</tr>
					<tr>
						<td class="pb-1" colspan="2">
							<label for="permission">Permission: </label>
							<select class="form-select-sm" name="permission" id="permission">
								<option value="Everyone">Everyone</option>
								<option value="Subscriber">Subscriber</option>
								<option value="VIP">VIP</option>
								<option value="Moderator">Moderator</option>
								<option value="Broadcaster">Broadcaster</option>
							</select>
						</td>
						<td class="pb-1" colspan="2">
							<label for="cooldown">Cooldown (min): </label>
							<input type="number" class="form-control-sm" name="cooldown" id="cooldown" value="0" min="0"
							       max="60" placeholder="0">
						<td class="pb-1" colspan="2">
							<label for="enabled">Enabled: </label>
							<div class="btn-group" role="group" id="enabled">
								<input type="radio" class="btn-check" name="enabled"
								       id="enabled-t" value="True" autocomplete="off" checked>
								<label class="btn-sm btn-outline-success" for="enabled-t">True</label>
								<input type="radio" class="btn-check" name="enabled"
								       id="enabled-f" value="False" autocomplete="off">
								<label class="btn-sm btn-outline-secondary"
								       for="enabled-f">False</label>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<button class="btn btn-vm" type="submit">Add</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<%-- Error Printout --%>
		<div>
			<c:forEach items="${formBeanKey.errorMessages}" var="message">
				<span style="color: red">${message}</span><br>
			</c:forEach>
		</div>
		
		<%-- Accordion Table of Commands; click on a row to view customization options --%>
		<form method="POST" action="/commandEdit" id="editCom">
			<table class="table table-vm table-striped mb-5" id="accordionTable">
				<thead>
					<tr>
						<th colspan="2" width="20%">Command</th>
						<th colspan="8" width="80%">Response</th>
					
					</tr>
				</thead>
				<tbody class="accordion">
					<c:forEach items="${commandListKey}" var="command">
						<tr class="collapsed" data-bs-toggle="collapse" data-bs-target=".accordion${command.cid}">
							<td hidden>
								<input type="hidden" class="form-control-sm" id="${command.cid}-id"
								       value="${command.cid}">
							</td>
							<td colspan="2" width="20%">
								<span>${command.command}</span>
							</td>
							<td colspan="6" width="60%">
								<span>${command.response}</span>
							</td>
							<td colspan="1" width="10%">
								<button type="button" class="btn-sm btn-primary btn-edit">Edit</button>
								<button type="button" class="btn-sm btn-danger btn-delete">Delete</button>
							</td>
						</tr>
						<tr>
							<td class="hidden-row" colspan="3" width="30%">
								<div class="accordion-collapse collapse accordion${command.cid}">
									<label for="${command.cid}-p">Permission:</label>
									<select class="form-select-sm" name="permission" id="${command.cid}-p" disabled>
										<option value="${command.permission}">${command.permission}</option>
									</select>
								</div>
							</td>
							<td class="hidden-row" colspan="3" width="30%">
								<div class="accordion-collapse collapse accordion${command.cid}">
									<label for="${command.cid}-c">Cooldown (min):</label>
									<input type="number" class="form-control-sm" id="${command.cid}-c"
									       name="cooldown" value="${command.cooldown}"
									       min="0" max="60" placeholder="0" disabled>
								</div>
							</td>
							<td class="hidden-row" colspan="3" width="30%">
								<div class="accordion-collapse collapse accordion${command.cid}">
									<label>Enabled:</label>
									<c:choose>
										<c:when test="${command.enabled=='true'}">
											<div class="btn-group" role="group" id="${command.cid}-e">
												<input type="radio" class="btn-check" name="${command.cid}-e" disabled
												       id="${command.cid}-e-t" value="True" autocomplete="off" checked>
												<label class="btn-sm btn-outline-success" for="${command.cid}-e-t">True
												</label>
												<input type="radio" class="btn-check" name="${command.cid}-e" disabled
												       id="${command.cid}-e-f" value="False" autocomplete="off">
												<label class="btn-sm btn-outline-secondary"
												       for="${command.cid}-e-f">False</label>
											</div>
										</c:when>
										<c:otherwise>
											<div class="btn-group" role="group" id="${command.cid}-e">
												<input type="radio" class="btn-check" name="${command.cid}-e" disabled
												       id="${command.cid}-e-t" value="True" autocomplete="off">
												<label class="btn-sm btn-outline-success" for="${command.cid}-e-t">True
												</label>
												<input type="radio" class="btn-check" name="${command.cid}-e" disabled
												       id="${command.cid}-e-f" value="False" autocomplete="off" checked>
												<label class="btn-sm btn-outline-secondary"
												       for="${command.cid}-e-f">False</label>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</main>

<%-- Delete button functionality --%>
<script>
	$(document).ready(function () {
		$('.btn-delete').click(function () {
			var cidVal = $(this).parents('tr').children('td:first').children('input').val()
			var commandName = $(this).parents('tr').children('td:nth-child(2)').children('span').html()
			var delConfirm = confirm('Delete command ' + commandName + '?')
			
			if (delConfirm == true) {
				window.location.href = '/commandDelete?cid=' + cidVal
			}
		})
	})
</script>

<%-- Edit button functionality --%>
<script>
	$(document).ready(function () {
		$('.btn-edit').click(function () {
			if ($(this).html() == 'Edit') {
				// Gets rows and cells
				var currentTR = $(this).parents('tr')
				var nextTR = currentTR.next()
				var cidTD = currentTR.children('td:first').children('input')
				var commandTD = currentTR.children('td:nth-child(2)').children('span')
				var commandVal = commandTD.html()
				var responseTD = currentTR.children('td:nth-child(3)').children('span')
				var responseVal = responseTD.html()
				var permissionTD = nextTR.children('td:first').children('div').children('select').children('option')
				var permissionVal = permissionTD.html()
				var permissions = ['Everyone', 'Subscriber', 'VIP', 'Moderator', 'Broadcaster']
				
				// Disables the toggle
				currentTR.removeAttr('data-bs-toggle')
				
				// Binds cid field to submission form
				cidTD.attr('name', 'cid')
				cidTD.attr('form', 'editCom')
				
				// Replaces Command and Response with text fields
				commandTD.replaceWith('<input type="text" class="form-control-sm text-light" name="command" ' +
					'form="editCom" style="width: 100%" value="' + commandVal + '">')
				responseTD.replaceWith('<textarea class="form-control-sm text-light" name="response" rows="1" ' +
					'form="editCom" style="width: 100%">' + responseVal + '</textarea>')
				
				// Removes hidden row class from cells
				nextTR.children('td').removeClass('hidden-row')
				// Forces show of hidden fields TODO still kinda yucky
				if (!nextTR.children('td').children('div').hasClass('show')) {
					nextTR.children('td').children('div').addClass('show')
				}
				// Removes disabled attribute from hidden fields
				nextTR.children('td').children('div').find('select, input').removeAttr('disabled')
				// Binds fields to submission form
				nextTR.children('td').children('div').find('select, input').attr('form', 'editCom')
				nextTR.children('td').children('div').children('div').find('input').attr('name', 'enabled')
				
				// Switches to full permission menu
				permissionTD.replaceWith(function () {
					let perms = ''
					
					for (let p in permissions) {
						if (permissions[p] == permissionVal) {
							perms += '<option value="' + permissions[p] + '" selected>' + permissions[p] + '</option>\n'
						} else {
							perms += '<option value="' + permissions[p] + '">' + permissions[p] + '</option>\n'
						}
					}
					
					return perms
				})
			}
			
			// Changes button to submit form
			if ($(this).html() == 'Save') {
				$(this).attr('type', 'submit')
				$(this).attr('form', 'editCom')
			}
			$(this).html('Save')
		})
	})
</script>

<jsp:include page="../include/footer.jsp"/>