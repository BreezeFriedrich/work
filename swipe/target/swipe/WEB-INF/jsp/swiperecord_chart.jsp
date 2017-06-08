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
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.0/css/bootstrap.min.css" media="screen"/>
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../resources/scripts/swiperecord_chart.js?ver=2"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="center-block">
            <div class="panel panel-default" style="width:550px;margin-top: 100px;margin-left: 30%;">
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
                    <div id="container-chart-pie" style="width: 450px;height:400px;">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
