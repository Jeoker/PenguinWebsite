<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/8/20
  Time: 5:17 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <form action="userlogin" method="post">
        <table border="2">
            <tr>
                <td>Status</td>
                <td>
                    <select name="status">
                        <option value="User" selected>User</option>
                        <option value="Researcher">Researcher</option>
                        <option value="Administrator">Administrator</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Username</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Login"/></td>
                <td><input type="reset" /></td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>
