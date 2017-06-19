<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/19
  Time: 9:27
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
    <script type="text/javascript" src="../../resources/scripts/swiperecord_mainchart.js?ver=11"></script>
</head>
<body>
<div class="container-fluid" style="height:1000px;">
    <div class="row-fluid">
        <div class="span12">
    <div class="panel panel-primary" style="width: 800px;margin:30px auto;">
        <div class="panel-heading">删除记录:刷卡记录</div>
        <div class="panel-body">
            <div class="form-horizontal" role="form">
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
                        <button type="submit" class="btn btn-info" onclick="refreshData()">查询</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <dl class="dl-horizontal">
                <dt>
                    总失败率
                </dt>
                <dd>
                    劳力士创始人为汉斯.威尔斯多夫，1908年他在瑞士将劳力士注册为商标。
                </dd>
                <dt>
                    刷卡总次数
                </dt>
                <dd>
                    是世界上历史最悠久、延续时间最长的名表之一。
                </dd>
                <dt>
                    所用SAM数量
                </dt>
                <dd>
                    创立于1868年的万国表有“机械表专家”之称。
                </dd>
                <dt>
                    不同的刷卡设备的数量
                </dt>
                <dd>
                    卡地亚拥有150多年历史，是法国珠宝金银首饰的制造名家。
                </dd>
            </dl>
        </div>
    </div>

    <div class="row-fluid">
        <div class="span12">
    <div class="panel panel-info" style="width: 1200px;height:550px;margin:30px auto 50px;">
        <div class="panel-heading">删除记录:刷卡记录</div>
        <div class="panel-body">
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="container-chart" style="width: 1100px;height:450px;"></div>
        </div>
    </div>
        </div>
    </div>
</div>

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
</body>
</html>
