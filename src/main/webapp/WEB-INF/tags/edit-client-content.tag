<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="edit_client_action" value="/do/editClientAction"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/create-client-content.css">

<main class="content">
    <form action="${edit_client_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
        <h3>Edit client: </h3>
        <div class="field-wrap">
            <span>Choose client type: </span>
            <select name="clientType" size="1" required>
                <c:if test="${project.client.clientType eq 'LEGAL'}">
                    <option value="LEGAL" selected>Legal Entity</option>
                    <option value="INDIVIDUAL">Individual Client</option>
                </c:if>
                <c:if test="${project.client.clientType eq 'INDIVIDUAL'}">
                    <option value="INDIVIDUAL" selected>Individual Client</option>
                    <option value="LEGAL">Legal Entity</option>
                </c:if>
            </select>
        </div>
        <div class="field-wrap">
            <span>Name (legal entity)/First name (individual): </span>
            <input type="text" name="nameFirstName" value="${project.client.firstName}" placeholder="Enter name/first name*" required/>
        </div>
        <div class="field-wrap">
            <span>Full name (legal entity)/Last name (individual): </span>
            <input type="text" name="fullNameLastName" value="${project.client.lastName}" placeholder="Enter full name/last name*" required/>
        </div>
        <div class="field-wrap">
            <span>Email address: </span>
            <input type="text" name="clientEmail" value="${project.client.email}" placeholder="Enter email*" required/>
        </div>
        <div class="field-wrap">
            <span>Country: </span>
            <input type="text" name="clientCountry" value="${project.client.country}" placeholder="Enter country*" required/>
        </div>
        <div class="field-wrap">
            <span>City: </span>
            <input type="text" name="clientCity" value="${project.client.city}" placeholder="Enter city*" required/>
        </div>
        <div class="field-wrap">
            <span>Address: </span>
            <input type="text" name="clientAddress" value="${project.client.address}" placeholder="Enter address*" required/>
        </div>
        <div class="field-wrap">
            <span>Telephone number: </span>
            <input type="text" name="clientTelephone"  value="${project.client.telephone}" placeholder="Enter telephone number*" required/>
        </div>
        <div class="field-wrap">
            <span>Bank account number: </span>
            <input type="text" name="clientBankAccountNumber" value="${project.client.bankAccountNumber}" placeholder="Enter bank account number*" required/>
        </div>
        <div class="field-wrap">
            <span>EIN (Legal entity)/ SSN (Individual): </span>
            <input type="text" name="clientEinSsn" value="${project.client.einSsn}" placeholder="Enter EIN/SSN*" required/>
        </div>
        <input type="hidden" name="projectId" value="${project.id}"/>
        <input type="hidden" name="clientId" value="${project.client.id}"/>
        <button class="edit-client-button" type="submit"/>Save</button>
    </form>
</main>