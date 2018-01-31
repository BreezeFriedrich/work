var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var userHierarchy;
var subordinates;
var lock;
var dateArr=new Array;
var dateStrArr=new Array;
var today,theDate,newDate;
var year,week,month,day,hours,minutes,seconds;
var authinfo;
var recordinfo;
var startTime;
var endTime;
var specificGatewayCode="GWH0081702000003";
var specificLockCode="LCN0011721000001";
var fixedTable;

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

function getZeroOfDate(date) {
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date;
}
function getDateStr(date) {
    return date.getFullYear()+ "-" + (date.getMonth()+1) + "-" + date.getDate();
}
function getDateArr(date) {
    dateArr.length=0;
    dateStrArr.length=0;
    (function () {
        var newDate;
        for (var i=-15;i<16;i++){
            newDate=new Date(date.getTime() + i*24*60*60*1000);
            dateArr.push(newDate);
            dateStrArr.push(getDateStr(newDate));
        }
    })()
    // console.log("dateArr:"+dateArr);
    // console.log("dateStrArr:"+dateStrArr);
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

            // if (subordinates.length===0){
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
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间1",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间2",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间3",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间4",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间5",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间6",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间7",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间8",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间9",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间10",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间11",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间12",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间13",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    },{
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间14",
                        "lockCode": "LCN0011702000019",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    }
                ]
            // }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

/*
 //删除指定行
 $("#del_row").on("click", function (){
 $(".fixed-table-box").deleteRow($(".fixed-table-box").children('.fixed-table_fixed-left').children('.fixed-table_body-wraper').find('tr').eq(0));
 });
 */

