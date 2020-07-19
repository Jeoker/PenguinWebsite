<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/11/20
  Time: 3:38 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Sign Up Or Login</title>
</head>
<body>
<center>
    <div><h1>Please Sign Up Or Login</h1></div><br/>
    <div>
        <%--create user--%>
        <a href="usercreate" class="btn btn-primary" role="button" style="width: 120px;margin-left: 20px">SIGN UP</a>
        <%-- user login--%>
        <a href="userlogin" class="btn btn-info" role="button" style="width: 120px;margin-left: 20px">LOG IN</a>
    </div>
</center>
</body>
</html>
