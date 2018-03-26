<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html class="pixel-ratio-1">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <title>亿数智能门锁</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>

    <%--<link rel="stylesheet" href="resources/css/sm.css">--%>
    <%--<link rel="stylesheet" href="resources/css/sm-extend.css">--%>
    <link rel="stylesheet" href="resources/css/demos.css">
    <link rel="stylesheet" href="resources/css/tjrzxx.css">

    <link rel="apple-touch-icon-precomposed" href="http://m.sui.taobao.org/assets/img/apple-touch-icon-114x114.png">

    <style>
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
<body class="">
<div  class="page-group">
    <div id="page-modal" class="page page-current">
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" href="http://m.sui.taobao.org/demos">
                <span class="icon icon-left"></span>
                返回
            </a>
            <h1 class="title">房间管理</h1>
        </header>

        <!-- 这里是页面内容区 -->
        <div class="content native-scroll" style="">
            <div class="list-block tjrzxx">
                <ul id="roomList">
                </ul>
            </div>

            <div class="content-block">
                <div class="row">
                    <div class="col-100"><a href="#" class="button button-big button-fill button-success prompt-ok">添加新房型</a></div>
                </div>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="INPUT_hidden" value="">

<script type="text/javascript" src="resources/js/room/hm.js"></script>
<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type="text/javascript" src="resources/js/room/config.js"></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type="text/javascript" src="resources/js/room/sm-city-picker.js"></script>
<script type="text/javascript" src="resources/js/room/demos.js"></script>
<script type='text/javascript' src="resources/js/room/roomManage.js"></script>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?ba76f8230db5f616edc89ce066670710";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
    $.init()
</script>

</body>
</html>