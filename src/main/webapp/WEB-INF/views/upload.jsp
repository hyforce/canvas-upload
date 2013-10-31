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

		      
		       <table class="table" style="width:300px">  
        <thead>  
          <tr>  
            <th style="font-size:10px;">Uploaded Files</th>  
            <th></th>  
            <th></th>  
            
          </tr>  
        </thead>  
        <tbody>  
          <c:forEach var="file" items="${uploadedFiles}"> 
             
        
          <tr>  
              
            <td style="font-size:10px;"><a href=""><c:out value="${file.fileName}"/></a> </td>  
            <td style="font-size:10px;"><a href="/delete?id=${file.id}&sObjectId=${file.sObjectId}">Delete</a></td>  
          </tr>  
        </c:forEach>    
        </tbody>  
      </table>  

				<table>
				<tr>
				    <td><input style="font-size:12px;width:25px;" type="submit" value="upload" /></td>
					<td><input style="font-size:11px;" name="file" type="file" /></td>
					<td><input style="font-size:10px;" name="sObjectId" type="hidden" value="${sObjectId}"/></td>
					
				</tr>
				</table>
	</form:form>
	
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
</body>
</html>