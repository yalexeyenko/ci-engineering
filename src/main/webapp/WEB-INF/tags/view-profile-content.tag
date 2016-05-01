<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="edit_profile" value="/do/pass-userId"/>

<link rel="stylesheet" href="${css_path}/edit-profile-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <h4>${user.firstName} ${user.lastName}</h4>
    <h4>Role:
        <c:if test="${not empty user.role}">${user.role}</c:if>
        <c:if test="${empty user.role}">not specified</c:if>
    </h4>
    <h4>Degree:
        <c:if test="${not empty user.degree}">${user.degree}</c:if>
        <c:if test="${empty user.degree}">not specified</c:if>
    </h4>
    <h4>Email: ${user.email}</h4>
    <a id="edit-profile" href="
        <c:url value="${edit_profile}">
            <c:param name="userId" value="${user.id}"></c:param>
            <c:param name="passUserId" value="edit-user"></c:param>
        </c:url>
    ">Edit profile</a>
</main>