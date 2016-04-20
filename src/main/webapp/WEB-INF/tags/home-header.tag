<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="sign_out_action" value="/do/signOut"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/home-header.css">

<header class="header">
    <h3>${user.firstName} ${user.lastName}</h3>
    <a href="${sign_out_action}">Sign out</a>
</header><!-- .header-->