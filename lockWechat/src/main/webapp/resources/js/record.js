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
var deviceRecordsMap;
var recordSize;
var name;
var operatorRecordsMap;

/*
//改变输入框datetimepicker的显示,若有yyyy-MM-dd h:mm,则转化为yyyy-MM-dd hh:mm.
function formatDatetimepicker(datetimepicker){
    var timeStr=datetimepicker.val();
    var pattern = /(\d{4})-(\d{2})-(\d{2})\s(\d{1}):(\d{2})/;
       console.log(pattern.test(timeStr));
    var rep=timeStr;
    if(pattern.test(rep)){
        var reg=new RegExp(pattern);
        rep=rep.replace(reg,"$1-$2-$3 0$4:$5")
    }
    console.log(rep);
    var newDate=new Date(Date.parse(rep));
    console.log('newDate : '+newDate);
    var dateObj=formatDate2Object(newDate);
    datetimepicker.datetimePicker({
        value: [dateObj.year,dateObj.month,dateObj.date,dateObj.hour,dateObj.min]
    });
}
*/
/**
 * 格式化时间字符串,从yyyyMMddhhmmss到yyyy-MM-dd hh:mm:ss.
 * @param dateStr
 */
function formatTimeString(dateStr) {
    return dateStr.replace(/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/,'$1-$2-$3 $4:$5:$6');
}
function getDateSeg(date) {
    var dateObj={};
    dateObj.year=date.year();
    dateObj.month=date.month()+1;
    dateObj.date=date.date();
    dateObj.hour=date.hour();
    dateObj.minute=date.minute();
    dateObj.second=date.second();
    if(dateObj.month<10){
        dateObj.month='0'+dateObj.month;
    }
    if(dateObj.date<10){
        dateObj.date='0'+dateObj.date;
    }
    if(dateObj.hour<10){
        dateObj.hour='0'+dateObj.hour;
    }
    if(dateObj.minute<10){
        dateObj.minute='0'+dateObj.minute;
    }
    if(dateObj.second<10){
        dateObj.second='0'+dateObj.second;
    }
    return dateObj;
}
$(function(){
    // ownerPhoneNumber=13998892002;
    $('nav a').removeClass('active').eq(3).addClass('active');

    // moment().format('YYYY-MM-DD HH:mm:ss');
    // moment().set('date',moment().get('date')+1).set('hour',12).set('minute',0).set('second',0)
    var temptime=new Object();
    temptime=getDateSeg(moment().subtract(3,'years'));
    $("#datetime-picker-1").datetimePicker({
        dateFormat:'YYYY-MM-DD HH:mm',
        value: [temptime.year,temptime.month,temptime.date,temptime.hour,temptime.minute,temptime.second],
        // value: [moment().format('YYYY-MM-DD HH:mm')],
        // value: ['2018-03-01 03:09:10'],
        // value: ['2018','03','01','03',09,'10'],
        // formatValue: function (picker, value, displayValue){
        //     if(displayValue[1]<10){
        //         displayValue[1]='0'+displayValue[1];
        //     }
        //     console.log('picker:'+picker+', value:'+value+', displayValue:'+displayValue);
        // },
        // onChange:function(picker, values, displayValues){
        //     console.log('picker:'+picker+', value:'+value+', displayValue:'+displayValue);
        // }
        // min:'2016-01-01 1:00',
        // maxDate:new Date(),
        onClose:function (p) {
            timeInSec_start=moment($("#datetime-picker-1").val(),'YYYY-MM-DD HH:mm').valueOf();
        }
    });
    temptime=getDateSeg(moment());
    $("#datetime-picker-2").datetimePicker({
        value: [temptime.year,temptime.month,temptime.date,temptime.hour,temptime.minute,temptime.second],
        onClose:function (p) {
            timeInSec_end=moment($("#datetime-picker-2").val(),'YYYY-MM-DD HH:mm').valueOf();
        }
    });
    timeInSec_start=moment($("#datetime-picker-1").val(),'YYYY-MM-DD HH:mm').valueOf();
    timeInSec_end=moment($("#datetime-picker-2").val(),'YYYY-MM-DD HH:mm').valueOf();

    //创建MeScroll对象,内部已默认开启下拉刷新,自动执行up.callback,重置列表数据;
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
        var url="record/getUnlockRecordPage.action";
        //联网加载数据
        getPageData(url,{},page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            //联网成功的回调,隐藏下拉刷新和上拉加载的状态;
            //mescroll会根据传的参数,自动判断列表如果无任何数据,则提示空;列表无下一页数据,则提示无更多数据;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);

            //方法一(推荐): 后台接口有返回列表的总页数 totalPage
            //mescroll.endByPage(curPageData.length, totalPage); //必传参数(当前页的数据个数, 总页数)

            //方法二(推荐): 后台接口有返回列表的总数据量 totalSize
            mescroll.endBySize(curPageData.length, totalSize); //必传参数(当前页的数据个数, 总数据量)

            //方法三(推荐): 您有其他方式知道是否有下一页 hasNext
            //mescroll.endSuccess(curPageData.length, hasNext); //必传参数(当前页的数据个数, 是否有下一页true/false)

            //方法四 (不推荐),会存在一个小问题:比如列表共有20条数据,每页加载10条,共2页.如果只根据当前页的数据个数判断,则需翻到第三页才会知道无更多数据,如果传了hasNext,则翻到第二页即可显示无更多数据.
//                mescroll.endSuccess(curPageData.length);

            //设置列表数据,因为配置了emptyClearId,第一页会清空dataList的数据,所以renderPage应该写在最后;
            renderPage(curPageData);
        }, function(){
            //联网失败的回调,隐藏下拉刷新和上拉加载的状态;
            mescroll.endErr();
        });
    }

    //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
    document.ondragstart=function() {return false;};

    $.init();
});

