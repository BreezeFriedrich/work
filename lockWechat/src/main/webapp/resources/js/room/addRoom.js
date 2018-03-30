/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var freeLocks=new Array();
var gatewayCodes=new Array();
var roomTypeId;

$(function () {
    roomTypeId=getQueryString("roomTypeId");
    getFreeLock();
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
                values: freeLocks
            }
        ]
    });

    document.getElementById("picker").addEventListener('blur',function(ev){
        var lock=$("#picker").val();
        for(var i=0;i<freeLocks.length;i++){
            if(lock==freeLocks[i]){
                $("#gatewaycode").val(gatewayCodes[i]);
                break;
            }
        }
    });
});

function getGatewayCode() {
    var lock=document.getElementById("picker").value;
    for(var i=0;i<freeLocks.length;i++){
        if(lock==freeLocks[i]){
            document.getElementById("gatewaycode").setAttribute("value",gatewayCodes[i]);
            break;
        }
    }
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
                var lockList=obj.lockList;
                for(var i=0;i<lockList.length;i++){
                    freeLocks.push(lockList[i].lockCode);
                    gatewayCodes.push(lockList[i].gatewayCode);
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

function addRoom() {
    var lock=document.getElementById("picker").value;
    var gatewaycode=document.getElementById("gatewaycode").value;
    var roomName=document.getElementById("roomName").value;

    if(lock==undefined || lock==null  ||lock==""){
        $.alert("当前没有可绑定锁，无法添加房间");
    }else{
        $.ajax({
            type:"POST",
            url:projectPath+"/room/addRoom.action",
            async:false,//异步
            data:{
                "roomName":roomName,
                "lockCode":lock,
                "gatewayCode":gatewaycode,
                "roomTypeId":roomTypeId
            },
            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data){
                var obj=JSON.parse(data);
                if(obj.result==0){
                    $.alert('添加房间成功！', function () {
                        window.history.back();
                    });
                    // $.alert("添加房间成功！");
                    // window.location.reload();
                }else{
                    $.alert("添加房间失败！");
                }
            },
            error:function(xhr,errorType,error){
                $.alert("获取设备请求失败！");
                console.log('ajax错误');
            }
        });
    }



}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}