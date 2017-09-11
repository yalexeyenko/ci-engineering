<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="edit_client" pageEncoding="UTF-8" %>
<c:url var="edit_client_action" value="/do/editClientAction"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_client" value="/do/pass-projectId"/>
<c:url var="edit_client" value="/do/pass-projectId"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Просмотр проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_client}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-client"></c:param>
                    </c:url>
                ">Просмотр заказчика</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${edit_client}">
                        <c:param name="projectId" value="${project.id}"></c:param>
                        <c:param name="passProjectId" value="edit-client"></c:param>
                    </c:url>
                ">Редактирование заказчика</a>
            </li>
        </ul>
    </div>
    <div id="edit-client">
        <form action="${edit_client_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
            <h3>Редактирование заказчика: </h3>
            <div class="wrap">
                <span class="sp">Выберите тип заказчика: </span>
                <select name="clientType" size="1" required>
                    <c:if test="${not empty clientType}">
                        <c:if test="${clientType eq 'LEGAL'}">
                            <option value="LEGAL" selected>Юридическое лицо</option>
                            <option value="INDIVIDUAL">Физическе лицо</option>
                        </c:if>
                        <c:if test="${clientType eq 'INDIVIDUAL'}">
                            <option value="INDIVIDUAL" selected>Физическе лицо</option>
                            <option value="LEGAL">Юридическое лицо</option>
                        </c:if>
                    </c:if>
                    <c:if test="${empty clientType}">
                        <option disabled selected>выберите тип...</option>
                        <option value="INDIVIDUAL">Физическе лицо</option>
                        <option value="LEGAL">Юридическое лицо</option>
                    </c:if>
                </select>
                <c:if test="${not empty clientTypeViolation}">
                    <span class="violation">${clientTypeViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Наименование (юридическое лицо)/Имя (физическое лицо): </span>
                <input class="twol" type="text" name="nameFirstName" value="${nameFirstName}" placeholder="введите название/имя*" required/>
                <c:if test="${not empty nameFirstNameViolation}">
                    <span class="violation">${nameFirstNameViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Полное наименование (юридическое лицо)/Фамилия (физическое лицо): </span>
                <input class="twol" type="text" name="fullNameLastName" value="${fullNameLastName}" placeholder="введите полное наименование/фамилию*" required/>
                <c:if test="${not empty fullNameLastNameViolation}">
                    <span class="violation">${fullNameLastNameViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Email: </span>
                <input class="onel" type="text" name="clientEmail" value="${clientEmail}" placeholder="введите email*" required/>
                <c:if test="${not empty fullNameLastNameViolation}">
                    <span class="violation">${fullNameLastNameViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Страна: </span>
                <select class="onel" name="clientCountry" size="1">
                    <c:if test="${empty clientCountry}"><option disabled selected>выберите страну...</option></c:if>
                    <c:if test="${not empty clientCountry}"><option value="${clientCountry}">${countriesMap[clientCountry]}</option></c:if>
                    <c:forEach items="${countriesMap}" var="entry">
                        <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                </select>
                <c:if test="${not empty clientCountryViolation}">
                    <span class="violation">${clientCountryViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Город: </span>
                <input class="onel" type="text" name="clientCity" value="${clientCity}" placeholder="введите город*" required/>
                <c:if test="${not empty clientCityViolation}">
                    <span class="violation">${clientCityViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Адрес: </span>
                <input class="onel" type="text" name="clientAddress" value="${clientAddress}" placeholder="введите адрес*" required/>
                <c:if test="${not empty clientAddressViolation}">
                    <span class="violation">${clientAddressViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Телефон: </span>
                <input class="onel" type="text" name="clientTelephone" value="${clientTelephone}" placeholder="+1 (123) 123-1234*" required/>
                <c:if test="${not empty clientTelephoneViolation}">
                    <span class="violation">${clientTelephoneViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">Номер банковского счета: </span>
                <input class="onel" type="text" name="clientBankAccountNumber" value="${clientBankAccountNumber}" placeholder="KZ000000000000000018*" required/>
                <c:if test="${not empty clientBankAccountNumberViolation}">
                    <span class="violation">${clientBankAccountNumberViolation}</span>
                </c:if>
            </div>
            <div class="wrap">
                <span class="sp">EIN (юридическое лицо)/ SSN (Физическое лицо): </span>
                <input class="twol" type="text" name="clientEinSsn" value="${clientEinSsn}" placeholder="123-12-1234*" required/>
                <c:if test="${not empty clientEinSsnViolation}">
                    <span class="violation">${clientEinSsnViolation}</span>
                </c:if>
            </div>
            <input type="hidden" name="projectId" value="${projectId}"/>
            <input type="hidden" name="clientId" value="${clientId}"/>
            <button class="create-project-button" type="submit"/>Сохранить</button>
            <c:if test="${not empty clientEdited}">
                <span class="edited-message">${clientEdited}</span>
            </c:if>
        </form>
    </div>

</main>