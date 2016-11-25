<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>admin page</title>
</head>
<body>
<p align="right"><c:out value="${user.fullName}"/></p>
<p align="right"><c:out value="${status}"/></p>
<form action="/logout" method="post">
    <p align="right"><input type="submit" value="Logout "></p>
</form>

<table class="item-table">
    <tr>
        <th>Login</th>
        <th>E-Mail</th>
        <th>Full Name</th>
    </tr>
    <c:forEach items="${usersList}" var="user">
        <tr>
            <td>${user.login}</td>
            <td>${user.mail}</td>
            <td>${user.fullName}</td>
            <td><a href="/delete?login=${user.login}&mail=${user.mail}&fullName=${user.fullName}&password=${user.password}">delete</a></td>
            <td><a href="/edit?login=${user.login}">edit</a></td>
        </tr>
    </c:forEach>
</table>
<form action="/views/registration.jsp">
    <input type="submit" value="New user">
</form>
</body>
</html>
