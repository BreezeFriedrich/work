var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

var district;
var landlords;
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
var landlordPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var index;
var html;
var fixedTable;
var phoneNumber;
var tableType;

function getStartOfDate(date) {
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date;
}
function getDateStr(date) {
    return date.getFullYear()+ "-" + (date.getMonth()+1) + "-" + date.getDate();
}
function getTheadDateStr(date) {
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
        async:false,//同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',
        success:function(data,status,xhr){
            district=data;
            landlords=district.subordinateList;
            // if (landlords.length===0){
                /*
                district={
                    "phoneNumber":"18255683932","grade":20,"name":"测试用户","location":null,"subordinateList":[{
                        "phoneNumber":"17705155208","grade":10,"name":"雨花台区","location":"第1个业主地址","subordinateList":[{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A1","lockCode":"LK001001","lockLocation":"南京市雨花台区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A2","lockCode":"LK001002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A3","lockCode":"LK001003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        }]},
                        {"phoneNumber":"18858865706","grade":10,"name":"鼓楼区","location":"第2个业主地址","subordinateList":[{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B1","lockCode":"LK001001","lockLocation":"南京市鼓楼区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B2","lockCode":"LK001002","lockLocation":"南京市鼓楼区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B3","lockCode":"LK001003","lockLocation":"南京市鼓楼区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        }]}
                    ]};
                */
                district={
                    "phoneNumber":"17705155208","grade":20,"name":"雨花台区","location":null,"subordinateList":[{
                        "phoneNumber":"18255683932","grade":10,"name":"业主1","location":"第1个业主地址","subordinateList":[{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A1","lockCode":"LCN0011721000001","lockLocation":"南京市雨花台区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000002","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A2","lockCode":"LCN0011721000002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A3","lockCode":"LCN0011721000003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        }]},
                        {"phoneNumber":"18352478654","grade":10,"name":"业主2","location":"第2个业主地址","subordinateList":[{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000004","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B1","lockCode":"LCN0011721000004","lockLocation":"南京市鼓楼区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B2","lockCode":"LCN0011721000001","lockLocation":"南京市鼓楼区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000006","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B3","lockCode":"LCN0011721000006","lockLocation":"南京市鼓楼区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        }]}
                    ]};
                landlords=district.subordinateList;
            // }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}
function showNavSide() {
    (function () {
        html = '';
        html += '<li><a href="javascript:void(0);"><i class="fa inco-ctiy"></i><span class="selected">'+district.name+'</span></a></li>';
        for(var i in landlords){
            landlord=landlords[i];
            // html += '<li><a href="#"><i class="fa inco-map"></i><span>'+landlord.name+'</span></a></li>';
            html += '<li><a phone="'+landlord.phoneNumber+'" href="javascript:void(0);"><i class="fa inco-map"></i><span>'+landlord.name+'</span></a></li>';
        }
        $('ul.cl-vnavigation').append(html);
    })()
}

var tableFunc={
    common:{
        drawTableHead:function drawTableHead(date) {
            getDateArr(date);
            // $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").removeClass("table-width105");
            // $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").addClass("table-width210");
            //表格标题-时间重设 function resetTableHeaderTxt
            var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
            var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");
            for(var i=0;i<31;i++){
                DIV_header[i].innerText=theadDateStrArr[i];
                TH_header.eq(i+1).attr("time",dateStrArr[i]);
            }
        }
    },
    district:{
        fillTable:function fillTable() {
            //表格数据行-添加数据
            fixedTable.addRow(function (){
                html = '';
                for(var k in landlords){
                    landlord=landlords[k];
                    locks=landlord.subordinateList;
                    for(var j=0,length=locks.length;j<length;j++){
                        lock=locks[j];
                        html += '<tr landlord="'+landlord.phoneNumber+'" gatewayid="'+lock.gatewayCode+'" lockid="'+lock.lockCode+'">';
                        html +=     '<td><div class="table-hight1 table-width210 table-butstyle">'+lock.lockName+'</div></td>';
                        for(var i=0; i<31; i++){
                            // html += '<td><div class="cd table-hight1 table-width140">'+dateStrArr[i]+'</div></td>';
                            html += '<td><div class="cd table-hight1 table-width140"></div></td>';
                        }
                        html += '</tr>';
                    }
                }
                return html;
            });
        },
        drawFixedColumn:function drawFixedColumn(date) {//固定列.
            //表格标题栏时间控件label值.()
            if($('.current-date label').length>1){
                $('.current-date label')[1].innerText = getDateStr(date);
            }
            // $('.fixed-table-box').children('.fixed-table_header-wraper').find('#datetimepicker').remove();//headdatetimepicker.删除这个div会导致DIV_header[]32->31,drawTableHead会出错.

            var DIV;
            DIV=$(".fixed-table_fixed-left tbody tr td div");
            DIV.removeClass("table-width210");
            DIV.addClass("table-width105");
            var locksLength;
            var lineHeight;
            var HTML_landlord;
            var TR_fixedlefttbody;
            for(var k in landlords){
                landlord=landlords[k];
                locks=landlord.subordinateList;
                locksLength=locks.length;
                lineHeight=44*locksLength;
                HTML_landlord='<td rowspan="'+locksLength+'"><div style="line-height: '+lineHeight+'px" class="table-width105 table-butstyle">'+landlord.name+'</div></td>';
                TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[landlord="+landlord.phoneNumber+"]");
                TR_fixedlefttbody.eq(0).prepend(HTML_landlord);
            }
        },
        renderRow:function renderRow(landlords,date) {
            (function () {
                // var time1=new Date();
                var tr_num=0;
                for(var k in landlords){
                    landlord=landlords[k];
                    locks=landlord.subordinateList;
                    for(var j in locks){
                        lock=locks[j];
                        var TDs_row=fixedTable.fixedTableBody.find("tbody tr").eq(tr_num).find("td:not(:first)");
                        // var TDs_row=fixedTable.fixedTableBody.find("tbody tr[gatewayid="+lock.gatewayCode+"][lockid="+lock.lockCode+"]").find("td:not(:first)");//多个同gatewayCode和lockCode的门锁对应的tbody->tr只有第一个会被选中并渲染.
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
                                        for(var i=0;i<recordinfoLength-1;i++){
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
                        tr_num=tr_num+1;
                    }
                }
                // var time2=new Date();
                // console.log("ajax num:"+locks.length+",time:"+(time2.getTime()-time1.getTime())/1000);
            })();
        },
        drawTable:function drawTable(landlords,date) {
            tableType="district";
            tableFunc.common.drawTableHead(date);
            tableFunc.district.fillTable();
            tableFunc.district.drawFixedColumn(date);
            tableFunc.district.renderRow(landlords,date);
        },
        redrawTableOnDateChange://datechange事件的handler.
            function(event){
                theDate=new Date(event.date.valueOf());
                fixedTable.empty();
                tableFunc.district.drawTable(event.data.landlords,theDate);
            },
        completeTable:function (landlords,date) {
            tableFunc.district.drawTable(landlords,date);
            //元素$(".fixed-table_fixed-left").find("div#datetimepicker")左边固定栏是在fixedTable.addRow()之后产生的.所以事件要放在fillTable()之后.
            $(".fixed-table_fixed-left").find("div#datetimepicker").off('changeDate');
            $(".fixed-table_fixed-left").find("div#datetimepicker").on('changeDate',{landlords:landlords},tableFunc.district.redrawTableOnDateChange);
        }
    },
    landlord:{
        fillTable:function fillTable() {
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
        },
        drawFixedColumn:function drawFixedColumn(date) {
            //表格标题栏时间控件label值.
            if($('.current-date label').length>1){
                $('.current-date label')[1].innerText = getDateStr(date);
            }
        },
        renderRow:function renderRow(landlord,date) {
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
        },
        drawTable:function drawTable(landlord,date) {
            tableType="landlord";
            tableFunc.common.drawTableHead(date);
            tableFunc.landlord.fillTable();
            tableFunc.landlord.drawFixedColumn(date);
            tableFunc.landlord.renderRow(landlord,date);
        },
        redrawTableOnDateChange:
            function(event){
                theDate=new Date(event.date.valueOf());
                fixedTable.empty();
                tableFunc.landlord.drawTable(event.data.landlord,theDate);
            },
        completeTable:function (landlord,date) {
            tableFunc.landlord.drawTable(landlord,date);
            $(".fixed-table_fixed-left").find("div#datetimepicker").off('changeDate');
            $(".fixed-table_fixed-left").find("div#datetimepicker").on('changeDate',{landlord:landlord},tableFunc.landlord.redrawTableOnDateChange);
        }
    }
};

//获取某个房间某天的请求参数,element是tbody->tr->td,element==0是房间号cell.
function getCellParam(element) {
    specificGatewayCode=element.parent("tr").attr("gatewayid");
    specificLockCode=element.parent("tr").attr("lockid");
    index=element.index();//index==0是房间cell.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");//第0个thead的th即房间号cell.
    theTime=TH_header.eq(index).attr("time");
}

$(document).ready(function(){
    $(".navbar-collapse ul:first li:eq(3)").addClass("active");
    theDate=new Date();
    getLocks();
    showNavSide();
    var html='';
    html += '<div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>';
    html += '<div id="datetimepicker" class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">';
    html +=     '<input class="form-control" size="16" type="text" value="" readonly style="display:none">';
    html +=     '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>';
    html += '</div>';

    var fields=new Array;
    fields[0]={
        width: "210px",
        field: '<th><div class="table-header-hight58 table-cell table-width210 table-butstyle">'+html+'</div></th>',
        htmlDom:true,
        fixed:true,
        // fixedDirection:"left"
    };
    for(var i=1;i<32;i++){
        fields[i]={
            width: "140px",
            field: '<th><div class="rq table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
            htmlDom:true,
            // fixed:false
        }
    }
    fixedTable = new FixedTable({
        wrap : document.getElementsByClassName("container-table")[0],
        type : "row-col-fixed",
        extraClass: "",
        maxHeight : true,
        /*
        fields : [
            {
                width: "210px",
                field: '<th class="table-width210"><div class="table-header-hight58 table-cell table-width210 table-butstyle">'+html+'</div></th>',
                htmlDom:true,
                fixed:true,
                fixedDirection:"left"
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-02</div></th>',
                htmlDom:true,
                fixed:false
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-03</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-04</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-05</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-06</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-07</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-08</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-09</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-10</div></th>',
                htmlDom:true
            },{
                // 11
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-11</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-12</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-13</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-14</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-15</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-16</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-17</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-18</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-19</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-20</div></th>',
                htmlDom:true
            },{
                // 21
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-21</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-22</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-23</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-24</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-25</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-26</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-27</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-28</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-29</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-30</div></th>',
                htmlDom:true
            },{
                // 31
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-31</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">02-01</div></th>',
                htmlDom:true
            }],
        */
        fields : fields,
        tableDefaultContent: "<div>表格数据内容为空</div>"
    });
    tableFunc.district.completeTable(landlords,theDate);

    $("ul.cl-vnavigation li a:first").click(function(){
        alert('district.name:'+district.name+';district.phone:'+district.phoneNumber);
        fixedTable.empty();
        tableFunc.district.completeTable(landlords,theDate);
    });
    $("ul.cl-vnavigation li a:not(:first)").click(function(){
        phoneNumber=$(this).attr('phone');
        alert('landlord.phoneNumber:'+phoneNumber);
        landlord=null;
        for (var index = 0; index < landlords.length; index++) {
            if(phoneNumber==landlords[index].phoneNumber){
                landlord=landlords[index];
                break;
            }
        }
        if(null!=landlord){
            fixedTable.empty();
            tableFunc.landlord.completeTable(landlord,theDate);
        }
    });

    $.contextMenu({
        selector: ".cd-unlockrecord:not(.cd-booked)",
        items: {
            record: {
                name: "入住记录", callback: function(key, opt){
                    $('#md-record').niftyModal();
                    if("district"==tableType){
                        landlordPhoneNumber=$(this).parent("tr").attr("landlord");
                        phoneNumber=landlordPhoneNumber;
                    }else{
                        phoneNumber=landlord.phoneNumber;
                    }
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
            authorization : {
                name: "开锁信息", callback: function(key, opt){
                    $('#md-authorization').niftyModal();
                    if("district"==tableType){
                        landlordPhoneNumber=$(this).parent("tr").attr("landlord");
                        phoneNumber=landlordPhoneNumber;
                    }else{
                        phoneNumber=landlord.phoneNumber;
                    }
                    getCellParam($(this));
                    tableElement=$("#table-authorization");
                    datatableSet.function_authorization.drawtable(tableElement);
                }
            }
        }
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
            param.ownerPhoneNumber=phoneNumber;
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
            $.fn.dataTable.tables({api: true}).destroy();
            tableInstance = element.dataTable($.extend(true, {}, datatableSet.options.DEFAULT_OPTION, {
                ajax: function (data, callback, settings) {
                    param = datatableSet.function_record.getQueryParams(data);
                    tableWrapper.spinModal();
                    $.ajax({
                        type: "POST",
                        url: projectPath+'/record/getDailyUnlockRecordLockPage.do',
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
            param.ownerPhoneNumber=phoneNumber;
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
            $.fn.dataTable.tables({api: true}).destroy();
            tableInstance = element.dataTable($.extend(true, {}, datatableSet.options.DEFAULT_OPTION, {
                ajax: function (data, callback, settings) {
                    param = datatableSet.function_authorization.getQueryParams(data);
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
                    if (data.role) {
                        $(row).addClass("info");
                    }
                },
                "drawCallback": function (settings) {}
            })).api();
        }
    }
};