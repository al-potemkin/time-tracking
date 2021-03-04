<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
	<title>Session Page</title>
	<link rel="stylesheet" type="text/css" href="/css/session.css"/>
</head>
<body>
<div style = "position:absolute; left:100px; top:200px;">
	<form name="sessionForm" method="POST" action="controller">
		<input type="hidden" name="command" value="login"/>
		<fieldset>
			<legend align="center">Session notice</legend>
			<table>
				<tr>
					<td>Your session is out</td>
				</tr>
			</table>
			<div class="wrapperButtons">
				${pageContext.session.setAttribute("backpage", "login")}
				<input type="button" value="Log In" onclick='location.href="controller?command=back"'/>
			</div>
		</fieldset>
		<div class="operationMessageElement">
			</br>${operationMessage}
		</div>
	</form>
</div>
<div class="registrationPageMessage">SESSION PAGE</div>
</body>
</html>
