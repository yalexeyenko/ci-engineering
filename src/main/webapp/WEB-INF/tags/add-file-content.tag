<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="add_file" pageEncoding="UTF-8" %>
<c:url var="edit_main_project_info" value="/do/edit-main-project-info-post"/>
<c:url var="add_file" value="/do/pass-projectId"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="edit_main_project_info" value="/do/pass-projectId"/>
<c:url var="upload_file" value="/upload"/>
<c:url var="view_files" value="/do/view-project-files"/>
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
                <a id="view-files" href="
            <c:url value="${view_files}">
                <c:param name="projectId" value="${projectId}"></c:param>
            </c:url>
    ">Документы проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a id="add-file" href="
                    <c:url value="${add_file}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="add-file"></c:param>
                    </c:url>
                ">Добавление документа</a>
            </li>
        </ul>
    </div>
    <div id="add-file-cont">
        <form action="${upload_file}/upload" method="post" enctype="multipart/form-data">
            <h3>Добавление документа</h3>
            <div class="wrap">
                <span class="sp">Описание документа: </span>
                <input type="text" name="description" value="${description}" placeholder="введите описание файла*" required/>
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
            <input type="hidden" name="projectId" value="${projectId}" />
            <input type="hidden" name="staffId" value="${user.id}" />
            <button id="create-file-button" type="submit"/>Добавить</button>
            <c:if test="${not empty uploadFileSuccess}">
                <span class="created-message">${uploadFileSuccess}</span>
            </c:if>
        </form>
    </div>

</main>