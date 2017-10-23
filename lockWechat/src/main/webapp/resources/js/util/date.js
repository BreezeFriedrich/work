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