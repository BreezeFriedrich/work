<%--
  User: admin
  Date: 2017/12/20
  Time: 9:43
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/css/font-awesome.min.css" /><!--Font Awesome,为Bootstrap而创造的图标字体-->
    <link rel="stylesheet" href="resources/css/style.css" />
    <link rel="stylesheet" href="resources/css/index.css" />

    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" />
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <%--<link rel="stylesheet" href="resources/plugin/FixedTable/fixed-table.css" />--%>
    <link rel="stylesheet" href="resources/plugin/dataTables/css/jquery.dataTables.css" />

    <%--<script type="text/javascript" src="resources/plugin/jquery.min.js"></script>--%>
    <script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
    <%--<script type="text/javascript" src="resources/js/fixed-table.js"></script>--%>
    <script type="text/javascript" src="resources/js/FixedTable.js"></script>
    <%--<script type="text/javascript" src="resources/plugin/FixedTable/fixed-table.js"></script>--%>
    <%--<script type="text/javascript" src="resources/plugin/FixedTable/FixedTable.js"></script>--%>
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px; min-height:500px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 88%;}
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 88%;}
    </style>

    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <script language="javascript" type="text/javascript">
        <!--
        //获得当前时间,刻度为一千分一秒
        var initializationTime=(new Date()).getTime();
        function showLeftTime(){
            var now=new Date();
            var year=now.getFullYear();
            var week=now.getDay();
            var month=now.getMonth();
            var day=now.getDate();
            var hours=now.getHours();
            var minutes=now.getMinutes();
            var seconds=now.getSeconds();
//            document.all.show3.innerHTML=year+"-"+month+"-"+day;
            //一秒刷新一次显示时间
//            var timeID=setTimeout(showLeftTime,1000);
            }
        //-->
    </script>
</head>
<body onload="showLeftTime()">
<!-- header -->
<jsp:include page="/jsp/header.jsp"/>
<!--header end-->

<div id="cl-wrapper" class="fixed-menu">
    <div class="cl-sidebar" data-position="right" data-step="1" data-intro="<strong>Fixed Sidebar</strong> <br/> It adjust to your needs." >
        <div class="cl-toggle"><i class="fa fa-bars"></i></div>
        <div class="cl-navblock">
            <div class="menu-space">
                <div class="content">
                    <ul class="cl-vnavigation">
                        <%--
                        <li><a><i class="fa inco-ctiy"></i><span class="selected">南京市</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>雨花台区</span></a>
                            <ul class="sub-menu">
                                <li><a href="#">业主1</a></li>
                                <li><a href="#">业主2</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="fa inco-map"></i><span>鼓楼区</span></a>
                            <ul class="sub-menu">
                                <li><a href="#">业主1</a></li>
                                <li><a href="#">业主2</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="fa inco-map"></i><span>江宁区</span></a>
                            <ul class="sub-menu">
                                <li><a href="#">业主1</a></li>
                                <li><a href="#">业主2</a></li>
                            </ul>
                        </li>
                        --%>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid table-odyssey " id="pcont" >

        <div class="container-table"></div>

        <div class="footer">&copy;2015-2016  南京亿数信息科技有限公司 版权所有</div>

        <div class="clearfix"></div>
    </div>

</div>

<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script><!--滚动条-->
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script><!--弹出框-->

<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="resources/plugin/jquery.parsley/dist/parsley.js"></script><!--表单验证-->
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script><!--switch-->
<script type="text/javascript" src="resources/plugin/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script><!--日历-->
<script type="text/javascript" charset="UTF-8" src="resources/plugin/bootstrap.datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<!--日期时间选择器-->
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/daterangepicker.js"></script>
<!--鼠标右键菜单-->
<script type="text/javascript" src="resources/plugin/jQuery-contextMenu/dist/jquery.ui.position.js"></script>
<script type="text/javascript" src="resources/plugin/jQuery-contextMenu/dist/jquery.contextMenu.js"></script>

<script type="text/javascript" src="resources/plugin/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/house_city.js"></script>
</body>

</html>