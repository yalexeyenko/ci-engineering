<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<html>
<t:head/>
<body>
<div class="wrapper">
    <div class="error-page">
        <h2>Status code: ${statusCode}</h2>
        <h3>Requested URI: ${requestUri}</h3>
    </div>
</div><!-- .wrapper -->
<t:footer/>
</body>
</html>