<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Datatable-serverSide 自行封装请求参数和返回数据例子</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="resources/bootstrap-3.3.0/css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="resources/bootstrap-3.3.0/css/bootstrap-responsive.min.css" media="screen">
    <!-- FontAwesome -->
    <link rel="stylesheet" href="asset/lib/font-awesome-4.2.0/css/font-awesome.min.css">
    <!-- DataTables CSS start-->
    <link rel="stylesheet" href="resources/dataTables/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="resources/dataTables/plugins/integration/font-awesome/dataTables.fontAwesome.css">
    <!-- DataTables CSS end-->
    <link rel="stylesheet" href="asset/css/user-manage.css">
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12" id="content">
            <div class="row-fluid">
                <div class="navbar" id="breadcrumb">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <li>
                                <a href="#">首页</a>
                                <span class="divider">/</span>
                            </li>
                            <li class="active">用户管理</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <div class="btn-toolbar">
                        <div class="pull-right">
                            <div class="input-append">
                                <input type="text" placeholder="模糊查询" id="fuzzy-search">
                                <div class="btn-group">
                                    <button type="button" class="btn" id="btn-simple-search"><i class="fa fa-search"></i></button>
                                    <button type="button" class="btn" title="高级查询" id="toggle-advanced-search">
                                        <i class="fa fa-angle-double-down"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" id="btn-add"><i class="fa fa-plus"></i> 添加</button>
                        <button type="button" class="btn btn-danger" id="btn-del"><i class="fa fa-remove"></i> 批量删除</button>
                    </div>
                </div>
            </div>
            <div class="row-fluid" style="display:none;" id="div-advanced-search">
                <form class="form-inline well">
                    <span>姓名:</span>
                    <input type="text" class="input-medium" placeholder="姓名" id="name-search">
                    <span>职位:</span>
                    <input type="text" class="input-medium" placeholder="职位" id="position-search">
                    <span>工作地点:</span>
                    <input type="text" class="input-medium" placeholder="工作地点" id="office-search">
                    <span>编号:</span>
                    <input type="text" class="input-medium" placeholder="编号" id="extn-search">
                    <span>在线状态:</span>
                    <select class="input-small" id="status-search">
                        <option value="">全部</option>
                        <option value="1">在线</option>
                        <option value="0">离线</option>
                    </select>
                    <select class="input-small" id="role-search">
                        <option value="">全部</option>
                        <option value="1">管理员</option>
                        <option value="0">操作员</option>
                    </select>
                    <button type="button" class="btn" id="btn-advanced-search"><i class="fa fa-search"></i> 查询</button>
                </form>
            </div>
            <div class="block info-block" id="user-view">
                <div class="navbar navbar-inner block-header">
                    <div class="block-title">用户详情</div>
                    <div class="header-buttons">
                        <button type="button" class="btn btn-primary" id="btn-view-edit">修改</button>
                    </div>
                </div>
                <div class="block-content info-content clearfix">
                    <div class="row-fluid">
                        <div class="span4">
                            <label class="prop-name">编号:</label>
                            <div class="prop-value" id="extn-view"></div>
                        </div>
                        <div class="span4">
                            <label class="prop-name">姓名:</label>
                            <div class="prop-value" id="name-view"></div>
                        </div>
                        <div class="span4">
                            <label class="prop-name">角色:</label>
                            <div class="prop-value" id="role-view"></div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span4">
                            <label class="prop-name">职位:</label>
                            <div class="prop-value" id="position-view"></div>
                        </div>
                        <div class="span4">
                            <label class="prop-name">薪水:</label>
                            <div class="prop-value" id="salary-view"></div>
                        </div>
                        <div class="span4">
                            <label class="prop-name">状态:</label>
                            <div class="prop-value" id="status-view"></div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span4">
                            <label class="prop-name">入职时间:</label>
                            <div class="prop-value" id="start-date-view"></div>
                        </div>
                        <div class="span8">
                            <label class="prop-name">办公地点:</label>
                            <div class="prop-value" id="office-view"></div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span12">
                            <label class="prop-name">备注:</label>
                            <div class="prop-value"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="block info-block" id="user-add" style="display:none;">
                <div class="navbar navbar-inner block-header">
                    <div class="block-title">添加账户</div>
                    <div class="header-buttons">
                        <button type="button" class="btn btn-primary" id="btn-save-add">确定添加</button>
                        <button type="button" class="btn btn-cancel">取消</button>
                    </div>
                </div>
                <div class="block-content info-content clearfix">
                    <form id="form-add">
                        <div class="control-group">
                            <label class="control-label" for="extn-add"><span
                                    class="red-asterisk">*</span>编号:</label>
                            <div class="controls">
                                <input type="text" id="extn-add" name="extn-add">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="name-add"><span
                                    class="red-asterisk">*</span>姓名:</label>
                            <div class="controls">
                                <input type="text" id="name-add" name="name-add">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="role-add">角色:</label>
                            <div class="controls">
                                <select id="role-add" name="role-add">
                                    <option value="1" selected>管理员</option>
                                    <option value="0">操作员</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="position-add">职位:</label>
                            <div class="controls">
                                <input type="text" id="position-add" name="position-add">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="salary-add">薪水:</label>
                            <div class="controls">
                                <input type="text" id="salary-add" name="salary-add">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="start-date-add">入职时间:</label>
                            <div class="controls">
                                <input type="text" id="start-date-add" name="start-date-add">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="office-add">办公地点:</label>
                            <div class="controls">
                                <input type="text" class="xlarge" id="office-add" name="office-add">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="remark-add">备注:</label>
                            <div class="controls">
                                <input type="text" class="xxxlarge" id="remark-add" name="remark-add">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="block info-block" id="user-edit" style="display:none;">
                <div class="navbar navbar-inner block-header">
                    <div class="block-title">修改账户:<span id="title-edit"></span></div>
                    <div class="header-buttons">
                        <button type="button" class="btn btn-primary" id="btn-save-edit">保存更改</button>
                        <button type="button" class="btn btn-cancel">取消</button>
                    </div>
                </div>
                <div class="block-content info-content clearfix">
                    <form id="form-edit">
                        <div class="control-group">
                            <label class="control-label" for="extn-edit"><span
                                    class="red-asterisk">*</span>编号:</label>
                            <div class="controls">
                                <input type="text" id="extn-edit" name="extn-edit">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="name-edit"><span
                                    class="red-asterisk">*</span>姓名:</label>
                            <div class="controls">
                                <input type="text" id="name-edit" name="name-edit">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="role-edit">角色:</label>
                            <div class="controls">
                                <select id="role-edit" name="role-edit">
                                    <option value="1" selected>管理员</option>
                                    <option value="0">操作员</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="position-edit">职位:</label>
                            <div class="controls">
                                <input type="text" id="position-edit" name="position-edit">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="salary-edit">薪水:</label>
                            <div class="controls">
                                <input type="text" id="salary-edit" name="salary-edit">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="start-date-edit">入职时间:</label>
                            <div class="controls">
                                <input type="text" id="start-date-edit" name="start-date-edit">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="office-edit">办公地点:</label>
                            <div class="controls">
                                <input type="text" class="xlarge" id="office-edit" name="office-edit">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="remark-edit">备注:</label>
                            <div class="controls">
                                <input type="text" class="xxxlarge" id="remark-edit" name="remark-edit">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span12" id="div-table-container">
                    <table class="table table-striped table-bordered table-hover table-condensed" id="table-user" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" name="cb-check-all">
                            </th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>状态</th>
                            <th>入职时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot>
                        <tr>
                            <th>
                                <input type="checkbox" name="cb-check-all">
                            </th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>状态</th>
                            <th>入职时间</th>
                            <th>操作</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="asset/lib/json2.js"></script>
<!-- JQuery -->
<script src="resources/scripts/jquery-3.1.0.min.js"></script>
<!-- Bootstrap -->
<script src="resources/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<!-- SpinJS-->
<script src="asset/lib/spin-2.1.0/jquery.spin.merge.js"></script>
<!-- lhgdialog -->
<script src="asset/lib/lhgdialog-4.2.0/lhgdialog.js?skin=bootstrap2"></script>
<!-- DataTables JS-->
<script src="resources/dataTables/js/jquery.dataTables.js"></script>
<script src="resources/dataTables/js/dataTables.bootstrap.js"></script>
<!-- DataTables JS end-->
<script src="resources/scripts/component/constant.js"></script>
<script src="resources/scripts/component/user-manage.js"></script>
</body>
</html>