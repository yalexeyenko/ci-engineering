<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/edit-main-project-info-post"/>
<c:url var="add_file" value="/do/pass-projectId"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="edit_main_project_info" value="/do/pass-projectId"/>
<c:url var="upload_file" value="/upload"/>

<link rel="stylesheet" href="${css_path}/edit-project-content.css">

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
                <a id="add-file" href="
                    <c:url value="${add_file}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="add-file"></c:param>
                    </c:url>
                ">Add file</a>
            </li>
        </ul>
    </div>
    <form action="${upload_file}/upload" method="post" enctype="multipart/form-data">
        <div class="field-wrap">
            <span>Description: </span>
            <input type="text" name="description" value="${description}" placeholder="Type file description*" required/>
            <c:if test="${not empty descriptionViolation}">
                <span class="created-message">${descriptionViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Choose file: </span>
            <input type="file" name="file" required/>
            <c:if test="${not empty filePartSizeViolation}">
                <span class="created-message">${filePartSizeViolation}</span>
            </c:if>
        </div>
        <input type="hidden" name="projectId" value="${projectId}" />
        <input type="hidden" name="staffId" value="${user.id}" />
        <button class="create-file-button" type="submit"/>Create file</button>
        <c:if test="${not empty uploadFileSuccess}">
            <span class="created-message">${uploadFileSuccess}</span>
        </c:if>
    </form>
</main>