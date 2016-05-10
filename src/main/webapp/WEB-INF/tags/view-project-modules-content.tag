<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="managerContentProjects" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="add_module" value="/do/pass-projectId"/>

<link rel="stylesheet" href="${css_path}/view-project-modules.css">

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
        </ul>
    </div>
    <div class="field-wrap">
        <a id="add-module" href="
            <c:url value="${add_module}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="passProjectId" value="add-module"></c:param>
            </c:url>
        ">Add module</a>
    </div>

    <%--modules table--%>
    <div id="table-modules">
        <c:if test="${not empty modules}">
            <table>
                <tr>
                    <th>№</th>
                    <th>ID</th>
                    <th>Module name</th>
                    <th>Start date</th>
                    <th>Deadline</th>
                    <th>Finished</th>
                    <th>View module</th>
                </tr>
                <c:forEach items="${modules}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.startDate}</td>
                        <td>${item.deadline}</td>
                        <td>${item.finished}</td>
                        <td>
                            <a href="
                                <c:url value="${view_module}">
                                    <c:param name="projectId" value="${projectId}"></c:param>
                                    <c:param name="moduleId" value="${item.id}"></c:param>
                                </c:url>
                            ">View</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty modules}">
            <span>No modules found</span>
        </c:if>
    </div>
</main>