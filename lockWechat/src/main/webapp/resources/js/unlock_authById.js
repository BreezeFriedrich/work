/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gatewayCode;
var lockCode;
var name;
var startTime;
var endTime;
var cardNumb;
var authPassword;

$(function(){
    //初始化时间选择器
    var temptime = new Date();
    $("#datetime-picker-1").datetimePicker({
        value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()],
        onClose:function (p) {
            startTime=moment($("#datetime-picker-1").val(),'YYYY-MM-DD HH:mm').valueOf();
        }
    });
    startTime=moment($("#datetime-picker-1").val(),'YYYY-MM-DD HH:mm').valueOf();
    temptime.setDate(temptime.getDate()+1);
    $("#datetime-picker-2").datetimePicker({
        value: [temptime.getFullYear(),temptime.getMonth()+1, temptime.getDate(),temptime.getHours(),temptime.getMinutes()],
        onClose:function (p) {
            endTime=moment($("#datetime-picker-2").val(),'YYYY-MM-DD HH:mm').valueOf();
        }
    });
    endTime=moment($("#datetime-picker-2").val(),'YYYY-MM-DD HH:mm').valueOf();

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

function authById() {
    authPassword=document.getElementsByTagName('input')[0].value;
    name=document.getElementsByTagName('input')[1].value;
    cardNumb=document.getElementsByTagName('input')[2].value;
    // 错误的写法 startTime=document.getElementsByTagName('input')[3].value;
    // startTime=$("#datetime-picker-1").val();
    // endTime=$("#datetime-picker-2").val();

    if(''!=name && ''!=cardNumb && ''!=startTime && ''!=endTime && ''!=authPassword){
        if(validateIdCard(cardNumb)){
            $.showIndicator();
            $.ajax({
                type:"POST",
                url:projectPath+"/unlock/authUnlockById.action",
                async:true,
                data:{
                    // "ownerPhoneNumber":ownerPhoneNumber,
                    "gatewayCode":gatewayCode,
                    "lockCode":lockCode,
                    "name":name,
                    "startTime":startTime,
                    "endTime":endTime,
                    "cardNumb":cardNumb,
                    "authPassword":authPassword
                },
                dataType:'json',
                success:function(data,status,xhr){
                    $.hideIndicator();
                    if(0==data.result){
                        $.toast('开锁授权成功,正在返回上一页...',1500);
                        window.setTimeout("history.go(-1)",2000);
                    }else if(2==data.result){
                        $.toast(data.msg,1500);
                        url="jsp/setting/set_authPassword.jsp?ownerPhoneNumber="+ownerPhoneNumber;
                        window.location.href=encodeURI(url);
                    }else {
                        $.toast(data.msg,1500);
                    }
                },
                error:function(xhr,errorType,error){
                    $.hideIndicator();
                    console.log('错误');
                    $.alert('开锁授权失败', '操作失败！');
                }
            });
        }else {
            $.toast('身份证号码输入错误',1500);
        }
    }else {
        $.toast('输入不能为空',1500);
    }
}
function validateIdCard(idCard){
    //15位和18位身份证号码的正则表达式
    var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

    //如果通过该验证，说明身份证格式正确，但准确性还需计算
    if(regIdCard.test(idCard)){
        return true;
        /*
        if(idCard.length==18){
            var idCardWi=new Array( 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ); //将前17位加权因子保存在数组里
            var idCardY=new Array( 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
            var idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
            for(var i=0;i<17;i++){
                idCardWiSum+=idCard.substring(i,i+1)*idCardWi[i];
            }

            var idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
            var idCardLast=idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if(idCardMod==2){
                if(idCardLast=="X"||idCardLast=="x"){
                    return true;
                }
            }else{
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if(idCardLast==idCardY[idCardMod]){
                    return true;
                }
            }
        }
        */
    }
    return false;
}

function refreshPage(){
    window.location.reload();
}