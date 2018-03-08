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
var ul_authInfo;

$(function(){
    FastClick.attach(document.body);

    ownerPhoneNumber=getQueryString("ownerPhoneNumber");
    specificGatewayCode=getQueryString("specificGatewayCode");
    specificLockCode=getQueryString("specificLockCode");
    $.ajax({
        type:"POST",
        url:projectPath+"/lock/getSpecificLock.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        data:{"gatewayCode":specificGatewayCode,"lockCode":specificLockCode},
        dataType:'json',
        success:function(data,status,xhr){
            json_theLock = data;
        },
        error:function(xhr,errorType,error){
            console.log('错误')
        }
    });
    if(undefined!==json_theLock){
        document.getElementsByClassName('property')[0].innerText=json_theLock.lockName;
        document.getElementsByClassName('property')[1].innerText=json_theLock.lockLocation;
        document.getElementsByClassName('property')[2].innerText=json_theLock.lockComment;
        document.getElementsByClassName('property')[3].innerText=specificLockCode;
        document.getElementsByClassName('property')[4].innerText=specificGatewayCode;
        // document.getElementsByTagName('input')[0].setAttribute('placeholder',json_theLock.lockName);
    }

    //添加开锁身份证授权
    var div_addAuthById=document.getElementById("link_addAuthById");
    div_addAuthById.addEventListener('click',function(ev){
        // var target = ev.target || window.event.srcElement;
        url="jsp/unlock/unlock_authById.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
        window.location.href=encodeURI(url);
    });
    /*
    var div_addAuthById=document.getElementById("link_addAuthById");
    div_addAuthById.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== div_addAuthById){
            if(target.getAttribute('class')==='item-inner'){
                url="jsp/unlock/unlock_authById.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
                window.location.href=encodeURI(url);
            }
            target = target.parentNode;
        }
    });
    */

    //添加开锁密码授权
    var div_addAuthById=document.getElementById("link_addAuthByPwd");
    div_addAuthById.addEventListener('click',function(ev){
        // var target = ev.target || window.event.srcElement;
        url="jsp/unlock/unlock_authByPwd.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
        window.location.href=encodeURI(url);
    });
    /*
    var div_addAuthById=document.getElementById("link_addAuthByPwd");
    div_addAuthById.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== div_addAuthById){
            if(target.getAttribute('class')==='item-inner'){
                url="jsp/unlock/unlock_authByPwd.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&gatewayCode="+specificGatewayCode+"&lockCode="+specificLockCode;
                window.location.href=encodeURI(url);
            }
            target = target.parentNode;
        }
    });
    */

    //取消开锁授权
    prohibitUnlockAuth();

    $.init();
});

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
function prohibitUnlockAuth() {
    var div_authInfo=document.getElementById('div_authInfo');
    div_authInfo.innerHTML = getAuthInfo();

    div_authInfo.addEventListener('click',function (ev) {
        var target = ev.target || window.event.srcElement;
        while(target !== div_authInfo){
            if(target.getAttribute('class')==='ul_authById'){
                $.confirm('删除该项开锁授权',
                    function () {
                        $.ajax({
                            type:"POST",
                            url:projectPath+"/unlock/prohibitUnlockById.action",
                            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                            // data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":specificLockCode,"cardNumb":target.getAttribute('id').split('-')[0],"serviceNumb":target.getAttribute('id').split('-')[1]},
                            data:{"lockCode":specificLockCode,"cardNumb":target.getAttribute('id').split('-')[0],"serviceNumb":target.getAttribute('id').split('-')[1]},
                            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text

                            success:function(data,status,xhr){
                                $.toast('操作成功,正在刷新页面...',1500);
                                window.setTimeout("window.location.reload();",2000);
                            },
                            error:function(xhr,errorType,error){
                                console.log('错误');
                            }
                        });
                });
                break;
            }
            if(target.getAttribute('class')==='ul_authByPwd'){
                $.confirm('删除该项开锁授权',
                    function () {
                        $.ajax({
                            type:"POST",
                            url:projectPath+"/unlock/prohibitUnlockByPwd.action",
                            async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
                            // data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":specificGatewayCode,"lockCode":specificLockCode,"serviceNumb":target.getAttribute('id')},
                            data:{"gatewayCode":specificGatewayCode,"lockCode":specificLockCode,"serviceNumb":target.getAttribute('id')},
                            dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text

                            success:function(data,status,xhr){
                                $.toast('操作成功,正在刷新页面...',1500);
                                window.setTimeout("window.location.reload();",2000);
                            },
                            error:function(xhr,errorType,error){
                                console.log('错误');
                            }
                        });
                });
                break;
            }
            target=target.parentNode;
        }
    });
}
function getAuthInfo() {
    ul_authInfo='';
    $.ajax({
        type:"POST",
        url:projectPath+"/unlock/getUnlockId.action",
        async:false,
        data:{
            // "ownerPhoneNumber":ownerPhoneNumber,
            "gatewayCode":specificGatewayCode,
            "lockCode":specificLockCode
        },
        dataType:'json',
        success:function(data,status,xhr){
            if(null!=data){
                if(data.length>0){
                    ul_authInfo += "<div class='content-block-title'>已授权开锁身份证</div>";
                }
                for(x in data){
                    ul_authInfo += "<div class='list-block'>";
                    ul_authInfo += "<ul class='ul_authById' id='"+data[x].cardNumb+'-'+data[x].serviceNumb+"'>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>姓名</div>";
                    ul_authInfo +=        "<div class='item-after'>"+data[x].name+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>身份证号码</div>";
                    ul_authInfo +=        "<div class='item-after'>"+data[x].cardNumb+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>授权起始时间</div>";
                    ul_authInfo +=        "<div class='item-after'>"+formatTimetillminString(data[x].startTime)+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>授权结束时间</div>";
                    ul_authInfo +=        "<div class='item-after'>"+formatTimetillminString(data[x].endTime)+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "</ul>";
                    ul_authInfo += "</div>";
                }
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
        async:false,
        data:{
            // "ownerPhoneNumber":ownerPhoneNumber,
            "gatewayCode":specificGatewayCode,
            "lockCode":specificLockCode
        },
        dataType:'json',
        success:function(data,status,xhr){
            if(null!=data){
                if('null'!=data.defaultPassword1 || 'null'!=data.defaultPassword2){
                    ul_authInfo += "<div class='content-block-title'>默认密码</div>";
                    ul_authInfo += "<div class='list-block'>";
                    ul_authInfo += "<ul>";
                    if('null'!=data.defaultPassword1){
                        ul_authInfo += "<li class='item-content'>";
                        ul_authInfo +=    "<div class='item-inner'>";
                        ul_authInfo +=        "<div class='item-title'>默认密码1</div>";
                        ul_authInfo +=        "<div class='item-after'>"+data.defaultPassword1+"</div>";
                        ul_authInfo +=    "</div>";
                        ul_authInfo += "</li>";
                    }
                    if('null'!=data.defaultPassword2){
                        ul_authInfo += "<li class='item-content'>";
                        ul_authInfo +=    "<div class='item-inner'>";
                        ul_authInfo +=        "<div class='item-title'>默认密码2</div>";
                        ul_authInfo +=        "<div class='item-after'>"+data.defaultPassword2+"</div>";
                        ul_authInfo +=    "</div>";
                        ul_authInfo += "</li>";
                    }
                    ul_authInfo += "</ul>";
                    ul_authInfo += "</div>";
                }
                var pwdList=data.passwordList;
                if(pwdList.length>0){
                    ul_authInfo += "<div class='content-block-title'>已授权开锁密码</div>";
                }
                for(x in pwdList){
                    ul_authInfo += "<div class='list-block'>";
                    ul_authInfo += "<ul class='ul_authByPwd' id='"+pwdList[x].serviceNumb+"'>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>密码</div>";
                    ul_authInfo +=        "<div class='item-after'>"+pwdList[x].password+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>授权起始时间</div>";
                    ul_authInfo +=        "<div class='item-after'>"+formatTimetillminString(pwdList[x].startTime)+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "<li class='item-content'>";
                    ul_authInfo +=    "<div class='item-inner'>";
                    ul_authInfo +=        "<div class='item-title'>授权结束时间</div>";
                    ul_authInfo +=        "<div class='item-after'>"+formatTimetillminString(pwdList[x].endTime)+"</div>";
                    ul_authInfo +=    "</div>";
                    ul_authInfo += "</li>";
                    ul_authInfo += "</ul>";
                    ul_authInfo += "</div>";
                }
            }
        },
        error:function(xhr,errorType,error){
            console.log('错误');
            $.toast('获取开锁授权信息失败');
        }
    });
    return ul_authInfo;
}