<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>会话过期</title>
</head>
<body>
<script language="JavaScript">
    alert("会话过期，请重新登录。");
    setTimeout(function () {
        window.top.location.href="<%=basePath%>/jsp/login.jsp";
    },2000);
</script>
</body>
</html>