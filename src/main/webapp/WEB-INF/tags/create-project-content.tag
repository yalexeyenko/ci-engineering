<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="createProjectAction" value="/do/createProject"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
        </ul>
    </div>
    <div id="create-project">
        <form action="${createProjectAction}" method="post" name="create_project">
            <h3>Создать проект</h3>
            <div class="field-wrap">
                <span>Имя проекта: </span>
                <input type="text" value="${unsavedName}" name="projectName" placeholder="введите название проекта*" required/>
                <c:if test="${not empty projectNameViolation}">
                    <span class="violation">${projectNameViolation}</span>
                </c:if>
            </div>
            <div class="field-wrap">
                <span>Дата окончания: </span>
                <input type="date" value="${unsavedDeadline}" id="project-deadline" name="projectDeadline" min="2016-01-01" max="2050-01-01" required>
                <c:if test="${not empty projectDeadlineViolation}">
                    <span class="violation">${projectDeadlineViolation}</span>
                </c:if>
            </div>
            <button class="create-project-button" type="submit"/>Создать</button>
            <c:if test="${not empty projectCreatedSuccess}">
                <span class="created-message">${projectCreatedSuccess}</span>
            </c:if>
        </form>
    </div>
</main>