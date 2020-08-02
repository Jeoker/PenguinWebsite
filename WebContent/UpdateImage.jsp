<%--
  Created by IntelliJ IDEA.
  User: jeoker
  Date: 7/14/20
  Time: 11:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Please Input new Filename or new Path</title>
</head>
<body>
<center>
    <form action="updateimage" method="post">
        <p>
            <input id="imageId" name="imageId" value="${sessionScope.cur_imageId}" hidden>
        </p>
        <p>
            <label for="path">path</label>
            <input id="path" name="path" value="">
        </p>
        <p>
            <input type="submit">
        </p>
    </form>
    <br/><br/>
    <p>
        <span id="successMessage"><b>${messages.result}</b></span>
    </p>
</body>
</html>
