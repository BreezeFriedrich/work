<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>

<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:300,200,100' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css" />
    <%--<link rel="stylesheet" href="resources/css/font-awesome.4.6.0.css"/>--%>
    <link rel="stylesheet" href="resources/css/font-awesome.min.css" />
    <link rel="stylesheet" href="resources/css/style.css" />
    <link rel="stylesheet" href="resources/css/index.css" />

    <link rel="stylesheet" href="resources/plugin/jquery.niftymodals/css/component.css" /><!-- 弹出框-->
    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <!--锁定表头和固定列插件FixedTable-->
    <link rel="stylesheet" href="resources/css/fixed-table.css" />
    <%--<link rel="stylesheet" href="resources/plugin/FixedTable/fixed-table.css" />--%>
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{ }
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 90% }
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 90% ; }
    </style>
</head>
<body>
<!-- header -->
<jsp:include page="/jsp/header.jsp"/>
<!--header end-->

<!--页面内容-->
<div id="cl-wrapper" class="fixed-menu">

    <!--表格内容-->
    <div class="container-fluid "  id="pcont" >
        <!--市级内容显示-->
        <div class="page-head"><img src="resources/images/lb.png" class="inco-lb"><h3>南京市(管理地区)</h3></div>

        <!--管理模块-->
        <div class="col-sm-9 table0">

            <div class="block-flat table0-top">
                <!--新增地区按钮-->
                <button type="button" class="btn btn-success btn-rad md-trigger" data-modal="reply-ticket"><i class="fa fa-plus"></i>新增地区</button>
                <!-- 新增地区-->
                <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-ticket">
                    <div class="md-content">
                        <div class="block-flat">
                            <div class="header">
                                <h3>新增地区</h3>
                                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            </div>
                            <div class="content">
                                <form class="form-horizontal" role="form">
                                    <!--新增下级电话-->
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <input type="text" class="form-control" name="juniorPhoneNumber"  placeholder="下级电话">
                                        </div>
                                    </div>
                                    <!--新增下级名称-->
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <input type="text" class="form-control" name="juniorName" placeholder="下级显示名称">
                                        </div>
                                    </div>
                                    <!--新增下级地址-->
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <input type="email" class="form-control" name="juniorLocation" placeholder="下级地址">
                                        </div>
                                    </div>
                                    <!--新增按钮-->
                                    <div class="form-group" >
                                        <div class="col-sm-10">
                                            <button type="button" class="btn btn-primary" onclick="addJunior(this.form)">确认新增</button>
                                            <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="md-overlay"></div>
                <!-- 新增地区  end-->

                <!--地区内容表格-->
                <div class="content">
                    <table>
                        <thead>
                        <tr>
                            <th style="width:50%;">地区名称</th>
                            <th>地址</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody id="showQ">
                        <%--<tr>--%>
                        <%--<td style="width:30%;">雨花台区</td>--%>
                        <%--<td>南京市雨花台区</td>--%>
                        <%--<td class="text-center"> <a class="label label-danger btn btn-danger btn-xs" href="#"><i class="fa fa-times"></i></a></td>--%>
                        <%--</tr>--%>
                        </tbody>
                    </table>

                    <!-- 修改
                    <div class="md-modal2 colored-header custom-width md-effect-9" id="reply-ticket2">
                        <div class="md-content">
                            <div class="block-flat">
                                <div class="header">
                                    <h3>修改地区信息</h3>
                                    <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                </div>
                                <div class="content">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="email" class="form-control" id="inputEmail3" placeholder="地区名称">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <input type="password" class="form-control" id="inputPassword3" placeholder="地址">
                                            </div>
                                        </div>
                                        <div class="form-group" >
                                            <div class="col-sm-12" >
                                                <button type="submit" class="btn btn-primary">确认修改</button>
                                                <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="md-overlay"></div>
                    修改 -->

                    <!--分页-->
                    <!--<ul class="pagination pag-left ">
                          <li class="disabled"><a href="#">&laquo;</a></li>
                          <li class="active"><a href="#">1</a></li>
                          <li><a href="#">2</a></li>
                          <li><a href="#">3</a></li>
                          <li><a href="#">4</a></li>
                          <li><a href="#">5</a></li>
                          <li><a href="#">&raquo;</a></li>
                        </ul>-->
                    <!--分页END-->
                    <div class="clearfix"></div>
                </div>
                <!--地区内容表格END-->
            </div>

        </div>
        <!--管理模块END-->

    </div>
    <!--表格内容END-->

    <div class="footer">2015-2016  南京亿数信息科技有限公司 版权所有</div>
    <div class="clearfix"></div>
