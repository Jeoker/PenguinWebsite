<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/18/20
  Time: 10:57 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Edit Post</title>
</head>
<body>

<center>
    <div class="middleWord" style="border-bottom: 1px solid white"><h1>EDIT POST</h1></div><br/>

    <form method="post" action="postupdate" style="width: 500px; height: 100px">
        <input type="text" name="postId" value="${post.postId}" hidden>
        <div>
            <input type="text" id="title" class="form-control" value="${post.title}" readonly>
        </div>
        <div>
            <textarea name="newContent" class="form-control" cols="30" rows="10">${post.content}</textarea>
        </div>
        <div align="center">
            <c:if test="${post.picture != null}">
                <img src="${post.picture}" width="500px" height="250px">
            </c:if>
        </div><br/>
        <input type="submit" value="SAVE" class="btn btn-primary"/>
    </form>
</center>
</body>
</html>
