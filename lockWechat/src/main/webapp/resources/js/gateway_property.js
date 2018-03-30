/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var gatewayName;
var gatewayLocation;
var gatewayComment;

$(function(){
    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getSpecificGateway.action",
        async:true,
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode},
        dataType:'json',
        success:function(data,status,xhr){
            gatewayName=data.gatewayName;
            gatewayLocation=data.gatewayLocation;
            gatewayComment=data.gatewayComment;
            document.getElementsByTagName('input')[0].setAttribute('placeholder',data.gatewayName);
//另一种JS设置placeholder的方法	document.getElementsByTagName('input')[1].placeholder=data.gatewayLocation;
            document.getElementsByTagName('input')[1].setAttribute('placeholder',data.gatewayLocation);
            document.getElementsByTagName('input')[2].setAttribute('placeholder',data.gatewayComment);
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

function getINPUT_gatewayProperty(){
    //	var gatewayName=document.getElementById("INPUT_gatewayName").value
    var temp_gatewayName=document.getElementsByTagName('input')[0].value;
    var temp_gatewayLocation=document.getElementsByTagName('input')[1].value;
    //input取value,默认值为空字符串.
    var temp_gatewayComment=document.getElementsByTagName('input')[2].value;

    if(""!==temp_gatewayName) {gatewayName=temp_gatewayName}
    if(""!==temp_gatewayLocation) {gatewayLocation=temp_gatewayLocation}
    if(""!==temp_gatewayComment) {gatewayComment=temp_gatewayComment}
}

function modifyGatewayInfo(){
    getINPUT_gatewayProperty();
    $.confirm('确认修改',function () {
        $.showIndicator();
        $.ajax({
            type:"POST",
            url:projectPath+"/gateway/modifyGatewayInfo.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerPhoneNumber":ownerPhoneNumber,
                "gatewayCode":specificGatewayCode,
                "gatewayName":gatewayName,
                "gatewayLocation":gatewayLocation,
                "gatewayComment":gatewayComment
            },
            dataType:'json',
            success:function(data,status,xhr){
                // alert('ajax-result : '+data)
            },
            error:function(xhr,errorType,error){
                console.log('错误')
            }
        });
        $.hideIndicator();
        window.location.reload(false);//缓存中获取当前页
    });
}

function deleteGateway(){
    $.confirm('确认删除',function () {
        $.showIndicator();
        $.ajax({
            type:"POST",
            url:projectPath+"/gateway/deleteGateway.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerPhoneNumber":ownerPhoneNumber,
                "gatewayCode":specificGatewayCode
            },
            dataType:'json',
            success:function(data,status,xhr){
            },
            error:function(xhr,errorType,error){
                console.log('错误')
            }
        });
        $.hideIndicator();
        window.location.href="jsp/main.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    });
}