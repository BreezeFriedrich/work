<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  WechatUser: admin
  Date: 2017/9/14
  Time: 11:52
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>门锁管理微信客户端-管理主页</title>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,wechatUser-scalable=no" />

    <link rel="stylesheet" href="<%=basePath%>/resources/bootstrap-3.3.0/css/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="<%=basePath%>/resources/css/common.css" type="text/css" />
    <!--[if lt IE 9]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=koQjIQZqZMn7HN61ELn5jsFU"></script>

    <script type="text/javascript" src="<%=basePath%>/resources/js/util/cookie.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/main.js"></script>
    <script type="text/javascript">
        $(function(){
            var bodyHeight=$(window).height();
            $('body').height(bodyHeight);
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-4"></div>
    </div>
    <div class="row">
    </div>
    <div class="row">
    </div>
</div>
</body>
</html>