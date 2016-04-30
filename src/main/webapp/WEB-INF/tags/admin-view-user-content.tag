<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="change_role" value="/do/change-role"/>
<c:url var="delete_user" value="/do/delete-user"/>

<link rel="stylesheet" href="${css_path}/edit-profile-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <h4>${adUser.firstName} ${adUser.lastName}</h4>
    <h4>Email: ${adUser.email}</h4>

    <form action="${change_role}" method="post" name="change-user-role" onSubmit="return validate_form(this);">
        <select name="role" size="1">
            <c:forEach items="${adUser.roleValues}" var="role">
                <c:if test="${role eq adUser.role}">
                    <option value="${role}" selected>${role}</option>
                </c:if>
                <c:if test="${role ne adUser.role}">
                    <option value="${role}">${role}</option>
                </c:if>
            </c:forEach>
        </select>
        <input type="hidden" name="userId" value="${adUser.id}"/>
        <button class="change-user-role" type="submit"/>Change role</button>
        <c:if test="${not empty changeRoleSuccess}">
            <span class="success-change-role">${changeRoleSuccess}</span>
        </c:if>
    </form>

    <h4>Degree:
        <c:if test="${not empty adUser.degree}">${adUser.degree}</c:if>
        <c:if test="${empty adUser.degree}">not specified</c:if>
    </h4>

    <form action="${delete_user}" method="post" name="delete-user" onSubmit="return validate_form(this);">
        <input type="hidden" name="userId" value="${adUser.id}"/>
        <button class="delete-user-button" type="submit"/>Delete user</button>
    </form>
</main>