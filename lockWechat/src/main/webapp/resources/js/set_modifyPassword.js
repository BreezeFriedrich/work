/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var newPassword;

if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}

$(function(){
    // FastClick.attach(document.body);

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function modifyPassword() {
    newPassword=document.getElementsByTagName('input')[0].value;

    if(''!==newPassword){
        if(validateOwnerPassword(newPassword)){
            $.showIndicator();
            $.ajax({
                type:"POST",
                url:projectPath+"/account/modifyPassword.action",
                async:true,//异步
                data:{
                    // "ownerPhoneNumber":ownerPhoneNumber,
                    "newPassword":newPassword
                },
                dataType:'json',
                success:function(data,status,xhr){
                    $.hideIndicator();
                    if(data){
                        $.toast('修改成功,返回上一页...',1500);
                        window.setTimeout("history.go(-1)",2000);
                    }else {
                        $.toast('修改失败!',1500);
                    }
                },
                error:function(xhr,errorType,error){
                    $.hideIndicator();
                    $.alert('修改用户登录密码失败', '操作失败！');
                }
            });
        }else {
            $.toast('用户登录密码使用4~12位字母数字',1500);
        }
    }
}
function validateOwnerPassword(password){
    var regOwnerPassword=/^[a-zA-Z0-9]{4,12}$/;//4~12位数字、字母.
    if(regOwnerPassword.test(password)){
        return true;
    }
    return false;
}