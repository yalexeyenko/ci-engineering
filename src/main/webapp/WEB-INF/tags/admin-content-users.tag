<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="adminContentUsers" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_user" value="/do/pass-userId"/>
<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>


<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li id="active"><a href="${main_page}">Главная</a></li>
        </ul>
    </div>
    <div id="table-users">
        <table>
            <tr>
                <th>№</th>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Email</th>
                <th>Должность (статус)</th>
                <th>Просмотр пользователя</th>
            </tr>
            <c:forEach items="${users}" var="item" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td class="td-align-left">${item.firstName}</td>
                    <td class="td-align-left">${item.lastName}</td>
                    <td>${item.email}</td>
                    <td>${item.roleName}</td>
                    <td>
                        <a href="
                        <c:url value="${view_user}">
                            <c:param name="userId" value="${item.id}"></c:param>
                            <c:param name="passUserId" value="admin-view-user"></c:param>
                        </c:url>
                        "><img id="view-icon" src="${images_path}/view.png"></a>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
</main>