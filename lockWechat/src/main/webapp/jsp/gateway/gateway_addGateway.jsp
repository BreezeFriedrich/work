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
    <%--<meta name="viewport" content="initial-scale=1, maximum-scale=1">--%>
    <meta name="viewport" content="width=device-width,initial-scale=2.0 ,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
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
        #ifram {
            border: 0;
            width: 1px;
            min-width: 100%;
            *width: 100%;
        }
    </style>
    <script>
        var ifram = document.getElementById('ifram');
        if (navigator.userAgent.match(/iPad|iPhone/i)) {
            var iframe_box = document.getElementById('iframe-box');
            iframe_box.style.width = 100 + '%';
            iframe_box.style.overflowX = 'hidden';
            iframe_box.style.overflowY = 'scroll';
            iframe_box.style.webkitOverflowScrolling = 'touch';
            ifram.setAttribute('scrolling', 'no');
            iframe_box.appendChild(ifram)
        }
    </script>
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

        <jsp:include page="/jsp/nav.jsp"/>

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
                                    <input type="text" placeholder="请输入网关编码"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <a href="javascript:void(0);" onclick="getGatewayVerifyCode()" class="button button-big button-fill button-success">获取网关验证码</a>
                <%--<iframe id="frame1" width="300" height="180" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"/></iframe>--%>
                <%--<iframe id="frame1" style="width: 300px;height: 180px;border: 0px;margin: 0px 0px;overflow: hidden;-webkit-overflow-scrolling: touch;" frameborder="0" scrolling="no"/></iframe>--%>
                <div id="iframe-box">
                    <iframe id="ifram" width="100%"></iframe>
                    <%--<iframe id="ifram" src="http://192.168.1.14:9018" width="100%"></iframe>--%>
                </div>
            </div>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">网关验证码</div>
                                <div class="item-input">
                                    <input type="text" placeholder="请输入网关验证码"/>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <a href="javascript:void(0);" onclick="addGateway()" class="button button-big button-fill button-success">添加网关</a>
            </div>
        </div>
    </div>
</div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/gateway_addGateway.js' charset='utf-8'></script>
</body>
</html>