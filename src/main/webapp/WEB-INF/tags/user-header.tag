<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="userHeader" pageEncoding="UTF-8" %>
<c:url var="sign_out_action" value="/do/signOut"/>
<c:url var="view_profile_action" value="/do/view-profile"/>
<c:url var="edit_profile_action" value="/do/edit-profile"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<header class="header">
    <c:if test="${user.role eq 'MANAGER'}"><img id="avatar" src="${images_path}/manager.png"></c:if>
    <c:if test="${user.role eq 'ENGINEER'}"><img id="avatar" src="${images_path}/engineer.png"></c:if>
    <c:if test="${user.role eq 'SENIOR'}"><img id="avatar" src="${images_path}/senior.png"></c:if>
    <c:if test="${user.role eq 'ADMIN'}"><img id="avatar" src="${images_path}/admin.png"></c:if>
    <c:if test="${user.role eq 'REGISTERED'}"><img id="avatar" src="${images_path}/reg.png"></c:if>
    <div id="header-info">
        <h3>${user.firstName} ${user.lastName}</h3>
        <span>Должность:
            <c:if test="${not empty user.role}">${user.roleName}</c:if>
            <c:if test="${empty user.role}">not specified</c:if>
        </span>
        <span>Образование (ученая степень):
            <c:if test="${not empty user.degree}">${user.degree}</c:if>
            <c:if test="${empty user.degree}">не указано</c:if>
        </span>
        <%--<span>Email: ${user.email}</span>--%>
        <span><a id="view-profile" href="${view_profile_action}">Профиль</a></span>
    </div>
    <a id="sign-out" href="${sign_out_action}">Выйти</a>
</header>
<!-- .header-->