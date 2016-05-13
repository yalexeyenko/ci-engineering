<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="add_file" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/edit-main-project-info-post"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="view_module_files" value="/do/view-module-files"/>
<c:url var="add_module_file" value="/do/add-module-file"/>
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
                <a id="view-modules" href="
                    <c:url value="${view_modules}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                    </c:url>
                ">Modules</a>
            </li>
            <li>
                <a href="
                    <c:url value="${view_module}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">View module</a>
            </li>
            <li>
                <a id="view-module-files" href="
                    <c:url value="${view_module_files}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Files</a>
            </li>
            <li>
                <a id="add-file" href="
                    <c:url value="${add_module_file}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
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
        <input type="hidden" name="moduleId" value="${moduleId}" />
        <input type="hidden" name="staffId" value="${user.id}" />
        <input type="hidden" name="sender" value="module-sender" />
        <button class="create-file-button" type="submit"/>Create file</button>
        <c:if test="${not empty uploadFileSuccess}">
            <span class="created-message">${uploadFileSuccess}</span>
        </c:if>
    </form>
</main>