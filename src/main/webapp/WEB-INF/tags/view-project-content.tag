<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="viewProjectContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/pass-projectId"/>
<c:url var="create_client" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="specify_senior" value="/do/pass-projectId"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_files" value="/do/view-project-files"/>
<c:url var="view_modules" value="/do/view-project-modules"/>

<link rel="stylesheet" href="${css_path}/edit-project-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Project</a>
            </li>
        </ul>
    </div>
    <h3>Project name: ${projectName}</h3>
    <h4>Start date: ${projectStartDate}</h4>
    <h4>Deadline: ${projectDeadline}</h4>
    <h4>Finished: ${projectFinished}</h4>
    <div class="field-wrap">
        <a id="edit-main-project-info" href="
            <c:url value="${edit_main_project_info}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="passProjectId" value="edit-main-project-info"></c:param>
            </c:url>
        ">Edit project</a>
    </div>
    <h4>Client:
        <c:if test="${not empty project.client}">
            <a href="
                <c:url value="${view_client}">
                    <c:param name="projectId" value="${project.id}"></c:param>
                    <c:param name="passProjectId" value="view-client"></c:param>
                </c:url>
            ">
                <c:if test="${project.client.clientType eq 'LEGAL'}">${project.client.firstName}</c:if>
                <c:if test="${project.client.clientType eq 'INDIVIDUAL'}">${project.client.firstName} ${project.client.lastName}</c:if>
            </a>
        </c:if>
        <c:if test="${empty project.client}">
            <a href="
                <c:url value="${create_client}">
                    <c:param name="projectId" value="${project.id}"></c:param>
                    <c:param name="passProjectId" value="create-client"></c:param>
                </c:url>
                    ">create</a>
        </c:if>
    </h4>
    <h4>Senior engineer:
        <c:if test="${not empty project.senior}">
            <a href="
                <c:url value="${specify_senior}">
                    <c:param name="projectId" value="${project.id}"></c:param>
                    <c:param name="passProjectId" value="specify-senior"></c:param>
                </c:url>
            ">${project.senior.firstName} ${project.senior.lastName}</a>
        </c:if>
        <c:if test="${empty project.senior}">
            <a href="
                <c:url value="${specify_senior}">
                    <c:param name="projectId" value="${project.id}"></c:param>
                    <c:param name="passProjectId" value="specify-senior"></c:param>
                </c:url>
            ">specify senior engineer</a>
        </c:if>
    </h4>
    <%--View files--%>
    <div class="field-wrap">
        <a id="view-files" href="
            <c:url value="${view_files}">
                <c:param name="projectId" value="${projectId}"></c:param>
            </c:url>
        ">View files</a>
    </div>
    <%--View modules--%>
    <div class="field-wrap">
        <a id="view-modules" href="
            <c:url value="${view_modules}">
                <c:param name="projectId" value="${projectId}"></c:param>
            </c:url>
        ">View modules</a>
    </div>
</main>