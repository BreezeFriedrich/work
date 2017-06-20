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
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../../resources/dataTables/css/jquery.dataTables.css">
    <script type="text/javascript" src="../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/scripts/echarts.js"></script>
    <script type="text/javascript" src="../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../resources/dataTables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="../../resources/scripts/swiperecord_mainchart.js?ver=17"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-lg-6 col-lg-offset-2">
        <div class="panel panel-primary">
            <div class="panel-heading">删除记录:刷卡记录</div>
            <div class="panel-body">
                <div class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="swipeRecord_startTime" class="col-lg-2 control-label">开始时间</label>
                        <div class="col-lg-10">
                            <input class="laydate-icon" id="swipeRecord_startTime" placeholder="(必要)请输入开始时间" style="height:36px;border-color:#00ff00;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="swipeRecord_endTime" class="col-lg-2 control-label">结束时间</label>
                        <div class="col-lg-10">
                            <input class="laydate-icon" id="swipeRecord_endTime" placeholder="(必要)请输入结束时间" style="height:36px;border-color:#0000ff;"/>
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
                            <button type="submit" class="btn btn-info" onclick="refreshData()">查询</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <div class="col-lg-3 col-lg-offset-1" style="display: none" id="summary">
            <div class="panel panel-primary">
                <div class="panel-heading">刷卡结果总计</div>
                <div class="panel-body">
                    <dl class="dl-horizontal">
                        <dt>总失败率</dt>
                        <dd></dd>
                        <dt>刷卡总次数</dt>
                        <dd></dd>
                        <dt>所用SAM数量</dt>
                        <dd></dd>
                        <dt>不同的刷卡设备的数量</dt>
                        <dd></dd>
                        <button type="submit" class="btn btn-info" onclick="create_table()">失败记录</button>
                    </dl>
                </div>
            </div>
        </div>
    </div>

    <div class="row-fluid">
        <div class="col-lg-11 col-lg-offset-1">
            <div class="panel panel-info" style="width: 1030px;height:550px;margin:30px auto 50px;">
                <div class="panel-heading">删除记录:刷卡记录</div>
                <div class="panel-body">
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                    <div id="container-chart" style="width: 1000px;height:450px;"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="row-fluid" style="display: none;" id="swipeFailRecord">
        <div class="col-lg-9 col-lg-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">失败记录</a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse">
                    <div class="panel-body">
                        <table id="table-swipeRecord" cellpadding="0" cellspacing="0" border="0">
                            <thead>
                            <tr>
                                <th width="200px">deviceid</th>
                                <th width="120px">deviceip</th>
                                <th width="80px">clientid</th>
                                <th width="100px">clientip</th>
                                <th width="50px">result</th>
                                <th width="200px">timestamp</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
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
