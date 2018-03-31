/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var authPassword;
var redirectUrl;

$(function(){
    // redirectUrl=getQueryString("redirectUrl");
    redirectUrl = document.getElementById("INPUT_hidden").value;

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function proofAuthpassword() {
    authPassword=document.getElementsByTagName('input')[0].value;
    if(validateAuthPassword(authPassword)){
        $.showIndicator();
        $.ajax({
            type:"POST",
            url:"account/proofAuthpassword.action",
            async:true,
            data:{
                "authPassword":authPassword,
                "redirectUrl":redirectUrl
            },
            dataType:'json',
            success:function(data,status,xhr){
                $.hideIndicator();
                if(data){
                    $.toast('授权码正确,正在返回...',1500);
                    // window.setTimeout("history.go(-1)",2000);
                    // window.location.href=encodeURI(redirectUrl);
                    window.setTimeout("window.location.href=encodeURI(redirectUrl);",2000);
                }else {
                    $.toast('授权码错误',1500);
                    window.location.reload();
                }
            },
            error:function(xhr,errorType,error){
                $.hideIndicator();
                $.toast('发送请求失败',1500);
            }
        });
    }else {
        $.toast('授权密码使用1~9位字母数字',1500);
    }
}
function validateAuthPassword(password){
    var regAuthPassword=/^[a-zA-Z0-9]{1,9}$/;//1~9位数字、字母.
    if(regAuthPassword.test(password)){
        return true;
    }
    return false;
}