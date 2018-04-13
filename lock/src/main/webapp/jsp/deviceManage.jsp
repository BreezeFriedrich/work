<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>
    <!-- Bootstrap core CSS -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:300,200,100' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css">
    <%--<link rel="stylesheet" type="text/css" href="resources/css/font-awesome.4.6.0.css">--%>
    <link rel="stylesheet" type="text/css" href="resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/css/style.css" />
    <link rel="stylesheet" href="resources/css/index.css" />

    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.daterangepicker/daterangepicker-bs3.css" />

    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" /><!-- 弹出框-->
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 90% }
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 90% ;}
    </style>
</head>
<body>
<!-- header -->
<jsp:include page="/jsp/header.jsp"/>
<!--header end-->

<div id="cl-wrapper" class="fixed-menu">

    <div class="container-fluid "  id="pcont" >
        <div class="page-head"><img src="resources/images/lb.png" class="inco-lb"><h3>设备管理</h3></div>

        <div class="col-sm-9 table1">

            <div class="block-flat table0-top gateway-table">

                <%--<button type="button" class="btn btn-success btn-rad md-trigger" data-modal="reply-ticket"><i class="fa fa-plus"></i>新增网关</button>--%>
                <button id="addGateway" type="button" class="btn btn-success btn-rad md-trigger"><i class="fa fa-plus"></i>新增网关</button>
                <button type="button" onclick="getgateway();" class="btn btn-success btn-rad md-trigger" data-modal="reply-lock"><i class="fa fa-plus"></i>新增门锁</button>

                <div class="content">
                    <!--网关和锁-->
                    <table>
                        <tbody id="showDevices">
                        <!--
                        <tr>
                            <th style="width:10%;" valign="middle">网关1</th>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁1
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁2
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁3
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁4
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁5
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁6
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁7
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁8
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁9
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        门锁10
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" class="md-trigger"  data-modal="reply-identity">身份证授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-password">密码授权</a></li>
                                        <li><a href="#" class="md-trigger"  data-modal="reply-unlocking">查看授权信息</a></li>
                                    </ul>
                                </div>
                            </td>
                            <th style="width:5%;"  valign="middle">
                                <a class="label label-danger btn btn-danger btn-xs  " href="#"><i class="fa fa-times"></i></a>
                            </th>
                        </tr>
                        -->
                        </tbody>
                    </table>
                    <!--网关和锁END-->

                    <!--网关分页-->
                    <div>
                        <ul class="pagination pag-left " id="pageList">
                            <%--<li class="disabled"><a href="#">&laquo;</a></li>--%>
                            <%--<li class="active"><a href="#">1</a></li>--%>
                            <%--<li><a href="#">2</a></li>--%>
                            <%--<li><a href="#">3</a></li>--%>
                            <%--<li><a href="#">4</a></li>--%>
                            <%--<li><a href="#">5</a></li>--%>
                            <%--<li><a href="#">&raquo;</a></li>--%>
                        </ul>
                    </div>
                    <!--网关分页END-->

                    <!-- 新增网关-->
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-ticket">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>新增网关 </h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="gatewayName" placeholder="网关名称">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" name="gatewayCode" placeholder="网关编码">
                                            </div>
                                            <div class="col-sm-4">
                                                <button type="button" class="btn btn-primary" onclick="getGatewayVerifyCode();">获取验证码</button>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="gatewayLocation"  placeholder="网关地址">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="opCode"  placeholder="网关验证码">
                                            </div>
                                            <div class="col-sm-12">
                                                <iframe style="top: -0px;position: relative;" id="frame1" width="300" height="180" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"/></iframe>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-10" >
                                                <button type="submit" class="btn btn-primary" onclick="addGateway(this.form)">确认新增</button>
                                                <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--<div class="md-overlay"></div>--%>
                    <!-- 新增网关end -->

                    <!-- 新增门锁-->
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-lock">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>新增门锁 </h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="lockName" placeholder="门锁名称">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="lockCode" placeholder="门锁编码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="lockLocation"  placeholder="门锁地址">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <%--<input type="text" class="form-control" name="gatewayCode"  placeholder="网关编码">--%>
                                                <select id="gatewayCode" name="gatewayCode" placeholder="网关编码" class="form-control"></select>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-10" >
                                                <button type="submit" class="btn btn-primary" onclick="addLock(this.form);">确认新增</button>
                                                <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--<div class="md-overlay"></div>--%>
                    <!-- 新增门锁end -->

                    <!-- 添加密码开锁授权  -->
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-password">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>添加密码开锁授权 </h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form id="doPwdAuth" class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="password" placeholder="请输入密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <!--直接选择区域，每添加一个时间选择，js需要增加一个-->
                                                <fieldset>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <div class="input-prepend ">
                                                                <input type="text" name="reservation" id="reservationtime2" placeholder="请选择时间范围" class="form-control span4" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-12" >
                                                <button type="submit" class="btn btn-primary" onclick="doPwdAuth(this.form)">确认授权</button>
                                                <button type="button" class="btn btn-default  md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--<div class="md-overlay"></div>--%>
                    <!-- 添加密码开锁授权end -->

                    <!-- 身份证授权 -->
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-identity">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>添加身份证开锁授权</h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form id="doIDAuth" class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="name" placeholder="姓名">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="text" class="form-control" name="cardNumb"  placeholder="身份证号码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <fieldset>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <div class="input-prepend ">
                                                                <input type="text" name="reservation" placeholder="请选择时间范围" id="reservationtime" class="form-control span4" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-10" >
                                                <button type="submit" class="btn btn-primary" onclick="doIDAuth(this.form)">提交授权</button>
                                                <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                    <%--<div class="md-overlay"></div>--%>
                    <!-- 身份证授权end -->

                    <!-- 开锁授权信息  -->
                    <div class="md-modal colored-header custom-width md-effect-9" id="reply-unlocking">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>开锁授权信息 </h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <div class="tc-table" id="showAuth">
                                        <div class="tc-table-th">
                                            <div class="col-sm-1">姓名</div><div class="col-sm-3">身份证</div><div class="col-sm-2">密码</div><div class="col-sm-5">预定时间段</div>
                                        </div>
                                        <%--<div class="tc-table-td">--%>
                                            <%--<div class="col-sm-1">陆帧</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-2">password</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div> <a class="label label-danger btn btn-danger btn-xs  " href="#"><i class="fa fa-times"></i></a>--%>
                                        <%--</div>--%>
                                        <%--<div class="tc-table-td2">--%>
                                            <%--<div class="col-sm-1">陆帧</div><div class="col-sm-3">256142576936541254</div><div class="col-sm-2">yishutech</div><div class="col-sm-5">2017-11-07 11:25-2017-11-07 11:25</div>  <a class="label label-danger btn btn-danger btn-xs  " href="#"><i class="fa fa-times"></i></a>--%>
                                        <%--</div>--%>
                                    </div>
                                    <div class="form-group" >
                                        <%--<div class="col-sm-10" >--%>
                                            <%--<button type="submit" class="btn btn-primary">提交授权</button>--%>
                                            <%--<button class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>--%>
                                        <%--</div>--%>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="md-overlay"></div>
                    <!-- 开锁信息end -->
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>

    <div class="footer">&copy;2015-2016 南京亿数信息科技有限公司 版权所有</div>
    <div class="clearfix"></div>
