<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Players</title>
</head>
<body>
	<form action="createPlayer" method="post">
		<label>Name:</label><input type="text" name="name" id="name" size="32">
		<input type="submit" name="create">
	</form>
	<br>
	<label> Players </label>
	<table border="2" width="70%" cellpadding="2">
		<tr>
			<th>Name</th>
			<th>Invested</th>
			<th>Winings</th>
			<th>Play</th>
			<th>View</th>
			<th>Record</th>
			<th>Refresh</th>
			<th>Performance</th>
		</tr>
		<c:forEach var="player" items="${players}">
			<tr>
			<td>${player.name}</td>
			<td>${player.invested}</td>
			<td>${player.winings}</td>
			<td><a href="/game/${player.playerId}">play</a></td>
			<td><a href="/playedShoes/${player.playerId}">view</a></td>
			<td><a href="/recordGame/${player.playerId}">record</a></td>
			<td><a href="/refreshScore/${player.playerId}">refreshScore</a></td>
			<td><a href="/showResult/${player.playerId}">refreshScore</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>