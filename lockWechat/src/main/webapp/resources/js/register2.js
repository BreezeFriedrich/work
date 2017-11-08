/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var verifyCode;
$(function () {
    $.init();
});

function registerPhoneByVerifyCode(){
    verifyCode=document.getElementsByTagName('input')[0].value;
    if(''!==verifyCode){
        $.ajax({
            type:"POST",
            url:projectPath+"/account/checkVerifyCodeThenRegister.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "verifyCode":verifyCode
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
        $.toast('验证码为空，无法完成注册',1500);
    }
}