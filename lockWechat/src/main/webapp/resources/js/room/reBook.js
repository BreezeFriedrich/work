/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var orderNumber;
var roomId;
var roomTypeId;
var roomName;
var roomType;
var startTime;
var endTime;
var password;
var checkInTime;
var cardInfoList;

var roomList=new Array();
var roomNameList=new Array();
var roomIDList=new Array();

$(function () {
    orderNumber=getQueryString("orderNumber");
    getOrderContent();
    getRooms();
    loadpage();
});

function loadpage() {
    document.getElementById("roomtag").setAttribute("placeholder",roomName+"("+roomType+")");
    //初始化房间选择列表
    for(var i=0;i<roomList.length;i++){
        for(var j=0;j<roomList[i].roomInfoList.length;j++){
            var rn=roomList[i].roomInfoList[j].roomName+"("+roomList[i].roomType+")";
            var ri=roomList[i].roomInfoList[j].roomId;
            // $.alert("rn[0]: "+rn);
            roomNameList.push(rn);
            roomIDList.push(ri);
        }
    }
    $("#roomtag").picker({
        toolbarTemplate:
            '<header class="bar bar-nav">\
              <button class="button button-link pull-left"></button>\
              <button class="button button-link pull-right close-picker">确定</button>\
              <h1 class="title">请选择房间</h1>\
             </header>',
        cols: [
            {
                textAlign: 'center',
                values: roomNameList
            }
        ]
    });
    document.getElementById("roomtag").addEventListener("blur",function () {
        var room=document.getElementById("roomtag").value;
        roomName=room.split("(")[0];
        // $.alert("roomID old: "+roomId);
        for(var i=0;i<roomNameList.length;i++){
            // $.alert("room: "+room);
            // $.alert("roomNameList[i]: "+roomNameList[i]);

            if(room==roomNameList[i]){
                roomId=roomIDList[i];
                // $.alert("roomID new: "+roomId);
            }
        }
    });


    //初始化时间选择
    $("#datetime-picker").datetimePicker({
        value: [startTime.substring(0,4), startTime.substring(4,6), startTime.substring(6,8), startTime.substring(8,10), startTime.substring(10,12)]
    });
    $("#datetime-picker-lk").datetimePicker({
        value: [endTime.substring(0,4), endTime.substring(4,6), endTime.substring(6,8), endTime.substring(8,10), endTime.substring(10,12)]
    });

    for(var j=0;j<cardInfoList.length;j++){
        var div=document.createElement("div");
        var div1=document.createElement("div");
        var div11=document.createElement("div");
        var div12=document.createElement("div");
        var div13=document.createElement("div");
        var img=document.createElement("img");

        div.setAttribute("class","item-content");
        div1.setAttribute("class","item-inner");
        div11.setAttribute("class","item-title");
        div12.setAttribute("class","item-after");
        div13.setAttribute("class","item-media");
        div12.setAttribute("name","auid");
        div11.setAttribute("name","auname");
        img.setAttribute("class","ry-j-1");
        img.setAttribute("src","resources/images/ry-j-1.png");

        div11.innerHTML=cardInfoList[j].name;
        div12.innerHTML=cardInfoList[j].cardNumber;

        div13.appendChild(img);
        div1.appendChild(div11);
        div1.appendChild(div12);
        div1.appendChild(div13);
        div.appendChild(div1);
        div13.addEventListener("click",function () {
            var fdiv=div13.parentNode.parentNode;
            var li=document.getElementById("addList");
            li.removeChild(fdiv);
        });

        document.getElementById("addList").appendChild(div);
    }

    document.getElementById("password").setAttribute("value",password);
}

