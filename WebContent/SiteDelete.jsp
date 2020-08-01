<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete a Site</title>
</head>
<body>
<center>
	<h1>Delete Site</h1>
	<form action="sitedelete" method="post" style="width: 250px; height: 200px">
		<p>
			<div <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<label for="name">Name</label>
				<input id="name" name="name" value="${fn:escapeXml(param.name)}" class="form-control">
				<label for="date">Date</label>
				<input id="date" name="date" value="${fn:escapeXml(param.date)}" class="form-control">
			</div>
		</p>
		<p>
			<span id="submitButton" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
			<input type="submit" value="site delete" class="btn btn-primary">
			</span>
		</p>
		<br/>
		<p>
			<%--return researcher Myprofile--%>
            <a href="AdministratorMyProfile.jsp" class="btn btn-info" role="button">My Profile</a>
		</p>
	</form>
	<br/><br/>
</center>
</body>
</html>