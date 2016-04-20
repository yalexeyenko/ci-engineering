<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="sign_out_action" value="/do/signOut"/>
<c:url var="welcome_css" value="${pageContext.request.contextPath}/css/welcome.css"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>

    <link rel="stylesheet" href="${welcome_css}">

</head>

<body>
<div class="wrapper">

    <header class="header">
        <h3>${user.firstName} ${user.lastName}</h3>
        <a href="${sign_out_action}">Sign out</a>
    </header><!-- .header-->

    <main class="content">

    </main><!-- .content -->

</div><!-- .wrapper -->

<footer class="footer">

</footer><!-- .footer -->

</body>
</html>
