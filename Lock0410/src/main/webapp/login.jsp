<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="styles/login.css"/>
<script type="text/javascript" src="easyui/jquery.min.js">
</script>
<script type="text/javascript" src="scripts/cookie_util.js">
</script>
<script type="text/javascript" src="scripts/base64.js">
</script>
<script type="text/javascript" src="scripts/basevalue.js">
</script>
<script type="text/javascript" src="scripts/login.js">
</script>
<script type="text/javascript">

$(function(){
	$("#login").click(func_checklogin);
});

</script>
</head>

	<body>
		<div class="global">
			<div class="log log_in" tabindex='-1' id='log_in'>
				<dl>
					<dt>
						<div class='header'>
							<h3>登&nbsp;录</h3>
						</div>
					</dt>
					<dt></dt>
					<dt>
						<div class='letter' style="font-family:'Times New Roman'">
							&nbsp;&nbsp;用户名:&nbsp;<input type="text" name="" id="account" tabindex='1'/>
							<span id="account_msg"></span>
						</div>
					</dt>
					<dt>
						<div class='letter' style="font-family:'Times New Roman'">
							&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码:&nbsp;<input type="password" name="" id="pwd" tabindex='2'/>
							<span id="password_msg"></span>
						</div>
					</dt>
					<dt>
						<div>
							<input type="button" name="" id="login" value='&nbsp登&nbsp录&nbsp' tabindex='3'/>
						</div>
					</dt>
				</dl>
			</div>
		</div>
	</body>
</html>



