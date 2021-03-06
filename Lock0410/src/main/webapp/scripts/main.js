//动态菜单数据
	var unlockTreeData =[{
				text : "开锁授权",
				attributes : {
				url : "deviceManage.jsp"
				}
			}];
	
	var countTreeData =[{
		text : "查询记录",
		attributes : {
		url : "lockoperate.jsp"
		}
	}];

$(function () {
	//实例化树形菜单
	$("#tree_unlock").tree({
		data : unlockTreeData,
		lines : true,
		onClick : function (node) {
			if (node.attributes) {
				Open(node.text, node.attributes.url);
			}
		}
	});
	$("#tree_count").tree({
		data : countTreeData,
		lines : true,
		onClick : function (node) {
			if (node.attributes) {
				Open(node.text, node.attributes.url);
			}
		}
	});
	//在右边center区域打开菜单，新增tab
	function Open(text, url) {
		if ($("#tabs").tabs('exists', text)) {
			$('#tabs').tabs('select', text);
			/*
			$('#tabs').tabs({
				border:false,
				onSelect:function(title){
					if(text=='开锁授权'){
						validOwnerPhoneNumber();
						showDevices();
					}
				}
			});
			*/
		} else {
			$('#tabs').tabs('add', {
				title : text,
				closable : true,
                cache:true,
				// href :url,
				content:'<iframe scrolling="yes" frameborder="0" src="'+url+'" style="width: 100%;height: 100%;"></iframe>'
				});
		}
	}
	
	//绑定tabs的右键菜单
	$("#tabs").tabs({
		onContextMenu : function (e, title) {
			e.preventDefault();
			$('#tabsMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data("tabTitle", title);
		}
	});
	//实例化menu的onClick事件
	$("#tabsMenu").menu({
		onClick : function (item) {
			CloseTab(this, item.name);
		}
	});
	
	if(getCookie("ownerName")){
		document.getElementById("loginStatus").innerHTML=getCookie("ownerName")+"|"+getNowFormat();
	}else{
		document.getElementById("loginStatus").innerHTML="未登录";
	}
	document.getElementById("loginStatus").style.cssText="line-height:15px;font-size:14px;color:#7f93ad";
	//安全退出
	document.getElementById("safetylogout").onclick=function(){
		// clearCookie();
		window.location.href="login.jsp";
	};
});

//几个关闭事件的实现
function CloseTab(menu, type) {
	var curTabTitle = $(menu).data("tabTitle");
	var tabs = $("#tabs");
	if (type === "close") {
		tabs.tabs("close", curTabTitle);
		return;
	}
	var allTabs = tabs.tabs("tabs");
	var closeTabsTitle = [];
	$.each(allTabs, function () {
		var opt = $(this).panel("options");
		if (opt.closable && opt.title != curTabTitle && type === "Other") {
			closeTabsTitle.push(opt.title);
		} else if (opt.closable && type === "All") {
			closeTabsTitle.push(opt.title);
		}
	});
	for (var i = 0; i < closeTabsTitle.length; i++) {
		tabs.tabs("close", closeTabsTitle[i]);
	}
}

function getNowFormat() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

//载入遮罩
function show(){
    $("#loading").fadeOut("normal", function(){
        $(this).remove();
    });
}
var delayTime;
$.parser.onComplete = function(){
    if(delayTime)
        clearTimeout(delayTime);
    delayTime = setTimeout(show,500);
};