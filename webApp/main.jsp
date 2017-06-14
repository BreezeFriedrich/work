<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <meta charset="utf-8">
        <title>亿数漫行智能锁</title>
		<link rel="stylesheet" type="text/css" href="../../resources/styles/main.css"/>
		<script type="text/javascript" src="../../resources/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="../../resources/scripts/main.js"></script>
		<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
		<script type="text/javascript" charset="utf8" src="js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" charset="utf8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript">
    		var rootPath = '${pageContext.request.contextPath}';
		</script>
    </head>
	<body>  
    <form>  
        <span>编号:</span> <input type="text" placeholder="编号" id="id-search">  
        <span>名称:</span> <input type="text" placeholder="名称" id="name-search">  
        <span>状态:</span> <select id="status-search">  
            <option value="">全部</option>
            <option value="1">可以查发</option>  
            <option value="2">可以链接</option>  
            <option value="3">仅供查询</option>  
            <option value="4">不可用</option>  
        </select>  
        <button type="button" id="btn_search">查询</button>  
          
        <a href="#" data-column="0">影藏编号</a>  
        <a href="#" data-column="1">影藏名称</a>  
        <a href="#" data-column="2">影藏状态</a>  
        <a href="#" data-column="3">影藏电话</a>  
        <a href="#" data-column="4">影藏网址</a>  
        <a href="#" data-column="5">影藏操作</a>  
    </form>  
    <table id="table" class="display">  
        <thead>  
            <tr>  
                <th>编号</th>  
                <th>名称</th>  
                <th>状态</th>  
                <th>电话</th>  
                <th>网址</th>  
                <th>操作</th>  
            </tr>  
        </thead>  
        <tbody></tbody>  
    </table>  
    <script type="text/javascript" src="js/constant.js"></script>  
    <script type="text/javascript">  
    $(function(){
        var $table = $("#table");
        var _table = $table.dataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
            ajax : function(data, callback, settings) {
                //封装请求参数  
                var param = userManage.getQueryCondition(data);
                $.ajax({  
                        type: "GET",  
                        url: rootPath+"/carrier/getCarrierByPage.action",
                        cache : false,  //禁用缓存  
                        data: param,    //传入已封装的参数  
                        dataType: "json",  
                        success: function(result) {
                                //异常判断与处理  
                                if (result.errorCode) {
                                    alert("查询失败");
                                    return;
                                }
                                //封装返回数据
                                var returnData = {};
                                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                                returnData.recordsTotal = result.total;//总记录数
                                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                                returnData.data = result.pageData;  
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(returnData);
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert("查询失败");  
                        }  
                    });  
            },  
            //绑定数据  
            columns: [  
                {  
                    data: "carrierId",//字段名  
                },  
                {  
                    data: "carrierName",//字段名  
                },  
                {  
                    data : "carrierStatus",//字段名  
                    render : function(data,type, row, meta) {  
                        return (data == 1? "可以查发":data == 2?"可以链接":data == 3?"仅供查询":"不可用");  
                    }  
                },  
                {  
                    data : "carrierPhone",//字段名  
                },  
                {  
                    data : "carrierLink",//字段名  
                    orderable : false,//禁用排序  
                    render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS//alt效果  
                },  
                 {  
                    data: null,//字段名  
                    defaultContent:"",//无默认值  
                    orderable : false//禁用排序  
                }  
            ],  
            "createdRow": function ( row, data, index ) {  
                //不使用render，改用jquery文档操作呈现单元格  
                var $btnEdit = $('<button type="button" class="btn-edit">修改</button>');  
                var $btnDel = $('<button type="button" class="btn-del">删除</button>');  
                $('td', row).eq(5).append($btnEdit).append($btnDel);  
            },  
            "drawCallback": function( settings ) {  
                //渲染完毕后的回调  
                //默认选中第一行  
                //$("tbody tr",$table).eq(0).click();  
            }  
        })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
        //查询  
        $("#btn_search").click(function(){  
            _table.draw();  
        });  
        //行点击事件  
        $("tbody",$table).on("click","tr",function(event) {  
            $(this).addClass("active").siblings().removeClass("active");
            //获取该行对应的数据  
            var item = _table.row($(this).closest('tr')).data();  
            userManage.showItemDetail(item);  
        });  
        //按钮点击事件  
        $table.on("click",".btn-edit",function() {  
            //点击编辑按钮  
            var item = _table.row($(this).closest('tr')).data();  
            userManage.editItemInit(item);  
        }).on("click",".btn-del",function() {  
            //点击删除按钮  
            var item = _table.row($(this).closest('tr')).data();  
            userManage.deleteItem(item);  
        });  
        //影藏列  
        $('a').on( 'click', function (e) {  
            var cut = $(this).text()  
            if(cut.indexOf("显示")>-1){  
                $(this).text("影藏"+cut.split("示")[1])  
            }else{  
                $(this).text("显示"+cut.split("藏")[1])  
            }  
            e.preventDefault();  
            var column = _table.column( $(this).attr('data-column') );  
            column.visible( ! column.visible() );  
        } );  
          
    });  
    var userManage = {  
            getQueryCondition : function(data) {  
                var param = {};  
                //组装排序参数  
                if (data.order&&data.order.length&&data.order[0]) {  
                    switch (data.order[0].column) {  
                    case 0:  
                        param.orderColumn = "carrier_id";//数据库列名称  
                        break;  
                    case 1:  
                        param.orderColumn = "carrier_name";//数据库列名称  
                        break;  
                    case 2:  
                        param.orderColumn = "carrier_status";//数据库列名称  
                        break;  
                    case 3:  
                        param.orderColumn = "carrier_phone";//数据库列名称  
                        break;  
                    default:  
                        param.orderColumn = "carrier_id";//数据库列名称  
                        break;  
                    }  
                    //排序方式asc或者desc  
                    param.orderDir = data.order[0].dir;  
                }  
                param.id = $("#id-search").val();//查询条件  
                param.name = $("#name-search").val();//查询条件  
                param.status = $("#status-search").val();//查询条件  
                //组装分页参数  
                param.startIndex = data.start;  
                param.pageSize = data.length;  
                param.draw = data.draw;  
                return param;  
            },  
            editItemInit : function(item) {  
                //编辑方法  
                alert("编辑"+item.carrierId+"  "+item.carrierName);  
            },  
            deleteItem : function(item) {  
                //删除  
                alert("删除"+item.carrierId+"  "+item.carrierName);     
            },  
            showItemDetail: function(item){  
                //点击行  
                alert("点击"+item.carrierId+"  "+item.carrierName);  
            }  
        };  
   </script>  
</body>
</html>
