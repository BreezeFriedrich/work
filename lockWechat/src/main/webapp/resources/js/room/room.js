/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var devices=new Array();
var orderList=new Array();
var romList=new Array();
var curDate=new Date();

$(function () {
    $('nav a').removeClass('active').eq(1).addClass('active');

    $(".fixed-table-box").fixedTable();
    curDate=new Date(getDatefromSession());
    getOrderList();
    getRoomList();
    loadPage();
});

/**
 * 获取订单列表
 */
function getOrderList() {
    var ed=new Date();
    var times=getSTandET(curDate,new Date(ed.setDate(curDate.getDate()+14)) );
    var startTime=times[0];
    var endTime=times[1];
    // var endTime="201901012359";
    // var startTime=curDate.getDate();
    // var endTime=curDate.getDate()+14;
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getOrderList.action",
        async:false,//异步
        data:{
            "startTime":startTime,
            "endTime":endTime
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                orderList=obj.orderList;
            }else{
                alert("获取订单失败！");
            }
        },
        error:function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}

function getRoomList() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getRoomList.action",
        async:false,//异步
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                romList=obj.roomList;
            }else{
                alert("获取设备失败！");
            }
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}

/**
 * session中获取日期
 * @return {*}
 */
function getDatefromSession() {
    var date;
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getFromSession.action",
        async:false,//异步
        data:{
            "sessionName":"cDate"
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
             date=data;
            // alert("getDatefromSession: "+data);
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
    return  date;
}


/**
 * 加载页面
 */
function loadPage(){
    loadCal(curDate);
    loadRoomList();
    loadRoomStatus();
}

function setToSession(sessionN,sessionV) {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/setToSession.action",
        async:false,//异步
        data:{
            "sessionName":sessionN,
            "sessionValue":sessionV
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            // alert("setDateToSession:  "+data);
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}


/**
 * 生成房态日期格
 * @param date session内日期
 */
function loadCal(date) {
    var cal=document.getElementById("cal");
    // $("#cal td").remove();
    var childs = cal.childNodes;
    for(var s = childs.length - 1; s >= 0; s--) {
        cal.removeChild(childs[s]);
    }
    var td=document.createElement("td");
    td.setAttribute("class","fixed-table_header-0");
    td.setAttribute("data-fixed","true");

    var div=document.createElement("div");
    div.setAttribute("class","table-cell w-80");
    div.innerHTML="日历表";

    td.appendChild(div);
    cal.appendChild(td);
    var weekdays=new Array(7);
    weekdays[0]="周日";
    weekdays[1]="周一";
    weekdays[2]="周二";
    weekdays[3]="周三";
    weekdays[4]="周四";
    weekdays[5]="周五";
    weekdays[6]="周六";

    for(var i=0;i<14;i++){
        var nDate=new Date();
        nDate.setDate(date.getDate()+i);
        var month=nDate.getMonth()+1;
        var day=nDate.getDate();
        var weekday=nDate.getDay();
        if(month<10){
            month="0"+month;
        }
        if(day<10){
            day="0"+day;
        }
        var td1=document.createElement("td");
        td1.setAttribute("class","fixed-table_header-1");

        var div1=document.createElement("div");
        div1.setAttribute("class","table-cell w-50");
        div1.innerHTML=month+"-"+day+"<br/>"+weekdays[weekday];

        td1.appendChild(div1);
        cal.appendChild(td1);
    }
}


/**
 * 生成房间栏
 */
function loadRoomList() {
    var roomList=document.getElementById("roomList");
    var childs = roomList.childNodes;
    for(var s = childs.length - 1; s >= 0; s--) {

        roomList.removeChild(childs[s]);
    }
    // alert("deviceslength： "+devices.length);
    for(var i=0;i<romList.length;i++){

        var roomTypeId=romList[i].roomTypeId;
        var roomType=romList[i].roomType;
        var roomInfoList=romList[i].roomInfoList;
        for(var j=0;j<roomInfoList.length;j++){
            var tr=document.createElement("tr");
            var td=document.createElement("td");
            var div=document.createElement("div");
            div.setAttribute("class","table-cell w-80");
            div.setAttribute("roomTypeId",roomTypeId);
            div.setAttribute("roomId",roomInfoList[j].roomId);
            var span=document.createElement("span");
            span.setAttribute("class","hxlx");
            span.innerHTML=roomType;

            div.innerHTML=roomInfoList[j].roomName;
            div.appendChild(span);
            td.appendChild(div);
            tr.appendChild(td);
            roomList.appendChild(tr);
        }

        // var gatewayCode=devices[i].gatewayCode;
        // var lockLists=devices[i].lockLists;
        //
        // for(var j=0;j<lockLists.length;j++){
        //     var tr=document.createElement("tr");
        //     var td=document.createElement("td");
        //     var div=document.createElement("div");
        //     var lockCode=lockLists[j].lockCode;
        //     div.setAttribute("class","table-cell w-80");
        //     div.setAttribute("gatewayCode",gatewayCode);
        //     div.setAttribute("lockCode",lockCode);
        //     var span=document.createElement("span");
        //     span.setAttribute("class","hxlx");
        //     span.innerHTML=lockLists[j].lockName;
        //
        //     div.appendChild(span);
        //     td.appendChild(div);
        //     tr.appendChild(td);
        //     roomList.appendChild(tr);
        // }
    }

}


/**
 * 生成房态格
 */
function loadRoomStatus() {
    var stAndEt=getSTandET(curDate,new Date());
    var startTime=stAndEt[0];
    var endTime=stAndEt[1];
    var roomStatus=document.getElementById("roomStatus");

    var childs = roomStatus.childNodes;
    for(var s = childs.length - 1; s >= 0; s--) {

        roomStatus.removeChild(childs[s]);
    }
    for(var i=0;i<romList.length;i++){
        var roomTypeId=romList[i].roomTypeId;
        var roomType=romList[i].roomType;
        var roomInfoList=romList[i].roomInfoList;
        //每个房间生成一行
        for(var j=0;j<roomInfoList.length;j++){
            var roomOrder=new Array();
            var roomId=roomInfoList[j].roomId;
            var roomName=roomInfoList[j].roomName;
            for(var s=0;s<orderList.length;s++){
                if(orderList[s].roomId == roomId){
                    roomOrder.push(orderList[s]);
                }
            }
            var tr =document.createElement("tr");
            var td =document.createElement("td");
            var div=document.createElement("div");
            var a=document.createElement("a");

            div.setAttribute("class","table-cell w-81");
            a.appendChild(div);
            td.appendChild(a);
            tr.appendChild(td);
            //生成14个状态格
            for(var k=0;k<14;k++){
                //生成timetag
                var nDate=new Date();
                nDate.setDate(curDate.getDate()+k);
                var month=nDate.getMonth()+1;
                var day=nDate.getDate();
                var year=nDate.getFullYear();
                if(month<10){
                    month="0"+month;
                }
                if(day<10){
                    day="0"+day;
                }
                var timetag=""+year+month+day;

                var td1 =document.createElement("td"  );
                var div1=document.createElement("div" );
                var span=document.createElement("span");
                var a1=document.createElement("a");

                a1.setAttribute("class","external");
                a1.setAttribute("href","jsp/room/addAuth.jsp?roomId="+roomId+"&roomTypeId="+roomTypeId+"&roomName="+roomName+"&clickDate="+timetag+"12");
                // a1.onclick=setToSession("roomId",roomId);
                div1.setAttribute("class","table-cell w-50");

                for(var t=0;t<roomOrder.length;t++){
                    var st=roomOrder[t].startTime;
                    var et=roomOrder[t].endTime;
                    var tflag1=((parseInt(et) - parseInt(timetag+"1200") )>0);
                    var tflag2=( ( parseInt(st.substring(0,8))-parseInt(timetag) ) <= 0);
                    if(tflag1&&tflag2){
                        var orderNumber=roomOrder[t].orderNumber;
                        a1.setAttribute("href","jsp/room/aBook.jsp?orderNumber="+orderNumber);
                        if(roomOrder[t].checkInTime==null || roomOrder[t].checkInTime=="" ){
                            var newclass=div1.getAttribute("class")+" "+"cd-booked";
                            div1.setAttribute("class",newclass);
                            if(( parseInt(et.substring(0,8))-parseInt(timetag) ) !=0 ){
                                td1.style.borderRightColor="#9FDBBD";
                                td1.style.borderLeftColor="#9FDBBD";
                            }
                            if(( parseInt(st.substring(0,8))-parseInt(timetag) ) ==0 ){
                                span.innerHTML="已预订";
                            }
                            if(k==0){
                                span.innerHTML="已预订";
                            }
                        }else{
                            var newclass1=div1.getAttribute("class")+" "+"cd-select";
                            div1.setAttribute("class",newclass1);
                            if(( parseInt(et.substring(0,8))-parseInt(timetag) ) !=0 ){
                                td1.style.borderLeftColor="#65C596";
                                td1.style.borderRightColor="#65C596";
                            }
                            if(( parseInt(st.substring(0,8))-parseInt(timetag) ) ==0 ){
                                span.innerHTML="已入住";
                            }
                            if(k==0){
                                span.innerHTML="已入住";
                            }
                        }
                        // div1.onmousedown=setToSession("orderNumber",roomOrder[t].orderNumber);
                    }
                }
                div1.setAttribute("timetag",timetag);
                div1.setAttribute("roomId",roomId);
                div1.appendChild(span);
                a1.appendChild(div1);
                td1.appendChild(a1);
                tr.appendChild(td1);
            }
            roomStatus.appendChild(tr);
        }
    }

    // for(var i=0;i<devices.length;i++){
    //
    //     var gatewayCode=devices[i].gatewayCode;
    //     var lockLists=devices[i].lockLists;
    //     //每个锁生成一行
    //     for(var j=0;j<lockLists.length;j++){
    //         var lockCode=lockLists[j].lockCode;
    //         var lockName=lockLists[j].lockName;
    //         var userList=getIDAuth(gatewayCode,lockCode);
    //         var passwordList=getPwdAuth(gatewayCode,lockCode);
    //
    //         var tr =document.createElement("tr");
    //         var td =document.createElement("td");
    //         var div=document.createElement("div");
    //         var a=document.createElement("a");
    //
    //         div.setAttribute("class","table-cell w-81");
    //         a.appendChild(div);
    //         td.appendChild(a);
    //         tr.appendChild(td);
    //

    //         for(var k=0;k<14;k++){
    //             var checkInFlag=false;
    //             var bookFlag=false;
    //             //生成timetag
    //             var nDate=new Date();
    //             nDate.setDate(curDate.getDate()+k);
    //             var month=nDate.getMonth()+1;
    //             var day=nDate.getDate();
    //             var year=nDate.getFullYear();
    //             if(month<10){
    //                 month="0"+month;
    //             }
    //             if(day<10){
    //                 day="0"+day;
    //             }

    //             var timetag=""+year+month+day;
    //             //是否有入住记录 判断标记时按中午十二点为临界
    //             for(var r=0;r<recordList.length;r++){
    //                 var rlc=recordList[r].lockCode;
    //                 var rt=recordList[r].timetag;
    //
    //                 if(lockCode === rlc){
    //                     if(timetag===rt.substring(0,8)){
    //                         if( ( 12- parseInt(rt.substring(8,10)) )<= 0 ){
    //                             checkInFlag=true;
    //                         }
    //                     }
    //                 }
    //
    //
    //             }
    //
    //             //是否有预订信息 判断标记时按中午十二点为临界(有入住记录时，不判断)
    //             if(!checkInFlag){
    //                 for(var s=0;s<userList.length;s++){
    //                     var et=userList[s].endTime;
    //                     var st=userList[s].startTime;
    //                     var tflag1=((parseInt(et) - parseInt(timetag+"1200") )>0);
    //                     var tflag2=(( parseInt(st.substring(0,8))-parseInt(timetag) )<= 0);
    //                      if(tflag1 && tflag2){
    //                         bookFlag = true;
    //                     }
    //                 }
    //
    //                 if(!bookFlag){
    //                     for(var t=0;t<passwordList;t++){
    //                         var et1=passwordList[t].endTime;
    //                         var st1=passwordList[t].startTime;
    //                         var eflag1=((parseInt(et1) - parseInt(timetag+"1200") )>0);
    //                         var eflag2=(( parseInt(st1.substring(0,8))-parseInt(timetag) )<= 0);
    //                         if(eflag1 &&eflag2 ){
    //                             bookFlag=true;
    //                         }
    //                     }
    //                 }
    //             }
    //
    //
    //             var td1 =document.createElement("td"  );
    //             var div1=document.createElement("div" );
    //             var span=document.createElement("span");
    //             var a1=document.createElement("a");
    //             td1.setAttribute("onclick","setGaLToSession(\""+gatewayCode+"\",\""+lockCode+"\",\""+lockName+"\")");
    //
    //             if(checkInFlag){
    //                 div1.setAttribute("class","table-cell w-50 cd-select");
    //                 span.innerHTML="已入住";
    //             }else if(bookFlag){
    //                 div1.setAttribute("class","table-cell w-50 cd-booked");
    //                 span.innerHTML="已预订";
    //             }else{
    //                 a1.setAttribute("class","external");
    //                 a1.setAttribute("href","jsp/room/addAuth.jsp");
    //                 div1.setAttribute("class","table-cell w-50");
    //             }
    //             div1.setAttribute("timetag",timetag);
    //             div1.appendChild(span);
    //             a1.appendChild(div1);
    //             td1.appendChild(a1);
    //             tr.appendChild(td1);
    //
    //         }
    //         roomStatus.appendChild(tr);
    //     }
    // }

}


/**
 * 生成开始时间结束时间
 * @param cDate 开始时间为cDate的00点00分
 * @param lDate 结束时间为lDate的23点59分
 * @return {Array}
 */
function getSTandET(cDate,lDate) {
    var times=new Array(2);

    var year=cDate.getFullYear();
    var month=cDate.getMonth()+1;
    var day=cDate.getDate();

    var year1=lDate.getFullYear();
    var month1=lDate.getMonth()+1;
    var day1=lDate.getDate();
    if(month<10){
        month="0"+month;
    }
    if(month1<10){
        month1="0"+month1;
    }
    if(day<10){
        day="0"+day;
    }
    if(day1<10){
        day1="0"+day1;
    }
    var startTime=""+year+month+day+"0000";
    var endTime=""+year1+month1+day1+"2359";
    times[0]=startTime;
    times[1]=endTime;
    return times;
}

