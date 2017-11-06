<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>用户登录</title>
        <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet">
        <style type="text/css">
            .loginTitle{
                text-align: center;
                font-family: 微软雅黑;
            }
        </style>
    </head>

    <body>
        <div class="container" style="padding-left:auto;padding-top: 150px;width: 60%">
            <div class="loginTitle">
                <h1>刷卡管理中心</h1>
            </div>
            <hr>
            <form class="form-horizontal" role="form" method="post" action="${pageContext.request.contextPath}/login">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputEmail3" placeholder="请输入用户名" name="username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword3" placeholder="请输入密码" name="password">
                    </div>
                </div>
                <%--<div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox"> Remember me
                            </label>
                        </div>
                    </div>
                </div>--%>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>

            <span class="label label-danger">${msg}</span>

            <br><br>

            <div class="panel panel-default">
                <div class="panel-body">
                    <span>©2015-2016 南京亿数信息科技有限公司 版权所有</span>
                    <%--<span>Contact us：<a href="https://www.yishutech.com">https://www.yishutech.com</a> </span>--%>
                    <span>欢迎反馈软件问题,email：<a>geysererupt@163.com</a> </span>
                </div>
            </div>
        </div>
    </body>
</html>
