<%@ page import="web.dal.CommentsDao" %>
<%@ page import="web.model.Posts" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="web.dal.CollectionsDao" %>
<%@ page import="web.model.Users" %>
<%@ page import="web.model.Collections" %>
<%@ page import="web.dal.LikesDao" %>
<%@ page import="web.model.Likes" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Reply</title>
</head>
<body>
    <div class="post" style="background-color: white">
        <%--current post--%>
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
                                        <input type="text" name="redirect" value="CommentReply" hidden>
                                        <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                                        <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="postunsave" method="post">
                                        <input type="text" name="redirect" value="CommentReply" hidden>
                                        <input type="text" name="postId" value="${userSave.post.postId}" hidden>
                                        <div><input type="submit" value="unSave" class="btn btn-primary"></div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                    <td>
                        <%--Like posts--%>
                        <%
                            LikesDao likesDao = LikesDao.getInstance();
                            Likes like = null;
                            try {
                                // get the number of likes for this post
                                int numberOfLikes = likesDao.getLikeNumberByPostId(post.getPostId());
                                request.setAttribute("numberOfLikes",numberOfLikes);
                                // get like by userId and PostId
                                like = likesDao.getLikesByUserIdPostId(user,post);
                                request.setAttribute("userLike",like);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        %>
                        <div>
                            <%--if user has not liked this post, he can choose like; otherwise he can cancel like--%>
                            <c:choose>
                                <c:when test="${userLike == null}">
                                    <form action="postlike" method="post">
                                        <input type="text" name="redirect" value="CommentReply" hidden>
                                        <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                                        <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="postlikedelete" method="post">
                                        <input type="text" name="redirect" value="CommentReply" hidden>
                                        <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                        <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-danger"></div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                </tr>
            </table>

        <%--current comment information--%>
        <div><c:out value="${sessionScope.currentComment.user.userName}" /></div>
        <div><c:out value="${sessionScope.currentComment.content}" /></div>
        <div><c:out value="${sessionScope.currentComment.created}" /></div><br/>
        ------------------------------------

        <%--current reply comment--%>
        <div>
            <c:forEach items="${sessionScope.currentReplies}" var="comment">
                <div>
                    <div><c:out value="${comment.user.userName}"/></div>
                    <div>@<c:out value="${comment.fatherComment.user.userName}: ${comment.content}"/></div>
                    <div><fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
                </div>
            </c:forEach>
        </div><br/>
        ------------------------------------

        <%----%>
        <div>
            <form action="commentreply" method="post">
                <div>Reply as ${sessionScope.user.userName}</div>
                <input type="text" name="commentId" value="${currentComment.getCommentId()}" hidden>
                <div><input type="text" name="content" style="width: 500px;height: 200px"></div>
                <div><input type="submit" value="REPLY"></div>
            </form>
        </div>
</body>
    </div>
</html>
