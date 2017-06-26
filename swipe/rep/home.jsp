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
    <title>亿数刷卡管理中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="../src/main/webapp/resources/bootstrap-3.3.0/css/bootstrap.min.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="home.css" />
    <script type="text/javascript" src="../src/main/webapp/resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../src/main/webapp/resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../src/main/webapp/resources/scripts/component/cookie.js"></script>
    <script type="text/javascript" src="../src/main/webapp/resources/scripts/main.js?ver=1"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row" style="background:#95b8e7;">
        <div class="col-lg-2">
            <a>
                <img class="brand-logo" src="" alt="Logo">
            </a>
        </div>
        <div class="col-lg-4 col-lg-offset-2">
            <h2>亿数刷卡管理中心</h2>
        </div>
        <div class="col-lg-2">
            <span id="loginStatus"><shiro:principal property="nickname"/></span>
        </div>
        <div class="col-lg-2">
            <button class="btn btn-danger" id="safetylogout">安全退出</button>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-2">
            <div class="leftsidebar">
                <%--<div class="line"></div>--%>
                <dl class="custom">
                    <dt style="background-image: url(../src/main/webapp/resources/styles/images/left/custom.png)">用户管理<img src="../src/main/webapp/resources/styles/images/left/select_xl01.png"></dt>
                    <shiro:hasPermission name="/admin/user/list">
                        <dd class="first_dd">
                            <a onclick=iframe("admin/user/list")>用户列表</a>
                        </dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/admin/role/list">
                        <dd>
                            <a onclick=iframe("admin/role/list")>角色管理</a>
                        </dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/admin/resource/list">
                        <dd>
                            <a onclick=iframe("admin/resource/list")>资源管理</a>
                        </dd>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/database/**">
                        <dd>
                            <a onclick=iframe("database/clear")>数据清理</a>
                        </dd>
                    </shiro:hasPermission>
                </dl>
                <dl class="deviceManage">
                    <dt style="background-image: url(../src/main/webapp/resources/styles/images/left/device_manage.png)">模块状态<img src="../src/main/webapp/resources/styles/images/left/select_xl01.png"></dt>
                    <dd class="first_dd">
                        <a onclick=iframe("dispatcher/modulestatus_table.jsp")>模块状态表</a>
                    </dd>
                </dl>
                <dl class="statistics">
                    <dt style="background-image: url(../src/main/webapp/resources/styles/images/left/statistics.png)">刷卡记录<img src="../src/main/webapp/resources/styles/images/left/select_xl01.png"></dt>
                    <dd class="first_dd">
                        <a onclick=iframe("dispatcher/swiperecord_table.jsp")>总记录表</a>
                    </dd>
                    <dd>
                        <a onclick=iframe("dispatcher/swiperecord_chart.jsp")>成功率饼图</a>
                    </dd>
                    <dd>
                        <a onclick=iframe("dispatcher/swiperecord_show.jsp")>成功率柱状图</a>
                    </dd>
                    <dd>
                        <a onclick=iframe("dispatcher/sAnalyseByInterval.jsp")>失败率-频度-并发量</a>
                    </dd>
                    <dd>
                        <a onclick=iframe("dispatcher/sAnalyseByDay.jsp")>单日分析</a>
                    </dd>
                </dl>
                <dl class="statistics">
                    <dt style="background-image: url(../src/main/webapp/resources/styles/images/left/statistics.png)">测试<img src="../src/main/webapp/resources/styles/images/left/select_xl01.png"></dt>
                    <dd class="first_dd">
                        <a onclick=iframe("dispatcher/test_datatables.jsp")>DataTables</a>
                    </dd>
                </dl>
            </div>
        </div>
        <div class="col-lg-10">
            <iframe id="iframe" frameborder="0" scrolling="no" width="100%" border="0" marginwidth="0" marginheight=" 0" allowtransparency="yes" src="dispatcher/analyse_byday.jsp"></iframe>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">YISHUTECH</div>
    </div>
</div>
</body>
</html>
