/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gatewayCode;
var lockCode;
var password;
var startTime;
var endTime;
$(function(){
    // FastClick.attach(document.body);

    //初始化时间选择器
    var temptime = new Date();
    $("#datetime-picker-1").datetimePicker({
        value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
    });
    temptime.setDate(temptime.getDate()+1);
    $("#datetime-picker-2").datetimePicker({
        value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()]
    });

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    gatewayCode=getQueryString("gatewayCode");
    lockCode=getQueryString("lockCode");

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function authByPwd() {
    $.showIndicator();
    password=document.getElementsByTagName('input')[0].value;
    startTime=document.getElementsByTagName('input')[1].value;
    endTime=document.getElementsByTagName('input')[2].value;

    if(''!=password && ''!=startTime && ''!=endTime){
        if(validatePwd(password)){
            $.ajax({
                type:"POST",
                url:projectPath+"/unlock/authUnlockByPwd.action",
                async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                data:{
                    "ownerPhoneNumber":ownerPhoneNumber,
                    "gatewayCode":gatewayCode,
                    "lockCode":lockCode,
                    "password":password,
                    "startTime":startTime,
                    "endTime":endTime
                },
                dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
                success:function(data,status,xhr){
                    $.hideIndicator();
                    ajaxResult = data;
                    $.toast('开锁授权成功,正在刷新页面...',1500);
                    // window.setTimeout("refreshPage()",2000);
                    window.setTimeout("history.go(-1)",2000);
                },
                error:function(xhr,errorType,error){
                    console.log('错误');
                    $.alert('开锁授权失败', '操作失败！');
                }
            });
        }else {
            $.toast('密码输入错误,请输入4~12位数字或字母',1500);
        }
    }
    $.hideIndicator();
}
function validatePwd(password){
    //密码验证规则:4~12位数字和字母的组合
    var regPwd=/^[0-9A-Za-z]{4,12}$/;
    if(regPwd.test(password)){
        return true;
    }
    return false;
}

function refreshPage(){
    window.location.reload();
}

