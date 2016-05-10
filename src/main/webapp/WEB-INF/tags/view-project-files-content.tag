<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="managerContentProjects" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_files" value="/do/view-project-files"/>
<c:url var="add_file" value="/do/pass-projectId"/>
<c:url var="download_file" value="/download"/>
<c:url var="delete_file" value="/do/delete-file"/>

<link rel="stylesheet" href="${css_path}/manager-content-projects.css">

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
                <a id="view-files" href="
            <c:url value="${view_files}">
                <c:param name="projectId" value="${projectId}"></c:param>
            </c:url>
    ">Files</a>
            </li>
        </ul>
    </div>
    <div class="field-wrap">
        <a id="add-file" href="
            <c:url value="${add_file}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="passProjectId" value="add-file"></c:param>
            </c:url>
    ">Add file</a>
    </div>
    <div id="table-fileDocs">
        <c:if test="${not empty fileDocs}">
            <table>
                <tr>
                    <th>№</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Last modified</th>
                    <th>Status</th>
                    <th>download</th>
                    <th>delete</th>
                </tr>
                <c:forEach items="${fileDocs}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.description}</td>
                        <td>${item.lastModified}</td>
                        <td>${item.status}</td>
                        <td>
                            <a href="
                                <c:url value="${download_file}/download">
                                    <c:param name="fileDocId" value="${item.id}"></c:param>
                                </c:url>
                            " target="_blank">download</a>
                        </td>
                        <td>
                            <a href="
                                <c:url value="${delete_file}">
                                    <c:param name="fileDocId" value="${item.id}"></c:param>
                                    <c:param name="projectId" value="${projectId}"></c:param>
                                </c:url>
                            ">delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty fileDocs}">
            <span>No files found</span>
        </c:if>
    </div>
</main>