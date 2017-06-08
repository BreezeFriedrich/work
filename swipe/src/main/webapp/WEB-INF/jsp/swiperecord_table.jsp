<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/5/22
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>swiperecord_table</title>
    <link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="../../resources/styles/component/swiperecord_table.css"/>
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.js"></script>
    <script type="text/javascript" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/table.js"></script>
    <script type="text/javascript" src="../../resources/scripts/swiperecord_table.js?ver=1"></script>
</head>

<body>
    <%--<table id="swipeRecordTable">--%>
        <%--<caption>刷卡记录表</caption>--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th>deviceid</th>--%>
            <%--<th>deviceip</th>--%>
            <%--<th>clientid</th>--%>
            <%--<th>clientip</th>--%>
            <%--<th>result</th>--%>
            <%--<th>timestamp</th>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody id="tbody">--%>
        <%--</tbody>--%>
    <%--</table>--%>

    <!--
    <table id="swipeRecordTable2">
        <caption>刷卡记录表</caption>
        <thead>
        <tr>
            <th>deviceid</th>
            <th>deviceip</th>
            <th>clientid</th>
            <th>clientip</th>
            <th>result</th>
            <th>timestamp</th>
        </tr>
        </thead>
        <tbody id="tbody2">
        </tbody>
    </table>
    -->
    <div id="container-table" style="width: 1000px;margin-top: 100px;margin-left: 100px;">
        <div style="padding-left: 380px"><h2>刷卡记录表</h2></div>
        <table id="table-swipeRecord" cellpadding="0" cellspacing="0" border="0" width="1000px">
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
