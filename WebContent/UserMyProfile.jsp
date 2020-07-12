<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyProfile</title>
</head>
<body>
<center>
<%--message: user login successful or not--%>
    <p>
        <span id="successMessage"><b>${messages.NewPost}${messages.PostUnsave}${messages.CommentUnsave}${messages.deletePost}${messages.commentDelete}</b></span>
    </p>

    <h1>MyProfile</h1><br/>

<%--Home Page--%>
    <div><a href="index.jsp">Home Page</a></div>

<%--view/update user's personal information--%>
    <div><a href="finduser">User Settings</a></div>

<%--user can create new posts--%>
    <div><a href="postcreate">New Post</a></div>

<%--user can view their posts--%>
    <div><a href="findpost">POSTS</a></div>
    <c:forEach items="${userpost}" var="post" >
        <div>
            <div><c:out value="${post.getTitle()}" /></div>
            <div><c:out value="${post.getContent()}" /></div>
            <div><c:if test="${post.getPicture() != null}">
                <img src="${post.getPicture()}" width="100px">
            </c:if></div>
            <div><fmt:formatDate value="${post.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
            <div>
                <%--post's comments--%>
                <form action="postcomment" method="post">
                    <input type="text" name="postId" value="${post.getPostId()}" hidden>
                    <div><input type="submit" value="Comment"></div>
                </form>
                <%--delete post, user can delete their own posts--%>
                <form action="postdelete" method="post">
                    <input type="text" name="postId" value="${post.getPostId()}" hidden>
                    <div><input type="submit" value="Delete"></div>
                </form>
            </div>
            ---------------------------------
        </div>
    </c:forEach>

    <%--user can view their comments--%>
    <div><a href="findcomment">COMMENTS</a></div>
    <%--user's comments--%>
    <div>
        <c:forEach items="${userComment}" var="comment">
            <div>
                <div><c:out value="${comment.user.userName}"/></div>
                <div><c:out value="${comment.content}"/></div>
                <div><fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
            </div>
            <div>
                    <%--delete comments, user can delete their own comments--%>
                <form action="commentdelete" method="post">
                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                    <div><input type="submit" value="Delete"></div>
                </form>
            </div>
            ------------------------------------
        </c:forEach>
    </div>

<%--user can view their collections--%>
    <div><a href="findsave">SAVED</a></div>
<%--saved posts--%>
    <c:forEach items="${savedPost}" var="post" >
        <div>
            <div><c:out value="${post.getTitle()}" /></div>
            <div><c:out value="${post.getContent()}" /></div>
            <div><c:if test="${post.getPicture() != null}">
                <img src="${post.getPicture()}" width="100px">
            </c:if></div>
            <div><fmt:formatDate value="${post.getCreated()}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
                <%--post's comments--%>
            <div>
                <form action="postcomment" method="post">
                    <input type="text" name="postId" value="${post.getPostId()}" hidden>
                    <div><input type="submit" value="Comment"></div>
                </form>
                    <%--unsave post--%>
                <form action="postunsave" method="post">
                    <input type="text" name="postId" value="${post.getPostId()}" hidden>
                    <div><input type="submit" value="Unsave"></div>
                </form>
            </div>
            ---------------------------------
        </div>
    </c:forEach>

    <%--saved comments--%>
    <div>
        <c:forEach items="${savedComment}" var="comment">
            <div>
                <div><c:out value="${comment.user.userName}"/></div>
                <div><c:out value="${comment.content}"/></div>
                <div><fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
            </div>
            <%--unsave comment--%>
            <div>
                <form action="commentunsave" method="post">
                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                    <div><input type="submit" value="Unsave"></div>
                </form>
            </div>
            ------------------------------------
        </c:forEach>
    </div>


</center>
</body>
</html>
