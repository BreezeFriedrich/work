<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/5/22
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../../resources/bootstrap-3.3.0/css/bootstrap.min.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="../../../resources/dataTables/css/jquery.dataTables.css">
    <script type="text/javascript" src="../../../resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/sChart.js?ver=1"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="center-block">
            <div class="panel panel-default" style="width:550px;margin-top: 100px;margin-left: 10%;">
                <div class="panel-heading">
                    <h2 class="panel-title">
                        刷卡成功率：按时间查询
                    </h2>
                </div>
                <div class="panel-body">
                    开始日：<input class="laydate-icon" id="startTime" style="width:160px;height: 25px;"/>
                    结束日：<input class="laydate-icon" id="endTime" style="width:160px;height: 25px;"/>
                    <input class="btn btn-info" role="button" type="submit" value="查询" onclick="getPie()">
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                    <div id="container-chart-pie" style="width: 450px;height:350px;">
                    </div>
                </div>
            </div>
            <div class="panel panel-default" id="panel-table" style="margin-top: 50px;margin-left: 10%;display: none;">
                <div class="panel-heading">
                    <h2 class="panel-title">
                        失败记录
                    </h2>
                </div>
                <div class="panel-body">
                    <div id="container-table" style="width: 1000px;">
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
