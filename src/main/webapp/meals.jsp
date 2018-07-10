<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <form method="get">
        <input type="hidden" name="action" value="filter">
        <table>
            <tr>
                <td colspan="2"><h4>Фильтрация по дате/времени</h4></td>
            </tr>
            <tr>
                <td style="width: 50%;"><label>От даты</label></td>
                <td style="width: 50%;"><label>От времени</label></td>
            </tr>
            <tr>
                <td>
                    <input value="${param.fromDate}" type="date" name="fromDate" title="От даты">
                </td>
                <td>
                    <input value="${param.fromTime}" type="time" name="fromTime" title="От времени">
                </td>
            </tr>
            <tr>
                <td><label>До даты</label></td>
                <td><label>От времени</label></td>
            </tr>
            <tr>
                <td><input value="${param.toDate}" type="date" name="toDate" title="До даты"></td>
                <td><input value="${param.toTime}" type="time" name="toTime" title="До времени"></td>
            </tr>
            <tr><td colspan="2"><button type="submit">Отфильтровать</button></td></tr>
        </table>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>