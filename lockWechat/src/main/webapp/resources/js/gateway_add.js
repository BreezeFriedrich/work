/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var specificGatewayCode;
var json_theGateway;
$(function(){
    specificGatewayCode=getQueryString("gatewayCode");
    $.ajax({
        type:"POST",
        url:"http://localhost:80/lockWechat/gateway/getSpecificGateway.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
//	headers:{"Access-Control-Allow-Origin":"*"},
        data:{"ownerPhoneNumber":"18255683932","gatewayCode":specificGatewayCode},
//	timeout:3000,
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text

        success:function(data,status,xhr){
            json_theGateway = data;
        },
        error:function(xhr,errorType,error){
            console.log('错误')
            console.log(xhr)
            console.log(errorType)
            console.log(error)
        }
    });
    UL_gatewayProperty.innerHTML += getGatewayDetails();
    UL_theSpecificGateway=document.getElementById("UL_theSpecificGateway");
    UL_theSpecificGateway.innerHTML = createLockNode();
})

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};

function getGatewayDetails(){
    var LI_gatewayProperty='';
    LI_gatewayProperty += "<li class='item-content'>";
    LI_gatewayProperty +=	"<div class='item-inner'>";
    LI_gatewayProperty +=		"<div class='item-title'>网关名称</div>";
    LI_gatewayProperty +=		"<div class='item-after'>"+json_theGateway.gatewayName+"</div>";
    LI_gatewayProperty +=	"</div>";
    LI_gatewayProperty += "</li>";

    LI_gatewayProperty += "<li class='item-content'>";
    LI_gatewayProperty +=	"<div class='item-inner'>";
    LI_gatewayProperty +=		"<div class='item-title'>网关地址</div>";
    LI_gatewayProperty +=		"<div class='item-after'>"+json_theGateway.gatewayLocation+"</div>";
    LI_gatewayProperty +=	"</div>";
    LI_gatewayProperty += "</li>";

    LI_gatewayProperty += "<li class='item-content'>";
    LI_gatewayProperty +=	"<div class='item-inner'>";
    LI_gatewayProperty +=		"<div class='item-title'>网关备注</div>";
    LI_gatewayProperty +=		"<div class='item-after'>"+json_theGateway.gatewayComment+"</div>";
    LI_gatewayProperty +=	"</div>";
    LI_gatewayProperty += "</li>";

    LI_gatewayProperty += "<li class='item-content'>";
    LI_gatewayProperty +=	"<div class='item-inner'>";
    LI_gatewayProperty +=		"<div class='item-title'>网关条码</div>";
    LI_gatewayProperty +=		"<div class='item-after'>"+json_theGateway.gatewayCode+"</div>";
    LI_gatewayProperty +=	"</div>";
    LI_gatewayProperty += "</li>";

    return LI_gatewayProperty;
}

function createLockNode(){
    var LI_lock="";
    var lockLists;
    lockLists=json_theGateway.lockLists;
    for(x in lockLists){
        LI_lock += "<li class='card lock' id='"+lockLists[x].lockCode+"' style='margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:'0.2rem';'>";
        LI_lock += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+lockLists[x].lockName+"</div>";
        LI_lock += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
        LI_lock += 		"<div class='card-content-inner'>";
        LI_lock += 			"<img class='auto-zoom-3' src='img/lock.png' />";
        LI_lock += 		"</div>";
        LI_lock += 	"</div>";
        LI_lock += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
        LI_lock += 		"<p style='color: #00B83F;'>"+lockLists[x].lockStatus+"</p><a href='#' class='icon icon-down'></a>";
        LI_lock += 	"</div>";
        LI_lock += "</li>";
    }
    return LI_lock;
}

