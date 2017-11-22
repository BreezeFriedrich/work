/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var ownerPassword;
var ownerName;
$(function () {
    $.init();
});
//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function register() {
    ownerName=document.getElementsByTagName('input')[0].value;
    ownerPhoneNumber=getQueryString('ownerPhoneNumber');
    ownerPassword=getQueryString("ownerPassword");
    if(''!==ownerName){
        $.ajax({
            type:"POST",
            url:projectPath+"/account/register.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerName":ownerName,
                "ownerPhoneNumber":ownerPhoneNumber,
                "ownerPassword":ownerPassword
            },
            dataType:'json',
            success:function(data,status,xhr){
                if(true===data.result){
                    window.location.href="main.jsp?ownerPhoneNumber="+ownerPhoneNumber;
                }else {
                    $.toast('注册失败',1500);
                    window.location.reload();
                }
            },
            error:function(xhr,errorType,error){
                console.log('错误')
            }
        });
    }else {
        $.toast('请先输入昵称',1500);
    }
}

