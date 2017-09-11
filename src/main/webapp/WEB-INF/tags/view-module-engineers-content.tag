<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="adminContentUsers" pageEncoding="UTF-8" %>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="main_page" value="/do/main-page"/>
<c:url var="view_project" value="/do/pass-projectId"/>
<c:url var="view_modules" value="/do/view-project-modules"/>
<c:url var="view_module" value="/do/view-module"/>
<c:url var="view_module_engineers" value="/do/view-module-engineers"/>

<c:url var="add_module_engineer" value="/do/add-module-engineer"/>
<c:url var="delete_user_from_module" value="/do/delete-user-from-module"/>

<c:url var="images_path" value="${pageContext.request.contextPath}/images"/>

<main class="content">
    <div id="navcontainer">
        <ul id="navlist">
            <li><a href="${main_page}">Главная</a></li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li><a href="
                    <c:url value="${view_project}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="passProjectId" value="view-project"></c:param>
                    </c:url>
                ">Просмотр проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a id="view-modules" href="
                    <c:url value="${view_modules}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                    </c:url>
                ">Разделы проекта</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a href="
                    <c:url value="${view_module}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Просмотр раздела</a>
            </li>
            <li><img src="${images_path}/nav-arrow.png"></li>
            <li>
                <a id="view-module-engineers" href="
                    <c:url value="${view_module_engineers}">
                        <c:param name="projectId" value="${projectId}"></c:param>
                        <c:param name="moduleId" value="${moduleId}"></c:param>
                    </c:url>
                ">Инженеры</a>
            </li>
        </ul>
    </div>

    <div id="add-engineer-select">
        <%--Add engineer--%>
        <form action="${add_module_engineer}" method="post">
            <span>Добавить инженера к разделу: </span>
            <select name="moduleEngineer" size="1">
                <c:forEach items="${engineers}" var="engineer">
                    <option value="${engineer.id}">${engineer.firstName} ${engineer.lastName} - ${engineer.degree}</option>
                </c:forEach>
            </select>
            <input type="hidden" name="projectId" value="${projectId}"/>
            <input type="hidden" name="moduleId" value="${moduleId}"/>
            <button class="add-module-engineer-button" type="submit"/>Добавить</button>
            <c:if test="${not empty addEngineerSuccess}">
                <span class="violation1">${addEngineerSuccess}</span>
            </c:if>
            <c:if test="${not empty addEngineerFail}">
                <span class="violation">${addEngineerFail}</span>
            </c:if>
        </form>
        <c:if test="${not empty removalSuccess}">
            <span class="violation2">${removalSuccess}</span>
        </c:if>
    </div>

    <div id="table-users">
        <c:if test="${not empty moduleEngineers}">
            <table>
                <tr>
                    <th>№</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Образование</th>
                    <th>Открепить</th>
                </tr>
                <c:forEach items="${moduleEngineers}" var="item" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td class="td-align-left">${item.firstName}</td>
                        <td class="td-align-left">${item.lastName}</td>
                        <td class="td-align-left">${item.degree}</td>
                        <td>
                            <a href="
                            <c:url value="${delete_user_from_module}">
                                <c:param name="engineerId" value="${item.id}"></c:param>
                                <c:param name="projectId" value="${projectId}"></c:param>
                                <c:param name="moduleId" value="${moduleId}"></c:param>
                            </c:url>
                        "><img id="delete-icon" src="${images_path}/delete-icon.png"></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty moduleEngineers}">
            <span>Инженеры не найдены</span>
        </c:if>
    </div>
</main>