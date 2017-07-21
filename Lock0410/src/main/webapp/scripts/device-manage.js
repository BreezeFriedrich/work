var data=new Array();
var ownerPhoneNumber;
var theGateway;var theLock;var lockList;
var name;
var cardNumb;
var unlockData;
var today;
var newDate;
$(function(){
	validOwnerPhoneNumber();
});
function showDevices(){
	var jsonResult=null;
	$.ajax({
		type:"POST",
		url:"deviceManage/getDeviceInfo.do",
		data:{"ownerPhoneNumber":ownerPhoneNumber},
		dataType:"json",
		async:false,
		success:function(jsonData){
			jsonResult=eval(jsonData);
//			alert('showDevice()-json:'+jsonData);
			if(jsonResult.result==0){
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
						alert(XMLHttpRequest.status);
						 alert(XMLHttpRequest.readyState);
						 alert(textStatus);
					}
				})
		}
	});
	var devices= document.getElementById("devices");
	for(i = 0; i < data.length; i++){
		gateway=document.createElement("li");
//		gateway.style.cssText="margin-bottom:50px";
		gateway.style.cssText="list-style:inside url('styles/images/gateway.png');width:300px;float:left;border:2px #95b8e7;";
		lockUl=document.createElement("ul");
		gateway.innerText=data[i].gatewayName;
//         gateway.setAttribute("","");
//         gateway.value = data[i].id;
//         gateway.text = data[i].text;
		gateway.appendChild(lockUl);
		j=0;
		while(j<data[i].lockLists.length){
			lockData=data[i].lockLists[j];
			lock=document.createElement("li");
			lock.style.cssText="list-style:outside url('styles/images/doorlock.png')";
			lockEntity=document.createElement("p");
			lockEntity.innerText=lockData.lockName+','+lockData.lockCode;
			lockEntity.setAttribute("class","lock");
			lockEntity.style.cssText="line-height:30px;font-size:26px;cursor:pointer";
			lock.appendChild(lockEntity);
			lockUl.appendChild(lock);
			j++;
		}
		if(devices&&devices.childNodes.length<data.length){
			devices.appendChild(gateway);
		}
	}
	open();
}
function open(){
	var locks=new Array();
	locks=document.getElementsByTagName("p");
	/*easyui-dialog中不用datagrid,而是手动innerHTML。
	for(i=0;i<locks.length-2;i++){
	var theLock=locks[i];
		alert('theLock:'+theLock);alert('theLock.innerText:'+theLock.innerText);
		locks[i].setAttribute('onclick','onClick');
		locks[i].onClick=function(){
			alert('?'+locks[i].innerText);
//			document.getElementById("LockInfo").setAttribute("display", "block");
			document.getElementById("LockInfo").innerText='开锁身份证'+'开锁密码';
			$('#dd').dialog("open");
		}
	};
	for(var k=0;k<locks.length-2;k++){
		(function(k){
			locks[k].onclick=function(){
            	gatewayNode=locks[k].parentNode.parentNode;
            	lockTxt=locks[k].innerText.split(',');
            	alert('lockName:'+lockTxt[0]+';'+'lockCode:'+lockTxt[1]);
            	for(i=0;i<data.length;i++){
            		lockList=data[i].lockLists;
            		for(j=0;j<lockList.length;j++){
            			if(lockList[j].lockName==lockTxt[0]&&lockList[j].lockCode==lockTxt[1]){
            				theGateway=data[i];theLock=lockList[j];
            				alert('find!'+theGateway.gatewayName+';'+theLock.lockName);
            			}
            		}
            	};
            	unlockData=getAuthInfo(theGateway.gatewayCode,theLock.lockCode,unlockData);
            	document.getElementById("LockInfo").innerHTML="<p>网关: "+theGateway.gatewayName+","+theGateway.gatewayCode+"<br/>门锁: "+theLock.lockName+","+theLock.lockCode+"<br/>"+"<br/>"+unlockData+"</p>";
            	
            	$('#dd').dialog("open");
            };
		})(k)
    }
*/	
	for(var k=0;k<locks.length;k++){
		(function(k){//k与作用域。
			locks[k].onclick=function(){
            	gatewayNode=locks[k].parentNode.parentNode;
            	lockTxt=locks[k].innerText.split(',');
//            	alert('lockName:'+lockTxt[0]+';'+'lockCode:'+lockTxt[1]);
            	for(i=0;i<data.length;i++){
            		lockList=data[i].lockLists;
            		for(j=0;j<lockList.length;j++){
            			if(lockList[j].lockName==lockTxt[0]&&lockList[j].lockCode==lockTxt[1]){
            				theGateway=data[i];theLock=lockList[j];
            				addCookie("gatewayCode",theGateway.gatewayCode,0.1);
            				addCookie("lockCode",theLock.lockCode,0.1);
            				addCookie("lockLocation",theLock.lockLocation,0.1);
            			}
            		}
            	};
//            	alert('lockCode:'+getCookie('lockCode')+";"+'gatewayCode:'+getCookie('gatewayCode')+";"+'lockLocation:'+getCookie('lockLocation'));
//            	unlockData=getAuthInfo(theGateway.gatewayCode,theLock.lockCode,unlockData);
//            	document.getElementById("LockInfo").innerHTML="<p>网关: "+theGateway.gatewayName+","+theGateway.gatewayCode+"<br/>门锁: "+theLock.lockName+","+theLock.lockCode+"<br/>"+"<br/>"+unlockData+"</p>";
            	initAuth();
            	
            };
		})(k)
    }
}

