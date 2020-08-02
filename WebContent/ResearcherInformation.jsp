<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet">
<head>
    <title>Researcher Information</title>
</head>
<body>
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
                         <input type="text" id="username" name="firstName" value="${currentResearcher.getfirstName()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="password">Password</label>
                         <input type="text" id="password" name="lastName" value="${currentResearcher.getlastName()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="status">Status</label>
                         <input type="text" id="status" name="Gender" value="${currentResearcher.getGender()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="password">Password</label>
                         <input type="text" id="password" name="academicPaper" value="${currentResearcher.getacademicPaper()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="status">Status</label>
                         <input type="text" id="status" name="institute" value="${currentResearcher.getinstitute()}" class="form-control">
                     </div>
                     <div class="form-group">
                         <input type="submit" value="CHANGE" class="btn btn-primary">
                     </div>
                 </form>
             </c:if>
         </div>
     </center>
</body>
</html>
