<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>资源查看列表</title>
        <link href="../../../resources/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
        <script type="text/javascript" src="../../../resources/scripts/jquery-3.1.0.min.js"></script>
        <script type="text/javascript">
            window.onload=function () {
                // iframe和导航高度随table元素高度变化
                var thisheight = $(document).height();
                var myIframe = $(window.parent.document).find("#iframe");
                var west = $(window.parent.document).find("#west");
                myIframe.height(thisheight);
                west.height(thisheight);
            };
        </script>
    </head>
    <body>
        <div class="container">
            <%--<jsp:include page="inc.jsp"/>--%>
            <table class="table table-striped">
                <thead>
                    <tr class="danger">
                        <th>资源标识</th>
                        <th>资源名称</th>
                        <th>资源 permission</th>
                        <th>资源 url</th>
                        <th>资源操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${resourceList}" var="resource">
                        <tr>
                            <td>${resource.id}</td>
                            <td>${resource.name}</td>
                            <td>${resource.permission}</td>
                            <td>${resource.url}</td>
                            <td>
                                <shiro:hasPermission name="/admin/resource/*">
                                    <a href="${pageContext.request.contextPath}/admin/resource/${resource.id}">修改权限</a>
                                </shiro:hasPermission>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            权限操作：
            <shiro:lacksPermission name="/admin/resource/*">
                无权限!
            </shiro:lacksPermission>
            <shiro:hasPermission name="/admin/resource/add">
                <a class="btn btn-danger" role="button" href="${pageContext.request.contextPath}/admin/resource/add">添加权限</a>
            </shiro:hasPermission>
        </div>
    </body>
</html>
