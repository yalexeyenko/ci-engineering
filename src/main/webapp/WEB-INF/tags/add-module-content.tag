<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="create_module" value="/do/create-module"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="add_module" value="/do/pass-projectId"/>

<link rel="stylesheet" href="${css_path}/add-module-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li><a href="${main_page}">Home</a></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Project</a>
            </li>
            <li>
                <a id="view-modules" href="
                    <c:url value="${view_modules}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                    </c:url>
                ">View modules</a>
            </li>
            <li>
                <a id="add-module" href="
                    <c:url value="${add_module}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="add-module"></c:param>
                    </c:url>
                ">Add module</a>
            </li>
        </ul>
    </div>

    <form action="${create_module}" method="post" name="add-module">
        <h3>Create Module</h3>
        <div class="field-wrap">
            <span>Module name: </span>
            <input type="text" value="${unsavedName}" name="moduleName" placeholder="Enter module name*" required/>
            <c:if test="${not empty moduleNameViolation}">
                <span class="violation">${moduleNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Choose deadline: </span>
            <input type="date" value="${unsavedDeadline}" name="moduleDeadline" min="2016-01-01" max="2050-01-01" required>
            <c:if test="${not empty moduleDeadlineViolation}">
                <span class="violation">${moduleDeadlineViolation}</span>
            </c:if>
        </div>
        <input type="hidden" value="${projectId}" name="projectId"/>
        <button class="create-module-button" type="submit"/>Create</button>
        <c:if test="${not empty moduleCreatedSuccess}">
            <span class="created-message">${moduleCreatedSuccess}</span>
        </c:if>
    </form>
</main>