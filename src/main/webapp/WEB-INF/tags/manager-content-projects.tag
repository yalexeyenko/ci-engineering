<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="managerContentProjects" pageEncoding="UTF-8" %>
<c:url var="create_project_action" value="/do/create-project"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>


<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
        </ul>
    </div>
    <div class="field-wrap">
        <a id="create-project" href="${create_project_action}">Создать проект</a>
    </div>

    <%--projects table--%>
    <div id="table-projects">
        <c:if test="${not empty projects}">
            <table>
                <tr>
                    <th>№</th>
                    <th>Название проекта</th>
                    <th>Дата создания</th>
                    <th>Дата окончания</th>
                    <th>Статус</th>
                    <th>Заказчик</th>
                    <th>Главный инженер</th>
                    <th>Просмотр проекта</th>
                </tr>
                <c:forEach items="${projects}" var="item" varStatus="status">
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
                            <c:if test="${item.client.clientType eq 'LEGAL'}">${item.client.firstName}</c:if>
                            <c:if test="${item.client.clientType eq 'INDIVIDUAL'}">${item.client.firstName} ${item.client.lastName}</c:if>
                        </td>
                        <td>${item.senior.firstName} ${item.senior.lastName}</td>
                        <td>
                            <a id="view-ref" href="
                        <c:url value="${view_project}">
                            <c:param name="projectId" value="${item.id}"></c:param>
                            <c:param name="passProjectId" value="view-project"></c:param>
                        </c:url>
                        "><img id="view-icon" src="${images_path}/view.png"></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty projects}">
            <span>Проекты не найдены</span>
        </c:if>
    </div>
</main>