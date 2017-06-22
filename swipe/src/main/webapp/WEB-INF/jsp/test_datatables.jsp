<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/14
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta charset="utf-8">
    <title>学习测试DataTables</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.0/css/bootstrap.min.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/jquery.dataTables.css">
    <%--<link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/dataTables.bootstrap.min.css">--%>
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <%--<script type="text/javascript" src="../../resources/dataTables/js/dataTables.bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="../../resources/scripts/test_datatables.js?ver=40"></script>
    <script type="text/javascript">
        var rootPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid" style="padding-top: 15px">
        <div class="col-lg-2">
            <div class="btn btn-default" data-toggle="collapse" href="#collapseOne">查询条件</div>
        </div>
        <div class="col-lg-10">
            <div id="collapseOne" class="collapse in">
                <%--<form>--%>
                    <%--<span>设备编号:</span> <input type="text" placeholder="设备编号" id="deviceid-search">--%>
                    <%--<span>刷卡时间:</span> <input type="text" placeholder="刷卡时间" id="endTime-search">--%>
                    <%--<span>刷卡结果:</span>--%>
                    <%--<select id="result-search">--%>
                        <%--<option value="">全部</option>--%>
                        <%--<option value="0">成功</option>--%>
                        <%--<option value="1">失败</option>--%>
                    <%--</select>--%>
                    <%--<button type="button" id="btn_search">查询</button>--%>
                <%--</form>--%>

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="deviceid-search" class="col-lg-2 control-label">SAM模块编号</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" id="deviceid-search"
                                   placeholder="请输入SAM模块编号">
                        </div>
                        <div class="col-lg-6"></div>
                    </div>
                    <div class="form-group">
                        <label for="deviceip-search" class="col-lg-2 control-label">SAM模块IP</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" id="deviceip-search"
                                   placeholder="请输入SAM模块IP">
                        </div>
                        <div class="col-lg-6"></div>
                    </div>
                    <div class="form-group">
                        <label for="startTime-search" class="col-lg-2 control-label">开始时间</label>
                        <div class="col-lg-4">
                            <input class="laydate-icon" id="startTime-search" placeholder="请输入开始时间"
                                   style="height:36px;border-color:#00ff00;"/>
                        </div>
                        <div class="col-lg-6"></div>
                    </div>
                    <div class="form-group">
                        <label for="endTime-search" class="col-lg-2 control-label">结束时间</label>
                        <div class="col-lg-4">
                            <input class="laydate-icon" id="endTime-search" placeholder="请输入结束时间"
                                   style="height:36px;border-color:#0000ff;"/>
                        </div>
                        <div class="col-lg-6"></div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label for="endTime-search" class="col-lg-2 control-label">刷卡时间</label>--%>
                        <%--<div class="col-lg-10">--%>
                            <%--<input type="text" class="form-control" id="endTime-search"--%>
                                   <%--placeholder="请输入刷卡时间">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="result-search" class="col-lg-2 control-label">刷卡结果</label>
                        <div class="col-lg-2">
                            <select class="form-control" id="result-search">
                                <option value="">全部</option>
                                <option value="0">成功</option>
                                <option value="1">失败</option>
                            </select>
                        </div>
                        <div class="col-lg-8"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-2 col-lg-10">
                            <button type="button" class="btn btn-primary" id="btn_search">查询</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="row-fluid" style="padding-top: 15px">
        <div class="col-lg-12">
            <table id="table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th width="220px">deviceid</th>
                    <th width="150px">deviceip</th>
                    <th width="100px">clientid</th>
                    <th width="150px">clientip</th>
                    <th width="100px">result</th>
                    <th width="250px">timestamp</th>
                    <th width="200px">edit</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<!--
    <div id="container-table" style="width: 1200px;">
        <div style="padding-left: 380px"><h2>刷卡记录表</h2></div>
        <table id="table" cellpadding="0" cellspacing="0" border="0" width="1200px">
            <thead>
            <tr>
                <th width="220px">deviceid</th>
                <th width="150px">deviceip</th>
                <th width="100px">clientid</th>
                <th width="150px">clientip</th>
                <th width="100px">result</th>
                <th width="250px">timestamp</th>
                <th width="200px">edit</th>
            </tr>
            </thead>
        </table>
    </div>
-->
</body>
</html>