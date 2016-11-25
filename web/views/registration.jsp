<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>registration</title>
</head>
<body>
<p align="right"><c:out value="${user.fullName}"/></p>
<p align="right"><c:out value="${status}"/></p>
<form action="/logout" method="post">
    <p align="right"><input type="submit" value="Logout "></p>
</form>
<form action="/registration" method="post">
    login:<br/>
    <input type="text" maxlength="20" size="40" name="login"><br/><br/>
    full name:<br/>
    <input type="text" maxlength="30" size="40" name="fullName"><br/><br/>
    e-mail:<br/>
    <input type="email" maxlength="30" size="40" name="mail"><br/><br/>
    password:<br/>
    <input type="password" maxlength="20" size="40" name="password"><br/><br/>
    <input type="submit" value="Sign up">
</form>
</body>
</html>
