<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/edit-main-project-info"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>

<link rel="stylesheet" href="${css_path}/edit-project-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <form action="${edit_main_project_info}" method="post" name="edit-main-project-info">
        <h3>Edit main project info: </h3>
        <div class="field-wrap">
            <span>Project name: </span>
            <input type="text" value="${projectName}" name="projectName" placeholder="Enter project name*" required/>
            <c:if test="${not empty projectNameViolation}">
                <span class="violation">${projectNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Project start date: </span>
            <input type="date" value="${projectStartDate}" name="projectStartDate" placeholder="Enter project start date*" required/>
            <c:if test="${not empty projectStartDateViolation}">
                <span class="violation">${projectStartDateViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Project deadline: </span>
            <input type="date" value="${projectDeadline}" name="projectDeadline" placeholder="Enter project deadline*"required/>
            <c:if test="${not empty projectDeadlineViolation}">
                <span class="violation">${projectDeadlineViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Finished: </span>
            <select name="projectFinished" size="1">
                <option value="${projectFinished}" selected>${projectFinished}</option>
                <c:if test="${projectFinished eq true}">
                    <option value="false">false</option>
                </c:if>
                <c:if test="${projectFinished eq false}">
                    <option value="true">true</option>
                </c:if>
            </select>
        </div>
        <input type="hidden" value="${projectId}" name="projectId"/>
        <button class="save_changes" type="submit"/>Save changes</button>
        <c:if test="${not empty changesSavedSuccessfully}">
            <span class="created-message">${changesSavedSuccessfully}</span>
        </c:if>
    </form>
</main>