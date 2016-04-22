<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="sign_out_action" value="/do/signOut"/>
<c:url var="edit_profile_action" value="/do/edit-profile"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/user-header.css">

<header class="header">
    <h3>${user.firstName} ${user.lastName}</h3>
    <h4>Role:
        <c:if test="${not empty user.role}">
            ${user.role}
        </c:if>
        <c:if test="${empty user.role}">
            not specified
        </c:if>
    </h4>
    <h4>Degree:
        <c:if test="${not empty user.degree}">
            ${user.degree}
        </c:if>
        <c:if test="${empty user.degree}">
            not specified
        </c:if>
    </h4>
    <h4>Email: ${user.email}</h4>
    <a id="edit-profile" href="${edit_profile_action}">Edit profile</a>
    <a id="sign-out" href="${sign_out_action}">Sign out</a>
</header>
<!-- .header-->