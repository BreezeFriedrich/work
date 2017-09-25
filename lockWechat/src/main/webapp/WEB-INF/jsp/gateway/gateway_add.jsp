<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/9/14
  Time: 11:45
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
    <title>gateway-addGateway</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>
    <!--<link rel="stylesheet" href="css/picSrc.css"/>-->
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
    </style>
</head>
<body>
<div style="display: none;">
    <s:property value=""></s:property>
</div>

<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-home pull-left" href="javascript:window.location.href='main.jsp';"></a>
            <h1 class="title">网关管理</h1>
        </header>

        <!-- 工具栏 -->
        <nav class="bar bar-tab">
            <a class="tab-item external active" href="#">
                <span class="icon icon-home"></span>
                <span class="tab-label">首页</span>
            </a>
            <a class="tab-item external" href="#">
                <span class="icon icon-edit"></span>
                <span class="tab-label">修改网关</span>
            </a>
            <a class="tab-item external" href="#">
                <span class="icon icon-settings"></span>
                <span class="tab-label">设置</span>
            </a>
        </nav>

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="content-block-title">网关详情</div>
            <div class="list-block" id="cardList">
                <ul id="UL_gatewayProperty">
                    <!--
                        <li class="item-content">
                            <div class="item-inner">
                                <div class="item-title">网关名称</div>
                                <div class="item-after">天字号</div>
                            </div>
                        </li>
                        <li class="item-content">
                            <div class="item-inner">
                                <div class="item-title">网关地址</div>
                                <div class="item-after">江苏省.南京市.雨花台区</div>
                            </div>
                        </li>
                        <li class="item-content">
                            <div class="item-inner">
                                <div class="item-title">网关备注</div>
                                <div class="item-after">格林豪泰</div>
                            </div>
                        </li>
                        <li class="item-content">
                            <div class="item-inner">
                                <div class="item-title">网关条码</div>
                                <div class="item-after">888888</div>
                            </div>
                        </li>
                    -->
                </ul>
            </div>
            <div class="list-block">
                <ul>
                    <li class="item-content item-link">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">
                                <img class="auto-zoom-1" src="img/connectLock.png" alt=""/>
                                增加关联门锁
                            </div>
                            <div class="item-after"></div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block-title">已关联门锁</div>
            <div class="list-block cards-list">
                <ul id="UL_theSpecificGateway">
                    <!--
                        <li class='card lock' id='lockLists[x].lockCode' style="margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:0.2rem;">
                            <div class='card-header' style='background-color: #FAF1FC;'>lockLists[x].lockName</div>
                            <div class='card-content' style='background-color: #EEFFFF;'>
                                <div class='card-content-inner'>
                                    <img class='auto-zoom-3' src='img/lock.png' />
                                </div>
                            </div>
                            <div class='card-footer' style='background-color: #F3FAF3;'>
                                <p style='color: #00B83F;'>lockLists[x].lockStatus</p><a href='#' class='icon icon-down'></a>
                            </div>
                        </li>
                        <li class='card lock' id='lockLists[x].lockCode' style="margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:0.2rem;">
                            <div class='card-header' style='background-color: #FAF1FC;'>lockLists[x].lockName</div>
                            <div class='card-content' style='background-color: #EEFFFF;'>
                                <div class='card-content-inner'>
                                    <img class='auto-zoom-3' src='img/lock.png' />
                                </div>
                            </div>
                            <div class='card-footer' style='background-color: #F3FAF3;'>
                                <p style='color: #00B83F;'>lockLists[x].lockStatus</p><a href='#' class='icon icon-down'></a>
                            </div>
                        </li>
                        <li class='card lock' id='lockLists[x].lockCode' style="margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:0.2rem;">
                            <div class='card-header' style='background-color: #FAF1FC;'>lockLists[x].lockName</div>
                            <div class='card-content' style='background-color: #EEFFFF;'>
                                <div class='card-content-inner'>
                                    <img class='auto-zoom-3' src='img/lock.png' />
                                </div>
                            </div>
                            <div class='card-footer' style='background-color: #F3FAF3;'>
                                <p style='color: #00B83F;'>lockLists[x].lockStatus</p><a href='#' class='icon icon-down'></a>
                            </div>
                        </li>
                    -->
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- 其他的单个page内联页（如果有） -->
<div class="page">...</div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/gateway_add.js' charset='utf-8'></script>
<!-- 默认必须要执行$.init(),实际业务里一般不会在HTML文档里执行，通常是在业务页面代码的最后执行 -->
<script>
    $(function(){
        $.init();
    });
</script>
</body>
</html>