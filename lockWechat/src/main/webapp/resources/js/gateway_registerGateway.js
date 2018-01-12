/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gatewayCode;
var opCode;
var gatewayName;
var gatewayLocation;
var gatewayComment;

$(function () {
    ownerPhoneNumber=getQueryString('ownerPhoneNumber');
    gatewayCode=getQueryString('gatewayCode');
    opCode=getQueryString('opCode');
    document.getElementsByClassName('property')[0].innerText=gatewayCode;
    document.getElementsByClassName('property')[1].innerText=opCode;

    $.init();
});

function registerGateway() {
    getINPUT_gatewayProperty();
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/registerGatewayInfo.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{
            // "ownerPhoneNumber":ownerPhoneNumber,
            "gatewayCode":gatewayCode,
            "gatewayName":gatewayName,
            "gatewayLocation":gatewayLocation,
            "gatewayComment":gatewayComment,
            "opCode":opCode
        },
        dataType:'json',
        success:function(data,status,xhr){
            $.hideIndicator();
            // alert('Action-addGateway ajax-result : '+data);
            $.toast('操作成功,正在返回上一页...',1500);
            window.setTimeout("refreshPage()",2000);
        },
        error:function(xhr,errorType,error){
            console.log('错误')
        }
    });
    $.hideIndicator();
}

function getINPUT_gatewayProperty(){
    //	var gatewayName=document.getElementById("INPUT_gatewayName").value
    gatewayName=document.getElementsByTagName('input')[0].value;
    gatewayLocation=document.getElementsByTagName('input')[1].value;
    gatewayComment=document.getElementsByTagName('input')[2].value;
}

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
function refreshPage()
{
    // window.location.href='jsp/main.jsp';
    window.history.go(-1);
}