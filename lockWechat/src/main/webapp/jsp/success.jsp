<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/9/1
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>success.jsp</p>
<s:property value="#session.user.userName"/>
<s:property value="#session.loginPara.userName"/>
<s:property value="#session.jwtAccessToken"/>s
</body>
</html>
