<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="viewProjectContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/pass-projectId"/>
<c:url var="create_client" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/edit-project-content.css">

<main class="content">
    <h3>Project name: ${project.name}</h3>
    <h4>Start date: ${project.startDate}</h4>
    <h4>Deadline: ${project.deadline}</h4>
    <h4>Finished: ${project.finished}</h4>
    <a id="edit-main-project-info" href="
            <c:url value="${edit_main_project_info}">
                <c:param name="projectId" value="${project.id}"></c:param>
                <c:param name="passProjectId" value="edit-main-project-info"></c:param>
            </c:url>
    ">Edit main project info</a>
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

            <%--<c:if test="${project.client.clientType eq 'LEGAL'}">${project.client.firstName}</c:if>--%>
            <%--<c:if test="${project.client.clientType eq 'INDIVIDUAL'}">${project.client.firstName} ${project.client.lastName}</c:if>--%>
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
            ${project.senior.firstName} ${project.senior.lastName}
        </c:if>
        <c:if test="${empty project.senior}">
            not specified
        </c:if>
    </h4>
    <h4>Modules:
        <c:if test="${empty project.modules}">
            no modules
        </c:if>
    </h4>
</main>