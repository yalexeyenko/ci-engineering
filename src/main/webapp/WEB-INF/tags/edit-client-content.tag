<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="edit_client_action" value="/do/editClientAction"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>

<link rel="stylesheet" href="${css_path}/create-client-content.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <form action="${edit_client_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
        <h3>Edit client: </h3>
        <div class="field-wrap">
            <span>Choose client type: </span>
            <select name="clientType" size="1" required>
                <c:if test="${not empty clientType}">
                    <c:if test="${clientType eq 'LEGAL'}">
                        <option value="LEGAL" selected>Legal Entity</option>
                        <option value="INDIVIDUAL">Individual Client</option>
                    </c:if>
                    <c:if test="${clientType eq 'INDIVIDUAL'}">
                        <option value="INDIVIDUAL" selected>Individual Client</option>
                        <option value="LEGAL">Legal Entity</option>
                    </c:if>
                </c:if>
                <c:if test="${empty clientType}">
                    <option disabled selected>Select type...</option>
                    <option value="INDIVIDUAL">Individual Client</option>
                    <option value="LEGAL">Legal Entity</option>
                </c:if>
            </select>
            <c:if test="${not empty clientTypeViolation}">
                <span class="violation">${clientTypeViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Name (legal entity)/First name (individual): </span>
            <input type="text" name="nameFirstName" value="${nameFirstName}" placeholder="Enter name/first name*" required/>
            <c:if test="${not empty nameFirstNameViolation}">
                <span class="violation">${nameFirstNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Full name (legal entity)/Last name (individual): </span>
            <input type="text" name="fullNameLastName" value="${fullNameLastName}" placeholder="Enter full name/last name*" required/>
            <c:if test="${not empty fullNameLastNameViolation}">
                <span class="violation">${fullNameLastNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Email address: </span>
            <input type="text" name="clientEmail" value="${clientEmail}" placeholder="Enter email*" required/>
            <c:if test="${not empty fullNameLastNameViolation}">
                <span class="violation">${fullNameLastNameViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Country: </span>
            <select name="clientCountry" size="1">
                <c:if test="${empty clientCountry}"><option disabled selected>Select country...</option></c:if>
                <c:if test="${not empty clientCountry}"><option value="${clientCountry}">${countriesMap[clientCountry]}</option></c:if>
                <c:forEach items="${countriesMap}" var="entry">
                    <option value="${entry.key}">${entry.value}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty clientCountryViolation}">
                <span class="violation">${clientCountryViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>City: </span>
            <input type="text" name="clientCity" value="${clientCity}" placeholder="Enter city*" required/>
            <c:if test="${not empty clientCityViolation}">
                <span class="violation">${clientCityViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Address: </span>
            <input type="text" name="clientAddress" value="${clientAddress}" placeholder="Enter address*" required/>
            <c:if test="${not empty clientAddressViolation}">
                <span class="violation">${clientAddressViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Telephone number: </span>
            <input type="text" name="clientTelephone" value="${clientTelephone}" placeholder="+1 (123) 123-1234*" required/>
            <c:if test="${not empty clientTelephoneViolation}">
                <span class="violation">${clientTelephoneViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>Bank account number: </span>
            <input type="text" name="clientBankAccountNumber" value="${clientBankAccountNumber}" placeholder="KZ000000000000000018*" required/>
            <c:if test="${not empty clientBankAccountNumberViolation}">
                <span class="violation">${clientBankAccountNumberViolation}</span>
            </c:if>
        </div>
        <div class="field-wrap">
            <span>EIN (Legal entity)/ SSN (Individual): </span>
            <input type="text" name="clientEinSsn" value="${clientEinSsn}" placeholder="123-12-1234*" required/>
            <c:if test="${not empty clientEinSsnViolation}">
                <span class="violation">${clientEinSsnViolation}</span>
            </c:if>
        </div>
        <input type="hidden" name="projectId" value="${projectId}"/>
        <input type="hidden" name="clientId" value="${clientId}"/>
        <button class="create-project-button" type="submit"/>Save changes</button>
        <c:if test="${not empty clientEdited}">
            <span class="edited-message">${clientEdited}</span>
        </c:if>
    </form>
</main>