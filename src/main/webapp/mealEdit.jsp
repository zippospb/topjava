<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Редактирование еды</title>
</head>
<body style="text-align: center">

    <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
    <form action="meals" method="post">
        <input type="hidden" name="id" value="${not empty meal ? meal.id : ''}">
        <input type="hidden" name="method" value="save">
        <c:set var="meals" value="${requestScope.meal}" />
        <p><label>Дата/Время</label></p>
        <p><input name="dateTime" value="${not empty meal ? meal.dateTime : ''}" placeholder="Дата/Время" type="datetime-local"></p>
        <p><label>Описание</label></p>
        <p><input name="description" value="${not empty meal ? meal.description : ''}" placeholder="Описание" type="text"></p>
        <p><label>Калории</label></p>
        <p><input name="calories" value="${not empty meal ? meal.calories : ''}" placeholder="Калории" type="number"></p>
        <button type="submit">Сохранить</button>
    </form>


</body>
</html>
