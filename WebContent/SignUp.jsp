<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/8/20
  Time: 3:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
<center>
<h1>Sign Up</h1>
<form action="usercreate" method="post">
    <p>
        <label for="username">UserName</label>
        <input type="text" id="username" name="username" value="">
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" value="">
    </p>
    <p>
        <label for="status">Status</label>
        <select id="status" name="status">
            <option value="Researcher">Researcher</option>
            <option value="User" selected>User</option>
        </select>
    </p>
    <p>
        <input type="submit" value="SignUp">
        <input type="reset" value="Reset">
    </p>
</form>
<br/><br/>
<p>
    <span id="successMessage"><b>${messages.signUp}</b></span>
</p>
</center>
</body>
</html>
