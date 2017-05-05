<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

	<head>
		<meta charset="utf-8">
		<title>亿数漫行智能锁</title>
		<link rel="icon" type="image/x-icon" href="styles/images/intellilock.png" />
		<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css" />
		<link rel="stylesheet" type="text/css" href="styles/main.css" />
		<link rel="stylesheet" type="text/css" href="styles/UserManage.css" />
		<link rel="stylesheet" type="text/css" href="styles/deviceManage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="easyui/jquery.min.js"></script>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="js/cookie_util.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="js/lockoperate.js"></script>
		<script type="text/javascript" src="js/device-manage.js"></script>
		<!-- <script type="text/javascript">
			//客户端登陆信息检查
			var user = getCookie("userId");
			if(user==null){//如果用户信息不存在进入登陆页面
  			window.location.href="login.jsp";
			}
		</script>
		 -->
		<!--<script type="text/javascript">
		//载入提示遮罩
		function closes() {
			$("#Loading").fadeOut("normal", function() {
				$(this).remove();
			});
		}
		var pc;
		$.parser.onComplete = function() {
			if(pc) clearTimeout(pc);
			pc = setTimeout(closes, 1000);
		}
	</script>-->
	</head>

	<body class="easyui-layout" style="min-width:900px;">		
		<div id="container">
			<div id="north" style="height:60px;background:#95b8e7;padding:10px;border: none;clear: both;position: relative;">
				<div class="header-brand" style="float:left;">
					<a>
						<img class="brand-logo" src="" alt="Logo">
					</a>
				</div>
				<div id="pagetitle" style="display:inline-block;float:left;width:238px;height:34px;position:absolute;top:50%;left:50%;margin-left:-118px;margin-top:-17px;">漫行智能锁管理系统</div>
				<div class="header-menu" style="float:right;display:inline-block;">
					<span id="loginStatus">登录信息</span>
					<button class="button" id="safetylogout" style="border:2px solid #b2cef3;border-radius:3px;background-color:#c4d8f3;font-size:17px;font-family:Arial, Helvetica, sans-serif;color:#305b96;padding:8px 8px;">安全退出</button>
				</div>
			</div>
			<div id="west" style="width:150px;padding:0px;float: left;">

				<div class="leftsidebar">

					<dl class="custom">
						<dt>用户管理<img src="img/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a href="#">用户列表</a>
						</dd>
						<dd>
							<a href="#">权限管理</a>
						</dd>
						<dd>
							<a href="#">数据清理</a>
						</dd>
					</dl>

					<dl class="syetem_management">
						<dt>设备管理<img src="img/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a href="#">模块状态</a>
						</dd>
					</dl>

					<dl class="statistics">
						<dt>统计分析<img src="img/left/select_xl01.png"></dt>
						<dd class="first_dd">
							<a href="#">数据查询</a>
						</dd>
						<dd>
							<a href="#">成功率分析</a>
						</dd>
						<dd>
							<a href="#">数据对比</a>
						</dd>
					</dl>

				</div>
			</div>
			<div id="center">
				<iframe style="height: 100%;width: 100%;"></iframe>
			</div>
		</div>

	</body>

</html>