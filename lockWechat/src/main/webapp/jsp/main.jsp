
<%--
  ~ Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
  ~ Nanjing yishu information technology co., LTD. All Rights Reserved.
  --%>

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html class="pixel-ratio-1">
<head>
    <base href="<%=basePath%>"><!--<base href=".">-->
    <title>亿数智能门锁</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="initial-scale=1,maximum-scale=1">
    <link rel="shortcut icon" href="resources/img/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>
    <link rel="stylesheet" href="resources/css/main.css">
    <style>
        .card .card-footer {
            /*上下 ,左右*/
            padding: 0 0.75rem;
        }

        /*徽章badge*/
        .badge {
            display: inline-block;
            min-width: 10px;
            padding: 3px 7px;
            font-size: 12px;
            font-weight: bold;
            line-height: 1;
            color: #fff;
            text-align: center;
            white-space: nowrap;
            /*vertical-align: baseline;*/
            background-color: #999;
            border-radius: 10px;
        }
        .badge:empty {
            display: none;
        }
        li.inset{
            margin-left: .75rem;
            margin-right: .75rem;
            border-radius: .35rem;
        }
    </style>

    <link rel="apple-touch-icon-precomposed" href="http://m.sui.taobao.org/assets/img/apple-touch-icon-114x114.png">
</head>
  <body>
    <div class="page-group">
      <div class="page page-current" id="page-index">
          <header class="bar bar-nav">
              <a class="icon icon-menu pull-left open-panel" data-panel=".panel-left"></a>
              <h1 class="title">主页</h1>
          </header>
          <!-- 工具栏 -->
          <nav class="bar bar-tab">
              <a class="tab-item external active" href="jsp/main.jsp">
                  <span class="icon icon-home"></span>
                  <span class="tab-label">首页</span>
              </a>
              <a class="tab-item external" href="jsp/room/roomStatus.jsp">
                  <span class="icon icon-code"></span>
                  <span class="tab-label">房态</span>
              </a>
              <a class="tab-item external" href="jsp/gateway/gateway_addGateway.jsp">
                  <span class="icon icon-browser"></span>
                  <span class="tab-label">网关</span>
              </a>
              <a class="tab-item external" href="jsp/record/record.jsp">
                  <span class="icon icon-search"></span>
                  <span class="tab-label">记录</span>
              </a>
              <a class="tab-item external" href="jsp/alert.jsp">
                  <span class="icon icon-phone"></span>
                  <span class="tab-label">告警<span class="badge" style="vertical-align: -50%;"></span></span>
              </a>
              <a class="tab-item external" href="jsp/setting.jsp">
                  <span class="icon icon-settings"></span>
                  <span class="tab-label">设置</span>
              </a>
          </nav>
          <!-- 这里是页面内容区 -->
          <div class="content native-scroll">
              <div class="list-block media-list tjrzxx">
                  <ul>
                      <!--
                      <li>
                          <a href="javascript:void(0);" class="item-link item-content item-gateway">
                              <div class="item-media"><img src="resources/images/inco-gateway.png" width="44"></div>
                              <div class="item-inner">
                                  <div class="item-title-row">
                                      <div class="item-title">网关3_25</div>
                                  </div>
                                  <div class="item-subtitle gateway-red">连接失败</div>
                              </div>
                          </a>
                      </li>
                      <li>
                          <a href="javascript:void(0);" class="item-link item-content item-gateway gateway-linegreen">
                              <div class="item-media"><img src="resources/images/inco-doorlock.png" width="44"></div>
                              <div class="item-inner  item-none">
                                  <div class="item-title-row">
                                      <div class="item-title">门锁1</div>
                                  </div>
                                  <div class="item-subtitle gateway-green">正常</div>
                              </div>
                          </a>
                      </li>
                      -->
                  </ul>
              </div>
              <!--
              <div class="list-block media-list inset">
                  <ul>
                      <li>
                          <a href="javascript:void(0);" onclick="javascript:console.log('lock2')" class="item-link item-content item-gateway gateway-linered">
                              <div class="item-media"><img src="resources/images/inco-doorlock.png" width="44"></div>
                              <div class="item-inner item-battery">
                                  <div class="item-title-row">
                                      <div class="item-title">门锁1</div>
                                  </div>
                                  <div class="item-subtitle gateway-red">电量不足</div>
                              </div>
                          </a>
                      </li>
                  </ul>
              </div>
              -->
          </div>
      </div>
    </div>
    <div class="panel-overlay"></div>
    <div class="panel panel-left panel-reveal">
        <div class="content-block content-block-my">
            <div class="my-yonghu">
                <img src="resources/images/my-tx.png" class="my-tx">
                <div class="my-iphone"></div>
            </div>
            <div class="list-block list-my">
                <ul>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-code"></i></div>
                        <div class="item-inner my-inner">
                            <div class="item-title">房态</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-my1"></i></div>
                        <div class="item-inner my-inner">
                            <div class="item-title">添加网关</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-my2"></i></div>
                        <div class="item-inner my-inner">
                            <div class="item-title">查询统计</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-my3"></i></div>
                        <div class="item-inner my-inner">
                            <div class="item-title">异常警示</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-my4"></i></div>
                        <div class="item-inner my-inner">
                            <div class="item-title">系统设置</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <input type="hidden" id="INPUT_hidden" value="${ownerPhoneNumber}"/>

    <%--<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>--%>
    <script type="text/javascript" src="resources/js/zepto.min.js"></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='resources/js/main.js?v=1' charset='utf-8'></script>
</body>
</html>