<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>delete</title>
</head>
<body>
<p align="right"><c:out value="${user.fullName}"/></p>
<p align="right"><c:out value="${status}"/></p>
<form action="/logout" method="post">
    <p align="right"><input type="submit" value="Logout "></p>
</form>
<form action="/delete" method="post">
    Вы уверены что хотите удалить пользователя?<br/>
    <input type="submit" value="Yes">
</form><form action="/views/admin.jsp">
    <input type="submit" value="No">
</form>
</body>
</html>
