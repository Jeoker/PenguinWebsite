<%@ page import="web.model.Likes" %>
<%@ page import="java.util.List" %>
<%@ page import="web.dal.LikesDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="web.model.Users" %>
<%@ page import="web.dal.PostsDao" %>
<%@ page import="web.model.Posts" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home Page</title>
  </head>
  <body>
  <center>
<%--message: create user successful or not--%>
    <p>
      <span id="successMessage"><b>${messages.signUp}${messages.login}${messages.SavePost}</b></span>
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
          <c:when test="${sessionScope.user.status.name().equals('Administrator')}">
            <%--user my profile--%>
            <div><a href="AdministratorMyProfile.jsp">My Profile</a></div>
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
<%--all posts--%>
  <%
    PostsDao postsDao = PostsDao.getInstance();
    List<Posts> postsList = new ArrayList<>();
    try {
      postsList = postsDao.getAllPost();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    request.setAttribute("allPosts", postsList);
  %>

<%-- users can see all posts--%>
      <c:forEach items="${allPosts}" var="post" >
        <div>
          <div><c:out value="${post.user.userName}" /></div>
          <div><c:out value="${post.title}" /></div>
          <div><c:out value="${post.content}" /></div>
          <div><c:if test="${post.picture != null}">
            <img src="${post.picture}" width="100px">
          </c:if></div>
          <div><fmt:formatDate value="${post.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>

<%--post's comments--%>
          <div>
            <form action="postcomment" method="post">
              <input type="text" name="postId" value="${post.postId}" hidden>
              <div><input type="submit" value="Comment"></div>
            </form>

<%--save post, user cannot save their own posts--%>
            <c:if test="${post.user.userId != sessionScope.user.userId}">
              <form action="postsave" method="post">
                <input type="text" name="postId" value="${post.postId}" hidden>
                <div><input type="submit" value="Save"></div>
              </form>
            </c:if>
          </div>

          <%--Like posts--%>
          <%--only if user has login, they can like posts; otherwise they need to login first--%>
          <c:choose>
            <c:when test="${sessionScope.user == null}">
              <form action="postlike" method="post">
                <input type="text" name="postId" value="${post.postId}" hidden>
                <div><input type="submit" value="Like"></div>
              </form>
            </c:when>

            <c:otherwise>
              <%--get a list of likes by userId--%>
              <div>
                <%
                  LikesDao likesDao = LikesDao.getInstance();
                  Users user = (Users) session.getAttribute("user");
                  List<Likes> likesList = null;
                  try {
                    likesList = likesDao.getLikesByUserId(user);
                  } catch (SQLException e) {
                    e.printStackTrace();
                  }
                  session.setAttribute("userLikes",likesList);
                  // initial isLike FALSE
                  request.setAttribute("isLike",false);
                %>
                
                <%--iterate the list of likes, if current postId equal to like's postId, set isLike TRUE--%>
                <c:forEach items="${sessionScope.userLikes}" var="like">
                  <c:if test="${post.postId == like.post.postId}">
                    <%
                      request.setAttribute("isLike",true);
                    %>
                    <c:set var="likeId" scope="request" value="${like.likeId}"/>
                  </c:if>
                </c:forEach>

                <%--if isLike is TRUE, it means user has liked this post, so user can cancel this like--%>
                <%--otherwise, user can like this post--%>
                <c:choose>
                  <c:when test="${isLike != true}">
                    <form action="postlike" method="post">
                      <input type="text" name="postId" value="${post.postId}" hidden>
                      <div><input type="submit" value="Like"></div>
                    </form>
                  </c:when>
                  <c:otherwise>
                    <form action="postlikedelete" method="post">
                      <input type="text" name="likeId" value="${likeId}" hidden>
                      <div><input type="submit" value="unLike"></div>
                    </form>
                  </c:otherwise>
                </c:choose>
              </div>
            </c:otherwise>
          </c:choose>

          ---------------------------------
        </div>
      </c:forEach>

  </center>
  </body>
</html>
