var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var param;
var startTime;
var endTime;
var tableElement;
var tableInstance;
var gatewaysAndLocks;
var gateways;
var locks;
var isCodeNotName_gateway;
var isCodeNotName_lock;

$(function () {
    $(".navbar-collapse ul:first li:eq(2)").addClass("active");

    // $.fn.bootstrapSwitch.defaults.size = 'small';
    // $.fn.bootstrapSwitch.defaults.onColor = 'success';
    // $.fn.bootstrapSwitch.defaults.offColor = 'warning';
    $("[name='switchbox']").bootstrapSwitch('state', false);
    $('input[name="switchbox"]').eq(0).on('init.bootstrapSwitch', function(event, state) {
        console.log('event:'+event+',state:'+state);
        isCodeNotName_gateway=state;
        console.log('isCodeNotName_gateway:'+isCodeNotName_gateway);
    });
    $('input[name="switchbox"]').eq(1).on('init.bootstrapSwitch', function(event, state) {
        console.log('event:'+event+',state:'+state);
        isCodeNotName_lock=state;
        console.log('isCodeNotName_lock:'+isCodeNotName_lock);
    });
    $('input[name="switchbox"]').eq(0).on('switchChange.bootstrapSwitch', function(event, state) {
        console.log('event:'+event+',state:'+state);
        isCodeNotName_gateway=state;
        console.log('isCodeNotName_gateway:'+isCodeNotName_gateway);
    });
    $('input[name="switchbox"]').eq(1).on('switchChange.bootstrapSwitch', function(event, state) {
        console.log('event:'+event+',state:'+state);
        isCodeNotName_lock=state;
        console.log('isCodeNotName_lock:'+isCodeNotName_lock);
    });
    // $("[name='switchbox']").eq(0).bootstrapSwitch('setState', false);
    // $("[name='switchbox']").on('switch-change', function (e, data) {
    //     var $el = $(data.el)
    //         , value = data.value;
    //     console.log(e, $el, value);
    // });
    // $("[name='switchbox']")[0].on('switch-change', function (e, data) {
    //     isCodeNotName_gateway=data.value;
    //     alert("isCodeNotName_gateway:"+isCodeNotName_gateway);
    // });

    var options_daterange={
        "format": 'YYYY-MM-DD HH:mm',// format: 'MM/DD/YYYY', // format: 'MM/DD/YYYY h:mm A',
        "separator":'  ~  ',
        "timePicker": true,
        "timePicker12Hour": false,//24小时制
        "timePickerIncrement": 30,//min
        "dateLimit": {"days": 366*4},
        "showCustomRangeLabel": true,
        "alwaysShowCalendars": true,
        "autoUpdateInput": true,//默认为true
        "startDate": moment().add(-1,'month'),
        "endDate": moment(),
        "minDate": moment().add(-3,'years'),
        "maxDate": moment().add(1,'years'),
        "opens": "right",
        "drops": "down",
        "showDropdowns": true,
        "showWeekNumbers": true,
        "buttonClasses": ['btn'],
        "applyClass": 'btn-small btn-primary',
        "cancelClass": 'btn-small'
    };
    options_daterange.ranges={
        '今天': [moment().set('hour',0).set('minute',0).set('second',0), moment()],
        '昨天': [moment().subtract('days', 1).set('hour',0).set('minute',0).set('second',0), moment().subtract('days', 1).set('hour',23).set('minute',59).set('second',59)],
        '过去7天': [moment().subtract('days', 6).set('hour',0).set('minute',0).set('second',0), moment()],
        '本月': [moment().startOf('month'), moment()],
        '上个月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
    };
    options_daterange.locale= {
        // direction: 'ltr',
        // format: 'YYYY-MM-DD HH:mm',//无效// format: 'MM/DD/YYYY', // format: 'MM/DD/YYYY h:mm A', // format: 'MM/DD/YYYY HH:mm',
        // separator: ' - ',//无效
        applyLabel: '确定',
        cancelLabel: '取消',
        fromLabel: '起始',
        toLabel: '结束',
        customRangeLabel: '选取',
        daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
        monthNames: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        firstDay: 1
    };
    $('#timerange').daterangepicker(options_daterange,function (start, end, label) {
        startTime=start.format('YYYY-MM-DD HH:mm');
        endTime=end.format('YYYY-MM-DD HH:mm');
        console.log("startTime:"+startTime+";endTime:"+endTime+"predefined range:"+label);
    });

    getGatewaysAndLocks();
    var datatableSet = {
        OPTIONS : {
            DEFAULT_OPTION : { //DataTables初始化选项
                language: {
                    "sProcessing":   "处理中...",
                    "sLengthMenu":   "显示 _MENU_ 项结果",
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
                bLengthChange : true, //改变每页显示数据数量,bLengthChange==false会隐藏lengthMenu
                lengthMenu : [10,20,30,50],
                ordering : true,
                stateSave : true,
                retrieve : true,
                // paginationType:"scrolling",
                // pagingType:"scrolling",
                // orderMulti: true,//默认可以多列排序
                orderClasses: true,//高亮排序列
                orderFixed: {
                    // "pre": [ 2, 'asc' ],
                    // "post": [ 3, 'desc' ]
                    // "post": [[ 0, 'asc' ], [ 1, 'asc' ]]
                }
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
        },
        function : {
            getQueryParams: function (data) {
                var param = {};
                //组装排序参数
                if (data.order && data.order.length && data.order[0]) {
                    console.log('data.order.length:'+data.order.length);
                    /*
                    switch (data.order[0].column) {
                        case 0:
                            param.orderColumn = "openMode";
                            break;
                        case 1:
                            param.orderColumn = "timestamp";
                            break;
                        case 2:
                            param.orderColumn = "credential";
                            break;
                        case 3:
                            param.orderColumn = "name";
                            break;
                        default:
                            param.orderColumn = "timestamp";
                            break;
                    }
                    //排序方式asc或者desc
                    param.orderDir = data.order[0].dir;
                    */
                    var order=new Array();
                    // for(var index in data.order){
                    //     param.order[index]={};
                    //     param.order[index].orderDir = data.order[index].dir;
                    //     switch (data.order[index].column) {
                    //         case 0:
                    //             param.order[index].orderColumn = "openMode";
                    //             break;
                    //         case 1:
                    //             param.order[index].orderColumn = "timestamp";
                    //             break;
                    //         case 2:
                    //             param.order[index].orderColumn = "credential";
                    //             break;
                    //         case 3:
                    //             param.order[index].orderColumn = "name";
                    //             break;
                    //         default:
                    //             param.order[index].orderColumn = "timestamp";
                    //             break;
                    //     }
                    // }
                    for(var index in data.order){
                        order[index]={};
                        order[index].orderDir = data.order[index].dir;
                        switch (data.order[index].column) {
                            case 0:
                                order[index].orderColumn = "gatewayCode";
                                break;
                            case 1:
                                order[index].orderColumn = "lockCode";
                                break;
                            case 2:
                                order[index].orderColumn = "openMode";
                                break;
                            case 3:
                                order[index].orderColumn = "timestamp";
                                break;
                            case 4:
                                order[index].orderColumn = "credential";
                                break;
                            case 5:
                                order[index].orderColumn = "name";
                                break;
                            default:
                                order[index].orderColumn = "timestamp";
                                break;
                        }
                    }
                    param.order=JSON.stringify(order);
                }
                param.startTime=startTime;
                param.endTime=endTime;
                // param.gatewayCode=$("#form-gatewayCode").val();
                // param.lockCode=$("#form-lockCode").val();
                if(isCodeNotName_gateway){
                    param.gatewayCode=$("#form-gatewayCode").val();
                }else{
                    param.gatewayName=$("#form-gatewayCode").val();
                }
                if(isCodeNotName_lock){
                    param.lockCode=$("#form-lockCode").val();
                }else{
                    param.lockName=$("#form-lockCode").val();
                }
                param.openMode=$("#form-openMode").val();
                //组装分页参数
                param.startIndex = data.start;
                param.pageSize = data.length;
                param.draw = data.draw;
                return param;
            },
            drawtable: function (element) {
                //element代表table元素. element===$("#table");
                // tableWrapper= element.parent('div .col-lg-12').eq(0);
                tableWrapper=element.parent('div .table-container').eq(0);
                $.fn.dataTable.tables({api: true}).destroy();
                tableInstance = element.dataTable($.extend(true, {}, datatableSet.OPTIONS.DEFAULT_OPTION, {
                    ajax: function (data, callback, settings) {
                        param = datatableSet.function.getQueryParams(data);
                        tableWrapper.spinModal();
                        $.ajax({
                            type: "POST",
                            url: projectPath+'/record/unlockRecordFilterOrderPage.do',
                            cache: false,
                            data: param,
                            dataType: "json",
                            success: function (data) {
                                var returnData = {};
                                if(data.success){
                                    if(data.biz.code===0){
                                        var result=data.biz.data;
                                        returnData.draw = result.draw;
                                        returnData.recordsTotal = result.total;
                                        returnData.recordsFiltered = result.total;
                                        returnData.data = result.pageData;
                                    }else{
                                        console.log("biz.code:"+data.biz.code+",biz.msg:"+data.biz.msg);
                                    }
                                }else{
                                    console.log("errmsg:"+data.errmsg);
                                }
                                if(undefined==returnData.data || null==returnData.data){
                                    returnData.recordsTotal=0;
                                    returnData.recordsFiltered=0;
                                    returnData.data=[];
                                }
                                tableWrapper.spinModal(false);
                                callback(returnData);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("查询失败");
                                tableWrapper.spinModal(false);
                            }
                        });
                    },
                    //绑定数据
                    columns: [
                        {
                            data: "gatewayCode",
                            orderable: true,
                            render: function (data) {
                                if(gateways[data]){
                                    return gateways[data].gatewayName;
                                }
                                return data;
                            }
                        },{
                            data: "lockCode",
                            render: function (data) {
                                if(locks[data]){
                                    return locks[data].lockName;
                                }
                                return data;
                            }
                        },{
                            data: "openMode",
                            orderable: true,
                            render: function (data, type, row, meta) {
                                if(1==data){
                                    return "身份证开锁"
                                }else if(2==data){
                                    return "密码开锁"
                                }else if(3==data){
                                    return "门卡开锁"
                                }else{
                                    return data;
                                }
                            }
                        },{
                            data: "timestamp",
                            // render: datatableSet.options.RENDER.ELLIPSIS//alt效果
                        },{
                            data: "credential"
                        },{
                            data: "name"
                        }
                    ],
                    "columnDefs": [
                        {
                            "defaultContent": "",
                            "targets": "_all"
                        }
                    ],
                    "drawCallback": function (settings) {}
                })).api();
            }
        }
    };

    tableElement=$("#table");
    datatableSet.function.drawtable(tableElement);
    //查询
    $("#btn_search").click(function () {
        tableInstance.draw();
    });

    /*
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
    */

    App.init({nanoScroller:false,dateTime:false});
});

function getGatewaysAndLocks() {
    $.ajax({
        type:"POST",
        url:"device/getGatewaysAndLocks.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',
        success:function(data,status,xhr){
            console.log("GatewaysAndLocks:"+data);
            gatewaysAndLocks=data;
            gateways=data.gateways;
            locks=data.locks;
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}