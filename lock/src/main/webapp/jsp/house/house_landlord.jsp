<%--
  User: admin
  Date: 2017/12/18
  Time: 18:37
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

    <link rel="stylesheet" href="resources/css/font-awesome.min.css" />
    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/css/style.css" />
    <link rel="stylesheet" href="resources/css/index.css" />

    <!-- DateRange -->
    <link rel="stylesheet"  href="resources/plugin/bootstrap.daterangepicker/daterangepicker-bs3.css" />
    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <!-- 弹出-->
    <%--<link rel="stylesheet" href="resources/plugin/jquery-niftymodals/dist/jquery.niftymodals.css" />--%>
    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" />
    <!--右键菜单-->
    <link rel="stylesheet" href="resources/plugin/jQuery-contextMenu/dist/jquery.contextMenu.css" />
    <!-- table-->
    <%--<link rel="stylesheet" href="resources/plugin/FixedTable/fixed-table.css" />--%>
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <link rel="stylesheet" href="resources/plugin/dataTables/css/jquery.dataTables.css" />
    <style>
        .fixed-table-box {
            position: absolute;
            right: 0px;
            left: 20px;
            bottom: 60px;
            top: 70px;
        }
        .fixed-table_body-wraper {}
        .fixed-table_fixed {}
        .fixed-table-box > .fixed-table_body-wraper { /*内容了表格主体内容有纵向滚动条*/
            height: 88%
        }
        .fixed-table_fixed > .fixed-table_body-wraper { /*为了让两侧固定列能够同步表格主体内容滚动*/
            height: 88%;
            padding: 0 0 0 0
        }

        .md-content table thead th{
            font-size: 18px;
            font-weight: 500;
        }
        .md-content table tbody td{
            /*font-size: 1.15em;*/
            font-size: 15px;
            font-weight: 200;
            line-height: 30px;
            height: 30px;
        }
    </style>

    <script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
    <%--<script type="text/javascript" src="resources/plugin/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="resources/js/fixed-table.js"></script>--%>
    <script type="text/javascript" src="resources/js/FixedTable.js"></script>
    <%--
    <script language="javascript" type="text/javascript">
        //获得当前时间,刻度为一千分一秒
        var initializationTime = (new Date()).getTime();
        function showLeftTime() {
            var now = new Date();
            var year = now.getFullYear();
            var week = now.getDay();
            var month = now.getMonth();
            var day = now.getDate();
            var hours = now.getHours();
            var minutes = now.getMinutes();
            var seconds = now.getSeconds();
            document.all.show3[1].innerHTML = year + "-" + month + "-" + day;
            //一秒刷新一次显示时间
            var timeID = setTimeout(showLeftTime, 1000);
        }
    </script>
    --%>
</head>
<body onload="showLeftTime()">

<!-- header -->
<jsp:include page="/jsp/header.jsp"/>
<!--header end-->

