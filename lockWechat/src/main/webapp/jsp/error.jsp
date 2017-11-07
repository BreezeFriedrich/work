<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/11/7
  Time: 16:22
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>错误页面</title>
    <meta charset="utf-8">
</head>
<%--<body style="background-image:url(<%=basePath%>images/123.jpg); background-repeat:no-repeat;">--%>
<body>
<div>
    <img src="<%=basePath%>/images/img001.png">
    <p>Exception.jsp</p>
    <h2>错误信息</h2>
    <p>
        <s:if test="#attr.errMsg!=null">
            <s:property value="#attr.errMsg" />
        </s:if>
    </p>
    <h2>调试Struts2</h2>
    <p><s debug/></p>
</div>
</body>
</html>