var startTime;
var endTime;
var myChart;
var myTable;
var tableApi;
var option;
$(function () {
    date_plugin_init();
    myChartInit();
});

function getPie(){
    if(startTime&&endTime){
        $.ajax({
            type:"POST",
            url:"/swipeRecord/listByTimezoneToChartPie.do",
            data:{startTime:startTime,endTime:endTime},
            dataType:"json",
            async:false,
            success: function(result){
                myChart.setOption({
                    // title : {
                    //     text: '时间:'+startTime+"--"+endTime
                    // },
                    series: [{
                        name: '刷卡',
                        data:result
                    }]
                });
                pieAction();
            },
            error:function(XMLHttpRequest){
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
            }
        });
    }
}

function myChartInit() {
    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('container-chart-pie'));

// 指定图表的配置项和数据
    option = {
        // title : {
        //     text: '时间',
        //     subtext: '...',
        //     x:'center'
        // },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['成功','失败']
        },
        series : [
            {
                name: '刷卡成功率',
                type: 'pie',
                radius : '55%',
                center: ['30%', '60%'],
                data:[
//                            {value:335, name:'成功'},
//                            {value:310, name:'失败'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                color:['#00CC00','#FF0000']
                // label: {
                //     normal: {
                //         formatter: function (obj) {
                //             return obj.name.title
                //         },
                //         show: true,
                //         position: 'inner',//文字显示位置,如上图中的1.0,1.1字样
                //         textStyle: {
                //             fontSize: '14',
                //             fontWeight: 'normal'
                //         }
                //     },
                //     emphasis: {
                //         //show: true,
                //         position: 'inner',
                //         textStyle: {
                //             fontSize: '14',
                //             fontWeight: 'normal'
                //         }
                //     }
                // },

                // markPoint : {
                //     symbol :'circle',
                //     label:{
                //         normal : {
                //             show : true
                //         }
                //     },
                //     itemStyle : {},
                //     data: [{
                //         name: '失败率',
                //         x: 400,
                //         y: 80
                //     }]
                // }
            }
        ]
    };

// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function pieAction() {
    alert('pieAction');
    myChart.on('click', function (param){
        if('失败'==param.name){
            create_table();
        }
    })
}
function date_plugin_init() {
    var start = {
        elem: '#startTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2010-01-01 00:00:01', //设定最小日期
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

function create_table() {
    $('#panel-table').css('display','block');
    if (typeof(myTable) == "undefined") {
        myTable=$('#table-swipeRecord').dataTable({
            "language": {
                "sProcessing":   "处理中...",
                "sLengthMenu":   "每页 _MENU_ 项",
                "sZeroRecords":  "没有匹配结果",
                "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":  "",
                // "sSearch":       "搜索:",
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
            "bFilter" : false, //过滤功能
            "ordering" : false,
            "searching":false,
            //"bInfo" : true,//页脚信息
            //"bAutoWidth" : true,//自动宽度
            "stateSave" : true,
            "processing" : true,
            "serverSide" : true,
            "bProcessing": true,//服务器端进行分页处理的意思
            "bServerSide": true,
            "bSort": false,
            "bDestroy":true,
            "retrieve":true,//一个表格实例
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
                    "sClass" : "center",
                    "orderable":true,
                    "orderSequence":["asc","desc","asc"]
                }],
            "aoColumnDefs" : [{
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
                param.startTime=startTime;
                param.endTime=endTime;
                //console.log(param);
                $.ajax({
                    type: "POST",
                    url: '/swipeRecord/listByTimezoneWhenFail.do',
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        // alert('result:'+result);
                            //封装返回数据
                            var returnData = {};
                            returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                            returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = result.data;//返回的数据列表
                            //关闭遮罩
                            //$wrapper.spinModal(false);
                            callback(returnData);
                            resizeIframe();
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert("查询失败");
                        // $wrapper.spinModal(false);
                    }
                })
            },
            //数据渲染,使数据“失败”高亮!
            "createdRow": function ( row, data, index ) {
                if ( data['result'] =='1' ) {
                    $('td', row).eq(4).css('font-weight',"bold").css("color","red");
                }
            }
        });
    }else {
        // myTable.fnClearTable();
        // myTable.fnDestroy();
        // myTable.fnDraw(false);
        // myTable.fnReloadAjax();
        tableApi=$('#table-swipeRecord').DataTable();
        tableApi.ajax.reload();
    }

    $('#table-swipeRecord tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
}

function resizeIframe(){// iframe和导航高度随table元素高度变化
    var thisheight = $(document).height();
    var myIframe = $(window.parent.document).find("#iframe");
    var west = $(window.parent.document).find("#west");
    myIframe.height(thisheight);
    west.height(thisheight);
}

//当你需要多条件查询，你可以调用此方法，动态修改参数传给服务器
function reloadTable() {
    var name = $("#seName").val();
    var admin = $("#seAdmin").val();
    var param = {
        "obj.name": name,
        "obj.admin": admin
    };
    tableApi.settings()[0].ajax.data = param;
    tableApi.ajax.reload();
}