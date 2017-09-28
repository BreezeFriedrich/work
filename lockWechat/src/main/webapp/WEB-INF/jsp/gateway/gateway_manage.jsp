<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/9/27
  Time: 14:07
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
    <title>gateway_manage</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
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
<!--
<div style="display: none;">
    <s:property value=""></s:property>
</div>
-->

<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <!--如果用SUI组件直接href='main.jsp',不会加载main.js-->
            <!--
            关于window.location.href的URL路径
            ${pageContext.request.contextPath}会访问jsp的内置对象pageContext 本例中pageContext.request.contextPath代表'lockWechat/',即绝对路径指向webapp目录.
            -->
            <a class="icon icon-home pull-left" href="javascript:window.location.href='${pageContext.request.contextPath}/jsp/main.jsp';"></a>
            <h1 class="title">网关管理</h1>
            <!-- 直接用相对路径也可以,是否与<base href="<%=basePath%>">有关? -->
            <a class="icon icon-edit pull-right" href="javascript:void(0);" onclick="javascript:window.location.href='jsp/gateway/gateway_property.jsp?ownerPhoneNumber='+ownerPhoneNumber+'&specificGatewayCode='+specificGatewayCode;"></a>
        </header>

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
                                <img class="auto-zoom-1" src="resources/img/connectLock.png" alt=""/>
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
                                    <img class='auto-zoom-3' src='resources/img/lock.png' />
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
                                    <img class='auto-zoom-3' src='resources/img/lock.png' />
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
                                    <img class='auto-zoom-3' src='resources/img/lock.png' />
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
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/gateway_manage.js' charset='utf-8'></script>
<!-- 默认必须要执行$.init(),实际业务里一般不会在HTML文档里执行，通常是在业务页面代码的最后执行 -->
<script>
    $(function(){
        $.init();
    });
</script>
</body>
</html>