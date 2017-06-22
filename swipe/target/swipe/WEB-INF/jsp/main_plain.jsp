<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

	<head>
		<meta charset="utf-8">
		<title>亿数刷卡管理中心</title>
		<%--<link rel="icon" type="image/x-icon" href="../../resources/styles/images/intellilock.png" />--%>
		<link rel="stylesheet" type="text/css" href="../../resources/styles/main_plain.css" />
		<script type="text/javascript" src="../../resources/scripts/component/cookie.js"></script>
		<script type="text/javascript" src="../../resources/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../../resources/scripts/main_plain.js?ver=1"></script>
		<!-- <script type="text/javascript">
			//客户端登陆信息检查
			var user = getCookie("userId");
			if(user==null){//如果用户信息不存在进入登陆页面
  			window.location.href="login.jsp";
			}
		</script>
		-->		
	</head>

	<body style="min-width:1400px;">
		<div id="container">

			<div id="north" style="height:60px;background:#95b8e7;padding:10px;border: none;clear: both;position: relative;">
				<div class="header-brand" style="float:left;">
					<a>
						<img class="brand-logo" src="" alt="Logo">
					</a>
				</div>
				<div id="pagetitle" style="display:inline-block;float:left;width:238px;height:34px;position:absolute;top:50%;left:50%;margin-left:-118px;margin-top:-17px;">亿数刷卡管理中心</div>
				<div class="header-menu" style="float:right;display:inline-block;">
					<span id="loginStatus"><shiro:principal property="nickname"/></span>
					<button class="button" id="safetylogout" style="border:2px solid #b2cef3;border-radius:3px;background-color:#c4d8f3;font-size:17px;font-family:Arial, Helvetica, sans-serif;color:#305b96;padding:8px 8px;">安全退出</button>
				</div>
			</div>

			<div id="west" style="width:150px;padding:0px;float: left;">
				<div class="leftsidebar">
					<%--<div class="line"></div>--%>
					<dl class="custom">
						<dt style="background-image: url(../../resources/styles/images/left/custom.png)">用户管理<img src="../../resources/styles/images/left/select_xl01.png"></dt>
						<shiro:hasPermission name="/admin/user/list">
							<dd class="first_dd">
								<a onclick=iframe("admin/user/list")>用户列表</a>
							</dd>
						</shiro:hasPermission>
						<shiro:hasPermission name="/admin/role/list">
							<dd>
								<a onclick=iframe("admin/role/list")>角色管理</a>
							</dd>
						</shiro:hasPermission>
						<shiro:hasPermission name="/admin/resource/list">
							<dd>
								<a onclick=iframe("admin/resource/list")>资源管理</a>
							</dd>
						</shiro:hasPermission>
						<shiro:hasPermission name="/database/**">
							<dd>
								<a onclick=iframe("database/clear")>数据清理</a>
							</dd>
						</shiro:hasPermission>
					</dl>
					<!--
					<dl class="deviceManage">
						<dt style="background-image: url(../../resources/styles/images/left/device_manage.png)">设备管理<img src="../../resources/styles/images/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a onclick=iframe("dispatcher/modulestatus_table.jsp")>模块状态-表</a>
						</dd>
					</dl>
					<dl class="statistics">
						<dt style="background-image: url(../../resources/styles/images/left/statistics.png)">统计分析<img src="../../resources/styles/images/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a onclick=iframe("dispatcher/swiperecord_table.jsp")>数据查询-表</a>
						</dd>
						<dd>
							<a onclick=iframe("dispatcher/swiperecord_chart.jsp")>成功率分析-图</a>
						</dd>
						<dd>
							<a onclick=iframe("dispatcher/swiperecord_show.jsp")>数据对比</a>
						</dd>
					</dl>
					-->
					<dl class="deviceManage">
						<dt style="background-image: url(../../resources/styles/images/left/device_manage.png)">模块状态<img src="../../resources/styles/images/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a onclick=iframe("dispatcher/modulestatus_table.jsp")>模块状态表</a>
						</dd>
					</dl>
					<dl class="statistics">
						<dt style="background-image: url(../../resources/styles/images/left/statistics.png)">刷卡记录<img src="../../resources/styles/images/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a onclick=iframe("dispatcher/swiperecord_table.jsp")>总记录表</a>
						</dd>
						<dd>
							<a onclick=iframe("dispatcher/swiperecord_chart.jsp")>成功率饼图</a>
						</dd>
						<dd>
							<a onclick=iframe("dispatcher/swiperecord_show.jsp")>成功率柱状图</a>
						</dd>
						<dd>
							<a onclick=iframe("dispatcher/swiperecord_mainchart.jsp")>失败率-频度-并发量</a>
						</dd>
						<dd>
							<a onclick=iframe("dispatcher/analyse_byday.jsp")>单日分析</a>
						</dd>
					</dl>
					<dl class="statistics">
						<dt style="background-image: url(../../resources/styles/images/left/statistics.png)">测试<img src="../../resources/styles/images/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a onclick=iframe("dispatcher/test_datatables.jsp")>DataTables</a>
						</dd>
					</dl>
				</div>
			</div>

			<div id="center" style="position:relative;float: left;width:1250px;">
				<iframe id="iframe" style="height: 100%;width: 100%;" frameborder="0" scrolling="no"></iframe>
            </div>
		</div>

	</body>

</html>