<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>Client account page</title>
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
            <table class="table">
                <thead>
                <tr>
                    <th><fmt:message key="ACTIVITIES"/></th>
                    <th><fmt:message key="STATUS"/></th>
                    <th><fmt:message key="ACTION"/></th>
                    <th><fmt:message key="TIME"/></th>
                    <th><fmt:message key="NOTICE"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.trackingList}" var="tracking">
                    <c:set var="userId" value="${sessionScope.clientUser.userId}"/>
                    <tr>
                    <c:if test="${tracking.user.userId==userId}">
                        <td>
                            <c:out value="${tracking.activity.activityName}"/>
                        </td>
                        <td>
                            <fmt:message key="${tracking.status}"/>
                        </td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <form class="formElement" name="actionForm" method="POST"
                                              action="controller">
                                            <div class="wrapperButtons">
                                                <input type="hidden" name="trackingId"
                                                       value="${tracking.trackingId}"/>
                                                <input type="hidden" name="command" value="startTime"/>
                                                <input class="buttonElement" type="submit"
                                                       value="<fmt:message key="start"/>"/>
                                            </div>
                                        </form>
                                    </td>
                                    <td>
                                        <form class="formElement" name="actionForm" method="POST"
                                              action="controller">
                                            <div class="wrapperButtons">
                                                <input type="hidden" name="trackingId"
                                                       value="${tracking.trackingId}"/>
                                                <input type="hidden" name="command" value="stopTime"/>
                                                <input class="buttonElement" type="submit"
                                                       value="<fmt:message key="stop"/>"/>
                                            </div>
                                        </form>
                                    </td>
                                    <td align="center">
                                        <form class="formElement" name="finishForm" method="POST"
                                              action="controller">
                                            <div class="wrapperButtons">
                                                <input type="hidden" name="trackingId"
                                                       value="${tracking.trackingId}"/>
                                                <input type="hidden" name="command" value="finish"/>
                                                <input class="buttonElement" type="submit"
                                                       value="<fmt:message key="finish"/>"/>
                                            </div>
                                        </form>
                                    </td>
                                    <td align="center">
                                        <c:if test="${tracking.status == 'FINISHED'}">
                                            <form class="formElement" name="finishForm" method="POST"
                                                  action="controller">
                                                <div class="wrapperButtons">
                                                    <input type="hidden" name="trackingId"
                                                           value="${tracking.trackingId}"/>
                                                    <input type="hidden" name="command" value="remove"/>
                                                    <input class="buttonElement" type="submit"
                                                           value="<fmt:message key="remove"/>"/>
                                                </div>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${tracking.timeSwitch == 'true'}">
                                    <script type="text/javascript">
                                        var countSecond = parseInt(${tracking.differenceTime/1000%60});
                                        var countMinutes = parseInt(${tracking.differenceTime/1000/60%60});
                                        var countHours = parseInt(${tracking.differenceTime/1000/60/60%60});
                                    </script>
                                    <label id="hours"></label>:<label id="minutes"></label>:<label id="seconds"></label>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${tracking.elapsedTime}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:set var="request" value="${tracking.userRequest}"/>
                            <c:choose>
                                <c:when test="${request=='REMOVE'}">
                                    <fmt:message key="approval"/>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${requestScope.duplicateStart == 'true' &&
                                     requestScope.trackingId == tracking.trackingId}">
                                        <fmt:message key="warning_duplicate_start"/>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
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
                    <h5 class="card-title">
                        <jsp:useBean id="welcometext" scope="session" class="com.potemkin.timetracking.mybean.MyBean"/>
                        <jsp:getProperty name="welcometext" property="field"/>
                        <c:out value="${welcometext.who}"/>
                    </h5>
                    <h5 class="card-title">
                        <c:out value="${sessionScope.clientUser.firstName} ${sessionScope.clientUser.surName}"/></h5>
                </div>
                <div class="card-footer text-muted">
                    <form class="formElement" name="formElement" method="POST" action="controller">
                        <div class="wrapperButtons">
                            <input type="hidden" name="userId" value="${sessionScope.clientUser.userId}"/>
                            <input type="hidden" name="command" value="add"/>
                            <input type="submit" class="btn btn-primary" value="<fmt:message key="add_activity"/>"/>
                        </div>
                    </form>
                    <form name="logout" method="POST" action="controller">
                        <input type="hidden" name="command" value="logout"/>
                        <input type="submit" class="btn btn-primary" value="<fmt:message key="logout"/>"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    <%---------------------------------------------seconds--------------------------------------------------------%>
    if (countSecond > 0 && countSecond < 10) {
        document.getElementById("seconds").innerHTML = "0" + countSecond;
    } else if (countSecond == 0 || countSecond == 60) {
        document.getElementById("seconds").innerHTML = "00";
    } else {
        document.getElementById("seconds").innerHTML = countSecond;
    }

    setInterval(function () {
        myTimerSec()
    }, 1000);

    function myTimerSec() {
        if (countSecond == 59) {
            document.getElementById("seconds").innerHTML = "00";
            countSecond = 0;
            myTimerMin();
        } else if (countSecond < 9) {
            countSecond = countSecond + 1;
            document.getElementById("seconds").innerHTML = "0" + countSecond;
        } else if (countSecond < 59 && countSecond > 0) {
            countSecond = countSecond + 1;
            document.getElementById("seconds").innerHTML = countSecond;
        }
    }

    <%---------------------------------------------minutes--------------------------------------------------------%>
    if (countMinutes > 0 && countMinutes < 10) {
        document.getElementById("minutes").innerHTML = "0" + countMinutes;
    } else if (countMinutes == 0 || countMinutes == 60) {
        document.getElementById("minutes").innerHTML = "00";
    } else {
        document.getElementById("minutes").innerHTML = countMinutes;
    }

    function myTimerMin() {
        if (countMinutes == 59) {
            document.getElementById("minutes").innerHTML = "00";
            countMinutes = 0;
            myTimerHour();
        } else if (countMinutes < 9) {
            countMinutes = countMinutes + 1;
            document.getElementById("minutes").innerHTML = "0" + countMinutes;
        } else if (countMinutes < 59 && countMinutes > 0) {
            countMinutes = countMinutes + 1;
            document.getElementById("minutes").innerHTML = countMinutes;
        }
    }

    <%---------------------------------------------hours--------------------------------------------------------%>
    if (countHours > 0 && countHours < 10) {
        document.getElementById("hours").innerHTML = "0" + countHours;
    } else if (countHours == 0 || countHours == 60) {
        document.getElementById("hours").innerHTML = "00";
    } else {
        document.getElementById("hours").innerHTML = countHours;
    }

    function myTimerHour() {
        if (countHours == 59) {
            document.getElementById("hours").innerHTML = "00";
            countHours = 0;
        } else if (countHours < 9) {
            countHours = countHours + 1;
            document.getElementById("hours").innerHTML = "0" + countHours;
        } else if (countHours < 59 && countHours > 0) {
            countHours = countHours + 1;
            document.getElementById("hours").innerHTML = countHours;
        }
    }
</script>
<script type="text/javascript" src="/js/bootstrap_js/bootstrap.min.js"></script>
</body>
</html>
