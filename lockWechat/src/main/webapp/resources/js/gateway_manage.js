/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var json_theGateway;
var url;
$(function(){
    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getSpecificGateway.action",
        async:true,
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode},
        dataType:'json',
        success:function(data,status,xhr){
            json_theGateway = data;
            $("#UL_gatewayProperty").html(getGatewayDetails());
            $(".content .list-block #UL_theSpecificGateway").html(createLockNode(specificGatewayCode));
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
    //动态显示门锁列表
    /*
    UL_gatewayProperty=document.getElementById("UL_gatewayProperty");
    UL_gatewayProperty.innerHTML = getGatewayDetails();
    UL_theSpecificGateway=document.getElementById("UL_theSpecificGateway");
    UL_theSpecificGateway.innerHTML = createLockNode();

    UL_theSpecificGateway.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== UL_theSpecificGateway){
            // if(target.getAttribute('class')==='card-content' && target.parentNode.className==='card lock'){
            if(target.tagName==='A'){
                //页面跳转并传递参数
                specificLockCode=target.parentNode.id;
                url="jsp/lock/lock_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+specificGatewayCode+"&specificLockCode="+specificLockCode;
                window.location.href=encodeURI(url);
                break;
            }
            target = target.parentNode;
        }
    });
    */
    // $("#UL_theSpecificGateway a").click(function (event) {
    //     specificLockCode=event.target.id;
    //     console.log("specificLockCode:"+specificLockCode);
    // });
    $("#UL_theSpecificGateway").on('click',function (event) {
        var target=event.target;
        while (target != $("#UL_theSpecificGateway")){
            if(target.nodeName.toLowerCase() == 'a'){
                specificLockCode=target.id;
                console.log("specificLockCode:"+specificLockCode);
                url="jsp/lock/lock_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+specificGatewayCode+"&specificLockCode="+specificLockCode;
                window.location.href=encodeURI(url);
            }
            target = target.parentNode;
        }
    });
    //添加关联门锁
    var div_addLock=document.getElementById("link_addLock");
    /*
    div_addLock.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== div_addLock){
            if(target.getAttribute('class')==='item-inner'){
                url="jsp/gateway/gateway_addLock.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+specificGatewayCode;
                window.location.href=encodeURI(url);
            }
            target = target.parentNode;
        }
    });
    */
    div_addLock.addEventListener('click',function(ev){
        url="jsp/gateway/gateway_addLock.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+specificGatewayCode;
        window.location.href=encodeURI(url);
    });

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

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

/*
function createLockNode(){
    var LI_lock="";
    var lockLists;
    lockLists=json_theGateway.lockLists;
    for(x in lockLists){
        if('1'===lockLists[x].lockStatus){lockLists[x].lockStatus="正常"}
        if('2'===lockLists[x].lockStatus){lockLists[x].lockStatus="异常"}
        if('3'===lockLists[x].lockStatus){lockLists[x].lockStatus="连接失败"}

        LI_lock += "<li class='card lock' id='"+lockLists[x].lockCode+"' style='margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:'0.2rem';'>";
        LI_lock += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+lockLists[x].lockName+"</div>";
        LI_lock += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
        LI_lock += 		"<div class='card-content-inner'>";
        LI_lock += 			"<img class='auto-zoom-3' src='resources/img/lock.png' />";
        LI_lock += 		"</div>";
        LI_lock += 	"</div>";
        LI_lock += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
        LI_lock += 		"<p style='color: #00B83F;'>"+lockLists[x].lockStatus+"</p>";
        LI_lock += 	"</div>";
        LI_lock += "</li>";
    }
    return LI_lock;
}
*/
function createLockNode(gatewayCode){
    var html='';
    var lockLists=json_theGateway.lockLists;
    for(x in lockLists){
        if('0'===lockLists[x].lockStatus){lockLists[x].lockStatus="无消息"}
        if('1'===lockLists[x].lockStatus){lockLists[x].lockStatus="正常"}
        if('2'===lockLists[x].lockStatus){lockLists[x].lockStatus="异常"}
        if('3'===lockLists[x].lockStatus){lockLists[x].lockStatus="连接失败"}

        html +=     '<a id="'+lockLists[x].lockCode+'" href="javascript:void(0);" class="lock item-link item-content item-gateway">';
        html +=         '<div class="item-media"><img src="resources/images/inco-doorlock.png" width="44"></div>';
        html +=         '<div class="item-inner item-none">';
        html +=             '<div class="item-title-row">';
        html +=                 '<div class="item-title">'+lockLists[x].lockName+'</div>';
        html +=             '</div>';
        html +=             '<div class="item-subtitle gateway-green">'+lockLists[x].lockStatus+'</div>';
        html +=         '</div>';
        html +=     '</a>';
    }
    return html;
}