<div id="cl-wrapper" class="fixed-menu">
    <div class="container-fluid table-odyssey ">

        <%--
        <div class="fixed-table-box row-col-fixed">
            <!-- 表头 start -->
            <div class="fixed-table_header-wraper" style="margin: 0 15px 0 0;">
                <table class="fixed-table_header table-butstyle" cellspacing="0" cellpadding="0" border="0">
                    <thead>
                    <tr>
                        <th class="table-width1" data-fixed="true">
                            <div class="table-time table-width1 table-cell table-header-hight58 table-butstyle">当前日期：2017-11-09</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-01</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-02</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle cd-choice">11-03</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-04</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-05</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-06</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-07</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-08</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-09</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-10</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-11</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-12</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-13</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-14</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-15</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-16</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-17</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-18</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-19</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-20</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-21</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-22</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-23</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-24</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-25</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-26</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-27</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-28</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-29</div>
                        </th>
                        <th class="table-width140 table-butstyle" data-fixed>
                            <div class="rq  table-header-hight58 table-cell table-width140 table-butstyle">11-30</div>
                        </th>
                    </tr>
                    </thead>
                </table>
            </div>
            <!-- 表头 end -->
            <!-- 表格内容 start -->
            <div class="fixed-table_body-wraper">
                <table class="fixed-table_body" cellspacing="0" cellpadding="0" border="0">
                    <tbody>
                    <tr>
                        <td class="table-width1" data-fixed="true">
                            <div class="table-time table-cell table-width1 table-butstyle"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140 cd-select">
                            <div class="cd table-hight1 table-width140 btn-rad md-trigger" data-modal="reply-ticket">
                                已预订
                            </div>
                        </td>
                        <td class="table-width140 cd-booked">
                            <div class="cd table-hight1 table-width140 btn-rad md-trigger" data-modal="reply-ticket2">
                                被预订
                            </div>
                        </td>
                        <td class="table-width140 rightclick">
                            <div class="cd table-hight1 table-width140 rightclick" style="color:green;"></div>
                        </td>
                        <!--右键菜单的源-->
                        <!--
                        <div class=" contextMenu btn-group-vertical rightclicktc" id="myMenu1">
                            <ul>
                                <button class="btn btn-primary btn-flat md-trigger rightclickan"
                                        data-modal="reply-identity">身份证授权
                                </button>
                                <button class="btn btn-primary btn-flat md-trigger rightclickan"
                                        data-modal="reply-password">密码授权
                                </button>
                                <button class="btn btn-primary btn-flat md-trigger rightclickan"
                                        data-modal="reply-unlocking">开锁信息
                                </button>
                            </ul>
                        </div>
                        -->
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140 "></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="table-width1">
                            <div class="table-time  table-butstyle"><!--没有内容--></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                        <td class="table-width140">
                            <div class="cd table-hight1 table-width140"></div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- 表格内容 end -->

            <!-- 固定列 start -->
            <div class="fixed-table_fixed fixed-table_fixed-left" id="#pcont">
                <div class="fixed-table_header-wraper">
                    <table class="fixed-table_header" cellspacing="0" cellpadding="0" border="0">
                        <thead>
                        <tr>
                            <th colspan="1" class=" table-width12">
                                <div class="table-time table-header-hight58  table-butstyle">
                                    <div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>
                                    <div class="input-group date datetime date-selection" data-min-view="2"
                                         data-date-format="yyyy-mm-dd">
                                        <input class="form-control" size="16" type="text" value="" readonly style="display:none">
                                        <span class="input-group-addon btn btn-primary calendar  date-selection-span">
                                            <span class="glyphicon glyphicon-th  date-selection-img"></span>
                                        </span>
                                    </div>
                                </div>
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="fixed-table_body-wraper">
                    <table class="fixed-table_body" cellspacing="0" cellpadding="0" border="0">
                        <tbody>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NY875-01</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NY875-02</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle"> NY875-03</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NY764-01</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NY764-02</div>
                            </td>
                        </tr>


                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-01</div>
                            </td>

                        </tr>

                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-02</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-03</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-04</div>
                            </td>
                        </tr>


                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NJ921-01</div>
                            </td>

                        </tr>

                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NJ513-01</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NJ513-02</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NJ513-03</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NJ238-01</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NJ238-02</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-01</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-02</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-03</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-hight1 table-width190  table-butstyle">NG032-04</div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 固定列 end -->
        </div>

        <script>
            $(".fixed-table-box").fixedTable();
        </script>
        --%>
        <div id="theFixedTable"></div>

        <div class="footer">&copy;2015-2016 南京亿数信息科技有限公司 版权所有</div>
        <div class="clearfix"></div>
    </div>
</div>

<%--入住记录--%>
<div class="md-overlay"></div>
<div class="md-modal colored-header custom-width md-effect-9" id="reply-ticket" style="width: 680px;">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>入住记录</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <%--<button type="button" class="btn btn-primary" id="btn_search">查询</button>--%>
                <table id="table-unlockrecord" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="150px">开锁类型</th>
                        <th width="200px">开锁时刻</th>
                        <th width="180px">开锁凭据</th>
                        <th width="100px">开锁人</th>
                        <%--<th width="200px">edit</th>--%>
                    </tr>
                    </thead>
                </table>
                <!--
                <div class="tc-table">
                    <div class="tc-table-th">
                        <div class="col-sm-2">姓名</div>
                        <div class="col-sm-2">籍贯</div>
                        <div class="col-sm-3">身份证</div>
                        <div class="col-sm-5">入住时间</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                </div>
                <div>
                    <ul class="pagination pag-left ">
                        <li class="disabled"><a href="#">&laquo;</a></li>
                        <li class="active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">&raquo;</a></li>
                    </ul>
                </div>
                -->
            </div>

        </div>
    </div>
</div>
<!-- 入住记录
<div class="md-modal colored-header custom-width md-effect-9" id="reply-ticket">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>入住记录</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <div class="tc-table">
                    <div class="tc-table-th">
                        <div class="col-sm-2">姓名</div>
                        <div class="col-sm-2">籍贯</div>
                        <div class="col-sm-3">身份证</div>
                        <div class="col-sm-5">入住时间</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                </div>
                <div>
                    <ul class="pagination pag-left ">
                        <li class="disabled"><a href="#">&laquo;</a></li>
                        <li class="active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">&raquo;</a></li>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>
