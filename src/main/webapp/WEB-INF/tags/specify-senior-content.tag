<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="specify_senior_action" value="/do/specifySeniorAction"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>

<link rel="stylesheet" href="${css_path}/cpecify-senior-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <form action="${specify_senior_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
        <h3>Specify Senior Engineer: </h3>
        <select name="projectSenior" size="1">
            <c:if test="${empty seniorId}">
                <option disabled selected>Select Senior Engineer...</option>
            </c:if>
            <c:if test="${not empty seniorId}">
                <option value="${seniorId}">${seniorFirstName} ${seniorLastName}</option>
            </c:if>
            <c:forEach items="${seniors}" var="senior">
                <c:if test="${seniorId ne senior.id}">
                    <option value="${senior.id}">${senior.firstName} ${senior.lastName}</option>
                </c:if>
            </c:forEach>
        </select>
        <input type="hidden" name="projectId" value="${project.id}"/>
        <button class="specify-senior-button" type="submit"/>
        Save</button>
        <c:if test="${not empty specifySeniorSuccess}">
            <span class="violation">${specifySeniorSuccess}</span>
        </c:if>
    </form>
</main>