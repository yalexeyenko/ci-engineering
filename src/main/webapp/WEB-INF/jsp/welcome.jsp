<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="sign_in_action" value="/do/signIn"/>
<c:url var="sign_up_action" value="/do/signUp"/>
<c:url var="welcome_css" value="${pageContext.request.contextPath}/css/welcome.css"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome!</title>

    <link rel="stylesheet" href="${welcome_css}">

</head>

<body>
<div class="wrapper">

    <header class="header">
        <div id="sign_in_form">
            <form action="${sign_in_action}" method="post" name="sign_in">
                <div class="field-wrap">
                    <span>Email: </span>
                    <input type="text" name="email_in" value="${param.email_in}" placeholder="Enter your email" required/>
                </div>
                <div class="field-wrap">
                    <span>Password: </span>
                    <input type="password" name="password_in" value="${param.password_in}" placeholder="Password" required/>
                    <c:if test="${not empty signInError}">
                        <div class="signError">${signInError}</div>
                    </c:if>
                </div>
                <button class="in_up"/>Sign in</button>
            </form>
        </div>
    </header><!-- .header-->

    <main class="content">
        <div id="sign_up_form">
            <h3>Sign in or create account to continue</h3>
            <form action="${sign_up_action}" method="post" name="sign_up" onSubmit="return validate_form(this);">
                <div class="top-row">
                    <div class="field-wrap">
                        <span>First name: </span>
                        <input type="text" name="firstName" value="${param.firstName}" placeholder="First name*" required/>
                        <c:if test="${not empty firstNameViolation}">
                            <span class="violation">${firstNameViolation}</span>
                        </c:if>
                    </div>
                    <div class="field-wrap">
                        <span>Last name: </span>
                        <input type="text" name="lastName"  value="${param.lastName}" placeholder="Last name*" required/>
                        <c:if test="${not empty lastNameViolation}">
                            <span class="violation">${lastNameViolation}</span>
                        </c:if>
                    </div>
                </div>
                <div class="field-wrap">
                    <span>Email: </span>
                    <input type="text" name="email"  value="${param.email}" placeholder="Enter your email*" required/>
                    <c:if test="${not empty emailViolation}">
                        <span class="violation">${emailViolation}</span>
                    </c:if>
                    <c:if test="${not empty signUpError}">
                        <span class="violation">${signUpError}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Password: </span>
                    <input type="password" name="password" placeholder="Password*" id="password" required/>
                    <c:if test="${not empty passwordViolation}">
                        <span class="violation">${passwordViolation}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Repeat password: </span>
                    <input type="password" name="repeatPassword" placeholder="Repeat password*" id="confirm_password" required/>
                    <c:if test="${not empty repeatPasswordViolation}">
                        <span class="violation">${repeatPasswordViolation}</span>
                    </c:if>
                </div>
                <button class="create" type="submit" class="button-block"/>Create account</button>
            </form>
        </div>
    </main><!-- .content -->

</div><!-- .wrapper -->

<footer class="footer">
</footer><!-- .footer -->

</body>
</html>