function renderRow(locks,date) {
    (function () {
        for(var i in locks){
            lock=locks[i];
            var TDs_row=fixedTable.fixedTableBody.find("tbody tr").eq(1).find("td:not(:first)");//表格每一行row的第一个td是房间信息所以舍弃.
            //auth获取开锁授权信息
            $.ajax({
                type:"POST",
                url:projectPath+"/unlock/getUnlockAuthorizationDailyArr.do",
                async:false,
                data:{
                    "gatewayCode":lock.gatewayCode,
                    "lockCode":lock.lockCode,
                    "theDate":getDateStr(date)
                },
                dataType:'json',
                success:function(data,status,xhr){
                    authinfo=data;
                    var authinfodailyArr=authinfo.authinfoDaily;
                    var authinfodaily;
                    for(var i=0;i<authinfodailyArr.length;i++){
                        authinfodaily=authinfodailyArr[i];
                        if(authinfodaily.idIndexes.length+authinfodaily.pwdIndexes.length>0){
                            // fixedTable.fixedTableBody.find("tbody tr td").eq(i+16).addClass("cd-booked");
                            TDs_row.eq(i+15).addClass("cd-booked");
                        }
                    }
                },
                error:function(xhr,errorType,error){
                    console.log('错误');
                }
            });
            //record获取入住记录
            $.ajax({
                type:"POST",
                url:projectPath+"/record/getLockUnlockRecordDaily.do",
                async:false,
                data:{"lockCode":lock.lockCode,"theDate":getDateStr(date)},
                dataType:'json',
                success:function(data,status,xhr){
                    recordinfo=data;
                    (function () {
                        var recordDaily;
                        for(var i=0;i<recordinfo.length;i++){
                            recordDaily=recordinfo[i];
                            if(recordDaily.totalSize>0){
                                // fixedTable.fixedTableBody.find("tbody tr td").eq(i+1).addClass("cd-select");
                                TDs_row.eq(i).addClass("cd-select");
                            }
                        }
                    })()
                },
                error:function(xhr,errorType,error){
                    console.log('错误');
                }
            });
        }
    })();
}
function getAuthinfo(specificGatewayCode,specificLockCode,date) {
    $.ajax({
        type:"POST",
        url:projectPath+"/unlock/getUnlockAuthorizationDailyArr.do",
        async:true,
        data:{
            "gatewayCode":specificGatewayCode,
            "lockCode":specificLockCode,
            "theDate":getDateStr(date)
        },
        dataType:'json',
        success:function(data,status,xhr){
            authinfo=data;
            // console.log("authinfo:"+authinfo);
            var authinfodailyArr=authinfo.authinfoDaily;
            // for(var i=0;i<authinfodailyArr.length;i++){
            //     console.log("index:"+i+",date:"+authinfodailyArr[i].date+",time:"+authinfodailyArr[i].time+",ids:"+authinfodailyArr[i].idIndexes+",pwds:"+authinfodailyArr[i].pwdIndexes);
            // }
            var authinfodaily;
            for(var i=0;i<authinfodailyArr.length;i++){
                authinfodaily=authinfodailyArr[i];
                if(authinfodaily.idIndexes.length+authinfodaily.pwdIndexes.length>0){
                    fixedTable.fixedTableBody.find("tbody tr td").eq(i+16).addClass("cd-booked");
                    // fixedTable.fixedTableBody.find("tbody tr td").eq(i+16).addClass("rightclick");
                    // console.log("tbody tr (3)[0] :"+fixedTable.fixedTableBody.find("tbody tr").eq(3));
                }
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

function getDailyRecords(lockCode,date) {
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getLockUnlockRecordDaily.do",
        async:true,
        data:{"lockCode":lockCode,"theDate":getDateStr(date)},
        dataType:'json',
        success:function(data,status,xhr){
            recordinfo=data;
            // console.log("recordinfo:"+recordinfo);
            (function () {
                var recordDaily;
                for(var i=0;i<recordinfo.length;i++){
                    recordDaily=recordinfo[i];
                    if(recordDaily.totalSize>0){
                        fixedTable.fixedTableBody.find("tbody tr td").eq(i+1).addClass("cd-select");
                        // fixedTable.fixedTableBody.find("tbody tr td").eq(i+1).addClass("rightclick");
                    }
                }
            })()
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

function renderTable(date) {
    // getDateArr(date);
    //表格标题-时间重设 function resetTableHeaderTxt
    var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
    for(var i=0;i<=30;i++){
        DIV_header[i].innerText=dateStrArr[i];
    }
    //表格数据行-添加数据
    fixedTable.addRow(function (){
        var html = '';
        (function () {
            for(var i in subordinates){
                lock=subordinates[i];//$(this).parent("tr").attr("roomid")
                html += '<tr roomid="'+lock.gatewayCode+'-'+lock.lockCode+'">';
                html += '<td class="table-width190"><div class="table-hight1 table-cell table-width190 table-butstyle">'+lock.lockName+'</div></td>';
                for (var i=0; i<dateArr.length; i++){
                    html += '<td class="table-width140"><div class="cd table-hight1 table-width140">'+dateStrArr[i]+'</div></td>';
                }
                html += '</tr>';
            }
        })();
        return html;
    });
    //表格标题栏时间控件label值.
    if($('.current-date label').length>1){
        $('.current-date label')[1].innerText = getDateStr(date);
    }

    // getAuthinfo(specificGatewayCode,specificLockCode,date);
    // getDailyRecords(specificLockCode,date);

    renderRow(subordinates,date);
}

$(document).ready(function () {
    $(".navbar-collapse ul:first li:eq(3)").addClass("active");
    getLocks();
    theDate=getZeroOfDate(new Date());
    getDateArr(theDate);
    var datehtml=
        '<div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>'+
        '<div id="datetimepicker" class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">'+
            '<input class="form-control" size="16" type="text" value="" readonly style="display:none">'+
            '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>'+
        '</div>';

    fixedTable = new FixedTable({
        wrap: document.getElementById("test_fixedTable"),//生成的表格需要放到哪里
        type: "row-col-fixed",//表格类型，有：head-fixed、col-fixed、row-col-fixed
        extraClass: "",//需要添加到表格中的额外class
        maxHeight: true,
        fields: [//表格的列
            {
                width: "206px",
                // field: '<th class="table-width1 "><div class="table-time table-header-hight58 table-cell table-width1 table-butstyle">当前日期：'+dateStrArr[15]+'</div></th>',
                field: '<th class="table-width190 table-butstyle"><div class="table-header-hight58 table-cell table-width190 table-butstyle">'+datehtml+'</div></th>',
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
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[29]+'</div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle">'+dateStrArr[30]+'</div></th>',
                htmlDom: true,
                fixed: false
            }
        ],
        tableDefaultContent: "<div>我是一个默认的div</div>"
    });
    // plugin datetimepicker event on changeDate 要在renderTable之前才有效.
    $('#datetimepicker')
        // .datetimepicker()
        .on('changeDate', function(ev){
            // console.log('ev.date.valueOf() : '+ev.date.valueOf());
            if (ev.date.valueOf() !== theDate.getTime()+8*60*60*1000){
                console.log('日期改变');
                theDate=new Date(ev.date.valueOf());
                theDate.setHours(0);
                theDate.setMinutes(0);
                theDate.setSeconds(0);
                theDate.setMilliseconds(0);

                getDateArr(theDate);
                fixedTable.empty();
                renderTable(theDate);
            }
        });
    renderTable(theDate);
/*
    $.contextMenu({
        // define which elements trigger this menu
        selector: ".rightclick",
        // define the elements of the menu
        items: {
            identity: {name: "身份证授权", callback: function(key, opt){
                // $('#reply-identity').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }},
            password: {name: "密码授权", callback: function(key, opt){
                // $('#reply-password').niftyModal();
            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){
                // $('#reply-unlocking').niftyModal();
            }}
        },
        callback: function(itemKey, opt){
            // Alert the key of the item and the trigger element's id.
            // console.log("Clicked on " + itemKey + " on element " + opt.$trigger.attr("id"));
            console.log("Clicked on " + itemKey + " on element " + opt.$trigger.parent("tr").attr("roomid"));
            console.log($(this).parent("tr").attr("roomid"));
            // Do not close the menu after clicking an item
            return false;
        }
    });
*/
    $.contextMenu({
        selector: ".cd-select:not(.cd-booked)",
        items: {
            ticket: {name: "入住记录", callback: function(key, opt){
                // $('#reply-ticket').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }}
        }
    });
    $.contextMenu({
        selector: ".cd-booked:not(.cd-select)",
        items: {
            identity: {name: "身份证授权", callback: function(key, opt){
                // $('#reply-identity').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }},
            password: {name: "密码授权", callback: function(key, opt){
                // $('#reply-password').niftyModal();
            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){
                // $('#reply-unlocking').niftyModal();
            }}
        }
    });
    $.contextMenu({
        selector: ".cd-select.cd-booked",
        items: {
            ticket: {name: "入住记录", callback: function(key, opt){
                // $('#reply-ticket').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }},
            identity: {name: "身份证授权", callback: function(key, opt){
                // $('#reply-identity').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }},
            password: {name: "密码授权", callback: function(key, opt){
                // $('#reply-password').niftyModal();
            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){
                // $('#reply-unlocking').niftyModal();
            }}
        }
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