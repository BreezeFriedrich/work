/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var ownerName;
$(function () {
    $.init();
});

function register() {
    ownerName=document.getElementsByTagName('input')[0].value;
    if(''!==ownerName){
        $.showPreloader();
        $.ajax({
            type:"POST",
            url:projectPath+"/login/register.action",
            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
            data:{
                "ownerName":ownerName
            },
            dataType:'json',
            success:function(data,status,xhr){
                $.hidePreloader();
                if(true===data.result){
                    window.location.href=encodeURI("jsp/main.jsp");
                }else {
                    $.toast('注册失败',1500);
                    window.location.reload();
                }
            },
            error:function(xhr,errorType,error){
                $.hidePreloader();
                console.log('错误')
            }
        });
    }else {
        $.toast('请先输入昵称',1500);
    }
}

