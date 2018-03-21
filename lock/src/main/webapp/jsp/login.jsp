<%--
  User: admin
  Date: 2017/12/18
  Time: 18:44
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" conteznt="">
    <link rel="shortcut icon" href="resources/images/favicon.png" />
    <title>漫行智能锁管理系统</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css" />

    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css" />
    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="resources/css/style.css" />
    <link rel="stylesheet" href="resources/css/index.css" />
</head>

<body class="texture">

<div id="cl-wrapper" class="login-container">

    <div class="middle-login">
        <div class="block-flat">
            <div class="header">
                <h3 class="text-center"><img class="logo-img" src="resources/images/logo2.png" alt="logo"/></h3>
            </div>
            <div>
                <!--
                <form id="defaultForm" method="post" class="form-horizontal">
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-4">
                            <input type="text" id="username" name="username" class="form-control" placeholder="用户名" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-4">
                            <input type="password" id="password" name="password" class="form-control" placeholder="新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-2 control-label">确认密码</label>
                        <div class="col-sm-4">
                            <input type="password" id="confirmpwd" name="confirmpwd" class="form-control" placeholder="确认密码">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <button type="button" class="btn btn-primary" id="saveBtn" onclick="doRemindPwd()">Sign up</button>
                        </div>
                    </div>
                </form>
                -->

                <form class="form-horizontal" method="post" action="user/login.do" style="margin-bottom: 0px !important;">
                    <div class="content">
                        <h4 class="title">用户登录</h4>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                    <input type="text" placeholder="用户名" id="username" name="username" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                    <input type="password" placeholder="密码" id="password" name="password" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="foot">
                        <button class="btn btn-primary btn-dlan" data-dismiss="modal" type="submit">登  录</button>
                    </div>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
        <div class="text-center out-links"><a href="http://www.yishutech.com">&copy;2015-2016 南京亿数信息科技有限公司 版权所有</a></div>
    </div>

</div>

<script type="text/javascript" src="resources/plugin/jquery.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.flot/jquery.flot.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.flot/jquery.flot.labels.js"></script>
</body>

</html>
