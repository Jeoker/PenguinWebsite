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
<title>Create a Site</title>
</head>
<body>
<center>
	<h1>Create Site</h1>
	<form action="sitecreate" method="post" style="width: 250px; height: 200px">
		<p>
			<label for="name">Name</label>
			<input id="name" name="name" value="" class="form-control">
		</p>
		<p>
			<label for="date">Date (yyyy-mm-dd)</label>
			<input id="date" name="date" value="" class="form-control">
		</p>
		<p>
			<input type="submit" value="site create" class="btn btn-primary">
			
		</p>
		<br/>
		<p>
		<c:choose>
	      <c:when test="${sessionScope.user != null}">
	        <div>
	          <%--according to user's different status, choose different myprofile--%>
	          <c:choose>
	            <c:when test="${sessionScope.user.status.name().equals('Administrator')}">
	              <%--user my profile--%>
	              <a href="AdministratorMyProfile.jsp" class="btn btn-info" role="button">My Profile</a>
	            </c:when>
	            <c:when test="${sessionScope.user.status.name().equals('Researcher')}">
	              <%--user my profile--%>
	              <a href="ResearcherMyProfile.jsp" class="btn btn-info" role="button">My Profile</a>
	            </c:when>
	          </c:choose>
			 </div>
		  </c:when>
		</c:choose>

		
			<%--return researcher Myprofile
            <a href="AdministratorMyProfile.jsp" class="btn btn-info" role="button">My Profile</a>--%>
		</p>
	</form>
	<br/><br/><br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</center>
</body>
</html>