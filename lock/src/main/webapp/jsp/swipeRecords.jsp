<%--
  User: admin
  Date: 2018/2/26
  Time: 9:33
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>

    <%--<link rel="stylesheet" href="resources/plugin/DataTables-Bootstrap3/Bootstrap-3.3.7/css/bootstrap.min.css"/>--%>
    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/css/font-awesome.min.css" /><!--Font Awesome,为Bootstrap而创造的图标字体-->
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"/>--%>
    <link rel="stylesheet" href="resources/css/style.css?v=5" />
    <link rel="stylesheet" href="resources/css/index.css" />

    <link rel="stylesheet"  href="resources/plugin/bootstrap.daterangepicker/daterangepicker-bs3.css" />
    <%--<link rel="stylesheet" href="resources/plugin/DataTables-Bootstrap3/datatables.min.css"/>--%>
    <link rel="stylesheet" href="resources/plugin/dataTables/css/jquery.dataTables.css" />
    <%--<link rel="stylesheet" type="text/css" href="resources/plugin/dataTables/css/dataTables.bootstrap.min.css">--%>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>

<%--<div class="container-fluid">--%>
<div id="cl-wrapper" class="fixed-menu">
    <div class="container-fluid table-odyssey ">
        <div class="row-fluid" style="padding-top: 15px">
            <div class="col-lg-offset-3 col-lg-2">
                <div class="btn btn-default" data-toggle="collapse" href="#collapseOne">查询条件</div>
            </div>
            <div class="col-lg-7"></div>
            <div class="col-lg-offset-3 col-lg-9">
                <div id="collapseOne" class="collapse in">
                    <%--<form>--%>
                    <%--<span>设备编号:</span> <input type="text" placeholder="设备编号" id="deviceid-search">--%>
                    <%--<span>刷卡时间:</span> <input type="text" placeholder="刷卡时间" id="endTime-search">--%>
                    <%--<span>刷卡结果:</span>--%>
                    <%--<select id="result-search">--%>
                    <%--<option value="">全部</option>--%>
                    <%--<option value="0">成功</option>--%>
                    <%--<option value="1">失败</option>--%>
                    <%--</select>--%>
                    <%--<button type="button" id="btn_search">查询</button>--%>
                    <%--</form>--%>

                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="form-gatewayCode" class="col-lg-2 control-label">网关编码</label>
                            <div class="col-lg-4">
                                <input type="text" class="form-control" id="form-gatewayCode"
                                       placeholder="请输入网关编码">
                            </div>
                            <div class="col-lg-6"></div>
                        </div>
                        <div class="form-group">
                            <label for="form-lockCode" class="col-lg-2 control-label">门锁编码</label>
                            <div class="col-lg-4">
                                <input type="text" class="form-control" id="form-lockCode"
                                       placeholder="请输入门锁编码">
                            </div>
                            <div class="col-lg-6"></div>
                        </div>
                        <div class="form-group">
                            <label for="form-gatewayCode" class="col-lg-2 control-label">开锁时间</label>
                            <div class="col-lg-4">
                                <fieldset>
                                    <div class="control-group">
                                        <div class="controls">
                                            <div class="input-prepend ">
                                                <input type="text" name="reservation" id="timerange" class="form-control span4" />
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="col-lg-6"></div>
                        </div>
                        <div class="form-group">
                            <label for="form-openMode" class="col-lg-2 control-label">开锁方式</label>
                            <div class="col-lg-2">
                                <select class="form-control" id="form-openMode">
                                    <option value="">全部</option>
                                    <option value="1">身份证</option>
                                    <option value="2">密码</option>
                                    <option value="3">门卡</option>
                                </select>
                            </div>
                            <div class="col-lg-8"></div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-offset-2 col-lg-10">
                                <button type="button" class="btn btn-primary" id="btn_search">查询</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-lg-offset-3 col-lg-6 table-container" ><!--style="background-color: #ffffff"-->
                <table id="table" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="150px">开锁类型</th>
                        <th width="200px">开锁时刻</th>
                        <th width="180px">开锁凭据</th>
                        <th width="100px">开锁人</th>
                        <%--<th width="200px">edit</th>--%>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="col-lg-3"></div>
            <div class="col-lg-12" style="height: 38px;"></div>
        </div>
        <div class="footer">&copy;2015-2016 南京亿数信息科技有限公司 版权所有</div>
        <div class="clearfix"></div>
    </div>
</div>

<%--<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>--%>
<%--<script type="text/javascript" src="resources/plugin/DataTables-Bootstrap3/Bootstrap-3.3.7/js/bootstrap.min.js"></script>--%>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.daterangepicker/daterangepicker.js"></script>
<%--<script type="text/javascript" src="resources/plugin/DataTables-Bootstrap3/datatables.min.js"></script>--%>
<script type="text/javascript" src="resources/plugin/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/plugin/dataTables/pagination/scrolling.js"></script>
<%--<script type="text/javascript" src="resources/plugin/dataTables/js/dataTables.bootstrap.min.js"></script>--%>
<script type="text/javascript" src="resources/js/spin-2.1.0/jquery.spin.merge.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/js/swipeRecords.js?v=3"></script>
</body>
</html>