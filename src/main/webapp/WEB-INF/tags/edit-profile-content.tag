<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_profile_info" value="/do/edit-main-profile-info"/>
<c:url var="change_password" value="/do/change-password"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/edit-profile-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <form action="${edit_main_profile_info}" method="post" name="edit-main-profile-info" onSubmit="return validate_form(this);">
        <h3>Edit profile</h3>
        <h4>Edit main info:</h4>
        <c:if test="${not empty editProfileError}">
            <span class="violation">${editProfileError}</span>
        </c:if>
        <div class="field-wrap">
            <span>First name: </span>
            <input type="text" name="userFirstName" value="${userFirstName}" placeholder="First name*" required/>
            <c:if test="${not empty userFirstNameViolation}">
                <span class="violation">${userFirstNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Last name: </span>
            <input type="text" name="userLastName" value="${userLastName}" placeholder="Last name*" required/>
            <c:if test="${not empty userLastNameViolation}">
                <span class="violation">${userLastNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Email: </span>
            <input type="text" name="userEmail" value="${userEmail}" placeholder="Enter email*" required/>
            <c:if test="${not empty userEmailViolation}">
                <span class="violation">${userEmailViolation}</span>
            </c:if>
            <c:if test="${not empty editMainProfileInfoError}">
                <span class="violation">${editMainProfileInfoError}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Degree: </span>
            <input type="text" name="userDegree" value="${userDegree}" placeholder="Degree*" required/>
            <c:if test="${not empty userDegreeViolation}">
                <span class="violation">${userDegreeViolation}</span>
            </c:if>
        </div>
        <button class="save-main-info" type="save"/>Save changes</button>
        <c:if test="${not empty editMainProfileInfoSuccess}">
            <span class="success-edit">${editMainProfileInfoSuccess}</span>
        </c:if>
    </form>
    <form action="${change_password}" method="post" name="change-password" onSubmit="return validate_form(this);">
        <h4>Change password:</h4>
        <div class="field-wrap">
            <span>Current password: </span>
            <input type="password" name="current-password" placeholder="Current password*" required/>
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
        <input type="hidden" name="userFirstName" value="${userFirstName}" value=""/>
        <input type="hidden" name="userLastName" value="${userLastName}" value=""/>
        <input type="hidden" name="userEmail" value="${userEmail}" value=""/>
        <input type="hidden" name="userDegree" value="${userDegree}" value=""/>
        <input type="hidden" name="userRole" value="${userRole}" value=""/>
        <button class="change-password" type="submit"/>Change password</button>
        <c:if test="${not empty changePasswordSuccess}">
            <span class="success-edit">${changePasswordSuccess}</span>
        </c:if>
    </form>
</main>