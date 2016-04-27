<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="specify_senior_action" value="/do/specifySeniorAction"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/cpecify-senior-content.css">

<main class="content">
    <form action="${specify_senior_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
        <h3>Specify Senior Engineer: </h3>
        <select name="projectSenior" size="1">
            <c:forEach items="${seniors}" var="senior">
                <c:if test="${senior eq project.senior}">
                    <option value="${senior.id}" selected>${senior.firstName} ${senior.lastName}</option>
                </c:if>
                <c:if test="${senior ne project.senior}">
                    <option value="${senior.id}">${senior.firstName} ${senior.lastName}</option>
                </c:if>
            </c:forEach>
        </select>
        <input type="hidden" name="projectId" value="${project.id}"/>
        <button class="specify-senior-button" type="submit"/>
        Save</button>
    </form>
</main>