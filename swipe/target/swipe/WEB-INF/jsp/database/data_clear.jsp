<%--
  Created by IntelliJ IDEA.
  User: WindSpring
  Date: 2017/5/29
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/jquery.dataTables.css">
    <%--<link rel="stylesheet" type="text/css" href="../../resources/styles/component/data_clear.css"/>--%>
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.js"></script>
    <script type="text/javascript" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/data_clear.js"></script>
</head>
<body>
<div id="container-table" style="width: 1000px;height: 1900px;margin-top: 100px;margin-left: 100px;">
    <div style="width: 1000px;height: 800px;margin-top: 100px;margin-left: 100px;">

    </div>
    <div><button id="delbutton-moduleStatus">删除模块状态数据</button></div>
    <table id="table-moduleStatus" cellpadding="0" cellspacing="0" border="0" width="1000px" height="400px" border="2px solid gray" margin-top="20px";>
        <thead>
        <tr>
            <th width="220px">deviceid</th>
            <th width="150px">deviceip</th>
            <th width="100px">status</th>
            <th width="150px">timestamp</th>
            <th width="100px">info</th>
            <th width="250px">reserved</th>
        </tr>
        </thead>
    </table>
    <table id="table-swipeRecord" cellpadding="0" cellspacing="0" border="0" width="1000px" height="400px" border="2px solid red" margin-top="20px";>
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