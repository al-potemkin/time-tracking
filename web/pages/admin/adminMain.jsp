<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/tag.tld" prefix="myTag" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current" value="${sessionScope.language}"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="bundle" scope="session"/>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset = UTF-8">
    <link href="/css/bootstrap_css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap_css/bootstrap-grid.css" rel="stylesheet">
    <link href="/css/bootstrap_css/bootstrap.css" rel="stylesheet">
    <title>Admin page</title>
    <style type="text/css">
        body {
            background-image: url(images/man.jpg);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: bottom;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row align-items-center">
        <jsp:include page="header.jsp"/>

        <div class="col-9">
            <!--Панель с юзерами-->
            <table class="table">
                <thead>
                <tr>
                    <th><fmt:message key="USERS"/></th>
                    <th><fmt:message key="ACTIVITIES"/></th>
                    <th><fmt:message key="REQUEST_FROM_CLIENT"/></th>
                    <th><fmt:message key="NOTICE"/></th>
                </tr>
                </thead>

                <tbody>
                <c:set var="countPage" value="${1}" scope="page"/>
                <c:set var="countItem" value="${0}" scope="page"/>
                <c:forEach items="${sessionScope.userList}" var="user">
                    <c:if test="${user.userType.userType=='client'}">
                        <c:if test="${countPage == sessionScope.currentPage}">
                            <tr>
                                <td>
                                    <c:out value="${user.firstName} ${user.surName}"/>
                                </td>
                                <td>
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="controller">
                                        <div class="wrapperButtons">
                                            <input type="hidden" name="userId" value="${user.userId}"/>
                                            <input type="hidden" name="command" value="overviewClient"/>
                                            <input type="submit" class="btn btn-primary btn-sm"
                                                   value="<fmt:message key="overview"/>"/>
                                        </div>
                                    </form>

                                </td>
                                <td>
                                    <table>
                                        <tr>
                                            <td>
                                                <c:set var="flag" value="false"/>
                                                <c:if test="${user.requestAdd=='true'}">
                                                    <button class="btn btn-success btn-sm"><fmt:message
                                                            key="add_new_activity"/></button>
                                                    <c:set var="flag" value="true"/>
                                                </c:if>
                                                <c:if test="${flag == 'false'}">
                                                    <button class="btn btn-secondary btn-sm" disabled><fmt:message
                                                            key="add_new_activity"/></button>

                                                </c:if>
                                            </td>
                                            <td>
                                                <c:set var="flag" value="false"/>
                                                <c:forEach items="${sessionScope.trackingList}" var="tracking">
                                                    <c:if test="${tracking.user.userId==user.userId &&
                                                 tracking.userRequest == 'REMOVE'}">
                                                        <c:set var="flag" value="true"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:choose>
                                                    <c:when test="${flag == 'true'}">
                                                        <button class="btn btn-success btn-sm"><fmt:message
                                                                key="remove_finished_activity"/></button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-secondary btn-sm" disabled>
                                                            <fmt:message
                                                                    key="remove_finished_activity"/></button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <c:set var="flag" value="false"/>
                                    <c:forEach items="${sessionScope.trackingList}" var="tracking">
                                        <c:if test="${tracking.user.userId==user.userId && (tracking.userRequest == 'REMOVE'||
                                    tracking.userRequest == 'ADD' || user.requestAdd == 'true')}">
                                            <c:set var="flag" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${flag == 'true'}">
                                            <fmt:message key="waiting"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="no_request"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:if>
                        <c:set var="countItem" value="${countItem + 1}"/>
                        <c:if test="${countItem%sessionScope.itemsPerPage==0}">
                            <c:set var="countPage" value="${countPage + 1}"/>
                        </c:if>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
            <!--Пагинация-->
            <div class="btn-toolbar">
                <div class="btn-group me-2">
                    <c:if test="${sessionScope.currentPage<sessionScope.lastPage}">
                        <a class="btn btn-outline-primary disabled">&laquo;</a>
                    </c:if>
                    <c:if test="${sessionScope.currentPage>'1'}">
                        <c:set var="count" value="${sessionScope.currentPage - 1}" scope="page"/>
                        <a class="btn btn-outline-primary" href="controller?command=chosePage&currentPage=${count}">&laquo;</a>
                    </c:if>
                    <c:forEach items="${sessionScope.numbersPages}" var="page">
                        <c:choose>
                            <c:when test="${page == sessionScope.currentPage}">
                                <a class="btn btn-primary"
                                   href="controller?command=chosePage&currentPage=${page}"><c:out
                                        value="${page}"/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-outline-primary"
                                   href="controller?command=chosePage&currentPage=${page}"><c:out
                                        value="${page}"/></a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${sessionScope.currentPage<sessionScope.lastPage}">
                        <c:set var="count" value="${sessionScope.currentPage + 1}" scope="page"/>
                        <a class="btn btn-outline-primary" href="controller?command=chosePage&currentPage=${count}">&raquo;</a>
                    </c:if>
                    <c:if test="${sessionScope.currentPage>'1'}">
                        <a class="btn btn-outline-primary disabled">&raquo;</a>
                    </c:if>
                </div>
            </div>
        </div>

        <!--Боковая панель-->
        <div class="col-3">
            <div class="card text-end">
                <div class="card-header">
                    <h5 class="card-title"><fmt:message key="welcome_admin"/></h5>
                    <h5 class="card-title"><c:out
                            value="${sessionScope.adminUser.firstName} ${sessionScope.adminUser.surName}"/></h5>
                </div>
                <div class="card-body">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#targetModal">
                        <fmt:message key="AVAILABLE_ACTIVITIES"/>
                    </button>
                </div>
                <div class="card-footer text-muted">

                    <form name="logout" method="POST" action="controller">
                        <input type="hidden" name="command" value="logout"/>
                        <input type="submit" class="btn btn-primary" value="<fmt:message key="logout"/>"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="targetModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="AVAILABLE_ACTIVITIES"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <c:forEach items="${sessionScope.activityAdminList}" var="activity">
                                ${activity.activityName}<br>
                            </c:forEach>
                        </td>
                    </tr>
                    </tbody>
                    <form class="formElement" name="actionForm" method="POST" action="controller">
                        <div class="modal-footer">
                            <div class="input-group mb-3">
                                <input type="hidden" name="command" value="createActivity"/>
                                <input class="form-control" type="text" name="activityName" value=""/>
                                <input class="btn btn-primary" type="submit"
                                       value="<fmt:message key="add_activity"/>"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </table>
            </div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/bootstrap_js/bootstrap.min.js"></script>
</body>
</html>
