<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="welcomeHeader" pageEncoding="UTF-8" %>
<c:url var="sign_in_action" value="/do/signIn"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<header class="header">
    <div class="logotype">
        <img src="${images_path}/logo.png">
    </div>
    <div id="sign_in_form">
        <h3>Вход</h3>
        <form action="${sign_in_action}" method="post" name="sign_in">
            <div class="field-wrap">
                <span>Email: </span>
                <input type="text" name="email_in" value="${param.email_in}" placeholder="введите email" required/>
            </div>
            <div class="field-wrap">
                <span>Пароль: </span>
                <input type="password" name="password_in" value="${param.password_in}" placeholder="введите пароль"
                       required/>
                <c:if test="${not empty signInError}">
                    <div class="signError">${signInError}</div>
                </c:if>
            </div>
            <button class="in_up"/>Войти</button>
        </form>
    </div>
</header>
<!-- .header-->