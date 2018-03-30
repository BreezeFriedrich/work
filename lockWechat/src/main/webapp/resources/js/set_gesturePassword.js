/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gesturePassword;
var newGesturePassword;

$(function(){
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

function setGesturePassword() {
    gesturePassword=document.getElementsByTagName('input')[0].value;
    newGesturePassword=document.getElementsByTagName('input')[1].value;

    if(''!==newGesturePassword){
        if(validateGesturePassword(newGesturePassword)){
            $.showIndicator();
            /*
            $.ajax({
                type:"POST",
                url:projectPath+"/account/authGesturePassword.action",
                async:true,//异步
                data:{
                    // "ownerPhoneNumber":ownerPhoneNumber,
                    "gesturePassword":gesturePassword,
                    "newGesturePassword":newGesturePassword
                },
                dataType:'json',
                success:function(data,status,xhr){
                    $.hideIndicator();
                    if(data){
                        $.toast('修改开锁授权密码成功,正在刷新页面...',1500);
                        window.setTimeout("history.go(-1)",2000);
                    }else {
                        $.toast('修改开锁授权密码失败!',1500);
                    }
                },
                error:function(xhr,errorType,error){
                    $.hideIndicator();
                    $.alert('修改开锁授权密码失败', '操作失败！');
                }
            });
            */
            $.ajax({
                type:"POST",
                url:projectPath+"/account/authGesturePassword.action",
                async:true,//异步
                data:{
                    // "ownerPhoneNumber":ownerPhoneNumber,
                    "gesturePassword":gesturePassword,
                    "newGesturePassword":newGesturePassword
                },
                dataType:'json',
                success:function(data,status,xhr){
                    $.hideIndicator();
                    if(0==data.result){
                        $.toast('修改成功,返回上一页...',1500);
                        window.setTimeout("history.go(-1)",2000);
                    }else if(2==data.result){
                        $.toast(data.msg,1500);
                        url="jsp/setting/set_gesturePassword.jsp?ownerPhoneNumber="+ownerPhoneNumber;
                        window.location.href=encodeURI(url);
                    }else {
                        $.toast(data.msg,1500);
                    }
                },
                error:function(xhr,errorType,error){
                    $.hideIndicator();
                    $.alert('修改开锁授权密码(ajax)失败', '操作失败！');
                }
            });
        }else {
            $.toast('开锁授权密码使用1~9位字母数字',1500);
        }
    }
}
function validateGesturePassword(password){
    var regGesturePassword=/^[a-zA-Z0-9]{1,9}$/;//1~9位数字、字母.
    if(regGesturePassword.test(password)){
        return true;
    }
    return false;
}