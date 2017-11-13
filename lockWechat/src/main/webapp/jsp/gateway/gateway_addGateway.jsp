<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  WechatUser: admin
  Date: 2017/10/18
  Time: 15:27
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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <style>
        .list-block {
            margin: 1.5rem 0;
            min-height: 2.0rem;
            line-height: 2.0rem;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>

<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">增加网关</h1>
        </header>

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">网关编码</div>
                                <div class="item-input">
                                    <input type="text"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <a href="javascript:void(0);" onclick="addGateway()" class="button button-big button-fill button-success">添加网关</a>
            </div>
            <!--
            <div class="content-block" id="div_iframe">
                <div class="content-block-title">网关内网ip</div>
                <div class="card">
                    <div class="card-content">
                        <div class="card-content-inner" id="expande_iframe">
                            <%--<iframe frameborder="0" scrolling="no" class="external"></iframe>--%>
                        </div>
                    </div>
                </div>
            </div>
            -->
        </div>
    </div>
</div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/fastclick.js'></script>
<script type='text/javascript' src='resources/js/gateway_addGateway.js?ver=2' charset='utf-8'></script>
</body>
</html>