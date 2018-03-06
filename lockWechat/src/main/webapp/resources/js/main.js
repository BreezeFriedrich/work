/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var json;
var ownerPhoneNumber;
var url;
$(function(){
    ownerPhoneNumber = document.getElementById("INPUT_hidden").value;
    if(undefined==ownerPhoneNumber || ''==ownerPhoneNumber){
        ownerPhoneNumber=getQueryString('ownerPhoneNumber');
    }

    if (""!==ownerPhoneNumber){
        $.toast('通过隐藏输入框获取手机号码:'+ownerPhoneNumber,3000);
    }else {
        $.toast('无法通过隐藏输入框获取手机号码');
        ownerPhoneNumber=18255683932
    }

    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getDeviceInfo.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // headers:{"Access-Control-Allow-Origin":"*"},
        // data:{"ownerPhoneNumber":ownerPhoneNumber},
        data:{},
        // timeout:3000,
        dataType:'json',

        success:function(data,status,xhr){
            $.hideIndicator();
            json = data;
        },
        error:function(xhr,errorType,error){
            $.hideIndicator();
            console.log('错误');
            console.log(xhr);
            console.log(errorType);
            console.log(error)
        }
    });
    showDevices();

    //给左边栏绑定事件
    var div_addGateway=document.getElementById("div_addGateway");
    div_addGateway.addEventListener('click',function(ev){
        url="jsp/gateway/gateway_addGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    var div_statistics=document.getElementById("div_statistics");
    div_statistics.addEventListener('click',function(ev){
        url="jsp/record/record.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });

    $.init();
});

function showDevices(){
    var html='';
    html +='';

    var UL_gateway=document.createElement('ul');
    UL_gateway.id="UL_gateway";
    document.getElementById('cardList').appendChild(UL_gateway);
    // UL_gateway=document.getElementById('UL_gateway');
    var UL_lockList=document.createElement('ul');
    UL_lockList.id="UL_lockList";
    UL_lockList.style.paddingLeft='0';
    UL_lockList.style.marginTop='0.5rem';
    UL_lockList.style.background='rgba(0,0,0,0.3)';
    // document.getElementById('cardList').appendChild(UL_lockList);

    UL_gateway.innerHTML = createGatewayNode();
    //事件代理
    UL_gateway.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== UL_gateway){
            if(target.getAttribute('class')==='card-content' && target.parentNode.className==='card gateway'){
                var gatewayCode=target.parentNode.id;
                url="jsp/gateway/gateway_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode;
                window.location.href=encodeURI(url);
                break;
            }
            if(target.className==='card-footer' && target.parentNode.className==='card gateway'){
                //向下扩展DOM,门锁card
                selectedGateway=target.parentNode;
                // alert(selectedGateway.id);
                // $(target).parent('li').siblings('li').remove();

                /*
                //门锁节点是网关节点子节点.
                if(selectedGateway.contains(UL_lockList)){
                    selectedGateway.removeChild(UL_lockList);
                }else{
                    UL_lockList.innerHTML = createLockNode(selectedGateway.id);
                    selectedGateway.appendChild(UL_lockList);
                }
                */
                //门锁节点与网关节点同级.
                if(UL_lockList===selectedGateway.nextSibling){
                    selectedGateway.parentNode.removeChild(UL_lockList);
                }else{
                    UL_lockList.innerHTML = createLockNode(selectedGateway.id);
                    selectedGateway.parentNode.insertBefore(UL_lockList,selectedGateway.nextSibling);
                }
                break;
            }
            target = target.parentNode;
        }
    })
}
function createGatewayNode(){
    var LI_gateway="";
    for(x in json){
        if('4'===json[x].gatewayStatus){json[x].gatewayStatus="正常"}
        if('5'===json[x].gatewayStatus){json[x].gatewayStatus="异常"}
        if('6'===json[x].gatewayStatus){json[x].gatewayStatus="连接失败"}

        LI_gateway += "<li class='card gateway' id='"+json[x].gatewayCode+"' style='border: 0.3rem outset rgba(100,100,0,0.5);'>";
        LI_gateway += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+json[x].gatewayName+"</div>";
        LI_gateway += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
        LI_gateway += 		"<div class='card-content-inner'>";
        LI_gateway += 			"<img class='auto-zoom-4' src='resources/img/gateway.png' />";
        LI_gateway += 		"</div>";
        LI_gateway += 	"</div>";
        LI_gateway += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
        LI_gateway += 		"<p style='color: #00B83F;'>"+json[x].gatewayStatus+"</p><a href='#' class='icon icon-down'></a>";
        LI_gateway += 	"</div>";
        LI_gateway += "</li>"+"<br/>";
    }
    return LI_gateway;
}

function createLockNode(gatewayCode){
    var LI_lock="";
    var lockLists=null;
    for(x in json){
        if(json[x].gatewayCode===gatewayCode){
            lockLists=json[x].lockLists;
            break;
        }
    }
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

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}