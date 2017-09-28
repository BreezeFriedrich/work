<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/8/21
  Time: 9:19
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

        <div class="row" style="background-color: #fff;min-height: 50px; margin-top: 50px;">
            <div class="col-sm-offset-4 col-sm-8" style="padding-left: 6px;padding-right: 6px;padding-top: 6px;background-color: #fff;">
                亿数门锁管理
            </div>
            <div class="col-sm-12" style="height: 80px;"></div>
            <!--
            <div class="col-xs-offset-2 col-xs-10">
                <a href="lang.action?request_locale=zh_CN">中文</a>&nbsp
                <a href="lang.action?request_locale=en_US">英文</a>
            </div>
            <div class="col-xs-offset-2 col-xs-9">
                <form action="account/loginAccount.action" method="post">
                    用户名：<input type="text" name="loginPara.username"/><br>
                    密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:<input type="password" name="loginPara.password"/><br>
                    <table>
                        <tr>
                            <td><input type="submit" value="注册"/></td>
                            <td><input type="reset" value="重置" ></td>
                        </tr>
                    </table>
                </form>
                <s:fielderror />
            </div>
            -->
            <form class="form-horizontal" role="form" method="post" action="<%=basePath%>account/loginAccount.action">
                <div class="form-group">
                    <label for="inputUserName" class="col-sm-4 control-label">用户名</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="inputUserName" placeholder="请输入用户名" name="loginPara.username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-sm-4 control-label">密码</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="inputPassword" placeholder="请输入密码" name="loginPara.password">
                    </div>
                </div>
                <div><s:property value="authenticateErrMsg" /> </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-8">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>
        </div>

    </div>
</body>
</html>
