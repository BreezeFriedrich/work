/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var lockName;
var lockLocation;
var lockComment;

$(function(){
    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    specificLockCode=getQueryString('specificLockCode');
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/getSpecificLock.action",
        async:true,
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        data:{"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        dataType:'json',
        success:function(data,status,xhr){
            lockName=data.lockName;
            lockLocation=data.lockLocation;
            lockComment=data.lockComment;
            document.getElementsByTagName('input')[0].setAttribute('placeholder',data.lockName);
            document.getElementsByTagName('input')[1].setAttribute('placeholder',data.lockLocation);
            document.getElementsByTagName('input')[2].setAttribute('placeholder',data.lockComment);
        },
        error:function(xhr,errorType,error){
            console.log('ajax 请求出错')
        }
    });

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
    $.confirm('确认修改',function () {
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
    });
}

function deleteLock(){
    $.confirm('确认删除',function () {
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
    });
}