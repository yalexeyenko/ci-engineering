<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="editProfileContent" pageEncoding="UTF-8" %>
<c:url var="edit_main_profile_info" value="/do/edit-main-profile-info"/>
<c:url var="change_password" value="/do/change-password"/>
<c:url var="main_page" value="/do/main-page"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}"> Главная</a></li>
        </ul>
    </div>
    <div id="edit-profile">
        <h3>Редактирование профиля</h3>
        <div class="edit-profile">
            <h4>Изменить личные данные:</h4>
            <form action="${edit_main_profile_info}" method="post" name="edit-main-profile-info"
                  onSubmit="return validate_form(this);">
                <c:if test="${not empty editProfileError}">
                    <span class="violation">${editProfileError}</span>
                </c:if>
                <div class="field-wrap">
                    <span>Имя: </span>
                    <input type="text" name="userFirstName" value="${userFirstName}" placeholder="введите имя*" required/>
                    <c:if test="${not empty userFirstNameViolation}">
                        <span class="violation">${userFirstNameViolation}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Фамилия: </span>
                    <input type="text" name="userLastName" value="${userLastName}" placeholder="введите фамилию*" required/>
                    <c:if test="${not empty userLastNameViolation}">
                        <span class="violation">${userLastNameViolation}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Email: </span>
                    <input type="text" name="userEmail" value="${userEmail}" placeholder="введите email*" required/>
                    <c:if test="${not empty userEmailViolation}">
                        <span class="violation">${userEmailViolation}</span>
                    </c:if>
                    <c:if test="${not empty editMainProfileInfoError}">
                        <span class="violation">${editMainProfileInfoError}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Образование (ученая сепень): </span>
                    <input type="text" name="userDegree" value="${userDegree}" maxlength="150"
                           placeholder="введите образование или ученую степень*" required/>
                    <c:if test="${not empty userDegreeViolation}">
                        <span class="violation">${userDegreeViolation}</span>
                    </c:if>
                </div>
                <button class="save-main-info" type="save"/>
                Сохранить</button>
                <c:if test="${not empty editMainProfileInfoSuccess}">
                    <span class="success-edit">${editMainProfileInfoSuccess}</span>
                </c:if>
            </form>
        </div>
        <div class="edit-profile">
            <h4>Изменить пароль:</h4>
            <form action="${change_password}" method="post" name="change-password" onSubmit="return validate_form(this);">
                <div class="field-wrap">
                    <span>Текущий пароль: </span>
                    <input type="password" name="current-password" placeholder="введите текущий пароль*" required/>
                    <c:if test="${not empty wrongPasswordViolation}">
                        <span class="violation">${wrongPasswordViolation}</span>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Новый пароль: </span>
                    <input type="password" name="password" placeholder="введите новый пароль*" required/>
                    <c:if test="${empty wrongPasswordViolation}">
                        <c:if test="${not empty passwordViolation}">
                            <span class="violation">${passwordViolation}</span>
                        </c:if>
                    </c:if>
                </div>
                <div class="field-wrap">
                    <span>Повтор нового пароля: </span>
                    <input type="password" name="repeatPassword" placeholder="повторите новый пароль*" required/>
                    <c:if test="${empty passwordViolation}">
                        <c:if test="${not empty mismatchViolation}">
                            <span class="violation">${mismatchViolation}</span>
                        </c:if>
                    </c:if>
                </div>
                <input type="hidden" name="userFirstName" value="${userFirstName}" value=""/>
                <input type="hidden" name="userLastName" value="${userLastName}" value=""/>
                <input type="hidden" name="userEmail" value="${userEmail}" value=""/>
                <input type="hidden" name="userDegree" value="${userDegree}" value=""/>
                <input type="hidden" name="userRole" value="${userRole}" value=""/>
                <button class="change-password" type="submit"/>
                Сохранить</button>
                <c:if test="${not empty changePasswordSuccess}">
                    <span class="success-edit">${changePasswordSuccess}</span>
                </c:if>
            </form>
        </div>
    </div>
</main>