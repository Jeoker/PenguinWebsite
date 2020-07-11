<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/9/20
  Time: 1:22 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Setting</title>
</head>
<body>
<center>
<%--message: user change personal information successful or not--%>
    <p>
        <span id="successMessage"><b>${messages.update}</b></span>
    </p>

<%--form: user update their personal information--%>
<form action="userupdate" method="post">
    <table border="2">
        <input type="hidden" name="userId" value="${sessionScope.user.userId}"/>
        <tr>
            <td>User Name</td>
            <td><input type="text" name="username" value="${sessionScope.user.userName}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="text" name="password" value="${sessionScope.user.password}"/></td>
        </tr>
        <tr>
            <td>Status</td>
            <td><input type="text" name="status" value="${sessionScope.user.status}" readonly/></td>
        </tr>
        <tr>
            <td align="center" colspan="2"><input type="submit" value="CHANGE"></td>
        </tr>
    </table>
</form>
</center>
</body>
</html>
