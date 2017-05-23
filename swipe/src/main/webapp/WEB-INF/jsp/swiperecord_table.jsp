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
    <script type="text/javascript" charset="utf8" src="../../resources/dataTables/js/jquery.js"></script>
    <script type="text/javascript" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/table.js"></script>
    <script type="text/javascript" src="../../resources/scripts/swiperecord_table.js"></script>
</head>

<body>
    <table id="swipeRecordTable">
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
        <tbody id="tbody">
        </tbody>
    </table>
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
    <div id="container-table" style="width: 700px;margin-top: 100px;margin-left: 100px;">
        <table id="table-swipeRecord" cellpadding="0" cellspacing="0" border="0" width="100%">
            <thead>
                <tr>
                    <th width="18%">deviceid</th>
                    <th width="18%">deviceip</th>
                    <th width="16%">clientid</th>
                    <th width="16%">clientip</th>
                    <th width="8%">result</th>
                    <th width="24%">timestamp</th>
                </tr>
            </thead>
        </table>
    </div>
</body>
</html>
