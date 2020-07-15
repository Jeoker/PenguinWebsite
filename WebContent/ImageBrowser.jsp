<%--
  Created by IntelliJ IDEA.
  User: jeoker
  Date: 7/12/20
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Images</title>
</head>
<body>
    <center>
    <h1>All Images</h1>
    <%--<div><a href="/imagesFromSite">Post</a></div><br/>--%>
    <p>
        <span id="successMessage"><b>${messages.result}</b></span>
    </p>
    <h1>Upload new Image</h1><br/>
    <div><a href="UploadNewImage">Upload</a></div><br/>

    <c:forEach items="${siteImages}" var="image" >
<%--        <c:if test="${image.getImageId() < 0}">--%>
        <div>
            <div><c:out value="${image.getFileName()}" /></div>
            <div><c:out value="${image.getFileType()}" /></div>
            <div><c:out value="${image.getMediaLink()}" /></div>
            <div><fmt:formatDate value="${image.getTimestamp()}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
            <div><c:if test="${image.getMediaLink() != null}">
                <img src="${image.getMediaLink()}" width="300px">
            </c:if>
            </div>
            <div>
                <form action="imagedelete" method="post">
                    <input type="text" name="imageId" value="${image.getImageId()}" hidden>
                    <div><input type="submit" value="delete"></div>
                </form>
                <form action="updateimage" method="get">
                    <input type="text" name="imageId" value="${image.getImageId()}" hidden>
                    <div><input type="submit" value="update"></div>
                </form>
            </div>
<%--        </c:if>--%>
        </div>
    </c:forEach>

    </center>
</body>
</html>
