var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

var landlord;
var locks;
var lock;
var dateArr=new Array;
var dateStrArr=new Array;
var theadDateStrArr=new Array;
var today,theDate,theTime;
var authinfo;
var recordinfo;
var startTime;
var endTime;
var specificGatewayCode;//specificGatewayCode="GWH0081702000003";
var specificLockCode;//specificLockCode="LCN0011721000001";
var index;
var html;
var fixedTable;
var TH_header;
var name;
var cardNumb;
var password;
var tableWrapper;
var tableElement;
var tableInstance;
var countTable=0;
var params;

function getStartOfDate(date) {
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date;
}
function getDateStr(date) {
    // (function () {
    //     var year,week,month,day,hours,minutes,seconds;
    //     year=date.getFullYear();
    //     month=date.getMonth()+1;
    //     day=date.getDate();
    //     if(month<10){month='0'+month}
    //     if(day<10){day='0'+day}
    //     return year+'-'+month+'-'+day;
    // })();
    return date.getFullYear()+ "-" + (date.getMonth()+1) + "-" + date.getDate();
}
function getTheadDateStr(date) {
    // (function () {
        // var week,hours,minutes,seconds;
        var year,month,day;
        year=date.getFullYear();
        month=date.getMonth()+1;
        day=date.getDate();
        if(month<10){month='0'+month}
        if(day<10){day='0'+day}
        if(month==1 && day==1){//01==1为true,只能用双等于号.
            return year+'-'+month+'-'+day;
        }
        return month+'-'+day;
    // })();
}
function getDateArr(date) {
    date=getStartOfDate(date);
    dateArr.length=0;
    dateStrArr.length=0;
    theadDateStrArr.length=0;
    var newDate;
    for (var i=-15;i<16;i++){
        newDate=new Date(date.getTime() + i*24*60*60*1000);
        dateArr.push(newDate);
        dateStrArr.push(getDateStr(newDate));
        theadDateStrArr.push(getTheadDateStr(newDate));
    }
}
function getLocks() {
    $.ajax({
        type:"POST",
        url:"user/getSubordinateHierarchyTillLock.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',
        success:function(data,status,xhr){
            landlord=data;
            landlord={
                "phoneNumber": "18255683932",
                "grade": 10,
                "name": "汪凯",
                "location": "业主地址",
                "subordinateList": [
                    {
                        "gatewayName":"网关3",
                        "gatewayCode":"GWH0081702000003",
                        "gatewayLocation":"网关地址",
                        "gatewayComment":"网关备注",
                        "gatewayStatus":4,
                        "lockName": "房间1",
                        "lockCode": "LCN0011721000001",
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
                        "lockCode": "LCN0011702000002",
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
                        "lockCode": "LCN0011702000003",
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
                        "lockCode": "LCN0011721000001",
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
                        "lockCode": "LCN0011702000005",
                        "lockLocation": "bigdata",
                        "lockComment": "bigdata",
                        "lockStatus": "1",
                        "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间6",
                    //     "lockCode": "LCN0011702000006",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间7",
                    //     "lockCode": "LCN0011702000007",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间8",
                    //     "lockCode": "LCN0011702000008",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间9",
                    //     "lockCode": "LCN0011702000009",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间10",
                    //     "lockCode": "LCN0011702000010",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间11",
                    //     "lockCode": "LCN0011702000011",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间12",
                    //     "lockCode": "LCN0011702000012",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间13",
                    //     "lockCode": "LCN0011702000013",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    // },{
                    //     "gatewayName":"网关3",
                    //     "gatewayCode":"GWH0081702000003",
                    //     "gatewayLocation":"网关地址",
                    //     "gatewayComment":"网关备注",
                    //     "gatewayStatus":4,
                    //     "lockName": "房间14",
                    //     "lockCode": "LCN0011702000014",
                    //     "lockLocation": "bigdata",
                    //     "lockComment": "bigdata",
                    //     "lockStatus": "1",
                    //     "lockPower": "4"
                    }
                ],
                "roomTypeContainRoomList": [
                    {
                        "roomTypeId": "20180314152405182556839321904192",
                        "roomType": "标准单人间",
                        "roomInfoList": []
                    },{
                        "roomTypeId": "20180316152405182556839321904192",
                        "roomType": "标准双人间",
                        "roomInfoList": [
                            {
                                "roomId": "20180323072240182556839322944258",
                                "roomName": "D101",
                                "gatewayCode": "GWH0081702000003",
                                "lockCode": "LCN0011721000001"
                            },{
                                "roomId": "20180323082240182556839322944258",
                                "roomName": "D101",
                                "gatewayCode": "GWH0081702000003",
                                "lockCode": "LCN0011721000001"
                            },{
                                "roomId": "20180323102240182556839322944258",
                                "roomName": "D101",
                                "gatewayCode": "GWH0081702000003",
                                "lockCode": "LCN0011721000001"
                            }
                        ]
                    },{
                        "roomTypeId": "20180315141955182556839321308676",
                        "roomType": "豪华大床房",
                        "roomInfoList": [
                            {
                                "roomId": "20180318122240182556839322944258",
                                "roomName": "L101",
                                "gatewayCode": "GWH0081702000003",
                                "lockCode": "LCN0011721000001"
                            },{
                                "roomId": "20180318132240182556839322944258",
                                "roomName": "L101",
                                "gatewayCode": "GWH0081702000003",
                                "lockCode": "LCN0011721000001"
                            },{
                                "roomId": "20180318142240182556839322944258",
                                "roomName": "L101",
                                "gatewayCode": "GWH0081702000003",
                                "lockCode": "LCN0011721000001"
                            }
                        ]
                    }
                ]
            };
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}
function drawTableHead(date) {
    getDateArr(date);
    //表格标题-时间重设 function resetTableHeaderTxt
    var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");
    for(var i=0;i<31;i++){
        // DIV_header[i].innerText=dateStrArr[i];
        DIV_header[i].innerText=theadDateStrArr[i];
        TH_header.eq(i+1).attr("time",dateStrArr[i]);
    }
}
function fillTable() {
    //表格数据行-添加数据
    fixedTable.addRow(function (){
        html = '';
        (function () {
            locks=landlord.subordinateList;
            for(var j in locks){
                lock=locks[j];
                html += '<tr gatewayid="'+lock.gatewayCode+'" lockid="'+lock.lockCode+'">';
                html += '<td class="table-width210"><div class="table-hight1 table-cell table-width210 table-butstyle">'+lock.lockName+'</div></td>';
                for (var i=0; i<dateArr.length; i++){
                    // html += '<td class="table-width140"><div class="cd table-hight1 table-width140">'+dateStrArr[i]+'</div></td>';
                    html += '<td class="table-width140"><div class="cd table-hight1 table-width140"></div></td>';
                }
                html += '</tr>';
            }
        })();
        return html;
    });
}
function drawFixedColumn(date) {
    //表格标题栏时间控件label值.
    if($('.current-date label').length>1){
        $('.current-date label')[1].innerText = getDateStr(date);
    }
}
function renderRow(landlord,date) {
    (function () {
        // var time1=new Date();
        locks=landlord.subordinateList;
        for(var j in locks){
            lock=locks[j];
            var TDs_row=fixedTable.fixedTableBody.find("tbody tr").eq(j).find("td:not(:first)");//表格每一行row的第一个td是房间信息所以舍弃.
            //auth获取开锁授权信息
            $.ajax({
                type:"POST",
                url:projectPath+"/unlock/getUnlockAuthorizationDailyArr.do",
                async:false,
                data:{
                    "ownerPhoneNumber":landlord.phoneNumber,
                    "gatewayCode":lock.gatewayCode,
                    "lockCode":lock.lockCode,
                    "theDate":getDateStr(date)
                },
                dataType:'json',
                success:function(data,status,xhr){
                    if(data.success){
                        if(data.biz.code===0){
                            authinfo=data.biz.data;
                            var authinfodailyArr=authinfo.authinfoDaily;
                            var authinfodaily;
                            var authinfodailyArrLength=authinfodailyArr.length;
                            for(var i=0;i<authinfodailyArrLength;i++){
                                authinfodaily=authinfodailyArr[i];
                                if(authinfodaily.idIndexes.length+authinfodaily.pwdIndexes.length>0){
                                    TDs_row.eq(i+15).addClass("cd-booked");
                                }else {
                                    TDs_row.eq(i+15).addClass("cd-vacant");
                                }
                            }
                        }else if(data.biz.code===1){
                            for(var i=0;i<16;i++){
                                TDs_row.eq(i+15).addClass("cd-vacant");
                            }
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
                data:{"ownerPhoneNumber":landlord.phoneNumber,"lockCode":lock.lockCode,"theDate":getDateStr(date)},
                dataType:'json',
                success:function(data,status,xhr){
                    if(data.success){
                        if(data.biz.code===0){
                            recordinfo=data.biz.data;
                            var recordinfoLength=recordinfo.length;
                            var recordDaily;
                            for(var i=0;i<recordinfoLength-1;i++){//i<recordinfoLength-1表示当天的刷卡记录不渲染表格cell.
                                recordDaily=recordinfo[i];
                                if(recordDaily.totalSize>0){
                                    TDs_row.eq(i).addClass("cd-unlockrecord");
                                }else{
                                    TDs_row.eq(i).addClass("cd-blank");
                                }
                            }
                        }
                    }
                },
                error:function(xhr,errorType,error){
                    console.log('错误');
                }
            });
        }
        // var time2=new Date();
        // console.log("ajax num:"+locks.length+",time:"+(time2.getTime()-time1.getTime())/1000);
    })();
}
function drawTable(date) {
    drawTableHead(date);
    fillTable();
    drawFixedColumn(date);
    renderRow(landlord,date);
}
//获取某个房间某天的请求参数,element是tbody->tr->td,element==0是房间号cell.
function getCellParam(element) {
    specificGatewayCode=element.parent("tr").attr("gatewayid");
    specificLockCode=element.parent("tr").attr("lockid");
    index=element.index();//index==0是房间cell.
    TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");//第0个thead的th即房间号cell.
    theTime=TH_header.eq(index).attr("time");
}

$(document).ready(function () {
    $(".navbar-collapse ul:first li:eq(3)").addClass("active");
    getLocks();
    html='';
    html += '<div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>';
    html += '<div id="datetimepicker" class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">';
    html +=     '<input class="form-control" size="16" type="text" value="" readonly style="display:none">';
    html +=     '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>';
    html += '</div>';

    var fields=new Array;
    fields[0]={
        width: "210",
        field: '<th class="table-width210"><div class="table-header-hight58 table-cell table-width210 table-butstyle">'+html+'</div></th>',
        htmlDom: true,
        fixed: true
    };
    for(var i=1;i<32;i++){
        fields[i]={
            width: "140px",
            field: '<th class="table-width140"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
            htmlDom: true,
            fixed: false
        }
    }
    fixedTable = new FixedTable({
        wrap: document.getElementById("theFixedTable"),//生成的表格需要放到哪里
        type: "row-col-fixed",
        extraClass: "",//需要添加到表格中的额外class
        maxHeight: true,
        /*
        fields: [//表格的列
            {
                width: "206px",
                field: '<th class="table-width190 table-butstyle"><div class="table-header-hight58 table-cell table-width190 table-butstyle">'+html+'</div></th>',
                htmlDom: true,
                fixed: true
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            },{
                width: "140px",
                field: '<th class="table-width140 table-butstyle"><div class="table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
                htmlDom: true,
                fixed: false
            }
        ],
        */
        fields: fields,
        tableDefaultContent: "<div>表格数据内容为空</div>"
    });
    // plugin datetimepicker event on changeDate 要在drawTable之前才有效.
    $('#datetimepicker')
        .datetimepicker()
        .on('changeDate', function(ev){
            // if (ev.date.valueOf() !== theDate.getTime()+8*60*60*1000){
                theDate=new Date(ev.date.valueOf());
                fixedTable.empty();
                drawTable(theDate);
            // }
        });
    drawTable(new Date());

    $.contextMenu({
        selector: ".cd-unlockrecord:not(.cd-booked)",
        items: {
            record: {
                name: "入住记录", callback: function(key, opt){
                    $('#md-record').niftyModal();
                    getCellParam($(this));
                    tableElement=$("#table-unlockrecord");
                    datatableSet.function_record.drawtable(tableElement);
                }
            }
        }
    });
    $.contextMenu({
        selector: ".cd-booked:not(.cd-unlockrecord)",
        items: {
            identity: {
                name: "身份证授权", callback: function (key, opt) {
                    specificGatewayCode = $(this).parent("tr").attr("gatewayid");
                    specificLockCode = $(this).parent("tr").attr("lockid");
                    $('#md-identity').niftyModal();
                }
            },
            separator1: "-----",
            password: {
                name: "密码授权", callback: function (key, opt) {
                    specificGatewayCode = $(this).parent("tr").attr("gatewayid");
                    specificLockCode = $(this).parent("tr").attr("lockid");
                    $('#md-pwd').niftyModal();
                }
            },
            separator2: "-----",
            authorization : {
                name: "开锁信息", callback: function(key, opt){
                    $('#md-authorization').niftyModal();
                    getCellParam($(this));
                    tableElement=$("#table-authorization");
                    datatableSet.function_authorization.drawtable(tableElement);
                }
            }
        }
    });
    /*
    $.contextMenu({
        selector: ".cd-unlockrecord.cd-booked",
        items: {
            record: {name: "入住记录", callback: function(key, opt){

            }},
            identity: {name: "身份证授权", callback: function(key, opt){

            }},
            password: {name: "密码授权", callback: function(key, opt){

            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){

            }}
        }
    });
    */
    //cd-vacant
    $.contextMenu({
        selector: ".cd-vacant",
        items: {
            identity: {
                name: "身份证授权", callback: function (key, opt) {
                    // getCellParam($(this));
                    specificGatewayCode = $(this).parent("tr").attr("gatewayid");
                    specificLockCode = $(this).parent("tr").attr("lockid");
                    $('#md-identity').niftyModal();
                }
            },
            separator1: "-----",
            password: {
                name: "密码授权", callback: function (key, opt) {
                    specificGatewayCode = $(this).parent("tr").attr("gatewayid");
                    specificLockCode = $(this).parent("tr").attr("lockid");
                    $('#md-pwd').niftyModal();
                    //$('#submit-pwd').click(function(){}),事件click不能嵌入其他事件之中,会造成多次绑定.
                }
            }
        }
    });
    var options_daterange={
        "format": 'YYYY-MM-DD HH:mm',// format: 'MM/DD/YYYY', // format: 'MM/DD/YYYY h:mm A',
        "separator":'  ~  ',
        "timePicker": true,
        "timePicker12Hour": false,//24小时制
        "timePickerIncrement": 30,//min
        "dateLimit": {"days": 366},
        "showCustomRangeLabel": true,
        "alwaysShowCalendars": true,
        "autoUpdateInput": true,//默认为true
        "startDate": moment(),
        "endDate": moment().add(1,'days'),
        "minDate": moment(),
        "maxDate": moment().add(1,'years'),
        "opens": "right",
        "drops": "down",
        "showDropdowns": true,
        "showWeekNumbers": true,
        "buttonClasses": ['btn'],
        "applyClass": 'btn-small btn-primary',
        "cancelClass": 'btn-small'
    };
    options_daterange.ranges={
        // 'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
        // 'Last 7 Days': [moment().subtract('days', 6), moment()],
        // 'Last 30 Days': [moment().subtract('days', 29), moment()],
        // 'Today': [moment(), moment()],
        // 'This Month': [moment().startOf('month'), moment().endOf('month')],
        // 'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')],
        '明日中午': [
            moment(),
            moment().set('date',moment().get('date')+1).set('hour',12).set('minute',0).set('second',0)
        ],
        '一天': [moment(),moment().add(1,'days')],
        '一周': [moment(),moment().add(7,'days')],
        '一月': [moment(),moment().add(1,'month')]
    };
    options_daterange.locale= {
        // direction: 'ltr',
        // format: 'YYYY-MM-DD HH:mm',//无效// format: 'MM/DD/YYYY', // format: 'MM/DD/YYYY h:mm A', // format: 'MM/DD/YYYY HH:mm',
        // separator: ' - ',//无效
        applyLabel: '确定',
        cancelLabel: '取消',
        fromLabel: '起始',
        toLabel: '结束',
        customRangeLabel: '选取',
        daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
        monthNames: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        firstDay: 1
    };
    $('#time-book-identity').daterangepicker(options_daterange,function (start, end, label) {
        startTime=start.format('YYYY-MM-DD HH:mm');
        endTime=end.format('YYYY-MM-DD HH:mm');
        console.log("startTime:"+startTime+";endTime:"+endTime+"predefined range:"+label);
    });
    $('#time-book-pwd').daterangepicker(options_daterange,function (start, end, label) {
        startTime=start.format('YYYY-MM-DD HH:mm');
        endTime=end.format('YYYY-MM-DD HH:mm');
        console.log("startTime:"+startTime+";endTime:"+endTime+"predefined range:"+label);
    });

    $('#submit-identity').click(function () {
        name = $("form#form-identity input[name='name']").val();
        cardNumb = $("form#form-identity input[name='cardNumb']").val();
        params = {
            "ownerPhoneNumber": landlord.phoneNumber,
            "gatewayCode": specificGatewayCode,
            "lockCode": specificLockCode,
            "name": name,
            "cardNumb": cardNumb,
            "startTime": startTime,
            "endTime": endTime
        };
        $.ajax({
            type: "POST",
            url: "unlock/authUnlockById.do",
            async: false,
            data: params,
            dataType: 'json',
            success: function (data, status, xhr) {
                alert('data:' + data);
            },
            error: function (xhr, errorType, error) {
                console.log('错误');
            }
        });
    });
    $('#submit-pwd').click(function () {
        password = $("form#form-pwd input[name='password']").val();
        params = {
            "ownerPhoneNumber": landlord.phoneNumber,
            "gatewayCode": specificGatewayCode,
            "lockCode": specificLockCode,
            "password": password,
            "startTime": startTime,
            "endTime": endTime
        };
        $.ajax({
            type: "POST",
            url: "unlock/authUnlockByPwd.do",
            async: false,
            data: params,
            dataType: 'json',
            success: function (data, status, xhr) {
                alert('data:' + data);
            },
            error: function (xhr, errorType, error) {
                console.log('错误');
            }
        });
    });

    App.init();
});

var datatableSet = {
        options : {
            DEFAULT_OPTION : { //DataTables初始化选项
                language: {
                    "sProcessing":   "处理中...",
                    "sLengthMenu":   "每页 _MENU_ 项",
                    "sZeroRecords":  "没有匹配结果",
                    "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                    "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                    "sInfoPostFix":  "",
                    "sSearch":       "搜索:",
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
                autoWidth: false,   //禁用自动调整列宽
                stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
                order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
                processing: false,  //隐藏加载提示,自行处理
                serverSide: true,   //启用服务器端分页
                searching: false,    //禁用原生搜索
                // bProcessing: true,
                // bServerSide: true,
                iDisplayLength : 5,//默认每页数量
                bLengthChange : false, //改变每页显示数据数量,bLengthChange==false会隐藏lengthMenu
                lengthMenu : [5,10,15],
                ordering : true,
                stateSave : true,
                retrieve : true
                //bPaginate: true, //翻页功能
                //bFilter : true, //过滤功能
                // bSort : false, //排序功能
                //bInfo : true,//页脚信息
                //bAutoWidth : true,//自动宽度
                //bPaginate : true,
                //bProcessing: true//服务器端进行分页处理的意思
            },
            COLUMN: {
                CHECKBOX: { //复选框单元格
                    className: "td-checkbox",
                    orderable: false,
                    width: "30px",
                    data: null,
                    render: function (data, type, row, meta) {
                        return '<input type="checkbox" class="iCheck">';
                    }
                }
            },
            RENDER: {   //常用render可以抽取出来，如日期时间、头像等
                ELLIPSIS: function (data, type, row, meta) {
                    data = data||"";
                    return '<span title="' + data + '">' + data + '</span>';
                }
            }
        },
        function_record : {
            getQueryParams: function (data) {
                var param = {};
                //组装排序参数
                if (data.order && data.order.length && data.order[0]) {
                    switch (data.order[0].column) {
                        case 0:
                            param.orderColumn = "openMode";
                            break;
                        case 1:
                            param.orderColumn = "timestamp";
                            break;
                        case 2:
                            param.orderColumn = "credential";
                            break;
                        case 3:
                            param.orderColumn = "name";
                            break;
                        default:
                            param.orderColumn = "timestamp";
                            break;
                    }
                    //排序方式asc或者desc
                    param.orderDir = data.order[0].dir;
                }
                // param.deviceid = $("#deviceid-search").val();
                // param.deviceip = $("#deviceip-search").val();
                // param.status = $("#status-search").val();
                // param.startTime=startTime;
                // param.endTime=endTime;

                param.ownerPhoneNumber=landlord.phoneNumber;
                param.date=theTime;//yyyy_MM_dd格式的日期字符串.
                param.gatewayCode=specificGatewayCode;
                param.lockCode=specificLockCode;
                //组装分页参数
                param.startIndex = data.start;
                param.pageSize = data.length;
                param.draw = data.draw;
                return param;
            },
            drawtable: function (element) {
                //element代表table元素. element===$("#table-unlockrecord");
                tableWrapper= element.parent('div .content').eq(0);
                // if(undefined!=tableInstance){
                //     // tableInstance.clear();
                //     tableInstance.destroy();
                // }
                $.fn.dataTable.tables({api: true}).destroy();
                tableInstance = element.dataTable($.extend(true, {}, datatableSet.options.DEFAULT_OPTION, {
                    ajax: function (data, callback, settings) {
                        param = datatableSet.function_record.getQueryParams(data);
                        // console.log('contextMenu --> unlockrecord index:'+countTable++);
                        tableWrapper.spinModal();
                        $.ajax({
                            type: "POST",
                            url: projectPath+'/record/getDailyUnlockRecordLockPage.do',
                            cache: false,  //禁用缓存
                            data: param,  //传入组装的参数
                            dataType: "json",
                            success: function (data) {
                                var returnData = {};
                                if(data.success){
                                    if(data.biz.code===0){
                                        var result=data.biz.data;
                                        returnData.draw = result.draw;//后台返回draw计数器转int,防止跨站脚本(XSS)攻击
                                        returnData.recordsTotal = result.total;//总记录数
                                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                                        returnData.data = result.pageData;
                                    }else{
                                        console.log("biz.code:"+data.biz.code+",biz.msg:"+data.biz.msg);
                                    }
                                }else{
                                    console.log("errmsg:"+data.errmsg);
                                }
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                if(undefined==returnData.data || null==returnData.data){
                                    returnData.recordsTotal=0;
                                    returnData.recordsFiltered=0;
                                    returnData.data=[];
                                }
                                tableWrapper.spinModal(false);
                                callback(returnData);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("查询失败");
                                tableWrapper.spinModal(false);
                            }
                        });
                    },
                    //绑定数据
                    columns: [
                        {
                            data: "openMode",
                            orderable: false,
                            render: function (data, type, row, meta) {
                                if(1==data){
                                    return "身份证开锁"
                                }else if(2==data){
                                    return "密码开锁"
                                }else if(3==data){
                                    return "门卡开锁"
                                }else{
                                    return null;
                                }
                            }
                        },{
                            data: "timestamp",
                            // render: datatableSet.options.RENDER.ELLIPSIS//alt效果
                        },{
                            data: "credential"
                        },{
                            data: "name"
                        }
                        // ,{
                        //     data: "status",
                        //     render: function (data, type, row, meta) {
                        //         return (data == 0 ? "模块正常" :  "模块异常");
                        //     }
                        // }
                    ],
                    "columnDefs": [
                        {
                            "defaultContent": "",
                            "targets": "_all"
                        }
                    ],
                    "drawCallback": function (settings) {}
                })).api();
            }
        },
        function_authorization : {
            getQueryParams: function (data) {
                var param = {};
                //组装排序参数
                if (data.order && data.order.length && data.order[0]) {
                    switch (data.order[0].column) {
                        case 0:
                            param.orderColumn = "openMode";
                            break;
                        case 1:
                            param.orderColumn = "credential";
                            break;
                        case 2:
                            param.orderColumn = "name";
                            break;
                        case 3:
                            param.orderColumn = "startTime";
                            break;
                        case 4:
                            param.orderColumn = "endTime";
                            break;
                        default:
                            param.orderColumn = "openMode";
                            break;
                    }
                    //排序方式asc或者desc
                    param.orderDir = data.order[0].dir;
                }
                param.ownerPhoneNumber=landlord.phoneNumber;
                param.date=theTime;//yyyy_MM_dd格式的日期字符串.
                param.gatewayCode=specificGatewayCode;
                param.lockCode=specificLockCode;
                param.startIndex = data.start;
                param.pageSize = data.length;
                param.draw = data.draw;
                return param;
            },
            drawtable: function (element) {//element===$('#table-authorization');
                tableWrapper= element.parent('div .content').eq(0);
                // if(undefined!=tableInstance){
                //     tableInstance.destroy();
                // }
                $.fn.dataTable.tables({api: true}).destroy();
                tableInstance = element.dataTable($.extend(true, {}, datatableSet.options.DEFAULT_OPTION, {
                    ajax: function (data, callback, settings) {
                        param = datatableSet.function_authorization.getQueryParams(data);
                        // console.log('contextMenu --> authorization index:'+countTable++);
                        tableWrapper.spinModal();
                        $.ajax({
                            type: "POST",
                            url: projectPath+'/unlock/getDailyUnlockAuthorizationRecord.do',
                            cache: false,
                            data: param,
                            dataType: "json",
                            success: function (data) {
                                var returnData = {};
                                if(data.success){
                                    if(data.biz.code===0){
                                        var result=data.biz.data;
                                        returnData.draw = result.draw;
                                        returnData.recordsTotal = result.total;
                                        returnData.recordsFiltered = result.total;
                                        returnData.data = result.pageData;
                                    }else{
                                        console.log("biz.code:"+data.biz.code+",biz.msg:"+data.biz.msg);
                                    }
                                }else{
                                    console.log("errmsg:"+data.errmsg);
                                }
                                //调用DataTables提供的callback方法,代表数据已封装完成并传回DataTables进行渲染. 此时的数据需确保正确无误,异常判断应在执行此回调前自行处理完毕.
                                if(undefined==returnData.data || null==returnData.data){
                                    returnData.recordsTotal=0;
                                    returnData.recordsFiltered=0;
                                    returnData.data=[];
                                }
                                tableWrapper.spinModal(false);
                                callback(returnData);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("查询失败");
                                tableWrapper.spinModal(false);
                            }
                        });
                    },
                    //绑定数据
                    columns: [
                        {
                            data: "openMode",
                            orderable: false,
                            render: function (data, type, row, meta) {
                                if(1==data){
                                    return "身份证"
                                }else if(2==data){
                                    return "密码"
                                }else if(3==data){
                                    return "门卡"
                                }else{
                                    return null;
                                }
                            }
                        },{
                            data: "credential"
                        },{
                            data: "name"
                        },{
                            data: "startTime",
                            // render: datatableSet.options.RENDER.ELLIPSIS//alt效果
                        },{
                            data: "endTime",
                            // render: datatableSet.options.RENDER.ELLIPSIS//alt效果
                        },{
                            // className : "td-operation",
                            data: null,
                            defaultContent:"",
                            orderable : false,
                            width : "50px"
                        }
                    ],
                    "columnDefs": [
                        {
                            "defaultContent": "",
                            "targets": "_all"
                        }
                    ],
                    "createdRow": function ( row, data, index ) {
                        //行渲染回调,在这里可以对该行dom元素进行任何操作
                        //给当前行加样式
                        if (data.role) {
                            $(row).addClass("info");
                        }
                        //给当前行某列加样式
                        // $('td', row).eq(3).addClass(data.status?"text-success":"text-error");
                        // //不使用render，改用jquery文档操作呈现单元格
                        // var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
                    // '<a class="label label-danger btn btn-danger btn-xs" onclick="delJunior(13998892002);"><i class="fa fa-times"></i></a>
                        var $btnDel = $('<button type="button" class="btn btn-xs btn-danger btn-del">删除</button>');
                        $('td', row).eq(5).append($btnDel);
                    },
                    "drawCallback": function (settings) {}
                })).api();
                tableElement.on("click", ".btn-del", function () {
                    //点击删除按钮
                    var row = tableInstance.row($(this).closest('tr'));
                    var item = row.data();
                    datatableSet.function_authorization.deleteItem(item);
                    row.remove().draw(false);
                });
            },
            deleteItem: function (item) {
                // console.log("item:"+item+" --> {\"openMode\":"+item.openMode + ";\"credential\":" + item.credential+"}");
                if(1==item.openMode){
                    params = {
                        "ownerPhoneNumber":landlord.phoneNumber,
                        "lockCode":specificLockCode,
                        "cardNumb":item.credential,
                        "serviceNumb":item.serviceNumb
                    };
                    $.ajax({
                        type: "POST",
                        url: "unlock/prohibitUnlockById.do",
                        async: false,
                        data: params,
                        dataType: 'json',
                        success: function (data, status, xhr) {
                            // alert('data:' + data);
                        },
                        error: function (xhr, errorType, error) {
                            console.log('错误');
                        }
                    });
                }else if(2==item.openMode){
                    params = {
                        "ownerPhoneNumber":landlord.phoneNumber,
                        "gatewayCode":specificGatewayCode,
                        "lockCode":specificLockCode,
                        "serviceNumb":item.serviceNumb
                    };
                    $.ajax({
                        type: "POST",
                        url: "unlock/prohibitUnlockByPwd.do",
                        async: false,
                        data: params,
                        dataType: 'json',
                        success: function (data, status, xhr) {
                            // alert('data:' + data);
                        },
                        error: function (xhr, errorType, error) {
                            console.log('错误');
                        }
                    });
                }
            }
        }
    };