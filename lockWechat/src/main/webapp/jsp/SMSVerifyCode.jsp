<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/11/21
  Time: 15:22
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

    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <style>
        .row.pad-left {
            padding-left: 1rem;
        }
        .card .card-footer {
            /*上下 ,左右*/
            padding: 0 0.75rem;
        }
        .button-big {margin: 0.1rem 3rem;}
    </style>
</head>
<body>
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <h1 class="title">身份验证</h1>
        </header>

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="content-block-title">第一步</div>
            <div class="list-block" style="margin: 3rem 0;min-height: 3rem;line-height: 2.2rem;font-size: 1.0rem;">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">手机号码</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请输入手机号码"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
                <a id="A_sendVerifyCode" href="javascript:void(0);" onclick="sendVerifyCode()" class="button button-big button-fill button-success">获取短信验证码</a>
            </div>
            <div class="content-block-title">第二步</div>
            <div class="list-block" style="margin: 3rem 0;min-height: 3rem;line-height: 2.2rem;font-size: 1.0rem;">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">短信验证码</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请输入验证码"/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">设置登录密码</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请设置密码"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
                <a href="javascript:void(0);" onclick="checkVerifyCode()" class="button button-big button-fill button-success">登录</a>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/smsverifycode.js?ver=22' charset='utf-8'></script>
</body>
</html>