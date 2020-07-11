<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>PenguinWeb</title>
  </head>
  <body>
  <center>
<%--message: create user successful or not--%>
    <p>
      <span id="successMessage"><b>${messages.signUp}${messages.login}${messages.NewPost}</b></span>
    </p>

<%-- web name--%>
  <h1>PenguinWeb</h1><br/>

<%--if user has not signed up or loged in, they can sign up or login;
if they do, they can view their profile or log out--%>
  <div>
    <c:choose>
      <c:when test="${sessionScope.user != null}">
        <%--according to user's different status, choose different myprofile--%>
        <c:choose>
          <c:when test="${sessionScope.user.status.name().equals('User')}">
            <%--user my profile--%>
            <div><a href="UserMyProfile.jsp">My Profile</a></div>
          </c:when>
        </c:choose>

        <%--user log out--%>
        <div>
          <form action="userlogout" method="post">
            <input type="submit" value="Log Out">
          </form>
        </div>
      </c:when>
      <c:otherwise>
        <%--create user--%>
        <div><a href="usercreate">SIGN UP</a></div>

        <%-- user login--%>
        <div><a href="userlogin">LOG IN</a></div>
      </c:otherwise>
    </c:choose>
  </div>




  <h1>Post</h1>
    <div><a href="allpost">Post</a></div><br/>

<%-- users can see all posts--%>
      <c:forEach items="${allPosts}" var="post" >
        <div>
          <div><c:out value="${post.getTitle()}" /></div>
          <div><c:out value="${post.getContent()}" /></div>
          <div><c:if test="${post.getPicture() != null}">
            <img src="${post.getPicture()}" width="100px">
          </c:if></div>
          <div><fmt:formatDate value="${post.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
          <div>
            <form action="postcomment" method="post">
              <input type="text" name="postId" value="${post.getPostId()}" hidden>
              <div><input type="submit" value="comment"></div>
            </form>
          </div>
          ---------------------------------
        </div>
      </c:forEach>

  </center>
  </body>
</html>
