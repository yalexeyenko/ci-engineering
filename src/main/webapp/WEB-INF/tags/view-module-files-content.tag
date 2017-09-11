<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="managerContentProjects" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="view_module_files" value="/do/view-module-files"/>
<c:url var="add_module_file" value="/do/add-module-file"/>
<c:url var="download_file" value="/download"/>
<c:url var="delete_file" value="/do/delete-file"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li><a href="${main_page}">Главная</a></li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Просмотр проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a id="view-modules" href="
                    <c:url value="${view_modules}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                    </c:url>
                ">Разделы проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a href="
                    <c:url value="${view_module}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Просмотр раздела</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a id="view-module-files" href="
                    <c:url value="${view_module_files}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Документы раздела</a>
            </li>
        </ul>
    </div>

    <div class="field-wrap">
        <a id="add-file" href="
            <c:url value="${add_module_file}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Добавить документ</a>
    </div>

    <%--Files table--%>
    <div id="table-fileDocs">
        <c:if test="${not empty fileDocs}">
            <table>
                <tr>
                    <th>№</th>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Дата изменений</th>
                    <th>Статус</th>
                    <th>Скачать</th>
                    <th>Удалить</th>
                </tr>
                <c:forEach items="${fileDocs}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td class="td-align-left">${item.name}</td>
                        <td class="td-align-left">${item.description}</td>
                        <td>${item.lastModified}</td>
                        <td>${item.statusName}</td>
                        <td>
                            <a href="
                                <c:url value="${download_file}/download">
                                    <c:param name="fileDocId" value="${item.id}"></c:param>
                                    <c:param name="projectId" value="${projectId}"></c:param>
                                    <c:param name="moduleId" value="${moduleId}"></c:param>
                                    <c:param name="sender" value="module-sender"></c:param>
                                </c:url>
                            " target="_blank"><img id="download-icon" src="${images_path}/download-icon.png"></a>
                        </td>
                        <td>
                            <a href="
                                <c:url value="${delete_file}">
                                    <c:param name="fileDocId" value="${item.id}"></c:param>
                                    <c:param name="projectId" value="${projectId}"></c:param>
                                    <c:param name="moduleId" value="${moduleId}"></c:param>
                                    <c:param name="sender" value="module-sender"></c:param>
                                </c:url>
                            "><img id="delete-icon" src="${images_path}/delete-icon.png"</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty fileDocs}">
            <span>Документы не найдены</span>
        </c:if>
    </div>
</main>