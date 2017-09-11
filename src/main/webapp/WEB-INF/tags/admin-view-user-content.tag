<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="view_user" value="/do/pass-userId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="change_role" value="/do/change-role"/>
<c:url var="delete_user" value="/do/delete-user"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>


<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_user}">
                        <c:param name="userId" value="${adUser.id}"></c:param>
                        <c:param name="passUserId" value="admin-view-user"></c:param>
                    </c:url>
                ">Просмотр пользователя</a>
            </li>
        </ul>
    </div>
    <div id="view-user">
        <h3>Просмотр пользователя:</h3>
        <div class="wrap">
            <span class="sp">Имя: </span>
            <span class="val">${adUser.firstName}</span>
        </div>
        <div class="wrap">
            <span class="sp">Фамилия: </span>
            <span class="val">${adUser.lastName}</span>
        </div>
        <div class="wrap">
            <span class="sp">Email: </span>
            <span class="val">${adUser.email}</span>
        </div>
        <div class="wrap">
            <span class="sp">Образование (ученая степень): </span>
            <span class="val">
                <c:if test="${not empty adUser.degree}">${adUser.degree}</c:if>
                <c:if test="${empty adUser.degree}">не указано</c:if>
            </span>
        </div>
        <div class="wrap">
            <form action="${change_role}" method="post" name="change-user-role" onSubmit="return validate_form(this);">
                <span class="sp">Должность (статус): </span>
                <select name="role" size="1">
                    <c:forEach items="${adUser.roleValues}" var="role">
                        <c:if test="${role eq adUser.role}">
                            <option value="${role}" selected>${role.toStr}</option>
                        </c:if>
                        <c:if test="${role ne adUser.role}">
                            <option value="${role}">${role.toStr}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <input type="hidden" name="userId" value="${adUser.id}"/>
                <button class="change-user-role" type="submit"/>
                Изменить</button>
                <c:if test="${not empty changeRoleSuccess}">
                    <span class="success-change-role">${changeRoleSuccess}</span>
                </c:if>
            </form>
        </div>
    </div>

</main>