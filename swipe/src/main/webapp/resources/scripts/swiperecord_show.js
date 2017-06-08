var startTime;
var endTime;
var mode;
var myChart;
$(function () {
    date_plugin_init();
    myChartInit();
    refreshData();
});

function refreshData(){

    mode = $('input:radio[name="swipeRecord_options"]:checked').val();
    if(startTime&&endTime){

        date = new Date();
        now = getNowFormatDate(date);

        $.ajax({
            type:"POST",
            url:"/swipeRecord/listByTimezoneToChart.do",
            data:{mode:mode,startTime:startTime,endTime:endTime},
            dataType:"json",
            async:false,
            success: function(result){
                alert("result:"+result);
                myChart.setOption({
                    xAxis: {
                        /*
                        type: 'time',
                        boundaryGap: true,
                        interval:86400000,
                        min:result.xAxisTime.min,
                        max:result.xAxisTime.max
                        */
                        data :result.category
                    },
                    series: {
                        // 根据名字对应到相应的系列
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
                rotate: 60
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