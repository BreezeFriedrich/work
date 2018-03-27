<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    @font-face {
        font-family: 'iconfont';  /* project id 604475 */
        src: url('//at.alicdn.com/t/font_604475_oruz46qg4sk5u3di.eot');
        src: url('//at.alicdn.com/t/font_604475_oruz46qg4sk5u3di.eot?#iefix') format('embedded-opentype'),
        url('//at.alicdn.com/t/font_604475_oruz46qg4sk5u3di.woff') format('woff'),
        url('//at.alicdn.com/t/font_604475_oruz46qg4sk5u3di.ttf') format('truetype'),
        url('//at.alicdn.com/t/font_604475_oruz46qg4sk5u3di.svg#iconfont') format('svg');
    }
    .iconfont{
        font-family:"iconfont";
        font-size:24px;
        font-style:normal;
        -webkit-font-smoothing: antialiased;
        -webkit-text-stroke-width: 0.2px;
        -moz-osx-font-smoothing: grayscale;
        /*padding-left:20px*/
    }
    .tab-item .tab-label{
        display: block;
        font-size: .55rem;
        position: relative;
        top: .15rem;
    }
</style>
<nav class="bar bar-tab">
    <a class="tab-item external active" href="jsp/main.jsp">
        <span class="iconfont">&#xe62e;</span>
        <span class="tab-label">首页</span>
    </a>
    <a class="tab-item external" href="jsp/room/roomStatus.jsp">
        <span class="iconfont">&#xe64f;</span>
        <span class="tab-label">房态</span>
    </a>
    <a class="tab-item external" href="jsp/gateway/gateway_addGateway.jsp">
        <span class="iconfont">&#xeb59;</span>
        <span class="tab-label">网关</span>
    </a>
    <a class="tab-item external" href="jsp/record/record.jsp">
        <span class="iconfont">&#xe61e;</span>
        <span class="tab-label">记录</span>
    </a>
    <a class="tab-item external" href="jsp/alert.jsp">
        <span class="iconfont">&#xe676;</span>
        <span class="tab-label">告警</span>
    </a>
    <a class="tab-item external" href="jsp/setting.jsp">
        <span class="iconfont">&#xe78a;</span>
        <span class="tab-label">设置</span>
    </a>
</nav>