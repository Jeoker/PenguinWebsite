<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/8/20
  Time: 3:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>SignUp</title>
</head>
<body>
<center>
<h1>Sign Up</h1>
<%--form: submit user created information--%>
<div>
    <form action="usercreate" method="post" style="width: 250px; height: 100px">
        <div class="form-group row">
            <label for="username">UserName</label>
            <input type="text" id="username" name="username" value="" class="form-control">
        </div>
        <div class="form-group row">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" value="" class="form-control">
        </div>
        <div class="form-group row">
            <label for="status">Status</label>
            <select id="status" name="status" class="form-control">
                <option value="Researcher">Researcher</option>
                <option value="User" selected>User</option>
            </select>
        </div>
        <div class="form-group">
            <input type="submit" value="SignUp" class="btn btn-primary">
            <input type="reset" value="Reset" class="btn btn-primary">
        </div>
    </form>
</div>
<br/><br/>
<p>
    <span id="successMessage"><b>${messages.signUp}</b></span>
</p>

</center>
</body>
</html>
