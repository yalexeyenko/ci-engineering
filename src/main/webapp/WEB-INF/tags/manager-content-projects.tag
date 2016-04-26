<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="managerContentProjects" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="create_project_action" value="/do/create-project"/>
<c:url var="pass_projectId" value="/do/pass-projectId"/>

<link rel="stylesheet" href="${css_path}/manager-content-projects.css">

<main class="content">
    manager content projects page
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="#" id="current">Projects</a></li>
            <li><a href="#">Item 2</a></li>
            <li><a href="#">Item 3</a></li>
            <li><a href="#">Item 4</a></li>
        </ul>
    </div>
    <a id="create-project" href="${create_project_action}">Create project</a>
    <div id="table-users">
        <table>
            <tr>
                <th>№</th>
                <th>ID</th>
                <th>Project name</th>
                <th>Start date</th>
                <th>Deadline</th>
                <th>Finished</th>
                <th>Client</th>
                <th>Senior Engineer</th>
                <th>Edit project</th>
            </tr>
            <c:forEach items="${projects}" var="item" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.startDate}</td>
                    <td>${item.deadline}</td>
                    <td>${item.finished}</td>
                    <td>
                        <c:if test="${item.client.clientType eq 'LEGAL'}">${item.client.firstName}</c:if>
                        <c:if test="${item.client.clientType eq 'INDIVIDUAL'}">${item.client.firstName} ${item.client.lastName}</c:if>
                    </td>
                    <td>${item.senior.firstName} ${item.senior.lastName}</td>
                    <td>
                        <a href="
                        <c:url value="${pass_projectId}">
                            <c:param name="projectId" value="${item.id}"></c:param>
                            <c:param name="passProjectId" value="passProjectIdToViewProject"></c:param>
                        </c:url>
                        ">View</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
</main>