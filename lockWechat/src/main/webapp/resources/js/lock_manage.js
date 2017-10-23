/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var specificGatewayCode;
var specificLockCode;
var json_theLock;

$(function(){
    // FastClick.attach(document.body);

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    specificLockCode=getQueryString("specificLockCode");
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/getSpecificLock.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        dataType:'json',
        success:function(data,status,xhr){
            json_theLock = data;
        },
        error:function(xhr,errorType,error){
            console.log('错误')
        }
    });
    document.getElementsByClassName('property')[0].innerText=json_theLock.lockName;
    document.getElementsByClassName('property')[1].innerText=json_theLock.lockLocation;
    document.getElementsByClassName('property')[2].innerText=json_theLock.lockComment;
    document.getElementsByClassName('property')[3].innerText=specificLockCode;
    document.getElementsByClassName('property')[4].innerText=specificGatewayCode;
    // document.getElementsByTagName('input')[0].setAttribute('placeholder',json_theLock.lockName);

    //添加关联门锁
    var div_addAuth=document.getElementById("link_addAuthById");
    div_addAuth.addEventListener('click',function(ev){
        // var target = ev.target || window.event.srcElement;
        url="jsp/unlock/unlock_authById.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
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

function getAuthInfo() {
    $.ajax({
        type:"POST",
        url:projectPath+"/unlock/getUnlockId.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{
            "ownerPhoneNumber":ownerPhoneNumber,
            "gatewayCode":gatewayCode,
            "lockCode":lockCode
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            ajaxResult = data;
            var UL_authInfo=document.getElementById('UL_authInfo');
            for(x in data){
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>姓名</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.name+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>身份证号码</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.cardNumb+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>授权起始时间</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.startTime+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>授权结束时间</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.endTime+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
            $.toast('获取开锁授权信息失败');
        }
    });
    $.ajax({
        type:"POST",
        url:projectPath+"/unlock/getUnlockPwd.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{
            "ownerPhoneNumber":ownerPhoneNumber,
            "gatewayCode":gatewayCode,
            "lockCode":lockCode
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            ajaxResult = data;
            var pwdList=data.passwordList;
            var UL_authInfo=document.getElementById('UL_authInfo');
            if('null'!=data.defaultPassword1){

            }
            for(x in data){
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>姓名</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.name+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>身份证号码</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.cardNumb+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>授权起始时间</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.startTime+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
                UL_authInfo.innerHTML += "<li class='item-content'>";
                UL_authInfo.innerHTML +=    "<div class='item-inner'>";
                UL_authInfo.innerHTML +=        "<div class='item-title'>授权结束时间</div>";
                UL_authInfo.innerHTML +=        "<div class='item-after'>"+data.endTime+"</div>";
                UL_authInfo.innerHTML +=    "</div>";
                UL_authInfo.innerHTML += "</li>";
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
            $.toast('获取开锁授权信息失败');
        }
    });
}
/*
"
<li class='item-content'>
    <div class='item-inner'>
        <div class='item-title'>姓名</div>
        <div class='item-after'>李克用</div>
    </div>
</li>
<li class='item-content'>
    <div class='item-inner'>
        <div class='item-title'>身份证号码</div>
        <div class='item-after'>340987993883</div>
    </div>
</li>
<li class='item-content'>
    <div class='item-inner'>
        <div class='item-title'>授权起始时间</div>
        <div class='item-after'>2017-11-15 09:01:20</div>
    </div>
</li>
<li class='item-content'>
    <div class='item-inner'>
        <div class='item-title'>授权结束时间</div>
        <div class='item-after'>2017-11-16 09:01:20</div>
    </div>
</li>"
*/