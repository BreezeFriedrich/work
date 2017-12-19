<%--
  User: admin
  Date: 2017/12/19
  Time: 9:26
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
    <title>error</title>
</head>
<body>
<div>
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