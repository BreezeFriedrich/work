/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gatewayCode;
var gatewayIp;
var LANip;
var LANaddr;
var opCode;
var isCorrectGatewayVerificationCode;
var url;
$(function () {
    ownerPhoneNumber=getQueryString('ownerPhoneNumber');

    $.init();
});

function addGateway() {
    gatewayIp=null;
    getGatewayIp();
    LANaddr=null;
    if(null!=gatewayIp && ''!==gatewayIp){
        getGatewayLANaddr();
    }else {
        $.toast('获取网关所在数据服务器地址失败');
    }
    opCode=null;
    if(null!=LANip && ''!==LANip){
        getVerificationCode();
    }else {
        $.toast('获取网关所在局域网地址失败,可能是网关已被添加过');
    }
    isCorrectGatewayVerificationCode=null;
    if(null!=opCode && ''!==opCode){
        correctGatewayVerificationCode();
    }else {
        $.toast('获取网关验证码失败');
    }
    if(true===isCorrectGatewayVerificationCode){
        url="jsp/gateway/gateway_registerGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+gatewayCode+"&opCode="+opCode;
        window.location.href=encodeURI(url);
    }else {
        $.toast('校验网关验证码失败');
    }
}
//获取网关的数据服务器ip
function getGatewayIp() {
    gatewayCode=document.getElementsByTagName('input')[0].value;
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getGatewayIp.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            // alert('Action-getGatewayIp ajax-result : '+data);
            gatewayIp=data;
        },
        error:function(xhr,errorType,error){
            console.log('ajax错误');
        }
    });
}
//网关未被添加,获取网关的内网LANaddr
function getGatewayLANaddr() {
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getGatewayLANIp.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            // alert('Action-getGatewayLANIp ajax-result : '+data);
            LANip=data.ip;
            LANaddr='http://'+data.ip+':9018';
        },
        error:function(xhr,errorType,error){
            console.log('ajax错误');
        }
    });
}

//根据网关内网LANaddr获取网关验证码opCode
function getVerificationCode() {
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getVerificationCode.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{url:LANaddr},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            // alert('opCode : '+data);
            opCode=data;
        },
        error:function(xhr,errorType,error){
            console.log('ajax错误');
        }
    });
}

//验证网关验证码是否正确
function correctGatewayVerificationCode() {
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/isCorrectGatewayVerificationCode.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"opCode":opCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            // alert('Action-isCorrectGatewayVerificationCode ajax-result : '+data);
            if(1==data){
                isCorrectGatewayVerificationCode=true;
            }else {
                isCorrectGatewayVerificationCode=false;
            }
        },
        error:function(xhr,errorType,error){
            console.log('ajax错误');
        }
    });
}

/*
//创建连接内网中网关从而获取验证码的窗体frame.
function createFrame() {
    var $iframe = document.createElement('iframe');
    $iframe.name='iframe_opCode';
    $iframe.src = LANaddr;
    $iframe.style.height = 200;
    document.getElementById('expande_iframe').appendChild($iframe);
}
function createIframeCard() {
    var div_iframe='';
    div_iframe += "<div class='content-block-title'>网关内网ip</div>";
    div_iframe += "<div class='card'>";
    div_iframe +=     "<div class='card-content'>";
    div_iframe +=         "<div class='card-content-inner'>";
    // div_iframe +=             "<iframe src='LANaddr' frameborder='0' scrolling='no'></iframe>";
    div_iframe +=             "<iframe src="+LANaddr+" frameborder='0' scrolling='no'></iframe>";
    div_iframe +=         "</div>";
    div_iframe +=     "</div>";
    div_iframe += "</div>";
    return div_iframe;
}
*/

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}