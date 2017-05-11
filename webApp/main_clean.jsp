<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>左侧导航</title>

		<head>

			<style type="text/css">
				#bg {
					background-image: url(img/content/dotted.png);
				}
				img {
					border: none;
				}
				dl,dt,dd {
					display: block;
					margin: 0;
				}				
				a {
					text-decoration: none;
				}
				* {
					font-family: '微软雅黑';
					font-size: 12px;
					color: #626262;
				}
				
				html,body {
					height: 100%;
				}
				body {
					margin: 0;
					padding: 0;
					overflow-x: hidden;
				}
				.container {
					width: 100%;
					height: 100%;
					margin: auto;
				}				
				
				/*left*/
				
				.leftsidebar {
					width: 160px;
					height: auto !important;
					overflow: visible !important;
					position: fixed;
					height: 100% !important;
					background-color: #3992d0;
				}
				
				.line {
					height: 2px;
					width: 100%;
					background-image: url(img/left/line_bg.png);
					background-repeat: repeat-x;
				}
				
				.leftsidebar dt {
					padding-left: 40px;
					padding-right: 10px;
					background-repeat: no-repeat;
					background-position: 10px center;
					color: #f5f5f5;
					font-size: 14px;
					position: relative;
					line-height: 48px;
					cursor: pointer;
				}
				
				.leftsidebar dd {
					background-color: #317eb4;
					padding-left: 40px;
				}
				
				.leftsidebar dd a {
					color: #f5f5f5;
					line-height: 20px;
				}
				
				.leftsidebar dt img {
					position: absolute;
					right: 10px;
					top: 20px;
				}
				
				.custom dt {
					background-image: url(img/left/custom.png)
				}
				.syetem_management dt {
					background-image: url(img/left/device_manage.png)
				}
				.statistics dt {
					background-image: url(img/left/statistics.png)
				}
				
				.leftsidebar dl dd:last-child {
					padding-bottom: 10px;
				}
			</style>

		</head>

		<body id="bg">

			<div class="container">

				<div class="leftsidebar">
					<div class="line"></div>
					
					<dl class="custom">
						<dt onClick="changeImage()">用户管理<img src="img/left/select_xl01.png"></dt>
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
						<dd>
							<a href="#">微功能管理</a>
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

			<script type="text/javascript" src="js/jquery.js"></script>
			<script type="text/javascript">
				$(".leftsidebar dt").css({
					"background-color": "#3992d0"
				});
				$(".leftsidebar dt img").attr("src", "img/left/select_xl01.png");
				$(function() {
					$(".leftsidebar dd").hide();
					$(".leftsidebar dt").click(function() {
						$(".leftsidebar dt").css({
							"background-color": "#3992d0"
						})
						$(this).css({
							"background-color": "#317eb4"
						});
						$(this).parent().find('dd').removeClass("menu_chioce");
						$(".leftsidebar dt img").attr("src", "img/left/select_xl01.png");
						$(this).parent().find('img').attr("src", "img/left/select_xl.png");
						$(".menu_chioce").slideUp();
						$(this).parent().find('dd').slideToggle();
						$(this).parent().find('dd').addClass("menu_chioce");
					});
				})
			</script>

			<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
				<p>适用浏览器：IE8、360、FireFox、Chrome、Safari、Opera、傲游、搜狗、世界之窗.</p>
				<p>来源：
					<a href="http://sc.chinaz.com/" target="_blank">站长素材</a>
				</p>
			</div>
		</body>

</html>