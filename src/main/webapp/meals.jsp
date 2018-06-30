<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <label for="caloriesPerDay">Норма калорий в день</label>
    <input id="caloriesPerDay" name="caloriesPerDay" type="number">
    <%--@elvariable id="meals" type="java.util.List"--%>
    <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
    <c:set var="meals" value="${requestScope.meals}" />
    <table style="width: 90%">
        <tr>
            <th style="text-align: left; width: 35%">Дата/Время</th>
            <th style="text-align: left; width: 20%">Описание</th>
            <th style="text-align: left; width: 20%">Калории</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <tr bgcolor="${meal.exceed ? '#ff7f50' : '#9acd32'}">
                <td ><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd 10:00"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
