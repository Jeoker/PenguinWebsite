<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>

<%--current post information--%>
<div><c:out value="${sessionScope.currentPost.title}" /></div>
<div><c:out value="${sessionScope.currentPost.content}" /></div>
<div><c:if test="${sessionScope.currentPost.picture != null}">
    <img src="${sessionScope.currentPost.picture}" width="100px">
</c:if></div>
<div><fmt:formatDate value="${sessionScope.currentPost.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div><br/>
<%--message: create comment successful or not--%>
<p>
    <span id="successMessage"><b>${messages.NewComment}${messages.SaveComment}${messages.createReply}</b></span>
</p>
<%--if user has loged in, he can create comments for current post; if not, he should sign up or login to create comments--%>
<div>
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
            <c:if test="${comment.fatherComment == null}">
                <div><c:out value="${comment.user.userName}"/></div>
                <div><c:out value="${comment.content}"/></div>
                <div><fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
                <%--user can save other's comment, but they cannot save their own comment--%>
                <div>
                    <form action="commentsave" method="post">
                        <input type="text" name="commentId" value="${comment.commentId}" hidden>
                        <div><input type="submit" value="Save"></div>
                    </form>
                </div>
                <%--user can reply other's comment, but they cannot save their own comment--%>
                <div>
                    <form action="commentreply" method="get">
                        <input type="text" name="commentId" value="${comment.commentId}" hidden>
                        <div><input type="submit" value="Reply"></div>
                    </form>
                </div>
                <div>
                    <c:forEach items="${sessionScope.currentPostComment}" var="childComment">
                        <c:if test="${childComment.fatherComment.commentId == comment.commentId}">
                            <div><c:out value="${childComment.user.userName}"/></div>
                            <div><c:out value="@${childComment.fatherComment.user.userName}: ${childComment.content}"/></div>
                            <div><fmt:formatDate value="${childComment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
                            <%--user can save other's comment, but they cannot save their own comment--%>
                            <div>
                                <form action="commentsave" method="post">
                                    <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                    <div><input type="submit" value="Save"></div>
                                </form>
                            </div>
                            <%--user can reply other's comment, but they cannot save their own comment--%>
                            <div>
                                <form action="commentreply" method="get">
                                    <input type="text" name="commentId" value="${childComment.commentId}" hidden>
                                    <div><input type="submit" value="Reply"></div>
                                </form>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                ---------------------------------------------
            </c:if>
        </c:forEach>
    </div>


</center>
</body>
</html>
