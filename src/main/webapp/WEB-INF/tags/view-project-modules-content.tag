<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="managerContentProjects" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="add_module" value="/do/pass-projectId"/>
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
        </ul>
    </div>
    <div class="field-wrap">
        <a id="add-module" href="
            <c:url value="${add_module}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="passProjectId" value="add-module"></c:param>
            </c:url>
        ">Добавить раздел</a>
    </div>

    <%--modules table--%>
    <div id="table-modules">
        <c:if test="${not empty modules}">
            <table>
                <tr>
                    <th>№</th>
                    <th>Название раздела</th>
                    <th>Дата начала</th>
                    <th>Дата окончания</th>
                    <th>Статус</th>
                    <th>Просмотр раздела</th>
                </tr>
                <c:forEach items="${modules}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td class="td-align-left">${item.name}</td>
                        <td>${item.startDate}</td>
                        <td>${item.deadline}</td>
                        <td>
                            <c:if test="${item.finished eq false}">не завершен</c:if>
                            <c:if test="${item.finished eq true}">завершен</c:if>
                        </td>
                        <td>
                            <a href="
                                <c:url value="${view_module}">
                                    <c:param name="projectId" value="${projectId}"></c:param>
                                    <c:param name="moduleId" value="${item.id}"></c:param>
                                </c:url>
                            "><img id="view-icon" src="${images_path}/view.png"></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty modules}">
            <span>Разделы не найдены</span>
        </c:if>
    </div>
</main>