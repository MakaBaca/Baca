<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Baca Game</title>
<style type="text/css">
.circle-text {
	display: table-cell;
	height: 20px;
	/*change this and the width for the size of your initial circle*/
	width: 20px;
	text-align: center;
	vertical-align: middle;
	border-radius: 50%;
	/*make it pretty*/
	background: #fff;
	border-style: solid;
	color: #000;
	font: 18px "josefin sans", arial;
	/*change this for font-size and font-family*/
}

.win {
	background: #ADFF2F;
}

.lose {
	background: #FF0000;
}

td {
	text-align: center;
}
</style>
</head>
<body>
	<table border="2" width="70%" cellpadding="2">
		<tr>
			<th>#</th>
			<th>Player</th>
			<th>Banker</th>
			<th>Score</th>
			<th>P/B</th>
			<th>OR</th>
			<th>OT</th>
			<th>1's</th>
			<th>2's</th>
			<th>3'2+</th>
		</tr>
		<c:forEach var="btc" items="${btcList}">
			<tr>
				<td>${btc.noOfHands}</td>
				<c:if test="${btc.player == 'O  1'}">
					<td class="win">
						<div class="circle-text">1</div>
					</td>
				</c:if>
				<c:if test="${btc.player == 'O  0'}">
					<td class="win">
						<div class="circle-text">0</div>
					</td>
				</c:if>
				<c:if test="${btc.player == '1'}">
					<td class="lose">${btc.player}</td>
				</c:if>
				<c:if test="${btc.player == '0'}">
					<td class="lose">${btc.player}</td>
				</c:if>
				<c:if test="${btc.player == 'O'}">
					<td>
						<div class="circle-text"></div>
					</td>
				</c:if>
				<c:if test="${empty btc.player}">
					<td>${btc.player}</td>
				</c:if>
				<c:if test="${btc.banker == 'O  1'}">
					<td class="win">
						<div class="circle-text">1</div>
					</td>
				</c:if>
				<c:if test="${btc.banker == 'O  0'}">
					<td class="win">
						<div class="circle-text">0</div>
					</td>
				</c:if>
				<c:if test="${btc.banker == '1'}">
					<td class="lose">${btc.banker}</td>
				</c:if>
				<c:if test="${btc.banker == '0'}">
					<td class="lose">${btc.banker}</td>
				</c:if>
				<c:if test="${btc.banker == 'O'}">
					<td>
						<div class="circle-text"></div>
					</td>
				</c:if>
				<c:if test="${empty btc.banker}">
					<td>${btc.banker}</td>
				</c:if>
				<td>${btc.score}</td>
				<td>${btc.pb}</td>
				<td>${btc.orCount}</td>
				<td>${btc.otCount}</td>
				<td>${btc.oneInARow}</td>
				<td>${btc.twoInARow}</td>
				<td>${btc.threeOrMoreInARow}</td>
			</tr>
		</c:forEach>
	</table>
	<form action="vinSapAction" method="post">
		<input type="radio" name="wager" value="bet">Bet
		<input type="radio" name="wager" value="nb" checked>NoBet
		<input type="submit" name ="Deal" value="Deal">
		<input type="submit" name ="New"value="New Shoe">
	</form>

</body>
</html>
