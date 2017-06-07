<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/5/22
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>modulestatus</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="../../resources/styles/component/swiperecord_table.css"/>
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/modulestatus_table.js?ver=11"></script>
</head>
<body>
    <div id="container-table" style="width: 800px;margin-top: 100px;margin-left: 100px;">
        开始日：<input class="laydate-icon" id="startTime" style="width:160px;height: 25px;"/>
        结束日：<input class="laydate-icon" id="endTime" style="width:160px;height: 25px;"/>
        <button onclick="refreshData()">按时间查询</button>
        <table id="table-moduleStatus" cellpadding="0" cellspacing="0" border="1px #333" width="1000px">
            <thead>
            <tr>
                <th width="220px">deviceid</th>
                <th width="150px">deviceip</th>
                <th width="100px">status</th>
                <th width="250px">timestamp</th>
                <th width="100px">info</th>
                <th width="150px">reserved</th>
            </tr>
            </thead>
        </table>
    </div>
</body>
</html>
