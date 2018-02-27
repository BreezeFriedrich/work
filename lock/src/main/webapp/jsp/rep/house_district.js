var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var userHierarchy;
var subordinates;
var landlords;
var locks;
var lock;
var dateArr=new Array;
var dateStrArr=new Array;
var today,theDate,theTime,newDate;
var authinfo;
var recordinfo;
var startTime;
var endTime;
var specificGatewayCode="GWH0081702000003";
var specificLockCode="LCN0011721000001";
var index;
var fixedTable;

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
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            userHierarchy=data;
            landlords=data.subordinateList;
            // if (landlords.length===0){
                /*
                userHierarchy={
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
                userHierarchy={
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
                landlords=userHierarchy.subordinateList;
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
        var html_nav_left = '';
        html_nav_left += '<li><a><i class="fa inco-ctiy"></i><span class="selected">'+userHierarchy.name+'</span></a></li>';
        for(var i in landlords){
            landlord=landlords[i];
            html_nav_left += '<li><a href="#"><i class="fa inco-map"></i><span>'+landlord.name+'</span></a></li>';
        }
        $('ul.cl-vnavigation').append(html_nav_left);
    })()
}
function renderRow(locks,date) {
    (function () {
        var time1=new Date();
        for(var j in locks){
            // console.log('j:'+j);
            lock=locks[j];
            var TDs_row=fixedTable.fixedTableBody.find("tbody tr").eq(j).find("td:not(:first)");//表格每一行row的第一个td是房间信息所以舍弃.
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
                    if(data.success){
                        if(data.biz.code===0){
                            authinfo=data.biz.data;
                            var authinfodailyArr=authinfo.authinfoDaily;
                            var authinfodaily;
                            var authinfodailyArrLength=authinfodailyArr.length;
                            for(var i=0;i<authinfodailyArrLength;i++){
                                authinfodaily=authinfodailyArr[i];
                                if(authinfodaily.idIndexes.length+authinfodaily.pwdIndexes.length>0){
                                    // fixedTable.fixedTableBody.find("tbody tr td").eq(i+16).addClass("cd-booked");
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
            //record获取入住记录
            $.ajax({
                type:"POST",
                url:projectPath+"/record/getLockUnlockRecordDaily.do",
                async:false,
                data:{"lockCode":lock.lockCode,"theDate":getDateStr(date)},
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
                                    // fixedTable.fixedTableBody.find("tbody tr td").eq(i+1).addClass("cd-select");
                                    TDs_row.eq(i).addClass("cd-select");
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
        var time2=new Date();
        console.log("ajax num:"+locks.length+",time:"+(time2.getTime()-time1.getTime())/1000);
    })();
}
function renderTable(date) {
    // getDateArr(date);
    //表格标题-时间重设 function resetTableHeaderTxt
    var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");
    for(var i=0;i<31;i++){
        DIV_header[i].innerText=dateStrArr[i];
        TH_header.eq(i+1).attr("time",dateStrArr[i]);
    }
    //表格数据行-添加数据
    /*
    fixedTable.addRow(function (){
        var html = '';
        (function () {
            for(var j in subordinates){
                lock=subordinates[j];//$(this).parent("tr").attr("roomid")
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
    */
    fixedTable.addRow(function (){
        landlords=userHierarchy.subordinateList;
        var landlordNum=landlords.length;
        var landlord;
        var lock;
        var html = '';
        for(var k in landlords){
            console.log('k : '+k);
            landlord=landlords[k];
            locks=landlord.subordinateList;

            lock=locks[0];
            html += '<tr roomid="'+lock.gatewayCode+'-'+lock.lockCode+'">';
            html +=     '<td class="table-width190"><div class="table-hight1 table-cell table-width190 table-butstyle">'+landlord.name+'</div></td>';
            html +=     '<td class="table-width190"><div class="table-hight1 table-cell table-width190 table-butstyle">'+lock.lockName+'</div></td>';
            for(var l=0; l<dateArr.length; l++){
                html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">'+dateStrArr[l]+'</div></td>';
            }
            html += '</tr>';

            for(var j=1,length=locks.length;j<length;j++){
                lock=locks[j];
                html += '<tr roomid="'+lock.gatewayCode+'-'+lock.lockCode+'">';
                html +=     '<td class="table-width190"><div class="table-hight1 table-cell table-width190 table-butstyle">'+lock.lockName+'</div></td>';
                // html +=     '<td ><div class="table-hight1 table-width105 table-butstyle">'+lock.lockName+'</div></td>';
                for(var i=0; i<dateArr.length; i++){
                    html += '<td  class="table-width140"><div class="cd table-hight1 table-width140">'+dateStrArr[i]+'</div></td>';
                }
                html += '</tr>';
            }
        }
        return html;
    });
    //表格标题栏时间控件label值.
    if($('.current-date label').length>1){
        $('.current-date label')[1].innerText = getDateStr(date);
    }
    // renderRow(subordinates,date);
}

//获取某个房间某天的请求参数,element是tbody->tr->td,element==0是房间号cell.
function getCellParam(element) {
    var roomid=element.parent("tr").attr("roomid").split("-");
    specificGatewayCode=roomid[0];
    specificLockCode=roomid[1];
    index=element.index();//index==0是房间cell.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");//第0个thead的th即房间号cell.
    theTime=TH_header.eq(index).attr("time");
}

