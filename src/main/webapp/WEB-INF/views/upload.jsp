<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

 
<form:form method="post" action="upload"
        modelAttribute="uploadForm" enctype="multipart/form-data">
    <div>
      <input name="file" type="file" /> <input type="submit" value="upload" />
    </div>
    
 
    
            
    
   
    
   
    
</form:form>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
</body>
</html>