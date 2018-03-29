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
        //联网加载数据
        getListDataFromNet(page.num, page.size, function(data){
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

            //设置列表数据,因为配置了emptyClearId,第一页会清空dataList的数据,所以setListData应该写在最后;
            setListData(curPageData);
        }, function(){
            //联网失败的回调,隐藏下拉刷新和上拉加载的状态;
            mescroll.endErr();
        });
    }

    //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
    document.ondragstart=function() {return false;};

    $.init();
});

function showAllRecords() {
    /*
    startTime="2014-01-01 01:01";
    endTime="2017-12-10 01:01";
    startTime=$("#datetime-picker-1").val();
    endTime=$("#datetime-picker-2").val();
    if (timeStr1BigThantimeStr2(formatTimetillminString2(endTime),formatTimetillminString2(startTime))){
        $.toast('开始时间不能大于截止时间',1500);
        return null;
    }
    */
    // timeInSec_start=getTimeFromDatetimepicker($("#datetime-picker-1"));
    // timeInSec_end=getTimeFromDatetimepicker($("#datetime-picker-2"));
    if (timeInSec_start>=timeInSec_end){
        $.toast('开始时间应当小于截止时间',1500);
        return null;
    }
    mescroll.destroy();
    // setTimeout(console.log('delay...'),5000);
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
        getListDataFromNet(page.num, page.size, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            setListData(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}

/*设置列表数据与渲染列表*/
function setListData(curPageData){
    var listDom=document.getElementById("dataList");
    for (var i = 0; i < curPageData.length; i++) {
        var pd=curPageData[i];
//            alert('curPageData['+i+'] : {gatewayCode:'+pd.gatewayCode+',lockCode'+pd.lockCode+'}');

        var str='<div class="pd">';
        str+='<div class="pd-left">';
        str+='<p class="pd-name"><img class="pd-img" src="resources/img/gateway_64px.png"/> '+'<span class="entry-val">'+pd.gatewayCode+'</span></p>';
        str+='<p><img src="resources/img/padlock_64px.png"/> '+'<span class="entry-val">'+pd.lockCode+'</span></p>';
        str+='</div>';
        str+='<div class="pd-right">';
        console.log(pd.cardInfo);
        if(null !== pd.cardInfo && 'null'!==pd.cardInfo){
            str+='<p><img class="pd-img" src="resources/img/idCard_48px.png"/> <span class="entry-val">'+pd.cardInfo.cardNumb+'</span></p>';
            str+='<p><img class="pd-img" src="resources/img/person_64px.png"/> <span class="entry-val">'+pd.cardInfo.name+'</span></p>';
        }
        if(null !== pd.passwordInfo && 'null'!==pd.passwordInfo){
            str+='<p class="pd-unlock"><img class="pd-img" src="resources/img/password_64px.png"/> '+'<span class="entry-val">'+pd.passwordInfo.password+'</span></p>';
        }
        str+='<p><img class="pd-img" src="resources/img/time_64px.png"/> <span class="entry-val">'+formatTimeString(pd.timetag)+'</span></p>';
        str+='</div>';
        str+='</div>';

        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}

/*联网加载列表数据*/
function getListDataFromNet(pageNum,pageSize,successCallback,errorCallback) {
    //ownerPhoneNumber,startTime,endTime
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getUnlockRecordPage.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}

/*根据开锁记录展示网关和门锁的设备信息*/
function showDevicesFromRecords() {
    // timeInSec_start=getTimeFromDatetimepicker($("#datetime-picker-1"));
    // timeInSec_end=getTimeFromDatetimepicker($("#datetime-picker-2"));
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

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        getUnlockDevice(page.num, page.size, function(data){
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
function getUnlockDevice(pageNum,pageSize,successCallback,errorCallback) {
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getUnlockRecordDevice.action",
        async:false,
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}
function setDeviceWithRecord(data){
    deviceRecordsMap=data;
    console.log('data: '+data);
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
}

/*切换显示门锁下拉列表*/
function shiftExpand(gatewayNum,element) {
    var LI=$(element.parent()[0]).parent()[0];
    var DIV_row_expand=$(LI).children(".row-expand");
    //.row-expand存在,删除.row-expand元素并使图标不旋转.
    if (undefined!=DIV_row_expand && DIV_row_expand.size()!==0){
        element.children("img")[0].style.transform= "rotate(0deg)";
        DIV_row_expand.remove();
        return null;
    }
    //.row-expand不存在,扩展.row-expand元素并使图标旋转.
    element.children("img")[0].style.transform= "rotate(180deg)";
    lockRecordsMap=deviceRecordsMap[gatewayNum].data;
    var str='';
    // var lockHeight=Object.keys(lockRecordsMap).length*75;
    // var str='<div class="row-expand" style="height:'+lockHeight+'px;">';
    str+='<div class="row-expand" >';
//        str+='<div class="expand-left" style="width: 100px;float:left;">';
//        str+="<a href='javascript:void(0);' onclick='deleteExpandLock($(this))'><img alt='arrow-triangle' src='resources/img/arrow-triangle_64px.png'/></a>";
//        str+='</div>';
    str+='<div class="expand-right" style="float:left;margin-left: 60px">';
    str+='<ul>';
    for(var lockCode in lockRecordsMap) {
        unlockRecordList=lockRecordsMap[lockCode].data;
        recordSize=lockRecordsMap[lockCode].size;
        str+='<li style="width: 280px;height:50px;">';
        str+=   '<div class="row-line">';
        str+=       '<a class="a-device" href="javascript:void(0);" onclick="showLockRecords(\''+lockCode+'\')"><img alt="lock" src="resources/img/padlock_64px.png"/>';
        str+=           '<span style="width: 180px;margin-left: 20px;">'+lockCode+'</span>';
        str+=           '<span style="width: 30px;margin-left: 20px;">'+recordSize+'</span>';
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
    console.log('gatewayNum : '+gatewayNum);
    // timeInSec_start=getTimeFromDatetimepicker($("#datetime-picker-1"));
    // timeInSec_end=getTimeFromDatetimepicker($("#datetime-picker-2"));
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

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        getGatewayRecords(page.num, page.size,gatewayNum, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            setListData(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}
function getGatewayRecords(pageNum,pageSize,gatewayNum,successCallback,errorCallback) {
    // console.log("ownerPhoneNumber:"+ownerPhoneNumber+",pageNum:"+pageNum+",pageSize:"+pageSize+",gatewayNum:"+gatewayNum+",timeInSec_start:"+timeInSec_start+",timeInSec_end:"+timeInSec_end);
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getGatewayUnlockRecordPage.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"gatewayCode":gatewayNum,"pageNum":pageNum,"pageSize":pageSize},
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"gatewayCode":gatewayNum,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}

/*按门锁展示记录*/
function showLockRecords(lockNum) {
    // console.log('lockNum : '+lockNum);
    // timeInSec_start=getTimeFromDatetimepicker($("#datetime-picker-1"));
    // timeInSec_end=getTimeFromDatetimepicker($("#datetime-picker-2"));
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

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        getLockRecords(page.num, page.size,lockNum, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            setListData(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}
function getLockRecords(pageNum,pageSize,lockNum,successCallback,errorCallback) {
    // console.log("ownerPhoneNumber:"+ownerPhoneNumber+",pageNum:"+pageNum+",pageSize:"+pageSize+",lockNum:"+lockNum+",timeInSec_start:"+timeInSec_start+",timeInSec_end:"+timeInSec_end);
    //ownerPhoneNumber,startTime,endTime
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getLockUnlockRecordPage.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"lockCode":lockNum,"pageNum":pageNum,"pageSize":pageSize},
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"lockCode":lockNum,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}

/*根据开锁记录展示开锁人信息*/
function showOperatorFromRecords() {
    // timeInSec_start=getTimeFromDatetimepicker($("#datetime-picker-1"));
    // timeInSec_end=getTimeFromDatetimepicker($("#datetime-picker-2"));
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

    /*联网加载列表数据  page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        getUnlockOperator(page.num, page.size, function(data){
            console.log('length : '+Object.keys(data).length);
            mescroll.setPageSize(Object.keys(data).length+1);
            mescroll.endSuccess(Object.keys(data).length);
            setOperatorWithRecord(data);
        }, function(){
            mescroll.endErr();
        });
    }
}
function getUnlockOperator(pageNum,pageSize,successCallback,errorCallback) {
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getUnlockOperator.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}
function setOperatorWithRecord(data) {
    operatorRecordsMap=data;
    // console.log('data: '+data);
    var listDom=document.getElementById("dataList");
    for(var cardNum in data) {
        lockRecordsMap=data[cardNum].data;
        recordSize=data[cardNum].size;
        name=data[cardNum].note;
        var str='';
        str+='<div>';
        str+=    '<a class="a-id" href="javascript:void(0);" onclick="showOperatorRecords(\''+cardNum+'\')" style="width: 340px;">';
        str+=       '<img alt="idCard" src="resources/img/idCard_48px.png">';
        str+=       '<span style="width: 55px;margin-left: 5px;">'+name+'</span>';
        str+=       '<img alt="idCard" src="resources/img/person_64px.png">';
        str+=       '<span style="width: 145px;margin-left: 5px;">'+cardNum+'</span>';
        str+=       '<span style="width: 65px;margin-left: 5px;">'+recordSize+'</span>';
        str+=    '</a>';
        str+='</div>';
        var liDom=document.createElement("li");
        liDom.innerHTML=str;
        listDom.appendChild(liDom);
    }
}

/*按开锁人展示记录*/
function showOperatorRecords(cardNum) {
    // console.log('cardNum : '+cardNum);
    // timeInSec_start=getTimeFromDatetimepicker($("#datetime-picker-1"));
    // timeInSec_end=getTimeFromDatetimepicker($("#datetime-picker-2"));
    if (timeInSec_start>=timeInSec_end){
        $.toast('开始时间应当小于截止时间',1500);
        return null;
    }
    mescroll.destroy();
    // setTimeout(console.log('delay...'),5000);
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
        getOperatorRecords(page.num, page.size,cardNum, function(data){
            var curPageData=data.rows;
            var totalSize=data.totalSize;
            console.log("page.num="+page.num+", page.size="+page.size+", curPageData.length="+curPageData.length);
            mescroll.endBySize(curPageData.length, totalSize);
            setListData(curPageData);
        }, function(){
            mescroll.endErr();
        });
    }
}
function getOperatorRecords(pageNum,pageSize,cardNum,successCallback,errorCallback) {
    // console.log("ownerPhoneNumber:"+ownerPhoneNumber+",pageNum:"+pageNum+",pageSize:"+pageSize+",cardNum:"+cardNum+",timeInSec_start:"+timeInSec_start+",timeInSec_end:"+timeInSec_end);
    $.ajax({
        type:"POST",
        url:projectPath+"/record/getOperatorUnlockRecordPage.action",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        // data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timeInSec_start,"endTime":timeInSec_end,"cardNum":cardNum,"pageNum":pageNum,"pageSize":pageSize},
        data:{"startTime":timeInSec_start,"endTime":timeInSec_end,"cardNum":cardNum,"pageNum":pageNum,"pageSize":pageSize},
        dataType:'json',
        success:function(data,status,xhr){
            successCallback(data);
        },
        error:errorCallback
    });
}

//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
