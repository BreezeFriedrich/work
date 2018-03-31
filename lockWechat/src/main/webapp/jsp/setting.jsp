<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        .button-big {margin: 0 0.9rem;}
    </style>
</head>
<body>
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">用户设置</h1>
        </header>
        <jsp:include page="/jsp/nav.jsp"/>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="content-block-title">帐号相关</div>
            <div class="list-block">
                <ul>
                    <li class="item-content item-link">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner" id="link_modifyNickname">
                            <div class="item-title">
                                <%--<img class="auto-zoom-1" src="resources/img/password_set.png" alt="用户昵称"/>--%>
                                <span class="icon icon-edit"></span>
                                修改用户昵称
                            </div>
                            <div class="item-after"></div>
                        </div>
                    </li>
                </ul>
                <ul>
                    <li class="item-content item-link">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner" id="link_modifyPassword">
                            <div class="item-title">
                                <%--<img class="auto-zoom-1" src="resources/img/password_set.png" alt="登录密码"/>--%>
                                <span class="icon icon-edit"></span>
                                修改登录密码
                            </div>
                            <div class="item-after"></div>
                        </div>
                    </li>
                </ul>
                <ul>
                    <li class="item-content item-link">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner" id="link_setAuthPassword">
                            <div class="item-title">
                                <%--<img class="auto-zoom-1" src="resources/img/password_set.png" alt="授权密码"/>--%>
                                <span class="icon icon-edit"></span>
                                设置授权密码
                            </div>
                            <div class="item-after"></div>
                        </div>
                    </li>
                </ul>
                <ul>
                    <li class="item-content item-link">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner" id="link_roomManage">
                            <div class="item-title">
                                <%--<img class="auto-zoom-1" src="resources/img/password_set.png" alt="房间设置"/>--%>
                                <span class="icon icon-edit"></span>
                                房间设置
                            </div>
                            <div class="item-after"></div>
                        </div>
                    </li>
                </ul>
            </div>
            <!--
            <div class="content-block">
                <a href="javascript:void(0);" onclick="modifyNickname()" class="button button-big button-fill button-success">修改用户昵称</a>
            </div>
            <div class="content-block">
                <a href="javascript:void(0);" onclick="modifyPassword()" class="button button-big button-fill button-success">修改登录密码</a>
            </div>
            <div class="content-block">
                <a href="javascript:void(0);" onclick="setAuthPassword()" class="button button-big button-fill button-success">设置授权密码</a>
            </div>
            -->
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/setting.js?ver=6' charset='utf-8'></script>
</body>
</html>