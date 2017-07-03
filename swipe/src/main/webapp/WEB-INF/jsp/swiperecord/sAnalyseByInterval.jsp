<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/19
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="../../../resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../../../resources/dataTables/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/styles/common.css"/>
    <script type="text/javascript" src="../../../resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/sAnalyseByInterval.js?ver=1"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-lg-4 col-lg-offset-2">
            <div class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="swipeRecord_startTime" class="col-lg-4">开始时间：</label>
                    <div class="col-lg-8">
                        <input class="laydate-icon" id="swipeRecord_startTime" placeholder="(必要)请输入开始时间"
                               style="height:36px;border-color:#00ff00;"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="swipeRecord_endTime" class="col-lg-4">结束时间：</label>
                    <div class="col-lg-8">
                        <input class="laydate-icon" id="swipeRecord_endTime" placeholder="(必要)请输入结束时间"
                               style="height:36px;border-color:#0000ff;"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option1" value="0"
                                       checked/>日
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option2" value="1"/>周
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option3" value="2"/>月
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
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

    <div class="row-fluid" style="display: none;" id="swipeFailRecord">
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

<!--
<div class="row-fluid">
    <div class="span12">
        <div class="accordion" id="accordion-91720">
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-91720" href="#accordion-element-6479">失败记录</a>
                </div>
                <div id="accordion-element-6479" class="accordion-body in collapse">
                    <div class="accordion-inner">
                        功能块...
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
-->
</body>
</html>
