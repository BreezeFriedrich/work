
var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var roomList=new Array();
var roomTypeId;
var roomType;
var roomInfoList=new Array();



$(function () {
    roomTypeId = getQueryString("roomTypeId");
    document.getElementById("ar").setAttribute("href","jsp/room/addRoom.jsp?roomTypeId="+roomTypeId);
    // $.alert("roomTypeId:  "+roomTypeId);
    // roomTypeId = getfromSession("roomTypeId");
    getRooms();
    for(var i=0;i<roomList.length;i++){
        if(roomTypeId==roomList[i].roomTypeId){
            roomType=roomList[i].roomType;
            // setToSession("roomType",roomType);
            roomInfoList=roomList[i].roomInfoList;
            break;
        }
    }

    loadPage();
    $(document).on('click','.icon-remove', function () {
        $.confirm('是否确定删除?', function () {
            delRoomType();

        });
    });
});
//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function delRoomType() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/delRoomType.action",
        async:false,//异步
        data:{
            "roomTypeId":roomTypeId
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                $.alert("删除房型成功！");

            }else{
                $.alert("删除房型失败！");
            }
        },
        error
            :function(xhr,errorType,error){
            $.alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}

function loadPage() {
    document.getElementById("roomType").innerHTML=roomType;
    var rList= document.getElementById("rList");
    // $("#rList li").remove();
    var childs = rList.childNodes;
    for(var s = childs.length - 1; s >= 0; s--) {

        rList.removeChild(childs[s]);
    }
    for(var i=0;i<roomInfoList.length;i++){

        // $.alert("roomInfo.roomName:  "+roomInfo);
        var li=document.createElement("li");
        var a=document.createElement("a");
        var div=document.createElement("div");
        var div1=document.createElement("div");
        var div2=document.createElement("div");


        a.setAttribute("class","external item-link item-content");
        a.setAttribute("href","jsp/room/roomInfoSetting.jsp?roomTypeId="+roomTypeId+"&roomId="+roomInfoList[i].roomId);
        // $.alert("roomTypeId:  "+roomTypeId);
        // a.onclick=setToSession("roomId",roomInfoList[i].roomId);
        // a.onclick=setToSession("roomInfo",roomInfoList[i]);
        // a.addEventListener("click",function () {
        //     $.ajax({
        //         type:"POST",
        //         url:projectPath+"/room/setToSession.action",
        //         async:false,//异步
        //         data:{
        //             "sessionName":"roomInfo",
        //             "sessionValue":roomInfo
        //         },
        //         dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        //         success:function(data){
        //             // alert("setDateToSession:  "+data);
        //
        //         },
        //         error
        //             :function(xhr,errorType,error){
        //             alert("获取设备请求失败！");
        //             console.log('ajax错误');
        //         }
        //     });
        // });

        div.setAttribute("class","item-inner");
        div1.setAttribute("class","item-title");
        div2.setAttribute("class","item-after");
        div1.innerHTML="房间号";
        div2.innerHTML=roomInfoList[i].roomName;


        div.appendChild(div1);
        div.appendChild(div2);
        a.appendChild(div);
        li.appendChild(a);
        rList.appendChild(li);
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
        error
            :function(xhr,errorType,error){
            $.alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}



function getfromSession(sessionName) {
    var result;
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getFromSession.action",
        async:false,//异步
        data:{
            "sessionName":sessionName
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            result=data;
            // alert("getDatefromSession: "+data);
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
    return  result;
}