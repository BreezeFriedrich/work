<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html class="pixel-ratio-1"><head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>亿数智能门锁</title>
    <base href="<%=basePath%>">
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
<body class="">
<div class="page-group">
    <div class="page page-current" id="page-picker">
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" href="http://m.sui.taobao.org/demos">
                <span class="icon icon-left"></span>
                返回
            </a>
            <h1 class="title">修改订单</h1>
        </header>

        <!-- 工具栏 -->
        <jsp:include page="/jsp/nav.jsp"/>

        <!-- 这里是页面内容区 -->
        <div class="content native-scroll" style="">

            <div class="list-block tjrzxx">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">换房</div>
                                <div class="item-input">
                                    <%--<input type="text" placeholder="101（大户型）" id="picker" readonly="">--%>
                                    <input type="text" id="roomtag" placeholder="101（大户型）"  readonly="readonly">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">姓名</div>
                                <div class="item-input">
                                    <input id="idname" value="" type="text" placeholder="请输入您的姓名">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">身份证号码</div>
                                <div class="item-input">
                                    <input id="idcard" value="" type="email" placeholder="请输入您的身份证号码">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li class="tjrzr-lbz" id="addList">
                        <div class="content-block tjrzr">
                            <div class="row">
                                <div class="col-100"><a onclick="addID();" class="button  button-big button-success" ><img src="resources/images/ry-j.png" class="ry-j"><span class="tjrzr-wz">添加入住人</span></a></div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">修改开锁密码</div>
                                <div class="item-input">
                                    <input id="password" type="email" placeholder="请输入您的开锁密码">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">修改入住时间</div>
                                <div class="item-input">
                                    <input  type="text" placeholder="" id="datetime-picker" readonly="">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">修改离开时间</div>
                                <div class="item-input">
                                    <input type="text" placeholder="" id="datetime-picker-lk" readonly="">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li class="align-top">
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input">
                                    <textarea placeholder="备注"></textarea>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="content-block">
                <div class="row">
                    <div class="col-100"><a onclick="doAuth();"  class="button button-big button-fill button-success">确认修改</a></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<%--<script type="text/javascript" src="resources/js/zepto.js"></script>--%>
<%--<script type="text/javascript" src="resources/js/sm.js"></script>--%>
<%--<script type="text/javascript" src="resources/js/sm-extend.js"></script>--%>
<script type="text/javascript" src="resources/js/room/hm.js"></script>
<script type="text/javascript" src="resources/js/room/config.js"></script>
<script type="text/javascript" src="resources/js/room/sm-city-picker.js"></script>
<script type="text/javascript" src="resources/js/room/demos.js"></script>
<script type="text/javascript" src="resources/js/room/reBook.js"></script>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?ba76f8230db5f616edc89ce066670710";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
    $.init();
</script>
</body>
</html>