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

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="resources/css/index.css"/>

    <link rel="stylesheet" href="resources/plugin/jQuery-contextMenu/dist/jquery.contextMenu.css" />
    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css" />
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="resources/plugin/dataTables/css/jquery.dataTables.css" />
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px; min-height:500px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 88%;}
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 88% ;}

        .md-content table thead th{
            font-size: 18px;
            font-weight: 500;
        }
        .md-content table tbody td{
            font-size: 15px;
            font-weight: 200;
            line-height: 30px;
            height: 30px;
        }
        .fixed-table_body .cd-blank{
            background: #c2c2c2 !important;
            color: #fff;
            font-size:14px;
            opacity: 50;
        }
        /*重写contextMenu.css部分内容*/
        .context-menu-list {
            min-width: 7.5em;
            max-width: 8em;
            padding: .2em 0;
            margin: 0;
            background: #2494f2;
            text-align: center;
            vertical-align: middle;
        }
        .context-menu-item {
            padding: .2em 1em;
            color: #fff;
            background-color: #2494f2;
        }
        .context-menu-item.context-menu-hover {
            background-color: #36a3ff;
        }
        .context-menu-separator {
            margin: 0;
        }
    </style>
</head>
<body>
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

    <div class="container-fluid table-odyssey" id="pcont">
        <div class="container-table"></div>

        <div class="footer">&copy;2015-2016 南京亿数信息科技有限公司 版权所有</div>

        <div class="clearfix"></div>
    </div>
</div>

<%--入住记录--%>
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-record" style="width: 680px;">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>入住记录</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <table id="table-unlockrecord" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="150px">开锁类型</th>
                        <th width="200px">开锁时刻</th>
                        <th width="180px">开锁凭据</th>
                        <th width="100px">开锁人</th>
                    </tr>
                    </thead>
                </table>
            </div>

        </div>
    </div>
</div>
<%-- 开锁信息 --%>
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-authorization" style="width: 880px;">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>开锁授权信息</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <table id="table-authorization" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="150px">开锁类型</th>
                        <th width="180px">开锁凭据</th>
                        <th width="100px">开锁人</th>
                        <th width="200px">生效时间</th>
                        <th width="200px">失效时间</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>

<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
<%--<script type="text/javascript" src="resources/js/fixed-table.js"></script>--%>
<script type="text/javascript" src="resources/js/FixedTable.js"></script>
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
<script type="text/javascript" src="resources/plugin/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/spin-2.1.0/jquery.spin.merge.js"></script>
<script type="text/javascript" src="resources/js/house_district.js"></script>
</body>

</html>