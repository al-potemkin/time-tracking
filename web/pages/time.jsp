<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>

    <script type="text/javascript">
        var myVar=setInterval(function () {myTimer()}, 1000);
        var counter = 0;
        function myTimer() {
            var date = new Date();
            document.getElementById("demo").innerHTML = date.toISOString();
        }
    </script>

</head>
<body>
<span id="demo"></span>
</body>
</html>
