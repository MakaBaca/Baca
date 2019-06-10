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
			<th>hand #</th>
			<th>Player Id</th>
			<th>Outcome</th>
			<th>Stacked</th>
			<th>did Win</th>
			<th>Amount Won</th>
		</tr>
		<c:forEach var="pps" items="${show}">
			<tr>
				<td>${pps.pk.shoeNumber} </td>
				<td>${pps.pk.handNumber} </td>
				<td>${pps.playerId} </td>
				<td>${pps.outcome} </td>
				<td>${pps.betAmount} </td>
				<td>${pps.didWin} </td>
				<td>${pps.amountWon} </td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>