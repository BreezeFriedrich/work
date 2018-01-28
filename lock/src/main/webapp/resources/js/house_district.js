var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var userHierarchy;
var subordinates;

/*
//初始化FixedTable
$(".fixed-table-box").fixedTable();

//清空表格
$("#empty_data").on("click", function (){
    $(".fixed-table-box").emptyTable();
});
//添加数据
$("#add_data").on("click", function (){
    $(".fixed-table-box").addRow(function (){
        var html = '';
        for(var i = 0; i < 5; i ++){
            html += '<tr>';
            html += '    <td class="w-150"><div class="table-cell"> 2016-05-03</div></td>';
            html += '    <td class="w-120"><div class="table-cell">王小虎</div></td>';
            html += '    <td class="w-120"><div class="table-cell">上海</div></td>';
            html += '    <td class="w-120"><div class="table-cell">普陀区</div></td>';
            html += '    <td class="w-300"><div class="table-cell">上海市普陀区金沙江路 1518 路</div></td>';
            html += '    <td class="w-120"><div class="table-cell">200333</div></td>';
            html += '    <td class="w-100">';
            html += '        <div class="table-cell">';
            html += '            <a href="###">查看</a>';
            html += '            <a href="###">编辑</a>';
            html += '        </div>';
            html += '    </td>';
            html += '</tr>';
        }
        return html;
    });
});
//删除指定行
$("#del_row").on("click", function (){
    $(".fixed-table-box").deleteRow($(".fixed-table-box").children('.fixed-table_fixed-left').children('.fixed-table_body-wraper').find('tr').eq(0));
});
*/
function setLeftColumn() {
    var aimDom=$('.fixed-table_body tbody');
    aimDom.empty();
    var html = '';
    subordinates=userHierarchy.subordinateList;
    var subordinatesLength=subordinates.length;
    var item;
    html += '<tr>';
    html += '    <td rowspan="'+subordinates.length+'"><div class="table-hight3 table-width105 table-butstyle">userHierarchy.name</div></td>';
    for(var i in subordinates){
        item=subordinates[i];
        html += '    <td><div class="table-hight1 table-width105 table-butstyle">'+item.name+'</div></td>';
    }
    html += '</tr>';
    html.appendTo(aimDom);
}
/*
<table class="fixed-table_body" cellspacing="0" cellpadding="0" border="0">
                    <tbody>
                    <tr>
                        <td rowspan="3"><div class="table-hight3 table-width105 table-butstyle">业主1 NY875</div></td>
                        <td ><div class="table-hight1 table-width105 table-butstyle">NY875-01</div></td>
                    </tr>
 */
function completeTable() {
    $.ajax({
        type:"POST",
        url:"user/getUserHierarchy.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            userHierarchy=data;
            subordinates=data.subordinateList;
            /*
            $(".fixed-table-box").addRow(function (){
                var html = '';
                for(var i = 0; i < 5; i ++){
                    html += '<tr>';
                    html += '    <td class="w-150"><div class="table-cell"> 2016-05-03</div></td>';
                    html += '    <td class="w-120"><div class="table-cell">王小虎</div></td>';
                    html += '    <td class="w-120"><div class="table-cell">上海</div></td>';
                    html += '    <td class="w-120"><div class="table-cell">普陀区</div></td>';
                    html += '    <td class="w-300"><div class="table-cell">上海市普陀区金沙江路 1518 路</div></td>';
                    html += '    <td class="w-120"><div class="table-cell">200333</div></td>';
                    html += '    <td class="w-100">';
                    html += '        <div class="table-cell">';
                    html += '            <a href="###">查看</a>';
                    html += '            <a href="###">编辑</a>';
                    html += '        </div>';
                    html += '    </td>';
                    html += '</tr>';
                }
                return html;
            });
            */
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

$(document).ready(function(){
    console.log('haha');

    //initialize the javascript
    App.init();

    $(".navbar-collapse ul:first li:eq(3)").addClass("active");

    completeTable();
    setLeftColumn();

    //App.dashBoard();
    /*Sparklines*/
    $(".spk1").sparkline([2,4,3,6,7,5,8,9,4,2,6,8,8,9,10], { type: 'bar', width: '80px', barColor: '#4A8CF7'});
    $(".spk2").sparkline([4,6,7,7,4,3,2,1,4,4 ,5,6,5], { type: 'discrete', width: '80', lineColor: '#4A8CF7',thresholdValue: 4,thresholdColor: '#ff0000'});
    $(".spk4").sparkline([2,4,3,6,7,5,8,9,4,2,10], { type: 'bar', width: '80px', height: '30px',barColor: '#EA6153'});
    $(".spk5").sparkline([5,3,5,6,5,7,4,8,6,9,8], { type: 'bar', width: '80px', height: '30px',barColor: '#4AA3DF'});

    $(".spk3").sparkline([5,6,7,9,9,5,3,2,2,4,6,7], {
        type: 'line',
        lineColor: '#258FEC',
        fillColor: '#4A8CF7',
        spotColor: false,
        width: '80px',
        minSpotColor: false,
        maxSpotColor: false,
        highlightSpotColor: '#1e7ac6',
        highlightLineColor: '#1e7ac6'});

    //Maps
    $('#world-map').vectorMap({
        map: 'world_mill_en',
        backgroundColor: 'transparent',
        regionStyle: {
            initial: {
                fill: '#38c3c1',
            },
            hover: {
                "fill-opacity": 0.8
            }
        },
        markerStyle:{
            initial:{
                r: 10
            },
            hover: {
                r: 12,
                stroke: 'rgba(255,255,255,0.8)',
                "stroke-width": 4
            }
        },
        markers: [
            {latLng: [41.90, 12.45], name: '1.512 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
            {latLng: [1.3, 103.8], name: '940 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
            {latLng: [51.511214, -0.119824], name: '530 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
            {latLng: [40.714353, -74.005973], name: '340 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}},
            {latLng: [-22.913395, -43.200710], name: '1.800 Visits', style: {fill: '#E44C34',stroke:'rgba(255,255,255,0.7)',"stroke-width": 3}}
        ]
    });

    /*Pie Chart*/
    var data = [
        { label: "Google", data: 50},
        { label: "Dribbble", data: 15},
        { label: "Twitter", data: 12},
        { label: "Youtube", data: 14},
        { label: "Microsoft", data: 14}
    ];

    $.plot('#ticket-chart', data, {
        series: {
            pie: {
                show: true,
                innerRadius: 0.5,
                shadow:{
                    top: 5,
                    left: 15,
                    alpha:0.3
                },
                stroke:{
                    width:0
                },
                label: {
                    show: false
                },
                highlight:{
                    opacity: 0.08
                }
            }
        },
        grid: {
            hoverable: true,
            clickable: true
        },
        colors: ["#5793f3", "#19B698","#dd4444","#fd9c35","#fec42c","#d4df5a","#5578c2"],
        legend: {
            show: false
        }
    });

    $("table td .legend").each(function(){
        var el = $(this);
        var color = el.data("color");
        el.css("background",color);
    });

});