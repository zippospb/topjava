<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>

        <div class="row">
            <div class="col-7">
                <div class="card">
                    <div class="card-header">
                        <h5><spring:message code="meal.filterHeader"/></h5>
                    </div>
                    <div class="card-body py-0">
                        <form method="post" action="meals/filter">
                            <div class="row">
                                <div class="col-6">
                                    <div class="form-group">
                                        <label class="col-form-label" for="startDate">
                                            <spring:message code="meal.startDate"/>:
                                        </label>
                                        <input class="form-control col-7" type="date" name="startDate" id="startDate"
                                               value="${param.startDate}">
                                        <label class="col-form-label" for="endDate">
                                            <spring:message code="meal.endDate"/>:
                                        </label>
                                        <input class="form-control col-7" type="date" name="endDate" id="endDate"
                                               value="${param.endDate}">
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="form-group">
                                        <label class="col-form-label" for="startTime">
                                            <spring:message code="meal.startTime"/>:
                                        </label>
                                        <input class="form-control col-3" type="time" name="startTime" id="startTime"
                                               value="${param.startTime}">
                                        <label class="col-form-label" for="endTime">
                                            <spring:message code="meal.endTime"/>:
                                        </label>
                                        <input class="form-control col-3" type="time" name="endTime" id="endTime"
                                               value="${param.endTime}">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer">
                        <button class="button btn-danger" onclick="clearFilter()"><spring:message code="common.cancel"/></button>
                        <button class="button btn-primary" type="submit"><spring:message code="meal.filter"/></button>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        <button class="button btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="meal.add"/>
        </button>
        <%--<a href="meals/create"><spring:message code="meal.add"/></a>--%>
        <hr>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                <tr data-mealExceed="${meal.exceed}" data-id="${meal.id}">
                    <td>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals/update?id=${meal.id}"><span class="fa fa-pencil"/></a></td>
                    <td><a class="delete"><span class="fa fa-remove"></span></a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h3>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" name="id" value="${meal.id}">
                    <div class="form-group">
                        <label class="col-form-label" for="dateTime"><spring:message code="meal.dateTime"/>:</label>
                        <input type="datetime-local" class="form-control" value="${meal.dateTime}" name="dateTime" id="dateTime" required>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label" for="description"><spring:message code="meal.description"/>:</label>
                        <input type="text" class="form-control" value="${meal.description}" name="description" id="description" size=40 required>
                    </div>
                    <div class="form-group">
                        <label class="col-form-label" for="calories"><spring:message code="meal.calories"/>:</label>
                        <input type="number" class="form-control" value="${meal.calories}" name="calories" id="calories" required>
                    </div>
                    <button type="button" class="button btn-secondary" data-dismiss="modal">
                        <span class="fa fa-close"></span>
                        <spring:message code="common.cancel"/>
                    </button>
                    <button type="button" onclick="save()" class="button btn-primary">
                        <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>
                </form>
            </div>
            <div class="modal-footer">

            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>