function showPage(upCallback) {
    if (timeInSec_start>=timeInSec_end){
        $.toast('开始时间应当小于截止时间',1500);
        return null;
    }
    mescroll.destroy();
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
}

/*联网加载列表数据*/
function getPageData(url,params,pageNum,pageSize,successCallback,errorCallback) {
    params.startTime=timeInSec_start;
    params.endTime=timeInSec_end;
    params.pageNum=pageNum;
    params.pageSize=pageSize;
    $.ajax({
        type:"POST",
        url:url,
        // url:projectPath+"/record/getUnlockRecordPage.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        // data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        data:params,
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}

/*设置列表数据与渲染列表*//*
function renderPage(curPageData){
    var listDom=document.getElementById("dataList");
    for (var i = 0; i < curPageData.length; i++) {
        var pd=curPageData[i];
//            alert('curPageData['+i+'] : {gatewayCode:'+pd.gatewayCode+',lockCode'+pd.lockCode+'}');

        var str='<div class="pd">';
        str+='<div class="pd-left">';
        str+='<p class="pd-name"><img class="pd-img" src="resources/img/gateway_64px.png"/> '+'<span class="entry-val">'+pd.gatewayCode+'</span></p>';
        str+='<p><img src="resources/img/padlock_64px.png"/>'+'<span class="entry-val">'+pd.lockCode+'</span></p>';
        str+='</div>';
        str+='<div class="pd-right">';
        if(null !== pd.cardInfo && 'null'!==pd.cardInfo){
            str+='<p><img class="pd-img" src="resources/img/idCard_48px.png"/><span class="entry-val">'+pd.cardInfo.cardNumb+'</span></p>';
            str+='<p><img class="pd-img" src="resources/img/person_64px.png"/><span class="entry-val">'+pd.cardInfo.name+'</span></p>';
        }
        if(null !== pd.passwordInfo && 'null'!==pd.passwordInfo){
            str+='<p class="pd-unlock"><img class="pd-img" src="resources/img/password_64px.png"/>'+'<span class="entry-val">'+pd.passwordInfo.password+'</span></p>';
        }
        str+='<p><img class="pd-img" src="resources/img/time_64px.png"/><span class="entry-val">'+formatTimeString(pd.timetag)+'</span></p>';
        str+='</div>';
        str+='</div>';

        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}*/
function renderPage(curPageData){
    var listDom=document.getElementById("dataList");
    for (var i = 0; i < curPageData.length; i++) {
        var pd=curPageData[i];

        str='<div style="width: 340px;">';
        if(null!==pd.roomName && 'null'!==pd.roomName){
            str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xeb59;</i><b style="width: 260px;padding-left: 10px">'+pd.roomName+'</b></span>';
        }else{
            str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xeb59;</i><b style="width: 260px;padding-left: 10px">'+pd.gatewayCode+'</b></span>';
            str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xe61c;</i><b style="width: 260px;padding-left: 10px">'+pd.lockCode+'</b></span>';
        }
        if(null!==pd.cardInfo && 'null'!==pd.cardInfo){
            str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xe678;</i><b style="width: 260px;padding-left: 10px">'+pd.cardInfo.name+'</b></span>';
            str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xe687;</i><b style="width: 260px;padding-left: 10px">'+pd.cardInfo.cardNumb+'</b></span>';
        }
        if(null!==pd.passwordInfo && 'null'!==pd.passwordInfo){
            str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xe675;</i><b style="width: 260px;padding-left: 10px">'+pd.passwordInfo.password+'</b></span>';
        }
        str+=   '<span style="width: 300px;padding-left: 40px;"><i class="iconfont">&#xe616;</i><b style="width: 260px;padding-left: 10px">'+formatTimeString(pd.timetag)+'</b></span>';
        str+='</div>';

        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}

function showAllRecords() {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getUnlockRecordPage.action";
        getPageData(url,{},page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            renderPage(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}

/*根据开锁记录展示网关和门锁的设备信息*/
function showDevicesFromRecords() {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        // url:projectPath+"/record/getUnlockRecordDevice.action",
        var url="record/getUnlockRecordDevice.action";
        getPageData(url,{},page.num, page.size, function(data){
            /*
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            showDeviceWithRecord(curPageData);
            */
            console.log('length : '+Object.keys(data).length);
            mescroll.setPageSize(Object.keys(data).length+1);
            mescroll.endSuccess(Object.keys(data).length);
            setDeviceWithRecord(data);
        }, function(){
            mescroll.endErr();
        });
    }
}
function setDeviceWithRecord(data){
    deviceRecordsMap=data;
    var listDom=document.getElementById("dataList");
    var mapLength=Object.keys(data).length;
    for(var gatewayNum in data) {
        lockRecordsMap=data[gatewayNum].data;
        recordSize=data[gatewayNum].size;
        /*
        gatewayNum="'"+gatewayNum+"'";
        var str='';
        str+='<div class="row-header">';
        str+='<a href="javascript:void(0);" onclick="shiftExpand('+gatewayNum+',$(this))"><img alt="arrow-triangle" src="resources/img/arrow-triangle_64px.png" /></a>';
        str+='<a class="a-device" href="javascript:void(0);" onclick="showGatewayRecords('+gatewayNum+')" style="width: 280px;margin-left: 30px;">';
        str+='<img alt="gateway" src="resources/img/gateway_64px.png"/>';
        str+='<span style="width: 230px;margin-left: 20px;">'+gatewayNum.substring(1,gatewayNum.length-1);
        str+='</span>';
        str+='</a>';
        str+='</div>';
        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
         */
        var str='';
        str+='<div class="row-header">';
        // str+=   '<a href="javascript:void(0);" onclick="shiftExpand(\''+gatewayNum+'\',$(this))"><img alt="arrow-triangle" src="resources/img/arrow-triangle_64px.png" /></a>';
        str+=   '<a href="javascript:void(0);" onclick="shiftExpand(\''+gatewayNum+'\',$(this))"><i class="iconfont">&#xe618;</i></a>';
        str+=   '<a class="a-device" href="javascript:void(0);" onclick="showGatewayRecords(\''+gatewayNum+'\')" style="width: 300px;">';
        // str+=       '<img alt="gateway" src="resources/img/gateway_64px.png"/>';
        // str+=       '<span style="width: 180px;margin-left: 20px;">'+gatewayNum+'</span>';
        // str+=       '<span style="width: 30px;margin-left: 20px;">'+recordSize+'</span>';
        str+=       '<span style="width: 200px;padding-left: 20px;"><i class="iconfont">&#xeb59;</i><b style="width: 160px;padding-left: 10px">'+gatewayNum+'</b></span>';
        str+=       '<span style="width: 100px;padding-left: 20px;"><i class="iconfont">&#xe6be;</i><b style="width: 80px;padding-left: 10px">'+recordSize+'</b></span>';
        str+=   '</a>';
        str+='</div>';
        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}
/*切换显示门锁下拉列表*/
function shiftExpand(gatewayNum,element) {
    var LI=$(element.parent()[0]).parent()[0];
    var DIV_row_expand=$(LI).children(".row-expand");
    //.row-expand存在,删除.row-expand元素并使图标不旋转.
    if (undefined!=DIV_row_expand && DIV_row_expand.size()!==0){
        // element.children("img")[0].style.transform= "rotate(0deg)";
        element.children('i').html('&#xe618;');
        DIV_row_expand.remove();
        return null;
    }
    //.row-expand不存在,扩展.row-expand元素并使图标旋转.
    // element.children("img")[0].style.transform= "rotate(180deg)";
    element.children('i').html('&#xe6aa;');
    lockRecordsMap=deviceRecordsMap[gatewayNum].data;
    var str='';
    str+='<div class="row-expand" >';
    str+='<div class="expand-right" style="float:left;margin-left: 18px">';
    str+='<ul>';
    for(var lockCode in lockRecordsMap) {
        unlockRecordList=lockRecordsMap[lockCode].data;
        recordSize=lockRecordsMap[lockCode].size;
        str+='<li style="width: 300px;">';
        str+=   '<div class="row-line">';
        str+=       '<a class="a-device" href="javascript:void(0);" onclick="showLockRecords(\''+lockCode+'\')">';
        // str+=           '<span style="width: 180px;margin-left: 20px;">'+lockCode+'</span>';
        // str+=           '<span style="width: 30px;margin-left: 20px;">'+recordSize+'</span>';
        str+=       '<span style="width: 200px;padding-left: 20px;"><i class="iconfont">&#xe61c;</i><b style="width: 160px;padding-left: 10px">'+lockCode+'</b></span>';
        str+=       '<span style="width: 80px;padding-left: 20px;"><i class="iconfont">&#xe6be;</i><b style="width: 60px;padding-left: 10px">'+recordSize+'</b></span>';
        str+=       '</a>';
        str+=   '</div>';
        str+='</li>';
    }
    str+='</ul>';
    str+='</div>';
    str+='</div>';
    LI.innerHTML+=str;
}

/*按网关展示记录*/
function showGatewayRecords(gatewayNum) {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getGatewayUnlockRecordPage.action";
        getPageData(url,{gatewayCode:gatewayNum},page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            renderPage(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}

/*按门锁展示记录*/
function showLockRecords(lockNum) {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getLockUnlockRecordPage.action";
        getPageData(url,{lockCode:lockNum},page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            renderPage(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}

/*根据开锁记录展示开锁人信息*/
function showOperatorFromRecords() {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getUnlockOperator.action";
        getPageData(url,{},page.num, page.size, function(data){
            console.log('length : '+Object.keys(data).length);
            mescroll.setPageSize(Object.keys(data).length+1);
            mescroll.endSuccess(Object.keys(data).length);
            setOperatorWithRecord(data);
        }, function(){
            mescroll.endErr();
        });
    }
}
function setOperatorWithRecord(data) {
    operatorRecordsMap=data;
    var listDom=document.getElementById("dataList");
    for(var cardNum in data) {
        lockRecordsMap=data[cardNum].data;
        recordSize=data[cardNum].size;
        name=data[cardNum].note;
        var str='';
        str+='<div>';
        str+=    '<a class="a-id" href="javascript:void(0);" onclick="showOperatorRecords(\''+cardNum+'\')" style="width: 340px;">';
        // str+=       '<img alt="idCard" src="resources/img/idCard_48px.png">';
        // str+=       '<span style="width: 55px;margin-left: 5px;">'+name+'</span>';
        // str+=       '<img alt="idCard" src="resources/img/person_64px.png">';
        // str+=       '<span style="width: 145px;margin-left: 5px;">'+cardNum+'</span>';
        // str+=       '<span style="width: 65px;margin-left: 5px;">'+recordSize+'</span>';
        str+=       '<span style="width: 300px;padding-left: 20px;"><i class="iconfont">&#xe678;</i><b style="width: 80px;padding-left: 10px">'+name+'</b></span>';
        str+=       '<span style="width: 300px;padding-left: 20px;"><i class="iconfont">&#xe687;</i><b style="width: 80px;padding-left: 10px">'+cardNum+'</b></span>';
        str+=       '<span style="width: 300px;padding-left: 20px;"><i class="iconfont">&#xe6be;</i><b style="width: 80px;padding-left: 10px">'+recordSize+'</b></span>';
        str+=    '</a>';
        str+='</div>';
        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}

/*按开锁人展示记录*/
function showOperatorRecords(cardNum) {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getOperatorUnlockRecordPage.action";
        getPageData(url,{cardNum:cardNum},page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            renderPage(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}

/*根据开锁记录展示开锁的房间信息*/
function showRoomFromRecords() {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getRecordRoom.action";
        getPageData(url,{ownerPhoneNumber:ownerPhoneNumber},page.num, page.size, function(data){
            console.log('length : '+Object.keys(data).length);
            mescroll.setPageSize(Object.keys(data).length+1);
            mescroll.endSuccess(Object.keys(data).length);
            setRoomWithRecord(data);
        }, function(){
            mescroll.endErr();
        });
    }
}
function setRoomWithRecord(data) {
    var listDom=document.getElementById("dataList");
    for(var roomId in data) {
        roomRecordsMap=data[roomId].data;
        recordSize=data[roomId].size;
        roomName=data[roomId].note;
        var str='';
        str+='<div>';
        str+=    '<a class="a-id" href="javascript:void(0);" onclick="showRoomRecords(\''+roomId+'\')" style="width: 340px;">';
        // str+='<i class="iconfont">&#xe7ff;</i>';
        str+=       '<span style="width: 150px;padding-left: 20px;"><i class="iconfont">&#xe7ff;</i><b style="width: 80px;padding-left: 10px">'+roomName+'</b></span>';
        // str+='<span class="iconfont">&#xe608;</span>';
        str+=       '<span style="width: 150px;padding-left: 20px;"><i class="iconfont">&#xe6be;</i><b style="width: 80px;padding-left: 10px">'+recordSize+'</b></span>';
        str+=    '</a>';
        str+='</div>';
        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}

/*按开锁人展示记录*/
function showRoomRecords(roomId) {
    showPage(upCallback);

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        var url="record/getRoomRecordPage.action";
        getPageData(url,{roomId:roomId,ownerPhoneNumber:ownerPhoneNumber},page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            renderPage(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}
