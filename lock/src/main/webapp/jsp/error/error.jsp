<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page  isELIgnored="false"%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>error</title>
</head>
<body>
<div>
    <p>Exception.jsp</p>
    <h2>错误信息</h2>
    <p>${throwable}</p>
</div>
</body>
</html>