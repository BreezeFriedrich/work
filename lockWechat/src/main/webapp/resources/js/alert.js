/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var ownerPhoneNumber;
var startTime;
var endTime;
var timeInSec_start;
var timeInSec_end;
var mescroll;
var gatewayCode;
var lockCode;
var json;

$(function () {
    ownerPhoneNumber="17705155208";

    mescroll = new MeScroll("mescroll", {
        //上拉加载列表项.
        up: {
            clearEmptyId: "dataList", //1.下拉刷新时会自动先清空此列表,再加入数据; 2.无任何数据时会在此列表自动提示空
            callback: upCallback, //上拉加载的回调,此处可简写; 相当于 callback: function (page) { getListData(page); }
            toTop:{ //配置回到顶部按钮
                src : "resources/img/mescroll-totop.png", //默认滚动到1000px显示,可配置offset修改
                //offset : 1000
            },
            empty:{
                tip: "我也是有底线的~",
            }
        }
    });

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        getAbnormalDevice(page.num, page.size, function(data){
            /*
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            showDeviceWithRecord(curPageData);
            */
            mescroll.setPageSize(data.length+1);
            mescroll.endSuccess(data.length);
            setAbnormalDevice(data);
        }, function(){
            mescroll.endErr();
        });
    }

    //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
    document.ondragstart=function() {return false;};

    $.init();
});
function getAbnormalDevice(pageNum,pageSize,successCallback,errorCallback) {
    $.ajax({
        type:"POST",
        url:projectPath+"/device/getAbnormalDevice.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{"ownerPhoneNumber":ownerPhoneNumber,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}
function setAbnormalDevice(data){
    /*
    // 函数自执行的方式避免i变量被保留.
    (function() {
        for(var i=0, len=data.length; i<len; i++) {
            data.
            if (i == 2) {
                // return;   // 函数执行被终止
                // break;    // 循环被终止
                continue; // 循环被跳过
            };
            console.log('data['+ i +']:' + data[i]);
        }
    })();

    deviceRecordsMap=data;
    console.log('data: '+data);
    var listDom=document.getElementById("dataList");
    var mapLength=Object.keys(data).length;
    for(var gatewayNum in data) {
        lockRecordsMap=data[gatewayNum].data;
        recordSize=data[gatewayNum].size;
        var str='';
        str+='<div class="row-header">';
        str+=   '<a href="javascript:void(0);" onclick="shiftExpand(\''+gatewayNum+'\',$(this))"><img alt="arrow-triangle" src="resources/img/arrow-triangle_64px.png" /></a>';
        str+=   '<a class="a-device" href="javascript:void(0);" onclick="showGatewayRecords(\''+gatewayNum+'\')" style="width: 280px;margin-left: 30px;">';
        str+=       '<img alt="gateway" src="resources/img/gateway_64px.png"/>';
        str+=       '<span style="width: 180px;margin-left: 20px;">'+gatewayNum+'</span>';
        str+=       '<span style="width: 30px;margin-left: 20px;">'+recordSize+'</span>';
        str+=   '</a>';
        str+='</div>';
        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
    */

    /*
    json=data;
    UL_gateway=document.getElementById('dataList');
    var UL_lockList=document.createElement('ul');
    UL_lockList.id="UL_lockList";
    UL_lockList.style.paddingLeft='0';
    UL_lockList.style.marginTop='0.5rem';
    UL_lockList.style.background='rgba(0,0,0,0.3)';

    UL_gateway.innerHTML = createGatewayNode();
    //事件代理
    UL_gateway.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== UL_gateway){
            if(target.getAttribute('class')==='card-content' && target.parentNode.className==='card gateway'){
                //href内联页面,网关操作
                //页面跳转并传递参数
                var gatewayCode=target.parentNode.id;
                url="jsp/gateway/gateway_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode;
                window.location.href=encodeURI(url);
                break;
            }
            if(target.className==='card-footer' && target.parentNode.className==='card gateway'){
                //向下扩展DOM,门锁card
                selectedGateway=target.parentNode;
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
    */
    json=data;
    UL_gateway=document.getElementById('dataList');
    var UL_lockList=document.createElement('ul');
    UL_lockList.id="UL_lockList";
    UL_lockList.style.paddingLeft='0';
    UL_lockList.style.marginTop='0.5rem';
    UL_lockList.style.background='rgba(0,0,0,0.3)';

    var liDom=document.createElement("li");
    liDom.innerHTML=createLiDom();
    UL_gateway.appendChild(liDom);
    //事件代理
    UL_gateway.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== UL_gateway){
            if(target.getAttribute('class')==='card-content' && target.parentNode.className==='card gateway'){
                //href内联页面,网关操作
                //页面跳转并传递参数
                var gatewayCode=target.parentNode.id;
                url="jsp/gateway/gateway_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode;
                window.location.href=encodeURI(url);
                break;
            }
            if(target.className==='card-footer' && target.parentNode.className==='card gateway'){
                //向下扩展DOM,门锁card
                selectedGateway=target.parentNode;
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

function createLiDom(){
    var LI_gateway="";
    for(x in json){
        if('4'===json[x].gatewayStatus){json[x].gatewayStatus="正常"}
        if('5'===json[x].gatewayStatus){json[x].gatewayStatus="异常"}
        if('6'===json[x].gatewayStatus){json[x].gatewayStatus="连接失败"}

        LI_gateway += "<div class='card gateway' id='"+json[x].gatewayCode+"' style='border: 0.3rem outset rgba(100,100,0,0.5);'>";
        LI_gateway += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+json[x].gatewayName+"</div>";
        LI_gateway += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
        LI_gateway += 		"<div class='card-content-inner'>";
        // LI_gateway += 			"<img class='auto-zoom-4' src='${pageContext.request.contextPath}/resources/img/gateway.png' />";
        LI_gateway += 			"<img class='auto-zoom-4' src='resources/img/gateway.png' />";
        LI_gateway += 		"</div>";
        LI_gateway += 	"</div>";
        LI_gateway += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
        LI_gateway += 		"<p style='color: #00B83F;'>"+json[x].gatewayStatus+"</p><a href='#' class='icon icon-down'></a>";
        LI_gateway += 	"</div>";
        LI_gateway += "</div>"+"<br/>";
    }
    return LI_gateway;
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
        // LI_gateway += 			"<img class='auto-zoom-4' src='${pageContext.request.contextPath}/resources/img/gateway.png' />";
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