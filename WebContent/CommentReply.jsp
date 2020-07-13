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
    <p>
        <span id="successMessage"><b>${messages.createReply}</b></span>
    </p>

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
<%--        <c:if test="${sessionScope.currentReply != null}">--%>
<%--            <div>--%>
<%--                <div><c:out value="${sessionScope.currentReply.user.userName}"/></div>--%>
<%--                <div>@<c:out value="${sessionScope.currentReply.fatherComment.user.userName}: ${sessionScope.currentReply.content}"/></div>--%>
<%--                <div><fmt:formatDate value="${sessionScope.currentReply.created}" pattern="MM-dd-yyyy hh:mm:sa"/></div>--%>
<%--            </div>--%>
<%--        </c:if>--%>
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
</center>
</body>
</html>
