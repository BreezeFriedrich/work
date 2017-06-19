var startTime;
var endTime;
var mode;
var myChart;
$(function () {
    date_plugin_init();
    myChartInit();
});

function refreshData(){

    mode = $('input:radio[name="swipeRecord_options"]:checked').val();
    if(startTime&&endTime){

        date = new Date();
        now = getNowFormatDate(date);

        $.ajax({
            type:"POST",
            url:"/swipeRecord/listByTimezoneToMainChart1.do",
            data:{mode:mode,startTime:startTime,endTime:endTime},
            dataType:"json",
            async:false,
            success: function(result){
                alert("result:"+result);
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
            },
            error:function(XMLHttpRequest){
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
            }
        });

    }
}

function myChartInit(){

    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('container-chart'));
// 指定图表的配置项和数据
    var option = {
        title: {
            text: '刷卡记录追踪'
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
                // type: 'value',
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
    myChart.setOption(option);
}

// 获取当前时间 格式“yyyy-MM-dd HH:MM:SS”
function getNowFormatDate(date) {
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
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

function date_plugin_init() {
    var start = {
        elem: '#swipeRecord_startTime',
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
        elem: '#swipeRecord_endTime',
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