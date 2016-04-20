<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="sign_in_action" value="/do/signIn"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/welcome-header.css">

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