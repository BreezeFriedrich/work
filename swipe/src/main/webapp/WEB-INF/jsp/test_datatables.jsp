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
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/test_datatables.js?ver=2"></script>
    <script type="text/javascript">
        var rootPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
<form>
    <span>设备编号:</span> <input type="text" placeholder="设备编号" id="deviceid-search">
    <span>刷卡时间:</span> <input type="text" placeholder="刷卡时间" id="timestamp-search">
    <span>刷卡结果:</span> <select id="result-search">
    <option value="">全部</option>
    <option value="0">成功</option>
    <option value="1">失败</option>
</select>
    <button type="button" id="btn_search">查询</button>
    <!--
    <a href="#" data-column="0">影藏编号</a>
    <a href="#" data-column="1">影藏名称</a>
    <a href="#" data-column="2">影藏状态</a>
    <a href="#" data-column="3">影藏电话</a>
    <a href="#" data-column="4">影藏网址</a>
    <a href="#" data-column="5">影藏操作</a>
    -->
</form>
<!--
<table id="table" class="display">
    <thead>
    <tr>
        <th>编号</th>
        <th>名称</th>
        <th>状态</th>
        <th>电话</th>
        <th>网址</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>
-->
    <div id="container-table" style="width: 1000px;">
        <div style="padding-left: 380px"><h2>刷卡记录表</h2></div>
        <table id="table" cellpadding="0" cellspacing="0" border="0" width="1000px">
            <thead>
            <tr>
                <th width="220px">deviceid</th>
                <th width="150px">deviceip</th>
                <th width="100px">clientid</th>
                <th width="150px">clientip</th>
                <th width="100px">result</th>
                <th width="250px">timestamp</th>
            </tr>
            </thead>
        </table>
    </div>
</body>
</html>