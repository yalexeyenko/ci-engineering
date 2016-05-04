<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="welcomeContent" pageEncoding="UTF-8" %>
<c:url var="sign_up_action" value="/do/signUp"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/welcome-content.css">

<main class="content">
        <div id="sign_up_form">
            <h3>Sign in or create account to continue</h3>
            <form action="${sign_up_action}" method="post" name="sign_up" onSubmit="return validate_form(this);">
                <div class="top-row">
                    <div class="field-wrap">
                        <span>First name: </span>
                        <input type="text" name="userFirstName" value="${userFirstName}" placeholder="First name*" required/>
                        <c:if test="${not empty userFirstNameViolation}">
                            <span class="violation">${userFirstNameViolation}</span>
                        </c:if>
                    </div>
                    <div class="field-wrap">
                        <span>Last name: </span>
                        <input type="text" name="userLastName"  value="${userLastName}" placeholder="Last name*" required/>
                        <c:if test="${not empty userLastNameViolation}">
                            <span class="violation">${userLastNameViolation}</span>
                        </c:if>
                    </div>
                </div>
                <div class="field-wrap">
                    <span>Email: </span>
                    <input type="text" name="userEmail"  value="${userEmail}" placeholder="Enter your email*" required/>
                    <c:if test="${not empty userEmailViolation}">
                        <span class="violation">${userEmailViolation}</span>
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
                    <c:if test="${not empty mismatchViolation and empty passwordViolation}">
                        <span class="violation">${mismatchViolation}</span>
                    </c:if>
                </div>
                <button class="create" type="submit" class="button-block"/>Create account</button>
            </form>
        </div>
    </main><!-- .content -->