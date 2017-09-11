<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="edit_profile" value="/do/pass-userId"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
        </ul>
    </div>
    <div id="view-profile">
        <span>Имя: ${user.firstName}</span>
        <span>Фамилия: ${user.firstName}</span>
        <span>Должность:
            <c:if test="${not empty user.role}">${user.roleName}</c:if>
            <c:if test="${empty user.role}">not specified</c:if>
        </span>
        <span>Образование (ученая степень):
            <c:if test="${not empty user.degree}">${user.degree}</c:if>
            <c:if test="${empty user.degree}">не указано</c:if>
        </span>
        <span>Email: ${user.email}</span>
    </div>

    <a id="edit-profile" href="
        <c:url value="${edit_profile}">
            <c:param name="userId" value="${user.id}"></c:param>
            <c:param name="passUserId" value="edit-user"></c:param>
        </c:url>
    ">Редактировать профиль</a>
</main>