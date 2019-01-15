<%@page  language = "java"  contentType = "text/html; charset = UTF-8"  pageEncoding = "UTF-8" isELIgnored = "false"%>
<%@taglib  uri = "http://java.sun.com/jsp/jstl/core"  prefix = "c"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>The doors</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <script type="text/javascript">
        var socket = new WebSocket("ws://localhost:8888/check");

        socket.onmessage = function(event) {
          var light = document.getElementById("light");
          light.value = event.data;
        };

        socket.onerror = function(error) {
          alert("Ошибка " + error.message);
        };

        function send() {
          var s = "${door.name}";
          socket.send(s);
        }
    </script>
    <body>
        <div class="container">
            <c:if test="${door.light}">
                <c:set var="status" scope="session" value="The light is ON"/>
            </c:if>
            <c:if test="${door.light == false}">
                <c:set var="status" scope="session" value="The light is OFF"/>
            </c:if>
            <c:set var="action" scope="session" value="Change state"/>
            <h2>Room name: ${door.name}</h2><br/>
            <input type="text" value="${status}" id="light" disabled><br/>
            <input type="button" onclick="send()" value="${action}" id="send"/><br/>
            <a href="/">Go home</a>
        </div>
    </body>
</html>
