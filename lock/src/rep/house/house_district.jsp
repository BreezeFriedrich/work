<%--
  User: admin
  Date: 2017/12/18
  Time: 18:44
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

    <link rel="stylesheet" type="text/css" href="resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="resources/css/index.css"/>

    <!-- 弹出-->
    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" />

    <!-- table-->
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="resources/js/fixed-table.js"></script>
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px; min-height:500px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 88%;}
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 88% ;}
    </style>

    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <script language="javascript" type="text/javascript">
        <!--
        //获得当前时间,刻度为一千分一秒
        var initializationTime=(new Date()).getTime();
        function showLeftTime(){
            var now=new Date();
            var year=now.getFullYear();
            var week=now.getDay();;
            var month=now.getMonth();
            var day=now.getDate();
            var hours=now.getHours();
            var minutes=now.getMinutes();
            var seconds=now.getSeconds();
            document.all.show3.innerHTML=year+"-"+month+"-"+day;
            //一秒刷新一次显示时间
            var timeID=setTimeout(showLeftTime,1000);
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
                        <li><a><i class="fa inco-ctiy"></i><span class="selected">雨花区</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a><ul class="sub-menu"></ul></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid table-odyssey "  id="pcont" >
        <div class="fixed-table-box row-col-fixed" >
            <!-- 表头 start -->
            <div class="fixed-table_header-wraper" style="margin: 0 15px 0  0 " >
                <table class="fixed-table_header table-butstyle" cellspacing="0" cellpadding="0" border="0"  >
                    <thead>
                    <tr>
                        <th   class="table-width2" ><div class="table-time table-cell table-header-hight58 table-width2 table-butstyle">当前日期：2017-11-09</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-01</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-02</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle cd-choice">11-03</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-04</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-05</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-06</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-07</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-08</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-09</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-10</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-11</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-12</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-13</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-14</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-15</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-16</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-17</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-18</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-19</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-20</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-21</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-22</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-23</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-24</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-25</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-26</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-27</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-28</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-29</div></th>
                        <th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">11-30</div></th>
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
                        <td   class="table-width2"  data-fixed="true" ><div class="table-time table-cell table-width2  table-butstyle">当前日期：2017-11-09</div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140  cd-select"><div class="cd table-hight1 table-width140 btn-rad md-trigger"  data-modal="reply-ticket">已预订</div></td>
                        <!-- 已预订-->
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
                                                <div class="col-sm-2">姓名</div><div class="col-sm-2">籍贯</div><div class="col-sm-3">身份证</div><div class="col-sm-5">入住时间</div>
                                            </div>
                                            <div class="tc-table-td">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                            </div>
                                            <div class="tc-table-td2">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                            </div>
                                            <div class="tc-table-td">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                            </div>
                                            <div class="tc-table-td2">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
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
                        <div class="md-overlay"></div>
                        <!-- 已预订  end-->

                        <td  class="table-width140  cd-booked"><div class="cd table-hight1 table-width140 btn-rad md-trigger"  data-modal="reply-ticket2">被预订 </div></td>
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
                                                <div class="col-sm-2">姓名</div><div class="col-sm-2">籍贯</div><div class="col-sm-3">身份证</div><div class="col-sm-5">入住时间</div>
                                            </div>
                                            <div class="tc-table-td">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                            </div>
                                            <div class="tc-table-td2">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                            </div>
                                            <div class="tc-table-td">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
                                            </div>
                                            <div class="tc-table-td2">
                                                <div class="col-sm-2">陆帧</div><div class="col-sm-2">广西省柳州市</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>
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
                        <div class="md-overlay"></div>
                        <!-- 被预订  end -->

                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">11</div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140 "> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- 表格内容 end -->

            <!-- 固定列 start -->
            <div class="fixed-table_fixed fixed-table_fixed-left" id="#pcont" >
                <div class="fixed-table_header-wraper">
                    <table class="fixed-table_header" cellspacing="0" cellpadding="0" border="0">
                        <thead>
                        <tr>
                            <th  colspan="2"  class="table-width22 ">
                                <div class="table-time table-header-hight58  table-butstyle">
                                    <div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>
                                    <div class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">
                                        <input class="form-control" size="16" type="text" value="" readonly  style="display:none" >
                                        <span class="input-group-addon btn btn-primary calendar  date-selection-span"><span class="glyphicon glyphicon-th  date-selection-img"></span></span>
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
                            <td rowspan="3"><div class="table-hight3 table-width105   table-butstyle"> 业主1 NY875</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NY875-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NY875-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle"> NY875-03</div></td>
                        </tr>
                        <tr>
                            <td rowspan="2"><div class="table-hight2 table-width105   table-butstyle">业主2 NY764</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NY764-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NY764-02</div></td>
                        </tr>
                        <tr>
                            <td rowspan="4"><div class="table-hight4 table-width105   table-butstyle"> 业主3 NG0325</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-03</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-04</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105   table-butstyle"> 业主4 NJ921</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NJ921-01</div></td>
                        </tr>
                        <tr>
                            <td rowspan="3"><div class="table-hight3 table-width105  table-butstyle">业主5 NJ513</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NJ513-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NJ513-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NJ513-03</div></td>
                        </tr>
                        <tr>
                            <td  rowspan="2"><div class="table-hight2 table-width105  table-butstyle">业主6 NJ238</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NJ238-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NJ238-02</div></td>
                        </tr>
                        <tr>
                            <td rowspan="4"><div class="table-hight4 table-width105   table-butstyle"> 业主3 NG0325</div></td>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-03</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105  table-butstyle">NG032-04</div></td>
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

        <div class="footer">2015-2016  南京亿数信息科技有限公司 版权所有</div>

        <div class="clearfix"></div>
    </div>

</div>

<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>

<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="resources/plugin/jquery.parsley/dist/parsley.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

<!-- Bootstrap core JavaScript ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript">
    $(document).ready(function(){
        //initialize the javascript
        App.init();
        //App.dashBoard();
        /*Sparklines*/
        $(".spk1").sparkline([2,4,3,6,7,5,8,9,4,2,6,8,8,9,10], { type: 'bar', width: '80px', barColor: '#4A8CF7'});
        $(".spk2").sparkline([4,6,7,7,4,3,2,1,4,4 ,5,6,5], { type: 'discrete', width: '80', lineColor: '#4A8CF7',thresholdValue: 4,thresholdColor: '#ff0000'});
        $(".spk4").sparkline([2,4,3,6,7,5,8,9,4,2,10,], { type: 'bar', width: '80px', height: '30px',barColor: '#EA6153'});
        $(".spk5").sparkline([5,3,5,6,5,7,4,8,6,9,8,], { type: 'bar', width: '80px', height: '30px',barColor: '#4AA3DF'});

        $(".spk3").sparkline([5,6,7,9,9,5,3,2,2,4,6,7], {
            type: 'line',
            lineColor: '#258FEC',
            fillColor: '#4A8CF7',
            spotColor: false,
            width: '80px',
            minSpotColor: false,
            maxSpotColor: false,
            highlightSpotColor: '#1e7ac6',
            highlightLineColor: '#1e7ac6'});

        //Maps
        $('#world-map').vectorMap({
            map: 'world_mill_en',
            backgroundColor: 'transparent',
            regionStyle: {
                initial: {
                    fill: '#38c3c1',
                },
                hover: {
                    "fill-opacity": 0.8
                }
            },
            markerStyle:{
                initial:{
                    r: 10
                },
                hover: {
                    r: 12,
                    stroke: 'rgba(255,255,255,0.8)',
                    "stroke-width": 4
                }
            },
            markers: [
                {latLng: [41.90, 12.45], name: '1.512 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [1.3, 103.8], name: '940 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [51.511214, -0.119824], name: '530 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [40.714353, -74.005973], name: '340 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [-22.913395, -43.200710], name: '1.800 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}}
            ]
        });

        /*Pie Chart*/
        var data = [
            { label: "Google", data: 50},
            { label: "Dribbble", data: 15},
            { label: "Twitter", data: 12},
            { label: "Youtube", data: 14},
            { label: "Microsoft", data: 14}
        ];

        $.plot('#ticket-chart', data, {
            series: {
                pie: {
                    show: true,
                    innerRadius: 0.5,
                    shadow:{
                        top: 5,
                        left: 15,
                        alpha:0.3
                    },
                    stroke:{
                        width:0
                    },
                    label: {
                        show: false
                    },
                    highlight:{
                        opacity: 0.08
                    }
                }
            },
            grid: {
                hoverable: true,
                clickable: true
            },
            colors: ["#5793f3", "#19B698","#dd4444","#fd9c35","#fec42c","#d4df5a","#5578c2"],
            legend: {
                show: false
            }
        });

        $("table td .legend").each(function(){
            var el = $(this);
            var color = el.data("color");
            el.css("background",color);
        });

    });
</script>
</body>

</html>