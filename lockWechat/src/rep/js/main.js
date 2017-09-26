var json;
var ownerPhoneNumber=18255683932;
$(function(){
//	ownerPhoneNumber = document.getElementById("INPUT-hiddden_ownerPhoneNumber").value;
$.ajax({
	type:"POST",
	url:"http://localhost:80/lockWechat/device/getDeviceInfo.action",
	async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
//	headers:{"Access-Control-Allow-Origin":"*"},
	data:{"ownerPhoneNumber":ownerPhoneNumber},
//	timeout:3000,
	dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
	
    success:function(data,status,xhr){
    	json = data;
    },
    error:function(xhr,errorType,error){
        console.log('错误')
        console.log(xhr)
        console.log(errorType)
        console.log(error)
    },
//  beforeSend:function(xhr,settins){
//      console.log(xhr)
//      console.log('发送前')
//  },
//  complete:function(xhr,status){
//      console.log('结束')
//  }
});
showDevices();
})

function showDevices(){
//	alert('json : '+json);
	var UL_gateway=document.createElement('ul');
	UL_gateway.id="UL_gateway";
	document.getElementById('cardList').appendChild(UL_gateway);
	var UL_gateway=document.getElementById('UL_gateway');
	var UL_lockList=document.createElement('ul');
	UL_lockList.id="UL_lockList";
	UL_lockList.style.paddingLeft='0';
	UL_lockList.style.marginTop='0.5rem';
	UL_lockList.style.background='rgba(0,0,0,0.3)';
//	document.getElementById('cardList').appendChild(UL_lockList);
	
	UL_gateway.innerHTML = createGatewayNode();
	//事件代理
    UL_gateway.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== UL_gateway){
        	if(target.getAttribute('class')==='card-content' && target.parentNode.className==='card gateway'){
        		//href内联页面,网关操作
//      		alert('card-content');
				//页面跳转并传递参数
				var gatewayCode=target.parentNode.id
				window.location.href="gateway_manage.jsp?ownerPhoneNumber="+ownerPhoneNumber+"&specificGatewayCode="+gatewayCode;
				//@deprecated 未启用：经struts action跳转
//      		window.location.href="http://localhost:80/lockWehat/redirect/specificGateway?ownerPhoneNumber=18255683932&gatewayCode=777777";
        		//@deprecated 路由跳转无法加载js
//      		$.router.load('addGateway.jsp',true);
        		break;
        	}
        	if(target.className==='card-footer' && target.parentNode.className==='card gateway'){
        		//向下扩展DOM,门锁card
//      		alert('card-footer');
        		selectedGateway=target.parentNode;
//      		alert(selectedGateway.id);
//      		$(target).parent('li').siblings('li').remove();

/*
				//门锁节点是网关节点子节点.
				if(selectedGateway.contains(UL_lockList)){
					selectedGateway.removeChild(UL_lockList);
				}else{
					UL_lockList.innerHTML = createLockNode(selectedGateway.id);
					selectedGateway.appendChild(UL_lockList);
				}
*/
				//门锁节点与网关节点同级.
				if(UL_lockList===selectedGateway.nextSibling){
					selectedGateway.parentNode.removeChild(UL_lockList);
				}else{
					UL_lockList.innerHTML = createLockNode(selectedGateway.id);
					selectedGateway.parentNode.insertBefore(UL_lockList,selectedGateway.nextSibling);
				}
        		break;
        	}
//          if(target.tagName.toLowerCase() == 'li'){
//              console.log('li click~');
//              break;
//          }
            target = target.parentNode;
        }
    })
}
function createGatewayNode(){
	var LI_gateway="";
	for(x in json){
		LI_gateway += "<li class='card gateway' id='"+json[x].gatewayCode+"' style='border: 0.3rem outset rgba(100,100,0,0.5);'>";
		LI_gateway += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+json[x].gatewayName+"</div>";
		LI_gateway += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
		LI_gateway += 		"<div class='card-content-inner'>";
		LI_gateway += 			"<img class='auto-zoom-4' src='img/gateway.png' />";
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
//	var LI_lock="
//		<li class="card">
//			<div class="card-header" style="background-color: #FAF1FC;">芷蕴小筑</div>
//			<div class="card-content" style="background-color: #EEFFFF;">
//				<div class="card-content-inner">
//					<img class="auto-zoom-5" src="img/lock.png" />
//				</div>
//			</div>
//			<div class="card-footer" style="background-color: #F3FAF3;">
//				<p style="color: #00B83F;">工作正常</p><a href="#" class="icon icon-down"></a>
//			</div>
//		</li>
//		";
	var LI_lock="";
	var lockLists=null;
	for(x in json){
		if(json[x].gatewayCode===gatewayCode){
			lockLists=json[x].lockLists;
			break;
		}
	}
	for(x in lockLists){
		LI_lock += "<li class='card lock' id='"+lockLists[x].lockCode+"' style='margin: 0 0.5rem;border: 0.3rem outset rgba(100,100,0,0.5);border-top-width:'0.2rem';'>";
		LI_lock += 	"<div class='card-header' style='background-color: #FAF1FC;'>"+lockLists[x].lockName+"</div>";
		LI_lock += 	"<div class='card-content' style='background-color: #EEFFFF;'>";
		LI_lock += 		"<div class='card-content-inner'>";
		LI_lock += 			"<img class='auto-zoom-3' src='img/lock.png' />";
		LI_lock += 		"</div>";
		LI_lock += 	"</div>";
		LI_lock += 	"<div class='card-footer' style='background-color: #F3FAF3;'>";
		LI_lock += 		"<p style='color: #00B83F;'>"+lockLists[x].lockStatus+"</p><a href='#' class='icon icon-down'></a>";
		LI_lock += 	"</div>";
		LI_lock += "</li>";

//		LI_lock +=	"<li class='lock' id='"+lockLists[x].lockCode+"' style='border: 0.2rem outset green;'>";
//		LI_lock +=	"<div class='row no-gutter'>";
//		LI_lock +=		"<div class='col-50'>"+"<img class='auto-zoom-5' src='img/lock.png' />"+"</div>";
//		LI_lock +=		"<div class='col-50'>";
//		LI_lock +=			"<div><p style='margin:0.2rem 0;font-size: 0.8rem;color: red;'>"+lockLists[x].lockName+"</div>";
//		LI_lock +=			"<div>"+lockLists[x].lockComment+"<br/>"+"</div>";
//		LI_lock +=			"<div>"+lockLists[x].lockLocation+"<br/>"+"</div>";
//		LI_lock +=			"<div><p style='margin:0.2rem 0;font-size: 0.8rem;color: red;'>"+lockLists[x].lockStatus+"</div>";
//		LI_lock +=		"</div>";
//		LI_lock +=	"</div>";
//		LI_lock +=	"</li>"+"<br/>";
		
//		"++" <p>lockLists[x].lockComment</p><p>lockLists[x].lockLocation</p><p>lockLists[x].lockStatus</p>
	}
	return LI_lock;
}

function removeElementsByClass(className){
    var elements = document.getElementsByClassName(className);
    while(elements.length > 0){
        elements[0].parentNode.removeChild(elements[0]);
    }
}

/*
	//为了解决: Zepto.js touch模块与手机自带的滑动效果冲突
	document.addEventListener('touchmove', function (event) {
				event.preventDefault();
			}, false);
*/
/*
//	json=eval("("+json+")");
//	json=JSON.parse(json);
//	alert(json);
//	alert(json[0].gatewayName+','+json[0].gatewayComment+','+json[0].gatewayLocation);
 */
/*
//	document.addEventListener('DOMContentLoaded',showDevices());
//	$('body').on('onload',showDevices());
//document.getElementById('UL_gateway').onload=showDevices;
//window.onload=showDevices;
 */
/*
if(UL_lockList.innerHTML === ""){
	UL_lockList.innerHTML += createLockNode(json,selectedGateway.id);
//	document.getElementById('UL_gateway').insertBefore(UL_lockList,selectedGateway.nextSibling);
	selectedGateway.appendChild(UL_lockList);
}else{
//	removeElementsByClass("lock");
	UL_lockList.innerHTML = "";
}
*/