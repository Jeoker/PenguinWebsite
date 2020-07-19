<%@ page import="java.sql.SQLException" %>
<%@ page import="web.dal.CollectionsDao" %>
<%@ page import="web.dal.LikesDao" %>
<%@ page import="web.model.*" %>
<%@ page import="web.dal.CommentsDao" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Title</title>
</head>
<body>
<%--if user has not signed up or loged in, they can sign up or login;
if they do, they can view their profile or log out--%>
<div class="navbar" style="background-color: white">
    <%-- web name--%>
    <a href="index.jsp" style="color: black"><h1>PenguinWeb</h1></a>
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <div>

                    <%--according to user's different status, choose different myprofile--%>
                <c:choose>
                    <c:when test="${sessionScope.user.status.name().equals('User')}">
                        <%--user my profile--%>
                        <a href="UserMyProfile.jsp" class="btn btn-primary" role="button" style="width: 120px;height: 50px;margin-left: 20px">My Profile</a>
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
                    <input type="submit" value="Log Out" class="btn btn-info" style="width: 120px;height: 50px;margin-left: 20px">
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                    <%-- user login--%>
                <a href="userlogin" class="btn btn-info" role="button" style="width: 120px;margin-left: 20px">LOG IN</a>
                    <%--create user--%>
                <a href="usercreate" class="btn btn-primary" role="button" style="width: 120px;margin-left: 20px">SIGN UP</a>
            </div>

        </c:otherwise>
    </c:choose>
</div><br/>

