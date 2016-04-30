<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="adminContentUsers" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_user" value="/do/pass-userId"/>

<link rel="stylesheet" href="${css_path}/admin-content-users.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Home</a></li>
        </ul>
    </div>
    <div id="table-users">
        <table>
            <tr>
                <th>№</th>
                <th>ID</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Email</th>
                <th>Role</th>
                <th>View</th>
            </tr>
            <c:forEach items="${users}" var="item" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${item.id}</td>
                    <td>${item.firstName}</td>
                    <td>${item.lastName}</td>
                    <td>${item.email}</td>
                    <td>${item.role}</td>
                    <td>
                        <a href="
                        <c:url value="${view_user}">
                            <c:param name="userId" value="${item.id}"></c:param>
                            <c:param name="passUserId" value="admin-view-user"></c:param>
                        </c:url>
                        ">View</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
</main>