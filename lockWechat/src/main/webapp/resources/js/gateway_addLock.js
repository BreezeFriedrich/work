/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var ajaxResult;
var lockCode;
var lockName;
var lockLocation;
var lockComment;
$(function(){
    // FastClick.attach(document.body);

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
function addLock() {
    lockCode=document.getElementsByTagName('input')[0].value;
    lockName=document.getElementsByTagName('input')[1].value;
    lockLocation=document.getElementsByTagName('input')[2].value;
    lockComment=document.getElementsByTagName('input')[3].value;
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/hasLockAdded.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text

        success:function(data,status,xhr){
            ajaxResult = data;
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
    if(0===ajaxResult.result) {
        //已确认该门锁可以被添加
        $.ajax({
            type:"POST",
            url:projectPath+"/lock/addLock.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":lockCode,"lockName":lockName,"lockLocation":lockLocation,"lockComment":lockComment},
            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text

            success:function(data,status,xhr){
                // $.alert('增加门锁成功,刷新页面', '操作成功');
                $.toast('操作成功,正在刷新页面...',1500);
                window.setTimeout("refreshPage()",2000);
            },
            error:function(xhr,errorType,error){
                console.log('错误');
            }
        });
    }else if(1===ajaxResult.result){
        $.alert('该门锁已被添加到网关，不可重复添加。', '操作失败！');
    }else {
        $.alert('该门锁不被允许添加到网关', '操作失败！');
    }
}
function refreshPage()
{
    window.location.reload();
}