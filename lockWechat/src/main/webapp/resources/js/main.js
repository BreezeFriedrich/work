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
        // $.toast('通过隐藏输入框获取手机号码:'+ownerPhoneNumber,3000);
    }else {
        $.toast('无法通过隐藏输入框获取手机号码');
        ownerPhoneNumber=18255683932
    }
    ownerPhoneNumber=18255683932;

    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getDeviceInfo.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // headers:{"Access-Control-Allow-Origin":"*"},
        data:{"ownerPhoneNumber":ownerPhoneNumber},
        // timeout:3000,
        dataType:'json',

        success:function(data,status,xhr){
            $.hideIndicator();
            json = data;
            var jsonStr='[{"gatewayCode":"GWH0081702000003","gatewayComment":"备注","gatewayLocation":"地址","gatewayName":"网关3_25","gatewayStatus":"6","lockLists":[' +
                '{"lockCode":"LCN0011721000001","lockComment":"备注","lockLocation":"地址","lockName":"门锁1","lockPower":"0","lockStatus":"0"},' +
                '{"lockCode":"LCN0011721000002","lockComment":"备注","lockLocation":"地址","lockName":"门锁2","lockPower":"0","lockStatus":"0"},' +
                '{"lockCode":"LCN0011721000003","lockComment":"备注","lockLocation":"地址","lockName":"门锁3","lockPower":"0","lockStatus":"0"},' +
                ']},' +
                '{"gatewayCode":"GWH0081702000002","gatewayComment":"备注","gatewayLocation":"地址","gatewayName":"网关2","gatewayStatus":"6","lockLists":[' +
                    '{"lockCode":"LCN0011721000004","lockComment":"备注","lockLocation":"地址","lockName":"门锁4","lockPower":"0","lockStatus":"0"},' +
                    '{"lockCode":"LCN0011721000005","lockComment":"备注","lockLocation":"地址","lockName":"门锁5","lockPower":"0","lockStatus":"0"},' +
                    ']}' +
                ']';
            json=eval(jsonStr);
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
    $('.nav a').eq(1).click(function () {
        url="jsp/gateway/gateway_addGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    $('.nav a').eq(2).click(function () {
        url="jsp/record/record.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    // var div_addGateway=document.getElementById("div_addGateway");
    // div_addGateway.addEventListener('click',function(ev){
    //     url="jsp/gateway/gateway_addGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    //     window.location.href=encodeURI(url);
    // });
    // var div_statistics=document.getElementById("div_statistics");
    // div_statistics.addEventListener('click',function(ev){
    //     url="jsp/record/record.jsp?ownerPhoneNumber="+ownerPhoneNumber;
    //     window.location.href=encodeURI(url);
    // });

    $.init();
});

// function showDevices(){
//     $('.content ul:first').html(createGatewayNode);
//     // createGatewayNode($('.content ul:first'));
//     var toggleDisplay=false;
//     // $('.content li > a').on("click",'a:first',function (event) {
//     $('.content li').on("click",'a.gateway',function (event) {
//         console.log('this:'+$(this));
//         // var $el=event.target.children('a');
//         var $el=$(this);
//         if(0==$el.siblings().length){
//             $el.after(createLockNode($el.attr('id')));
//         }else {
//             // $el.siblings().toggle();
//             if(toggleDisplay){
//                 $el.siblings().fadeIn();
//                 toggleDisplay=false;
//             }else {
//                 $el.siblings().fadeOut();
//                 toggleDisplay=true;
//             }
//         }
//         return false;
//     });
//     $('.content li').on("click",'a.lock',function (event) {
//         var $el=$(this);
//         var lockCode=$el.id;
//         var gatewayCode=$el.siblings(':first').id;
//         url="jsp/lock/lock_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode+"&specificLockCode="+lockCode;
//         window.location.href=encodeURI(url);
//         return false;
//     });
// }
function showDevices(){
    $('.content ul:first').html(createGatewayNode);
    $('.content li a.gateway').on("click",'img',function (e) {
        var gatewayCode=$(this).parent().parent('a').attr('id');
        url="jsp/gateway/gateway_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode;
        window.location.href=encodeURI(url);
    });
    $('.content li').on("click",'a.gateway',function (event) {
        var $el=$(this);
        if(0==$el.siblings().length){
            $el.after(createLockNode($el.attr('id')));
        }else {
            $el.siblings().remove();
            // $el.siblings().toggle();
            // if(toggleDisplay){
            //     $el.siblings().fadeIn();
            //     toggleDisplay=false;
            // }else {
            //     $el.siblings().fadeOut();
            //     toggleDisplay=true;
            // }
        }
        return false;
    });
    $('.content li').on("click",'a.lock',function (event) {
        var $el=$(this);
        var lockCode=$el.id;
        var gatewayCode=$el.siblings(':first').id;
        url="jsp/lock/lock_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode+"&specificLockCode="+lockCode;
        window.location.href=encodeURI(url);
        return false;
    });
}
function createGatewayNode(){
    var html='';
    for(x in json) {
        if ('4' === json[x].gatewayStatus) {
            json[x].gatewayStatus = "正常"
        }
        if ('5' === json[x].gatewayStatus) {
            json[x].gatewayStatus = "异常"
        }
        if ('6' === json[x].gatewayStatus) {
            json[x].gatewayStatus = "连接失败"
        }
        html += '<li>';
        html +=     '<a id="'+json[x].gatewayCode+'" href="javascript:void(0);" class="gateway item-content item-gateway gateway-linegreen">';
        html +=         '<div class="item-media"><img src="resources/images/inco-gateway.png" width="44"></div>';
        html +=         '<div class="item-inner">';
        html +=             '<div class="item-title-row">';
        html +=                 '<div class="item-title">'+json[x].gatewayName+'</div>';
        html +=             '</div>';
        html +=             '<div class="item-subtitle gateway-red">'+json[x].gatewayStatus+'</div>';
        html +=         '</div>';
        html += '<div style="float: right">';
        html +=     '<img src="resources/img/right_arrow.png" style="height: 40px;width: 40px;"/>';
        html += '</div>';
        html +=     '</a>';
        html += '</li>';
    }
    return html;
}

function createLockNode(gatewayCode){
    var html='';
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

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}