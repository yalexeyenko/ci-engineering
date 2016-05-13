<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="adminContentUsers" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="view_module_engineers" value="/do/view-module-engineers"/>

<c:url var="add_module_engineer" value="/do/add-module-engineer"/>
<c:url var="delete_user_from_module" value="/do/delete-user-from-module"/>

<link rel="stylesheet" href="${css_path}/admin-content-users.css">

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
                <a id="view-module-engineers" href="
                    <c:url value="${view_module_engineers}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Engineers</a>
            </li>
        </ul>
    </div>


    <%--Add engineer--%>
    <form action="${add_module_engineer}" method="post">
        <span>Add engineer to module: </span>
        <select name="moduleEngineer" size="1">
            <c:forEach items="${engineers}" var="engineer">
                    <option value="${engineer.id}">${engineer.firstName} ${engineer.lastName} ${engineer.degree}</option>
            </c:forEach>
        </select>
        <input type="hidden" name="projectId" value="${projectId}"/>
        <input type="hidden" name="moduleId" value="${moduleId}"/>
        <button class="add-module-engineer-button" type="submit"/>Add engineer</button>
        <c:if test="${not empty addEngineerSuccess}">
            <span class="violation">${addEngineerSuccess}</span>
        </c:if>
        <c:if test="${not empty addEngineerFail}">
            <span class="violation">${addEngineerFail}</span>
        </c:if>
    </form>
    <c:if test="${not empty removalSuccess}">
        <span class="violation">${removalSuccess}</span>
    </c:if>
    <div id="table-users">
        <c:if test="${not empty moduleEngineers}">
            <table>
                <tr>
                    <th>№</th>
                    <th>ID</th>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Degree</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${moduleEngineers}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${item.id}</td>
                        <td>${item.firstName}</td>
                        <td>${item.lastName}</td>
                        <td>${item.degree}</td>
                        <td>
                            <a href="
                            <c:url value="${delete_user_from_module}">
                                <c:param name="engineerId" value="${item.id}"></c:param>
                                <c:param name="projectId" value="${projectId}"></c:param>
                                <c:param name="moduleId" value="${moduleId}"></c:param>
                            </c:url>
                        ">delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty moduleEngineers}">
            <span>No engineers found</span>
        </c:if>

    </div>
</main>