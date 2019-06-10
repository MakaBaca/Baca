<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Generate Baca Shoe Here</title>
</head>
<body>
	<form action="generateShoe">
		<input type="submit">
	</form>

	<table border="2" width="70%" cellpadding="2">
		<tr>
			<th>Shoe #</th>
			<th>Hand #</th>
			<th>Outcome</th>
		</tr>
		<c:forEach var="shoe" items="${shoeList}">
			<tr>
				<td>${shoe.sh.shoeNumber}</td>
				<td>${shoe.sh.handNumber}</td>
				<td>${shoe.outcome}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