$(document).ready(function(){
    $(".navbar-collapse ul:first li:eq(3)").addClass("active");
    getLocks();
    showNavSide();
    theDate=getZeroOfDate(new Date());
    getDateArr(theDate);
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

    // var html='';
    // html += '<th colspan="2">';
    // html +=     '<div class="table-time table-header-hight58 table-butstyle">';
    // html +=         '<div class="current-date">当前日期：<label id="show3" class="time3">'+today+'</label></div>';
    // html +=         '<div class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">';
    // html +=             '<input class="form-control" size="16" type="text" value="" readonly style="display:none">';
    // html +=             '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>';
    // html +=         '</div>';
    // html +=     '</div>';
    // html += '</th>';

    fixedTable = new FixedTable({
        wrap : document.getElementsByClassName("fixed-table-box")[0],
        type : "row-col-fixed",
        extraClass: ["table-width140","table-time","table-header-hight58","table-butstyle","current-date","input-group","date","datetime","date-selection"],
        maxHeight : true,
        fields : [
            {
                width: "206px",
//                field: '<th   class="table-width2" ><div class="table-time table-cell table-header-hight58 table-width2 table-butstyle">当前日期：'+today+'</div></th>',
//                 field: html,colspan="2" class="table-width22"
                field: '<th class="table-width190 table-butstyle"><div class="table-header-hight58 table-cell table-width190 table-butstyle">'+html+'</div></th>',
                htmlDom:true,
                fixed:true,
                fixedDirection:"left"
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-02</div></th>',
                htmlDom:true,
                fixed:false
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-03</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-04</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-05</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-06</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-07</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-08</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-09</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-10</div></th>',
                htmlDom:true
            },{
                // 11
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-11</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-12</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-13</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-14</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-15</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-16</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-17</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-18</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-19</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-20</div></th>',
                htmlDom:true
            },{
                // 21
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-21</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-22</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-23</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-24</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-25</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-26</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-27</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-28</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-29</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-30</div></th>',
                htmlDom:true
            },{
                // 31
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">01-31</div></th>',
                htmlDom:true
            },{
                width: "140px",
                field: '<th   class="table-width140 table-butstyle" data-fixed><div class="rq table-header-hight58 table-cell table-width140 table-butstyle">02-01</div></th>',
                htmlDom:true
            }],
        tableDefaultContent: "<div>我是一个默认的div</div>"
    });


    $('#datetimepicker').on('changeDate', function(ev){
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
    $.contextMenu({
        selector: ".cd-select:not(.cd-booked)",
        items: {
            ticket: {name: "入住记录", callback: function(key, opt){
                $('#reply-ticket').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
                var roomid=$(this).parent("tr").attr("roomid").split("-");
                specificGatewayCode=roomid[0];
                specificLockCode=roomid[1];
                var index=$(this).index();//index==0是房间cell.
                console.log($(this).parent("tr").children("td").index($(this).parent("td")));
                console.log("index:"+index);
                var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th:gt(0)");//去除第0个thead的th即房间号cell.
                theTime=TH_header.eq(index-1).attr("time");
                console.log("theTime:"+theTime);
                var $table = $("#table-unlockrecord");
                var _table = $table.dataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
                    ajax: function (data, callback, settings) {
                        //封装请求参数
                        param = userManage.getQueryCondition(data);
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
                                if(null===returnData.data){
                                    returnData.data={};
                                }
                                callback(returnData);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("查询失败");
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
                            render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS//alt效果
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
                })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
                _table.draw();
                // $("#btn_search").click(function () {
                //     _table.draw();
                // });
            }}
        }
    });
    $.contextMenu({
        selector: ".cd-booked:not(.cd-select)",
        items: {
            identity: {name: "身份证授权", callback: function(key, opt){
                $('#reply-identity').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }},
            password: {name: "密码授权", callback: function(key, opt){
                $('#reply-password').niftyModal();
            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){
                $('#reply-unlocking').niftyModal();
            }}
        }
    });
    $.contextMenu({
        selector: ".cd-select.cd-booked",
        items: {
            ticket: {name: "入住记录", callback: function(key, opt){
                $('#reply-ticket').niftyModal();
                getCellParam($(this));
                var $table = $("#table-unlockrecord");
                var _table = $table.dataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
                    ajax: function (data, callback, settings) {
                        //封装请求参数
                        param = userManage.getQueryCondition(data);
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
                                if(null===returnData.data){
                                    returnData.data={};
                                }
                                callback(returnData);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("查询失败");
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
                            render: CONSTANT.DATA_TABLES.RENDER.ELLIPSIS//alt效果
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
                })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
                _table.draw();
            }},
            identity: {name: "身份证授权", callback: function(key, opt){
                $('#reply-identity').niftyModal();
                console.log($(this).parent("tr").attr("roomid"));
            }},
            password: {name: "密码授权", callback: function(key, opt){
                $('#reply-password').niftyModal();
            }},
            unlocking: {name: "开锁信息", callback: function(key, opt){
                $('#reply-unlocking').niftyModal();
            }}
        }
    });

    //initialize the javascript
    App.init();
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