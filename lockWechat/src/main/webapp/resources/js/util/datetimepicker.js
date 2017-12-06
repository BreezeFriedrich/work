/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

//new Date() 格式化为 js对象 {year: yyyy,month: MM,date: dd,hour: hh,min: mm,second: ss}
function formatDate2Object(temptime){
    var newDate={};
    newDate.year=temptime.getFullYear();
    var tempMonth=temptime.getMonth()+1;
    if(tempMonth<10){
        tempMonth='0'+tempMonth;
    }
    var tempDate=temptime.getDate();
    if(tempDate<10){
        tempDate='0'+tempDate;
    }
    var tempHour=temptime.getHours();
    if(tempHour<10){
        tempHour='0'+tempHour;
    }
    var tempMin=temptime.getMinutes();
    if(tempMin<10){
        tempMin='0'+tempMin;
    }
    var tempSec=temptime.getSeconds();
    if(tempSec<10){
        tempSec='0'+tempSec;
    }
    newDate.month=tempMonth;
    newDate.date=tempDate;
    newDate.hour=tempHour;
    newDate.min=tempMin;
    newDate.second=tempSec;
    return newDate;
}
//改变输入框datetimepicker的显示,若有yyyy-MM-dd h:mm,则转化为yyyy-MM-dd hh:mm.
function formatDatetimepicker(datetimepicker){
    var timeStr=datetimepicker.val();
    var pattern = /(\d{4})-(\d{2})-(\d{2})\s(\d{1}):(\d{2})/;
//        console.log(pattern.test(timeStr));
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
//获得datetimepicker的时间与GMT时间1970年1月1日之间相差的毫秒数
function getTimeFromDatetimepicker(datetimepicker){
    var timeStr=datetimepicker.val();
    var pattern = /(\d{4})-(\d{2})-(\d{2})\s(\d{1}):(\d{2})/;
//        console.log(pattern.test(timeStr));
    var rep=timeStr;
    if(pattern.test(rep)){
        var reg=new RegExp(pattern);
        rep=rep.replace(reg,"$1-$2-$3 0$4:$5")
    }
//        console.log(rep);
    var timeInSec=Date.parse(rep);
    console.log('timeInSec : '+timeInSec);
    return timeInSec;
}
/**
 * 获得datetimepicker时间的时间戳格式的字符串
 *
 * @param datetimepicker时间控件DOM对象. i.e. $('#datetime-picker-1')
 * @return 'yyyyMMddhhmm'格式的时间字符串. i.e. 2015-01-03 13:32
 */
function getTimeInTimestampFromDatetimepicker(datetimepicker){
    var timeStr=datetimepicker.val();
    var pattern = /(\d{4})-(\d{2})-(\d{2})\s(\d{1}):(\d{2})/;
    // console.log(pattern.test(timeStr));
    var rep=timeStr;
    if(pattern.test(rep)){
        var reg=new RegExp(pattern);
        rep=rep.replace(reg,"$1-$2-$3 0$4:$5")
    }
    // var timeInSec=Date.parse(rep);
    // console.log('timeInSec : '+timeInSec);
    var newDate=new Date(Date.parse(rep));
    // console.log('newDate : '+newDate);
    // console.log(newDate+'————>'+'[Format|\'yyyy-MM-dd h:m:s\'] : '+newDate.format('yyyy-MM-dd h:m:s'));
    // console.log(newDate+'————>'+'[Format|\'yyyy-MM-dd hh:mm:ss\'] : '+newDate.format('yyyy-MM-dd hh:mm:ss'));
    console.log(newDate+'————>'+'[Format|\'yyyyMMddhhmm\'] : '+newDate.format('yyyyMMddhhmm'));
    return newDate.format('yyyyMMddhhmm');
}

/**
 * 添加时间格式化的原型方法,用法 i.e. new Date().format('yyyy-MM-dd hh:mm:ss')
 *
 * @param format 时间格式
 * @return {*}
 */
Date.prototype.format = function(format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
};

