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
    <form method="post" style="width: 100%">
        <input type="hidden" name="method" value="create">
        <button >
            <svg width="24" height="24" viewBox="0 0 32 32" style="margin-left: calc(50% - 0.7em); visibility: visible">
                <g transform="scale(0.03125 0.03125)">
                    <path d="M992 384h-352v-352c0-17.672-14.328-32-32-32h-192c-17.672 0-32 14.328-32 32v352h-352c-17.672 0-32 14.328-32 32v192c0 17.672 14.328 32 32 32h352v352c0 17.672 14.328 32 32 32h192c17.672 0 32-14.328 32-32v-352h352c17.672 0 32-14.328 32-32v-192c0-17.672-14.328-32-32-32z"></path>
                </g>
            </svg>
        </button>
    </form>
    <%--@elvariable id="meals" type="java.util.List"--%>
    <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
    <c:set var="meals" value="${requestScope.meals}" />
    <table style="width: 90%">
        <tr bgcolor="#a9a9a9" style="height: 32px">
            <th style="text-align: left; width: 35%">Дата/Время</th>
            <th style="text-align: left; width: 20%">Описание</th>
            <th style="text-align: left; width: 20%">Калории</th>
            <th style="width: 5%"></th>
            <th style="width: 5%"></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <tr bgcolor="${meal.exceed ? '#ff7f50' : '#9acd32'}">
                <td ><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd 10:00"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td>

                    <form action="meals" method="post" style="height: 100%; width: 100%">
                        <input type="hidden" name="method" value="update">
                        <input type="hidden" name="id" value="${meal.id}">
                        <button style="margin-top: 15%">
                            <svg width="24" height="24" viewBox="0 0 32 32" style="margin-left: calc(50% - 0.7em); visibility: visible"><g transform="scale(0.03125 0.03125)"><path d="M864 0c88.364 0 160 71.634 160 160 0 36.020-11.91 69.258-32 96l-64 64-224-224 64-64c26.742-20.090 59.978-32 96-32zM64 736l-64 288 288-64 592-592-224-224-592 592zM715.578 363.578l-448 448-55.156-55.156 448-448 55.156 55.156z"></path></g></svg>
                        </button>

                    </form>
                </td>
                <td>
                    <form method="post" style="height: 100%; width: 100%">
                        <input type="hidden" name="method" value="delete">
                        <input type="hidden" name="id" value="${meal.id}">
                        <button style="margin-top: 15%">
                            <svg width="24" height="24" viewBox="0 0 32 32" style="margin-left: calc(50% - 0.7em); visibility: visible"><g transform="scale(0.03125 0.03125)"><path d="M128 320v640c0 35.2 28.8 64 64 64h576c35.2 0 64-28.8 64-64v-640h-704zM320 896h-64v-448h64v448zM448 896h-64v-448h64v448zM576 896h-64v-448h64v448zM704 896h-64v-448h64v448z"></path><path d="M848 128h-208v-80c0-26.4-21.6-48-48-48h-224c-26.4 0-48 21.6-48 48v80h-208c-26.4 0-48 21.6-48 48v80h832v-80c0-26.4-21.6-48-48-48zM576 128h-192v-63.198h192v63.198z"></path></g></svg>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
