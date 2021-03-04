<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Login Page</title>
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
        <jsp:include page="admin/header.jsp"/>


        <div class="col-9">
            <h1 class="display-5">Тайм-менеджмент:</h1>
            <h1 class="display-6">простые способы управления временем</h1>
        </div>

        <div class="col-3">
            <form name="loginForm" method="POST" action="controller">
                <div class="mb-2">
                    <input type="hidden" name="command" value="login"/>
                    <label class="form-label"><fmt:message key="login"/></label>
                    <input class="form-control" type="text" required minlength="3" maxlength="30" name="login" value=""/>
                </div>
                <div class="mb-3">
                    <label class="form-label"><fmt:message key="password"/></label>
                    <input class="form-control" type="password" required minlength="3" maxlength="30" name="password"
                           value=""/>
                </div>
                <input class="btn btn-primary" type="submit" value="<fmt:message key="log_in"/>"/>
                <input class="btn btn-primary" type="button" value="<fmt:message key="registration"/>"
                       onclick='location.href="controller?command=gotoregistration"'/>
            </form>
        </div>

    </div>
</div>
<script type="text/javascript" src="/js/bootstrap_js/bootstrap.min.js"></script>
</body>
</html>
