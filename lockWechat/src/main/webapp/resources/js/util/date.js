/**
 * 获取今天日期  格式 MM-dd
 */
function MonthAndDay(){
	var temp = new Date();
	var year=temp.getFullYear();
	var month=temp.getMonth()+1;
	var day=temp.getDate();
	
	return year+"-"+ month+"-"+day;
}

/**
 * 获取今天的下一天日期  格式 MM-dd
 */
function  MouthAndNextDay(){
	var temp = new Date();
	temp.setDate(temp.getDate()+1);
	var year=temp.getFullYear();
	var month=temp.getMonth()+1;
	var day=temp.getDate();
	
	return year+"-"+month+"-"+day;
}

/**
 * 格式化时间字符串,从yyyyMMddhhmm(ss)到yyyy-MM-dd hh:mm.
 * @param dateStr
 */
function formatTimetillminString(dateStr) {
    // var ttDate = "2013年12月20日 14:20:20";
    // ttDate = ttDate.replace(/(\d{4}).(\d{1,2}).(\d{1,2}).+/mg, '$1-$2-$3');
    //
    // var dateStr2="20171127131712";
    // alert(dateStr2.replace(/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})?$/,'$1-$2-$3 $4:$5'));
	return dateStr.replace(/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})?$/,'$1-$2-$3 $4:$5');
}

/**
 * 格式化时间字符串,从yyyyMMddhhmmss到yyyy-MM-dd hh:mm:ss.
 * @param dateStr
 */
function formatTimeString(dateStr) {
    var dateStr="20171127131712";
    return dateStr.replace(/^(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})$/,'$1-$2-$3 $4:$5:$6');
}