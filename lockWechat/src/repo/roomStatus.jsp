<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>亿数智能门锁</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="resources/img/favicon.png" type="image/x-icon">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css"/>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css"/>

    <style>
        img.auto-zoom-1 {
            width: 1rem;
            height: 1rem;
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
    </style>
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <script type="text/javascript" src="resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="resources/js/room/fixed-table.js"></script>
    <style>
        .fixed-table-box{ position: absolute; top: 0; left: 0; right: 0; bottom: 0px}
        .fixed-table_body-wraper{
            /* height: 260px; */
        }
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/
            position: absolute; top: 51px; left: 0; right: 0; bottom: 0px}

        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/
            position: absolute; top: 51px; left: 0; right: 0; bottom: 0px
        }

        .w-50{
            width: 55px !important;overflow: hidden;
        }
        .w-80{
            width: 80px;
        }

        .w-81{
            width: 80px;
        }
    </style>
</head>
<body>

<div class="page-group">
    <div class="page page-current" id="page-1516691372553">
        <header class="bar bar-nav">
            <a class="icon icon-left pull-left" href="javascript:history.go(-1);"></a>
            <h1 class="title">房态</h1>
            <a  href="jsp/room/cal.jsp" class="rili"><img src="resources/images/rili.png" ></a>
        </header>

        <div class="content">
            <div class="fixed-table-box row-col-fixed">
                <!-- 表头 start -->
                <div class="fixed-table_header-wraper">
                    <table class="fixed-table_header" cellspacing="0" cellpadding="0" border="0">
                        <thead >
                        <tr id="cal">
                            <td  class="fixed-table_header-0" data-fixed="true"><div class="table-cell w-80">日历表</div></td>
                            <td  class="fixed-table_header-1" ><a><div class="table-cell w-50"><br/></div></a></td>
                            <td  class="fixed-table_header-1"><a><div class="table-cell w-50"><br/></div></a></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                            <td  class="fixed-table_header-1"><div class="table-cell w-50"><br/></div></td>
                        </tr>
                        </thead>
                    </table>
                </div>
                <!-- 表头 end -->
                <!-- 表格内容 start -->
                <div class="fixed-table_body-wraper">
                    <table class="fixed-table_body" cellspacing="0" cellpadding="0" border="0">
                        <tbody id="roomStatus">
                        </tbody>
                    </table>
                </div>
                <!-- 表格内容 end -->

                <!-- 固定列 start -->
                <div class="fixed-table_fixed fixed-table_fixed-left">
                    <div class="fixed-table_header-wraper">
                        <table class="fixed-table_header" cellspacing="0" cellpadding="0" border="0">
                            <thead>
                            <tr>
                                <td class="fixed-table_header-0" ><div class="table-cell w-80 wx-llb">日历表</div></td>
                            </tr>
                            </thead>
                        </table>
                    </div>

                    <div class="fixed-table_body-wraper">
                        <table class="fixed-table_body  fixed-table_body-left" cellspacing="0" cellpadding="0" border="0">
                            <tbody id="roomList">
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- 固定列 end -->
            </div>

            <script>
                $(".fixed-table-box").fixedTable();
            </script>
        </div>
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type="text/javascript" src="resources/js/room/room.js"></script>

</body>
</html>
