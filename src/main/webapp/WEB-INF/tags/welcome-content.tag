<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="welcomeContent" pageEncoding="UTF-8" %>
<c:url var="sign_up_action" value="/do/signUp"/>

<main class="content">
        <div id="sign_up_form">
            <h3>Регистрация</h3>
            <form action="${sign_up_action}" method="post" name="sign_up" onSubmit="return validate_form(this);">
                <div class="top-row">
                    <div class="field-wrap">
                        <span>Имя: </span>
                        <input type="text" name="userFirstName" value="${userFirstName}" placeholder="введите имя*" required/>
                        <c:if test="${not empty userFirstNameViolation}">
                            <span class="violation">${userFirstNameViolation}</span>
                        </c:if>
                    </div>
                    <div class="field-wrap">
                        <span>Фамилия: </span>
                        <input type="text" name="userLastName"  value="${userLastName}" placeholder="введите фамилию*" required/>
                        <c:if test="${not empty userLastNameViolation}">
                            <span class="violation">${userLastNameViolation}</span>
                        </c:if>
                    </div>
                </div>
                <div class="field-wrap">
                    <span>Email: </span>
                    <input type="text" name="userEmail"  value="${userEmail}" placeholder="введите email*" required/>
                    <c:if test="${not empty userEmailViolation}">
                        <span class="violation">${userEmailViolation}</span>
                    </c:if>
                    <c:if test="${not empty signUpError}">
                        <span class="violation">${signUpError}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Пароль: </span>
                    <input type="password" name="password" placeholder="введите пароль*" id="password" required/>
                    <c:if test="${not empty passwordViolation}">
                        <span class="violation">${passwordViolation}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Повтор пароля: </span>
                    <input type="password" name="repeatPassword" placeholder="повторите пароль*" id="confirm_password" required/>
                    <c:if test="${not empty mismatchViolation and empty passwordViolation}">
                        <span class="violation">${mismatchViolation}</span>
                    </c:if>
                </div>
                <button class="create" type="submit" class="button-block"/>Зарегистрироваться</button>
            </form>
        </div>
    </main><!-- .content -->