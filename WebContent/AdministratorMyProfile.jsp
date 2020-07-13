<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/11/20
  Time: 11:01 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AdministratorMyProfile</title>
</head>
<body>
<center>
    <p>
        <span id="successMessage"><b>${messages.deleteUser}</b></span>
    </p>
    <%--Home Page--%>
    <div><a href="index.jsp">Home Page</a></div><br/>

    <%--View/Delete Users--%>
    <div><a href="findusers">View Users</a></div><br/>
    <div>
        <table border="1">
            <c:if test="${allUsers != null}">
                <tr>
                    <td>UserId</td>
                    <td>UserName</td>
                    <td>Password</td>
                    <td>Status</td>
                    <td>Delete User</td>
                </tr>
            </c:if>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <td><c:out value="${user.getUserId()}"/></td>
                <td><c:out value="${user.getUserName()}"/></td>
                <td><c:out value="${user.getPassword()}"/></td>
                <td><c:out value="${user.getStatus()}"/></td>
                <td><a href="userdelete?userId=${user.getUserId()}">DELETE</a></td>
            </tr>
        </c:forEach>
        </table>
    </div>


</center>
</body>
</html>