<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/12/16
  Time: 11:14
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>亿数智能门锁</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>
    <link rel="stylesheet" href="resources/css/mescroll.min.css"/>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            -webkit-touch-callout:none;
            -webkit-user-select:none;
            -webkit-tap-highlight-color:transparent;
        }
        body{background-color: white}
        ul{list-style-type: none}
        a {text-decoration: none;color: #6d6d72;}

        /*列表*/
        .mescroll{
            position: fixed;
            /*top: 44px;*/
            top: 170px;
            bottom: 0;
            height: auto;
        }
        /*展示上拉加载的数据列表*/
        .data-list li{
            /*background-color: lightyellow;*/
            background-color: white;
            position: relative;
            padding: 10px 6px 10px 6px;
            border-bottom: 1px solid #eee;
        }
        /*表格卡片样式*/
        .pd{
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
            height: 80px;
        }
        .pd>div{
            float: left;
        }
        .pd p{
            margin: 0;
            overflow: hidden;
        }
        img{
            vertical-align: middle;
        }
        .pd-left{
            width: 48%;
            font-size: 14px;
            line-height: 25px;
        }
        .pd-right{
            width: 48%;
            font-size: 14px;
            line-height: 20px;
        }
        .pd-left img{
            max-height: 25px;
            max-width: 25px;
        }
        .pd-right img{
            max-height: 20px;
            max-width: 20px;
        }

        .row-header,row-line{
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
            font-size: 15px;
            height: 30px;
            line-height: 30px;
        }
        .row-header img,.row-line img,.a-id img{
            max-height: 30px;
            max-width: 30px;
        }
        .row-expand{
            overflow: hidden;
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
            font-size: 15px;
            line-height: 30px;
        }
        img.auto-zoom-3 {
            width: 3rem;
            height: 3rem;
            max-width: 100%;
            max-height: 100%;
        }
        img.auto-zoom-4 {
            width: 4rem;
            height: 4rem;
            max-width: 100%;
            max-height: 100%;
        }
        .card .card-footer {
            /*上下 ,左右*/
            padding: 0 0.75rem;
        }
    </style>
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">告警</h1>
        </header>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <%--<div class="list-block cards-list" id="cardList"></div>--%>
            <div class="list-block media-list tjrzxx">
                <ul></ul>
            </div>
        </div>
    </div>
</div>
<%--<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>--%>
<script type="text/javascript" src="resources/js/zepto.min.js"></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/mescroll.min.js'></script>
<script type='text/javascript' src='resources/js/alert.js?v=1' charset='utf-8'></script>
</body>
</html>