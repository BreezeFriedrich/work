var city;
var districts;
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
var districtPhoneNumber;
var landlordPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var roomTypeId;
var roomId;
var index;
var TH_header;
var fixedTable;
var html;
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
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            city=data;
            // if (landlords.length===0){
            /*
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
                                    "subordinateList":[
                                        {
                                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,"lockName":"房间A1","lockCode":"LCN0011721000001","lockLocation":"南京市雨花台区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                        },{
                                            "gatewayName":"网关3","gatewayCode":"GWH0081702000002","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,"lockName":"房间A2","lockCode":"LCN0011721000002","lockLocation":"南京市雨花台区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                        },{
                                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,"lockName":"房间A3","lockCode":"LCN0011721000003","lockLocation":"南京市雨花台区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
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
                                },{"phoneNumber":"13998892002","grade":10,"name":"yangwenshang","location":"第2个业主地址",
                                    "subordinateList":[
                                        {
                                            "gatewayName":"网关3","gatewayCode":"GWH0081702000003","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,"lockName":"房间B1","lockCode":"LCN0011721000001","lockLocation":"南京市鼓楼区西善桥街道22号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                        },{
                                            "gatewayName":"网关3","gatewayCode":"GWH0081702000005","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,"lockName":"房间B2","lockCode":"LCN0011721000005","lockLocation":"南京市鼓楼区春江路129号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
                                        },{
                                            "gatewayName":"网关3","gatewayCode":"GWH0081702000006","gatewayLocation":"网关地址","gatewayComment":"网关备注","gatewayStatus":4,"lockName":"房间B3","lockCode":"LCN0011721000006","lockLocation":"南京市鼓楼区江泉路65号","lockComment":"一个门锁","lockStatus":1,"lockPower":3
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
                                }
                            ]
                        }
                    ]
                };
            */
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
        html += '<li><a href="javascript:void(0);"><i class="fa inco-ctiy"></i><span class="selected">'+city.name+'</span></a></li>';
        for(var j in districts){
            district=districts[j];
            html += '<li><a phone="'+district.phoneNumber+'" href="javascript:void(0);"><i class="fa inco-map"></i><span>'+district.name+'</span></a><ul class="sub-menu">';
            landlords=district.subordinateList;
            for(var i in landlords){
                landlord=landlords[i];
                html += '<li><a phone="'+landlord.phoneNumber+'" href="javascript:void(0);">'+landlord.name+'</a></i></li>';
            }
            html += '</ul></li>';
        }
        $('ul.cl-vnavigation').append(html);
    })()
}