</div>
<%--<script type="text/javascript" src="resources/plugin/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="resources/plugin/jquery.js"></script>--%>
<script type="text/javascript" src="resources/plugin/jquery.select2/select2.min.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.slider/js/bootstrap-slider.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.parsley/dist/parsley.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="resources/js/jquery.contextmenu.r2.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/fixed-table.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/js/gatewayJs.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $(".navbar-collapse ul:first li:eq(1)").addClass("active");

        //initialize the javascript
        App.init();

        $('#reservation').daterangepicker();
        $('#reservationtime').daterangepicker({
            timePicker: true,
            timePickerIncrement: 30,
            format: 'MM/DD/YYYY h:mm A'
        });
        $('#reservationtime2').daterangepicker({
            timePicker: true,
            timePickerIncrement: 30,
            format: 'MM/DD/YYYY h:mm A'
        });

        var cb = function(start, end) {
            $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
            alert("Callback has fired: [" + start.format('MMMM D, YYYY') + " to " + end.format('MMMM D, YYYY') + "]");
        };

        var optionSet1 = {
            startDate: moment().subtract('days', 29),
            endDate: moment(),
            minDate: '01/01/2012',
            maxDate: '12/31/2014',
            dateLimit: { days: 60 },
            showDropdowns: true,
            showWeekNumbers: true,
            timePicker: false,
            timePickerIncrement: 1,
            timePicker12Hour: true,
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                'Last 7 Days': [moment().subtract('days', 6), moment()],
                'Last 30 Days': [moment().subtract('days', 29), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
            },
            opens: 'left',
            buttonClasses: ['btn'],
            applyClass: 'btn-small btn-primary',
            cancelClass: 'btn-small',
            format: 'MM/DD/YYYY',
            separator: ' to ',
            locale: {
                applyLabel: 'Submit',
                cancelLabel: 'Clear',
                fromLabel: 'From',
                toLabel: 'To',
                customRangeLabel: 'Custom',
                daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
                monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                firstDay: 1
            }
        };

        var optionSet2 = {
            startDate: moment().subtract('days', 7),
            endDate: moment(),
            opens: 'left',
            ranges: {
                'Today': [moment(), moment()],
                'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                'Last 7 Days': [moment().subtract('days', 6), moment()],
                'Last 30 Days': [moment().subtract('days', 29), moment()],
                'This Month': [moment().startOf('month'), moment().endOf('month')],
                'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
            }
        };
        $('#reportrange span').html(moment().subtract('days', 29).format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        $('#reportrange').daterangepicker(optionSet1, cb);
    });
</script>
</body>

</html>
