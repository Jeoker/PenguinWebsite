<%--
  Created by IntelliJ IDEA.
  User: jeoker
  Date: 7/13/20
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<div class="bodystyle"></div>

<head>
    <title>Upload New Image</title>
</head>
<body>
<center>
    <p>
        <span id="successMessage"><b>${messages.success}</b></span>
    </p>
    <form action="UploadNewImage" method="post">
        <p>
            <label for="name">Name</label>
            <input id="name" name="name" value="">
        </p>
        <p>
            <label for="type">file type</label>
            <input id="type" name="type" value="">
        </p>
        <p>
            <label for="path">path</label>
            <input id="path" name="path" value="">
        </p>
        <p>
            <label for="site">site</label>
            <input id="site" name="site" value="">
        </p>
        <p>
            <input type="submit">
        </p>
    </form>
    <br/><br/>
</center>
</body>
</html>
