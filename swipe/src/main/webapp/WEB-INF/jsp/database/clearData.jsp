<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/1
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>clearData</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../../../resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <%--<link rel="stylesheet" href="../../../resources/bootstrapvalidator/css/bootstrapValidator.css"/>--%>
    <script type="text/javascript" src="../../../resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <%--<script type="text/javascript" src="../../../resources/bootstrapvalidator/js/bootstrapValidator.js"></script>--%>
    <script type="text/javascript" src="../../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/clearData.js?ver=5"></script>
</head>
<body>
    <div class="panel panel-danger"style="width: 800px;margin: 60px auto 50px;">
        <div class="panel-heading">删除记录:设备状态</div>
        <div class="panel-body">
            <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="moduleStatus_deviceid" class="col-sm-2 control-label">设备序号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="moduleStatus_deviceid" placeholder="请输入设备序号" style="width:450px;height: 36px" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="moduleStatus_endTime" class="col-sm-2 control-label">截止时间</label>
                        <div class="col-sm-10">
                            <input class="laydate-icon" id="moduleStatus_endTime" placeholder="(必要)请输入截止时间" style="width:450px;height:36px;border-color:#ff0000;" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label class="checkbox-inline">
                                    <input type="radio" name="moduleStatus_options" id="moduleStatus_option1" value="" checked />状态不限
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" name="moduleStatus_options" id="moduleStatus_option2"  value="0"/>状态正常
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" name="moduleStatus_options" id="moduleStatus_option3"  value="1"/>状态异常
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-danger" onclick="retrieveModuleStatus()">查询/删除</button>
                        </div>
                    </div>
            </form>
        </div>
    </div>

    <div class="panel panel-danger"style="width: 800px;margin:30px auto;">
        <div class="panel-heading">删除记录:刷卡记录</div>
        <div class="panel-body">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="swipeRecord_deviceid" class="col-sm-2 control-label">设备序号</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="swipeRecord_deviceid" placeholder="请输入设备序号" style="width:450px;height: 36px"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="swipeRecord_endTime" class="col-sm-2 control-label">截止时间</label>
                    <div class="col-sm-10">
                        <input class="laydate-icon" id="swipeRecord_endTime" placeholder="(必要)请输入截止时间" style="width:450px;height:36px;border-color:#ff0000;"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option1" value="" checked />刷卡结果不限
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option2"  value="0"/>刷卡成功
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="swipeRecord_options" id="swipeRecord_option3"  value="1"/>刷卡失败
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-danger" onclick="retrieveSwipeRecord()">查询/删除</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
