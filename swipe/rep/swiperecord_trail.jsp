<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/5/26
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>swiperecord_trail</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="../src/main/webapp/resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../src/main/webapp/resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../src/main/webapp/resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/swiperecord_trail.js"></script>
</head>
<body>
    开始日：<li class="laydate-icon" id="startTime" style="width:200px; margin-right:10px;"></li>
    结束日：<li class="laydate-icon" id="endTime" style="width:200px;"></li>

    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="container-chart" style="width: 900px;height:400px;">
    </div>

</body>
</html>
