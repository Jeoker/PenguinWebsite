<%--
  Created by IntelliJ IDEA.
  User: na
  Date: 7/2/20
  Time: 6:00 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>upload image</h1>
<form method="post" action="upload" enctype="multipart/form-data">
    <label>
        <input type="text" name="title">
    </label>
    选择一个文件:
    <input type="file" name="uploadFile" />
    <br/><br/>
    <input type="submit" value="Upload" />
</form>
</body>
</html>
