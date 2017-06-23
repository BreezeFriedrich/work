<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/7
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>亿数刷卡管理中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.0/css/bootstrap.min.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="../../resources/styles/main_plain.css" />
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/cookie.js"></script>
    <script type="text/javascript" src="../../resources/scripts/main_plain.js?ver=1"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-lg-12">
            <div class="row-fluid">
                <div class="col-lg-4 col-lg-4">
                    <h5>
                        <strong>刷卡管理中心</strong>
                    </h5>
                </div>
                <div class="col-lg-4">
                    <span id="loginStatus"><shiro:principal property="nickname"/></span>
                    <div class="btn-group">
                        <button class="btn btn-danger" type="button" id="safetylogout"></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-lg-2">
            <ul class="nav nav-list">
                <li class="nav-header">
                    列表标题
                </li>
                <li class="active">
                    <a href="#">首页</a>
                </li>
                <li>
                    <a href="#">库</a>
                </li>
                <li>
                    <a href="#">应用</a>
                </li>
                <li class="nav-header">
                    功能列表
                </li>
                <li>
                    <a href="#">资料</a>
                </li>
                <li>
                    <a href="#">设置</a>
                </li>
                <li class="divider">
                </li>
                <li>
                    <a href="#">帮助</a>
                </li>
            </ul>
        </div>
        <div class="col-lg-1">
        </div>
        <div class="col-lg-9">
            <div class="tabbable" id="tabs-262418">
                <ul class="nav nav-tabs">
                    <li>
                        <a data-toggle="tab" href="#panel-355199">第一部分</a>
                    </li>
                    <li class="active">
                        <a data-toggle="tab" href="#panel-455156">第二部</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane" id="panel-355199">
                        <div class="alert">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>
                                提示!
                            </h4> <strong>警告!</strong> 请注意你的个人隐私安全.
                        </div>
                        <form class="form-horizontal">
                            <div class="control-group">
                                <label class="control-label" for="inputEmail">邮箱</label>
                                <div class="controls">
                                    <input id="inputEmail" type="text" />
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="inputPassword">密码</label>
                                <div class="controls">
                                    <input id="inputPassword" type="password" />
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <label class="checkbox"><input type="checkbox" /> Remember me</label> <button type="submit" class="btn">登陆</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="tab-pane active" id="panel-455156">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-lg-12">
            <address> <strong>南京亿数信息科技有限公司</strong><br /> <abbr title="Phone">TEL:</abbr> (123) 456-7890</address>
        </div>
    </div>
</div>
</body>
</html>