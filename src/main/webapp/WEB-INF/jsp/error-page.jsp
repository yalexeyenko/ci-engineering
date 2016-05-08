<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<c:url var="go_to_welcome" value="/do/welcome"/>
<c:url var="go_home" value="/do/main-page"/>

<html>
<t:head/>
<body>
<div class="wrapper">
    <div class="error-page">
        <h2>Status code: ${statusCode}</h2>
        <h3>Requested URI: ${requestUri}</h3>
        <c:if test="${empty user}">
            <a id="go-to-welcome" href="${go_to_welcome}">Go to welcome page</a>
        </c:if>
        <c:if test="${not empty user}">
            <a id="go-home" href="${go_home}">Go home</a>
        </c:if>
    </div>
</div><!-- .wrapper -->
<t:footer/>
</body>
</html>