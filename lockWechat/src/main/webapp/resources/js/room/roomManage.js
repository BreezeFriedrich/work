var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

var roomList=new Array();

$(function () {
    getRooms();
    loadRoom();
    $(document).on('click','.prompt-ok', function () {
        $.prompt('请输入房型', function (value) {
            addRoomType(value);
        });
    });
});

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
    });
}

function loadRoom() {
    var rList=document.getElementById("roomList");
    var childs = rList.childNodes;
    for(var s = childs.length - 1; s >= 0; s--) {
        rList.removeChild(childs[s]);
    }
    // $("#roomList li").remove();

    for(var i=0;i<roomList.length;i++){
        var li=document.createElement("li");
        var a=document.createElement("a");
        var div=document.createElement("div");
        var div1=document.createElement("div");
        var div2=document.createElement("div");

        var value=roomList[i].roomTypeId;
        a.setAttribute("class","external item-link item-content");
        a.setAttribute("href","jsp/room/roomTypeDetail.jsp?roomTypeId="+value);
        // a.onclick=setToSession("roomTypeId",roomList[i].roomTypeId);
        // a.addEventListener("click",function () {
        //     $.ajax({
        //         type:"POST",
        //         url:projectPath+"/room/setToSession.action",
        //         async:false,//异步
        //         data:{
        //             "sessionName":"roomTypeId",
        //             "sessionValue":value
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
        div1.innerHTML=roomList[i].roomType;
        div2.innerHTML=roomList[i].roomInfoList.length+"间";

        div.appendChild(div1);
        div.appendChild(div2);
        a.appendChild(div);
        li.appendChild(a);
        rList.appendChild(li);
    }
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

function addRoomType(roomType) {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/addRoomType.action",
        async:false,//异步
        data:{
            "roomType":roomType
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                $.alert('添加房型："' + roomType + '" 成功！');
                window.location.reload();
            }else{
                $.alert("添加房型失败！");
            }
        },
        error
            :function(xhr,errorType,error){
            $.alert("获取房型请求失败！");
            console.log('ajax错误');
        }
    });
}