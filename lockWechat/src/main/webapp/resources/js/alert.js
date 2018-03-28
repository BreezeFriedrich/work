/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var startTime;
var endTime;
var mescroll;
var gatewayCode;
var lockCode;
var device;

$(function () {
    $('nav a').removeClass('active').eq(4).addClass('active');
    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getAbnormalDevice.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber},
        // data:{},
        dataType:'json',
        success:function(data,status,xhr){
            // showAbnormalDevice(data);
            device=data;
            showDevices();
        },
        error:function(xhr,errorType,error){
            console.log('错误');
            console.log(xhr);
            console.log(errorType);
            console.log(error)
        }
    });
    $.hideIndicator();

    $.init();
});
function showDevices(){
    $('.content ul:first').html(createGatewayNode);
    $('.content li').on("click",'a.gateway',function (event) {
        var $el=$(this);
        if(0==$el.siblings().length){
            $el.after(createLockNode($el.attr('id')));
        }else {
            $el.siblings().remove();
        }
        return false;
    });
}
function createGatewayNode(){
    var html='';
    for(x in device) {
        // if ('4' === device[x].gatewayStatus) {
        //     device[x].gatewayStatus = "正常"
        // }
        // if ('5' === device[x].gatewayStatus) {
        //     device[x].gatewayStatus = "异常"
        // }
        // if ('6' === device[x].gatewayStatus) {
        //     device[x].gatewayStatus = "连接失败"
        // }
        html += '<li>';
        html +=     '<a id="'+device[x].gatewayCode+'" href="javascript:void(0);" class="gateway item-content item-gateway gateway-linegreen">';
        html +=         '<div class="item-media"><img src="resources/images/inco-gateway.png" width="44"></div>';
        html +=         '<div class="item-inner">';
        html +=             '<div class="item-title-row">';
        html +=                 '<div class="item-title">'+device[x].gatewayName+'</div>';
        html +=             '</div>';
        html +=             '<div class="item-subtitle gateway-red">';
        if('4'===device[x].gatewayStatus){
            // html += 		"<p style='color: #00B83F;'>"+"正常"+"</p><a href='#' class='icon icon-down'></a>";
            html += 		"<b style='color: #00B83F;'>"+"正常    "+"</b><i class='iconfont'>&#xeb5f;</i>";
        }else if('5'===device[x].gatewayStatus){
            // html += 		"<p style='color: RED;'>"+"异常"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color: RED;'>"+"异常    "+"</b><i class='iconfont'>&#xe642;</i>";
        }
        else if('6'===device[x].gatewayStatus){
            // html += 		"<p style='color: RED;'>"+"连接失败"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color: RED;'>"+"连接失败  "+"</b><i class='iconfont'>&#xe620;</i>";
        }
        html +=             '</div>';
        html +=         '</div>';
        html +=     '</a>';
        html += '</li>';
    }
    return html;
}

function createLockNode(gatewayCode){
    var html='';
    for(x in device){
        if(device[x].gatewayCode===gatewayCode){
            lockLists=device[x].lockLists;
            break;
        }
    }
    for(x in lockLists){
        // if('0'===lockLists[x].lockStatus){lockLists[x].lockStatus="无消息"}
        // if('1'===lockLists[x].lockStatus){lockLists[x].lockStatus="正常"}
        // if('2'===lockLists[x].lockStatus){lockLists[x].lockStatus="异常"}
        // if('3'===lockLists[x].lockStatus){lockLists[x].lockStatus="连接失败"}

        html +=     '<a id="'+lockLists[x].lockCode+'" href="javascript:void(0);" class="lock item-content item-gateway">';
        html +=         '<div class="item-media"><img src="resources/images/inco-doorlock.png" width="44"></div>';
        html +=         '<div class="item-inner item-none">';
        html +=             '<div class="item-title-row">';
        html +=                 '<div class="item-title">'+lockLists[x].lockName+'</div>';
        html +=             '</div>';
        html +=             '<div class="item-subtitle gateway-green">';
        // 门锁状态lockStatus
        if('0'===lockLists[x].lockStatus){
            // html += 		"<p style='color:RED;'>"+"无信息"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color:RED;'>"+"无数据   "+"</b><i class='iconfont'>&#xeb60;</i>";
        }else if('1'===lockLists[x].lockStatus){
            // html += 		"<p style='color:#00B83F;'>"+"连接正常"+"</p><a href='#' class='icon icon-down'></a>";
            html += 		"<b style='color:#00B83F;'>"+"连接正常  "+"</b>";
        }else if('2'===lockLists[x].lockStatus){
            // html += 		"<p style='color:RED;'>"+"工作异常"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color:RED;'>"+"工作异常  "+"</b><i class='iconfont'>&#xe642;</i>";
        }
        else if('3'===lockLists[x].lockStatus){
            // html += 		"<p style='color:RED;'>"+"连接失败"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color:RED;'>"+"连接失败  "+"</b><i class='iconfont'>&#xe620;</i>";
        }
        // html +="<span style='width: 40px'></span>";
        // 门锁电量lockPower(4-正常,5-不足)
        if('4'===lockLists[x].lockPower){
            // html += 		"<p style='color: RED;'>"+"电量充足"+"</p><a href='#'><img src='resources/img/battery80.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color: RED;'>"+"电量充足  "+"</b>";
        }else{
            // html += 		"<p style='color: RED;'>"+"电量不足"+"</p><a href='#'><img src='resources/img/battery20.png' style='max-width: 30px;max-height: 30px;'/></a>";
            html += 		"<b style='color: RED;'>"+"电量不足  "+"</b><i class='iconfont'>&#xe630;</i>";
        }
        html +=             '</div>';
        html +=         '</div>';
        html +=     '</a>';
    }
    return html;
}

