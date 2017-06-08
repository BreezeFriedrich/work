var startTime;
var endTime;
var table_moduleStatus;
var table_moduleStatus_new;
var param;
$(function(){
    // date_plugin_init();
    initData();
});

function refreshData() {
    if(startTime&&endTime){
        alert('tag1');
        table_moduleStatus_new=$('#table-moduleStatus').DataTable();
        table_moduleStatus_new.clear().draw();
        data=table_moduleStatus_new.data();

        // param.startTime=startTime;
        // param.endTime=endTime;
        // table_moduleStatus_new.fnSettings().ajax.data=param;
        table_moduleStatus_new.ajax.url("/moduleStatus/listByTimezone.do").load();
        // table_moduleStatus_new.ajax().load();

        // table_moduleStatus_new.ajax=function (data, callback, settings) {
        //         //封装请求参数
        //         var param = {};
        //         param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
        //         param.start = data.start;//开始的记录序号
        //         param.page = (data.start / data.length) + 1;//当前页码
        //         //ajax请求数据
        //         $.ajax({
        //             type: "POST",
        //             url: "/moduleStatus/listByTimezone.do",
        //             data: {startTime: startTime, endTime: endTime},
        //             dataType: "json",
        //             async: false,
        //             success: function (result) {
        //                 var returnData = {};
        //                 returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
        //                 returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
        //                 returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
        //                 returnData.data = result.data;
        //                 callback(returnData);
        //                 // iframe和导航高度随table元素高度变化
        //                 var thisheight = $(document).height();
        //                 var myIframe = $(window.parent.document).find("#iframe");
        //                 var west = $(window.parent.document).find("#west");
        //                 myIframe.height(thisheight);
        //                 west.height(thisheight);
        //             },
        //             error: function (XMLHttpRequest) {
        //                 alert(XMLHttpRequest.status);
        //                 alert(XMLHttpRequest.readyState);
        //             }
        //         });
        //     };

        // table_moduleStatus_new.draw();
    }else{
        alert('请输入时间')
    }
}

function initData() {
    table_moduleStatus=$('#table-moduleStatus').dataTable({
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
        "lengthMenu" : [10,20,30],
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
            "mDataProp":"deviceid",
            // "bVisible" : false,//此列不显示
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
                "sTitle" : "设备状态",
                "sDefaultContent" : "",
                "sClass" : "center"
            },
            {"mDataProp":"timestamp",
                "sTitle" : "时间",
                "sDefaultContent" : "",
                "sClass" : "left"
            },
            {"mDataProp":"info",
                "sTitle" : "其他信息",
                "sDefaultContent" : "",
                "sClass" : "center"
            },
            {"mDataProp":"reserved",
                "sTitle" : "留白",
                "sDefaultContent" : "",
                "sClass" : "center"
            }],
        "aoColumnDefs" : [{
            "targets" : 2,//操作按钮目标列
            "render" : function(data,type,row) {
                if (data == "0") {
                    return "正常";
                } else {
                    return "异常";
                }
            }
        }],
        "columnDefs":[{
            targets: [2],
            orderData: [2,3]  //如果第3列进行排序，有相同数据则按照第4列顺序排列
        }, {
            targets: [0],
            orderData: [0,3]  //如果第1列进行排序，有相同数据则按照第4列顺序排列
        }],
        "ajax": function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            if(startTime!=undefined&&startTime!=null){
                param.startTime=startTime;
                param.endTime=endTime;
            }
            //ajax请求数据
            $.ajax({
                type: "POST",
                url: '/moduleStatus/listAll.do',
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    // alert('result:'+result);
                    setTimeout(function () {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                        returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;//返回的数据列表
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
        //数据渲染,使数据状态“异常”高亮!
        "createdRow": function ( row, data, index ) {
            if ( data['status'] =='1' ) {
                $('td', row).eq(2).css('font-weight',"bold").css("color","red");
            }
        }
    });

    $('#table-moduleStatus tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
}

//供主页面调用
//设置iframe高度
function setActiveIframeHeight(){
    //计算iframe内容的高度
    function getBodyHeight(){
        var height = 0;
        if (document) {
            height = $(document.body).height();//Math.max(document.body.clientHeight,document.body.offsetHeight);
            //获取iframe中显示的脱离文档流的元素
            var panels = $('.page-shadow.active'),
                pHeight = 0;
            //计算其中最大的值
            for(var i = 0; i < panels.length; i++){
                //计算撑开iframe的高度
                var panelContent = $(panels[i]),
                    panelContentHeight = panelContent.height() + panelContent.offset().top + 50;
                pHeight = (panelContentHeight > pHeight)?panelContentHeight:pHeight;
            }
            height = (pHeight > height)?pHeight:height;
        }
        return height;
    }
    var curHeight = getBodyHeight(),
        //这里使用#right-content-test自适应来探测中部内容显示区域的最小高度
        minHeight = top.$('#right-content-test').height(),
        //获取iframe元素
        htmlDom = top.$('.tab-content>.active').find('iframe')[0];

    curHeight = (minHeight >= curHeight) ? minHeight : curHeight;

    //top.activeIframeHeight记录了当前的iframe的的高度
    if(htmlDom && htmlDom.height != top.activeIframeHeight){
        htmlDom.height = top.activeIframeHeight;
    }

    //防止临界值导致滚动条时有时无使用Math.abs处理
    if(setActiveIframeHeight.isFirst || (Math.abs(top.activeIframeHeight - curHeight) > 2)){
        top.activeIframeHeight = curHeight;
        htmlDom && (htmlDom.height = top.activeIframeHeight);
    }
    setActiveIframeHeight.isFirst = 0;
}
setActiveIframeHeight.isFirst  = 1;

function date_plugin_init() {
    var start = {
        elem: '#startTime',
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
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2014-01-01 00:00:01',
        max: laydate.now(),
        istime: true,
        istoday: false,
        festival:true,
        choose: function(time){
            start.max = time; //结束日选好后，重置开始日的最大日期
            endTime=time;
        }
    };
    laydate.skin('molv');
    laydate(start);
    laydate(end);
}