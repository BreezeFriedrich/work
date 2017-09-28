<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/9/27
  Time: 14:20
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>lock_manage</title>
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
        img.auto-zoom-2_2 {
            width: 2.2rem;
            height: 2.2rem;
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

<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <!--如果直接href='main.jsp',不会加载main.js-->
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">门锁管理</h1>
            <a class="icon icon-edit pull-right" href="javascript:void(0);" onclick="javascript:window.location.href='${pageContext.request.contextPath}/jsp/lock/lock_property.jsp?ownerPhoneNumber='+ownerPhoneNumber+'&specificGatewayCode='+specificGatewayCode+'&specificLockCode='+specificLockCode;"></a>
        </header>

        <!-- 工具栏 -->
        <!--
        <nav class="bar bar-tab">
            <a class="tab-item external active" href="#">
                <span class="icon icon-home"></span>
                <span class="tab-label">首页</span>
            </a>
        </nav>
        -->

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="content-block-title">门锁详情</div>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">门锁名称</div>
                                <!--
                                <div class="item-input">
                                    <input type="text"/>
                                </div>
                                -->
                                <div class="item-after">
                                    <p class="property"></p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-email"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">门锁地址</div>
                                <div class="item-after">
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-password"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">门锁备注</div>
                                <div class="item-after">
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-password"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">门锁条码</div>
                                <div class="item-after">
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-password"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">所属网关</div>
                                <div class="item-after">
                                    <p></p>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="list-block">
                <div style="padding: 0.1rem 0.5rem;background-color: #FFFFFF;line-height: 2.2rem;font-size: 1.0rem;">
                    <img class="auto-zoom-2_2" src="resources/img/user_authorize.png" alt="" align="left" style="vertical-align: middle;"/>
                    增加授权用户
                    <img class="auto-zoom-2_2" src="resources/img/down_arrow.png" alt="" align="right" style="vertical-align: middle;"/>
                </div>
                <!--<ul>
                    <li class="item-content item-link">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">
                                <img class="auto-zoom-1" src="resources/img/user_authorize.png" alt=""/>
                                增加授权用户
                            </div>
                            <div class="item-after"></div>
                        </div>
                    </li>
                </ul>-->
                <div style="height: 2rem;"></div>
                <div style="padding: 0.1rem 0.5rem;background-color: #FFFFFF;line-height: 2.2rem;font-size: 1.0rem;">
                    <img class="auto-zoom-2_2" src="resources/img/password_set.png" alt="" align="left" style="vertical-align: middle;"/>
                    门锁密码设置
                    <img class="auto-zoom-2_2" src="resources/img/down_arrow.png" alt="" align="right" style="vertical-align: middle;"/>
                </div>
            </div>
            <div class="content-block-title">已授权用户</div>
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/lock_manage.js' charset='utf-8'></script>
<!-- 默认必须要执行$.init(),实际业务里一般不会在HTML文档里执行，通常是在业务页面代码的最后执行 -->
<script>
    $(function(){
        $.init();
    });
</script>
</body>
</html>