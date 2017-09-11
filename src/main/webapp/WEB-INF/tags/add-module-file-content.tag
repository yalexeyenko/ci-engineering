<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="add_file" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/edit-main-project-info-post"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="view_module_files" value="/do/view-module-files"/>
<c:url var="add_module_file" value="/do/add-module-file"/>
<c:url var="upload_file" value="/upload"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li><a href="${main_page}"> Главная</a></li>
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
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a id="add-file" href="
                    <c:url value="${add_module_file}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Добавить документ</a>
            </li>
        </ul>
    </div>

    <div id="add-file-cont">
        <form action="${upload_file}/upload" method="post" enctype="multipart/form-data">
            <h3>Добавление документа</h3>
            <div class="wrap">
                <span class="sp">Описание: </span>
                <input type="text" name="description" value="${description}" placeholder="введите описание файла*"
                       required/>
                <c:if test="${not empty descriptionViolation}">
                    <span class="not-created-message">${descriptionViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Выберите файл: </span>
                <input type="file" name="file" required/>
                <c:if test="${not empty filePartSizeViolation}">
                    <span class="not-created-message2">${filePartSizeViolation}</span>
                </c:if>
            </div>
            <input type="hidden" name="projectId" value="${projectId}"/>
            <input type="hidden" name="moduleId" value="${moduleId}"/>
            <input type="hidden" name="staffId" value="${user.id}"/>
            <input type="hidden" name="sender" value="module-sender"/>
            <button id="create-file-button" type="submit"/>Добавить</button>
            <c:if test="${not empty uploadFileSuccess}">
                <span class="created-message">${uploadFileSuccess}</span>
            </c:if>
        </form>
    </div>
</main>