function addID() {
    var name=document.getElementById("idname").value;
    var id=document.getElementById("idcard").value;

    if(name==undefined || name=="" || name== null){}
    if(id==undefined || id=="" || id== null){

    }else{
        var li=document.getElementById("addList");
        var div=document.createElement("div");
        div.setAttribute("class","item-content");
        var div1=document.createElement("div");
        div1.setAttribute("class","item-inner");
        div.appendChild(div1);
        li.appendChild(div);

        var div11=document.createElement("div");
        div11.setAttribute("class","item-title");
        div11.setAttribute("name","auname");

        div11.innerHTML=name;
        var div12=document.createElement("div");
        div12.setAttribute("class","item-after");
        div12.setAttribute("name","auid");

        div12.innerHTML=id;
        var div13=document.createElement("div");
        div13.setAttribute("class","item-media");
        var img=document.createElement("img");
        img.setAttribute("class","ry-j-1");
        img.setAttribute("src","resources/images/ry-j-1.png");
        div13.appendChild(img);

        div13.addEventListener("click",function () {
            var fdiv=div13.parentNode.parentNode;
            var li=document.getElementById("addList");
            li.removeChild(fdiv);
        });

        div1.appendChild(div11);
        div1.appendChild(div12);
        div1.appendChild(div13);
    }
}
function getRooms() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getRoomList.action",
        async:false,//异步
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                roomList=obj.roomList;
            }else{
                $.alert("获取设备失败！");
            }
        },
        error:function(xhr,errorType,error){
            $.alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    })
}
function delOrder() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/delOrder.action",
        async:false,//异步
        data:{
            "orderNumber":orderNumber
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);

            if(obj.result==0){
                // $.alert("删除订单成功！");
                // window.history.back();
            }else{
                // $.alert("删除订单失败！");
            }
            // alert("getDatefromSession: "+data);
        },
        error:function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}
function doAuth() {
    cardInfoList.length=0;
    var names=document.getElementsByName("auname");
    var ids=document.getElementsByName("auid");
    for(var i=0;i<ids.length;i++){
        cardInfo=new Object();
        cardInfo.cardNumber=ids[i].innerHTML;
        cardInfo.name=names[i].innerHTML;
        cardInfo.dnCode="";
        cardInfoList.push(cardInfo);
    }

    password=document.getElementById("password").value;
    var st=document.getElementById("datetime-picker").value;
    var et=document.getElementById("datetime-picker-lk").value;
    startTime=getTimeString(st)+"00";
    endTime=getTimeString(et)+"59";
    // $.alert("st:  "+st);
    // $.alert("et:  "+et);
    var cardString=JSON.stringify(cardInfoList);
    // $.alert(cardString);
    delOrder();
    $.ajax({
        type:"POST",
        url:projectPath+"/room/addOrder.action",
        async:false,//异步
        data:{
            "startTime":startTime,
            "endTime":endTime,
            "roomId":roomId,
            "roomTypeId":roomTypeId,
            "cardInfoList":cardString,
            "password":password
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                $.alert("修改订单成功！");
                // window.history.back();
            }else{
                $.alert("修改订单失败！");
            }
            // alert("getDatefromSession: "+data);
        },
        error
            :function(xhr,errorType,error){
            $.alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });

}
function getTimeString(sTime) {
    var t1=sTime.split(" ")[0];
    var t2=sTime.split(" ")[1];
    var year=t1.split("-")[0];
    var month=t1.split("-")[1];
    var day=t1.split("-")[2];
    var hour=t2.split(":")[0];
    var minute=t2.split(":")[1];
    if(parseInt(hour)<10){
        hour="0"+hour;
    }
    var tag=year+month+day+hour+minute;
    return tag;
}
function getOrderContent() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getOrderContent.action",
        async:false,//异步
        data:{
            "orderNumber":orderNumber
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                checkInTime=obj.checkInTime;
                startTime=obj.startTime;
                endTime=obj.endTime;
                roomId=obj.roomId;
                roomName=obj.roomName;
                roomTypeId=obj.roomTypeId;
                roomType=obj.roomType;
                cardInfoList=obj.cardInfoList;
                password=obj.password;
            }else{
                $.alert("获取订单失败！");
            }
            // alert("getDatefromSession: "+data);
        },
        error:function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}
//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}