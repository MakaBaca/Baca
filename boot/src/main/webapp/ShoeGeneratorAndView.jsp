<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shoe Generator and View</title>
</head>
<body>
	<form action="generateShoe">
		<input type="submit">
	</form>

	<table border="2" width="70%" cellpadding="2">
		<tr>
			<th>Shoe #</th>
			<th>Date</th>
		</tr>

			<tr>
				<td>${shoe.shoeNumber}</td>
				<td>${shoe.shoeTime}</td>
			</tr>
	</table>
</body>
</html>