/*
function getAuthInfo(gatewayCode,lockCode,unlockData){
//	gatewayCode=$('#gatewayCodeTxt').textbox('getValue');
//	lockCode=$('#lockCodeTxt').textbox('getValue');
	$.ajax({
		type:"POST",
		url:"deviceManage/getCertiAuthInfo.do",
		data: {"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
		dataType:"json",
		async:false,
		success:function(info){
//			alert('js-info:'+info);
			CertiUnlockdata=eval(info);
			alert('CertiUnlockdata:'+CertiUnlockdata.userList.length+','+CertiUnlockdata.userList[0].serviceNumb);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
		}
	});
	$.ajax({
		type:"POST",
		url:"deviceManage/getPwdAuthInfo.do",
		data: {"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
		dataType:"json",
		async:false,
		success:function(info){
			PwdUnlockdata=eval(info);
			alert('PwdUnlockdata:'+PwdUnlockdata.passwordList.length+','+PwdUnlockdata.passwordList[0].password);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
		}
	});
	unlockData='';
	certis=CertiUnlockdata.userList;
	for(i=0;i<CertiUnlockdata.userList.length;i++){
		unlockData=unlockData.concat('身份证开锁账户 ',i+1,':<br/>','姓名 ',certis[i].name,';身份证号码 ',certis[i].cardNumb,';起始时间 ',certis[i].startTime,':截止时间 ',certis[i].endTime,'<br/>');
	};
	pwds=PwdUnlockdata.passwordList;
	for(i=0;i<PwdUnlockdata.passwordList.length;i++){
		unlockData=unlockData.concat('密码开锁账户 ',i+1,':<br/>','密码 ',pwds[i].password,';起始时间 ',pwds[i].startTime,':截止时间 ',pwds[i].endTime,'<br/>');
	};
	alert('unlockData:'+unlockData);
	return unlockData;
}
*/
/*
function author(){
	alert('author');
	getQueryParam();
	if($('#LockAuthName').textbox('getValue')&&$('#CertAuthTxt').val()){
		name=$('#LockAuthName').textbox('getValue');
		cardNumb=$('#CertAuthTxt').val();
		alert('js-name:'+name+';'+'js-cardNumb:'+cardNumb);
		$.ajax({
			type:"POST",
			url:"deviceManage/doCertiAuth.do",
			data: {"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":theGateway.gatewayCode,"lockCode":theLock.lockCode,"name":name,"cardNumb":cardNumb,"startTime":startTime,"endTime":endTime},
			dataType:"json",
			async:false,
			success:function(result){
				alert('certiAuth-result:'+result);
				$('#cancelAuthdg').datagrid('reload');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
	}else if($('#PwdAuthTxt').textbox('getValue')){
		password=$('#PwdAuthTxt').textbox('getValue');
		alert('js-password:'+password);
		$.ajax({
			type:"POST",
			url:"deviceManage/doPwdAuth.do",
			data: {"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":theGateway.gatewayCode,"lockCode":theLock.lockCode,"password":password,"startTime":startTime,"endTime":endTime},
			dataType:"json",
			async:false,
			success:function(result){
				alert('PwdAuth-result:'+result);
				$('#cancelAuthdg').datagrid('reload');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
	}	
}
*/
function certiUnlockAuthBt(){
//	var el = document.getElementById("unlockAuthBt");	el.parentNode.removeChild(el);
	$("#unlockAuthBt").css("display","none");
	$("#certiUnlockAuth").css("display","block");
	$('#certiAuthDialogST').datetimebox('setValue',dateFormat(today));
	$('#certiAuthDialogET').datetimebox('setValue',dateFormat(newDate));
}
function pwdUnlockAuthBt(){
	$("#unlockAuthBt").css("display","none");
	$("#pwdUnlockAuth").css("display","block");
	$('#pwdAuthDialogST').datetimebox('setValue',dateFormat(today));
	$('#pwdAuthDialogET').datetimebox('setValue',dateFormat(newDate));
}
function certiUnlockAuthor(){
	getQueryParam(true);
//	alert('LockAuthName.getValue:'+($('#LockAuthName').textbox('getValue')));
//	alert('LockAuthName!=null:'+($('#LockAuthName').textbox('getValue')!=null));
//	alert('LockAuthName为空值=:'+($('#LockAuthName').textbox('getValue')==''));
//	if($('#LockAuthName').textbox('getValue')&&$('#CertAuthTxt').val()&&startTime&&endTime&&ownerPhoneNumber){
	if(theGateway&&theLock&&startTime&&endTime&&validOwnerPhoneNumber()&&validCardNumb()&&validName()){
		$.ajax({
			type:"POST",
			url:"deviceManage/doCertiAuth.do",
			data: {"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":theGateway.gatewayCode,"lockCode":theLock.lockCode,"name":name,"cardNumb":cardNumb,"startTime":startTime,"endTime":endTime},
			dataType:"json",
			async:false,
			success:function(result){
				alert('js-certiAuthor-result:'+result);
				$('#cancelAuthdg').datagrid('reload');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus);
			}
		});	
	}else{
		$.messager.alert('输入错误','输入信息未通过验证!','error');
	}
	destroyParam(true);
}
function pwdUnlockAuthor(){
	getQueryParam(false);
	if(theGateway&&theLock&&startTime&&endTime&&validOwnerPhoneNumber()&&validPassword()){
		$.ajax({
			type:"POST",
			url:"deviceManage/doPwdAuth.do",
			data: {"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":theGateway.gatewayCode,"lockCode":theLock.lockCode,"password":password,"startTime":startTime,"endTime":endTime},
			dataType:"json",
			async:false,
			success:function(result){
				alert('js-pwdAuthor-result:'+result);
				$('#cancelAuthdg').datagrid('reload');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
	}else{
		$.messager.alert('输入错误','输入信息未通过验证!','error');
	}
	destroyParam(false);
}
function initAuth(){
	today = new Date();
    newDate = new Date(today.valueOf()+24*60*60*1000);
	$('#cancelAuthdg').datagrid({
		url:'deviceManage/getUnlockAccountInfo.do',
		method:'post',
		queryParams:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":theGateway.gatewayCode,"lockCode":theLock.lockCode},
		onLoadSuccess:function(data){  
			$('#dd').dialog("open");
        	document.getElementById("theGateway").innerText='网关:'+theGateway.gatewayCode;
        	document.getElementById("theLock").innerText='门锁:'+theLock.lockCode;
        	document.getElementById("lockLoc").innerText='位置:'+theLock.lockLocation;
        }
	});
}
function cancelAuth(){
	var theRow=$('#cancelAuthdg').datagrid('getSelected');
	if(theRow.cardNumb){
		$.ajax({
			type:"POST",
			url:"deviceManage/doCertiCancelAuth.do",
			data: {"ownerPhoneNumber":ownerPhoneNumber,"lockCode":theLock.lockCode,"serviceNumb":theRow.serviceNumb,"cardNumb":theRow.cardNumb},
			dataType:"json",
			async:false,
			success:function(result){
				alert('CertiCancelAuth-result:'+result);
				$('#cancelAuthdg').datagrid('reload');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		 		alert(XMLHttpRequest.status);
		 		alert(XMLHttpRequest.readyState);
		 		alert(textStatus);
			}
		});
	}else{
		$.ajax({
			type:"POST",
			url:"deviceManage/doPwdCancelAuth.do",
			data: {"ownerPhoneNumber":ownerPhoneNumber,"lockCode":theLock.lockCode,"gatewayCode":theGateway.gatewayCode,"serviceNumb":theRow.serviceNumb},
			dataType:"json",
			async:false,
			success:function(result){
				alert('PwdCancelAuth-result:'+result);
				$('#cancelAuthdg').datagrid('reload');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		 		alert(XMLHttpRequest.status);
		 		alert(XMLHttpRequest.readyState);
		 		alert(textStatus);
			}
		});
	}
	$('#cancelAuthdg').datagrid('clearSelections');
}
function getQueryParam(flag){
	if(flag){//flag==true-->certiAuthDiagTime;flag==false-->pwdAuthDiagTime
		startTime=dateFormat($('#certiAuthDialogST').datetimebox('getValue'));
		endTime=dateFormat($('#certiAuthDialogET').datetimebox('getValue'));
	}else{
		startTime=dateFormat($('#pwdAuthDialogST').datetimebox('getValue'));
		endTime=dateFormat($('#pwdAuthDialogET').datetimebox('getValue'));
	}
}
function destroyParam(flag){
	if(flag){//flag==true-->certiAuthDiagTime;flag==false-->pwdAuthDiagTime
		dateFormat($('#certiAuthDialogST').datetimebox('setValue',dateFormat(today)));//setText,setVal
		dateFormat($('#certiAuthDialogET').datetimebox('setValue',dateFormat(newDate)));
//		$('#CertAuthTxt').val('');
		$('#CertAuthTxt').textbox('setValue','');
		$('#LockAuthName').textbox('setValue','');
	}else{
		dateFormat($('#pwdAuthDialogST').datetimebox('setValue',dateFormat(today)));
		dateFormat($('#pwdAuthDialogET').datetimebox('setValue',dateFormat(newDate)));
		$('#PwdAuthTxt').textbox('setValue','');
	}
}
function dateFormat(time){
	date=new Date(time);
	y = date.getFullYear();
	mon = date.getMonth()+1;
	d = date.getDate();
	h = date.getHours();
	min = date.getMinutes();
	now=y+(mon<10?('0'+mon):mon)+(d<10?('0'+d):d)+(h<10?('0'+h):h)+(min<10?('0'+min):min);
	return now;
}
function closeDialog(){
	$("#certiUnlockAuth").css("display","none");
	$("#pwdUnlockAuth").css("display","none");
	$("#unlockAuthBt").css("display","block");
	$('#CertAuthTxt').val('');
	$('#LockAuthName').textbox('setValue','');
	$('#PwdAuthTxt').textbox('setValue','');
	theGateway=0;
	theLock=0;	
	$('#dd').dialog('close')
}

//-----------------------------------------------------------------------------------------------------------------依据Regex规则取参数并验证，符合规则则赋值并return true,否则为null并return false.
function validPassword(){
	password=null;
	regPassword=/^[a-zA-Z\d_]{4,11}$/;//英文字母下划线数字.
	if($('#PwdAuthTxt').textbox('getValue')){
		tempPwd=trim($('#PwdAuthTxt').textbox('getValue'));
		if(regPassword.test(tempPwd)){
			password=tempPwd;
		}
	}
	if(password){
		return true;
	}else{
		return false;
	}
}
function validName(){
    name=null;
    if(($('#LockAuthName').textbox('getValue'))){
        tempName=trim($('#LockAuthName').textbox('getValue'));
        if(isLegalName(tempName)){
            name=tempName;
            return true;
        }else return false;
    }else {return false;}
}
function isLegalName(name){
    if (name.indexOf("·")>0 || name.indexOf("•")>0){
        if (name.match("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")){
            return true;
        }else {
            return false;
        }
    }else {
        if (name.match("^[\\u4e00-\\u9fa5]+$")){
            return true;
        }else {
            return false;
        }
    }
}
/*
function validName(){
	name=null;
	var regName = new RegExp("[\\u4E00-\\u9FFF]+");
	if(($('#LockAuthName').textbox('getValue'))){
		tempName=trim($('#LockAuthName').textbox('getValue'));
		if(regName.test(tempName)){
			name=tempName;
			return true;
		}else return false;
	}else {return false;}
}
*/
function validOwnerPhoneNumber(){
	ownerPhoneNumber=null;
	regPhone=/^(13|15|18)\d{9}$/;
	if(getCookie("ownerPhoneNumber")){
		tempNum=trim(getCookie("ownerPhoneNumber"));//调用本地cookie_utils中定义的trim()方法.
		if(tempNum.length==11&&regPhone.test(tempNum)){
			ownerPhoneNumber=tempNum;
		}
	}
	if(ownerPhoneNumber){
		return true;
	}else{
		ownerPhoneNumber='18255683932';
		return true;
	}
}
function validCardNumb(){
	tempId=$('#CertAuthTxt').textbox('getValue');
	//15位和18位身份证号码的正则表达式
	// var regCardNumb=/(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)/;
    var regCardNumb=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	//如果通过该验证，说明身份证格式正确，但准确性还需计算
	if(regCardNumb.test(tempId)){
//		if(tempId.length==18){
//			var cardNumbWi=new Array( 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ); //将前17位加权因子保存在数组里
//			var cardNumbY=new Array( 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
//			var cardNumbWiSum=0; //用来保存前17位各自乖以加权因子后的总和
//			for(var i=0;i<17;i++){
//				cardNumbWiSum+=tempId.substring(i,i+1)*cardNumbWi[i];
//			}
//			var cardNumbMod=cardNumbWiSum%11;//计算出校验码所在数组的位置
//			var cardNumbLast=cardNumb.substring(17);//得到最后一位身份证号码
//			//如果等于2，则说明校验码是10，身份证号码最后一位应该是X
//			if(cardNumbMod==2){
//				if(cardNumbLast=="X"||cardNumbLast=="x"){
//					return false;
//				}else{
//					return false;
//				}
//			}else{
//			//用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
//				if(cardNumbLast==cardNumbY[cardNumbMod]){
//					return false;
//				}else{
//					return false;
//				}
//			}
//		}else if(tempId.length==15){
//			return true;
//		}else{return false};
		cardNumb=tempId;
		return true;
	}else{
		return false;
	}
}

//验证规则---------------------------------------------------------------------------------------------------------------------
$.extend($.fn.validatebox.defaults.rules, {
//    lockPwd : {
//        validator : function(value, param) {
//            return value == $(param[0]).val();
//        },
//        message : '密码格式不正确'
//    },
    lockPwd : {
        validator : function(value, param) {
            return /^([A-Za-z\d_]{4,11})?$/i.test(trim(value));
        },
        message : '密码格式不正确(仅字母、数字、下划线)'
    },
    cardNumb : {// 验证身份证
        validator : function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(trim(value));//trim()自定义的
        },
        message : '身份证号码格式不正确'
    },
    chineseName : {// 验证中文
        validator : function(value) {
            return isLegalName(value);
        },
        message : '姓名输入错误(仅中文和·)'
    },
    minLength: {
        validator: function(value, param){
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length:{validator:function(value,param){
        var len=$.trim(value).length;
        return len>=param[0]&&len<=param[1];
    },
        message:"输入内容长度必须介于{0}和{1}之间."
    },
    mobile : {// 验证手机号码
        validator : function(value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message : '手机号码格式不正确'
    },
    intOrFloat : {// 验证整数或小数
        validator : function(value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message : '请输入数字，并确保格式正确'
    },
    integer : {// 验证整数
        validator : function(value) {
            return /^[-]?[1-9]+\d*$/i.test(value);//[+]--->[-]
        },
        message : '请输入整数'
    },
    chinese : {// 验证中文
        validator : function(value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message : '请输入中文'
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入英文'
    },
    unnormal : {// 验证是否包含空格和非法字符
        validator : function(value) {
            return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    }
});