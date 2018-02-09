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
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A2","lockCode":"LCN0011721000002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间A3","lockCode":"LCN0011721000003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        }]},
                        {"phoneNumber":"18352478654","grade":10,"name":"业主2","location":"第2个业主地址","subordinateList":[{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B1","lockCode":"LCN0011721000001","lockLocation":"南京市鼓楼区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                        },{
                            "gatewayName":"网关3","gatewayCode":"GWH0081702000005","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                            "lockName":"房间B2","lockCode":"LCN0011721000005","lockLocation":"南京市鼓楼区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
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
        var landlord;
        html = '';
        html += '<li><a><i class="fa inco-ctiy"></i><span class="selected">'+district.name+'</span></a></li>';
        for(var i in landlords){
            landlord=landlords[i];
            // html += '<li><a href="#"><i class="fa inco-map"></i><span>'+landlord.name+'</span></a></li>';
            html += '<li><a><i class="fa inco-map"></i><span>'+landlord.name+'</span></a></li>';
        }
        $('ul.cl-vnavigation').append(html);
    })()
}
function renderTableHead(date) {
    getDateArr(date);
    //表格标题-时间重设 function resetTableHeaderTxt
    var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");
    for(var i=0;i<31;i++){
        DIV_header[i].innerText=theadDateStrArr[i];
        TH_header.eq(i+1).attr("time",dateStrArr[i]);
    }
}
function renderTableBody() {
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
}
function renderFixedColumn(date) {//固定列.
        //表格标题栏时间控件label值.()
        if($('.current-date label').length>1){
            $('.current-date label')[1].innerText = getDateStr(date);
        }

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
}
function renderRow(landlords,date) {
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
                                    }
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
                                for(var i=0;i<recordinfoLength;i++){
                                    recordDaily=recordinfo[i];
                                    if(recordDaily.totalSize>0){
                                        TDs_row.eq(i).addClass("cd-unlockrecord");
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
}
function renderTable(date) {
    renderTableHead(date);
    renderTableBody();
    renderFixedColumn(date);
    renderRow(landlords,date);
}
//获取某个房间某天的请求参数,element是tbody->tr->td,element==0是房间号cell.
function getCellParam(element) {
    landlordPhoneNumber=element.parent("tr").attr("landlord");
    specificGatewayCode=element.parent("tr").attr("gatewayid");
    specificLockCode=element.parent("tr").attr("lockid");
    index=element.index();//index==0是房间cell.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");//第0个thead的th即房间号cell.
    theTime=TH_header.eq(index).attr("time");
}

$(document).ready(function(){
    $(".navbar-collapse ul:first li:eq(3)").addClass("active");
    getLocks();
    showNavSide();
    var html='';
    // html += '<th class="table-width190 table-butstyle">';
    // html +=     '<div class="table-header-hight58 table-cell table-width190 table-butstyle">';
    html +=         '<div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>';
    html +=         '<div id="datetimepicker" class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">';
    html +=             '<input class="form-control" size="16" type="text" value="" readonly style="display:none">';
    html +=             '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>';
    html +=         '</div>';
    // html +=     '</div>';
    // html += '</th>';
    fixedTable = new FixedTable({
        wrap : document.getElementsByClassName("container-table")[0],
        type : "row-col-fixed",
        extraClass: "",
        maxHeight : true,
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
        tableDefaultContent: "<div>我是一个默认的div</div>"
    });

    $('#datetimepicker').on('changeDate', function(ev){
        theDate=new Date(ev.date.valueOf());
        fixedTable.empty();
        renderTable(theDate);
    });
    renderTable(new Date());

    $("ul.cl-vnavigation li a:not(first)").click(function(){
        alert($(this));
    });

    App.init();
});