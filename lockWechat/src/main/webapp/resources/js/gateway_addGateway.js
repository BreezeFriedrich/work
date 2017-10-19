/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var gatewayCode;
var LANip;
var opCode;
var url;
$(function () {
    ownerPhoneNumber=getQueryString('ownerPhoneNumber');
});
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
            //网关未被添加,获取网关的内网LANip
            if(true){

                $.ajax({
                    type:"POST",
                    url:projectPath+"/gateway/getGatewayLANIp.action",
                    async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
                    dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data,status,xhr){
                        alert('Action-getGatewayLANIp ajax-result : '+data);
                        LANip='http://'+data.ip+':9018';
                        // window.location.href=LANip;

                        // var div_iframe=document.createElement('div').className='content-block';
                        // div_iframe.innerHTML=createIframeCard();

                        // document.frames[0].location.href =LANip;
                        // $.config = {router: false}
                        // document.getElementById('div_iframe').src=LANip;
                        // document.getElementById('div_iframe').setAttribute('src',LANip);

                        /*
                        var iframeParent=document.getElementById('expande_iframe');
                        iframeParent.remove(iframeParent.childNodes);
                        var iframe_opCode=iframeParent.createElement('iframe');
                        iframe_opCode.src = LANip;
                        iframeParent.appendChild(iframe_opCode);
                        */


                        var $iframe = document.createElement('iframe');
                        $iframe.src = LANip;
                        $iframe.style.height = 200;
                        $iframe.addEventListener('load', 'HEIHEI');
                        document.getElementById('expande_iframe').appendChild($iframe);

                    },
                    error:function(xhr,errorType,error){
                        console.log('错误');
                    }
                });
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
    //根据网关内网LANip获取网关验证码opCode
    $.ajax({
        type:"GET",
        url:"http://"+LANip.substring(1,LANip.length)+':9018',
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            alert('ajax-result : '+data);
            opCode=data;

            //添加网关
            $.ajax({
                type:"POST",
                url:projectPath+"/gateway/isCorrectGatewayVerificationCode.action",
                async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"opCode":opCode},
                dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
                success:function(data,status,xhr){
                    alert('Action-isCorrectGatewayVerificationCode ajax-result : '+data);
                    url="jsp/gateway/gateway_registerGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+gatewayCode+"&opCode="+opCode;
                    window.location.href=encodeURI(url);
                },
                error:function(xhr,errorType,error){
                    console.log('错误');
                }
            });
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });

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