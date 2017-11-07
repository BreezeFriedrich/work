/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var registerPhoneNumber;
$(function () {
    $.init();
});

function getVerifyCode(){
    registerPhoneNumber=document.getElementsByTagName('input')[0].value;
    if(''!==registerPhoneNumber){
        $.ajax({
            type:"POST",
            url:projectPath+"/account/register.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "registerPhoneNumber":registerPhoneNumber
            },
            dataType:'json',
            success:function(data,status,xhr){
                // alert('ajax-result : '+data)
            },
            error:function(xhr,errorType,error){
                console.log('错误')
            }
        });
    }else {
        $.toast('手机号码为空，无法获取验证码',1500);
    }
}