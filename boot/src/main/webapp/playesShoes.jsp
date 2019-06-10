<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Played Shoes</title>
</head>
<body>
	<table border="2" width="100%" cellpadding="2">
		<tr>
			<th>Shoe #</th>
			<th>Link</th>
			<th>BTC Mode</th>
			<th>Score</th>
		</tr>
		<c:forEach var="ps" items="${playedShoes}">
			<tr>
				<td>${ps.shoeNumber} </td>
				<td><a href="/showPlayedShoes/${ps.shoeNumber}/${ps.playerId}">show</a></td>
				<td><a href="/showInBtcMode/${ps.shoeNumber}/${ps.playerId}">BTC Mode</a></td>
				<td>${ps.score}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>