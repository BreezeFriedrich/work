var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var roomId;
var roomTypeId;
var roomName;
var roomType;
var startTime;
var endTime;
var password;
var clickDate;
var cardInfoList=new Array();
var roomList=new Array();

var roomNameList=new Array();
var roomIDList=new Array();


$(function () {
    initAttributes();

});


function initAttributes() {
    //初始化属性
    roomId=getQueryString("roomId");
    roomTypeId=getQueryString("roomTypeId");
    roomName=getQueryString("roomName");
    clickDate=getQueryString("clickDate");
    getRooms();
    var clickyear=clickDate.substring(0,4);
    var clickmonth=clickDate.substring(4,6);
    var clickday=clickDate.substring(6,8);
    var nextday=parseInt(clickday)+1;


    document.getElementById("roomtag").setAttribute("placeholder",roomName+"("+roomType+")");
    //初始化时间选择
    $("#datetime-picker").datetimePicker({
        value: [clickyear, clickmonth, clickday, '12', '00']
    });
    $("#datetime-picker-lk").datetimePicker({
        value: [clickyear, clickmonth, ''+nextday, '12', '00']
    });
    //初始化房间选择列表
    for(var i=0;i<roomList.length;i++){
        var rtype=roomList[i].roomType;
        if(roomTypeId==roomList[i].roomTypeId){
            roomType=rtype;
        }
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
}




function getfromSession(sessionName) {
    var sessionValue;
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getFromSession.action",
        async:false,//异步
        data:{
            "sessionName":sessionName
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            sessionValue=data;
            // alert("getDatefromSession: "+data);
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
    return  sessionValue;
}


function addID() {

    var name=document.getElementById("idname").value;
    var id=document.getElementById("idcard").value;

    if(name==undefined || name=="" || name== null){
    }if(id==undefined || id=="" || id== null){
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

function doAuth() {
    cardInfoList.length=0;
    var names=document.getElementsByName("auname");
    var ids=document.getElementsByName("auid");
    for(var i=0;i<ids.length;i++){
        // $.alert("names[0]:  "+names[i].innerHTML);
        // $.alert("ids[0]:  "+ids[i].innerHTML);

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
                $.alert("添加订单成功！");
                window.history.back();
            }else{
                $.alert("添加订单失败！");
            }
            // alert("getDatefromSession: "+data);
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
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
                for(var i=0;i<roomList.length;i++){
                    if(roomTypeId==roomList[i].roomTypeId){
                        roomType=roomList[i].roomType;
                    }
                }

            }else{
                $.alert("获取设备失败！");
            }
        },
        error:function(xhr,errorType,error){
            $.alert("获取设备请求失败！");
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