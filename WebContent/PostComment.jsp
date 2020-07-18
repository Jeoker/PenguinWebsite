<%@ page import="java.sql.SQLException" %>
<%@ page import="web.dal.CollectionsDao" %>
<%@ page import="web.dal.LikesDao" %>
<%@ page import="web.model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="index.jsp">BACK</a>
<center>

<%--current post information--%>
<div style="background-color: cornsilk">
    <div><c:out value="${sessionScope.currentPost.user.userName}" /></div>
    <div><c:out value="${sessionScope.currentPost.title}" /></div>
    <div><c:out value="${sessionScope.currentPost.content}" /></div>
    <div><c:if test="${sessionScope.currentPost.picture != null}">
        <img src="${sessionScope.currentPost.picture}" width="100px">
    </c:if></div>
    <div><fmt:formatDate value="${sessionScope.currentPost.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div><br/>
</div>

<%--message: create comment successful or not--%>
<p>
    <span id="successMessage"><b>${messages.NewComment}${messages.SaveComment}${messages.createReply}</b></span>
</p>

<%--if user has loged in, he can create comments for current post; if not, he should sign up or login to create comments--%>
<div style="background-color: darkgray">
    <c:choose>
        <c:when test="${sessionScope.user != null}">
            <div>
                <form action="commentcreate" method="post">
                    <div>Comment as ${sessionScope.user.userName}</div>
                    <input type="text" name="postId" value="${sessionScope.currentPost.postId}" hidden>
                    <div><input type="text" name="content" style="width: 500px;height: 200px"></div>
                    <div><input type="submit" value="COMMENT"></div>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div><h1>Log in or sign up to leave a comment</h1></div>
            <div><a href="usercreate">SIGN UP</a></div>
            <div><a href="userlogin">LOG IN</a></div>
        </c:otherwise>
    </c:choose>
</div><br/>

<%--current post's comments information--%>
    <div>
        <c:forEach items="${sessionScope.currentPostComment}" var="comment">
            <div style="background-color: chocolate">
            <c:set var="allCommentCurrentComment" scope="request" value="${comment}"/>
            <%--get father comments--%>
            <c:if test="${comment.fatherComment == null}">
                <div><c:out value="${comment.user.userName}"/></div>
                <div><c:out value="${comment.content}"/></div>
                <div><fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>

                <table>
                    <tr>
                        <td>
                                <%--user can reply other's comment--%>
                            <div>
                                <form action="commentreply" method="get">
                                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                    <div><input type="submit" value="Reply"></div>
                                </form>
                            </div>
                        </td>
                        <td>
                                <%--user can save other's comment--%>
                                <%--save post--%>
                                <%--only if user has login, they can save posts; otherwise they need to login first--%>
                            <c:choose>
                                <c:when test="${sessionScope.user == null}">
                                    <div>
                                        <form action="commentsave" method="post">
                                            <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                            <div><input type="submit" value="Save"></div>
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
                                                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                                    <div><input type="submit" value="Save"></div>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="commentunsave" method="post">
                                                    <input type="text" name="redirect" value="PostComment" hidden>
                                                    <input type="text" name="commentId" value="${userSave.comment.commentId}" hidden>
                                                    <div><input type="submit" value="unSave"></div>
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
                                        <div><input type="submit" value="Like"></div>
                                    </form>
                                </c:when>

                                <c:otherwise>
                                    <div>
                                        <%
                                            LikesDao likesDao = LikesDao.getInstance();
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
                                                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                                    <div>${numberOfLikes} people like <input type="submit" value="Like"></div>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="commentlikedelete" method="post">
                                                    <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                                    <div>${numberOfLikes} people like <input type="submit" value="unLike"></div>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>

                <%--get child comments--%>
                <div style="background-color: antiquewhite">
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
                                <div><c:out value="${childComment.user.userName}"/></div>
                                <div><c:out value="@${childComment.fatherComment.user.userName}: ${childComment.content}"/></div>
                                <div><fmt:formatDate value="${childComment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>

                                <table>
                                    <tr>
                                        <td>
                                                <%--user can reply other's comment--%>
                                            <div>
                                                <form action="commentreply" method="get">
                                                    <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                    <div><input type="submit" value="Reply"></div>
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
                                                            <div><input type="submit" value="Save"></div>
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
                                                                    <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                                    <div><input type="submit" value="Save"></div>
                                                                </form>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <form action="commentunsave" method="post">
                                                                    <input type="text" name="redirect" value="PostComment" hidden>
                                                                    <input type="text" name="commentId" value="${userSave.comment.commentId}" hidden>
                                                                    <div><input type="submit" value="unSave"></div>
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
                                                        <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                        <div><input type="submit" value="Like"></div>
                                                    </form>
                                                </c:when>

                                                <c:otherwise>
                                                    <div>
                                                        <%
                                                            LikesDao likesDao = LikesDao.getInstance();
                                                            Users user = (Users) session.getAttribute("user");
                                                            try {
                                                                Comments comment = (Comments) request.getAttribute("allChildCommentCurrentComment");
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
                                                                    <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                                                    <div>${numberOfLikes} people like <input type="submit" value="Like"></div>
                                                                </form>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <form action="commentlikedelete" method="post">
                                                                    <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                                                    <div>${numberOfLikes} people like <input type="submit" value="unLike"></div>
                                                                </form>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </table>

                            </c:if>
                        </c:if>
                    </c:forEach>
                </div>
            </c:if>
            </div><br/>
        </c:forEach>
    </div>


</center>
</body>
</html>
