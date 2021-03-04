<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="current" value="${sessionScope.language}"/>
<c:if test="${not empty current}">
    <fmt:setLocale value="${current}" scope="session"/>
</c:if>
<fmt:setBundle basename="bundle" scope="session"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset = UTF-8">
    <title>Registration Page</title>
    <link href="/css/bootstrap_css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap_css/bootstrap-grid.css" rel="stylesheet">
    <link href="/css/bootstrap_css/bootstrap.css" rel="stylesheet">
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
        <div class="col">
        </div>

        <div class="col-8">
            <form name="registrationForm" method="POST" action="controller">
                <input type="hidden" name="command" value="registration"/>
                <h1 class="display-6"><fmt:message key="Registration_Form"/></h1>
                <div class="mb-3">
                </div>

                <form class="row g-3">
                    <div class="col-12">
                        <div class="input-group">
                            <span class="input-group-text"><fmt:message key="Name"/> <fmt:message key="Surname"/></span>
                            <input class="form-control" type="text" name="firstName" value="">
                            <input class="form-control" type="text" name="surname" value="">
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label"><fmt:message key="login"/></label>
                        <input class="form-control" type="text" name="login" value=""/>
                    </div>
                    <div class="col-12">
                        <label class="form-label"><fmt:message key="password"/></label>
                        <input class="form-control" type="password" name="password" value=""/>
                        <div id="passwordHelpBlock" class="form-text">
                            Your password must be 8-20 characters long, contain letters and numbers, and must not
                            contain spaces, special characters, or emoji.
                        </div>
                    </div>
                    <div class="mb-3">
                    </div>
                    <div class="wrapperButtons">
                        <input class="btn btn-primary" type="submit" value="<fmt:message key="Register"/>"/>
                        <input class="btn btn-primary" type="reset" value="<fmt:message key="Reset"/>"/>
                        ${pageContext.session.setAttribute("backpage", "login")}
                        <input class="btn btn-primary" type="button" value="<fmt:message key="Back"/>"
                               onclick='location.href="controller?command=back"'/>
                    </div>
                </form>
            </form>
            <strong> <c:out value="${requestScope.operationMessage}"/></strong>
        </div>

        <div class="col">
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/bootstrap_js/bootstrap.min.js"></script>
</body>
</html>
