var selected_time;
var endTime;
var param_interval=1000*10;
var myChart;
$(function () {
    date_plugin_init();
    myChartInit();
});

function refreshData(){
    if(selected_time){
        $("#summary").css('display','block');

        $.ajax({
            type:"POST",
            url:"/swipeRecord/listByDateToChart1.do",
            data:{param_date:selected_time,param_interval:param_interval},
            dataType:"json",
            async:false,
            success: function(result){
                myChart.setOption({
                    xAxis: {
                        data :result.category
                    },
                    series: [{
                        name: '刷卡失败率',
                        data:result.data
                    },{
                        name:'刷卡频度',
                        data:result.series_frequency
                    },{
                        name:'SAM模块并发量',
                        data:result.series_samConcurrency
                    }]
                });
                $("#summary dl dt").eq(0).text('总失败率      :'+result.sumFailRatio);
                $("#summary dl dt").eq(1).text('刷卡总次数    :'+result.sumSwipeFrequency);
                $("#summary dl dt").eq(2).text('所用SAM数量   :'+result.sumSAM);
                $("#summary dl dt").eq(3).text('刷卡设备的数量 :'+result.sumDevices);
            },
            error:function(XMLHttpRequest){
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
            }
        });

        endTimeMilis=new Date(Date.parse(selected_time)).getTime()+86400000;//整形，毫秒数
        endTime=getFormatTime(new Date(endTimeMilis));//Date型
        $.ajax({
            type:"POST",
            url:"/swipeRecord/listByTimezoneToMainChart2.do",
            data:{startTime:selected_time,endTime:endTime},
            dataType:"json",
            async:false,
            success: function(result){
                //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
                container_myChart1.style.height = 50*result.category.length+'px';
                myChart1.resize();
                resizeIframe();
                myChart1.setOption({
                    yAxis: {
                        data :result.category
                    },
                    series: [{
                        name:'刷卡次数',
                        data:result.series_frequency
                    },{
                        name: '失败率',
                        data:result.series_failRatio
                    },{
                        name:'成功率',
                        data:result.series_successRatio
                    }]
                });
                // //用于使chart自适应高度和宽度
                // window.onresize = function () {
                //     //重置容器高宽
                //     resizeChart1Container();
                //     myChart1.resize();
                // };
            },
            error:function(XMLHttpRequest){
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
            }
        });

    }
    // if(selected_time){
    //     $.ajax({
    //         type:"POST",
    //         url:"/swipeRecord/listByDateToChart.do",
    //         data:{param_date:selected_time,param_interval:param_interval},
    //         dataType:"json",
    //         async:false,
    //         success: function(result){
    //             myChart.setOption({
    //                 xAxis: {
    //                     /*
    //                      type: 'time',
    //                      boundaryGap: true,
    //
    //                      min:result.xAxisTime.min,
    //                      max:result.xAxisTime.max
    //                      */
    //                     interval:0,
    //                     data :result.category
    //                 },
    //                 series: {
    //                     name: '刷卡成功率',
    //                     data:result.data
    //                 }
    //             });
    //         },
    //         error:function(XMLHttpRequest){
    //             alert(XMLHttpRequest.status);
    //             alert(XMLHttpRequest.readyState);
    //         }
    //     });
    // }
}

