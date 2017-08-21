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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/dataTables/css/jquery.dataTables.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/sAnalyseByDay.js?ver=9"></script>
</head>
<body>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-lg-4 col-lg-offset-2">
                <div class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="laydate_day" class="col-lg-4">查询日期：</label>
                        <div class="col-lg-8">
                            <input class="laydate-icon" id="laydate_day" placeholder="(必要)请输入日期" style="height:36px;border-color:#00ff00;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-4 col-lg-8">
                            <button type="button" class="btn btn-info" onclick="refreshData()">查询</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5 col-lg-offset-1" style="display: none" id="summary">
                <dl class="dl">
                    <dt></dt>
                    <dt></dt>
                    <dt></dt>
                    <dt></dt>
                    <button type="button" class="btn btn-info" onclick="create_table()">失败记录</button>
                </dl>
            </div>
        </div>

        <div class="row-fluid">
            <div class="col-lg-11 col-lg-offset-1">
                <div style="width: 1030px;height:480px;margin:30px auto 20px;">
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                    <div id="container-chart" style="width: 1000px;height:450px;"></div>
                </div>
            </div>
        </div>

        <div class="row-fluid">
            <div class="col-lg-11 col-lg-offset-1">
                <div style="width: 1030px;margin:0px auto 20px;">
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                    <div id="container-chart1" style="width: 1000px;height:4000px;"></div>
                </div>
            </div>
        </div>

        <div class="row-fluid" style="padding-top: 15px;display: none;" id="swipeFailRecord">
            <div class="col-lg-11 col-lg-offset-1">
                <h4>
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">失败记录</a>
                </h4>
                <div id="collapseOne" class="collapse in">
                    <table id="table-swipeRecord" class="table table-striped table-bordered table-hover" cellpadding="0" cellspacing="0" border="0">
                        <thead>
                        <tr>
                            <th width="200px">deviceid</th>
                            <th width="120px">deviceip</th>
                            <th width="80px">clientid</th>
                            <th width="100px">clientip</th>
                            <th width="80px">result</th>
                            <th width="220px">timestamp</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
