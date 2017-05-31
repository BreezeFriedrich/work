/**
 * Created by admin on 2017/5/26.
 */
var startTime;
var endTime;
var myChart;
$(function () {
    date_plugin_init();
    myChartInit();

    setTimeout(function(){
        if(startTime&&endTime){

            // (new Date).toLocaleTimeString().replace(/^\D*/,'');
            date = new Date();
            now = getNowFormatDate(date);
            nowYearAgo =  getNowYearAgo(date);

            $.ajax({
                type:"POST",
                url:"/swipeRecord/listByTimezoneToChart.do",
                data:{startTime:startTime,endTime:endTime},
                dataType:"json",
                async:false,
                success: function(result){
                    myChart.setOption({
                        xAxis: {
                            // data:result.xAxisArr
                            type : 'time',
                            interval:86400000,
                            min:result.xAxisTime.min,
                            max:result.xAxisTime.max
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '刷卡成功率',
                            data:result.data
                        },{
                            name: '折线',
                            data:result.data
                        }]
                    });
                },
                error:function(XMLHttpRequest){
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                }
            });

            // alert('startTime:'+startTime);
            // alert('endTime:'+endTime);
            // var time = Date.parse(endTime)-Date.parse(startTime);
            // var days = parseInt(time / (1000 * 60 * 60 * 24));
            // var timeArr=new Array();
            // var indexArr=new Array();
            // var countArr=new Array();
            // for(i=Date.parse(startTime);i<=Date.parse(endTime);i+86400000){
            //     timeArr.push(parseInt(i));
            // }
            // for(x in jsonArr){
            //     dot=jsonArr[x];
            //     // arr.push(swiperesult);
            //     timer=(Date.parse(dot.timeStamp)-Date.parse(startTime))
            //     parseInt(timer/86400000);
            //     if(0==dot.result){
            //         coutArr[timer-1].isSuccessful++;
            //     }else{
            //         coutArr[timer-1].isFailed++;
            //     }
            // };
            // for(y in countArr){
            //     indexArr.push(countArr[y].isSuccessful/(countArr[y].isSuccessful+countArr[y].isFailed));
            // };

        }
    },10000);

});

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
                dataView: {readOnly: false},
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
        xAxis: {
            data: []
        },
        yAxis: {
            type: 'value',
            scale:true,
            name:'成功率',
            max:1.5,
            min:0,
            boundaryGap:[0.02,0.02]
        },
        legend: {
            data:['刷卡成功率','折线']
        },
        series: [{
            name: '刷卡成功率',
            type: 'bar',
            // dimensions: ['xValue', 'yValue'],
            // encode: {
            //     x: 'xValue',
            //     y: 'yValue'
            // },
            data: []
        },{
            name:'折线',
            type:'line',
            yAxisIndex: 0,
            data:[]
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
};
function getNowYearAgo(date) {
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
    var currentdate = date.getFullYear()-1 + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
};
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
