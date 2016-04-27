<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_profile_action" value="/do/editProfile"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/edit-profile-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <form action="${edit_profile_action}" method="post" name="edit_profile" onSubmit="return validate_form(this);">
        <h3>Edit profile</h3>
        <c:if test="${not empty editProfileError}">
            <span class="violation">${editProfileError}</span>
        </c:if>
        <div class="field-wrap">
            <span>First name: </span>
            <input type="text" name="firstName" value="${user.firstName}" placeholder="First name*" required/>
            <c:if test="${not empty firstNameViolation}">
                <span class="violation">${firstNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Last name: </span>
            <input type="text" name="lastName" value="${user.lastName}" placeholder="Last name*" required/>
            <c:if test="${not empty lastNameViolation}">
                <span class="violation">${lastNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Email: </span>
            <input type="text" name="email" value="${user.email}" placeholder="Enter email*" required/>
            <c:if test="${not empty emailViolation}">
                <span class="violation">${emailViolation}</span>
            </c:if>
            <c:if test="${not empty editProfileError}">
                <span class="violation">${editProfileError}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Degree: </span>
            <input type="text" name="degree" value="${user.degree}" placeholder="Degree*" required/>
            <c:if test="${not empty degreeViolation}">
                <span class="violation">${degreeViolation}</span>
            </c:if>
        </div>
        <c:if test="${user.role eq 'ADMIN'}">
            <div class="field-wrap">
                <span>Role: </span>
                <select name="role" size="1">
                    <c:forEach items="${user.roleValues}" var="item">
                        <c:if test="${item eq user.role}">
                            <option value="${item}" selected>${item}</option>
                        </c:if>
                        <c:if test="${item ne user.role}">
                            <option value="${item}">${item}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </c:if>
        <div class="field-wrap">
            <span>Current password: </span>
            <input type="password" name="current_password" placeholder="Current password*" required/>
            <c:if test="${not empty wrongPasswordViolation}">
                <span class="violation">${wrongPasswordViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Password: </span>
            <input type="password" name="password" placeholder="Password*" required/>
            <c:if test="${empty wrongPasswordViolation}">
                <c:if test="${not empty passwordViolation}">
                    <span class="violation">${passwordViolation}</span>
                </c:if>
                <c:if test="${empty passwordViolation}">
                    <c:if test="${not empty duplicatePasswordViolation}">
                        <span class="violation">${duplicatePasswordViolation}</span>
                    </c:if>
                </c:if>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Repeat password: </span>
            <input type="password" name="repeatPassword" placeholder="Repeat password*" required/>
            <c:if test="${empty passwordViolation}">
                <c:if test="${not empty repeatPasswordViolation}">
                    <span class="violation">${repeatPasswordViolation}</span>
                </c:if>
            </c:if>
        </div>
        <button class="save_changes" type="submit"/>
        Save changes</button>
    </form>
</main>