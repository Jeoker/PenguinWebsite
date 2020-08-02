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
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>AdministratorMyProfile</title>
</head>
<body>
<center>
    <p>
        <span id="successMessage"><b>${messages.deleteUser}</b></span>
    </p>
    <h1>Administrator MyProfile</h1><br/>
    <%--Home Page--%>
    <div><a href="index.jsp" class="btn btn-info">Home Page</a></div><br/>
	<%--Site--%>
    <div><a href="findsites" class="btn btn-info">Sites management</a></div><br/>
    
    <%--View/Delete Users--%>
    <div><a href="findusers" class="btn btn-info">View Users</a></div><br/>
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

    <%--View/Delete Posts--%>
    <div><a href="findposts" class="btn btn-info">View Posts</a></div><br/>
    <div>
        <table border="1">
            <c:if test="${allPosts != null}">
                <tr>
                    <td>PostId</td>
                    <td>Title</td>
                    <td>Picture</td>
                    <td>Content</td>
                    <td>Published</td>
                    <td>Created</td>
                    <td>UserName</td>
                    <td>Delete User</td>
                </tr>
            </c:if>
            <c:forEach items="${allPosts}" var="post">
                <tr>
                    <td><c:out value="${post.getPostId()}"/></td>
                    <td><c:out value="${post.getTitle()}"/></td>
                    <td><c:if test="${post.getPicture() != null}">
                        <img src="${post.getPicture()}" width="100px">
                    </c:if></td>
                    <td><c:out value="${post.getContent()}"/></td>
                    <td><c:out value="${post.isPublished()}"/></td>
                    <td><c:out value="${post.getCreated()}"/></td>
                    <td><c:out value="${post.getUser().getUserName()}"/></td>
                    <td><a href="postdelete?postId=${post.getPostId()}">DELETE</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
	
	
	

</center>
</body>
</html>
