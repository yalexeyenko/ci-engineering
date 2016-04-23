<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="adminContentUsers" pageEncoding="UTF-8" %>
<c:url var="css_path" value="${pageContext.request.contextPath}/css"/>


<link rel="stylesheet" href="${css_path}/admin-content-users.css">

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="#" id="current">Users</a></li>
            <li><a href="#">Item 2</a></li>
            <li><a href="#">Item 3</a></li>
            <li><a href="#">Item 4</a></li>
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
                <th>Degree</th>
            </tr>
            <c:forEach items="${users}" var="item" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td>${item.id}</td>
                    <td>${item.firstName}</td>
                    <td>${item.lastName}</td>
                    <td>${item.email}</td>
                    <td>${item.degree}</td>
                </tr>
            </c:forEach>
        </table>

    </div>
</main>