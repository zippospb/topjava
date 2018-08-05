<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><a href="index">Home</a></h3>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form:form method="post" action="${pageContext.request.contextPath}/meals/save/${requestScope.id != null ? requestScope.id : ''}" modelAttribute="meal">
        <form:hidden path="id"/>
        <dl>
            <dt>DateTime:</dt>
            <dd><form:input type="datetime-local" path="dateTime"/></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><form:input size="40" path="description"/></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><form:input type="number" path="calories"/></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form:form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
