<%--
  User: admin
  Date: 2017/12/20
  Time: 16:29
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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>
    <!-- Bootstrap core CSS -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:300,200,100' rel='stylesheet' type='text/css'>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="page/css/font-awesome.4.6.0.css">
    <link rel="stylesheet" type="text/css" href="page/css/font-awesome.min.css">
    <!-- Custom styles for this template -->

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="page/css/font-awesome.min.css">
    <!-- Custom styles for this template -->

    <!-- Bootstrap core CSS -->
    <link href="resources/css/bootstrap.css" rel="stylesheet">
    <link href="resources/css/style.css" rel="stylesheet" />
    <link href="resources/css/index.css" rel="stylesheet" />

    <!-- DateRange -->
    <link rel="stylesheet" type="text/css" href="page/js/bootstrap.daterangepicker/daterangepicker-bs3.css" />

    <!-- 弹出-->
    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" />

    <!-- table-->
    <link rel="stylesheet" href="css/fixed-table.css" />
    <script src="resources/plugin/jquery.min.js"></script>
    <script src="js/fixed-table.js"></script>
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 90%;}
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 90%;}
    </style>

    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <script language="javascript" type="text/javascript">
        <!--
        //获得当前时间,刻度为一千分一秒
        var initializationTime=(new Date()).getTime();
        function showLeftTime(){
            var now=new Date();
            var year=now.getFullYear();
            var week=now.getDay();
            var month=now.getMonth();
            var day=now.getDate();
            var hours=now.getHours();
            var minutes=now.getMinutes();
            var seconds=now.getSeconds();
            document.all.show3.innerHTML=year+"年"+month+"月"+day+"日";
            //一秒刷新一次显示时间
            var timeID=setTimeout(showLeftTime,1000);
        }
        //-->
    </script>
</head>
<body onload="showLeftTime()">
<!-- header -->
<div id="head-nav" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><button id="sidebar-collapse" class="button-open" style=""></button></li>
                <li class="active"> <a href="#"> 设备管理 </a></li>
                <li><a href="#">查询与统计</a></li>
                <li ><a href="#" >房  态 </a></li>
                <li class=""><a href="#" >分级管理</a></li>
                <!--
                <li ><a href="#">设置</a></li>
                <li ><a href="#"><i class="inco-exit"></i>退出系统</a></li>
                -->
            </ul>
            <ul class="nav navbar-nav navbar-right user-nav">
                <li class="dropdown profile_menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img alt="Avatar" src="resources/images/avatar2.jpg" />张三<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">设置</a></li>
                        <li><a href="#">退出系统</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--header  end-->

<div id="cl-wrapper" class="fixed-menu">
    <div class="container-fluid "  id="pcont" >
        <div class="page-head"><img src="resources/images/lb.png" class="inco-lb"><h3>业主管理房间</h3></div>
        <div class="col-sm-9 table1">
            <div class="block-flat table0-top gateway-table">
                <div class="content">
                    <table>
                        <tr>
                            <th style="width:10%;"  valign="middle">网关1</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁8
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁9
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁10
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th>网关2</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th>网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th>网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th>网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <th >网关3</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"   data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <!-- 添加密码开锁授权  -->
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-password">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>添加密码开锁授权 </h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="email" class="form-control" placeholder="请输入密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <!--直接选择区域，每添加一个时间选择，js需要增加一个-->
                                                <fieldset>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <div class="input-prepend ">
                                                                <input type="text" name="reservation" id="reservationtime2" class="form-control" value="02/01/2014 1:00 PM - 02/05/2014 2:30 PM"  class="span4"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-12" >
                                                <button type="submit" class="btn btn-primary">确认修改</button>
                                                <button class="btn btn-default  md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="md-overlay"></div>
                    <!-- 添加密码开锁授权   end -->

                    <!-- 身份证授权 -->
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-identity">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>添加身份证开锁授权</h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="email" class="form-control" placeholder="地区名称">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="password" class="form-control"  placeholder="地址">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <fieldset>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <div class="input-prepend ">
                                                                <input type="text" name="reservation" id="reservationtime" class="form-control" value="02/01/2017 1:00 PM - 02/05/2017 2:30 PM"  class="span4"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </fieldset>

                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-10" >
                                                <button type="submit" class="btn btn-primary">提交授权</button>
                                                <button class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="md-overlay"></div>
                    <!-- 身份证授权  end -->

                    <!-- 开锁信息  -->
                    <div class="md-modal colored-header custom-width md-effect-9" id="reply-unlocking">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>开锁授权信息 </h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <div class="tc-table">
                                        <div class="tc-table-th">
                                            <div class="col-sm-2">姓名</div><div class="col-sm-3">身份证</div><div class="col-sm-2">密码</div><div class="col-sm-5">预定时间段</div>
                                        </div>
                                        <div class="tc-table-td">
                                            <div class="col-sm-2">陆帧</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-2">yishutech</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                        </div>
                                        <div class="tc-table-td2">
                                            <div class="col-sm-2">陆帧</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-2">yishutech</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                        </div>
                                    </div>
                                    <div class="form-group" >
                                        <div class="col-sm-10" >
                                            <button type="submit" class="btn btn-primary">提交授权</button>
                                            <button type="submit" class="btn btn-primary">取消授权</button>
                                            <button class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="md-overlay"></div>
                    <!-- 开锁信息   end -->

                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
    <div class="footer">2015-2016  南京亿数信息科技有限公司 版权所有</div>
    <div class="clearfix"></div>