</div>
<script type="text/javascript" src="resources/plugin/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/fixed-table.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nanoscroller/jquery.nanoscroller.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/general.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.niftymodals/js/jquery.modalEffects.js"></script>

<script type="text/javascript" src="resources/plugin/jquery.icheck/icheck.min.js"></script>
<script type="text/javascript" src="resources/plugin/behaviour/voice-commands.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="resources/plugin/jquery.parsley/dist/parsley.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.nestable/jquery.nestable.js"></script>
<script type="text/javascript" src="resources/plugin/jquery.ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.switch/bootstrap-switch.js"></script>
<script type="text/javascript" src="resources/plugin/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/js/manageJs.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $(".navbar-collapse ul:first li:eq(4)").addClass("active");

        //initialize the javascript
        App.init();
        //App.dashBoard();
        /*Sparklines*/
        $(".spk1").sparkline([2,4,3,6,7,5,8,9,4,2,6,8,8,9,10], { type: 'bar', width: '80px', barColor: '#4A8CF7'});
        $(".spk2").sparkline([4,6,7,7,4,3,2,1,4,4 ,5,6,5], { type: 'discrete', width: '80', lineColor: '#4A8CF7',thresholdValue: 4,thresholdColor: '#ff0000'});
        $(".spk4").sparkline([2,4,3,6,7,5,8,9,4,2,10], { type: 'bar', width: '80px', height: '30px',barColor: '#EA6153'});
        $(".spk5").sparkline([5,3,5,6,5,7,4,8,6,9,8], { type: 'bar', width: '80px', height: '30px',barColor: '#4AA3DF'});

        $(".spk3").sparkline([5,6,7,9,9,5,3,2,2,4,6,7], {
            type: 'line',
            lineColor: '#258FEC',
            fillColor: '#4A8CF7',
            spotColor: false,
            width: '80px',
            minSpotColor: false,
            maxSpotColor: false,
            highlightSpotColor: '#1e7ac6',
            highlightLineColor: '#1e7ac6'});

        //Maps
        $('#world-map').vectorMap({
            map: 'world_mill_en',
            backgroundColor: 'transparent',
            regionStyle: {
                initial: {
                    fill: '#38c3c1',
                },
                hover: {
                    "fill-opacity": 0.8
                }
            },
            markerStyle:{
                initial:{
                    r: 10
                },
                hover: {
                    r: 12,
                    stroke: 'rgba(255,255,255,0.8)',
                    "stroke-width": 4
                }
            },
            markers: [
                {latLng: [41.90, 12.45], name: '1.512 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [1.3, 103.8], name: '940 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [51.511214, -0.119824], name: '530 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [40.714353, -74.005973], name: '340 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
                {latLng: [-22.913395, -43.200710], name: '1.800 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}}
            ]
        });

        /*Pie Chart*/
        var data = [
            { label: "Google", data: 50},
            { label: "Dribbble", data: 15},
            { label: "Twitter", data: 12},
            { label: "Youtube", data: 14},
            { label: "Microsoft", data: 14}
        ];

        $.plot('#ticket-chart', data, {
            series: {
                pie: {
                    show: true,
                    innerRadius: 0.5,
                    shadow:{
                        top: 5,
                        left: 15,
                        alpha:0.3
                    },
                    stroke:{
                        width:0
                    },
                    label: {
                        show: false
                    },
                    highlight:{
                        opacity: 0.08
                    }
                }
            },
            grid: {
                hoverable: true,
                clickable: true
            },
            colors: ["#5793f3", "#19B698","#dd4444","#fd9c35","#fec42c","#d4df5a","#5578c2"],
            legend: {
                show: false
            }
        });

        $("table td .legend").each(function(){
            var el = $(this);
            var color = el.data("color");
            el.css("background",color);
        });

    });
</script>
</body>

</html>
