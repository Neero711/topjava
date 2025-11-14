<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 03.11.2025
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .excess-true {
            background-color: #ffcccc;
        }
        .excess-false {
            background-color: #ccffcc;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <tr>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr class="excess-${meal.excess}">
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" />
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=update&id=${meal.id}">Edit</a>
                <a href="meals?action=delete&id=${meal.id}"
                   onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
