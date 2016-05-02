<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="specify_senior_action" value="/do/specifySeniorAction"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="specify_senior" value="/do/pass-projectId"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>

<link rel="stylesheet" href="${css_path}/specify-senior-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="projectName" value="${projectName}"></c:param>
                        <c:param name="projectDeadline" value="${projectDeadline}"></c:param>
                        <c:param name="projectFinished" value="${projectFinished}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Project</a>
            </li>
            <li><a href="
                    <c:url value="${specify_senior}">
                        <c:param name="projectId" value="${project.id}"></c:param>
                        <c:param name="passProjectId" value="specify-senior"></c:param>
                    </c:url>
                ">Senior Engineer</a>
            </li>
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