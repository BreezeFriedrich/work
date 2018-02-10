var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
// var userHierarchy;
// var subordinates;
var city;
var districts;
var district;
var landlords;
var landlord;
var locks;
var lock;
var dateArr=new Array;
var dateStrArr=new Array;
var today,theDate,theTime,newDate;
var authinfo;
var recordinfo;
var startTime;
var endTime;
var districtPhoneNumber;
var landlordPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var index;
var fixedTable;
var html;

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
}
function getLocks() {
    $.ajax({
        type:"POST",
        url:"user/getSubordinateHierarchyTillLock.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            city=data;
            // if (landlords.length===0){
            city=
                {
                    "phoneNumber": "17705155208",
                    "grade": 30,
                    "name": "南京市局",
                    "location": null,
                    "subordinateList": [
                        {"phoneNumber": "13998892002",
                            "grade": 20,
                            "name": "鼓楼区分局",
                            "location": "一个地址",
                            "subordinateList": [
                                {"phoneNumber": "18255683932","grade":10,"name":"汪凯","location":"地址",
                                    "subordinateList": [{
                                        "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                                        "lockName":"房间A1","lockCode":"LCN0011721000001","lockLocation":"南京市雨花台区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                    },{
                                        "gatewayName":"网关3","gatewayCode":"GWH0081702000002","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                                        "lockName":"房间A2","lockCode":"LCN0011721000002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                    },{
                                        "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                                        "lockName":"房间A3","lockCode":"LCN0011721000003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                    }]
                                },{"phoneNumber":"13998892002","grade":10,"name":"yangwenshang","location":"第2个业主地址",
                                    "subordinateList":[{
                                        "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                                        "lockName":"房间B1","lockCode":"LCN0011721000001","lockLocation":"南京市鼓楼区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                    },{
                                        "gatewayName":"网关3","gatewayCode":"GWH0081702000005","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                                        "lockName":"房间B2","lockCode":"LCN0011721000005","lockLocation":"南京市鼓楼区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                    },{
                                        "gatewayName":"网关3","gatewayCode":"GWH0081702000006","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,
                                        "lockName":"房间B3","lockCode":"LCN0011721000006","lockLocation":"南京市鼓楼区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                    }]
                                }
                            ]
                        }
                    ]
                };
            districts=city.subordinateList;
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
        html += '<li><a><i class="fa inco-ctiy"></i><span class="selected">'+city.name+'</span></a></li>';
        for(var j in districts){
            district=districts[j];
            html += '<li><a href="#"><i class="fa inco-map"></i><span>'+district.name+'</span></a><ul class="sub-menu">';
            landlords=district.subordinateList;
            for(var i in landlords){
                landlord=landlords[i];
                html += '<li><a href="#">'+landlord.name+'</a></i></li>';
            }
            html += '</ul></li>';
        }
        $('ul.cl-vnavigation').append(html);
    })()
}
function renderRow(districts,date) {
    (function () {
        var time1=new Date();
        var tr_num=0;
        for(var l in districts){
            district=districts[l];
            landlords=district.subordinateList;
            for(var k in landlords){
                landlord=landlords[k];
                locks=landlord.subordinateList;
                for(var j in locks){
                    lock=locks[j];
                    var TDs_row=fixedTable.fixedTableBody.find("tbody tr").eq(tr_num).find("td:not(:first)");
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
        }
        var time2=new Date();
        console.log("ajax num:"+locks.length+",time:"+(time2.getTime()-time1.getTime())/1000);
    })();
}
function drawTable(date) {
    // getDateArr(date);
    //表格标题-时间重设 function resetTableHeaderTxt
    var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
    var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");
    for(var i=0;i<31;i++){
        DIV_header[i].innerText=dateStrArr[i];
        TH_header.eq(i+1).attr("time",dateStrArr[i]);
    }
    //表格数据行-添加数据
    fixedTable.addRow(function (){
        html = '';
        for(var i in districts){
            district=districts[i];
            landlords=district.subordinateList;
            for(var j in landlords){
                landlord=landlords[j];
                locks=landlord.subordinateList;
                for(var k in locks){
                    lock=locks[k];
                    html += '<tr district="'+district.phoneNumber+'" landlord="'+landlord.phoneNumber+'" gatewayid="'+lock.gatewayCode+'" lockid="'+lock.lockCode+'">';
                    html +=     '<td><div class="table-hight1 table-width32 table-butstyle">'+lock.lockName+'</div></td>';
                    for(var i=0; i<dateArr.length; i++){
                        html += '<td><div class="cd table-hight1 table-width140">'+dateStrArr[i]+'</div></td>';
                    }
                    html += '</tr>';
                }
            }
        }
        return html;
    });
    (function () {
        var DIV=$(".fixed-table_fixed-left tbody tr td div");
        DIV.removeClass("table-width32");
        DIV.addClass("table-width105");
        var locksLength;
        var landlordSize;
        var landlordHeight;
        var landlord_html;
        var districtContainRoomSize;
        var districtHeight;
        var district_html;
        var TR_fixedlefttbody;
        for(var i in districts){
            district=districts[i];
            districtContainRoomSize=0;
            landlords=district.subordinateList;
            landlordSize=landlords.length;
            for(var j in landlords){
                landlord=landlords[j];
                locks=landlord.subordinateList;
                locksLength=locks.length;
                landlordHeight=44*locksLength;
                landlord_html='<td rowspan="'+locksLength+'"><div style="line-height: '+landlordHeight+'px" class="table-width105 table-butstyle">'+landlord.name+'</div></td>';
                // lock=locks[0];
                // TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[district="+district.phoneNumber+"][landlord="+landlord.phoneNumber+"][gatewayid="+lock.gatewayCode+"][lockid="+lock.lockCode+"]");
                // TR_fixedlefttbody.prepend(landlord_html);
                TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[district="+district.phoneNumber+"][landlord="+landlord.phoneNumber+"]");
                TR_fixedlefttbody.eq(0).prepend(landlord_html);
                districtContainRoomSize=districtContainRoomSize+locksLength;
            }
            districtHeight=44*districtContainRoomSize;
            district_html='<td rowspan="'+districtContainRoomSize+'"><div style="line-height: '+districtHeight+'px" class="table-width105 table-butstyle">'+district.name+'</div></td>';
            TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[district="+district.phoneNumber+"]");
            TR_fixedlefttbody.eq(0).prepend(district_html);
        }
    })();
    //表格标题栏时间控件label值.
    if($('.current-date label').length>1){
        $('.current-date label')[1].innerText = getDateStr(date);
    }
    renderRow(districts,date);
}

//获取某个房间某天的请求参数,element是tbody->tr->td,element==0是房间号cell.
function getCellParam(element) {
    districtPhoneNumber=element.parent("tr").attr("district");
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
    theDate=getZeroOfDate(new Date());
    getDateArr(theDate);
    var html='';
    html += '<div class="current-date">当前日期：<label id="show3" class="time3">日期</label></div>';
    html += '<div id="datetimepicker" class="input-group date datetime date-selection" data-min-view="2" data-date-format="yyyy-mm-dd">';
    html +=     '<input class="form-control" size="16" type="text" value="" readonly style="display:none">';
    html +=     '<span class="input-group-addon btn btn-primary calendar date-selection-span"><span class="glyphicon glyphicon-th date-selection-img"></span></span>';
    html += '</div>';

    fixedTable = new FixedTable({
        wrap : document.getElementsByClassName("container-table")[0],
        type : "row-col-fixed",
        extraClass: "",
        maxHeight : true,
        fields : [
            {
                width: "317px",
                field: '<th class="table-width32"><div class="table-header-hight58 table-cell table-width32 table-butstyle">'+html+'</div></th>',
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
            drawTable(theDate);
        }
    });
    drawTable(theDate);
    $.contextMenu({
        selector: ".cd-unlockrecord:not(.cd-booked)",
        items: {
            ticket: {name: "入住记录", callback: function(key, opt){
                $('#reply-ticket').niftyModal();
                var roomid=$(this).parent("tr").attr("id").split("-");
                specificGatewayCode=roomid[0];
                specificLockCode=roomid[1];
                var index=$(this).index();//index==0是房间cell.
                var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th:gt(0)");//去除第0个thead的th即房间号cell.
                theTime=TH_header.eq(index-1).attr("time");
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
        selector: ".cd-booked:not(.cd-unlockrecord)",
        items: {
            identity: {name: "身份证授权", callback: function(key, opt){
                $('#reply-identity').niftyModal();
                console.log($(this).parent("tr").attr("id"));
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
        selector: ".cd-unlockrecord.cd-booked",
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
                console.log($(this).parent("tr").attr("id"));
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
    $(".spk2").sparkline([4,6,7,7,4,3,2,1,4,4,5,6,5], { type: 'discrete', width: '80', lineColor: '#4A8CF7',thresholdValue: 4,thresholdColor: '#ff0000'});
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
        highlightLineColor: '#1e7ac6'
    });
    //Maps
    $('#world-map').vectorMap({
        map: 'world_mill_en',
        backgroundColor: 'transparent',
        regionStyle: {
            initial: {
                fill: '#38c3c1'
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

var CONSTANT = {
    DATA_TABLES : {
        DEFAULT_OPTION : {
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
    }
};
var userManage = {
    getQueryCondition: function (data) {
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

        param.ownerPhoneNumber=landlordPhoneNumber;
        param.date=theTime;
        param.gatewayCode=specificGatewayCode;
        param.lockCode=specificLockCode;
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.draw = data.draw;
        return param;
    }
};