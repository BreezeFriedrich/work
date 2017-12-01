<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/12/1
  Time: 14:50
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
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="/favicon.ico">
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
        a {text-decoration: none;color: #18B4FE;}

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
            height: 80px;
        }
        /*表格卡片样式*/
        .pd{
            overflow: hidden;
            background-color: white;
            border: 1px solid #bbbbbb;
        }
        .pd>div{
            float: left;
        }
        .pd p{
            margin: 0;
            overflow: hidden;
        }
        .pd img{
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
    </style>
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">全部记录</h1>
            <!--<a class="pageTitle" href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/record/gatewayDivision.jsp?ownerPhoneNumber='+ownerPhoneNumber);">网关</a>
			    <a class="pageTitle" href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/record/lockDivision.jsp?ownerPhoneNumber='+ownerPhoneNumber);">门锁</a>
			    <a class="pageTitle" href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/record/timeDivision.jsp?ownerPhoneNumber='+ownerPhoneNumber);">时间</a>-->
        </header>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="content-block" style="background-color: #A3CE82;margin: 0px;padding: 0px;">
                <div style="height: 120px;width: 360px;margin: auto;background-color: #D1E08D;">
                    <div style="width:50%;height: 100px;margin:10px 5%; float: left;background-color: #F2F7DA;border: 1px solid #bbbbbb;">
                        <input type="text" placeholder="" id='datetime-picker-1' />
                        <input type="text" placeholder="" id='datetime-picker-2' />
                    </div>
                    <div style="width:30%;height: 75px; margin:22.5px 5%;float: left;">
                        <a href="#" class="open-popup" data-popup=".popup-menu">
                            <img alt="menu" src="../resources/img/menu-dots_128px.png" width="75px;"/>
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
                <p><a href="javascript:void(0);" onclick="javascript:console.log('Hello');" class="close-popup" >查询所有记录</a></p>
                <p><a href="javascript:void(0);" onclick="showDevicesWithinRecords()">按设备分类</a></p>
                <p><a href="#" class="close-popup">按时间分类</a></p>
            </div>
        </div>
        <div class="popup-overlay"></div>
    </div>
</div>
<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/mescroll.min.js'></script>
<script type='text/javascript' src='resources/js/util/date.js'></script>
<%--<script type='text/javascript' src='resources/js/record.js?ver=1' charset='utf-8'></script>--%>
<script type="text/javascript" charset="utf-8">
    var pathName=window.document.location.pathname;
    var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var ownerPhoneNumber;
    var startTime;
    var endTime;

    $(function(){
        //初始化时间选择器
        var temptime = new Date();
        $("#datetime-picker-1").datetimePicker({
            value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
        });
        temptime.setDate(temptime.getDate()+1);
        $("#datetime-picker-2").datetimePicker({
            value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
        });

        ownerPhoneNumber="13905169824";
        /*
        startTime="2014-01-01 01:01";
        endTime="2017-12-10 01:01";
        */
//      ownerPhoneNumber=getQueryString("ownerPhoneNumber");
        startTime=$("#datetime-picker-1").val();
        endTime=$("#datetime-picker-2").val();

        //创建MeScroll对象,内部已默认开启下拉刷新,自动执行up.callback,重置列表数据;
        var mescroll = new MeScroll("mescroll", {
            //上拉加载列表项.
            up: {
                clearEmptyId: "dataList", //1.下拉刷新时会自动先清空此列表,再加入数据; 2.无任何数据时会在此列表自动提示空
                callback: upCallback, //上拉加载的回调,此处可简写; 相当于 callback: function (page) { getListData(page); }
                toTop:{ //配置回到顶部按钮
                    src : "resources/img/mescroll-totop.png", //默认滚动到1000px显示,可配置offset修改
                    //offset : 1000
                }
            }
        });

        //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
        document.ondragstart=function() {return false;};

        $.init();
    });
    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        //联网加载数据
        getListDataFromNet(page.num, page.size, function(curPageData,totalSize){
            //联网成功的回调,隐藏下拉刷新和上拉加载的状态;
            //mescroll会根据传的参数,自动判断列表如果无任何数据,则提示空;列表无下一页数据,则提示无更多数据;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);

            //方法一(推荐): 后台接口有返回列表的总页数 totalPage
            //mescroll.endByPage(curPageData.length, totalPage); //必传参数(当前页的数据个数, 总页数)

            //方法二(推荐): 后台接口有返回列表的总数据量 totalSize
            mescroll.endBySize(curPageData.length, totalSize); //必传参数(当前页的数据个数, 总数据量)

            //方法三(推荐): 您有其他方式知道是否有下一页 hasNext
            //mescroll.endSuccess(curPageData.length, hasNext); //必传参数(当前页的数据个数, 是否有下一页true/false)

            //方法四 (不推荐),会存在一个小问题:比如列表共有20条数据,每页加载10条,共2页.如果只根据当前页的数据个数判断,则需翻到第三页才会知道无更多数据,如果传了hasNext,则翻到第二页即可显示无更多数据.
//                mescroll.endSuccess(curPageData.length);

            //设置列表数据,因为配置了emptyClearId,第一页会清空dataList的数据,所以setListData应该写在最后;
            setListData(curPageData);
        }, function(){
            //联网失败的回调,隐藏下拉刷新和上拉加载的状态;
            mescroll.endErr();
        });
    }

    /*设置列表数据与渲染列表*/
    function setListData(curPageData){
        var listDom=document.getElementById("dataList");
        for (var i = 0; i < curPageData.length; i++) {
            var pd=curPageData[i];

            var str='<div class="pd">';
            str+='<div class="pd-left">';
            str+='<p class="pd-name"><img class="pd-img" src="../resources/img/gateway_64px.png"/> '+'<span class="entry-val">'+pd.gatewayCode+'</span></p>';
            str+='<p><img src="../resources/img/padlock_64px.png"/> '+'<span class="entry-val">'+pd.lockCode+'</span></p>';
            str+='</div>';
            str+='<div class="pd-right">';
            if(null !== pd.cardInfo){
                str+='<p><img class="pd-img" src="../resources/img/idCard_48px.png"/> <span class="entry-val">'+pd.cardInfo.cardNumb+'</span></p>';
                str+='<p><img class="pd-img" src="../resources/img/person_64px.png"/> <span class="entry-val">'+pd.cardInfo.name+'</span></p>';
            }
            if(null !== pd.passwordInfo){
                str+='<p class="pd-unlock"><img class="pd-img" src="../resources/img/password_64px.png"/> '+'<span class="entry-val">'+pd.passwordInfo.password+'</span></p>';
            }
            str+='<p><img class="pd-img" src="../resources/img/time_64px.png"/> <span class="entry-val">'+formatTimeString(pd.timetag)+'</span></p>';
            str+='</div>';
            str+='</div>';

            var liDom=document.createElement("li");
            liDom.innerHTML=str;
            listDom.appendChild(liDom);
        }
    }

    /*联网加载列表数据*/
    function getListDataFromNet(pageNum,pageSize,successCallback,errorCallback) {
        //ownerPhoneNumber,startTime,endTime
        $.ajax({
            type:"POST",
            url:projectPath+"/record/pageUnlockRecord.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":startTime,"endTime":endTime,"pageNum":pageNum,"pageSize":pageSize},
            dataType:'json',
            success:function(data,status,xhr){
                successCallback(data.rows,data.totalSize);
            },
            error:errorCallback
        });
    }

    function showDevicesWithinRecords(){
        $.closeModal();
//        console.log('Hey');
        alert('Hi');
    }

    //获取链接参数
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = decodeURI(window.location.search).substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
</script>
</body>
</html>