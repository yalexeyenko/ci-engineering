<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="createProjectAction" value="/do/createProject"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/create-project-content.css">

<main class="content">
    <form action="${createProjectAction}" method="post" name="create_project" onSubmit="return validate_form(this);">
        <h3>Create project</h3>
        <div class="field-wrap">
            <span>Project name: </span>
            <input type="text" name="projectName" placeholder="Enter project name*" required/>
        </div>
        <div class="field-wrap">
            <span>Choose deadline: </span>
            <input type="date" name="projectDeadline">
        </div>
        <button class="create-project-button" type="submit"/>Create</button>
    </form>

</main>