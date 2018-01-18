/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var newName;

//fastclick javascript 调用
if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function() {
        FastClick.attach(document.body);
    }, false);
}

$(function(){
    //fastclick jQuery 调用
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

function modifyNickname() {
    newName=document.getElementsByTagName('input')[0].value;

    if(''!==newName){
        if(validateOwnerName(newName)){
            $.showIndicator();
            $.ajax({
                type:"POST",
                url:projectPath+"/account/modifyNickname.action",
                async:true,//异步
                data:{
                    // "ownerPhoneNumber":ownerPhoneNumber,
                    "newName":newName
                },
                dataType:'json',
                success:function(data,status,xhr){
                    $.hideIndicator();
                    // alert('data:'+data);
                    if(data){
                        $.toast('修改成功,返回上一页...',1500);
                        window.setTimeout("history.go(-1)",2000);
                        // window.location.href="jsp/main.jsp?ownerPhoneNumber="+ownerPhoneNumber;
                        // window.history.go(-2);
                    }else {
                        $.toast('修改失败!',1500);
                    }
                },
                error:function(xhr,errorType,error){
                    $.hideIndicator();
                    $.alert('修改用户昵称失败', '操作失败！');
                }
            });
        }else {
            $.toast('用户昵称使用字母数字或中文',1500);
        }
    }
}
function validateOwnerName(name){
    // var regOwnerName=/^[a-zA-Z0-9\u4e00-\u9fa5]+$/;//数字、字母、中文.
    var regOwnerName=/^[a-zA-Z0-9\u4e00-\u9fa5]{1,20}$/;
    if(regOwnerName.test(name)){
        return true;
    }
    return false;
}