var initializationTime=(new Date()).getTime();
var ownerPhoneNumber="13905169824";
//rDay为系统当前或指定的时间
var rDay=new Date();
//开锁记录
var recordList=new Array();
//身份证授权记录
var userList=new Array();
//密码授权记录
var passwordList=new Array();
$(function(){
	showLeftTime();
	showTime();
	showLock();
	showLockDetail();
	
});
/**
 * 设置当前时间
 */

function showLeftTime(){
	var now=new Date();
	var year=now.getFullYear(); 
	var week=now.getDay();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hours=now.getHours();
	var minutes=now.getMinutes();
	var seconds=now.getSeconds();  
    document.all.show3.innerHTML=year+"-"+month+"-"+day;

}
/**
 * 显示30天时间栏
 */
function showTime(){

	rDay.setDate(rDay.getDate()-1);
	var dates=new Array();
	dates=$("#timetag div");
	var i=1;
	for (i=1;i<dates.length;i++) {

		var newDay=new Date(rDay.setDate(rDay.getDate()+1));
		var year=newDay.getFullYear(); 
		var month=newDay.getMonth()+1;
		var day=newDay.getDate();

		var timetag=year+"-"+month+"-"+day;
		//添加div的time-tag属性
		$("#timetag div:eq("+i+")").attr("time-tag",timetag);
		//添加DIV的HTML的时间内容
		$("#timetag div:eq("+i+")").html(timetag);

	}	
}
/**
 * 日历选择时间
 */
function changeTime(){
	var date=new Array();
	// alert("a");
	var newTime=$("#chooseTime").val();
	date=newTime.split("-");
	var year=date[0];
	var month=date[1];
	// alert(month);
	var day=date[2];
	rDay.setFullYear(year);
	rDay.setMonth(month-1);
	rDay.setDate(day);
	document.all.show3.innerHTML=year+"-"+month+"-"+day;
	showTime();
	changeLockDetail();
}

/**
 * 更改时间时 需更改门锁对应的时间time-tag
 */
function changeLockDetail() {

}

/**
 * 把对应的Date类型转换成年月日
 * @param year
 * @param month
 * @param day
 * @returns dd Date类型返回值
 */
function changeDateToTimetag(date) {
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    if(month<10){
        month="0"+month;
    }
    var day=date.getDate();
    if(day<10){
        day="0"+day;
    }
    var timetag=""+year+month+day;
    return timetag;
}

/**
 * 显示锁列
 */
function  showLock(){
    var data=new Array();
    var jsonResult=null;
    $.ajax({
        type:"POST",
        url:"deviceManage/getDeviceInfo.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber},
        dataType:"json",
        async:false,
        success:function(jsonData){
            jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取锁数据成功");
                data=jsonResult.devices;

            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"deviceManage/getDeviceInfo.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    jsonResult=eval(jsonData);
                    if(jsonResult.result==0){

                        data=jsonResult.devices;
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });
    // alert("共有"+data.length+"把锁");

	var lockList=document.getElementById("lockList");
    for(i = 0; i < data.length; i++){

        for(j=0;j<data[i].lockLists.length;j++){
            // alert("进入j循环");
            var locktr=document.createElement("tr");
            var locktd=document.createElement("td");
            var lockdiv=document.createElement("div");
			locktd.appendChild(lockdiv);
            locktr.appendChild(locktd);
            lockList.appendChild(locktr);

            lockdiv.setAttribute("class","table-hight1 table-width190  table-butstyle");
            lockdiv.setAttribute("lockCode",data[i].lockLists[j].lockCode);
            lockdiv.setAttribute("gatewayCode",data[i].gatewayCode);

            // alert("设置属性");
            // lockdiv.html(data[i].lockLists[j].lockName);
            alert(data[i].lockLists[j].lockName);
            lockdiv.innerHTML=data[i].lockLists[j].lockName;
            // alert("添加内容");
            // alert("锁名"+data[i].lockLists[j].lockName);

        	j++;

		}

	}

}

/**
 * 获得开锁记录
 * @param ownerPhoneNumber
 * @param startTime
 * @param endTime
 */
function getLockOpenRecord(ownerPhoneNumber, startTime, endTime) {
    //当前锁开锁记录ajax
    $.ajax({
        type:"POST",
        url:"lockController/getLockOpenRecord.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":startTime,"endTime":endTime},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取KAI锁数据成功");
                recordList=jsonResult.recordList;
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"lockController/getLockOpenRecord.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":startTime,"endTime":endTime},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        // alert("获取锁数据成功");
                        recordList=jsonResult.recordList;
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });
}

/**
 * 获得门锁身份证授权
 * @param ownerPhoneNumber
 * @param gatewayCode
 * @param lockCode
 */
function getLockIDAuth(ownerPhoneNumber,gatewayCode,lockCode){

    $.ajax({
        type:"POST",
        url:"lockController/getLockIDAuth.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取ID锁数据成功");
                userList=jsonResult.userList;
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"lockController/getLockIDAuth.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        // alert("获取锁数据成功");
                        userList=jsonResult.userList;
                    }
                }
                ,
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });


}


/**
 * 获得门锁密码授权
 * @param ownerPhoneNumber
 * @param gatewayCode
 * @param lockCode
 */
function getLockPwdAuth(ownerPhoneNumber,gatewayCode,lockCode){

    $.ajax({
        type:"POST",
        url:"lockController/getLockPwdAuth.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取PWD锁数据成功");
                passwordList=jsonResult.userList;
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"lockController/getLockPwdAuth.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        // alert("获取锁数据成功");
                        passwordList=jsonResult.userList;
                    }
                }
                ,
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });

}
/**
 * 显示锁与时间对应格
 */
