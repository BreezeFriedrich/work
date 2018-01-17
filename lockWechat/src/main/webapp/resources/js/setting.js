/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var specificLockCode;

if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}

//修改用户昵称
var div_modifyNickname=document.getElementById("link_modifyNickname");
/*
div_modifyNickname.addEventListener('click',function(ev){
    // var target = ev.target || window.event.srcElement;
    url="jsp/setting/set_modifyNickname.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    window.location.href=encodeURI(url);
});
*/
div_modifyNickname.addEventListener('click',function(ev){
    var target = ev.target || window.event.srcElement;
    while(target !== div_modifyNickname){
        if(target.getAttribute('class')==='item-inner'){
            url="jsp/unlock/unlock_authById.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
            window.location.href=encodeURI(url);
        }
        target = target.parentNode;
    }
    url="jsp/setting/set_modifyNickname.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    window.location.href=encodeURI(url);
});
//修改登录密码
var div_modifyPassword=document.getElementById("link_modifyPassword");
/*
div_modifyPassword.addEventListener('click',function(ev){
    url="jsp/setting/set_modifyPassword.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    window.location.href=encodeURI(url);
});
*/
div_modifyPassword.addEventListener('click',function(ev){
    var target = ev.target || window.event.srcElement;
    while(target !== div_modifyPassword){
        if(target.getAttribute('class')==='item-inner'){
            url="jsp/unlock/unlock_authById.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
            window.location.href=encodeURI(url);
        }
        target = target.parentNode;
    }
    url="jsp/setting/set_modifyPassword.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    window.location.href=encodeURI(url);
});
//设置授权密码
var div_setGesturePassword=document.getElementById("link_setGesturePassword");
/*
div_setGesturePassword.addEventListener('click',function(ev){
    url="jsp/setting/set_gesturePassword.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    window.location.href=encodeURI(url);
});
*/
div_setGesturePassword.addEventListener('click',function(ev){
    var target = ev.target || window.event.srcElement;
    while(target !== div_setGesturePassword){
        if(target.getAttribute('class')==='item-inner'){
            url="jsp/setting/set_gesturePassword.jsp?ownerPhoneNumber="+ownerPhoneNumber;
            window.location.href=encodeURI(url);
        }
        target = target.parentNode;
    }
});

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