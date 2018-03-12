<%--
  User: admin
  Date: 2018/3/12
  Time: 9:50
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>漫行智能锁管理系统</title>
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,400italic,700,800'/>
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Raleway:300,200,100'/>

    <link rel="stylesheet" href="resources/plugin/bootstrap/dist/css/bootstrap.css"/>
    <link rel="stylesheet" href="resources/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <link rel="stylesheet" href="resources/css/index.css"/>

    <link rel="stylesheet" type="text/css" href="resources/plugin/jquery.niftymodals/css/component.css"/><!-- 弹出框-->
    <link rel="stylesheet" href="resources/css/fixed-table.css"/>
    <%--<link rel="stylesheet" href="resources/plugin/FixedTable/fixed-table.css" />--%>
    <link rel="stylesheet" type="text/css" href="resources/plugin/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <style>
        .fixed-table-box{position:absolute; right: 0px; left: 20px; bottom: 60px; top: 20px;}
        .fixed-table_body-wraper{}
        .fixed-table_fixed{}
        .fixed-table-box>.fixed-table_body-wraper{/*内容了表格主体内容有纵向滚动条*/height: 90%;}
        .fixed-table_fixed>.fixed-table_body-wraper{/*为了让两侧固定列能够同步表格主体内容滚动*/height: 90%;}
    </style>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>

<div id="cl-wrapper" class="fixed-menu">

    <div class="container-fluid" id="pcont">
        <div class="page-head"><img src="resources/images/lb.png" class="inco-lb"><h3>房型管理</h3></div>
        <div class="col-sm-9 table0">
            <div class="block-flat table0-top">
                <button type="button" class="btn btn-success btn-rad md-trigger" data-modal="md-addRoomType"><i class="fa fa-plus"></i>添加房型</button>

                <div class="content">
                    <table>
                        <thead>
                        <tr>
                            <th style="width:30%;">手机号码</th>
                            <th>昵称</th>
                            <th>地址</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbody"></tbody>
                    </table>

                    <ul class="pagination pag-left ">
                        <li class="disabled"><a href="#">&laquo;</a></li>
                        <li class="active"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">&raquo;</a></li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">2015-2016  南京亿数信息科技有限公司 版权所有</div>
    <div class="clearfix"></div>
</div>

<!--添加房型-->
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-addRoomType">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>添加房型</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <form class="form-horizontal" id="form-addSubordinate" method="post" action="roomType/addRoomType.do">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="roomType" placeholder="房型名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary">确认新增</button>
                            <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>

<!-- 删除房型-->
<div class="md-modal2 colored-header custom-width md-effect-9" id="md-deleteRoomType">
    <div class="md-content">
        <div class="block-flat">
            <div class="header">
                <h3>删除房型</h3>
                <button type="button" class="close md-close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="content">
                <form class="form-horizontal" method="post" action="roomType/addRoomType.do">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" name="roomType" placeholder="房型名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-primary">确认删除</button>
                            <button type="button" class="btn btn-default md-close" data-dismiss="modal" aria-hidden="true">取  消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="md-overlay"></div>

<%--<script type="text/javascript" src="resources/plugin/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="resources/js/jquery-3.2.1.min.js"></script>--%>
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

<%--<script type="text/javascript" src="resources/js/fixed-table.js"></script>--%>
<%--<script type="text/javascript" src="resources/js/FixedTable.js"></script>--%>
<script type="text/javascript" src="resources/plugin/FixedTable/fixed-table.js"></script>
<script type="text/javascript" src="resources/plugin/FixedTable/FixedTable.js"></script>

<script type="text/javascript">
    var pathName=window.document.location.pathname;
    var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var juniorPhoneNumber;
    var juniorName;
    var juniorLocation;
    var userHierarchy;

    function completeTable() {
        $.ajax({
            type:"POST",
            url:"user/getUserHierarchy.do",
            async:false,
            data:{},
            dataType:'json',
            success:function(data,status,xhr){
                userHierarchy = data;
                console.log('data : '+data);
                createTbody(userHierarchy);
            },
            error:function(xhr,errorType,error){
                console.log('错误');
            }
        });
    }
    function createTbody(data) {
        var subordinates=data.subordinateList;
        //第一种：动态创建表格的方式，使用拼接html的方式 （推荐）
        //清空所有的子节点
        $("#tbody").empty();
        var html = "";
        for( var i = 0; i < subordinates.length; i++ ) {
            html += '<tr>';
            html +=     '<td style="width:30%;">'+subordinates[i].phoneNumber+'</td>';
            html +=     '<td>'+subordinates[i].name+'</td>';
            html +=     '<td>'+subordinates[i].location+'</td>';
            html +=     '<td class="text-center">';
            html +=         '<a class="label label-danger btn btn-danger btn-xs md-trigger"><i class="fa fa-times"></i></a>';
            html +=     '</td>';
            html += '</tr>';
        }
        $("#tbody").html(html);
    }

    $(document).ready(function(){
        completeTable();

        //添加下级用户的弹出框,js方式实现弹出
        //    $('.md-trigger:first').on('click',function(){
        //        $('#reply-ticket').niftyModal();
        //    });
        $('.md-trigger:gt(0)').on('click',function(){
            var tds=$(this).closest('td').siblings();
            juniorPhoneNumber=tds.eq(0).text();
            juniorName=tds.eq(1).text();
            juniorLocation=tds.eq(2).text();
            $('#reply-ticket2').niftyModal();
        });
        $('#btn-cancleSubordinate').on('click',function(){
            console.log("projectPath : "+projectPath);
            console.log({"juniorPhoneNumber":juniorPhoneNumber,"juniorName":juniorName,"juniorLocation":juniorLocation});
            $.ajax({
                type:"POST",
                url:"user/cancleSubordinate.do",
                async:false,
                data:{"juniorPhoneNumber":juniorPhoneNumber,"juniorName":juniorName,"juniorLocation":juniorLocation},
                dataType:'json',
                success:function(data,status,xhr){
                    ajaxResult = data;
                },
                error:function(xhr,errorType,error){
                    console.log('错误');
                }
            });
        });

        $("table td .legend").each(function(){
            var el = $(this);
            var color = el.data("color");
            el.css("background",color);
        });

        App.init();
    });
</script>
</body>
</html>