/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var phoneNumber;
var verificationCode;
var ownerPassword;
var timerGate_sendCode;
var timerGate_login;
$(function () {
    timerGate_sendCode=true;
    timerGate_login=true;

    $.init();
});

/*
//请求短信验证码
function sendVerifyCode(){
    phoneNumber=document.getElementsByTagName('input')[0].value;
    if(''!==phoneNumber){
        $.ajax({
            type:"POST",
            url:projectPath+"/login/sendVerifyCode.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "phoneNumber":phoneNumber
            },
            dataType:'json',
            success:function(data,status,xhr){
                if (false==data.result){
                    window.location.reload();
                }
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
            url:projectPath+"/login/checkVerifyCode.action",
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
    phoneNumber=document.getElementsByTagName('input')[0].value;
    ownerPassword=document.getElementsByTagName('input')[2].value;
    if (''!==phoneNumber && ''!==ownerPassword){
        $.ajax({
            type:"POST",
            url:projectPath+"/login/bindOpenid.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerPhoneNumber":phoneNumber,
                "ownerPassword":ownerPassword
            },
            dataType:'json',
            success:function(data,status,xhr){
                if (0===data.result){
                    //已绑定openid到phone,直接登录.
                    window.location.href="jsp/main.jsp?ownerPhoneNumber="+phoneNumber;
                }
                if (2===data.result){
                    //手机号不存在，需要注册手机号.
                    window.location.href="jsp/register.jsp?ownerPhoneNumber="+phoneNumber+"&ownerPassword="+ownerPassword;
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
    // var url=projectPath+"/login/bindOpenid.action?ownerPhoneNumber="+phoneNumber+"&ownerPassword="+ownerPassword;
    // window.location.href=encodeURI(url);
}
*/

//请求短信验证码
function sendVerifyCode(){
    phoneNumber=document.getElementsByTagName('input')[0].value;
    if(''!==phoneNumber){
        if (timerGate_sendCode) {
            timerGate_sendCode = false;
            $.showPreloader('发送验证码...');
            $.ajax({
                type:"POST",
                url:projectPath+"/sms/sendVerifyCode.action",
                async:true,//异步
                data:{
                    "phoneNumber":phoneNumber
                },
                dataType:'json',
                success:function(data,status,xhr){
                    $.hidePreloader();
                    // $.toast('data.result'+data.result);
                    // $.toast('data.verificationCode : '+data.verificationCode);

                    // if (0!==data.result){
                    //     $.toast('发送失败,状态码: '+data.result,2000);
                    //     window.location.reload();
                    // }
                },
                error:function(xhr,errorType,error){
                    $.hidePreloader();
                    console.log('错误')
                }
            });
            scheduleINfo();
        }else {
            $.toast('暂时无法获取验证码,请稍待',1500);
        }
    }else {
        $.toast('手机号码为空，无法获取验证码',1500);
    }
}
//重新获取验证码提示
function scheduleINfo() {
    var codeCreateInterval=60;
    window.setTimeout(function () {
        window.clearInterval(timer1);
        timerGate_sendCode=true;
        document.getElementById("A_sendVerifyCode").innerText="重新获取验证码 ";
    },(codeCreateInterval+1)*1000);
    var timer1=window.setInterval(function () {
        if (codeCreateInterval>0){
            document.getElementById("A_sendVerifyCode").innerText="重新获取验证码 ("+codeCreateInterval+'s)';
            codeCreateInterval=codeCreateInterval-1;
        }
    },1000);
}
function checkVerifyCode() {
    verificationCode=document.getElementsByTagName('input')[1].value;
    if(''!==verificationCode){
        $.showPreloader('校对验证码...');
        $.ajax({
            type:"POST",
            url:projectPath+"/sms/checkVerifyCode.action",
            async:true,//异步
            data:{
                "verificationCode":verificationCode
            },
            dataType:'json',
            success:function(data,status,xhr){
                $.hidePreloader();
                if(1===data.result){
                    //验证码有效
                    bindOpenid();
                }else {
                    $.toast(data.errMsg,1500);
                }
            },
            error:function(xhr,errorType,error){
                $.hidePreloader();
                console.log('错误')
            }
        });
    }else {
        $.toast('请先输入验证码',1500);
    }
}

function bindOpenid() {
    ownerPassword=document.getElementsByTagName('input')[2].value;
    if (''!==ownerPassword){
        $.showPreloader();
        $.ajax({
            type:"POST",
            url:projectPath+"/login/bindOpenid.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerPassword":ownerPassword
            },
            dataType:'json',
            success:function(data,status,xhr){
                $.hidePreloader();
                if (0===data.result){
                    //已绑定openid到phone,直接登录.
                    window.location.href="jsp/main2.jsp";
                }
                if (2===data.result){
                    //手机号不存在，需要注册手机号.
                    window.location.href="jsp/register.jsp";
                }
                if (undefined===data.result || 0==data.result){
                    $.toast("data.result为空"+data.result,5000);
                }
                if (undefined!=data.errMsg && null!=data.errMsg && ''!=data.errMsg){
                    $.toast(data.errMsg,1500);
                }
            },
            error:function(xhr,errorType,error){
                $.hidePreloader();
                console.log('错误');
            }
        });
    }else {
        $.toast('请先输入密码',1500);
    }
}