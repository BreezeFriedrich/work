var startTime;
var endTime;
var myChart;
$(function () {
    date_plugin_init();
    myChartInit();
    setTimeout(function(){
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
                            // 根据名字对应到相应的系列
                            name: '刷卡成功率',
                            data:result
                        }]
                    });
                },
                error:function(XMLHttpRequest){
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                }
            });

        }
    },10000);
});

function myChartInit() {
    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('container-chart-pie'));

// 指定图表的配置项和数据
    var option = {
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
                center: ['50%', '60%'],
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
                }
            }
        ]
    };

// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
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