<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>File Upload</title>

</head>
<body>
<h1>File Upload</h1>
 
<form:form method="post" action="upload"
        modelAttribute="uploadForm" enctype="multipart/form-data">
 
    <p>Select file to upload</p>
 
    
    <table id="fileTable">
        <tr>
            <td><input name="file" type="file" /></td>
        </tr>
        
    </table>
    <br/><input type="submit" value="upload" />
    <p> SobjectId : ${sObjectId} </p>
   
    <input type="hidden" value="${sObjectId}" />
</form:form>
</body>
</html>