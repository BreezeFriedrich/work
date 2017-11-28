<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/11/28
  Time: 10:54
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

        /*模拟的标题*/
        .header{
            z-index: 9990;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 44px;
            line-height: 44px;
            text-align: center;
            border-bottom: 1px solid #eee;
            background-color: white;
        }
        .header .btn-left{
            position: absolute;
            top: 0;
            left: 0;
            padding:12px;
            line-height: 22px;
        }
        .header .btn-right{
            position: absolute;
            top: 0;
            right: 0;
            padding: 0 12px;
        }
        /*说明*/
        .notice{
            padding: 20px;
            border-bottom: 1px solid #eee;
            font-size: 14px;
            line-height: 24px;
            text-align: center;
            color:#555;
        }
        .btn-change{
            display: inline-block;
            margin-top: 14px;
            font-size: 14px;
            padding: 3px 15px;
            text-align: center;
            border: 1px solid #FF6990;
            border-radius: 20px;
            color: #FF6990;
        }
        .btn-change:active{
            opacity: .5;
        }
        /*列表*/
        .mescroll{
            position: fixed;
            top: 44px;
            bottom: 0;
            height: auto;
        }
        /*展示上拉加载的数据列表*/
        .data-list li{
            position: relative;
            padding: 10px 8px 10px 120px;
            border-bottom: 1px solid #eee;
        }
        .data-list .pd-img{
            position: absolute;
            left: 18px;
            top: 18px;
            width: 80px;
            height: 80px;
        }
        .data-list .pd-name{
            font-size: 16px;
            line-height: 20px;
            height: 40px;
            overflow: hidden;
        }
        .data-list .pd-price{
            margin-top: 8px;
            color: red;
        }
        .data-list .pd-sold{
            font-size: 12px;
            margin-top: 8px;
            color: gray;
        }
    </style>
</head>
<body>
<!--滑动区域-->
<div id="mescroll" class="mescroll">
    <!--展示上拉加载的数据列表-->
    <ul id="dataList" class="data-list">
    </ul>
</div>
<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/mescroll.min.js'></script>
<script type='text/javascript' src='resources/js/util/date.js'></script>
<%--<script type='text/javascript' src='resources/js/lockDivision.js?ver=1' charset='utf-8'></script>--%>
<script type="text/javascript" charset="utf-8">
    var pathName=window.document.location.pathname;
    var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var ownerPhoneNumber="13905169824";
    var startTime="20140101010101";
    var endTime="20171210010101";

    $(function(){
        //创建MeScroll对象,内部已默认开启下拉刷新,自动执行up.callback,重置列表数据;
        var mescroll = new MeScroll("mescroll", {
            up: {
                clearEmptyId: "dataList", //1.下拉刷新时会自动先清空此列表,再加入数据; 2.无任何数据时会在此列表自动提示空
                callback: getListData, //上拉回调,此处可简写; 相当于 callback: function (page) { getListData(page); }
                toTop:{ //配置回到顶部按钮
                    src : "resources/img/mescroll-totop.png", //默认滚动到1000px显示,可配置offset修改
                    //offset : 1000
                }
            }
        });

        function getListData(page){
            //联网加载数据
            getListDataFromNet(page.num, page.size, function(curPageData,totalSize){
                //方法二(推荐): 后台接口有返回列表的总数据量 totalSize
                mescroll.endBySize(curPageData.length, totalSize); //必传参数(当前页的数据个数, 总数据量)

                //设置列表数据,因为配置了emptyClearId,第一页会清空dataList的数据,所以setListData应该写在最后;
                setListData(curPageData);
            }, function(){
                //联网失败的回调,隐藏下拉刷新和上拉加载的状态;
                mescroll.endErr();
            });
        }

        /*设置列表数据*/
        function setListData(curPageData){
            var listDom=document.getElementById("dataList");
            for (var i = 0; i < curPageData.length; i++) {
                var pd=curPageData[i];

                var str='<img class="pd-img" src="'+pd.pdImg+'"/>';
                str+='<p class="pd-name">网关'+pd.gatewayCode+'</p>';
                str+='<p class="pd-name">门锁'+pd.lockCode+'</p>';
                str+='<p class="pd-price">时间'+pd.formatTimeString(timetag)+'</p>';
                if(null !== pd.cardInfo){
                    str+='<p class="pd-sold">身份证'+pd.cardInfo.cardNumb+'</p>';
                    str+='<p class="pd-sold">姓名'+pd.cardInfo.name+'</p>';
                }
                if(null !== pd.passwordInfo){
                    str+='<p class="pd-sold">密码'+pd.passwordInfo.password+'</p>';
                }

                var liDom=document.createElement("li");
                liDom.innerHTML=str;
                listDom.appendChild(liDom);
            }
        }

        /*联网加载列表数据*/
        function getListDataFromNet(pageNum,pageSize,successCallback,errorCallback) {
            $.ajax({
                type:"POST",
                url:projectPath+"/record/pageUnlockRecord.action",
                async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                data:{"ownerPhoneNumber":ownerPhoneNumber,"pageNum":pageNum,"pageSize":pageSize},
                dataType:'json',
                success:function(data,status,xhr){
                    successCallback(data.rows,data.totalSize);
                },
                error:errorCallback
            });
        }

        //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
        document.ondragstart=function() {return false;}
    });
</script>
</body>
</html>