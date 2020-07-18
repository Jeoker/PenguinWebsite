<%@ page import="web.model.Likes" %>
<%@ page import="java.util.List" %>
<%@ page import="web.dal.LikesDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="web.model.Users" %>
<%@ page import="web.dal.PostsDao" %>
<%@ page import="web.model.Posts" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="web.dal.CollectionsDao" %>
<%@ page import="web.model.Collections" %>
<%@ page import="web.dal.CommentsDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
  <head>
    <title>Home Page</title>
  </head>
  <body>
<%--message: create user successful or not--%>
<%--    <p>--%>
<%--      <span id="successMessage"><b>${messages.signUp}${messages.login}${messages.SavePost}</b></span>--%>
<%--    </p>--%>

<%--if user has not signed up or loged in, they can sign up or login;
if they do, they can view their profile or log out--%>
  <div class="navbar" style="background-color: white">
    <%-- web name--%>
    <h1>PenguinWeb</h1>
    <c:choose>
      <c:when test="${sessionScope.user != null}">
        <div>

            <%--according to user's different status, choose different myprofile--%>
          <c:choose>
            <c:when test="${sessionScope.user.status.name().equals('User')}">
              <%--user my profile--%>
              <a href="UserMyProfile.jsp" class="btn btn-primary" role="button" style="width: 120px;height: 50px">My Profile</a>
            </c:when>
            <c:when test="${sessionScope.user.status.name().equals('Administrator')}">
              <%--user my profile--%>
              <a href="AdministratorMyProfile.jsp" class="btn btn-primary btn-lg active" role="button">My Profile</a>
            </c:when>
            <c:when test="${sessionScope.user.status.name().equals('Researcher')}">
              <%--user my profile--%>
              <a href="ResearcherMyProfile.jsp" class="btn btn-primary btn-lg active" role="button">My Profile</a>
            </c:when>
          </c:choose>

            <%--user log out--%>
            <form action="userlogout" method="post" style="float: left">
              <input type="submit" value="Log Out" class="btn btn-info" style="width: 120px;height: 50px">
            </form>
        </div>
      </c:when>
      <c:otherwise>
          <div>
            <%-- user login--%>
            <a href="userlogin" class="btn btn-info" role="button" style="width: 120px">LOG IN</a>
            <%--create user--%>
            <a href="usercreate" class="btn btn-primary" role="button" style="width: 120px">SIGN UP</a>
          </div>

      </c:otherwise>
    </c:choose>
  </div>


  <h1 align="center">Test Section</h1><br/>
  <div align="center"><a href="imagesFromSite">allImages</a></div><br/>



  <div align="center"><h1>Post</h1></div>
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
        <c:set var="allPostCurrentPost" scope="request" value="${post}"/>
        <div class="post" style="background-color: white">
          <div class="smallWord">
            <c:out value="${post.user.userName}" />
            Posted by <fmt:formatDate value="${post.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
          </div>
          <div class="bigWord"><c:out value="${post.title}" /></div>
          <div class="middleWord"><c:out value="${post.content}" /></div>
          <div align="center"><c:if test="${post.picture != null}">
            <img src="${post.picture}" width="500px" height="250px">
          </c:if></div>
          <br/>
          <table>
            <tr>
              <td>
                  <%--post's comments--%>
                    <%
                      CommentsDao commentsDao = CommentsDao.getInstance();
                      try {
                        Posts post = (Posts) request.getAttribute("allPostCurrentPost");
                        // get the number of likes for this post
                        int numberOfComments = commentsDao.getCommentNumberByPostId(post.getPostId());
                        request.setAttribute("numberOfComments",numberOfComments);
                      } catch (SQLException e) {
                        e.printStackTrace();
                      }
                    %>
                <div>
                  <form action="postcomment" method="post">
                    <input type="text" name="postId" value="${post.postId}" hidden>
                    <div><input type="submit" value="${numberOfComments} Comments" class="btn btn-secondary"></div>
                  </form>
                </div>
              </td>
              <td>
                  <%--save post--%>
                  <%--only if user has login, they can save posts; otherwise they need to login first--%>
                <div>
                  <c:choose>
                    <c:when test="${sessionScope.user == null}">
                      <div>
                        <form action="postsave" method="post">
                          <input type="text" name="postId" value="${post.postId}" hidden>
                          <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                        </form>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <div>
                          <%--get save by userId and postId--%>
                        <%
                          CollectionsDao collectionsDao = CollectionsDao.getInstance();
                          Users user = (Users) session.getAttribute("user");
                          Posts post = (Posts) request.getAttribute("allPostCurrentPost");
                          try {
                            Collections collection = collectionsDao.getCollectionByUserIdPostId(user,post);
                            request.setAttribute("userSave",collection);
                          } catch (SQLException e) {
                            e.printStackTrace();
                          }
                        %>
                          <%--if user has not saved this post, he can choose save; otherwise he can cancel save--%>
                        <c:choose>
                          <c:when test="${userSave == null}">
                            <form action="postsave" method="post">
                              <input type="text" name="postId" value="${post.postId}" hidden>
                              <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                            </form>
                          </c:when>
                          <c:otherwise>
                            <form action="postunsave" method="post">
                              <input type="text" name="redirect" value="index" hidden>
                              <input type="text" name="postId" value="${userSave.post.postId}" hidden>
                              <div><input type="submit" value="unSave" class="btn btn-primary"></div>
                            </form>
                          </c:otherwise>
                        </c:choose>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </td>
              <td>
                  <%--Like posts--%>
                  <%--only if user has login, they can like posts; otherwise they need to login first--%>
                <c:choose>
                  <c:when test="${sessionScope.user == null}">
                    <form action="postlike" method="post">
                      <input type="text" name="postId" value="${post.postId}" hidden>
                      <div><input type="submit" value="Like" class="btn btn-secondary"></div>
                    </form>
                  </c:when>

                  <c:otherwise>
                    <div>
                      <%
                        LikesDao likesDao = LikesDao.getInstance();
                        Users user = (Users) session.getAttribute("user");
                        try {
                          Posts post = (Posts) request.getAttribute("allPostCurrentPost");
                          // get like by userId and PostId
                          Likes like = likesDao.getLikesByUserIdPostId(user,post);
                          // get the number of likes for this post
                          int numberOfLikes = likesDao.getLikeNumberByPostId(post.getPostId());
                          request.setAttribute("userLike",like);
                          request.setAttribute("numberOfLikes",numberOfLikes);
                        } catch (SQLException e) {
                          e.printStackTrace();
                        }
                      %>

                        <%--if user has not liked this post, he can choose like; otherwise he can cancel like--%>
                      <c:choose>
                        <c:when test="${userLike == null}">
                          <form action="postlike" method="post">
                            <input type="text" name="postId" value="${post.postId}" hidden>
                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                          </form>
                        </c:when>
                        <c:otherwise>
                          <form action="postlikedelete" method="post">
                            <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-danger"></div>
                          </form>
                        </c:otherwise>
                      </c:choose>
                    </div>
                  </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </table>
        </div><br/>
      </c:forEach>

  </body>
</html>
