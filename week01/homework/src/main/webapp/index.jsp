<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<%@ taglib uri="/WEB-INF/custom-tags.tld" prefix="sm" %>
<sm:common-response-headers cacheControl="no-cache" expires="-2" pragma="no-cache" />
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>