<%--
  User: admin
  Date: 2018/2/1
  Time: 16:51
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
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

    <script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>
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
    <script type="text/javascript" src="resources/js/contextmenu.js"></script>
</head>
<body>
<div class="md-modal colored-header custom-width md-effect-9" id="reply-ticket">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>入住记录</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>

            <div class="content">
                <table id="table-moduleStatus" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="100px">type</th>
                        <th width="160px">timestamp</th>
                        <th width="220px">credential</th>
                        <th width="150px">name</th>
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
<div class="md-overlay"></div>
</body>
</html>