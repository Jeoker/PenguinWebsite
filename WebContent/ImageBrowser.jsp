<%@ page import="web.dal.ImagesDao" %>
<%@ page import="web.dal.UAVsDao" %><%--
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
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>All Images</title>
</head>
<body>
<center>
    <br/>
    <br/>
    <h1>All Images</h1>
    <br/>
    <p>
        <span id="successMessage"><b>${messages.result}</b></span>
    </p>
    <div><a href="UploadNewImage" class="btn-group btn-group-lg">Upload New Images</a></div>
    <br/>

    <div class="container">
        <c:set var="totImages" value="${requestScope.totImages}"/>
        <c:set var="imagesPerPage" value="${requestScope.imagesPerPage}"/>
        <c:set var="totPages" value="${requestScope.totPages}"/>
        <c:set var="sIdx" value="${requestScope.sIdx}"/>
        <c:set var="eIdx" value="${requestScope.eIdx}"/>
        <c:set var="curImages" value="${requestScope.curImages}"/>
        <%
            ImagesDao imageDao = ImagesDao.getInstance();
        %>
        <c:forEach items="${curImages}" var="image">
            <div>
                <div><c:out value="${image.getFileName()}"/></div>
                <div><fmt:formatDate value="${image.getTimestamp()}" pattern="MM-dd-yyyy hh:mm:sa"/></div>
                <div><c:if test="${image.getMediaLink() != null}">
                    <c:set var="link" value="${image.getMediaLink()}"/>
                    <c:set var="id" value="${image.getImageId()}"/>
                    <%
                        int id = (Integer)pageContext.getAttribute("id");
                        String link = (String)pageContext.getAttribute("link");
                        int s_idx = link.indexOf("/d/")+3;
                        int e_idx = link.indexOf("/view");
                        String file_hash = link.substring(s_idx,e_idx);
                        link = link.substring(0,25) + "thumbnail?id=" + file_hash;
                        Float weather[] =
                                imageDao.getWeatherForImage(id);
                        if (weather == null) {
                            weather = new Float[2];
                            weather[0] = 0.f;
                            weather[1] = 0.f;
                        }
                        String UAVModel = imageDao.getUAVInfo(id);
                        if (UAVModel == null || UAVModel=="") {
                            UAVModel = "UnKnown";
                        }
                    %>
                    <img src="<%=link%>" width="400px">
                    <br/>
                    Real-time Temperature: <%=weather[0]%> &deg;C, Wind Speed:
                    <%=weather[1]%> km/h
                    <br/>
                    UAV Model: <%=UAVModel%>
                </c:if>
                </div>
                <div>
                    <form action="imagedelete" method="post">
                        <input type="text" name="imageId" value="${image.getImageId()}" hidden>
                        <div><input type="submit" value="delete" class="btn btn-success"></div>
                    </form>
                    <form action="updateimage" method="get">
                        <input type="text" name="imageId" value="${image.getImageId()}" hidden>
                        <div><input type="submit" value="update" class="btn btn-info"></div>
                    </form>
                </div>
            </div>
        </c:forEach>

        <div class="row justify-content-center">
            <div><nav><ul class="pagination">
                <li class="page-item ${page==1?'disabled':''}"><a class="page-link"
                                                                  href="<c:url value="/imagesFromSite?page=${page-1>1?page-1:1}"/>">Previous</a>
                </li>
                <c:if test="${page!=1}">
                    <li class="page-item">
                        <a class="page-link" href="<c:url value="/imagesFromSite?page=1"/>">1</a>
                    </li>
                </c:if>
                <c:if test="${page>2}">
                    <li class="page-item disabled">
                        <a class="page-link">...</a>
                    </li>
                </c:if>
                <li class="page-item active">
                    <a class="page-link" href="<c:url value="/imagesFromSite?page=${page}"/>">${page}</a>
                </li>
                <c:if test="${totalPages-page>1}">
                    <li class="page-item disabled">
                        <a class="page-link">...</a>
                    </li>
                </c:if>
                <c:if test="${page!=totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="<c:url value="/imagesFromSite?page=${totalPages}"/>">${totalPages}</a>
                    </li>
                </c:if>

                <li class="page-item ${page==totalPages?'disabled':''}">
                    <a class="page-link" href="<c:url value="/imagesFromSite?page=${page+1<totalPages?page+1:totalPages}"/>">Next</a>
                </li>
            </ul></nav></div>
        </div>
</center>
</body>
</html>
