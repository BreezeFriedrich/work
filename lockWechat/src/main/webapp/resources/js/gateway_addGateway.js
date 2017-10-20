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
var opCode;
var isCorrectGatewayVerificationCode;
var url;
$(function () {
    ownerPhoneNumber=getQueryString('ownerPhoneNumber');

    gatewayIp=null;
    getGatewayIp();
    LANip=null;
    if(null!=gatewayIp){
        getGatewayLANIp();
    }
    opCode=null;
    if(null!==LANip){
        getVerificationCode();
    }
    isCorrectGatewayVerificationCode=null;
    if(null!=opCode){
        correctGatewayVerificationCode();
    }
    if(true===isCorrectGatewayVerificationCode){
        url="jsp/gateway/gateway_registerGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+gatewayCode+"&opCode="+opCode;
        window.location.href=encodeURI(url);
    }
});

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
            alert('Action-getGatewayIp ajax-result : '+data);
            gatewayIp=data;
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}
//网关未被添加,获取网关的内网LANip
function getGatewayLANIp() {
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getGatewayLANIp.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            alert('Action-getGatewayLANIp ajax-result : '+data);
            LANip='http://'+data.ip+':9018';
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

//根据网关内网LANip获取网关验证码opCode
function getVerificationCode(LANip) {
    $.ajax({
        type:"GET",
        url:projectPath+"/gateway/getVerificationCode.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{url:LANip+':9018'},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            alert('ajax-result : '+data);
            opCode=data;
        },
        error:function(xhr,errorType,error){
            console.log('错误');
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
            alert('Action-isCorrectGatewayVerificationCode ajax-result : '+data);
            if(1==data){
                isCorrectGatewayVerificationCode=true;
            }else {
                isCorrectGatewayVerificationCode=false;
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

//创建连接内网中网关从而获取验证码的窗体frame.
function createFrame() {
    var $iframe = document.createElement('iframe');
    $iframe.name='iframe_opCode';
    $iframe.src = LANip;
    $iframe.style.height = 200;
    document.getElementById('expande_iframe').appendChild($iframe);
}
function createIframeCard() {
    var div_iframe='';
    div_iframe += "<div class='content-block-title'>网关内网ip</div>";
    div_iframe += "<div class='card'>";
    div_iframe +=     "<div class='card-content'>";
    div_iframe +=         "<div class='card-content-inner'>";
    // div_iframe +=             "<iframe src='LANip' frameborder='0' scrolling='no'></iframe>";
    div_iframe +=             "<iframe src="+LANip+" frameborder='0' scrolling='no'></iframe>";
    div_iframe +=         "</div>";
    div_iframe +=     "</div>";
    div_iframe += "</div>";
    return div_iframe;
}

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}