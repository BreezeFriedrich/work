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

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/css/font-awesome.min.css" />
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
        .fixed-table_body .cd-blank{
            background: #c2c2c2 !important;
            color: #fff;
            font-size:14px;
            opacity: 50;
        }
        /*重写contextMenu.css部分内容*/
        .context-menu-list {
            /*position: absolute;*/
            /*display: inline-block;*/
            min-width: 7.5em;
            max-width: 8em;
            padding: .2em 0;
            margin: 0;
            /*font-family: inherit;*/
            /*font-size: inherit;*/
            /*list-style-type: none;*/
            background: #2494f2;
            /*border: 1px solid #bebebe;*/
            /*border-radius: .2em;*/
            /*-webkit-box-shadow: 0 2px 5px rgba(0, 0, 0, .5);*/
            /*box-shadow: 0 2px 5px rgba(0, 0, 0, .5);*/
            text-align: center;
            vertical-align: middle;
        }
        .context-menu-item {
            /*position: relative;*/
            padding: .2em 1em;
            color: #fff;
            /*-webkit-user-select: none;*/
            /*-moz-user-select: none;*/
            /*-ms-user-select: none;*/
            /*user-select: none;*/
            /*background-color: #fff;*/
            background-color: #2494f2;
        }
        .context-menu-item.context-menu-hover {
            /*color: #fff;*/
            /*cursor: pointer;*/
            background-color: #36a3ff;
        }
        .context-menu-separator {
            /*padding: 0;*/
            margin: 0;
            /*border-bottom: 1px solid #e6e6e6;*/
        }
    </style>
</head>
<body>

<!-- header -->
<jsp:include page="/jsp/header.jsp"/>
<!--header end-->

<div id="cl-wrapper" class="fixed-menu">
    <div class="container-fluid table-odyssey">
        <div id="theFixedTable"></div>

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
                <%--<button type="button" class="btn btn-primary" id="btn_search">查询</button>--%>
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
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-authorization" style="width: 930px;">
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
                        <th width="50px">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<%-- 身份证授权 --%>
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-identity">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>添加身份证开锁授权</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <form id="form-identity" class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="name" placeholder="用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="cardNumb" placeholder="身份证号码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <fieldset>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="input-prepend ">
                                            <input type="text" name="reservation" id="time-book-identity" class="form-control span4" />
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button id="submit-identity" type="button" class="btn btn-primary">提交授权</button>
                            <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">关 闭</button>
                        </div>
                    </div>
                </form>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>

<%-- 添加密码开锁授权 --%>
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-pwd">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>添加密码开锁授权</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <form id="form-pwd" class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="password" placeholder="开锁密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <fieldset>
                                <div class="control-group">
                                    <div class="controls">
                                        <div class="input-prepend ">
                                            <input type="text" name="reservation" id="time-book-pwd" class="form-control span4" />
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button id="submit-pwd" type="button" class="btn btn-primary">提交授权</button>
                            <button type="button" class="btn btn-default md-close" data-dismiss="modal">关 闭</button>
                        </div>
                    </div>
                </form>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>

<%--<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>--%>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/FixedTable.js"></script>
<!--滚动条-->
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<!--开关switch-->
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script>
<!--CSS3实现的模态框-->
<%--<script type="text/javascript" src="resources/plugin/jquery-niftymodals/dist/jquery.niftymodals.js"></script>--%>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>
<!--下拉菜单-->
<script type="text/javascript" src="resources/plugin/jquery.select2/select2.min.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.slider/js/bootstrap-slider.js"></script>
<%--<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>--%>
<%--<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>--%>
<!--表单验证-->
<%--<script type="text/javascript" src="resources/plugin/jquery.parsley/dist/parsley.js"></script>--%>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
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
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>

<script type="text/javascript" src="resources/plugin/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/spin-2.1.0/jquery.spin.merge.js"></script>
<script type="text/javascript" src="resources/js/house_landlord.js?v=4"></script>
</body>

</html>