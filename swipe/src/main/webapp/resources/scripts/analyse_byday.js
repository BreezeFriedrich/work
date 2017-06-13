var selected_time;
var param_interval=1000*30;
var myChart;
$(function () {
    date_plugin_init();
    myChartInit();
});

function getChart(){
    if(selected_time){
        $.ajax({
            type:"POST",
            url:"/swipeRecord/listByDateToChart.do",
            data:{param_date:selected_time,param_interval:param_interval},
            dataType:"json",
            async:false,
            success: function(result){
                myChart.setOption({
                    xAxis: {
                        /*
                         type: 'time',
                         boundaryGap: true,

                         min:result.xAxisTime.min,
                         max:result.xAxisTime.max
                         */
                        interval:0,
                        data :result.category
                    },
                    series: {
                        name: '刷卡成功率',
                        data:result.data
                    }
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
    myChart = echarts.init(document.getElementById('container-chart'));
    var option = {
        title: {
            text: '单日刷卡成功率与刷卡失败记录'
        },
        toolbox: {
            show: true,
            feature: {
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
            // bottom: '3%',
            // containLabel: true
            x: 40,
            x2: 40,
            y2: 100
        },
        xAxis: {
            name:'时间',
            type:'category',
            boundaryGap:true,
            axisLabel: {
                rotate: -60
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
        yAxis: {
            name:'成功率',
            type: 'value',
            min:0,
            interval:0.1,
            boundaryGap:[0.02,0.02]
        },
        legend: {
            data:['刷卡成功率']
        },
        series: [{
            name: '刷卡成功率',
            type: 'bar',
            data: [],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        }]
    };
// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

// 获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
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
    var laydate_opt = {
        elem: '#laydate_day',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2013-01-01 00:00:01',
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