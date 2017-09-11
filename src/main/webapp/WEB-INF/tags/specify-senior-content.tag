<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="specify_senior_action" value="/do/specifySeniorAction"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="specify_senior" value="/do/pass-projectId"/>
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
                    <c:url value="${specify_senior}">
                        <c:param name="projectId" value="${project.id}"></c:param>
                        <c:param name="passProjectId" value="specify-senior"></c:param>
                    </c:url>
                ">Главный инженер</a>
            </li>
        </ul>
    </div>
    <div id="specify-senior">
        <form action="${specify_senior_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
            <h3>Выбор главного инженера: </h3>
            <select name="projectSenior" size="1">
                <c:if test="${empty seniorId}">
                    <option disabled selected>выберете главного инженера...</option>
                </c:if>
                <c:if test="${not empty seniorId}">
                    <option value="${seniorId}">${seniorFirstName} ${seniorLastName}</option>
                </c:if>
                <c:forEach items="${seniors}" var="senior">
                    <c:if test="${seniorId ne senior.id}">
                        <option value="${senior.id}">${senior.firstName} ${senior.lastName}</option>
                    </c:if>
                </c:forEach>
            </select>
            <input type="hidden" name="projectId" value="${project.id}"/>
            <button class="specify-senior-button" type="submit"/>
            Сохранить</button>
            <c:if test="${not empty specifySeniorSuccess}">
                <span id="senior-specified">${specifySeniorSuccess}</span>
            </c:if>
        </form>
    </div>

</main>