<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<p align="right"><c:out value="${user.fullName}"/></p>
<p align="right"><c:out value="${status}"/></p>
<form action="/logout" method="post">
    <p align="right"><input type="submit" value="Logout "></p>
</form>
<table class="item-table">
    <tr>
        <th>E-Mail</th>
        <th>Full Name</th>
    </tr>
    <c:forEach items="${usersList}" var="users">
        <tr>
            <td>${users.mail}</td>
            <td>${users.fullName}</td>
           <c:choose>
                <c:when test="${users.login == user.login}">
                    <td><a href="/edit?login=${user.login}">Edit</a></td>
                </c:when>
            </c:choose>
        </tr>
    </c:forEach>
</table>
</body>
</html>
