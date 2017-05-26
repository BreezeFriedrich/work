/**
 * Created by admin on 2017/5/26.
 */
$(function () {
    myChart();
})

function myChart(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('container-chart'));

// 指定图表的配置项和数据
    var option = {
        title: {
            text: '刷卡记录追踪'
        },
        legend: {
            data:['刷卡成功率']
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
        yAxis: {},
        series: [{
            name: '刷卡成功率',
            type: 'bar',
            data: []
        }]
    };
// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

// (new Date).toLocaleTimeString().replace(/^\D*/,'');
    date = new Date();
    now = getNowFormatDate(date);
    nowYearAgo =  getNowYearAgo(date);

    $.ajax({
        type:"POST",
        url:"/swipeRecord/listByTimezone.do",
        data:{startTime:nowYearAgo,endTime:now},
        dataType:"json",
        async:false,
        success: function(jsonArr){
            var arr=new Array();
            for(x in jsonArr){
                alert(jsonArr[x].result);
                arr.push(jsonArr[x].result)
            };
            myChart.setOption({
                xAxis: {
                    data: arr.categories
                },
                series: [{
                    // 根据名字对应到相应的系列
                    name: '刷卡成功率',
                    data: arr.data
                }]
            });
        },
        error:function(XMLHttpRequest){
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
        }
    });
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
