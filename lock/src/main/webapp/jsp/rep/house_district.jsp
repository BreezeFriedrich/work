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

    <link rel="stylesheet" href="resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="resources/css/index.css"/>

    <!-- 弹出-->
    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" />

    <!-- table-->
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
    <%--<script type="text/javascript" src="resources/js/fixed-table.js"></script>--%>
    <script type="text/javascript" src="resources/js/FixedTable.js"></script>
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
        function showLeftTime(){
            var now,year,week,month,day,hours,minutes,seconds;
            setTimeout(function () {
                now=new Date();
                year=now.getFullYear();
                week=now.getDay();
                month=now.getMonth()+1;
                day=now.getDate();
                hours=now.getHours();
                minutes=now.getMinutes();
                seconds=now.getSeconds();
                document.all.show3.innerHTML=year+"-"+month+"-"+day;
            },1000*2);
        }
        -->
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
                        <!--
                        <li><a><i class="fa inco-ctiy"></i><span class="selected">雨花区</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主1</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主2</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主3</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主4</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主5</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主6</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主7</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主8</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主9</span></a></li>
                        <li><a href="#"><i class="fa inco-map"></i><span>业主10</span></a></li>
                        -->
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid table-odyssey "  id="pcont" >
        <div class="fixed-table-box row-col-fixed" >
            <%--
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
                        <td  class="table-width2" data-fixed="true"><div class="table-time table-cell table-width2 table-butstyle">当前日期：2017-11-09</div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">2</div></td>
                        <td  class="table-width140 cd-select"><div class="cd table-hight1 table-width140 btn-rad md-trigger" data-modal="reply-ticket">已预订</div></td>
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

                        <td  class="table-width140 cd-booked"><div class="cd table-hight1 table-width140 btn-rad md-trigger" data-modal="reply-ticket2">被预订</div></td>
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

                        <td  class="table-width140"><div class="cd table-hight1 table-width140">5 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">10 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">15 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">20 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">28 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">30 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">31 </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time  table-butstyle"><!--没有内容--></div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">2 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">3 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">4 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">5 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">10 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">15 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">20 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">25 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140"> </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">30 </div></td>
                        <td  class="table-width140"><div class="cd table-hight1 table-width140">31 </div></td>
                    </tr>
                    <tr>
                        <td  class="table-width2"><div class="table-time table-butstyle"><!--没有内容--></div></td>
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
                            <th colspan="2" class="table-width22">
                                <div class="table-time table-header-hight58 table-butstyle">
                                    <div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>
                                    <div class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">
                                        <input class="form-control" size="16" type="text" value="" readonly style="display:none">
                                        <span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>
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
                            <td rowspan="3"><div class="table-hight3 table-width105 table-butstyle">业主1 NY875</div></td>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NY875-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NY875-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NY875-03</div></td>
                        </tr>
                        <tr>
                            <td rowspan="2"><div class="table-hight2 table-width105 table-butstyle">业主2 NY764</div></td>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NY764-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NY764-02</div></td>
                        </tr>
                        <tr>
                            <td rowspan="4"><div class="table-hight4 table-width105 table-butstyle">业主3 NG0325</div></td>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NG032-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NG032-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NG032-03</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NG032-04</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">业主4 NJ921</div></td>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NJ921-01</div></td>
                        </tr>
                        <tr>
                            <td rowspan="3"><div class="table-hight3 table-width105 table-butstyle">业主5 NJ513</div></td>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NJ513-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NJ513-02</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NJ513-03</div></td>
                        </tr>
                        <tr>
                            <td rowspan="2"><div class="table-hight2 table-width105 table-butstyle">业主6 NJ238</div></td>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NJ238-01</div></td>
                        </tr>
                        <tr>
                            <td ><div class="table-hight1 table-width105 table-butstyle">NJ238-02</div></td>
                        </tr>
                        <tr>
                            <td rowspan="4"><div class="table-hight4 table-width105 table-butstyle">业主3 NG0325</div></td>
                            <td><div class="table-hight1 table-width105 table-butstyle">NG032-01</div></td>
                        </tr>
                        <tr>
                            <td><div class="table-hight1 table-width105 table-butstyle">NG032-02</div></td>
                        </tr>
                        <tr>
                            <td><div class="table-hight1 table-width105 table-butstyle">NG032-03</div></td>
                        </tr>
                        <tr>
                            <td><div class="table-hight1 table-width105 table-butstyle">NG032-04</div></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 固定列 end -->
            --%>
        </div>

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

