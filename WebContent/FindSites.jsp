<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Site</title>
</head>
<body>
<center>
	<form action="findsites" method="post">
		<h1>Search for a Site by Name</h1>
		<p>
			<label for="name">Name</label>
			<input id="name" name="name" value="${fn:escapeXml(param.name)}">
		</p>
		<p>
			<input type="submit" value="find site">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<div><a href="findallsites">Show AllSites</a></div><br/>
	<br/>
        <div id="SiteCreate"><a href="sitecreate">Create Site</a></div>
        <div id="SiteDelete"><a href="sitedelete">Delete Site</a></div>
        <div id="SiteUpdate"><a href="SiteUpdate.jsp">Update Site</a></div>
        <br/>
        <table border="1">
            <tr>
                <th>SiteId</th>
                <th>Name</th>
                <th>Date</th>
            </tr>
            <c:forEach items="${sites}" var="site" >
                <tr>
                    <td><c:out value="${site.getSiteId()}" /></td>
                    <td><c:out value="${site.getName()}" /></td>
                    <td><fmt:formatDate value="${site.getDate()}" pattern="yyyy-MM-dd"/></td>
					<td>
						<form action="imagesFromSite" method="get">
                            <input type='hidden' name='siteId' value="${site.getSiteId()}"/>
							<input type="submit"  value="Show Images">
						</form>
					</td>
                </tr>
            </c:forEach>
       </table>
</center>
</body>
</html>
