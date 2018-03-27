<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html class="pixel-ratio-1">
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
        <link rel="stylesheet" href="resources/css/fixed-table.css" />
        <%--<script src="resources/js/jquery-1.11.3.min.js"></script>--%>
        <%--<script src="resources/js/fixed-table.js"></script>--%>
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
        <style>
            .fixed-table-box{ position: absolute; top: 0; left: 0; right: 0; bottom: 0px}
            .fixed-table_body-wraper{
                /* height: 260px; */
            }
            .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/
                position: absolute; top: 51px; left: 0; right: 0; bottom: 0px}

            .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/
                position: absolute; top: 51px; left: 0; right: 0; bottom: 0px}


            .w-50{
                width: 55px !important;overflow: hidden;
            }
            .w-80{
                width: 80px;
            }

            .w-81{
                width: 80px;
            }
        </style>
    </head>
<body>
<div class="page-group">
    <div class="page page-current" id="page-picker">
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" href="http://m.sui.taobao.org/demos">
                <span class="icon icon-left"></span>
                返回
            </a>
            <h1 class="title">添加房间</h1>
        </header>

        <!-- 工具栏 -->
        <nav class="bar bar-tab">
            <a class="tab-item external" href="jsp/main2.jsp">
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
            <a class="tab-item external" href="jsp/room/roomStatus.jsp">
                <span class="icon icon-search"></span>
                <span class="tab-label">房态</span>
            </a>
            <a class="tab-item external" href="jsp/alert.jsp">
                <span class="icon icon-phone"></span>
                <span class="tab-label">告警<span class="badge" style="vertical-align: -50%;"></span></span>
            </a>
            <a class="tab-item external  active" href="jsp/setting.jsp">
                <span class="icon icon-settings"></span>
                <span class="tab-label">设置</span>
            </a>
        </nav>

        <!-- 这里是页面内容区 -->
        <div class="content native-scroll">

            <div class="list-block tjrzxx">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">房间名称</div>
                                <div class="item-input">
                                    <input type="text" id="roomName" placeholder="请输入房间名称">
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">选择门锁</div>
                                <div class="item-input">
                                    <input type="text"  placeholder="" id="picker" >
                                </div>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">网关号</div>
                                <div class="item-input">
                                    <input type="email"  placeholder="网关号" readonly="readonly" id="gatewaycode"   value="">
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="content-block">
                <div class="row">
                    <div class="col-100"><a href="#" onclick="addRoom();" class="button button-big button-fill button-success">确认</a></div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type="text/javascript" src="resources/js/room/demos.js"></script>
<script type="text/javascript" src="resources/js/room/addRoom.js"></script>
<script>$.init()</script>

</body>
</html>