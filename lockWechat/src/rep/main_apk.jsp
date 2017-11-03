<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/8/22
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>门锁管理微信客户端-管理主页(仿apk)</title>
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
    <script type="text/javascript" src="<%=basePath%>/resources/js/main_apk.js"></script>
    <script type="text/javascript">
        $(function(){
            var bodyHeight=$(window).height();
            $('body').height(bodyHeight);
        });
    </script>
</head>
<body style="background-color: #f9f9f9;">
    <!-- 导航边栏 -->
    <div id="sidebar" style="display: none;position: fixed; top: 0px; left: 0px; z-index: 10;width: 150px;background-color: #66512c">
        <div style="height: 97px;padding-top: 15px;padding-bottom: 15px;">
            <span style="height: 68px;overflow: hidden;">
                <img src="<%=basePath%>/resources/img/usericon.png" alt="用户头像" style="max-height: 65px">
            </span>
            <span>
                <p style="padding-top: 15px;color: #dddddd;font-size: 15px;text-align: center;">${wechatUser.nickname}</p>
                <p style="padding-top: 5px;color: #dddddd;font-size: 12px;text-align: center;">${wechatUser.username}</p>
            </span>
        </div>
        <div href="javascript:window.location.href('gateway_add.jsp')" style="height: 33px;overflow: hidden;">
            <img src="<%=basePath%>/resources/img/add_gateway.png" style="max-height: 25px;padding-left: 15px;">
            <p style="padding-top: 8px;padding-left: 15px;color: #dddddd;font-size: 15px;text-align: center;">添加网关</p>
        </div>
        <div href="javascript:window.location.href('statistics.jsp')" style="height: 33px;overflow: hidden;">
            <img src="<%=basePath%>/resources/img/statistics.png" style="max-height: 25px;padding-left: 15px;">
            <p style="padding-top: 8px;padding-left: 15px;color: #dddddd;font-size: 15px;text-align: center;">查询统计</p>
        </div>
        <div href="javascript:window.location.href('exception_report.jsp')" style="height: 33px;overflow: hidden;">
            <img src="<%=basePath%>/resources/img/alert.png" style="max-height: 25px;padding-left: 15px;">
            <p style="padding-top: 8px;padding-left: 15px;color: #dddddd;font-size: 15px;text-align: center;">异常警示</p>
        </div>
        <div href="javascript:window.location.href('system_configure.jsp')" style="height: 33px;overflow: hidden;">
            <img src="<%=basePath%>/resources/img/system_configure.png" style="max-height: 25px;padding-left: 15px;">
            <p style="padding-top: 8px;padding-left: 15px;color: #dddddd;font-size: 15px;text-align: center;">系统设置</p>
        </div>
    </div>

    <!-- 头部 -->
    <div class="row" style="background-color:rgb(140, 83, 22); height: 36px;">
        <div class="col-xs-4" style="border:1px solid #000;height: 26px;overflow: hidden;"> <!--border--><!--$("#id").css('display','block');-->
            <a href="javascript:;" onclick="javascript:document.getElementById('sidebar').style.display='block';">
                <img src="<%=basePath%>/resources/img/menuitems.png" alt="menuitems" style="max-height: 25px;" >
            </a>
        </div>
        <div class="col-xs-7 col-xs-offset-1" style="padding-top: 9px;">
            <p style="color:#000;font-size: 14px;">我的设备</p>
        </div>
    </div>

    <div class="row" style="height: 1px;background-color: #eee;"></div>

    <!-- 网关列表 -->
    <div class="row" style="background-color: #fff;">
        <c:forEach items="${gatewaylist}" var="gateway">
            <div class="col-xs-12">
                <%--<a class="btn btn-default featvalue" data-featvalue="${characvalue.charav_id}" vid="${characvalue.chara_id}" style="width:100%; height: 25px;padding-top: 3.2222px;font-size: 12px; ">${characvalue.charav_name}</a>--%>
                <div class="row" onclick="manageGateway();" href="javascript:window.location.href('gateway_manage.jsp')" style="height: 90px;">
                    <div class="col-xs-3" style="height: 32px;overflow: hidden;">
                        <img src="<%=basePath%>/resources/img/gateway.png" alt="gateway" style="max-height: 30px;">
                    </div>
                    <div class="col-xs-9">
                        <p style="padding-top: 10px;color: #0f0f0f;font-size: 16px;text-align: center;">${gateway.name}</p>
                    </div>
                    <div class="col-xs-3">
                        <p style="padding-top: 10px;color: #0f0f0f;font-size: 14px;text-align: center;">网关</p>
                    </div>
                    <div class="col-xs-9">
                        <p style="padding-top: 5px;color: #999;font-size: 10px;text-align: center;">${gateway.location}</p>
                        <p style="padding-top: 5px;color: #999;font-size: 10px;text-align: center;">${gateway.nickname}</p>
                    </div>
                    <div class="col-xs-3">
                        <c:if test="${0 == gateway.status}">
                            <p style="padding-top: 10px;color: #3e8f3e;font-size: 14px;text-align: center;">${gateway.status}</p>
                        </c:if>
                        <c:if test="${0 != gateway.status}">
                            <p style="padding-top: 10px;color: #ac2925;font-size: 14px;text-align: center;">${gateway.status}</p>
                        </c:if>
                    </div>
                    <div class="col-xs-3 col-xs-offset-6" style="height: 20px;overflow: hidden;">
                        <a class="expand_down" onclick="javascript:document.getElementById('lockList').style('display',block);">
                            <img src="<%=basePath%>/resources/img/dblArrow_down.png" alt="向下扩展" style="max-height: 18px;">
                        </a>
                    </div>
                </div>

                <!-- 门锁列表 -->
                <div class="row" id="lockList" style="background-color: #fff;">
                    <c:forEach items="${locklist}" var="lock">
                        <div class="col-xs-12">
                            <div class="row" onclick="manageLock();" href="javascript:window.location.href('lock_manage.jsp')" style="height: 70px;">
                                <div class="col-xs-3" style="height: 32px;overflow: hidden;">
                                    <img src="<%=basePath%>/resources/img/lock.png" alt="lock" style="max-height: 30px;">
                                </div>
                                <div class="col-xs-9">
                                    <p style="padding-top: 10px;color: #0f0f0f;font-size: 16px;text-align: center;">${lock.name}</p>
                                </div>
                                <div class="col-xs-3">
                                    <p style="padding-top: 10px;color: #0f0f0f;font-size: 14px;text-align: center;">门锁</p>
                                </div>
                                <div class="col-xs-9">
                                    <p style="padding-top: 5px;color: #999;font-size: 10px;text-align: center;">${lock.location}</p>
                                    <p style="padding-top: 5px;color: #999;font-size: 10px;text-align: center;">${lock.nickname}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>