<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript">
    var userHierarchy;
    var landlords;
    var locks;

    /*
    //初始化FixedTable
    $(".fixed-table-box").fixedTable();

    //清空表格
    $("#empty_data").on("click", function (){
        $(".fixed-table-box").emptyTable();
    });
    //添加数据
    $("#add_data").on("click", function (){
        $(".fixed-table-box").addRow(function (){
            var html = '';
            for(var i = 0; i < 5; i ++){
                html += '<tr>';
                html += '    <td class="w-150"><div class="table-cell"> 2016-05-03</div></td>';
                html += '    <td class="w-120"><div class="table-cell">王小虎</div></td>';
                html += '    <td class="w-120"><div class="table-cell">上海</div></td>';
                html += '    <td class="w-120"><div class="table-cell">普陀区</div></td>';
                html += '    <td class="w-300"><div class="table-cell">上海市普陀区金沙江路 1518 路</div></td>';
                html += '    <td class="w-120"><div class="table-cell">200333</div></td>';
                html += '    <td class="w-100">';
                html += '        <div class="table-cell">';
                html += '            <a href="###">查看</a>';
                html += '            <a href="###">编辑</a>';
                html += '        </div>';
                html += '    </td>';
                html += '</tr>';
            }
            return html;
        });
    });
    //删除指定行
    $("#del_row").on("click", function (){
        $(".fixed-table-box").deleteRow($(".fixed-table-box").children('.fixed-table_fixed-left').children('.fixed-table_body-wraper').find('tr').eq(0));
    });
    */
    function getLocks() {
        $.ajax({
            type:"POST",
            url:"user/getSubordinateHierarchyTillLock.do",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{},
            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,status,xhr){
                userHierarchy=data;
                subordinates=data.subordinateList;
            },
            error:function(xhr,errorType,error){
                console.log('错误');
            }
        });
    }

    $(document).ready(function(){
        $(".navbar-collapse ul:first li:eq(3)").addClass("active");

        var now,year,week,month,day,hours,minutes,seconds;
        var today;
        now=new Date();
        year=now.getFullYear();
        week=now.getDay();
        month=now.getMonth()+1;
        day=now.getDate();
        hours=now.getHours();
        minutes=now.getMinutes();
        seconds=now.getSeconds();
        today=year+"-"+month+"-"+day;
//        document.all.show3.innerHTML=year+"-"+month+"-"+day;

        getLocks();
        /*
        var userHierarchy={
            "phoneNumber":"18255683932","grade":20,"name":"测试用户","location":null,"subordinateList":[{
                "phoneNumber":"17705155208","grade":10,"name":"雨花台区","location":"第1个业主地址","subordinateList":[{
                    "lockName":"房间A1","lockCode":"LK001001","lockLocation":"南京市雨花台区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                },{
                    "lockName":"房间A2","lockCode":"LK001002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                },{
                    "lockName":"房间A3","lockCode":"LK001003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                }]},
                {
                    "phoneNumber":"18858865706","grade":10,"name":"鼓楼区","location":"第2个业主地址","subordinateList":[{
                    "lockName":"房间B1","lockCode":"LK001001","lockLocation":"南京市鼓楼区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                },{
                    "lockName":"房间B2","lockCode":"LK001002","lockLocation":"南京市鼓楼区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                },{
                    "lockName":"房间B3","lockCode":"LK001003","lockLocation":"南京市鼓楼区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                }]}
            ]};
            */
        var userHierarchy={
            "phoneNumber":"18255683932","grade":20,"name":"测试用户","location":null,"subordinateList":[{
                "phoneNumber":"17705155208","grade":10,"name":"雨花台区","location":"第1个业主地址","subordinateList":[{
                    "lockName":"房间A1","lockCode":"LK001001","lockLocation":"南京市雨花台区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                },{
                    "lockName":"房间A2","lockCode":"LK001002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                },{
                    "lockName":"房间A3","lockCode":"LK001003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                }]}
            ]};

        landlords=userHierarchy.subordinateList;
        var landlordNum=landlords.length;
        var landlord;
        var html_nav_left = '';
        html_nav_left += '<li><a><i class="fa inco-ctiy"></i><span class="selected">'+userHierarchy.name+'</span></a></li>';
        for(var i in landlords){
            landlord=landlords[i];
            html_nav_left += '<li><a href="#"><i class="fa inco-map"></i><span>'+landlord.name+'</span></a></li>';
        }
//        html_nav_left += '<li><a href="#"><i class="fa inco-map"></i><span>'+'雨花区业主1'+'</span></a></li>';
//        html_nav_left += '<li><a href="#"><i class="fa inco-map"></i><span>'+'雨花区业主2'+'</span></a></li>';
        $('ul.cl-vnavigation').append(html_nav_left);

        var html='';
        html += '<th colspan="2">';
        html +=     '<div class="table-time table-header-hight58 table-butstyle">';
        html +=         '<div class="current-date">当前日期：<label id="show3" class="time3">'+today+'</label></div>';
        html +=         '<div class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">';
        html +=             '<input class="form-control" size="16" type="text" value="" readonly style="display:none">';
        html +=             '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>';
        html +=         '</div>';
        html +=     '</div>';
        html += '</th>';

        var fixedTable = new FixedTable({
            wrap : document.getElementsByClassName("fixed-table-box")[0],
            type : "row-col-fixed",
            extraClass: ["table-width140","table-time","table-header-hight58","table-butstyle","current-date","input-group","date","datetime","date-selection"],
            maxHeight : true,
            fields : [
                {
                    width: "210px",
//                field: '<th   class="table-width2" ><div class="table-time table-cell table-header-hight58 table-width2 table-butstyle">当前日期：'+today+'</div></th>',
                    field: html,
                    htmlDom:true,
                    fixed:true,
                    fixedDirection:"left"
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-02</div></th>',
                    htmlDom:true,
                    fixed:false
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-03</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-04</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-05</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-06</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-07</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-08</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-09</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-10</div></th>',
                    htmlDom:true
                },{
                    // 11
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-11</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-12</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-13</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-14</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-15</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-16</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-17</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-18</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-19</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-20</div></th>',
                    htmlDom:true
                },{
                    // 21
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-21</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-22</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-23</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-24</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-25</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-26</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-27</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-28</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-29</div></th>',
                    htmlDom:true
                },{
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-30</div></th>',
                    htmlDom:true
                },{
                    // 31
                    width: "140px",
                    field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-31</div></th>',
                    htmlDom:true
                }],
            tableDefaultContent: "<div>我是一个默认的div</div>"
        });

        fixedTable.addRow(function (){
            landlords=userHierarchy.subordinateList;
            var landlordNum=landlords.length;
            var landlord;
            var lock;
            var html = '';
            for(var i in landlords){
                console.log('i : '+i);
                landlord=landlords[i];
                locks=landlord.subordinateList;
                if(0===locks.length){
                    html += '<tr>';
                    html += '<td  class="table-width2" data-fixed="true"><div class="table-time table-cell table-width2 table-butstyle"></div></td>';
                    html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">2</div></td>';
//                        html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">已预订</div></td>';
//                        html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">被预订</div></td>';
                    html += '<td  class="table-width140 cd-select"><div class="cd table-hight1 table-width140 btn-rad md-trigger" data-modal="reply-ticket">已预订</div></td>';
                    html += '<td  class="table-width140 cd-booked"><div class="cd table-hight1 table-width140 btn-rad md-trigger" data-modal="reply-ticket2">被预订</div></td>';
                    for(i=5;i<=31;i++){
                        html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">'+i+'</div></td>';
                    }
                    html += '</tr>';
                }else {
                    html += '<tr>';
//                    html += '    <td rowspan="'+landlordNum+'"><div class="table-hight3 table-width105 table-butstyle">'+landlord.name+'</div></td>';
                    html += '    <td><div class="table-hight1 table-width105 table-butstyle">'+locks[0].lockName+'</div></td>';
                    for(i=2;i<=31;i++){
                        html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">'+i+'</div></td>';
                    }
                    html += '</tr>';
                    (function () {//循环置于闭包之内
                        for(var j=1,length=locks.length;j<length;j++){
                            lock=locks[j];
                            html += '<tr>';
                            html += '    <td ><div class="table-hight1 table-width105 table-butstyle">'+lock.lockName+'</div></td>';
                            for(i=2;i<=31;i++){
                                html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">'+i+'</div></td>';
                            }
                            html += '</tr>';
                        }
                    })();
                }
            }
            return html;
        });

        //initialize the javascript
        App.init();
        //App.dashBoard();
        /*Sparklines*/
        $(".spk1").sparkline([2,4,3,6,7,5,8,9,4,2,6,8,8,9,10], { type: 'bar', width: '80px', barColor: '#4A8CF7'});
        $(".spk2").sparkline([4,6,7,7,4,3,2,1,4,4 ,5,6,5], { type: 'discrete', width: '80', lineColor: '#4A8CF7',thresholdValue: 4,thresholdColor: '#ff0000'});
        $(".spk4").sparkline([2,4,3,6,7,5,8,9,4,2,10], { type: 'bar', width: '80px', height: '30px',barColor: '#EA6153'});
        $(".spk5").sparkline([5,3,5,6,5,7,4,8,6,9,8], { type: 'bar', width: '80px', height: '30px',barColor: '#4AA3DF'});

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