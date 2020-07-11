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
    <p>
        <span id="successMessage"><b>${messages.login}</b></span>
    </p>
    <div><a href="finduser">User Settings</a></div>
    <div><a href="postcreate">New Post</a></div>
</center>
</body>
</html>
