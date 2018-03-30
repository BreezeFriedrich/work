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
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>
    <link rel="stylesheet" href="resources/css/mescroll.min.css"/>

    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            -webkit-touch-callout:none;
            -webkit-user-select:none;
            -webkit-tap-highlight-color:transparent;
        }
        body{background-color: white}
        ul{list-style-type: none}
        a {text-decoration: none;color: #6d6d72;}

        /*列表*/
        .mescroll{
            position: fixed;
            /*top: 44px;*/
            top: 170px;
            bottom: 0;
            height: auto;
        }
        /*展示上拉加载的数据列表*/
        .data-list li{
            /*background-color: lightyellow;*/
            background-color: white;
            position: relative;
            padding: 10px 6px 10px 6px;
            border-bottom: 1px solid #eee;
        }
        /*表格卡片样式*/
        .pd{
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
            height: 80px;
        }
        .pd>div{
            float: left;
        }
        .pd p{
            margin: 0;
            overflow: hidden;
        }
        /*
        .pd img{
            vertical-align: middle;
        }
        */
        img{
            vertical-align: middle;
        }
        .pd-left{
            width: 48%;
            font-size: 14px;
            line-height: 25px;
        }
        .pd-right{
            width: 48%;
            font-size: 14px;
            line-height: 20px;
        }
        /*.pd-left .entry-val{
            color: #3366CC;
        }
        .pd-right .entry-val{
            color: #232332;
        }*/
        .pd-left img{
            max-height: 25px;
            max-width: 25px;
        }
        .pd-right img{
            max-height: 20px;
            max-width: 20px;
        }

        input{
            font-size: 18px;
            width: 150px;
            margin-left: 10px;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .row-header,row-line{
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
            font-size: 15px;
            height: 30px;
            line-height: 30px;
        }
        .row-header img,.row-line img,.a-id img{
            max-height: 30px;
            max-width: 30px;
        }
        .row-expand{
            overflow: hidden;
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
            font-size: 15px;
            line-height: 30px;
        }
        span{
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">开锁记录</h1>
            <!--<a class="pageTitle" href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/record/gatewayDivision.jsp?ownerPhoneNumber='+ownerPhoneNumber);">网关</a>
                <a class="pageTitle" href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/record/lockDivision.jsp?ownerPhoneNumber='+ownerPhoneNumber);">门锁</a>
                <a class="pageTitle" href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/record/timeDivision.jsp?ownerPhoneNumber='+ownerPhoneNumber);">时间</a>-->
        </header>
        <jsp:include page="/jsp/nav.jsp"/>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="content-block" style="background-color: #A3CE82;margin: 0px;padding: 0px;">
                <div style="height: 120px;width: 360px;margin: auto;background-color: #D1E08D;">
                    <div style="width:50%;height: 100px;margin:10px 5%; float: left;background-color: #F2F7DA;border: 1px solid #bbbbbb;">
                        <input type="text" placeholder="" id='datetime-picker-1' />
                        <input type="text" placeholder="" id='datetime-picker-2' />
                    </div>
                    <div style="width:30%;height: 75px; margin:22.5px 5%;float: left;">
                        <a href="javascript:void(0);" class="open-popup" data-popup=".popup-menu">
                            <img alt="menu" src="resources/img/menu-dots_128px.png" width="75px;"/>
                        </a>
                    </div>
                </div>
                <div id="mescroll" class="mescroll">
                    <ul id="dataList" class="data-list">
                    </ul>
                </div>
            </div>
        </div>

        <!-- About Popup -->
        <div class="popup popup-menu">
            <div class="content-block">
                <p><a href="javascript:void(0);" class="close-popup" onclick="showAllRecords()"><i style="padding-left: 15px" class="iconfont">&#xe6cc;</i><b style="padding-left: 20px">查询所有记录</b></a></p>
                <p><a href="javascript:void(0);" class="close-popup" onclick="showDevicesFromRecords()"><i style="padding-left: 15px" class="iconfont">&#xe6d2;</i><b style="padding-left: 20px">按设备查询记录</b></a></p>
                <p><a href="javascript:void(0);" class="close-popup" onclick="showOperatorFromRecords()"><i style="padding-left: 15px" class="iconfont">&#xe687;</i><b style="padding-left: 20px">按身份证查询记录</b></a></p>
                <p><a href="javascript:void(0);" class="close-popup" onclick="showRoomFromRecords()"><i style="padding-left: 15px" class="iconfont">&#xe7ff;</i><b style="padding-left: 20px">按房间查询记录</b></a></p>
            </div>
        </div>
        <div class="popup-overlay"></div>
    </div>
</div>
<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/mescroll.min.js'></script>
<%--<script type='text/javascript' src='resources/js/util/date.js'></script>--%>
<%--<script type='text/javascript' src='resources/js/util/datetimepicker.js'></script>--%>
<script type="text/javascript" src='resources/js/util/moment-with-locales.js'></script>
<script type='text/javascript' src='resources/js/record.js' charset='utf-8'></script>
</body>
</html>