/**
 * Created by admin on 2017/6/14.
 */
var param;
var startTime;
var endTime;
$(function () {
    date_plugin_init();
    var $table = $("#table");
    var _table = $table.dataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
        ajax: function (data, callback, settings) {
            //封装请求参数
            param = userManage.getQueryCondition(data);
            $.ajax({
                type: "POST",
                url: '/swipeRecord/listAllWithStrategy.do',
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    // //异常判断与处理
                    // if (result.errorCode) {
                    //     alert("查询失败");
                    //     return;
                    // }
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = result.draw;//后台返回draw计数器转int,防止跨站脚本(XSS)攻击
                    returnData.recordsTotal = result.total;//总记录数
                    returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.pageData;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    if(null===returnData.data){
                        returnData.data={};
                    }
                    callback(returnData);
                    resizeIframe();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("查询失败");
                }
            });
        },
        //绑定数据
        columns: [
            {
                data: "deviceid"
            },
            {
                data: "deviceip"
            },
            {
                data: "clientid",
                defaultContent: "",//无默认值
                orderable: false
            },
            {
                data: "clientip"
            },
            {
                data: "result",
                render: function (data, type, row, meta) {
                    return (data == 0 ? "刷卡成功" : data == 1 ? "刷卡失败" : "刷卡结果奇异");
                }
            },
            {
                data: "timestamp",
                render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS//alt效果
            },{
                data: "edit",
                orderable: false
            }
        ],
        "columnDefs": [
            {
                "defaultContent": "",
                "targets": "_all"
            }
        ],
        "createdRow": function (row, data, index) {
            //添加编辑行的2种方法： 1.render方式 "columnDefs":[{"render": function(data, type, row) {} }] 2.用jquery DOM操作呈现单元格createdRow.
            // var $btnEdit = $('<button type="button" class="btn-edit">修改</button>');
            // var $btnDel = $('<button type="button" class="btn-del">删除</button>');
            var $btnEdit = $('<a type="button" class="btn-edit">修改</a>');
            var $btnDel = $('<a class="delete btn btn-default btn-sm">删除</a>');
            $('td', row).eq(6).append($btnEdit).append($btnDel);
        },
        "drawCallback": function (settings) {
            //渲染完毕后的回调
            //默认选中第一行
            //$("tbody tr",$table).eq(0).click();
        }
    })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
    //查询
    $("#btn_search").click(function () {
        _table.draw();
    });
    //行点击事件
    $("tbody", $table).on("click", "tr", function (event) {
        $(this).addClass("active").siblings().removeClass("active");
        //获取该行对应的数据
        var item = _table.row($(this).closest('tr')).data();
        userManage.showItemDetail(item);
    });
    //按钮点击事件
    $table.on("click", ".btn-edit", function () {
        //点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
        userManage.editItemInit(item);
    }).on("click", ".btn-del", function () {
        //点击删除按钮
        var row = _table.row($(this).closest('tr'));
        var item = row.data();
        userManage.deleteItem(item);
        row.remove().draw(false);
    });
    //隐藏列
    $('a').on('click', function (e) {
        var cut = $(this).text();
        if (cut.indexOf("显示") > -1) {
            $(this).text("隐藏" + cut.split("示")[1])
        } else {
            $(this).text("显示" + cut.split("藏")[1])
        }
        e.preventDefault();
        var column = _table.column($(this).attr('data-column'));
        column.visible(!column.visible());
    });

});

function resizeIframe(){// iframe和导航高度随table元素高度变化
    var thisheight = $(document).height();
    var myIframe = $(window.parent.document).find("#iframe");
    var west = $(window.parent.document).find("#west");
    myIframe.height(thisheight);
    west.height(thisheight);
}
var CONSTANT = {
    DATA_TABLES : {
        DEFAULT_OPTION : { //DataTables初始化选项
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
            searching: false,    //禁用原生搜索
            // bProcessing: true,
            // bServerSide: true,
            iDisplayLength : 10,//默认每页数量
            bLengthChange : true, //改变每页显示数据数量
            lengthMenu : [10,20,30],
            ordering : true,
            stateSave : true,
            retrieve : true
            //bPaginate: true, //翻页功能
            //bFilter : true, //过滤功能
            // bSort : false, //排序功能
            //bInfo : true,//页脚信息
            //bAutoWidth : true,//自动宽度
            //bPaginate : true,
            //bProcessing: true//服务器端进行分页处理的意思
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
var userManage = {
    getQueryCondition: function (data) {
        var param = {};
        //组装排序参数
        if (data.order && data.order.length && data.order[0]) {
            switch (data.order[0].column) {
                case 0:
                    param.orderColumn = "deviceid";
                    break;
                case 1:
                    param.orderColumn = "deviceip";
                    break;
                case 2:
                    param.orderColumn = "clientid";
                    break;
                case 3:
                    param.orderColumn = "clientip";
                    break;
                case 4:
                    param.orderColumn = "result";
                    break;
                case 5:
                    param.orderColumn = "timestamp";
                    break;
                default:
                    param.orderColumn = "timestamp";
                    break;
            }
            //排序方式asc或者desc
            param.orderDir = data.order[0].dir;
        }
        param.deviceid = $("#deviceid-search").val();
        param.deviceip = $("#deviceip-search").val();
        // param.endTime = $("#endTime-search").val();
        param.startTime=startTime;
        param.endTime=endTime;
        param.result = $("#result-search").val();
        // param.result = $("#result-search").val()===""?-1:$("#result-search").val();
        //组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;
        return param;
    },
    editItemInit: function (item) {
        console.log(item);
        //编辑方法
        alert("编辑" + item.deviceid + "  " + item.timestamp);
    },
    deleteItem: function (item) {
        console.log(item);
        //删除
        alert("删除" + item.deviceid + "  " + item.timestamp);
    },
    showItemDetail: function (item) {
        //点击行
        alert("点击" + item.deviceid + "  " + item.timestamp);
    }
};

function date_plugin_init() {
    var start = {
        elem: '#startTime-search',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2010-01-01 00:00:01', //设定最小日期为当前日期
        max: laydate.now(-1), //最大日期
        istime: true,
        istoday: false,
        festival:true,
        choose: function(time){
            end.min = time; //开始日选好后，重置结束日的最小日期
            end.start = time;//将结束日的初始值设定为开始日
            startTime=time;
        }
    };
    var end = {
        elem: '#endTime-search',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2014-01-01 00:00:01',
        max: laydate.now(),
        istime: true,
        istoday: false,
        festival:true,
        choose: function(time){
            start.max = time; //结束日选好后，重置开始日的最大日期
            endTime=time;
        },
        clear: function (time) {
            alert('laydate-clear');
            start.max = time;
            endTime=time;
        }
    };
    laydate.skin('molv');
    laydate(start);
    laydate(end);
}