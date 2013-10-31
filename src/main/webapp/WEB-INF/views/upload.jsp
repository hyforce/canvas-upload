<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css"
	rel="stylesheet">

</head>
<body>

  
	<form:form method="post" action="upload" modelAttribute="uploadForm"
		enctype="multipart/form-data">

		      
		       <table class="table">  
        <thead>  
          <tr>  
            <th style="font-size:20px;">File Name</th>  
            <th></th>  
            <th></th>  
            
          </tr>  
        </thead>  
        <tbody>  
          <c:forEach var="fileName" items="${uploadedFiles}"> 
             
        
          <tr>  
              
            <td><c:out value="${fileName}"/> </td>  
            <td>View</td>  
            <td>Delete</td>  
          </tr>  
        </c:forEach>    
        </tbody>  
      </table>  

				<table>
				<tr>
					<td><input name="file" type="file" /></td>
					<td><input class="btn btn-success" type="submit" value="upload" /></td>
				</tr>
				</table>
	</form:form>
	
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
</body>
</html>