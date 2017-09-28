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

$(function(){
    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    specificLockCode=getQueryString("specificLockCode");
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/getSpecificLock.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        dataType:'json',
        success:function(data,status,xhr){
            json_theLock = data;
        },
        error:function(xhr,errorType,error){
            console.log('错误')
        }
    });
    document.getElementsByTagName('p')[0].innerText=json_theLock.lockName;
    document.getElementsByTagName('p')[1].innerText=json_theLock.lockLocation;
    document.getElementsByTagName('p')[2].innerText=json_theLock.lockComment;
    document.getElementsByTagName('p')[3].innerText=specificLockCode;
    document.getElementsByTagName('p')[4].innerText=specificGatewayCode;
    // document.getElementsByTagName('input')[0].setAttribute('placeholder',json_theLock.lockName);
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}