<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/8/20
  Time: 5:17 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Login</title>
</head>
<body>
<center>
<%--message: login successful or not--%>
    <p>
        <span id="successMessage"><b>${messages.login}</b></span>
    </p>

    <h1>Login</h1>

<%--form: submit user login information--%>
    <div>
        <form action="userlogin" method="post" style="width: 250px; height: 100px">
            <div class="form-group row">
                <label for="username">UserName</label>
                <input type="text" id="username" name="username" class="form-control">
            </div>
            <div class="form-group row">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control">
            </div>
            <div class="form-group row">
                <label for="status">Status</label>
                <select id="status" name="status" class="form-control">
                    <option value="Researcher">Researcher</option>
                    <option value="User" selected>User</option>
                    <option value="Administrator">Administrator</option>
                </select>
            </div>
            <div class="form-group">
                <input type="submit" value="Login" class="btn btn-primary">
                <input type="reset" value="Reset" class="btn btn-primary">
            </div>
        </form>
    </div>
</center>
</body>
</html>
