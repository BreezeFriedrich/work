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
    <script type="text/javascript" src="../../../resources/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="../../../resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <%--<script type="text/javascript" src="../../../resources/bootstrapvalidator/js/bootstrapValidator.js"></script>--%>
    <script type="text/javascript" src="../../../resources/scripts/component/laydate/laydate.js"></script>
    <script type="text/javascript" src="../../../resources/scripts/clearData.js"></script>
</head>
<body>
    <div style="width: 600px;">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label for="deviceid" class="col-sm-2 control-label">设备序号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="deviceid" placeholder="请输入设备序号">
                </div>
            </div>
            <div class="form-group">
                <label for="endTime" class="col-sm-2 control-label">截止时间</label>
                <div class="col-sm-10">
                    <li class="laydate-icon" id="endTime" style="width:200px;"></li>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label class="checkbox-inline">
                            <input type="radio" name="optionsRadiosinline" id="optionsRadios1" value="" checked>状态不限
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="optionsRadiosinline" id="optionsRadios2"  value="0">状态正常
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="optionsRadiosinline" id="optionsRadios3"  value="1">状态异常
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-info" onclick="retrieveData()">查询记录</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
