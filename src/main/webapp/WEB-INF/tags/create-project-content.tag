<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="createProjectAction" value="/do/createProject"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="js_path" value="${pageContext.request.contextPath}/js"/>
<c:url var="main_page" value="/do/main-page"/>

<link rel="stylesheet" href="${css_path}/create-project-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <form action="${createProjectAction}" method="post" name="create_project">
        <h3>Create project</h3>
        <div class="field-wrap">
            <span>Project name: </span>
            <input type="text" value="${unsavedName}" name="projectName" placeholder="Enter project name*" required/>
            <c:if test="${not empty projectNameViolation}">
                <span class="violation">${projectNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Choose deadline: </span>
            <input type="date" id="project-deadline" name="projectDeadline" min="2016-01-01" max="2050-01-01" required>
            <c:if test="${not empty projectDeadlineViolation}">
                <span class="violation">${projectDeadlineViolation}</span>
            </c:if>
        </div>
        <button class="create-project-button" type="submit"/>Create</button>
    </form>
</main>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="${js_path}/current-date.js"></script>