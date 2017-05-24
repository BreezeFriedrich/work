$(function(){
    // testA();
    // testB();
    ajaxData();
    // testD();
});
function testC() {
    $.ajax({
        type:"POST",
        url:"/swipeRecord/listAll.do",
        data:{},
        dataType:"json",
        async:false,
        success: function(data){
            alert("table-ajax-data:"+data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
        }
    })
};
function testA() {
    $.ajax({
        type:"POST",
        url:"/swipeRecord/listAll.do",
        data:{},
        dataType:"json",
        async:false,
        success: function(data){
            create_tbody($("#tbody"),data.data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
        }
    })
};
function testB() {
    beginTime="2014-01-01 00:00:01";
    endTime="2015-12-10 13:35:34";
    $.ajax({
        type:"POST",
        url:"/swipeRecord/listByTime.do",
        data:{"beginTime":beginTime,"endTime":endTime},
        dataType:"json",
        async:false,
        success: function(data){
            create_tbody($("#tbody2"),data.data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
        }
    })
};

function ajaxData() {
    $('#table-swipeRecord').dataTable({
        "language": {
            "sProcessing":   "处理中...",
            "sLengthMenu":   "每页 _MENU_ 项",
            "sZeroRecords":  "没有匹配结果",
            "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix":  "",
            "sSearch":       "搜索:",
            "sUrl":          "",
            "sEmptyTable":     "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands":  ",",
            "oPaginate": {
                "sFirst":    "首页",
                "sPrevious": "上页",
                "sNext":     "下页",
                "sLast":     "末页",
                "sJump":     "跳转"
            },
            "oAria": {
                "sSortAscending":  ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        "iDisplayLength" : 10,//默认每页数量
        //"bPaginate": true, //翻页功能
        "bLengthChange" : false, //改变每页显示数据数量
        //"bFilter" : true, //过滤功能
        "ordering" : false,
        "bSort" : false, //排序功能
        //"bInfo" : true,//页脚信息
        //"bAutoWidth" : true,//自动宽度
        "stateSave" : true,
        "retrieve" : true,
        "processing" : true,
        "serverSide" : true,
        //"bPaginate" : true,
        //"bProcessing": true//服务器端进行分页处理的意思

        "bProcessing": true,
        "bServerSide": true,
        "bSort": false,
        "aoColumns": [{
            // {"sName": "deviceid", "sClass": "center"},
            // {"sName": "deviceip", "sClass": "center"},
            // {"sName": "clientid", "sClass": "center"},
            // {"sName": "clientip", "sClass": "center"},
            // {"sName": "result", "sClass": "center"},
            // {"sName": "timestamp", "sClass": "center"},
            "mDataProp":"deviceid",
            // "bVisible" : false,//此列不显示
            "sTitle" : "模块编号",
            "sDefaultContent" : "",
            "sClass" : "left"
        },
            {"mDataProp":"deviceip",
                "sTitle" : "模块IP",
                "sDefaultContent" : "",
                "sClass" : "left"
            },
            {"mDataProp":"clientid",
                "sTitle" : "设备编号",
                "sDefaultContent" : "",
                "sClass" : "left"
            },
            {"mDataProp":"clientip",
                "sTitle" : "设备IP",
                "sDefaultContent" : "",
                "sClass" : "left"
            },
            {"mDataProp":"result",
                "sTitle" : "刷卡结果",
                "sDefaultContent" : "",
                "sClass" : "center"
            },
            {"mDataProp":"timestamp",
                "sTitle" : "刷卡时间",
                "sDefaultContent" : "",
                "sClass" : "center"
            }],
        // "columns" : [
        //     {
        //         data : "deviceid"
        //     },
        //     {
        //         data : "deviceip"
        //     },
        //     {
        //         data : "clientid"
        //     },
        //     {
        //         data : "clientip"
        //     },
        //     {
        //         data : "result",
        //         render : function(data,type,row) {
        //             if (data == "0") {
        //                 return "成功";
        //             } else {
        //                 return "失败";
        //             }
        //         }
        //     },
        //     {
        //         data : "timestamp"
        //     }],
        "aoColumnDefs" : [{
            // 定义操作列,######以下是重点########
            "targets" : 4,//操作按钮目标列
            "render" : function(data,type,row) {
                if (data == "0") {
                return "成功";
                } else {
                    return "失败";
                }
            }
        }],
        "ajax": function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            //console.log(param);
            //ajax请求数据
            $.ajax({
                type: "POST",
                url: '/swipeRecord/listAll.do',
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //console.log(result);
                    //setTimeout仅为测试延迟效果
                    alert('result:'+result);
                    setTimeout(function () {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                        returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;//返回的数据列表
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        //关闭遮罩
                        //$wrapper.spinModal(false);
                        callback(returnData);
                    },1);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            })
        }
    })
}

function testD() {
    var _table = $('#table-swipeRecord').dataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        // "aoColumns": [{
        //     // {"sName": "deviceid", "sClass": "center"},
        //     // {"sName": "deviceip", "sClass": "center"},
        //     // {"sName": "clientid", "sClass": "center"},
        //     // {"sName": "clientip", "sClass": "center"},
        //     // {"sName": "result", "sClass": "center"},
        //     // {"sName": "timestamp", "sClass": "center"},
        //         "mDataProp":"deviceid",
        //         // "bVisible" : false,//此列不显示
        //         "sTitle" : "模块编号",
        //         "sDefaultContent" : "",
        //         "sClass" : "left"
        //     },
        //     {"mDataProp":"deviceip",
        //         "sTitle" : "模块IP",
        //         "sDefaultContent" : "",
        //         "sClass" : "left"
        //     },
        //     {"mDataProp":"clientid",
        //         "sTitle" : "设备编号",
        //         "sDefaultContent" : "",
        //         "sClass" : "left"
        //     },
        //     {"mDataProp":"clientip",
        //         "sTitle" : "设备IP",
        //         "sDefaultContent" : "",
        //         "sClass" : "left"
        //     },
        //     {"mDataProp":"result",
        //         "sTitle" : "刷卡结果",
        //         "sDefaultContent" : "",
        //         "sClass" : "center"
        //     },
        //     {"mDataProp":"timestamp",
        //         "sTitle" : "刷卡时间",
        //         "sDefaultContent" : "",
        //         "sClass" : "center"
        //     }],
        // "aoColumnDefs": [
        //     {
        //         "aTargets": [4],
        //         "sName": "操作",
        //         "mRender": function (data, type, full) {
        //             return '<a href="javascript:void(0);" class="delete" tag=' + data + '>删除</a>';
        //         }
        //     }
        // ],
        columns:[CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
                className : "ellipsis", //文字过长时用省略号显示，CSS实现
                data: "deviceid",
                width : "180px",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
            },
            {
                className : "ellipsis",
                data: "deviceip",
                width : "180px",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                //固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
                //切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。
            },
            {
                data : "clientid",
                width : "100px",
                render : function(data,type, row, meta) {
                    return '<i class="fa fa-male"></i> '+(data?"在线":"离线");
                }
            },
            {
                className : "ellipsis",
                data: "clientip",
                width : "100px",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                //固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
                //切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。
            },
            {
                className : "ellipsis",
                data: "result",
                width : "80px",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
            },
            {
                className : "ellipsis",
                data: "timestamp",
                width : "80px",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
            },
            {
                className : "td-operation",
                data: null,
                defaultContent:"",
                orderable : false,
                width : "120px"
            }
        ],
        ajax: function (data, callback, settings) {
            //手动控制遮罩
            $wrapper.spinModal();
            //封装请求参数
            var param = {};
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            //console.log(param);
            //ajax请求数据
            $.ajax({
                type: "POST",
                url: '/swipeRecord/listAll.do',
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //console.log(result);
                    //setTimeout仅为测试延迟效果
                    alert('result:'+result);
                    setTimeout(function () {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.total;//返回数据全部记录
                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;//返回的数据列表
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                    }, 200);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            })
        }
    }))
}
function testK() {
        var table=$('#table-swipeRecord').DataTable({
            "pagingType": "full_numbers",
            "info":false,
            "lengthChange":false,
            "searching":false,
            "processing": true,
            "serverSide": true,
            ordering:false,
            "ajax":{
                url:"/swipeRecord/listAll.do",
                type:"POST",
                data:function(d){
                    d.beginTime="2014-01-01 00:00:01";
                    d.endTime="2015-12-10 13:35:34";
                }
            },
            "columns": [
            ]
        });
}

var CONSTANT = {
    DATA_TABLES : {
        DEFAULT_OPTION : { //DataTables初始化选项
            //国际化
            language: {
                "sProcessing":   "处理中...",
                "sLengthMenu":   "每页 _MENU_ 项",
                "sZeroRecords":  "没有匹配结果",
                "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":  "",
                "sSearch":       "搜索:",
                "sUrl":          "",
                "sEmptyTable":     "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands":  ",",
                "oPaginate": {
                    "sFirst":    "首页",
                    "sPrevious": "上页",
                    "sNext":     "下页",
                    "sLast":     "末页",
                    "sJump":     "跳转"
                },
                "oAria": {
                    "sSortAscending":  ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            autoWidth: false,   //禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,  //隐藏加载提示,自行处理
            serverSide: true,   //启用服务器端分页
            searching: false    //禁用原生搜索
        },
        COLUMN: {
            CHECKBOX: { //复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {   //常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data||"";
                return '<span title="' + data + '">' + data + '</span>';
            }
        }
    }
};