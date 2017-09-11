<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/view-profile.css">

<html>
<t:head/>
<body>
<div class="wrapper">
    <t:user-header/>
    <t:view-profile-content/>
</div><!-- .wrapper -->
<%--<t:footer/>--%>
</body>
</html>