已预订  end-->

<!-- 被预订 -->
<div class="md-modal colored-header custom-width md-effect-9" id="reply-ticket2">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>预订信息</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <div class="tc-table">
                    <div class="tc-table-th">
                        <div class="col-sm-2">姓名</div>
                        <div class="col-sm-2">籍贯</div>
                        <div class="col-sm-3">身份证</div>
                        <div class="col-sm-5">入住时间</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-2">广西省柳州市</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                </div>
                <div>
                    <ul class="pagination pag-left ">
                        <li class="disabled"><a href="#">&laquo;</a></li>
                        <li class="active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">&raquo;</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 被预订 end -->

<!-- 开锁授权信息 -->
<div class="md-modal colored-header custom-width md-effect-9" id="reply-unlocking">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>开锁授权信息</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <div class="tc-table">
                    <div class="tc-table-th">
                        <div class="col-sm-2">姓名</div>
                        <div class="col-sm-3">身份证</div>
                        <div class="col-sm-2">密码</div>
                        <div class="col-sm-5">预定时间段</div>
                    </div>
                    <div class="tc-table-td">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-2">yishutech</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                    <div class="tc-table-td2">
                        <div class="col-sm-2">陆帧</div>
                        <div class="col-sm-3">256142576936541254</div>
                        <div class="col-sm-2">yishutech</div>
                        <div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-10">
                        <button type="submit" class="btn btn-primary">提交授权</button>
                        <button class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取 消</button>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>

    </div>
</div>
<!-- 开锁信息 end -->

<!-- 添加密码开锁授权 -->
<div class="md-modal2 colored-header custom-width md-effect-9" id="reply-password">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>添加密码开锁授权</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="email" class="form-control" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <!--直接选择区域，每添加一个时间选择，js需要增加一个-->
                            <fieldset>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="input-prepend ">
                                            <input type="text" name="reservation" id="reservationtime2"
                                                   class="form-control span4" value="02/01/2014 1:00 PM - 02/05/2014 2:30 PM" />
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <button type="submit" class="btn btn-primary">确认修改</button>
                            <button class="btn btn-default  md-close" data-dismiss="modal" aria-hidden="true">取 消
                            </button>
                        </div>
                    </div>
                </form>
                <div class="clearfix"></div>
            </div>

        </div>
    </div>
</div>
<!-- 添加密码开锁授权 end -->

<!-- 身份证授权 -->
<div class="md-modal2 colored-header custom-width md-effect-9" id="reply-identity">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>添加身份证开锁授权</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="email" class="form-control" placeholder="地区名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="password" class="form-control" placeholder="地址">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <fieldset>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="input-prepend ">
                                            <input type="text" name="reservation" id="reservationtime"
                                                   class="form-control" value="02/01/2017 1:00 PM - 02/05/2017 2:30 PM"
                                                   class="span4"/>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>

                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary">提交授权</button>
                            <button class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取 消
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 身份证授权 end -->

<!--下拉菜单-->
<!--<script type="text/javascript" src="resources/plugin/jquery.select2/select2.min.js"></script>-->
<!--滑动条Slider(数值型input/进度条)-->
<!--<script type="text/javascript" src="resources/plugin/bootstrap.slider/js/bootstrap-slider.js"></script>-->
<!--滚动条-->
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<!--CSS3实现的模态框-->
<%--<script type="text/javascript" src="resources/plugin/jquery-niftymodals/dist/jquery.niftymodals.js"></script>--%>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>
<!--高度可定制的复选框和单选按钮（jQuery和Zepto）-->
<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
<!--表单验证-->
<script type="text/javascript" src="resources/plugin/jquery.parsley/dist/parsley.js"></script>
<!--可拖拽插件-->
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
<!--开关switch-->
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script>
<!--日历-->
<script type="text/javascript" src="resources/plugin/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="resources/plugin/bootstrap.datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<!--日期时间选择器-->
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/daterangepicker.js"></script>
<!--鼠标右键菜单-->
<%--<script type="text/javascript" src="resources/js/jquery.contextmenu.r2.js"></script>--%>
<script type="text/javascript" src="resources/plugin/jQuery-contextMenu/dist/jquery.ui.position.js"></script>
<script type="text/javascript" src="resources/plugin/jQuery-contextMenu/dist/jquery.contextMenu.js"></script>
<%--<script>--%>
    <%--//所有class为demo1的span标签都会绑定此右键菜单--%>
    <%--$('div.rightclick').contextMenu('myMenu1',{});--%>
<%--</script>--%>
<script type="text/javascript" src="resources/plugin/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/house_landlord.js"></script>
</body>

</html>