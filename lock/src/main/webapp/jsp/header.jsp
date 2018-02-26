<%--
  User: admin
  Date: 2017/12/28
  Time: 17:14
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header -->
<div id="head-nav" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><button id="sidebar-collapse" class="button-open" style=""></button></li>
                <li class="nav-left">
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/deviceManage.jsp');">设备管理</a>
                </li>
                <li>
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/swipeRecords.jsp');">查询与统计</a>
                </li>
                <li>
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/user/dispatcherHouseStatus.do');">房  态</a>
                    <%--<a href="javascript:void(0);" onclick="javascript:navToHouseStatus();">房  态</a>--%>
                </li>
                <li>
                    <a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/user/dispatcherJuniorSetting.do');">分级管理</a>
                </li>
                <!--
                <li><a href="#">设置</a></li>
                <li><a href="#"><i class="inco-exit"></i>退出系统</a></li>
                -->
            </ul>

            <ul class="nav navbar-nav navbar-right user-nav">
                <li class="dropdown profile_menu">
                    <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"><img alt="Avatar" src="resources/images/avatar2.jpg" />未名<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/userManage.jsp');">设置</a></li>
                        <li><a href="#">退出系统</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
<script>
    $.ajax({
        type:"GET",
        url:"user/getUserFromSession.do",
        async:false,
        data:{},
        dataType:'json',
        success:function(data,status,xhr){
            ownerPhoneNumber=data.phoneNumber;
            grade=data.grade;
//            console.log('{ ownerPhoneNumber:'+ownerPhoneNumber+' ; grade:'+grade+' }');
            $('.user-nav a.dropdown-toggle').html('<img alt="Avatar" src="resources/images/avatar2.jpg" />'+ownerPhoneNumber+'<b class="caret"></b>');
        },
        error:function(xhr,errorType,error){
            console.log('会话过期,请重新登录');
            window.location.href=('user/login.do');
        }
    });
</script>
<!--header end-->
<!--
<script>
    var ownerPhoneNumber;
    var grade;

    function navToHouseStatus() {
        if (grade>=30){
            window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/house/house_city.jsp');
        }else if (grade>=20){
            window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/house/house_district.jsp');
        }else if (grade>=10){
            window.location.href=encodeURI('${pageContext.request.contextPath}/jsp/house/house_landlord.jsp');
        }
    }
    $(function () {
        $.ajax({
            type:"GET",
            url:"user/getUserFromSession.do",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{},
            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,status,xhr){
                ownerPhoneNumber=data.phoneNumber;
                grade=data.grade;
                console.log('{ ownerPhoneNumber:'+ownerPhoneNumber+' ; grade:'+grade+' }');
            },
            error:function(xhr,errorType,error){
                console.log('会话过期,请重新登录');
                window.location.href=('user/login.do');
            }
        });
    })
</script>
-->