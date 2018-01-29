var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var userHierarchy;
var subordinates;
var lock;
var dateArr=new Array;
var dateStrArr=new Array;
var curDate,newDate;
var year,week,month,day,hours,minutes,seconds;
var authinfo;
var startTime;
var endTime;
var specificGatewayCode="GWH0081702000003";
var specificLockCode="LCN0011721000001";

var initializationTime = (new Date()).getTime();
function showLeftTime() {
    var now = new Date();
    var year = now.getFullYear();
    var week = now.getDay();
    var month = now.getMonth()+1;
    var day = now.getDate();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();
    // document.all.show3.innerHTML = year + "-" + month + "-" + day;
    //一秒刷新一次显示时间
    // var timeID = setTimeout(showLeftTime, 1000);
}

function getDateArr() {
    curDate=new Date();
    newDate;
    dateArr.length=0;
    dateStrArr.length=0;
    for (var i=-14;i<15;i++){
        newDate=new Date(curDate.getTime() + i*24*60*60*1000);
        dateArr.push(newDate);
        // dateStrArr.push(newDate.getFullYear()+ "-" + (newDate.getMonth()+1) + "-" + newDate.getDay());
        var year = newDate.getFullYear();
        var month = newDate.getMonth()+1;
        var day = newDate.getDate();
        dateStrArr.push(year + "-" + month + "-" + day);
    }
    console.log("dateArr:"+dateArr);
    console.log("dateStrArr:"+dateStrArr);
}

