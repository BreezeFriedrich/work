<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/12/4
  Time: 17:34
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
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>
    <!--<link rel="stylesheet" href="resources/css/picSrc.css"/>-->
    <style>
        img.auto-zoom-1 {
            width: 1rem;
            height: 1rem;
            max-width: 100%;
            max-height: 100%;
        }
        img.auto-zoom-2 {
            width: 2rem;
            height: 2rem;
            max-width: 100%;
            max-height: 100%;
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
        img.auto-zoom-5 {
            width: 5rem;
            height: 5rem;
            max-width: 100%;
            max-height: 100%;
        }
        .row.pad-left {
            padding-left: 1rem;
        }
        .card .card-footer {
            /*上下 ,左右*/
            padding: 0 0.75rem;
        }

        /*徽章badge*/
        .badge {
            display: inline-block;
            min-width: 10px;
            padding: 3px 7px;
            font-size: 12px;
            font-weight: bold;
            line-height: 1;
            color: #fff;
            text-align: center;
            white-space: nowrap;
            /*vertical-align: baseline;*/
            background-color: #999;
            border-radius: 10px;
        }
        .badge:empty {
            display: none;
        }
    </style>
</head>
<body>

<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-menu pull-left open-panel" data-panel='#panel-left-menu'></a>
            <h1 class="title">主页</h1>
        </header>

        <!-- 工具栏 -->
        <nav class="bar bar-tab">
            <a class="tab-item external active" href="jsp/main2.jsp">
                <span class="icon icon-home"></span>
                <span class="tab-label">首页</span>
            </a>
            <a class="tab-item external" href="jsp/gateway/gateway_addGateway.jsp">
                <span class="icon icon-edit"></span>
                <span class="tab-label">网关</span>
            </a>
            <a class="tab-item external" href="jsp/record/record.jsp">
                <span class="icon icon-search"></span>
                <span class="tab-label">记录</span>
            </a>
            <a class="tab-item external" href="jsp/alert.jsp">
                <span class="icon icon-phone"></span>
                <span class="tab-label">告警<span class="badge" style="vertical-align: -50%;"></span></span>
            </a>
            <a class="tab-item external" href="jsp/setting.jsp">
                <span class="icon icon-settings"></span>
                <span class="tab-label">设置</span>
            </a>
        </nav>

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="list-block cards-list" id="cardList">
                <!--<ul id="UL_gateway"></ul>-->
                <!--
                <ul id="devices">
                  <li class="card">
                    <div class="card-header" style="background-color: #FAF1FC;">天字号</div>
                    <div class="card-content" style="background-color: #EEFFFF;">
                      <div class="card-content-inner">
                          <img class="auto-zoom-5" src="resources/img/gateway.png" />
                      </div>
                    </div>
                    <div class="card-footer" style="background-color: #F3FAF3;">
                      <p style="color: #00B83F;">工作正常</p><a href="#" class="icon icon-down"></a>
                    </div>
                  </li>
                  <li class="card">
                    <div class="card-header" style="background-color: #FAF1FC;">地字号</div>
                    <div class="card-content" style="background-color: #EEFFFF;">
                      <div class="card-content-inner">
                          <img class="auto-zoom-5" src="resources/img/gateway.png" />
                      </div>
                    </div>
                    <div class="card-footer" style="background-color: #FAF1FC;">
                      <p style="color: #00B83F;">工作正常</p><a href="#" class="icon icon-down"></a>
                    </div>
                  </li>
                  <li class="card">
                    <div class="card-header" style="background-color: #FAF1FC;">玄字号</div>
                    <div class="card-content" style="background-color: #FFEEEE;">
                      <div class="card-content-inner">
                          <img class="auto-zoom-5" src="resources/img/gateway.png" />
                      </div>
                    </div>
                    <div class="card-footer" style="background-color: #FAF1FC;">
                      <p style="color: lightcoral;">异常</p><a href="#" class="icon icon-down"></a>
                    </div>
                  </li>
                </ul>
               -->
            </div>
        </div>
    </div>

</div>

<div class="panel panel-left panel-reveal" id="panel-left-menu">
    <div class="content-block content-padded grid-demo">
        <div style="height: 1rem;"></div>
        <!--
        <div class="row">
            <div class="col-50">
                <img class="auto-zoom-5" src="resources/img/username.png"/>
            </div>
            <div class="col-50">
                <p><span>北极星</span></p>
                <p><span>18255683932</span></p>
            </div>
        </div>
        -->
        <div>
            <img class="auto-zoom-5" src="resources/img/username.png" alt="用户图像"/>
        </div>
        <div>
            <p>
                <%--<span>18255683932</span>--%>
                <s:if test="#attr.ownerPhoneNumber!=null">
                    <s:property value="#attr.ownerPhoneNumber" />
                </s:if>
                <s:else>
                    获取手机号码失败
                </s:else>
            </p>
        </div>
        <div style="height: 2rem;"></div>
        <div class="row pad-left">
            <div class="col-20">
                <img class='auto-zoom-1' src="resources/img/add_gateway.png"/>
            </div>
            <div class="col-80" id="div_addGateway">
                添加网关
            </div>
        </div>
        <div style="height: 0.7rem;"></div>
        <div class="row pad-left">
            <div class="col-20">
                <img class='auto-zoom-1' src="resources/img/statistics.png"/>
            </div>
            <div class="col-80" id="div_statistics">
                查询统计
            </div>
        </div>
        <div style="height: 0.7rem;"></div>
        <div class="row pad-left">
            <div class="col-20">
                <img class='auto-zoom-1' src="resources/img/alert.png"/>
            </div>
            <div class="col-80">
                异常警示
            </div>
        </div>
        <div style="height: 0.7rem;"></div>
        <div class="row pad-left">
            <div class="col-20">
                <img class='auto-zoom-1' src="resources/img/system_configure.png"/>
            </div>
            <div class="col-80">
                系统设置
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="INPUT_hidden" value="${ownerPhoneNumber}"/>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/main2.js?ver=3' charset='utf-8'></script>
</body>
</html>

<html>