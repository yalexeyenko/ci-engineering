<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info_post" value="/do/edit-main-project-info-post"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/edit-project-content.css">

<main class="content">
    <form action="${edit_main_project_info_post}" method="post" name="edit-main-project-info"
          onSubmit="return validate_form(this);">
        <h3>Edit main project info: </h3>
        <div class="field-wrap">
            <span>Project name: </span>
            <input type="text" value="${project.name}" name="projectName" placeholder="Enter project name*" required/>
        </div>
        <div class="field-wrap">
            <span>Project start date: </span>
            <input type="date" value="${project.startDate}" name="projectStartDate"
                   placeholder="Enter project start date*" required/>
        </div>
        <div class="field-wrap">
            <span>Project deadline: </span>
            <input type="date" value="${project.deadline}" name="projectDeadline" placeholder="Enter project deadline*"
                   required/>
        </div>
        <div class="field-wrap">
            <span>Finished: </span>
            <select name="projectFinished" size="1">
                <option value="${project.finished}" selected>${project.finished}</option>
                <c:if test="${project.finished eq true}">
                    <option value="false">false</option>
                </c:if>
                <c:if test="${project.finished eq false}">
                    <option value="true">true</option>
                </c:if>
            </select>
        </div>
        <input type="hidden" value="${project.id}" name="projectId"/>
        <button class="save_changes" type="submit"/>Save changes</button>
    </form>
</main>