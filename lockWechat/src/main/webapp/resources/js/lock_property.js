/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var json_theLock;
var lockName;
var lockLocation;
var lockComment;

//fastclick javascript 调用
if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}
/*
document.querySelector("#box").addEventListener("click",function(){
        alert("click me!");
    },false)
 */

$(function(){
    //fastclick jQuery 调用
    // FastClick.attach(document.body);

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    specificLockCode=getQueryString('specificLockCode');
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/getSpecificLock.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        data:{"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        dataType:'json',
        success:function(data,status,xhr){
            json_theLock = data;
            lockName=json_theLock.lockName;
            lockLocation=json_theLock.lockLocation;
            lockComment=json_theLock.lockComment;
        },
        error:function(xhr,errorType,error){
            console.log('ajax 请求出错')
        }
    });
    document.getElementsByTagName('input')[0].setAttribute('placeholder',json_theLock.lockName);
    document.getElementsByTagName('input')[1].setAttribute('placeholder',json_theLock.lockLocation);
    document.getElementsByTagName('input')[2].setAttribute('placeholder',json_theLock.lockComment);

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function getINPUT_lockProperty(){
    var temp_lockName=document.getElementsByTagName('input')[0].value;
    var temp_lockLocation=document.getElementsByTagName('input')[1].value;
    //input取value,默认值为空字符串.
    var temp_lockComment=document.getElementsByTagName('input')[2].value;

    if(""!==temp_lockName) {lockName=temp_lockName}
    if(""!==temp_lockLocation) {lockLocation=temp_lockLocation}
    if(""!==temp_lockComment) {lockComment=temp_lockComment}
}

function modifyLockInfo(){
    getINPUT_lockProperty();
//	alert('gatewayName: '+gatewayName);
//	alert('gatewayLocation: '+gatewayLocation);
//	alert('gatewayComment: '+gatewayComment);
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/modifyLockInfo.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{
            // "ownerPhoneNumber":ownerPhoneNumber,
            "lockCode":specificLockCode,
            "lockName":lockName,
            "lockLocation":lockLocation,
            "lockComment":lockComment
        },
        dataType:'json',
        success:function(data,status,xhr){
            $.hideIndicator();
            // alert('ajax-result : '+data)
        },
        error:function(xhr,errorType,error){
            $.hideIndicator();
            console.log('错误')
        }
    });
    window.location.reload(false);//缓存中获取当前页
}

function deleteLock(){
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/deleteLock.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{
            // "ownerPhoneNumber":ownerPhoneNumber,
            "lockCode":specificLockCode
        },
        dataType:'json',
        success:function(data,status,xhr){
            $.showIndicator();
            // alert('ajax-result : '+data)
        },
        error:function(xhr,errorType,error){
            $.showIndicator();
            console.log('错误')
        }
    });
    // window.location.href="jsp/main.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    window.history.go(-2);
}