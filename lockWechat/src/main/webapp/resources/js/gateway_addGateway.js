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
    $('nav a').removeClass('active').eq(2).addClass('active');
    clearIframeById("frame1");
    ownerPhoneNumber=getQueryString('ownerPhoneNumber');
    document.getElementsByTagName('input')[0].value='';
    document.getElementsByTagName('input')[1].value='';

    $.init();
});
function clearIframeById(id) {
    var iframe = document.getElementById(id);
    if (iframe) {
        iframe.src = 'about:blank';
        try {
            iframe.contentWindow.document.write('');
            iframe.contentWindow.document.clear();
        } catch (e) {
        }
    //以上可以清除大部分的内存和文档节点记录数了
        document.body.removeChild(iframe);
        // iframe.parentNode.removeChild(iframe);
    }
}
    /*
    function getGatewayVerifyCode() {
        gatewayIp=null;
        getGatewayIp();
        LANaddr=null;
        if(null!=gatewayIp && ''!==gatewayIp){
            getGatewayLANaddr();
            opCode=null;
            if(null!=LANip && ''!==LANip){
                document.getElementById("frame1").src=LANaddr;
            }else {
                //获取网关所在局域网地址失败,可能是网关已被添加过
                $.toast('网关已被添加过');
            }
        }else {
            $.toast('获取网关所在数据服务器地址失败');
        }
    }
    */
function getGatewayVerifyCode() {
    gatewayIp=null;
    gatewayCode=document.getElementsByTagName('input')[0].value;
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getGatewayIp.action",
        async:true,//异步
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            $.hideIndicator();
            gatewayIp=data;
            LANaddr=null;
            if(null!=gatewayIp && ''!==gatewayIp){
                $.showIndicator();
                $.ajax({
                    type:"POST",
                    url:projectPath+"/gateway/getGatewayLANIp.action",
                    async:true,//异步
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
                    dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data,status,xhr){
                        $.hideIndicator();
                        LANip=data.ip;
                        LANaddr='http://'+data.ip+':9018';
                        opCode=null;
                        if(null!=LANip && ''!==LANip){
                            document.getElementById("frame1").src=LANaddr;
                        }else {
                            //获取网关所在局域网地址失败,可能是网关已被添加过
                            $.toast('网关已被添加过');
                        }
                    },
                    error:function(xhr,errorType,error){
                        $.hideIndicator();
                        console.log('ajax错误');
                    }
                });
            }else {
                $.toast('获取网关所在数据服务器地址失败');
            }
        },
        error:function(xhr,errorType,error){
            $.hideIndicator();
            console.log('ajax错误');
        }
    });
}

function addGateway() {
    isCorrectGatewayVerificationCode=null;
    opCode=document.getElementsByTagName('input')[1].value;
    if(null!=opCode && ''!==opCode){
        correctGatewayVerificationCode();
        if(true===isCorrectGatewayVerificationCode){
            url="jsp/gateway/gateway_registerGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+gatewayCode+"&opCode="+opCode;
            window.location.href=encodeURI(url);
        }else {
            $.toast('校验网关验证码失败');
        }
    }else {
        $.toast('网关验证码不能为空');
    }
}
//获取网关的数据服务器ip
function getGatewayIp() {
    gatewayCode=document.getElementsByTagName('input')[0].value;
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getGatewayIp.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            $.hideIndicator();
            // alert('Action-getGatewayIp ajax-result : '+data);
            gatewayIp=data;
        },
        error:function(xhr,errorType,error){
            $.hideIndicator();
            console.log('ajax错误');
        }
    });
}
//网关未被添加,获取网关的内网LANaddr
function getGatewayLANaddr() {
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/getGatewayLANIp.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            $.hideIndicator();
            // alert('Action-getGatewayLANIp ajax-result : '+data);
            LANip=data.ip;
            LANaddr='http://'+data.ip+':9018';
        },
        error:function(xhr,errorType,error){
            $.hideIndicator();
            console.log('ajax错误');
        }
    });
}

//验证网关验证码是否正确
function correctGatewayVerificationCode() {
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/gateway/isCorrectGatewayVerificationCode.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"opCode":opCode},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            $.hideIndicator();
            // alert('Action-isCorrectGatewayVerificationCode ajax-result : '+data);
            if(1==data){
                isCorrectGatewayVerificationCode=true;
            }else {
                isCorrectGatewayVerificationCode=false;
            }
        },
        error:function(xhr,errorType,error){
            $.hideIndicator();
            console.log('ajax错误');
        }
    });
}

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}