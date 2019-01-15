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
    <body>
        <div class="container">
            <form action="/door.html" method="post">
                <select name="door">
                    <option disabled>Choose door</option>
                    <c:forEach var="door" items="${doors}">
                        <option value="${door.name}">${door.name}</option>
                    </c:forEach>
               </select>
               <p><input type="submit" value="Open"></p>
            </form>
        </div>
    </body>
</html>
