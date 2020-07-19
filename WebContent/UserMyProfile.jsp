<%@ page import="web.dal.CommentsDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="web.dal.CollectionsDao" %>
<%@ page import="web.dal.LikesDao" %>
<%@ page import="web.model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>MyProfile</title>
</head>
<body>
<%--message: user login successful or not--%>
<%--    <p>--%>
<%--        <span id="successMessage"><b>${messages.NewPost}${messages.PostUnsave}${messages.CommentUnsave}${messages.deletePost}${messages.commentDelete}</b></span>--%>
<%--    </p>--%>

    <%--if user has not signed up or loged in, they can sign up or login;
    if they do, they can view their profile or log out--%>
    <div class="navbar" style="background-color: white">
        <%-- web name--%>
        <a href="index.jsp" style="color: black"><h1>PenguinWeb</h1></a>
            <div>
                <%--user log out--%>
                <form action="userlogout" method="post" style="float: left">
                    <input type="submit" value="Log Out" class="btn btn-info" style="width: 120px;height: 50px;margin-left: 20px">
                </form>
            </div>
    </div>

    <%--Navbar--%>
    <div class="navbar" style="background-color: white;border-top: solid 1px gray;border-bottom: solid 1px gray">
        <div>
            <%--view/update user's personal information--%>
            <a href="finduser" style="color: black">USER SETTING</a>
            <%--user can create new posts--%>
            <a href="postcreate" style="color: black">NEW POST</a>
            <%--user can view their posts--%>
            <a href="findpost" style="color: black">POSTS</a>
            <%--user can view their comments--%>
            <a href="findcomment" style="color: black">COMMENTS</a>
            <%--user can view their collections--%>
            <a href="findsave" style="color: black">SAVED</a>
        </div>
    </div>

     <%--form: user update their personal information--%>
     <center>
         <c:if test="${messages.update != null}">
             <div class="alert alert-success" role="alert">
                     ${messages.update}
             </div>
         </c:if>
         <div>
             <c:if test="${currentUser != null}">
                 <div class="middleWord" style="border-bottom: 1px solid white"><h1>USER SETTING</h1></div><br/>
                 <form action="userupdate" method="post" style="width: 250px; height: 100px">

                     <input type="hidden" name="userId" value="${currentUser.getUserId()}"/>
                     <div class="form-group row">
                         <label for="username">UserName</label>
                         <input type="text" id="username" name="username" value="${currentUser.getUserName()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="password">Password</label>
                         <input type="text" id="password" name="password" value="${currentUser.getPassword()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="status">Status</label>
                         <input type="text" id="status" name="status" value="${currentUser.getStatus()}" class="form-control" readonly/>
                     </div>
                     <div class="form-group">
                         <input type="submit" value="CHANGE" class="btn btn-primary">
                     </div>
                 </form>
             </c:if>
         </div>
     </center>

<%--Create a new post--%>
<c:if test="${messages.isNewPost != null}">
    <center>
        <div class="middleWord" style="border-bottom: 1px solid white"><h1>NEW POSTS</h1></div><br/>

        <form action="upload" method="post" enctype="multipart/form-data" style="width: 500px; height: 100px">
            <div>
                <input type="text" id="title" name="title" placeholder="Title" class="form-control">
            </div>
            <div>
                <textarea name="content" class="form-control" cols="30" rows="10" placeholder="Text"></textarea>
            </div>
            <div style="background-color: white">
                <div class="newPostPicture">
                    <div class="grayWord">Choose a picture to upload</div>
                    <%--picture--%>
                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="uploadFile">
                </div>
            </div>

            <input type="submit" value="Post" class="btn btn-primary"/>
        </form>
    </center>
</c:if>

