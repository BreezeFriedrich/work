<%--
  User: admin
  Date: 2017/12/28
  Time: 17:14
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header -->
<div id="head-nav" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><button id="sidebar-collapse" class="button-open" style=""></button></li>
                <li class="nav-left">
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/device/device_manage.jsp');">设备管理</a>
                </li>
                <li>
                    <a href="#">查询与统计</a>
                </li>
                <li class="active">
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/house/house_city.jsp');">房  态</a>
                </li>
                <li >
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/user/redirectHouseStatus.do');">分级管理</a>
                </li>
                <!--
                <li><a href="#">设置</a></li>
                <li><a href="#"><i class="inco-exit"></i>退出系统</a></li>
                -->
            </ul>

            <ul class="nav navbar-nav navbar-right user-nav">
                <li class="dropdown profile_menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img alt="Avatar" src="resources/images/avatar2.jpg" />张三<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/userManage.jsp');">设置</a></li>
                        <li><a href="#">退出系统</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--header end-->