<div class="post" style="background-color: white">
    <%--current post information--%>
    <div>
        <div class="smallWord">
            <c:out value="${sessionScope.currentPost.user.userName}" />
            Posted by <fmt:formatDate value="${sessionScope.currentPost.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
        </div>
        <div class="bigWord"><c:out value="${sessionScope.currentPost.title}" /></div>
        <div class="middleWord"><c:out value="${sessionScope.currentPost.content}" /></div>
        <div align="center">
            <c:if test="${sessionScope.currentPost.picture != null}">
                <img src="${sessionScope.currentPost.picture}" width="500px" height="250px">
            </c:if>
        </div><br/>
        <table>
            <tr>
                <td>
                    <%--post's comments--%>
                    <%
                        CommentsDao commentsDao = CommentsDao.getInstance();
                        try {
                            Posts post = (Posts) session.getAttribute("currentPost");
                            // get the number of likes for this post
                            int numberOfComments = commentsDao.getCommentNumberByPostId(post.getPostId());
                            request.setAttribute("numberOfComments",numberOfComments);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    %>
                    <div>
                        <form action="postcomment" method="post">
                            <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
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
                                        <input type="text" name="redirect" value="PostComment" hidden>
                                        <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
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
                                        Posts post = (Posts) session.getAttribute("currentPost");
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
                                                <input type="text" name="redirect" value="PostComment" hidden>
                                                <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                                                <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="postunsave" method="post">
                                                <input type="text" name="redirect" value="PostComment" hidden>
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
                    <%
                        // get the number of likes for this post
                        LikesDao likesDao = LikesDao.getInstance();
                        try {
                            Posts post = (Posts) session.getAttribute("currentPost");
                            int numberOfLikes = likesDao.getLikeNumberByPostId(post.getPostId());
                            request.setAttribute("numberOfLikes",numberOfLikes);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    %>
                    <c:choose>
                        <%--only if user has login, they can like posts; otherwise they need to login first--%>
                        <c:when test="${sessionScope.user == null}">
                            <form action="postlike" method="post">
                                <input type="text" name="redirect" value="PostComment" hidden>
                                <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                                <div><input type="submit" value="${numberOfLikes} Like" class="btn btn-secondary"></div>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <%
                                // get like by userId and PostId
                                Users user = (Users) session.getAttribute("user");
                                Posts post = (Posts) session.getAttribute("currentPost");
                                Likes like = null;
                                try {
                                    like = likesDao.getLikesByUserIdPostId(user,post);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                request.setAttribute("userLike",like);
                            %>
                            <div>
                                    <%--if user has not liked this post, he can choose like; otherwise he can cancel like--%>
                                <c:choose>
                                    <c:when test="${userLike == null}">
                                        <form action="postlike" method="post">
                                            <input type="text" name="redirect" value="PostComment" hidden>
                                            <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="postlikedelete" method="post">
                                            <input type="text" name="redirect" value="PostComment" hidden>
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
    </div>

    <%--message: create comment successful or not--%>
    <p>
        <span id="successMessage"><b>${messages.NewComment}${messages.SaveComment}${messages.createReply}</b></span>
    </p>

    <%--if user has loged in, he can create comments for current post; if not, he should sign up or login to create comments--%>
    <div>
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <div align="center">
                    <form action="commentcreate" method="post">
                        <div class="smallWord">Comment as ${sessionScope.user.userName}</div>
                        <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                        <div>
<%--                            <input type="text" name="content" class="form-control" placeholder="What are your thoughts?" size="35" maxlength="500" style="width: 600px;height: 300px">--%>
                            <textarea name="content" class="form-control" cols="30" rows="10" placeholder="What are your thoughts?"></textarea>
                        </div>
                        <div><input type="submit" value="COMMENT" class="btn btn-primary"></div>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div style="position: relative">
                    <span style="font-size: 22px;color: gray">Log in or sign up to leave a comment</span>
                        <%-- user login--%>
                    <a href="userlogin" class="btn btn-info" role="button" style="width: 120px;margin-left: 20px">LOG IN</a>
                        <%--create user--%>
                    <a href="usercreate" class="btn btn-primary" role="button" style="width: 120px;margin-left: 20px">SIGN UP</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div><br/>

    <%--current post's comments information--%>
    <div>
        <c:forEach items="${sessionScope.currentPostComment}" var="comment">
            <div>
                <c:set var="allCommentCurrentComment" scope="request" value="${comment}"/>
                    <%--get father comments--%>
                <c:if test="${comment.fatherComment == null}">
                    <div class="smallWord">
                        <c:out value="${comment.user.userName}"/>
                        Commented by <fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
                    </div>
                    <div class="middleWord"><c:out value="${comment.content}"/></div>

                    <table>
                        <tr>
                            <td>
                                <%--user can reply other's comment--%>
                                <div>
                                    <form action="commentreply" method="get">
                                        <input type="text" name="replyObject" value="comment" hidden>
                                        <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                        <div><input type="submit" value="Reply" class="btn btn-secondary"></div>
                                    </form>
                                </div>
                            </td>
                            <td>
                                    <%--user can save other's comment--%>
                                    <%--only if user has login, they can save posts; otherwise they need to login first--%>
                                <c:choose>
                                    <c:when test="${sessionScope.user == null}">
                                        <div>
                                            <form action="commentsave" method="post">
                                                <input type="text" name="commentId" value="${comment.commentId}" hidden>
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
                                                Comments comment = (Comments) request.getAttribute("allCommentCurrentComment");
                                                try {
                                                    Collections collection = collectionsDao.getCollectionByUserIdCommentId(user,comment);
                                                    request.setAttribute("userSave",collection);
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            %>
                                                <%--if user has not saved this post, he can choose save; otherwise he can cancel save--%>
                                            <c:choose>
                                                <c:when test="${userSave == null}">
                                                    <form action="commentsave" method="post">
                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                        <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                                        <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="commentunsave" method="post">
                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                        <input type="text" name="commentId" value="${userSave.comment.commentId}" hidden>
                                                        <div><input type="submit" value="unSave" class="btn btn-primary"></div>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                    <%--Like comments--%>
                                    <%--only if user has login, they can like comments; otherwise they need to login first--%>
                                <c:choose>
                                    <c:when test="${sessionScope.user == null}">
                                        <form action="commentlike" method="post">
                                            <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                            <div><input type="submit" value="Like" class="btn btn-secondary"></div>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <div>
                                            <%
                                                Users user = (Users) session.getAttribute("user");
                                                try {
                                                    Comments comment = (Comments) request.getAttribute("allCommentCurrentComment");
                                                    // get like by userId and PostId
                                                    Likes like = likesDao.getLikesByUserIdCommentId(user,comment);
                                                    // get the number of likes for this post
                                                    int numberOfLikes = likesDao.getLikeNumberByCommentId(comment.getCommentId());
                                                    request.setAttribute("userLike",like);
                                                    request.setAttribute("numberOfLikes",numberOfLikes);
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            %>

                                                <%--if user has not liked this post, he can choose like; otherwise he can cancel like--%>
                                            <c:choose>
                                                <c:when test="${userLike == null}">
                                                    <form action="commentlike" method="post">
                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                        <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                                        <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="commentlikedelete" method="post">
                                                        <input type="text" name="redirect" value="PostComment" hidden>
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
                    <%--reply comment--%>
                    <center>
                        <c:if test="${requestScope.commentId == comment.commentId}">
                            <div>
                                <form action="commentreply" method="post">
                                    <div class="smallWord">Reply as ${sessionScope.user.userName}</div>
                                    <input type="text" name="commentId" value="${requestScope.commentId}" hidden>
                                    <div>
                                        <textarea name="content" class="form-control" cols="30" rows="10" placeholder="What are your thoughts?" style="width: 600px"></textarea>
                                    </div>
                                    <div><input type="submit" value="REPLY" class="btn btn-primary"></div>
                                </form>
                            </div>
                        </c:if>
                    </center>

                    <%--get child comments--%>
                    <div style="position: relative;left: 50px">
                        <c:forEach items="${sessionScope.currentPostComment}" var="childComment">
                            <c:set var="allChildCommentCurrentComment" scope="request" value="${childComment}"/>
                            <%--iterate child comments--%>
                            <c:if test="${childComment.fatherComment != null}">
                                <c:set var="currentFatherComment" scope="request" value="${comment}"/>
                                <c:set var="currentChildComment" scope="request" value="${childComment}"/>
                                <%
                                    Comments fatherComment = (Comments) request.getAttribute("currentFatherComment");
                                    Comments currentChildComment = (Comments) request.getAttribute("currentChildComment");
                                    boolean isChild = true;
                                    // get father's child comment
                                    while (currentChildComment.getFatherComment().getCommentId() != fatherComment.getCommentId()){
                                        if(currentChildComment.getFatherComment().getFatherComment() == null){
                                            isChild = false;
                                            break;
                                        }
                                        currentChildComment = currentChildComment.getFatherComment();
                                    }
                                    request.setAttribute("isChild",isChild);
                                %>

                                <c:if test="${isChild == true}">
                                    <div class="smallWord">
                                        <c:out value="${childComment.user.userName}"/>
                                        <fmt:formatDate value="${childComment.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
                                    </div>
                                    <div class="middleWord"><c:out value="@${childComment.fatherComment.user.userName}: ${childComment.content}"/></div>

                                    <table>
                                        <tr>
                                            <td>
                                                    <%--user can reply other's comment--%>
                                                <div>
                                                    <form action="commentreply" method="get">
                                                        <input type="text" name="replyObject" value="childComment" hidden>
                                                        <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                        <div><input type="submit" value="Reply" class="btn btn-secondary"></div>
                                                    </form>
                                                </div>
                                            </td>
                                            <td>
                                                    <%--user can save other's comment--%>
                                                <c:choose>
                                                    <c:when test="${sessionScope.user == null}">
                                                        <div>
                                                            <form action="commentsave" method="post">
                                                                <input type="text" name="commentId" value="${childComment.commentId}" hidden>
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
                                                                Comments comment = (Comments) request.getAttribute("allChildCommentCurrentComment");
                                                                try {
                                                                    Collections collection = collectionsDao.getCollectionByUserIdCommentId(user,comment);
                                                                    request.setAttribute("userSave",collection);
                                                                } catch (SQLException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            %>
                                                                <%--if user has not saved this post, he can choose save; otherwise he can cancel save--%>
                                                            <c:choose>
                                                                <c:when test="${userSave == null}">
                                                                    <form action="commentsave" method="post">
                                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                                        <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                                        <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                                                    </form>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <form action="commentunsave" method="post">
                                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                                        <input type="text" name="commentId" value="${userSave.comment.commentId}" hidden>
                                                                        <div><input type="submit" value="unSave" class="btn btn-primary"></div>
                                                                    </form>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                    <%--Like comments--%>
                                                    <%--only if user has login, they can like comments; otherwise they need to login first--%>
                                                        <%
                                                            try {
                                                                Comments comment = (Comments) request.getAttribute("allChildCommentCurrentComment");
                                                                // get the number of likes for this post
                                                                int numberOfLikes = likesDao.getLikeNumberByCommentId(comment.getCommentId());
                                                                request.setAttribute("numberOfLikes",numberOfLikes);
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                        %>
                                                <c:choose>
                                                    <c:when test="${sessionScope.user == null}">
                                                        <form action="commentlike" method="post">
                                                            <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                            <div><input type="submit" value="${numberOfLikes} Like" class="btn btn-secondary"></div>
                                                        </form>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <%
                                                            Users user = (Users) session.getAttribute("user");
                                                            try {
                                                                Comments comment = (Comments) request.getAttribute("allChildCommentCurrentComment");
                                                                // get like by userId and PostId
                                                                Likes like = likesDao.getLikesByUserIdCommentId(user,comment);
                                                                request.setAttribute("userLike",like);
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                        %>
                                                        <div>
                                                                <%--if user has not liked this post, he can choose like; otherwise he can cancel like--%>
                                                            <c:choose>
                                                                <c:when test="${userLike == null}">
                                                                    <form action="commentlike" method="post">
                                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                                        <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                                        <div><input type="submit" value="${numberOfLikes} Like" class="btn btn-secondary"></div>
                                                                    </form>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <form action="commentlikedelete" method="post">
                                                                        <input type="text" name="redirect" value="PostComment" hidden>
                                                                        <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                                                        <div><input type="submit" value="${numberOfLikes} Like" class="btn btn-danger"></div>
                                                                    </form>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </table>

                                    <%--reply comment--%>

                                        <c:if test="${requestScope.childCommentId == childComment.commentId}">
                                            <div>
                                                <form action="commentreply" method="post">
                                                    <div class="smallWord" style="position: relative;left: 230px">Reply as ${sessionScope.user.userName}</div>
                                                    <input type="text" name="commentId" value="${requestScope.childCommentId}" hidden>
                                                    <div>
                                                        <textarea name="content" class="form-control" cols="30" rows="10" placeholder="What are your thoughts?" style="width: 530px"></textarea>
                                                    </div>
                                                    <div style="position: relative;left: 230px"><input type="submit" value="REPLY" class="btn btn-primary"></div>
                                                </form>
                                            </div>
                                        </c:if>


                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </div><br/>
        </c:forEach>
    </div>
</div>
</body>
</html>
