/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var registerPhoneNumber;
var verifyCode;
var ownerPassword;
$(function () {
    $.init();
});

//请求短信验证码
function sendVerifyCode(){
    registerPhoneNumber=document.getElementsByTagName('input')[0].value;
    if(''!==registerPhoneNumber){
        $.ajax({
            type:"POST",
            url:projectPath+"/account/sendVerifyCode.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "registerPhoneNumber":registerPhoneNumber
            },
            dataType:'json',
            success:function(data,status,xhr){
                var P_smsverifycode=document.getElementById('P_smsverifycode');
                P_smsverifycode.innerText="短信验证码为 "+data.smsverifycode;
            },
            error:function(xhr,errorType,error){
                console.log('错误')
            }
        });
    }else {
        $.toast('手机号码为空，无法获取验证码',1500);
    }
}
function checkVerifyCode() {
    verifyCode=document.getElementsByTagName('input')[1].value;
    $.ajax({
        type:"POST",
        url:projectPath+"/account/checkVerifyCode.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{
            "verifyCode":verifyCode
        },
        dataType:'json',
        success:function(data,status,xhr){
            if(1===data.result){
                bindOpenid();
            }else if(2===data.result){
                $.toast(data.errMsg,1500);
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误')
        }
    });
}
function bindOpenid() {
    ownerPassword=document.getElementsByTagName('input')[2].value;
    var url=projectPath+"/account/bindOpenid.action?ownerPhoneNumber="+registerPhoneNumber+"&ownerPassword="+ownerPassword;
    window.location.href=encodeURI(url);
}