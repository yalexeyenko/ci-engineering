<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="viewProjectContent" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="edit_main_module_info" value="/do/edit-main-module-info"/>
<c:url var="create_client" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="specify_senior" value="/do/pass-projectId"/>
<c:url var="view_module_files" value="/do/view-module-files"/>

<c:url var="view_module_engineers" value="/do/view-module-engineers"/>

<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>


<link rel="stylesheet" href="${css_path}/view-module-content.css">

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
        </ul>
    </div>
    <h3>Module name: ${moduleName}</h3>
    <h4>Start date: ${moduleStartDate}</h4>
    <h4>Deadline: ${moduleDeadline}</h4>
    <h4>Finished: ${moduleFinished}</h4>

    <%--Edit module--%>
    <div class="field-wrap">
        <a id="edit-main-module-info" href="
            <c:url value="${edit_main_module_info}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Edit module</a>
    </div>

    <%--View files--%>
    <div class="field-wrap">
        <a id="view-module-files" href="
            <c:url value="${view_module_files}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Files</a>
    </div>

    <%--View module engineers--%>
    <div class="field-wrap">
        <a id="view-module-engineers" href="
            <c:url value="${view_module_engineers}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Engineers</a>
    </div>

</main>