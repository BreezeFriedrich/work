<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/9
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="description" content="单日分析"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../resources/scripts/analyse_byday.js?ver=5"></script>
</head>
<body>
    <div>
        <div class="panel panel-info"style="width: 1000px;height:650px;margin:30px auto 50px;">
            <div class="panel-heading">删除记录:刷卡记录</div>
            <div class="panel-body">
                <div style="height:36px;border-width:1px;border-style:solid;border-color:#bce8f1;opacity:0.7;">
                    <label for="laydate_day" class="col-sm-offset-1 col-sm-2 control-label" style="line-height:30px;font-size:25px;vertical-align:middle;">查询日期</label>
                    <div class="col-sm-5">
                        <input class="laydate-icon" id="laydate_day" placeholder="请输入日期" style="width:250px;height:36px;border-color:#31b0d5;"/>
                    </div>
                    <div class="col-sm-offset-1 col-sm-1">
                        <button type="submit" class="btn btn-info" onclick="getChart()">查询</button>
                    </div>
                </div>
                <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                <div id="container-chart" style="width:900px;height:450px;margin-top:100px;"></div>
            </div>
        </div>
    </div>
</body>
</html>
