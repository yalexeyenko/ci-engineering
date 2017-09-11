<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="edit_main_module_info" value="/do/edit-main-module-info"/>
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
                <a id="edit-main-module-info" href="
                    <c:url value="${edit_main_module_info}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Редактирование раздела</a>
            </li>
        </ul>
    </div>

    <%--Edit main module info form--%>
    <div id="edit-module-info">
        <form action="${edit_main_module_info}" method="post" name="edit-main-module-info">
            <h3>Редактирование информации о разделе: </h3>
            <div class="wrap">
                <span>Название раздела: </span>
                <input type="text" value="${moduleName}" name="moduleName" placeholder="введите название раздела*" required/>
                <c:if test="${not empty moduleNameViolation}">
                    <span class="violation">${moduleNameViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span>Дата окончания: </span>
                <input type="date" value="${moduleDeadline}" name="moduleDeadline" placeholder="введите дату окончания*"
                       required/>
                <c:if test="${not empty moduleDeadlineViolation}">
                    <span class="violation">${moduleDeadlineViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span>Статус: </span>
                <select name="moduleFinished" size="1">
                    <option value="${moduleFinished}" selected>
                        <c:if test="${moduleFinished eq false}">не завершен</c:if>
                        <c:if test="${moduleFinished eq true}">завершен</c:if>
                    </option>
                    <c:if test="${moduleFinished eq true}">
                        <option value="false">не завершен</option>
                    </c:if>
                    <c:if test="${moduleFinished eq false}">
                        <option value="true">завершен</option>
                    </c:if>
                </select>
            </div>
            <input type="hidden" value="${projectId}" name="projectId"/>
            <input type="hidden" value="${moduleId}" name="moduleId"/>
            <button class="save_changes" type="submit"/>Сохранить</button>
            <c:if test="${not empty changesSavedSuccessfully}">
                <span id="edited-message">${changesSavedSuccessfully}</span>
            </c:if>
        </form>
    </div>
</main>