function showLockDetail(){
	var lockList=new Array();
	lockList=$("#lockList tr");
	var row=lockList.length;
    //开锁记录结束时间
    var endTime=changeDateToTimetag(rDay)+"2359";
	var newDay=new Date(rDay.setDate(rDay.getDate()-30));
    //开锁记录起始时间
    var startTime=changeDateToTimetag(newDay)+"0000";
    // alert(startTime);
    // alert(endTime);
    getLockOpenRecord(ownerPhoneNumber,startTime,endTime);
	//初始化行
	for(var i=0;i<row;i++){
        // alert("enter i");
        var cal=newDay;
		var locktr=document.createElement("tr");		
		var lockth=document.createElement("td");
		var lockthdiv=document.createElement("div");
		lockth.setAttribute("class","table-width1");
		lockth.setAttribute("data-fixed","true");
		lockthdiv.setAttribute("class","table-time table-width1  table-cell  table-butstyle");
		lockth.appendChild(lockthdiv);
		locktr.appendChild(lockth);
		var device=$("#lockList div:eq("+i+")");
		var gatewayCode=device.attr("gatewayCode");
		var lockCode=device.attr("lockCode");
        getLockIDAuth(ownerPhoneNumber,gatewayCode,lockCode);
        getLockPwdAuth(ownerPhoneNumber,gatewayCode,lockCode);
		//初始化列
		for(var j=0;j<30;j++){
		    //预订标记
		    var bookFlag=false;
		    //入住标记
		    var checkinFlag=false;
			var datenow=new Date(cal.setDate(cal.getDate()+1));
			var year=datenow.getFullYear(); 
			var month=datenow.getMonth()+1;
			if(month<10){
			    month="0"+month;
            }
			var day=datenow.getDate();
            if(day<10){
                day="0"+day;
            }
			var timetag=year+""+month+""+day;
			var locktd=document.createElement("td");
            var lockdiv=document.createElement("div");

            locktd.setAttribute("time-tag",timetag);
            locktd.setAttribute("class","table-width140");
            locktd.setAttribute("gatewayCode",gatewayCode);
            locktd.setAttribute("lockCode",lockCode);
            lockdiv.setAttribute("class","cd table-hight1 table-width140 ");
            locktd.appendChild(lockdiv);
			locktr.appendChild(locktd);

            //判断是否已入住
            for(var s=0;s<recordList.length;s++){
                var time=recordList[s].timetag.substring(0,8);
                if((time-timetag)==0){
                    checkinFlag=true;
                }
            }

            //判断是否已预订 以每天中午12点为准
            var timeb=timetag+"1200";
            for(var k=0;k<userList.length;k++){
                var st=userList[k].startTime.substring(0,8);
                var et=userList[k].endTime;
                if((timetag-st)>=0){
                    if((timeb-et)<=0){
                        bookFlag=true;
                    }
                }
            }

            // for(var u=0;u<passwordList.length;u++){
            //     alert("enter u");
            //     var st1=passwordList[u].startTime.substring(0,8);
            //     var et1=passwordList[u].endTime;
            //     if((timetag-st1)>=0){
            //         if((timeb-et1)<=0){
            //             bookFlag=true;
            //         }
            //     }
            // }
            // alert("否已预订");
            if(bookFlag){
                locktd.setAttribute("class","table-width140  cd-booked");
                lockdiv.setAttribute("class","cd table-hight1 table-width140 btn-rad md-trigger");
                lockdiv.setAttribute("data-modal","reply-ticket2");
                lockdiv.innerHTML="已预订";
            }
            if(checkinFlag){
                locktd.setAttribute("class","table-width140  cd-select");
                lockdiv.setAttribute("class","cd table-hight1 table-width140 btn-rad md-trigger");
                lockdiv.setAttribute("data-modal","reply-ticket");
                lockdiv.innerHTML="已入住";
            }

		}
		var tbody=document.getElementById("lockDetail");
        tbody.appendChild(locktr);
		
	}

    initAuth();
	
}

function initAuth(){

	//设置锁格右键样式
    $("td").mouseover(function () {
        if( $(this).hasClass("table-width140")){
            	$(this).addClass("rightclick");
        }
    });

    $("td").mouseout(function () {
        if( $(this).hasClass("rightclick")){
            $(this).removeClass("rightclick");
        }
    });

    $("div").mouseover(function () {
        if( $(this).hasClass("cd")){
            $(this).addClass("rightclick");
        }
    });

    $("div").mouseout(function () {
        if( $(this).hasClass("rightclick")){
            $(this).removeClass("rightclick");
        }
    });
    // openDia();
}

//查看预订和入住信息
// function openDia() {
// 	$("td").click(function () {
// 		//已入住格
// 		if($(this).hasClass("cd-select")){
// 			var timet=$(this).attr("time-tag");
// 			var timetag=timet+1;
// 			var lockCode=$(this).attr("lockCode");
//             var gatewayCode=$(this).attr("gatewayCode");
//
// 			//ajax请求入住记录
//             $.ajax({
//                 type:"POST",
//                 url:"lockController/getLockOpenRecord.do",
//                 data:{"ownerPhoneNumber":ownerPhoneNumber,"startTime":timet+"0000","endTime":timetag+"0000"},
//                 dataType:"json",
// 				async:true,
//
//                 success:function(data){
//
//                 }
//             });
//
//         }
//
//         //已预订格
//         if($(this).hasClass("cd-booked")){
// 			//ajax请求预订记录
//
//
//         }
//     });
//
// }