<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/8/21
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>门锁管理微信客户端登录</title>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

    <link rel="stylesheet" href="<%=basePath%>/resources/bootstrap-3.3.0/css/bootstrap.min.css" type="text/css" />
    <!--[if lt IE 9]>
    <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
    <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=koQjIQZqZMn7HN61ELn5jsFU"></script>

    <script type="text/javascript" src="<%=basePath%>/resources/js/util/cookie.js"></script>
    <script type="text/javascript" src="<%=basePath%>/resources/js/login.js"></script>
</head>
<body style="background-color: #f9f9f9;">
    <div class="container-fluid">

        <div class="row" style="background-color: #fff;min-height: 50px; margin-top: 10px;">
            <div class="col-xs-offset-4 col-xs-4 col-xs-offset-4" style="padding-left: 6px;padding-right: 6px;padding-top: 6px;background-color: #fff;">
                亿数门锁管理
            </div>
            <div class="col-xs-offset-4 col-xs-4 col-xs-offset-4" style="padding-left: 6px;padding-right: 6px;padding-top: 6px;background-color: #fff;">

            </div>
            <form class="form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/account/login.action">
                <div class="form-group">
                    <label for="inputUserName" class="col-xs-2 control-label">用户名</label>
                    <div class="col-xs-10">
                        <input type="text" class="form-control" id="inputUserName" placeholder="请输入用户名" name="username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-xs-2 control-label">密码</label>
                    <div class="col-xs-10">
                        <input type="password" class="form-control" id="inputPassword" placeholder="请输入密码" name="password">
                    </div>
                </div>
                <div><s:property value="authenticateErrMsg" /> </div>
                <div class="form-group">
                    <div class="col-xs-offset-2 col-xs-10">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>
        </div>

    </div>
</body>
</html>