<%--View Own Posts--%>
<c:if test="${userpost != null}">
    <div class="middleWord" style="border-bottom: 1px solid white;text-align: center"><h1>POSTS</h1></div><br/>
    <c:forEach items="${userpost}" var="post" >
        <c:set var="allPostCurrentPost" scope="request" value="${post}"/>
        <div class="post" style="background-color: white">
            <div class="smallWord">
                <c:out value="${post.user.userName}" />
                Posted by <fmt:formatDate value="${post.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
            </div>
            <div class="bigWord"><c:out value="${post.title}" /></div>
            <div class="middleWord"><c:out value="${post.content}" /></div>
            <div align="center">
                <c:if test="${post.picture != null}">
                    <img src="${post.picture}" width="500px" height="250px">
                </c:if>
            </div><br/>

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
                                        <input type="text" name="redirect" value="FindPost" hidden>
                                        <input type="text" name="postId" value="${post.postId}" hidden>
                                        <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="postunsave" method="post">
                                        <input type="text" name="redirect" value="FindPost" hidden>
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
                            // get the number of likes for this post
                            LikesDao likesDao = LikesDao.getInstance();
                            // get like by userId and PostId
                            Likes like = null;
                            try {
                                int numberOfLikes = likesDao.getLikeNumberByPostId(post.getPostId());
                                request.setAttribute("numberOfLikes",numberOfLikes);

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
                                        <input type="text" name="redirect" value="FindPost" hidden>
                                        <input type="text" name="postId" value="${post.postId}" hidden>
                                        <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="postlikedelete" method="post">
                                        <input type="text" name="redirect" value="FindPost" hidden>
                                        <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                        <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-danger"></div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                    <td>
                        <%--delete post, user can delete their own posts--%>
                        <form action="postdelete" method="post">
                            <input type="text" name="postId" value="${post.getPostId()}" hidden>
                            <div><input type="submit" value="Delete" class="btn btn-secondary"></div>
                        </form>
                    </td>
                    <td>
                        <%--edit post, user can edit their own posts--%>
                        <form action="postupdate" method="get">
                            <input type="text" name="postId" value="${post.getPostId()}" hidden>
                            <div><input type="submit" value="Edit Post" class="btn btn-secondary"></div>
                        </form>
                    </td>
                </tr>
            </table>
        </div><br/>
    </c:forEach>
</c:if>

<%--user can view their comments--%>
<%--user's comments--%>
<c:if test="${userComment != null}">
<div class="middleWord" style="border-bottom: 1px solid white;text-align: center"><h1>COMMENTS</h1></div><br/>
    <div>
        <c:forEach items="${userComment}" var="comment">
            <c:set var="allCommentCurrentComment" scope="request" value="${comment}"/>
            <div class="post" style="background-color: white">
                <div>
                    <div class="smallWord">
                        <c:out value="${comment.user.userName}"/>
                        Commented by <fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
                    </div>
                    <div class="middleWord"><c:out value="${comment.content}"/></div>
                </div>
                <table>
                    <tr>
                        <td>
                            <%--user can reply other's comment--%>
                            <div>
                                <form action="commentreply" method="get">
                                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                    <div><input type="submit" value="Reply" class="btn btn-secondary"></div>
                                </form>
                            </div>
                        </td>
                        <td>
                            <%--save comments--%>
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
                                            <input type="text" name="redirect" value="FindComment" hidden>
                                            <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                            <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="commentunsave" method="post">
                                            <input type="text" name="redirect" value="FindComment" hidden>
                                            <input type="text" name="commentId" value="${userSave.comment.commentId}" hidden>
                                            <div><input type="submit" value="unSave" class="btn btn-primary"></div>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                        <td>
                            <%--Like comments--%>
                            <div>
                                <%
                                    LikesDao likesDao = LikesDao.getInstance();
                                    try {
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
                                            <input type="text" name="redirect" value="FindComment" hidden>
                                            <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="commentlikedelete" method="post">
                                            <input type="text" name="redirect" value="FindComment" hidden>
                                            <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-danger"></div>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                        <td>
                            <div>
                                <%--delete comments, user can delete their own comments--%>
                                <form action="commentdelete" method="post">
                                    <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                    <div><input type="submit" value="Delete" class="btn btn-secondary"></div>
                                </form>
                            </div>
                        </td>
                        <td>
                                <%--edit comments, user can edit their own comments--%>
                            <form action="commentupdate" method="get">
                                <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                <div><input type="submit" value="Edit Post" class="btn btn-secondary"></div>
                            </form>
                        </td>
                    </tr>
                </table>
            </div><br/>
        </c:forEach>
    </div>
</c:if>

<%--user can view their collections--%>
<c:if test="${savedPost != null || savedComment != null}">
    <div>
        <div class="middleWord" style="border-bottom: 1px solid white;text-align: center"><h1>SAVED</h1></div><br/>

        <%--saved posts--%>
        <c:forEach items="${savedPost}" var="post" >
            <c:set var="allPostCurrentPost" scope="request" value="${post}"/>
            <div  class="post" style="background-color: white">
                <div class="smallWord">
                    <c:out value="${post.user.userName}" />
                    Posted by <fmt:formatDate value="${post.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
                </div>
                <div class="bigWord"><c:out value="${post.title}" /></div>
                <div class="middleWord"><c:out value="${post.content}" /></div>
                <div align="center">
                    <c:if test="${post.picture != null}">
                        <img src="${post.picture}" width="500px" height="250px">
                    </c:if>
                </div><br/>
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
                                            <input type="text" name="redirect" value="FindSave" hidden>
                                            <input type="text" name="postId" value="${post.postId}" hidden>
                                            <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="postunsave" method="post">
                                            <input type="text" name="redirect" value="FindSave" hidden>
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
                                // get the number of likes for this post
                                LikesDao likesDao = LikesDao.getInstance();
                                // get like by userId and PostId
                                Likes like = null;
                                try {
                                    int numberOfLikes = likesDao.getLikeNumberByPostId(post.getPostId());
                                    request.setAttribute("numberOfLikes",numberOfLikes);

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
                                            <input type="text" name="redirect" value="FindSave" hidden>
                                            <input type="text" name="postId" value="${post.postId}" hidden>
                                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="postlikedelete" method="post">
                                            <input type="text" name="redirect" value="FindSave" hidden>
                                            <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                            <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-danger"></div>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </table>
            </div><br/>
        </c:forEach>

        <%--saved comments--%>
        <div>
            <c:forEach items="${savedComment}" var="comment">
                <c:set var="allCommentCurrentComment" scope="request" value="${comment}"/>
                <div  class="post" style="background-color: white">
                    <div>
                        <div class="smallWord">
                            <c:out value="${comment.user.userName}"/>
                            Commented by <fmt:formatDate value="${comment.created}" pattern="MM-dd-yyyy HH:mm:ss"/>
                        </div>
                        <div class="middleWord"><c:out value="${comment.content}"/></div>
                    </div>
                    <table>
                        <tr>
                            <td>
                                    <%--user can reply other's comment--%>
                                <div>
                                    <form action="commentreply" method="get">
                                        <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                        <div><input type="submit" value="Reply" class="btn btn-secondary"></div>
                                    </form>
                                </div>
                            </td>
                            <td>
                                    <%--save comments--%>
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
                                                <input type="text" name="redirect" value="FindSave" hidden>
                                                <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                                <div><input type="submit" value="Save" class="btn btn-secondary"></div>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="commentunsave" method="post">
                                                <input type="text" name="redirect" value="FindSave" hidden>
                                                <input type="text" name="commentId" value="${userSave.comment.commentId}" hidden>
                                                <div><input type="submit" value="unSave" class="btn btn-primary"></div>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                            <td>
                                    <%--Like comments--%>
                                <div>
                                    <%
                                        LikesDao likesDao = LikesDao.getInstance();
                                        try {
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
                                                <input type="text" name="redirect" value="FindSave" hidden>
                                                <input type="text" name="commentId" value="${comment.commentId}" hidden>
                                                <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-secondary"></div>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="commentlikedelete" method="post">
                                                <input type="text" name="redirect" value="FindSave" hidden>
                                                <input type="text" name="likeId" value="${userLike.likeId}" hidden>
                                                <div><input type="submit" value="${numberOfLikes} Likes" class="btn btn-danger"></div>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div><br/>
            </c:forEach>
        </div>
    </div>
</c:if>

</body>
</html>
