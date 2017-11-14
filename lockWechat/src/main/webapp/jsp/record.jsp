<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%--
  User: admin
  Date: 2017/11/14
  Time: 14:43
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
<!--模拟的标题-->
<p class="header"><a class="btn-left" href="../index.html">main</a> 商品列表 <a class="btn-right" href="list-news.html">list-news</a></p>
<!--滑动区域-->
<div id="mescroll" class="mescroll">
    <!--展示上拉加载的数据列表-->
    <ul id="dataList" class="data-list">
        <!--<li>
            <img class="pd-img" src="../res/img/pd1.jpg"/>
            <p class="pd-name">商品标题商品标题商品标题商品标题商品标题商品</p>
            <p class="pd-price">200.00 元</p>
            <p class="pd-sold">已售50件</p>
        </li>-->
    </ul>
</div>
<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='resources/js/mescroll.min.js'></script>
<script type='text/javascript' src='resources/js/record.js?ver=1' charset='utf-8'></script>
<script type="text/javascript" charset="utf-8">
    $(function(){
        //创建MeScroll对象,内部已默认开启下拉刷新,自动执行up.callback,重置列表数据;
        var mescroll = new MeScroll("mescroll", {
            up: {
                clearEmptyId: "dataList", //1.下拉刷新时会自动先清空此列表,再加入数据; 2.无任何数据时会在此列表自动提示空
                callback: getListData, //上拉回调,此处可简写; 相当于 callback: function (page) { getListData(page); }
                toTop:{ //配置回到顶部按钮
                    src : "../res/img/mescroll-totop.png", //默认滚动到1000px显示,可配置offset修改
                    //offset : 1000
                }
            }
        });

        /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
        function getListData(page){
            //联网加载数据
            getListDataFromNet(page.num, page.size, function(curPageData){
                //联网成功的回调,隐藏下拉刷新和上拉加载的状态;
                //mescroll会根据传的参数,自动判断列表如果无任何数据,则提示空;列表无下一页数据,则提示无更多数据;
                console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);

                //方法一(推荐): 后台接口有返回列表的总页数 totalPage
                //mescroll.endByPage(curPageData.length, totalPage); //必传参数(当前页的数据个数, 总页数)

                //方法二(推荐): 后台接口有返回列表的总数据量 totalSize
                //mescroll.endBySize(curPageData.length, totalSize); //必传参数(当前页的数据个数, 总数据量)

                //方法三(推荐): 您有其他方式知道是否有下一页 hasNext
                //mescroll.endSuccess(curPageData.length, hasNext); //必传参数(当前页的数据个数, 是否有下一页true/false)

                //方法四 (不推荐),会存在一个小问题:比如列表共有20条数据,每页加载10条,共2页.如果只根据当前页的数据个数判断,则需翻到第三页才会知道无更多数据,如果传了hasNext,则翻到第二页即可显示无更多数据.
                mescroll.endSuccess(curPageData.length);

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
                str+='<p class="pd-name">'+pd.pdName+'</p>';
                str+='<p class="pd-price">'+pd.pdPrice+' 元</p>';
                str+='<p class="pd-sold">已售'+pd.pdSold+'件</p>';

                var liDom=document.createElement("li");
                liDom.innerHTML=str;
                listDom.appendChild(liDom);
            }
        }

        /*联网加载列表数据*/
        function getListDataFromNet(pageNum,pageSize,successCallback,errorCallback) {
            //延时一秒,模拟联网
            $.ajax({
                type: 'GET',
                url:projectPath+"/record/pageUnlockRecord.action?pageNum="+pageNum+"&pageSize="+pageSize,
                dataType: 'json',
                success: function(data){
                    /*
                    //模拟分页数据
                    var listData=[];
                    for (var i = (pageNum-1)*pageSize; i < pageNum*pageSize; i++) {
                        if(i==data.length) break;
                        listData.push(data[i]);
                    }
                    successCallback(listData);
                    */
                    successCallback(data.rows);
                },
                error: errorCallback
            });
        }

        //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
        document.ondragstart=function() {return false;}
    });
</script>
</body>
</html>