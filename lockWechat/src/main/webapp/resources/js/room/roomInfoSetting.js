var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var roomInfo;
var roomList=new Array();
var roomTypeId;
var roomType;
var roomId;
var lockList=new Array();
var lockandgateway;
//四个input
var roomN=document.getElementById("roomName");
var lockCode=document.getElementById("picker");
var roomt=document.getElementById("roomt");
var gatewayCode=document.getElementById("gatewayCode");

$(function () {
    // roomInfo=getQueryString("roomInfo");
    // $.alert("roomInfo.roomName:  "+roomInfo);
    getRooms();
    getFreeLock();
    loadPage();
    $(document).on('click','.confirm-ok', function () {
        $.confirm('是否确定删除?', function () {
            delRoom();
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
function loadPage() {
    roomTypeId = getQueryString("roomTypeId");
    roomId = getQueryString("roomId");
    // roomId= getfromSession("roomId");
    for(var j=0;j<roomList.length;j++){
        if(roomTypeId==roomList[j].roomTypeId){
            roomType=roomList[j].roomType;
            var roomInfoList=roomList[j].roomInfoList;
            for(var k=0;k<roomInfoList.length;k++){
                if(roomId==roomInfoList[k].roomId){
                    roomInfo=roomInfoList[k];
                }
            }
        }
    }
    roomN.getAttributeNode("value").value=roomInfo.roomName;
    lockCode.getAttributeNode("value").value=roomInfo.lockCode;
    roomt.getAttributeNode("value").value=roomType;
    gatewayCode.getAttributeNode("value").value=roomInfo.gatewayCode;
    $("#picker").picker({
        toolbarTemplate:
            '<header class="bar bar-nav">\
              <button class="button button-link pull-left"></button>\
              <button class="button button-link pull-right close-picker">确定</button>\
              <h1 class="title">请选择锁</h1>\
             </header>',
        cols: [
            {
                textAlign: 'center',
                values: lockList
            }
        ]
    });

    document.getElementById("picker").addEventListener('blur',function(ev){
        var lock=$('#picker').val();
        for(var i=0;i<lockList.length;i++){
            if(lock==lockList[i]){
                $('#gatewayCode').val(lockandgateway[i].gatewayCode);
                break;
            }
        }
    });


}

function getFreeLock() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getFreeLock.action",
        async:false,//异步
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                lockandgateway=obj.lockList;
                for(var i=0;i<lockandgateway.length;i++){
                    lockList.push(lockandgateway[i].lockCode);
                }
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


function delRoom() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/delRoom.action",
        async:false,//异步
        data:{
            "roomId":roomInfo.roomId
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                $.alert('删除房间成功！', function () {
                    window.history.back();
                });
            }else{
                $.alert("删除房间失败！");
            }
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}


function resetRoom() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/resetRoom.action",
        async:false,//异步
        data:{
            "roomTypeId":roomTypeId,
            "roomId":roomInfo.roomId,
            "newLockCode":$('#picker').val(),
            "newRoomName":$('#roomName').val()
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);
            if(obj.result==0){
                $.alert('修改房间成功！', function () {
                    window.history.back();
                });
                // $.alert('修改房间成功!');
            }else{
                $.alert("修改房间失败！");
            }
        },
        error:function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}


function changeGatewayCode() {
    var lock= lockCode.value;
    for(var i=0;i<lockandgateway.length;i++){
        if(lock==lockandgateway[i].lockCode){
            gatewayCode.getAttributeNode("value").value=lockandgateway[i].gatewayCode;
        }
    }
}
