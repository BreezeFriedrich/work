/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var device;
var ownerPhoneNumber;
var url;
$(function(){
    $('nav a').removeClass('active').eq(0).addClass('active');
    $.ajax({
        type:"POST",
        url:projectPath+"/account/getUserFromSession.action",
        async:false,
        // data:{},
        dataType:'json',
        success:function(data,status,xhr){
            ownerPhoneNumber=data.wechatUser.phonenumber;
        },
        error:function(xhr,errorType,error){
            console.log('错误');
            console.log(xhr);
            console.log(errorType);
            console.log(error)
        }
    });
    if(null==ownerPhoneNumber){
        ownerPhoneNumber = document.getElementById("INPUT_hidden").value;
    }
    if(undefined==ownerPhoneNumber || ''==ownerPhoneNumber){
        ownerPhoneNumber=getQueryString('ownerPhoneNumber');
    }
    // ownerPhoneNumber=18255683932;

    $('.my-iphone').html(ownerPhoneNumber);

    $.showIndicator();
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getDeviceInfo.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // headers:{"Access-Control-Allow-Origin":"*"},
        // data:{"ownerPhoneNumber":ownerPhoneNumber},
        // timeout:3000,
        dataType:'json',
        success:function(data,status,xhr){
            $.hideIndicator();
            device = data;
            /*
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
            device=eval(jsonStr);
            */
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
    $('.panel-left ul li').eq(0).click(function () {
        url="jsp/room/roomStatus.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    $('.panel-left ul li').eq(1).click(function () {
        url="jsp/gateway/gateway_addGateway.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    $('.panel-left ul li').eq(2).click(function () {
        url="jsp/record/record.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    $('.panel-left ul li').eq(3).click(function () {
        url="jsp/alert.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });
    $('.panel-left ul li').eq(4).click(function () {
        url="jsp/setting.jsp?ownerPhoneNumber="+ownerPhoneNumber;
        window.location.href=encodeURI(url);
    });

    initAlertBadge();

    $.init();
});

function initAlertBadge() {
    $.ajax({
        type:"POST",
        url:projectPath+"/device/countAbnormalDevice.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber},
        // data:{},
        dataType:'json',
        success:function(data,status,xhr){
            $("sup").remove();
            if(null!=data & data>0){
                var el=$("nav a").eq(4).find("span:last");
                var html="<sup>"+data+"</sup>";
                el.append(html);
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
        }
    });
}

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
        var lockCode=$el.attr('id');
        var gatewayCode=$el.siblings().eq(0).attr('id');
        url="jsp/lock/lock_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode+"&specificLockCode="+lockCode;
        window.location.href=encodeURI(url);
        return false;
    });
}
function createGatewayNode(){
    var html='';
    for(x in device) {
        if ('4' === device[x].gatewayStatus) {
            device[x].gatewayStatus = "正常"
        }
        if ('5' === device[x].gatewayStatus) {
            device[x].gatewayStatus = "异常"
        }
        if ('6' === device[x].gatewayStatus) {
            device[x].gatewayStatus = "连接失败"
        }
        html += '<li>';
        html +=     '<a id="'+device[x].gatewayCode+'" href="javascript:void(0);" class="gateway item-content item-gateway gateway-linegreen">';
        html +=         '<div class="item-media"><img src="resources/images/inco-gateway.png" width="44"></div>';
        html +=         '<div class="item-inner">';
        html +=             '<div class="item-title-row">';
        html +=                 '<div class="item-title">'+device[x].gatewayName+'</div>';
        html +=             '</div>';
        html +=             '<div class="item-subtitle gateway-red">'+device[x].gatewayStatus+'</div>';
        html +=         '</div>';
        html +=         '<div style="float: right">';
        // html +=             '<img src="resources/img/right_arrow.png" style="height: 40px;width: 40px;"/>';
        html +=             '<img src="resources/img/link.png" style="height: 40px;width: 40px;"/>';
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

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}