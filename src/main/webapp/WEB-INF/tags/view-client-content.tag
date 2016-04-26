<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="createClientContent" pageEncoding="UTF-8" %>
<c:url var="edit_client" value="/do/pass-projectId"/>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>

<link rel="stylesheet" href="${css_path}/view-client-content.css">

<main class="content">
    <h3>Client info</h3>
    <h4>Client name:
        <c:if test="${project.client.clientType eq 'LEGAL'}">${project.client.firstName}</c:if>
        <c:if test="${project.client.clientType eq 'INDIVIDUAL'}">${project.client.firstName} ${project.client.lastName}</c:if>
    </h4>
    <h4>Client type: ${project.client.clientType}</h4>
    <h4>Email: ${project.client.email}</h4>
    <h4>Country: ${project.client.country}</h4>
    <h4>City: ${project.client.city}</h4>
    <h4>Address: ${project.client.address}</h4>
    <h4>Telephone: ${project.client.telephone}</h4>
    <h4>Bank account number: ${project.client.bankAccountNumber}</h4>
    <h4>EIN/SSN: ${project.client.einSsn}</h4>
    <a href="
        <c:url value="${edit_client}">
            <c:param name="projectId" value="${project.id}"></c:param>
            <c:param name="passProjectId" value="edit-client"></c:param>
        </c:url>
    ">Edit client</a>
</main>