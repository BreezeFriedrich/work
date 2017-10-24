<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <meta charset="utf-8">
        <title>亿数漫行智能锁</title>
		<%--<link rel='icon' href='styles/images/intellilock.png ' type=‘image/x-icon’ />--%>
		<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="zTree/css/metroStyle/metroStyle.css"/>
		<%--<link rel="stylesheet" type="text/css" href="zTree/css/zTreeStyle/zTreeStyle.css"/>--%>
		<link rel="stylesheet" type="text/css" href="styles/main.css"/>
		<link rel="stylesheet" type="text/css" href="styles/UserManage.css"/>
		<link rel="stylesheet" type="text/css" href="styles/deviceManage.css"/>
		<%--<link rel="stylesheet" type="text/css" href="bootstrap-3.3.0/css/bootstrap.min.css"/>--%>
		<script type="text/javascript" src="scripts/jquery-3.2.1.min.js"></script>
		<%--<script type="text/javascript" src="zTree/js/jquery-1.4.4.min.js"></script>--%>
		<%--<script type="text/javascript" src="easyui/jquery.min.js"></script>--%>
		<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
		<%--<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>--%>
		<script type="text/javascript" src="zTree/js/jquery.ztree.core.min.js"></script>
		<script type="text/javascript" src="zTree/js/jquery.ztree.excheck.min.js"></script>
		<script type="text/javascript" src="zTree/js/jquery.ztree.exedit.min.js"></script>
		<script type="text/javascript" src="zTree/js/jquery.ztree.exhide.min.js"></script>
		<script type="text/javascript" src="scripts/cookie_util.js"></script>
		<script type="text/javascript" src="scripts/bindEvent_util.js?ver=1"></script>
		<script type="text/javascript" src="scripts/main.js?ver=7"></script>
		<script type="text/javascript" src="scripts/lockoperate.js?ver=1"></script>
		<%--<script type="text/javascript" src="scripts/device-manage.js?ver=18"></script>--%>
		<script type="text/javascript" src="scripts/tree.js?ver=21"></script>
		<%--<script type="text/javascript" src="bootstrap-3.3.0/js/bootstrap.min.js"></script>--%>
		<!-- <script type="text/javascript">
			//客户端登陆信息检查
			var user = getCookie("userId");
			if(user==null){//如果用户信息不存在进入登陆页面
  			window.location.href="login.jsp";
			}
		</script>
		 -->
		<script type="text/javascript">
			//载入提示遮罩
			function closes(){
				$("#Loading").fadeOut("normal",function(){
					$(this).remove();
					});
			}
			var pc;
			$.parser.onComplete = function(){
				if(pc) clearTimeout(pc);
				pc = setTimeout(closes, 1000);
			}
		</script>
    </head>
	
	<body class="easyui-layout" style="min-width:900px;">
		<div id='Loading' style="position:absolute;top: 50%; left: 50%;margin-left: -150px;margin-top: -150px;width:100%;height:100%;">
			<image src='styles/images/loading.svg'/>
		</div>
		<div data-options="region:'north',border:false" style="height:60px;background:#95b8e7;padding:10px">
			<div class="header-brand" style="float:left;">
                <!--<a>
                	<img class="brand-logo" src="" alt="Logo">
                </a>-->
            </div>
            <div id="pagetitle" style="display:inline-block;float:left;width:238px;height:34px;position:absolute;top:50%;left:50%;margin-left:-118px;margin-top:-17px;">漫行智能锁管理系统</div>
            <div class="header-menu" style="float:right;display:inline-block;">
            	<span id="loginStatus">登录信息</span>
            	<button class="button" id="safetylogout" style="border:2px solid #b2cef3;border-radius:3px;background-color:#c4d8f3;font-size:17px;font-family:Arial, Helvetica, sans-serif;color:#305b96;padding:8px 8px;">安全退出</button>
            </div>
		</div>
		<div data-options="region:'west',split:true,title:'菜单'" style="width:150px;padding:0px;">
			<div class="easyui-accordion" data-options="fit:true,border:false" style="width:30px,font-size:25px;">
				<div title="设备管理" style="padding-left:10px;font-size:20px;">
					<ul id="tree_unlock"></ul>
				</div>
				<div title="查询与统计" data-options="selected:true" style="padding-left:10px;font-size:20px;">
					<ul id="tree_count"></ul>
				</div>
			</div>
		</div>
		<%--<div data-options="region:'east',split:true,collapsed:true,title:'帮助'" style="width:100px;padding:10px;">--%>
			<%--<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true,dnd:true"></ul>--%>
		<%--</div>--%>
		<div data-options="region:'south',border:false" style="height:50px;background:#95b8e7;padding:10px;color:#7F93AD;font-size:14px;">©2015-2016 南京亿数信息科技有限公司 版权所有</div>
		<div data-options="region:'center',title:'详情'">
			<div class="easyui-tabs" data-options="fit:true,border:false,plain:true" id="tabs">
				<!-- <div title="欢迎" data-options="href:'welcome.jsp'" style="padding:10px"></div> -->
			</div>
		</div>
		<div id="tabsMenu" class="easyui-menu" style="width:120px;">
			<div name="close">关闭</div>
			<div name="Other">关闭其他</div>
			<div name="All">关闭所有</div>
		</div>
	</body>
</html>