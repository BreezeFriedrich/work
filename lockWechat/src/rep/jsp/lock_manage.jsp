<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <!--<base href="<%=basePath%>">-->
    <title>lock_manage</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <!--<link rel="stylesheet" href="css/picSrc.css"/>-->
    <style>
    	img.auto-zoom-1 {
    		width: 1rem;
    		height: 1rem;
    		max-width: 100%;
    		max-height: 100%;
    	}
    	img.auto-zoom-2_2 {
    		width: 2.2rem;
    		height: 2.2rem;
    		max-width: 100%;
    		max-height: 100%;
    	}
    	img.auto-zoom-2 {
    		width: 2rem;
    		height: 2rem;
    		max-width: 100%;
    		max-height: 100%;
    	}
    	img.auto-zoom-3 {
    		width: 3rem;
    		height: 3rem;
    		max-width: 100%;
    		max-height: 100%;
    	}
    	img.auto-zoom-4 {
    		width: 4rem;
    		height: 4rem;
    		max-width: 100%;
    		max-height: 100%;
    	}
    	img.auto-zoom-5 {
    		width: 5rem;
    		height: 5rem;
    		max-width: 100%;
    		max-height: 100%;
    	}
    	.row.pad-left {
    		padding-left: 1rem;
    	}
    	.card .card-footer {
    		/*上下 ,左右*/
    		padding: 0 0.75rem;
    	} 
    </style>
</head>
<body>
	<!--<div style="display: none;">
		<s:property value=""></s:property>
	</div>-->
  
	<div class="page-group">
        <!-- 单个page ,第一个.page默认被展示,page-current指定第一次进入展示-->
        <div class="page page-current">
            <!-- 标题栏 -->
            <header class="bar bar-nav">
            	<!--如果直接href='main.jsp',不会加载main.js-->
                <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
                <h1 class="title">门锁管理</h1>
            </header>

            <!-- 工具栏 -->
            <nav class="bar bar-tab">
                <a class="tab-item external active" href="#">
                    <span class="icon icon-home"></span>
                    <span class="tab-label">首页</span>
                </a>
            </nav>

            <!-- 这里是页面内容区 -->
            <div class="content">
            	<div class="content-block-title">门锁详情</div>
            	<div class="list-block" style="margin: 1rem 0;min-height: 3rem;line-height: 2.2rem;font-size: 1.0rem;">
					<ul>
						<li>
							<div class="item-content">
								<div class="item-media"><i class="icon icon-form-name"></i></div>
								<div class="item-inner">
									<div class="item-title label">网关名称</div>
									<div class="item-input">
										<input type="text" id="INPUT_gatewayName"/>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media"><i class="icon icon-form-email"></i></div>
								<div class="item-inner">
									<div class="item-title label">网关地址</div>
									<div class="item-input">
										<input type="text" id="INPUT_gatewayLocation"/>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media"><i class="icon icon-form-password"></i></div>
								<div class="item-inner">
									<div class="item-title label">网关备注</div>
									<div class="item-input">
										<input type="text" id="INPUT_gatewayComment"/>
									</div>
								</div>
							</div>
						</li>      
					</ul>
				</div>
				<div class="list-block">
					<div style="padding: 0.1rem 0.5rem;border: 0.1rem solid #AAAAAA;background-color: #FFFFFF;">
						<img class="auto-zoom-2_2" src="img/user_authorize.png" alt="" align="left" style="vertical-align: middle;"/>
						增加授权用户
						<img class="auto-zoom-2_2" src="img/down_arrow.png" alt="" align="right" style="vertical-align: middle;"/>
					</div>
					<!--<ul>
						<li class="item-content item-link">
							<div class="item-media"><i class="icon icon-f7"></i></div>
							<div class="item-inner">
								<div class="item-title">
									<img class="auto-zoom-1" src="img/user_authorize.png" alt=""/>
									增加授权用户
								</div>
								<div class="item-after"></div>
							</div>
						</li>
					</ul>-->
				</div>
				<div class="content-block-title">已授权用户</div>
			</div>
		</div>

        <!-- 其他的单个page内联页（如果有） -->
        <div class="page">...</div>
    </div>

  <script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
  <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
  <script type='text/javascript' src='js/lock_manage.js' charset='utf-8'></script>
  <!-- 默认必须要执行$.init(),实际业务里一般不会在HTML文档里执行，通常是在业务页面代码的最后执行 -->
  <script>
    $(function(){
        $.init();
    });
</script>
</body>
</html>