<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 50px;
        }
        .container {
            width: 400px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: white;
            background-color: #dc3545;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Welcome</h2>
        <p>${message}</p>

        <c:choose>
            <c:when test="${not empty sessionScope.userId}">
                <p><strong>User ID:</strong> ${sessionScope.userId}</p>
                <p><strong>Email:</strong> ${sessionScope.email}</p>
                <p><strong>First Name:</strong> ${sessionScope.firstName}</p>
                <p><strong>Last Name:</strong> ${sessionScope.lastName}</p>
                <a href="${pageContext.request.contextPath}/logout" class="btn">Logout</a>
            </c:when>
            <c:otherwise>
                <p>You are not logged in.</p>
                <a href="${pageContext.request.contextPath}/saml/login" class="btn">Login</a>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
