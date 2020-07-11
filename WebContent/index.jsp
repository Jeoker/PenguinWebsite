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
    <p>
      <span id="successMessage"><b>${messages.signUp}</b></span>
    </p>
  <h1>PenguinWeb</h1><br/>
  <div><a href="usercreate">SIGN UP</a></div>
  <div><a href="userlogin">LOG IN</a></div>

  <h1>Post</h1>
    <div><a href="allpost">Post</a></div><br/>

      <c:forEach items="${allPosts}" var="post" >
        <div>
          <div><c:out value="${post.getTitle()}" /></div>
          <div><c:out value="${post.getContent()}" /></div>
          <div><c:if test="${post.getPicture() != null}">
            <img src="${post.getPicture()}" width="100px">
          </c:if></div>
          <div><fmt:formatDate value="${post.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
          <div><a href="NewPost.jsp"><input type="button" value="comment"></a></div>
          ---------------------------------
        </div>
      </c:forEach>
  </center>
  </body>
</html>