var tableFunc={
    common:{
        drawTableHead:function drawTableHead(date) {
            getDateArr(date);
            if("city"==tableType){
                // $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").removeClass("table-width200").removeClass("table-width300");
                // $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").addClass("table-width300");
                $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").removeClass("table-width200").removeClass("table-width300").addClass("table-width400");
            }else if("district"==tableType) {
                // $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").removeClass("table-width200").removeClass("table-width300");
                // $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").addClass("table-width200");
                $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").removeClass("table-width400").removeClass("table-width200").addClass("table-width300");
            }else {
                $(".fixed-table-box").children(".fixed-table_header-wraper").find("th:first div.table-cell").removeClass("table-width400").removeClass("table-width300").addClass("table-width200");
            }

            //表格标题-时间重设 function resetTableHeaderTxt
            var DIV_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th div:gt(2)");//表格标题栏第一天元素序号为3.
            var TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");
            for(var i=0;i<31;i++){
                DIV_header[i].innerText=theadDateStrArr[i];
                TH_header.eq(i+1).attr("time",dateStrArr[i]);
            }
        }
    },
    city:{
        fillTable:function fillTable() {
            //表格数据行-添加数据
            fixedTable.addRow(function (){
                html = '';
                for(var m in districts){
                    district=districts[m];
                    landlords=district.subordinateList;
                    for(var l in landlords){
                        landlord = landlords[l];
                        roomTypeCRs = landlord.roomTypeContainRoomList;
                        for (var k in roomTypeCRs) {
                            roomTypeCR = roomTypeCRs[k];
                            rooms = roomTypeCR.roomInfoList;
                            for (var j in rooms) {
                                room = rooms[j];
                                html += '<tr district="'+district.phoneNumber+'" landlord="'+landlord.phoneNumber+'" roomtypeid="' + roomTypeCR.roomTypeId + '" roomid="' + room.roomId + '" gatewayid="' + room.gatewayCode + '" lockid="' + room.lockCode + '">';
                                html += '<td><div class="table-hight1 table-width400 table-butstyle">' + room.roomName + '</div></td>';
                                for (var i = 0; i < dateArr.length; i++) {
                                    html += '<td><div class="table-hight1 table-width140"></div></td>';
                                }
                                html += '</tr>';
                            }
                        }
                    }
                }
                return html;
            });
        },
        drawFixedColumn:function drawFixedColumn(date) {
            //表格标题栏时间控件label值.
            if($('.current-date label').length>1){
                $('.current-date label')[1].innerText = getDateStr(date);
            }

            $(".fixed-table_fixed-left th div.table-cell").removeClass("table-width300").removeClass("table-width200").addClass("table-width400");

            var DIV;
            DIV=$(".fixed-table_fixed-left tbody tr td div");
            DIV.removeClass("table-width400");
            DIV.addClass("table-width100");
            var roomtype_size;
            var roomtype_height;
            var roomtype_html;
            var landlord_rowspan;
            var landlord_height;
            var landlord_html;
            var district_rowspan;
            var district_height;
            var district_html;
            var TR_fixedlefttbody;
            for(var k in districts){
                district=districts[k];
                landlords=district.subordinateList;
                district_rowspan=0;
                for(var j in landlords){
                    landlord=landlords[j];
                    roomTypeCRs=landlord.roomTypeContainRoomList;
                    landlord_rowspan=0;
                    for(var i in roomTypeCRs) {
                        roomTypeCR = roomTypeCRs[i];
                        roomtype_size = roomTypeCR.roomInfoList.length;
                        roomtype_height=44*roomtype_size;
                        roomtype_html='<td rowspan="'+roomtype_size+'"><div style="line-height: '+roomtype_height+'px" class="table-width100 table-butstyle">'+roomTypeCR.roomType+'</div></td>';
                        TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[district="+district.phoneNumber+"][landlord="+landlord.phoneNumber+"][roomtypeid="+roomTypeCR.roomTypeId+"]");
                        TR_fixedlefttbody.eq(0).prepend(roomtype_html);
                        landlord_rowspan=landlord_rowspan+roomtype_size;
                        district_rowspan=district_rowspan+roomtype_size;
                    }
                    landlord_height=44*landlord_rowspan;
                    landlord_html = '<td rowspan="' + landlord_rowspan + '"><div style="line-height:' + landlord_height + 'px" class="table-width100 table-butstyle">'+landlord.name+'</div></td>';
                    TR_fixedlefttbody = $(".fixed-table_fixed-left tbody tr[district="+district.phoneNumber+"][landlord="+landlord.phoneNumber+"]");
                    TR_fixedlefttbody.eq(0).prepend(landlord_html);
                }
                district_height=44*district_rowspan;
                district_html='<td rowspan="'+district_rowspan+'"><div style="line-height: '+district_height+'px" class="table-width100 table-butstyle">'+district.name+'</div></td>';
                TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[district="+district.phoneNumber+"]");
                TR_fixedlefttbody.eq(0).prepend(district_html);
            }
        },
        renderRow:function renderRow(districts, date) {
            (function () {
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
                                url:"unlock/getUnlockAuthorizationDailyArr.do",
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
                                url:"record/getLockUnlockRecordDaily.do",
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
            })();
        },
        drawTable:function drawTable(districts, date) {
            tableType="city";
            tableFunc.common.drawTableHead(date);
            tableFunc.city.fillTable();
            tableFunc.city.drawFixedColumn(date);
            tableFunc.city.renderRow(districts,date);
        },
        redrawTableOnDateChange:
            function(event){
                theDate=new Date(event.date.valueOf());
                fixedTable.empty();
                tableFunc.city.drawTable(event.data.districts,theDate);
            },
        completeTable:function (districts,date) {
            tableFunc.city.drawTable(districts,date);
            //元素$(".fixed-table_fixed-left").find("div#datetimepicker")左边固定栏是在fixedTable.addRow()之后产生的.所以事件要放在fillTable()之后.
            $(".fixed-table_fixed-left").find("div#datetimepicker").off('changeDate');
            $(".fixed-table_fixed-left").find("div#datetimepicker").on('changeDate',{districts:districts},tableFunc.city.redrawTableOnDateChange);
        }
    },
    district:{
        fillTable:function fillTable() {
            //表格数据行-添加数据
            fixedTable.addRow(function (){
                html = '';
                for(var l in landlords) {
                    landlord = landlords[l];
                    roomTypeCRs = landlord.roomTypeContainRoomList;
                    for (var k in roomTypeCRs) {
                        roomTypeCR = roomTypeCRs[k];
                        rooms = roomTypeCR.roomInfoList;
                        for (var j in rooms) {
                            room = rooms[j];
                            html += '<tr landlord="'+landlord.phoneNumber+'" roomtypeid="' + roomTypeCR.roomTypeId + '" roomid="' + room.roomId + '" gatewayid="' + room.gatewayCode + '" lockid="' + room.lockCode + '">';
                            html += '<td><div class="table-hight1 table-cell table-width300 table-butstyle">' + room.roomName + '</div></td>';
                            for (var i = 0; i < dateArr.length; i++) {
                                html += '<td><div class="table-hight1 table-width140"></div></td>';
                            }
                            html += '</tr>';
                        }
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
            $(".fixed-table_fixed-left th div.table-cell").removeClass("table-width400").removeClass("table-width200").addClass("table-width300");
            // $('.fixed-table-box').children('.fixed-table_header-wraper').find('#datetimepicker').remove();//headdatetimepicker.删除这个div会导致DIV_header[]32->31,drawTableHead会出错.

            var DIV;
            DIV=$(".fixed-table_fixed-left tbody tr td div");
            DIV.removeClass("table-width300");
            DIV.addClass("table-width100");
            var roomtype_size;
            var roomtype_height;
            var roomtype_html;
            var landlord_rowspan;
            var landlord_height;
            var landlord_html;
            var TR_fixedlefttbody;
            for(var j in landlords){
                landlord=landlords[j];
                roomTypeCRs=landlord.roomTypeContainRoomList;
                landlord_rowspan=0;
                for(var i in roomTypeCRs) {
                    roomTypeCR = roomTypeCRs[i];
                    roomtype_size = roomTypeCR.roomInfoList.length;
                    roomtype_height=44*roomtype_size;
                    roomtype_html='<td rowspan="'+roomtype_size+'"><div style="line-height: '+roomtype_height+'px" class="table-width100 table-butstyle">'+roomTypeCR.roomType+'</div></td>';
                    TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[landlord="+landlord.phoneNumber+"][roomtypeid="+roomTypeCR.roomTypeId+"]");
                    TR_fixedlefttbody.eq(0).prepend(roomtype_html);
                    landlord_rowspan=landlord_rowspan+roomtype_size;
                }
                landlord_height=44*landlord_rowspan;
                landlord_html = '<td rowspan="' + landlord_rowspan + '"><div style="line-height: ' + landlord_height + 'px" class="table-width100 table-butstyle">' + landlord.name + '</div></td>';
                TR_fixedlefttbody = $(".fixed-table_fixed-left tbody tr[landlord="+landlord.phoneNumber+"]");
                TR_fixedlefttbody.eq(0).prepend(landlord_html);
            }
        },
        renderRow:function renderRow(landlords,date) {
            (function () {
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
                            url:"unlock/getUnlockAuthorizationDailyArr.do",
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
                            url:"record/getLockUnlockRecordDaily.do",
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
                roomTypeCRs=landlord.roomTypeContainRoomList;
                for(var k in roomTypeCRs){
                    roomTypeCR=roomTypeCRs[k];
                    rooms=roomTypeCR.roomInfoList;
                    for(var j in rooms){
                        room=rooms[j];
                        html += '<tr roomtypeid="'+roomTypeCR.roomTypeId+'" roomid="'+room.roomId+'" gatewayid="'+room.gatewayCode+'" lockid="'+room.lockCode+'">';
                        html += '<td><div class="table-hight1 table-cell table-width200 table-butstyle">'+room.roomName+'</div></td>';
                        for (var i=0; i<dateArr.length; i++){
                            html += '<td><div class="table-hight1 table-width140"></div></td>';
                        }
                        html += '</tr>';
                    }
                }
                return html;
            });
        },
        drawFixedColumn:function drawFixedColumn(date) {
            //表格标题栏时间控件label值.
            if($('.current-date label').length>1){
                $('.current-date label')[1].innerText = getDateStr(date);
            }
            $(".fixed-table_fixed-left th div.table-cell").removeClass("table-width400").removeClass("table-width300").addClass("table-width200");

            var DIV;
            DIV=$(".fixed-table_fixed-left tbody tr td div");
            DIV.removeClass("table-width200");
            DIV.addClass("table-width100");
            var roomtype_size;
            var roomtype_height;
            var roomtype_html;
            var TR_fixedlefttbody;
            roomTypeCRs=landlord.roomTypeContainRoomList;
            for(var i in roomTypeCRs) {
                roomTypeCR = roomTypeCRs[i];
                roomtype_size = roomTypeCR.roomInfoList.length;
                roomtype_height=44*roomtype_size;
                roomtype_html='<td rowspan="'+roomtype_size+'"><div style="line-height: '+roomtype_height+'px" class="table-width100 table-butstyle">'+roomTypeCR.roomType+'</div></td>';
                TR_fixedlefttbody=$(".fixed-table_fixed-left tbody tr[roomtypeid="+roomTypeCR.roomTypeId+"]");
                TR_fixedlefttbody.eq(0).prepend(roomtype_html);
            }
        },
        renderRow:function renderRow(landlord,date) {
            (function () {
                locks=landlord.subordinateList;
                for(var j in locks){
                    lock=locks[j];
                    var TDs_row=fixedTable.fixedTableBody.find("tbody tr").eq(j).find("td:not(:first)");//表格每一行row的第一个td是房间信息所以舍弃.
                    //auth获取开锁授权信息
                    $.ajax({
                        type:"POST",
                        url:"unlock/getUnlockAuthorizationDailyArr.do",
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
                        url:"record/getLockUnlockRecordDaily.do",
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
    // districtPhoneNumber=element.parent("tr").attr("district");
    // landlordPhoneNumber=element.parent("tr").attr("landlord");
    specificGatewayCode=element.parent("tr").attr("gatewayid");
    specificLockCode=element.parent("tr").attr("lockid");
    roomTypeId=element.parent("tr").attr("roomtypeid");
    roomId=element.parent("tr").attr("roomid");
    index=element.index();//index==0是房间cell.
    TH_header=$(".fixed-table-box").children(".fixed-table_header-wraper").find("th");//第0个thead的th即房间号cell.
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
        width: "400px",
        field: '<th><div class="table-header-hight58 table-cell table-width400 table-butstyle">'+html+'</div></th>',
        htmlDom:true,
        fixed:true,
        fixedDirection:"left"
    };
    for(var i=1;i<32;i++){
        fields[i]={
            width: "140px",
            field: '<th><div class="rq table-header-hight58 table-cell table-width140 table-butstyle"></div></th>',
            htmlDom:true
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
                width: "317px",
                field: '<th class="table-width300"><div class="table-header-hight58 table-cell table-width300 table-butstyle">'+html+'</div></th>',
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
        */
        fields : fields,
        tableDefaultContent: "<div>表格数据内容为空</div>"
    });
    tableFunc.city.completeTable(districts,theDate);

    $("ul.cl-vnavigation li:first a").click(function(){
        // alert('city.name:'+city.name+';city.phone:'+city.phoneNumber);
        fixedTable.empty();
        tableFunc.city.completeTable(districts,theDate);
    });
    $("ul.cl-vnavigation").children('li:not(:first)').find('a').dblclick(function(){
        phoneNumber=$(this).attr('phone');
        // alert('district.phoneNumber:'+phoneNumber);
        district=null;
        for (var index = 0; index < districts.length; index++) {
            if(phoneNumber==districts[index].phoneNumber){
                district=districts[index];
                break;
            }
        }
        if(null!=district){
            fixedTable.empty();
            tableFunc.district.completeTable(landlords,theDate);
        }
    });
    /*
    $("ul.cl-vnavigation").children('li:not(:first)').find('a').mousedown(function(event){
        if (event.which == 3) {
            // alert('我单击了右键');
            phoneNumber=$(this).attr('phone');
            alert('district.phoneNumber:'+phoneNumber);
            district=null;
            for (var index = 0; index < districts.length; index++) {
                if(phoneNumber==districts[index].phoneNumber){
                    district=districts[index];
                    break;
                }
            }
            if(null!=district){
                fixedTable.empty();
                tableFunc.district.completeTable(landlords,theDate);
            }
        }
    });
    */
    $("ul.cl-vnavigation").children('li:not(:first)').children('ul').find('li a').click(function(){
        phoneNumber=$(this).attr('phone');
        // alert('landlord.phoneNumber:'+phoneNumber);
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
            stateSave : false,
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
                        url: 'record/getDailyUnlockRecordLockPage.do',
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
                        url: 'unlock/getDailyUnlockAuthorizationRecord.do',
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
                            // if($.isEmptyObject(returnData)){
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