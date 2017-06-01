var table_moduleStatus;
var table_api;
// var table_swipeRecord;

$(function(){
    moduleStatus();
    // swipeRecord();
    edit();
});

function moduleStatus() {
    table_api =$('#table-moduleStatus').dataTable($.extend(true,{},{
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
        "bLengthChange" : true, //改变每页显示数据数量
        "lengthMenu" : [10,20],
        //"bFilter" : true, //过滤功能
        "ordering" : false,
        "bSort" : false, //排序功能
        //"bInfo" : true,//页脚信息
        //"bAutoWidth" : true,//自动宽度
        "stateSave" : true,
        "retrieve" : true,
        "processing" : true,
        "serverSide" : true,

        "bProcessing": true,//服务器端进行分页处理的意思
        "bServerSide": true,
        "aoColumns": [{
            "mDataProp":"deviceid",
            "sTitle" : "模块编号",
            "sDefaultContent" : "",
            "sClass" : "center"
            },
            {"mDataProp":"deviceip",
                "sTitle" : "模块IP",
                "sDefaultContent" : "",
                "sClass" : "left"
            },
            {"mDataProp":"status",
                "sTitle" : "模块状态",
                "sDefaultContent" : "",
                "sClass" : "center"
            },
            {"mDataProp":"timestamp",
                "sTitle" : "刷卡时间",
                "sDefaultContent" : "",
                "sClass" : "center"
            },
            {"mDataProp":"info",
                "sTitle" : "刷卡信息",
                "sDefaultContent" : "",
                "sClass" : "left"
            },
            {"mDataProp":"reserved",
                "sTitle" : "备注",
                "sDefaultContent" : "",
                "sClass" : "left"
            }],
        "aoColumnDefs" : [{
            "targets" : 2,//操作按钮目标列
            "render" : function(data,type,row) {
                if (0 === data ) {
                    return "正常";
                } else {
                    return "异常";
                }
            }
        }],
        "columnDefs":[{
            targets: [ 1 ],
            orderData: [ 1, 0 ]  //如果第2列进行排序，有相同数据则按照第1列顺序排列
        }, {
            targets: [ 4 ],
            orderData: [ 4, 5 ]  //如果第5列进行排序，有相同数据则按照第6列顺序排列
        }],

        "ajax": function (data, callback,settings) {
            alert('data:'+data);
            //封装请求参数
            var param = {};
            if(typeof(data)==undefined ){
                data=table_api.data();
            }
            alert('data:'+data);
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            $.ajax({
                type: "POST",
                url: '/moduleStatus/listAll.do',
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    setTimeout(function () {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                        returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;//返回的数据列表
                        callback(returnData);
                        // iframe和导航高度随table元素高度变化
                        var thisheight = $(document).height();
                        var myIframe = $(window.parent.document).find("#iframe");
                        var west = $(window.parent.document).find("#west");
                        myIframe.height(thisheight);
                        west.height(thisheight);
                    },1);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            })
        },
        //数据渲染,使数据“失败”高亮!
        "createdRow": function ( row, data, index ) {
            if ( data['status'] ===0 ) {
                $('td', row).eq(2).css('font-weight',"bold").css("color","green");
            }
            if ( data['status'] ===1 ){
                $('td', row).eq(2).css('font-weight',"bold").css("color","red");
            }
        }
    })).api();
    // $([name='table-moduleStatus_length select']).on('change', function () {
    //     page_length=$([name='table-moduleStatus_length select']).val();
    // } );
    $('#table-moduleStatus tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    });
}
function edit() {

    // $('#table-moduleStatus tbody').on( 'click', 'tr', function () {
    //     if ( $(this).hasClass('selected') ) {
    //         $(this).removeClass('selected');
    //     }
    //     else {
    //         table_moduleStatus.$('tr.selected').removeClass('selected');
    //         $(this).addClass('selected');
    //     }
    // } );

    $('#delbutton-moduleStatus').click( function () {
        // table_api=$('#table-moduleStatus').DataTable();
        var selectRows=table_api.rows( {selected:true} ).data();
        alert('data0: '+selectRows[0]);
        alert('data0: '+selectRows[0].deviceid);
        $.ajax({
            type: "POST",
            url: 'database/clearSwipeRecord',
            dataType: 'json',
            data: {rows:selectRows},
            success: function (getData) {
                alert('edit-success');
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("查询失败");
            }
        });
        alert('tag');
    });
}
function swipeRecord() {
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
        "bLengthChange" : true, //改变每页显示数据数量
        "lengthMenu" : [10,20],
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
            "sClass" : "center"
        },
            {"mDataProp":"deviceip",
                "sTitle" : "模块IP",
                "sDefaultContent" : "",
                "sClass" : "left",
            },
            {"mDataProp":"clientid",
                "sTitle" : "设备编号",
                "sDefaultContent" : "",
                "sClass" : "center"
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
        "columnDefs":[{
            targets: [ 1 ],
            orderData: [ 1, 0 ]  //如果第2列进行排序，有相同数据则按照第1列顺序排列
        }, {
            targets: [ 4 ],
            orderData: [ 4, 5 ]  //如果第5列进行排序，有相同数据则按照第6列顺序排列
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
                    // alert('result:'+result);
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
                        // iframe和导航高度随table元素高度变化
                        var thisheight = $(document).height();
                        var myIframe = $(window.parent.document).find("#iframe");
                        var west = $(window.parent.document).find("#west");
                        myIframe.height(thisheight);
                        west.height(thisheight);
                    },1);
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert("查询失败");
                    $wrapper.spinModal(false);
                }
            })
        },
        //数据渲染,使数据“失败”高亮!
        "createdRow": function ( row, data, index ) {
            if ( data['result'] =='0' ) {
                $('td', row).eq(4).css('font-weight',"bold").css("color","green");
            }
            if ( data['result'] =='1' ) {
                $('td', row).eq(4).css('font-weight',"bold").css("color","red");
            }
        },
    })

    $('#table-swipeRecord tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
}