</div>

<script src="resources/plugin/jquery.min.js"></script>
<script src="resources/plugin/jquery.js"></script>
<script src="resources/plugin/jquery.select2/select2.min.js" type="text/javascript"></script>
<script src="page/js/bootstrap.slider/js/bootstrap-slider.js" type="text/javascript"></script>
<script type="text/javascript" src="page/js/bootstrap.daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>
<script src="resources/plugin/behaviour/voice-commands.js"></script>
<script src="resources/plugin/jquery.parsley/dist/parsley.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<script src="resources/plugin/jquery.ui/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

<script type="text/javascript" src="page/js/bootstrap.daterangepicker/daterangepicker.js"></script>

<script src="js/jquery.contextmenu.r2.js"></script>
<script>
    //所有class为demo1的span标签都会绑定此右键菜单
    $('div.rightclick').contextMenu('myMenu1',{

    });
</script>

<script type="text/javascript">
    $(document).ready(function(){
        //initialize the javascript
        App.init();

        $('#reservation').daterangepicker();
        $('#reservationtime').daterangepicker({
            timePicker: true,
            timePickerIncrement: 30,
            format: 'MM/DD/YYYY h:mm A'
        });
        $('#reservationtime2').daterangepicker({
            timePicker: true,
            timePickerIncrement: 30,
            format: 'MM/DD/YYYY h:mm A'
        });

        var cb = function(start, end) {
            $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
            alert("Callback has fired: [" + start.format('MMMM D, YYYY') + " to " + end.format('MMMM D, YYYY') + "]");
        }

        var optionSet1 = {
            startDate: moment().subtract('days', 29),
            endDate: moment(),
            minDate: '01/01/2012',
            maxDate: '12/31/2014',
            dateLimit: { days: 60 },
            showDropdowns: true,
            showWeekNumbers: true,
            timePicker: false,
            timePickerIncrement: 1,
            timePicker12Hour: true,
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                'Last 7 Days': [moment().subtract('days', 6), moment()],
                'Last 30 Days': [moment().subtract('days', 29), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
            },
            opens: 'left',
            buttonClasses: ['btn'],
            applyClass: 'btn-small btn-primary',
            cancelClass: 'btn-small',
            format: 'MM/DD/YYYY',
            separator: ' to ',
            locale: {
                applyLabel: 'Submit',
                cancelLabel: 'Clear',
                fromLabel: 'From',
                toLabel: 'To',
                customRangeLabel: 'Custom',
                daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
                monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                firstDay: 1
            }
        };

        var optionSet2 = {
            startDate: moment().subtract('days', 7),
            endDate: moment(),
            opens: 'left',
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                'Last 7 Days': [moment().subtract('days', 6), moment()],
                'Last 30 Days': [moment().subtract('days', 29), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
            }
        };

        $('#reportrange span').html(moment().subtract('days', 29).format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        $('#reportrange').daterangepicker(optionSet1, cb);
    });
</script>

<!-- Bootstrap core JavaScript ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="resources/plugin/behaviour/voice-commands.js"></script>
<script src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
</body>

</html>