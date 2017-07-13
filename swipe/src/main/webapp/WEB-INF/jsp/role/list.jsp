<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>角色列表查询</title>
        <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="container">
            <%--<jsp:include page="inc.jsp"/>--%>
            <table class="table table-striped">
                <thead>
                    <tr class="success">
                        <th></th>
                        <th>角色标识</th>
                        <th>角色名称</th>
                        <th>角色字符串</th>
                        <th>
                            操作
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${roleList}" var="role">
                        <tr>
                            <td>
                                <input type="checkbox" value="${role.id}" class="roleId"/>
                            </td>
                            <td>${role.id}</td>
                            <td>${role.name}</td>
                            <td>${role.sn}</td>
                            <td>
                                <shiro:hasPermission name="/admin/role/update">
                                    <a href="${pageContext.request.contextPath}/admin/role/update/${role.id}">更新</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="/admin/role/resources">
                                    <a href="${pageContext.request.contextPath}/admin/role/resources/${role.id}">设置资源</a>
                                </shiro:hasPermission>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            角色操作：
            <shiro:lacksPermission name="/admin/role/*">
                无权限!
            </shiro:lacksPermission>
            <shiro:hasPermission name="/admin/role/add">
                <a class="btn btn-success" role="button" href="${pageContext.request.contextPath}/admin/role/add">添加角色</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="/admin/role/delete">
                <a id="deleteRoleBtn" class="btn btn-success" role="button">删除角色</a>
            </shiro:hasPermission>
        </div>

        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-3.1.0.min.js"></script>
        <script type="text/javascript">
            $(function(){
                $("#deleteRoleBtn").on("click",function(){
                    var checkedArray = [];
                    $("input[class='roleId']:checked").each(function () {
                        checkedArray.push($(this).val());
                    });
                    if(checkedArray.length == 0){
                        alert("请至少选择一个角色");
                    }
                    $.post("${pageContext.request.contextPath}/admin/role/delete",{
                        "roleIds":checkedArray
                    },function (data) {
                        if(data.success){
                            alert("删除用户成功!");
                            location.href="${pageContext.request.contextPath}/admin/role/list";

                        }
                    });
                });
            });

        </script>
    </body>
</html>
