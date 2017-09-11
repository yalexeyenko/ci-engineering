<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="edit_client" value="/do/pass-projectId"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="projectName" value="${projectName}"></c:param>
                        <c:param name="projectDeadline" value="${projectDeadline}"></c:param>
                        <c:param name="projectFinished" value="${projectFinished}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Просмотр проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_client}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-client"></c:param>
                    </c:url>
                ">Просмотр заказчика</a>
            </li>
        </ul>
    </div>
    <div id="view-client">
        <h3>Информация о заказчике</h3>
        <div class="wrap">
            <span class="sp">Заказчик: </span>
            <span class="val">
                <c:if test="${project.client.clientType eq 'LEGAL'}">${project.client.firstName}</c:if>
                <c:if test="${project.client.clientType eq 'INDIVIDUAL'}">${project.client.firstName} ${project.client.lastName}</c:if>
            </span>
        </div>
        <div class="wrap">
            <span class="sp">Тип заказчика: </span>
            <span class="val">${project.client.clientType}</span>
        </div>
        <div class="wrap">
            <span class="sp">Email: </span>
            <span class="val">${project.client.email}</span>
        </div>
        <div class="wrap">
            <span class="sp">Страна: </span>
            <span class="val">${countriesMap[project.client.country]}</span>
        </div>
        <div class="wrap">
            <span class="sp">Город: </span>
            <span class="val">${project.client.city}</span>
        </div>
        <div class="wrap">
            <span class="sp">Адрес: </span>
            <span class="val">${project.client.address}</span>
        </div>
        <div class="wrap">
            <span class="sp">Телефон: </span>
            <span class="val">${project.client.telephone}</span>
        </div>
        <div class="wrap">
            <span class="sp">Номер банковского счета: </span>
            <span class="val">${project.client.bankAccountNumber}</span>
        </div>
        <div class="wrap">
            <span class="sp">EIN/SSN: </span>
            <span class="val">${project.client.einSsn}</span>
        </div>
        <div class="wrap">
            <a id="edit-client" href="
                <c:url value="${edit_client}">
                    <c:param name="projectId" value="${project.id}"></c:param>
                    <c:param name="passProjectId" value="edit-client"></c:param>
                </c:url>
            ">Редактировать заказчика</a>
        </div>

    </div>
</main>