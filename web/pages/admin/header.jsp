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
</head>
<html>
<body>
<nav class="navbar navbar-light">
    <a class="navbar-brand">
        <h1 class="display-4">Time tracking</h1>
        <figcaption class="blockquote-footer">Organize your time</figcaption>
    </a>
    <div>
        <div class="languageElement" style="position:fixed; right:20px; top:10px;">
            <table>
                <tr>
                    <form class="formElement" name="actionForm" method="POST" action="controller">
                        <td>
                            <input type="hidden" name="command" value="setLanguage"/>
                            <input type="hidden" name="page" value="loginPage"/>
                        </td>
                        <td>
                            <div class="col-md">
                                <select class="form-select" id="floatingSelect" name="chosenLanguage"
                                        onchange="this.form.submit()">
                                    <c:choose>
                                        <c:when test="${current == 'en_EN'}">
                                            <option value="en_EN"><fmt:message key="en"/></option>
                                            <option value="ru_RU"><fmt:message key="ru"/></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="ru_RU"><fmt:message key="ru"/></option>
                                            <option value="en_EN"><fmt:message key="en"/></option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>

                        </td>
                    </form>
                </tr>
            </table>
        </div>
    </div>
</nav>
</body>
</html>
