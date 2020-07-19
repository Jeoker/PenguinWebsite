<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Comment</title>
</head>
<body>
<center>
    <div class="middleWord" style="border-bottom: 1px solid white"><h1>EDIT COMMENT</h1></div><br/>

    <form method="post" action="commentupdate" style="width: 500px; height: 100px">
        <input type="text" name="commentId" value="${comment.commentId}" hidden>
        <div>
            <textarea name="newContent" class="form-control" cols="30" rows="10">${comment.content}</textarea>
        </div><br/>
        <input type="submit" value="SAVE" class="btn btn-primary"/>
    </form>
</center>
</body>
</html>
