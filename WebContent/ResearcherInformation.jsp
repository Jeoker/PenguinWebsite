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
             <c:if test="${currentResearcher != null}">
                 <div class="middleWord" style="border-bottom: 1px solid white"><h1>USER SETTING</h1></div><br/>
                 <form action="researcherupdate" method="post" style="width: 250px; height: 200px">

                     <input type="hidden" name="userId" value="${currentResearcher.getUserId()}"/>
                     <div class="form-group row">
                         <label for="username">firstName</label>
                         <input type="text" id="FirstName"" name="FirstName" value="${currentResearcher.getFirstName()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="password">lastName</label>
                         <input type="text" id="LastName" name="LastName" value="${currentResearcher.getLastName()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="status">Gender</label>
                         <input type="text" id="Gender" name="Gender" value="${currentResearcher.getGender()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="password">academicPaper</label>
                         <input type="text" id="AcademicPaper" name="AcademicPaper" value="${currentResearcher.getAcademicPaper()}" class="form-control">
                     </div>
                     <div class="form-group row">
                         <label for="status">institute</label>
                         <input type="text" id="Institute" name="Institute" value="${currentResearcher.getInstitute()}" class="form-control">
                     </div>
                     <div class="form-group">
                         <input type="submit" value="CHANGE" class="btn btn-primary">
                     </div>
                     
                    
		             <br/>
	                 <a href="ResearcherMyProfile.jsp" class="btn btn-info" role="button">My Profile</a>
	          
                 </form>
             </c:if>
         </div>
     </center>
</body>
</html>
