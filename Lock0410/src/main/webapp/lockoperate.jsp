<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <title>lockoperate.jsp</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css"/>

	<link rel="stylesheet" type="text/css" href="styles/main.css"/>
	<link rel="stylesheet" type="text/css" href="styles/UserManage.css"/>
	<link rel="stylesheet" type="text/css" href="styles/deviceManage.css"/>
	<script type="text/javascript" src="scripts/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="scripts/cookie_util.js"></script>
	<script type="text/javascript" src="scripts/lockoperate.js?ver=110"></script>
  </head>
  
	<body>
		<div id="loading" style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; background: #ffffff; text-align: center; padding-top: 20%;"></div>

		<div id="dg" title="DataGrid" style="padding:0px ;margin:0px;width:100%;height:515px;">
			<table class="easyui-datagrid" id="lockoperateDg" data-options="singleSelect:true,fit:true,fitColumns:true,rownumbers:'true',loadMsg:'正在使用洪荒之力。。。',showFooter:'true',toolbar:'#lockoperateTb'," >
				<thead frozen="true">
					<tr>
						<th data-options="field:'lockCode'" width="140" sortable="true">lockCode</th>
						<th data-options="field:'gatewayCode'" width="130">gatewayCode</th>
						<th data-options="field:'openMode',align:'center'" width="70" sortable="true">openMode</th>
						<th data-options="field:'timetag',align:'center'" width="160" sortable="true">timetag</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'cardNumb'" width="80" sortable="true">cardNumb</th>
						<th data-options="field:'name'" width="60">name</th>
						<th data-options="field:'password',align:'left'" width="60">password</th>
					</tr>
				</thead>
			</table>
			<div id="lockoperateTb" style="padding:5px;height:auto">
				<div>
					Date From: <input id="startTime" class="easyui-datebox" style="width:120px;margin-left:40px;" required="true">
					To: <input id="endTime" class="easyui-datebox" style="width:120px;margin-left:40px;" required="required">
					<!-- <select id="lockoperate_combotree" class="easyui-combotree" style="width:200px;" data-options="url:'lockoperate/findDeviceTree.do?ownerPhoneNumber=18255683932',multiple:'true',prompt:'网关门锁查询'" checkbox="true"></select> -->
					<input id="lockoperate_combotree" style="width:200px;">
					<input id="cardNumbText" class="easyui-textbox" data-options="prompt:'按身份证查询',validType:'cardNumb'" style="width:150px;height:26px">
					<input id="pwdText" class="easyui-textbox" data-options="prompt:'按密码查询'" style="width:150px;height:26px">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:doSearch()">查询</a>
				</div>
			</div>
			<!-- <div id="lockoperatePag" style="background:#efefef;border:1px solid #ccc;"></div> -->
		</div>
		<div class="easyui-panel" style="width:100%">
			<div id="pagi" class="easyui-pagination" data-options="layout:['list','sep','first','prev','links','next','last','sep','refresh']"></div>
		</div>
	</body>
</html>
