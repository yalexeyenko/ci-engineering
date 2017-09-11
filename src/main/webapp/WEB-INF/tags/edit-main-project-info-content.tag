<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/edit-main-project-info-post"/>
<c:url var="edit_main_project_info_nav" value="/do/pass-projectId"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
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
                <a id="edit-main-project-info-nav" href="
                    <c:url value="${edit_main_project_info_nav}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="edit-main-project-info"></c:param>
                    </c:url>
                ">Редактирование проекта</a>
            </li>
        </ul>
    </div>
    <div id="edit-main-info">
        <form action="${edit_main_project_info}" method="post" name="edit-main-project-info">
            <h3>Редактирование информаци о проекте: </h3>
            <div class="wrap">
                <span>Название проекта: </span>
                <input type="text" value="${projectName}" name="projectName" placeholder="введите название проекта*" required/>
                <c:if test="${not empty projectNameViolation}">
                    <span class="violation">${projectNameViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span>Дата окончания: </span>
                <input type="date" value="${projectDeadline}" name="projectDeadline" placeholder="введите дату окончания*"
                       required/>
                <c:if test="${not empty projectDeadlineViolation}">
                    <span class="violation">${projectDeadlineViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span>Статус: </span>
                <select name="projectFinished" size="1">
                    <option value="${projectFinished}" selected>
                        <c:if test="${projectFinished eq false}">не завершен</c:if>
                        <c:if test="${projectFinished eq true}">завершен</c:if>
                    </option>
                    <c:if test="${projectFinished eq true}">
                        <option value="false">не завершен</option>
                    </c:if>
                    <c:if test="${projectFinished eq false}">
                        <option value="true">завершен</option>
                    </c:if>
                </select>
            </div>
            <input type="hidden" value="${projectId}" name="projectId"/>
            <button class="save_changes" type="submit"/>
            Сохранить</button>
            <c:if test="${not empty changesSavedSuccessfully}">
                <span id="edited-message">${changesSavedSuccessfully}</span>
            </c:if>
        </form>
    </div>
</main>