<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="current" value="${sessionScope.language}"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="bundle" scope="session"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset = UTF-8">
    <link href="/css/bootstrap_css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap_css/bootstrap-grid.css" rel="stylesheet">
    <link href="/css/bootstrap_css/bootstrap.css" rel="stylesheet">
    <title>Admin page</title>
</head>
<body>
<div class="container">
    <div class="row align-items-center">
        <jsp:include page="header.jsp"/>
        <div class="col-9">
            <table class="table">
                <thead>
                <tr>
                    <th><fmt:message key="ACTIVITIES"/></th>
                    <th><fmt:message key="STATUS"/></th>
                    <th><fmt:message key="TIME"/></th>
                    <th><fmt:message key="ACTION"/></th>
                    <th><fmt:message key="NOTICE"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${sessionScope.trackingList}" var="tracking">
                    <c:set var="userId" value="${sessionScope.overviewUser.userId}"/>
                    <c:if test="${tracking.user.userId==userId}">
                        <tr>
                            <td>
                                <c:out value="${tracking.activity.activityName}"/>
                            </td>
                            <td>
                                <fmt:message key="${tracking.status}"/>
                            </td>
                            <td>
                                <c:out value="${tracking.elapsedTime}"/>
                            </td>
                            <td>
                                <c:set var="status" value="${tracking.userRequest}"/>
                                <c:if test="${status=='REMOVE'}">
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="controller">
                                        <input type="hidden" name="trackingId" value="${tracking.trackingId}"/>
                                        <input type="hidden" name="userId" value="${tracking.user.userId}"/>
                                        <input type="hidden" name="command" value="removeAdmin"/>
                                        <input class="buttonElement" type="submit" value="<fmt:message key="remove"/>"/>
                                    </form>
                                </c:if>
                            </td>
                            <td>
                                <c:set var="request" value="${tracking.userRequest}"/>
                                <c:if test="${request=='REMOVE'}">
                                    <fmt:message key="response"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-3">
            <div class="card text-end">
                <div class="card-header">
                    <h5 class="card-title"><fmt:message key="Client"/></h5>
                    <h5 class="card-title"><c:out
                            value="${sessionScope.overviewUser.firstName} ${sessionScope.overviewUser.surName}"/></h5>
                </div>
                <div class="card-body">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#targetModal">
                        <fmt:message key="AVAILABLE_ACTIVITIES"/>
                    </button>
                </div>
                <div class="card-footer text-muted">
                    <form name="backForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="backAdmin"/>
                        <input type="submit" class="btn btn-primary" value="<fmt:message key="Back"/>"/>
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
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <c:forEach items="${sessionScope.activityAdminList}" var="activity">
                        <td>
                            <c:out value="${activity.activityName}"/>
                        </td>
                        <td>
                            <form class="formElement" name="actionForm" method="POST"
                                  action="controller">
                                <div class="wrapperButtons">
                                    <input type="hidden" name="userId" value="${sessionScope.overviewUser.userId}"/>
                                    <input type="hidden" name="activityId" value="${activity.activityId}"/>
                                    <input type="hidden" name="command" value="addActivity"/>
                                    <input class="buttonElement" type="submit" value="<fmt:message key="add"/>"/>
                                </div>
                            </form>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/bootstrap_js/bootstrap.min.js"></script>
</body>
</html>