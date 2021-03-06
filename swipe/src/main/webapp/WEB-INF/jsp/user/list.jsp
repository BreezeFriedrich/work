<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/8
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>用户列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap-3.3.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $(".status").each(function(){
                var status = $(this).attr("data-status");
                var oper = $(this);
                if(status == 1){
                    oper.text("禁用");
                }else {
                    oper.text("启用");
                }
            });

            $(".status").on("click",function(){
                var oper = $(this);
                var user_id = oper.attr("data-id");
                var user_status = oper.attr("data-status");
                var update_status = (user_status == 1 ? 0: 1);
                $.post("${pageContext.request.contextPath}/admin/user/updateStatus",
                    {
                        id:user_id,
                        status:update_status
                    },function(data){
                        if(data.success){
                            alert("修改状态成功！");
                            var oper_name = (update_status == 1 ? "禁用":"启用");
                            oper.attr("data-status",update_status);
                            oper.text(oper_name);
                        }else{
                            alert("失败");
                        }
                    }
                );
            });

            // 批量删除
            $("#deleteUserBtn").on("click",function(){
                var checkedArray =[];
                $('input[class="userId"]:checked').each(function(){
                    checkedArray.push($(this).val());
                });

                if(checkedArray.length==0){
                    alert("您还没有选择要删除的内容!");
                    return;
                }
                // 这里也可以使用表单提交的方式删除
                $.ajax({
                    type:"post",
                    url:"${pageContext.request.contextPath}/admin/user/delete",
                    dataType:"json",
                    data:{
                        userIds:checkedArray,
                        testData:"testStr"
                    },
                    success:function (data) {
                        if(data.success){
                            alert("数据删除成功!");
                            location.href = "${pageContext.request.contextPath}/admin/user/list";
                        }else {
                            alert(data.errorInfo);
                        }
                    },
                    error:function () {
                        alert("您没有相应的权限删除用户数据,请联系管理员");
                    }
                });
            });
        })
    </script>
</head>
<body>
    <div class="container-kik">
    <table class="table table-striped">
        <thead>
        <tr class="info">
            <th></th>
            <th>用户标识</th>
            <th>用户名</th>
            <th>密码</th>
            <th>用户昵称</th>
            <th>用户状态</th>
            <th>用户操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="user">
            <tr>
                <td><input type="checkbox" class="userId" value="${user.id}"></td>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>
                    (密码已经加密,查询无意义)
                        <%--${user.password}--%>
                </td>
                <td>${user.nickname}</td>
                <%--<shiro:principal property="status">--%>
                    <td >
                        【<a class="status" data-id="${user.id}" data-status="${user.status}">启用</a>】
                    </td>
                <%--</shiro:principal>--%>
                <td>
                    <shiro:hasPermission name="/admin/user/update">
                        <a href="${pageContext.request.contextPath}/admin/user/update/${user.id}">更新用户信息</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/admin/user/resources">
                        <a href="${pageContext.request.contextPath}/admin/user/resources/${user.id}">查询用户权限</a>
                    </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    用户操作：
    <shiro:lacksPermission name="/admin/role/*">
        无权限!
    </shiro:lacksPermission>
    <shiro:hasPermission name="/admin/user/add">
        <a class="btn btn-primary" role="button" href="${pageContext.request.contextPath}/admin/user/add">添加用户</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="/admin/user/delete">
        <a class="btn btn-primary" role="button" href="#" id="deleteUserBtn" >删除用户</a>
    </shiro:hasPermission>
</div>
</body>
</html>
