<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${meal.id == null ? 'Add Meal' : 'Edit Meal'}</title>
</head>
<body>
<h2>${meal.id == null ? 'Add New Meal' : 'Edit Meal'}</h2>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">

    <dl>
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}" required></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" name="description" value="${meal.description}" size=50 required></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" name="calories" value="${meal.calories}" required></dd>
    </dl>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>