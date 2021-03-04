<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <title>Error Pge</title>
    </head>
    <body>
        <div class="errorElement">
            <h2>Error was occured!</h2>
            <h3>${errorDataBase}</h3>
        </div>
        <div class="errorPageElement">
            <form name="logout" method="POST" action="controller">
                <input type="hidden" name="command" value="logout" />
                <input type="submit" value="Back to Login page" />
            </form>
        </div>
    </body>
</html>
