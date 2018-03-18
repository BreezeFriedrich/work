<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Recent Spittles</h1>

<c:forEach items="${spittleList}" var="spillte">
    <li id="spittle_<c:out value="spittle.id" />">
        <div class="spittleMessage">
            <c:out value="${spillte.message}" />
        </div>
        <div>
            <span class="spittleTime"><c:out value="spittle.time" /></span>
            <span class="spittleLocation">
                (<c:out value="${spittle.latitude}" />,<c:out value="${spittle.longitude}" />)
            </span>
        </div>
    </li>
</c:forEach>
</body>
</html>
