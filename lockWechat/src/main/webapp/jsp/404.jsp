<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>error-code:404</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>

<body style="background-image:url(<%=basePath%>images/123.jpg); background-repeat:no-repeat;">
<div style="width: 210px;height: 181px;margin: 170px auto;">
    <p>404.jsp</p>
    <img src="<%=basePath%>/images/img001.png">
    <p>哎呀……您访问的页面不存在</p>
</div>
</body>
</html>