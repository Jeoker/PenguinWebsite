<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a Site</title>
</head>
<body>
	<h1>Update Site</h1>
	<form action="siteupdate" method="post">
		<p>
			<label for="siteId">SiteId</label>
			<input id="siteId" name="siteId" value="${fn:escapeXml(param.siteId)}">
		</p>
		<p>
			<label for="name">New Name</label>
			<input id="name" name="name" value="">
		</p>
		<p>
			<input type="submit" value="site update">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>