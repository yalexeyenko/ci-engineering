<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="create_client_action" value="/do/createClientAction"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/create-client-content.css">

<main class="content">
    <form action="${create_client_action}" method="post" name="create_client" onSubmit="return validate_form(this);">
        <h3>Create client: </h3>
        <div class="field-wrap">
            <span>Choose client type: </span>
            <select name="clientType" size="1" required>
                <option disabled selected>...</option>
                <option value="LEGAL">Legal Entity</option>
                <option value="INDIVIDUAL">Individual Client</option>
            </select>
        </div>
        <div class="field-wrap">
            <span>Name (legal entity)/First name (individual): </span>
            <input type="text" name="nameFirstName" placeholder="Enter name/first name*" required/>
        </div>
        <div class="field-wrap">
            <span>Full name (legal entity)/Last name (individual): </span>
            <input type="text" name="fullNameLastName" placeholder="Enter full name/last name*" required/>
        </div>
        <div class="field-wrap">
            <span>Email address: </span>
            <input type="text" name="clientEmail" placeholder="Enter email*" required/>
        </div>
        <div class="field-wrap">
            <span>Country: </span>
            <input type="text" name="clientCountry" placeholder="Enter country*" required/>
        </div>
        <div class="field-wrap">
            <span>City: </span>
            <input type="text" name="clientCity" placeholder="Enter city*" required/>
        </div>
        <div class="field-wrap">
            <span>Address: </span>
            <input type="text" name="clientAddress" placeholder="Enter address*" required/>
        </div>
        <div class="field-wrap">
            <span>Telephone number: </span>
            <input type="text" name="clientTelephone" placeholder="Enter telephone number*" required/>
        </div>
        <div class="field-wrap">
            <span>Bank account number: </span>
            <input type="text" name="clientBankAccountNumber" placeholder="Enter bank account number*" required/>
        </div>
        <div class="field-wrap">
            <span>EIN (Legal entity)/ SSN (Individual): </span>
            <input type="text" name="clientEinSsn" placeholder="Enter EIN/SSN*" required/>
        </div>
        <input type="hidden" name="projectId" value="${project.id}"/>
        <button class="create-project-button" type="submit"/>Create</button>
    </form>
</main>