function myChartInit(){
    //chart0
    myChart = echarts.init(document.getElementById('container-chart'));
// 指定图表的配置项和数据
    var option0 = {
        title: {
            text: '单日刷卡记录'
        },
        toolbox: {
            show: true,
            feature: {
                // dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        dataZoom: {
            show: true,
            start: 0,
            end: 100
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#000000'
                }
            }
        },
        grid: {
            // left: '3%',
            // right: '4%',
            // bottom: '12%',
            // containLabel: true
            x: 40,
            x2: 80,
            y2: 100
        },
        xAxis: {
            name:'时间',
            type:'category',
            boundaryGap:true,
            axisLabel: {
                rotate: 60
            },
            axisPointer: {
                type: 'shadow'
            },
            data:[]
            // data : function(){
            //     var list = [];
            //     for (var x in data) {
            //         if (x != '')
            //             list.push(App.formatDate(x));
            //     }
            //     return list;
            // }()
        },
        yAxis: [{
            name:'比率',
            type: 'value',
            min:0,
            interval:0.1,
            boundaryGap:[0,0.02],
            // name:'体积 m',
            // boundaryGap: [0, '50%'],
            position:'left',
            axisLabel: {
                formatter: '{value}',
                textStyle:{
                    color:'#0B438B'
                }
            },
            axisLine: {
                lineStyle: {
                    color:'#0B438B'
                }
            },
            splitLine:{
                show:true
            }
        },
            {
                name:'频度',
                type: 'value',
                position:'right',
                boundaryGap: [0, '50%'],
                axisLine: {
                    lineStyle: {
                        color:'#0B438B'
                    }
                },
                axisTick:{
                    inside:'false',
                    length:10
                },
                splitLine:{
                    show:true
                }
            },
            {
                name:'并发量',
                type: 'value',
                min:0,
                // name:'温度 C',
                // type: 'value',
                position:'right',
                offset:50,
                boundaryGap: [0, '50%'],
                axisLabel: {
                    formatter: '{value} '
                },
                axisLine: {
                    lineStyle: {
                        color:'#0B438B'
                    }
                },
                splitLine:{
                    show:true
                }
            }],
        legend: {
            data:['刷卡失败率','刷卡频度','SAM模块并发量']
        },
        series: [{
            name: '刷卡失败率',
            type: 'bar',
            data: []
        },{
            name: '刷卡频度',
            type: 'line',
            yAxisIndex:1,
            data: []
        },{
            name: 'SAM模块并发量',
            type: 'line',
            yAxisIndex:2,
            data: []
        }]
    };
// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option0);

    //chart1
    container_myChart1=document.getElementById('container-chart1');
    myChart1 = echarts.init(container_myChart1);
    var option1 = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['刷卡次数', '成功率', '失败率']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'value'
            },
            {
                type : 'value'
            }
        ],
        yAxis : [
            {
                type : 'category',
                axisLabel:{
                    interval:0
                },
                axisTick : {show: false},
                data : []
            }
        ],
        series : [
            {
                name:'刷卡次数',
                type:'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    }
                },
                xAxisIndex:1,
                data:[]
            },
            {
                name:'失败率',
                type:'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true
                    }
                },
                data:[]
            },
            {
                name:'成功率',
                type:'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'left'
                    }
                },
                data:[]
            }
        ]
    };
    myChart1.setOption(option1);

    resizeIframe();
}

function create_table() {
    if($("#swipeFailRecord").css("display")==='none'){
        $('#swipeFailRecord').css('display','block');
        $('#collapseOne').collapse('show');
    }
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
                param.startTime=selected_time;
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
        tableApi=$('#table-swipeRecord').DataTable();
        tableApi.ajax.reload();
    }

    $('#table-swipeRecord tbody').on( 'click', 'tr', function () {
        $(this).toggleClass('selected');
    } );
}

function date_plugin_init() {
    var laydate_opt = {
        elem: '#laydate_day',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2014-01-01 00:00:01',
        max: laydate.now(),
        istime: true,
        istoday: false,
        festival:true,
        choose: function(time){
            selected_time=time;
        }
    };
    laydate.skin('molv');
    laydate(laydate_opt);
}

function resizeIframe() {
    // iframe和导航高度随table元素高度变化
    var thisheight = $(document).height();
    var myIframe = $(window.parent.document).find("#iframe");
    var west = $(window.parent.document).find("#west");
    myIframe.height(thisheight);
    west.height(thisheight);
}

// 获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
function getFormatTime(date) {
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if(2==month&&29==strDate){
        strDate=28;
    }
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}