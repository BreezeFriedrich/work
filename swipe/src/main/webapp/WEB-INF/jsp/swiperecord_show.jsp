<%--
  Created by IntelliJ IDEA.
  User: WindSpring
  Date: 2017/6/7
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../resources/scripts/swiperecord_show.js?ver=7"></script>
</head>
<body>
    <div class="panel panel-primary"style="width: 800px;margin:30px auto;">
        <div class="panel-heading">删除记录:刷卡记录</div>
        <div class="panel-body">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="swipeRecord_startTime" class="col-sm-2 control-label">开始时间</label>
                    <div class="col-sm-10">
                        <input class="laydate-icon" id="swipeRecord_startTime" placeholder="(必要)请输入开始时间" style="width:450px;height:36px;border-color:#00ff00;"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="swipeRecord_endTime" class="col-sm-2 control-label">结束时间</label>
                    <div class="col-sm-10">
                        <input class="laydate-icon" id="swipeRecord_endTime" placeholder="(必要)请输入结束时间" style="width:450px;height:36px;border-color:#0000ff;"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option1" value="0" checked />日
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option2"  value="1"/>周
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option3"  value="2"/>月
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-danger" onclick="refreshData()">查询</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="panel panel-info"style="width: 1000px;margin:30px auto;">
        <div class="panel-heading">删除记录:刷卡记录</div>
        <div class="panel-body">
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="container-chart" style="width: 900px;height:400px;">
            </div>
        </div>
    </div>
</body>
</html>
