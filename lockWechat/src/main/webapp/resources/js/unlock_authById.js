/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gatewayCode;
var lockCode;
var name;
var startTime;
var endTime;
var cardNumb;
$(function(){
    // FastClick.attach(document.body);

    //初始化时间选择器
    var temptime = new Date();
    $("#datetime-picker-1").datetimePicker({
        value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
    });
    temptime.setDate(temptime.getDate()+1);
    $("#datetime-picker-2").datetimePicker({
        value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
    });

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    gatewayCode=getQueryString("gatewayCode");
    lockCode=getQueryString("lockCode");

    getAuthInfo();

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function authById() {
    name=document.getElementsByTagName('input')[0].value;
    cardNumb=document.getElementsByTagName('input')[1].value;
    startTime=document.getElementsByTagName('input')[2].value;
    endTime=document.getElementsByTagName('input')[3].value;

    if(''!=name && ''!=cardNumb && ''!=startTime && ''!=endTime){
        $.ajax({
            type:"POST",
            url:projectPath+"/unlock/authUnlockById.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerPhoneNumber":ownerPhoneNumber,
                "gatewayCode":gatewayCode,
                "lockCode":lockCode,
                "name":name,
                "startTime":startTime,
                "endTime":endTime,
                "cardNumb":cardNumb
            },
            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,status,xhr){
                ajaxResult = data;
                $.toast('开锁授权成功,正在刷新页面...',1500);
                window.setTimeout("refreshPage()",2000);
            },
            error:function(xhr,errorType,error){
                console.log('错误');
                $.alert('开锁授权失败', '操作失败！');
            }
        });
    }
}
function refreshPage(){
    window.location.reload();
}