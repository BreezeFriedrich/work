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
                if (false==data.result){
                    window.location.reload();
                }
                // var P_smsverifycode=document.getElementById('P_smsverifycode');
                // P_smsverifycode.innerText="短信验证码为 "+data.smsverifycode;
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
    if(''!==verifyCode){
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
    }else {
        $.toast('请先输入验证码',1500);
    }
}
function bindOpenid() {
    registerPhoneNumber=document.getElementsByTagName('input')[0].value;
    ownerPassword=document.getElementsByTagName('input')[2].value;
    if (''!==registerPhoneNumber && ''!==ownerPassword){
        $.ajax({
            type:"POST",
            url:projectPath+"/account/bindOpenid.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerPhoneNumber":registerPhoneNumber,
                "ownerPassword":ownerPassword
            },
            dataType:'json',
            success:function(data,status,xhr){
                if (0===data.result){
                    //已绑定openid到phone,直接登录.
                    window.location.href="jsp/main.jsp?ownerPhoneNumber="+registerPhoneNumber;
                }
                if (2===data.result){
                    //手机号不存在，需要注册手机号.
                    window.location.href="jsp/register.jsp?ownerPhoneNumber="+registerPhoneNumber+"&ownerPassword="+ownerPassword;
                }
                if (undefined===data.result || 0==data.result){
                    $.toast("data.result为空"+data.result,5000);
                }
                if (undefined!=data.errMsg && null!=data.errMsg && ''!=data.errMsg){
                    $.toast(data.errMsg,1500);
                }
            },
            error:function(xhr,errorType,error){
                console.log('错误')
            }
        });
    }else {
        $.toast('请先输入手机号和密码',1500);
    }
    // var url=projectPath+"/account/bindOpenid.action?ownerPhoneNumber="+registerPhoneNumber+"&ownerPassword="+ownerPassword;
    // window.location.href=encodeURI(url);
}