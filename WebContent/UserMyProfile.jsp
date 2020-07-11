<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyProfile</title>
</head>
<body>
<center>
<%--message: user login successful or not--%>
    <p>
        <span id="successMessage"><b>${messages.NewPost}</b></span>
    </p>

    <h1>MyProfile</h1><br/>

<%--view/update user's personal information--%>
    <div><a href="finduser">User Settings</a></div>

<%--user can create new posts--%>
    <div><a href="postcreate">New Post</a></div>

</center>
</body>
</html>
