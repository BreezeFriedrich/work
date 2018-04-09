<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800'/>
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Raleway:300,200,100'/>

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css"/>
    <link rel="stylesheet" href="resources/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="resources/css/index.css"/>

    <link rel="stylesheet" href="resources/plugin/jquery.niftymodals/css/component.css"/><!-- 弹出框-->
    <link rel="stylesheet" href="resources/plugin/dataTables/css/jquery.dataTables.css"/>
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 90%;}
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 90%;}
    </style>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>

<div id="cl-wrapper" class="fixed-menu">

    <div class="container-fluid" id="pcont">
        <div class="page-head"><img src="resources/images/lb.png" class="inco-lb"><h3>房间管理</h3></div>
        <div class="col-sm-9 table0">
            <div class="block-flat table0-top">
                <%--<button type="button" class="btn btn-success btn-rad md-trigger" data-modal="md-addRoom"><i class="fa fa-plus"></i>添加房间</button>--%>
                <button id="btn-addRoom" type="button" class="btn btn-success btn-rad md-trigger"><i class="fa fa-plus"></i>添加房间</button>

                <div class="content">
                    <table id="table-room" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th width="200px">房型</th>
                            <th width="200px">房间名称</th>
                            <th width="100px">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">&copy;2015-2016 南京亿数信息科技有限公司 版权所有</div>
    <div class="clearfix"></div>
</div>

<!--添加房间-->
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-addRoom">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>添加房间</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <form class="form-horizontal" id="form-addRoom">
                    <div class="form-group">
                        <label for="select-roomType" class="col-sm-2 control-label">房型</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="select-roomType">
                                <option value="GWT001001">房型1</option>
                                <option value="GWT001002">房型2</option>
                            </select>
                        </div>
                        <div class="col-sm-6"></div>
                    </div>
                    <!--
                    <div class="form-group">
                        <label for="select-gateway" class="col-sm-2 control-label">网关</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="select-gateway">
                                <option value="GWT001001">网关1</option>
                                <option value="GWT001002">网关2</option>
                            </select>
                        </div>
                        <div class="col-sm-6"></div>
                    </div>
                    -->
                    <div class="form-group">
                        <label for="select-lock" class="col-sm-2 control-label">门锁</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="select-lock">
                                <option value="LCK001001">门锁1</option>
                                <option value="LCK001002">门锁2</option>
                            </select>
                        </div>
                        <div class="col-sm-6"></div>
                    </div>
                    <div class="form-group">
                        <label for="input-roomName" class="col-sm-2 control-label">房间名称</label>
                        <div class="col-sm-6">
                            <input id="input-roomName" type="text" class="form-control" name="roomName" placeholder="房间名称"/>
                        </div>
                        <div class="col-sm-4"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-8">
                            <button id="submit-addRoom" type="submit" class="btn btn-primary">确认新增</button>
                            <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                        </div>
                        <div class="col-sm-2"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>

<!--修改房间-->
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-editRoom">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>修改房间</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <form class="form-horizontal" id="form-editRoom">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="hidden" class="form-control" name="roomTypeId" placeholder="房型ID"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="hidden" class="form-control" name="roomId" placeholder="房间ID"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="select-lock2" class="col-sm-2 control-label">新门锁</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="select-lock2">
                                <option value="LCK001001">门锁1</option>
                                <option value="LCK001002">门锁2</option>
                            </select>
                        </div>
                        <div class="col-sm-6"></div>
                    </div>
                    <div class="form-group">
                        <label for="input-newRoomName" class="col-sm-2 control-label">房间名称</label>
                        <div class="col-sm-6">
                            <input id="input-newRoomName" type="text" class="form-control" name="newRoom" placeholder="新房间名称"/>
                        </div>
                        <div class="col-sm-4"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button id="submit-editRoom" type="submit" class="btn btn-primary">确认修改</button>
                            <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>

<%--<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>--%>
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/plugin/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/spin-2.1.0/jquery.spin.merge.js"></script>
<script type="text/javascript" src="resources/js/room.js?v=1"></script>
</body>
</html>