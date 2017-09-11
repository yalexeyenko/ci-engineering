<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="viewProjectContent" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="edit_main_module_info" value="/do/edit-main-module-info"/>
<c:url var="create_client" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="specify_senior" value="/do/pass-projectId"/>
<c:url var="view_module_files" value="/do/view-module-files"/>

<c:url var="view_module_engineers" value="/do/view-module-engineers"/>

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
        </ul>
    </div>

    <div id="view-module">
        <h3>Просмотр раздела</h3>
        <div class="wrapre">
            <span class="sp">Название раздела: </span>
            <span class="val">${moduleName}</span>
        </div>
        <div class="wrap">
            <span class="sp">Дата начала: </span>
            <span class="val">${moduleStartDate}</span>
        </div>
        <div class="wrap">
            <span class="sp">Дата окончания: </span>
            <span class="val">${moduleDeadline}</span>
        </div>
        <div class="wrap">
            <span class="sp">Статус: </span>
            <span class="val">
                <c:if test="${moduleFinished eq false}">не завершен</c:if>
                <c:if test="${moduleFinished eq true}">завершен</c:if>
            </span>
        </div>
    </div>

    <%--Edit module--%>
    <div class="wrapref">
        <a id="edit-main-module-info" href="
            <c:url value="${edit_main_module_info}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Редактировать раздел</a>
    </div>

    <%--View files--%>
    <div class="wrapref">
        <a id="view-module-files" href="
            <c:url value="${view_module_files}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Документы раздела</a>
    </div>

    <%--View module engineers--%>
    <div class="wrapref">
        <a id="view-module-engineers" href="
            <c:url value="${view_module_engineers}">
                <c:param name="projectId" value="${projectId}"></c:param>
                <c:param name="moduleId" value="${moduleId}"></c:param>
            </c:url>
        ">Инженеры</a>
    </div>

</main>