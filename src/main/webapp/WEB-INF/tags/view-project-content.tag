<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="viewProjectContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/pass-projectId"/>
<c:url var="create_client" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="specify_senior" value="/do/pass-projectId"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_files" value="/do/view-project-files"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Просмотр проекта</a>
            </li>
        </ul>
    </div>
    <div id="view-project">
        <div class="wrap">
            <span class="sp">Название проекта: </span>
            <span class="val">${projectName}</span>
        </div>
        <div class="wrap">
            <span class="sp">Дата начала: </span>
            <span class="val">${projectStartDate}</span>
        </div>
        <div class="wrap">
            <span class="sp">Дата окончания: </span>
            <span class="val">${projectDeadline}</span>
        </div>
        <div class="wrap">
            <span class="sp">Статус: </span>
            <span class="val">
                <c:if test="${projectFinished eq false}">не завершен</c:if>
                <c:if test="${projectFinished eq true}">завершен</c:if>
            </span>
        </div>
        <div class="wrap">
            <span class="sp">Заказчик: </span>
            <span class="val">
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
                </c:if>
                <c:if test="${empty project.client}">
                    <a href="
                        <c:url value="${create_client}">
                            <c:param name="projectId" value="${project.id}"></c:param>
                            <c:param name="passProjectId" value="create-client"></c:param>
                        </c:url>
                    ">создать заказчика</a>
                </c:if>
            </span>
        </div>
        <div class="wrap">
            <span class="sp">Главный инженер: </span>
            <span class="val">
                <c:if test="${not empty project.senior}">
                    <a href="
                        <c:url value="${specify_senior}">
                            <c:param name="projectId" value="${project.id}"></c:param>
                            <c:param name="passProjectId" value="specify-senior"></c:param>
                        </c:url>
                    ">${project.senior.firstName} ${project.senior.lastName}</a>
                </c:if>
                <c:if test="${empty project.senior}">
                    <a href="
                        <c:url value="${specify_senior}">
                            <c:param name="projectId" value="${project.id}"></c:param>
                            <c:param name="passProjectId" value="specify-senior"></c:param>
                        </c:url>
                    ">указать главного инженера</a>
                </c:if>
            </span>
        </div>
        <%--View files--%>
        <div class="wrap">
            <a id="view-files" href="
            <c:url value="${view_files}">
                <c:param name="projectId" value="${projectId}"></c:param>
            </c:url>
        ">Документы проекта</a>
        </div>
        <%--View modules--%>
        <div class="wrap">
            <a id="view-modules" href="
            <c:url value="${view_modules}">
                <c:param name="projectId" value="${projectId}"></c:param>
            </c:url>
        ">Разделы проекта</a>
        </div>
        <div class="wrap">
            <a id="edit-main-project-info" href="
            <c:url value="${edit_main_project_info}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="passProjectId" value="edit-main-project-info"></c:param>
            </c:url>
        ">Редактировать проект</a>
        </div>
    </div>
</main>