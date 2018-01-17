<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/11/29
  Time: 16:19
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
    <title>亿数智能门锁</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <style>
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
            <h1 class="title">刷卡记录</h1>
        </header>

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="list-block" style="margin: 3rem 0;min-height: 3rem;line-height: 2.2rem;font-size: 1.0rem;">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">起始时间</div>
                                <div class="item-input">
                                    <input type="text" placeholder="" id='datetime-picker-1' />
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">结束时间</div>
                                <div class="item-input">
                                    <input type="text" placeholder="" id='datetime-picker-2' />
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <a href="javascript:void(0);" onclick="setTimeBefRec()" class="button button-big button-fill button-success">查询记录</a>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<%--<script type='text/javascript' src='resources/js/fastclick.js'></script>--%>
<script type='text/javascript'>
    var pathName=window.document.location.pathname;
    var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var ownerPhoneNumber;
    var startTime;
    var endTime;
    $(function(){
        // FastClick.attach(document.body);

        //初始化时间选择器
        var temptime = new Date();
        $("#datetime-picker-1").datetimePicker({
            value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
        });
        temptime.setDate(temptime.getDate()+1);
        $("#datetime-picker-2").datetimePicker({
            value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
        });

        ownerPhoneNumber=getQueryString("ownerPhoneNumber");

        $.init();
    });

    //获取链接参数
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = decodeURI(window.location.search).substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    function setTimeBefRec() {
        $.showIndicator();
        startTime=$("#datetime-picker-1").val();
        endTime=$("#datetime-picker-2").val();

        /*
        if(''!=startTime && ''!=endTime){
                $.ajax({
                    type:"POST",
                    url:projectPath+"/unlock/authUnlockById.action",
                    async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                    data:{
                        "ownerPhoneNumber":ownerPhoneNumber,
                        "gatewayCode":gatewayCode,
                        "lockCode":lockCode,
                        "name":name,
                        "startTime":startTime,
                        "endTime":endTime,
                        "cardNumb":cardNumb
                    },
                    dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data,status,xhr){
                        $.hideIndicator();
                        ajaxResult = data;
                        $.toast('开锁授权成功,正在刷新页面...',1500);
                        // window.setTimeout("refreshPage()",2000);
                        window.setTimeout("history.go(-1)",2000);
                    },
                    error:function(xhr,errorType,error){
                        $.hideIndicator();
                        console.log('错误');
                    }
                });
        }else {
            $.hideIndicator();
            $.toast('起始时间和结束时间不能为空',1500);
        }
        */
        url="jsp/record/record_all.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&startTime="+startTime+"&endTime="+endTime;
        window.location.href=encodeURI(url);
    }
</script>
</body>
</html>