/*
function showAbnormalDevice(data){
    var UL_lockList=document.createElement('ul');
    UL_lockList.id="UL_lockList";
    UL_lockList.style.paddingLeft='0';
    UL_lockList.style.marginTop='0.5rem';
    UL_lockList.style.background='rgba(0,0,0,0.3)';

    var UL_gateway=document.createElement('ul');
    UL_gateway.id="UL_gateway";
    document.getElementById('cardList').appendChild(UL_gateway);
    UL_gateway.innerHTML=createGatewayNode(data);

    //事件代理
    UL_gateway.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== UL_gateway){
            if(target.className==='card-footer' && target.parentNode.className==='card gateway'){
                //向下扩展DOM,门锁card
                selectedGateway=target.parentNode;
                //门锁节点与网关节点同级.
                if(UL_lockList===selectedGateway.nextSibling){
                    selectedGateway.parentNode.removeChild(UL_lockList);
                }else{
                    UL_lockList.innerHTML = createLockNode(selectedGateway.id,data);
                    selectedGateway.parentNode.insertBefore(UL_lockList,selectedGateway.nextSibling);
                }
                break;
            }
            target = target.parentNode;
        }
    })
}

function createGatewayNode(json){
    var LI_gateway="";
    for(x in json){
        // if('4'===json[x].gatewayStatus){json[x].gatewayStatus="正常"}
        // if('5'===json[x].gatewayStatus){json[x].gatewayStatus="异常"}
        // if('6'===json[x].gatewayStatus){json[x].gatewayStatus="连接失败"}

        LI_gateway += "<li class='card gateway' id='"+json[x].gatewayCode+"' style='border: 0.3rem outset rgba(100,100,0,0.5);'>";
        LI_gateway += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+json[x].gatewayName+"</div>";
        LI_gateway += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
        LI_gateway += 		"<div class='card-content-inner'>";
        // LI_gateway += 			"<img class='auto-zoom-4' src='${pageContext.request.contextPath}/resources/img/gateway.png' />";
        LI_gateway += 			"<img class='auto-zoom-4' src='resources/img/gateway.png' />";
        LI_gateway += 		"</div>";
        LI_gateway += 	"</div>";
        LI_gateway += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
        if('4'===json[x].gatewayStatus){
            LI_gateway += 		"<p style='color: #00B83F;'>"+"正常"+"</p><a href='#' class='icon icon-down'></a>";
        }else if('5'===json[x].gatewayStatus){
            LI_gateway += 		"<p style='color: RED;'>"+"异常"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
        }
        else if('6'===json[x].gatewayStatus){
            LI_gateway += 		"<p style='color: RED;'>"+"连接失败"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
        }
        LI_gateway += 	"</div>";
        LI_gateway += "</li>"+"<br/>";
    }
    return LI_gateway;
}

function createLockNode(gatewayCode,json){
    var LI_lock="";
    var lockLists=null;
    for(x in json){
        if(json[x].gatewayCode===gatewayCode){
            lockLists=json[x].lockLists;
            break;
        }
    }
    for(x in lockLists){
        // if('1'===lockLists[x].lockStatus){lockLists[x].lockStatus="正常"}
        // if('2'===lockLists[x].lockStatus){lockLists[x].lockStatus="异常"}
        // if('3'===lockLists[x].lockStatus){lockLists[x].lockStatus="连接失败"}

        LI_lock += "<li class='card lock' id='"+lockLists[x].lockCode+"' style='margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:'0.2rem';'>";
        LI_lock += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+lockLists[x].lockName+"</div>";
        LI_lock += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
        LI_lock += 		"<div class='card-content-inner'>";
        LI_lock += 			"<img class='auto-zoom-3' src='resources/img/lock.png' />";
        LI_lock += 		"</div>";
        LI_lock += 	"</div>";
        LI_lock += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
        // 门锁电量lockPower(4-正常,5-不足)
        if('4'===lockLists[x].lockPower){
            LI_lock += 		"<p style='color: RED;'>"+"电量充足"+"</p><a href='#'><img src='resources/img/battery80.png' style='max-width: 30px;max-height: 30px;'/></a>";
        }else{
            LI_lock += 		"<p style='color: RED;'>"+"电量不足"+"</p><a href='#'><img src='resources/img/battery20.png' style='max-width: 30px;max-height: 30px;'/></a>";
        }
        // 门锁状态lockStatus
        if('1'===lockLists[x].lockStatus){
            LI_lock += 		"<p style='color: #00B83F;'>"+"连接正常"+"</p><a href='#' class='icon icon-down'></a>";
        }else if('2'===lockLists[x].lockStatus){
            LI_lock += 		"<p style='color: RED;'>"+"工作异常"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
        }
        else if('3'===lockLists[x].lockStatus){
            LI_lock += 		"<p style='color: RED;'>"+"连接失败"+"</p><a href='#'><img src='resources/img/caution.png' style='max-width: 30px;max-height: 30px;'/></a>";
        }
        LI_lock += 	"</div>";
        LI_lock += "</li>";
    }
    return LI_lock;
}
// */