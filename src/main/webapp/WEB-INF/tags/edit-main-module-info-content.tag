<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="edit_main_module_info" value="/do/edit-main-module-info"/>

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
                <a id="edit-main-module-info" href="
                    <c:url value="${edit_main_module_info}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Edit module</a>
            </li>
        </ul>
    </div>

    <%--Edit main module info form--%>
    <form action="${edit_main_module_info}" method="post" name="edit-main-module-info">
        <h3>Edit main module info: </h3>
        <div class="field-wrap">
            <span>Module name: </span>
            <input type="text" value="${moduleName}" name="moduleName" placeholder="Enter module name*" required/>
            <c:if test="${not empty moduleNameViolation}">
                <span class="violation">${moduleNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Module deadline: </span>
            <input type="date" value="${moduleDeadline}" name="moduleDeadline" placeholder="Enter module deadline*" required/>
            <c:if test="${not empty moduleDeadlineViolation}">
                <span class="violation">${moduleDeadlineViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Finished: </span>
            <select name="moduleFinished" size="1">
                <option value="${moduleFinished}" selected>${moduleFinished}</option>
                <c:if test="${moduleFinished eq true}">
                    <option value="false">false</option>
                </c:if>
                <c:if test="${moduleFinished eq false}">
                    <option value="true">true</option>
                </c:if>
            </select>
        </div>
        <input type="hidden" value="${projectId}" name="projectId"/>
        <input type="hidden" value="${moduleId}" name="moduleId"/>
        <button class="save_changes" type="submit"/>
        Save changes</button>
        <c:if test="${not empty changesSavedSuccessfully}">
            <span class="created-message">${changesSavedSuccessfully}</span>
        </c:if>
    </form>
</main>