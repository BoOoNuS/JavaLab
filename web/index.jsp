<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>authentication</title>
</head>
<body>
<form action="/authentication" method="post">
    login:<br/>
    <input type="text" maxlength="20" size="40" name="login"><br/><br/>
    password:<br/>
    <input type="password" maxlength="20" size="40" name="password"><br/><br/>
    <input type="submit" value="Sign in">
</form>
<form action="/views/registration.jsp">
    <input type="submit" value="Sign up">
</form>
</body>
</html>