function getLocks() {
    $.ajax({
        type:"POST",
        url:"user/getSubordinateHierarchyTillLock.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',
        success:function(data,status,xhr){
            userHierarchy=data;
            subordinates=data.subordinateList;

            if (subordinates.length===0){
                userHierarchy={
                    "phoneNumber": "17705155208",
                    "grade": 10,
                    "name": null,
                    "location": null,
                    "subordinateList": [
                        {
                            "lockName": "room1",
                            "lockCode": "LCN0011702000019",
                            "lockLocation": "bigdata",
                            "lockComment": "bigdata",
                            "lockStatus": "1",
                            "lockPower": "4"
                        }
                    ]
                };
                subordinates=[
                    {
                        "lockName": "房间1",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间2",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间3",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间4",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间5",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间6",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间7",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间8",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间9",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间10",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间11",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间12",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间13",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "lockName": "房间14",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    }
                ]
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

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

function getAuthinfo(specificGatewayCode,specificLockCode,startTime,endTime) {
    $.ajax({
        type:"POST",
        url:projectPath+"/unlock/getUnlockAuthorizationDailyArr.do",
        async:false,
        data:{
            // "ownerPhoneNumber":ownerPhoneNumber,
            "gatewayCode":specificGatewayCode,
            "lockCode":specificLockCode,
            "startTime":startTime,
            "endTime":endTime
        },
        dataType:'json',
        success:function(data,status,xhr){
            authinfo=data;
            console.log("authinfo:"+authinfo);
            var authinfodailyArr=authinfo.authinfoDaily;
            for(var i=0;i<authinfodailyArr.length;i++){
                console.log("index:"+i+",date:"+authinfodailyArr[i].date+",ids:"+authinfodailyArr[i].idIndexes+",pwds:"+authinfodailyArr[i].pwdIndexes);
            }
            var authinfodaily;
            for(var i=0;i<authinfodailyArr.length;i++){
                authinfodaily=authinfodailyArr[i];
                if(authinfodaily.idIndexes.length+authinfodaily.pwdIndexes.length>0){
                    fixedTable.fixedTableBody.find("tbody tr").eq(3)[0]
                }
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    })
}

function getDailyRecords(lockCode) {
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getLockUnlockRecord.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"lockCode":lockCode},
        dataType:'json',
        success:function(data,status,xhr){
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

$(document).ready(function () {
    $(".navbar-collapse ul:first li:eq(3)").addClass("active");
    console.log("projectPath:"+projectPath);
    getDateArr();
    getLocks();

    var fixedTable = new FixedTable({
        wrap: document.getElementById("test_fixedTable"),//生成的表格需要放到哪里
        type: "row-col-fixed",//表格类型，有：head-fixed、col-fixed、row-col-fixed
        extraClass: "",//需要添加到表格中的额外class
        maxHeight: true,
        fields: [//表格的列
            {
                width: "206px",
                field: '<th class="table-width1 "><div class="table-time table-header-hight58 table-cell table-width1 table-butstyle">当前日期：'+dateStrArr[14]+'</div></th>',
                htmlDom: true,
                fixed: true
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[0]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[1]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[2]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[3]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[4]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[5]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[6]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[7]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[8]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[9]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[10]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[11]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[12]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[13]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[14]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[15]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[16]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[17]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[18]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[19]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[20]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[21]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[22]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[23]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[24]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[25]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[26]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[27]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[28]+'</div></th>',
                htmlDom: true,
                fixed: false
            }
        ],
        tableDefaultContent: "<div>我是一个默认的div</div>"
    });

    //添加数据
    fixedTable.addRow(function (){
        var html = '';
        (function () {
            for(var i in subordinates){
                lock=subordinates[i];
                html += '<tr>';
                html += '<td class="table-width1"><div class="table-time table-cell table-hight1 table-width1 table-butstyle">'+lock.lockName+'</div></td>';
                for (var i=0; i<dateArr.length; i++){
                    html += '<td class="table-width140"><div class="cd table-hight1 table-width140">'+dateStrArr[i]+'</div></td>';
                }
                html += '</tr>';
            }
        })();
        return html;
    });

    curDate=new Date();
    startTime=curDate.getTime() -14*24*60*60*1000;
    endTime=curDate.getTime() + 15*24*60*60*1000;
    getAuthinfo(specificGatewayCode,specificLockCode,startTime,endTime);


    $.contextMenu({
        // define which elements trigger this menu
        selector: ".rightclick",
        // define the elements of the menu
        items: {
            identity: {name: "身份证授权", callback: function(key, opt){
                $('#reply-identity').niftyModal();
            }},
            password: {name: "密码授权", callback: function(key, opt){
                $('#reply-password').niftyModal();
            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){
                $('#reply-unlocking').niftyModal();
            }}
        }
        // there's more, have a look at the demos and docs...
    });
    //initialize the javascript
    App.init();

    $('#reservation').daterangepicker();
    $('#reservationtime').daterangepicker({
        timePicker: true,
        timePickerIncrement: 30,
        format: 'MM/DD/YYYY h:mm A'
    });
    $('#reservationtime2').daterangepicker({
        timePicker: true,
        timePickerIncrement: 30,
        format: 'MM/DD/YYYY h:mm A'
    });

    var cb = function (start, end) {
        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        alert("Callback has fired: [" + start.format('MMMM D, YYYY') + " to " + end.format('MMMM D, YYYY') + "]");
    };

    var optionSet1 = {
        startDate: moment().subtract('days', 29),
        endDate: moment(),
        minDate: '01/01/2012',
        maxDate: '12/31/2014',
        dateLimit: {days: 60},
        showDropdowns: true,
        showWeekNumbers: true,
        timePicker: false,
        timePickerIncrement: 1,
        timePicker12Hour: true,
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
            'Last 7 Days': [moment().subtract('days', 6), moment()],
            'Last 30 Days': [moment().subtract('days', 29), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
        },
        opens: 'left',
        buttonClasses: ['btn'],
        applyClass: 'btn-small btn-primary',
        cancelClass: 'btn-small',
        format: 'MM/DD/YYYY',
        separator: ' to ',
        locale: {
            applyLabel: 'Submit',
            cancelLabel: 'Clear',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
        }
    };

    var optionSet2 = {
        startDate: moment().subtract('days', 7),
        endDate: moment(),
        opens: 'left',
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
            'Last 7 Days': [moment().subtract('days', 6), moment()],
            'Last 30 Days': [moment().subtract('days', 29), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
        }
    };

    $('#reportrange span').html(moment().subtract('days', 29).format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));

    $('#reportrange').daterangepicker(optionSet1, cb);

});