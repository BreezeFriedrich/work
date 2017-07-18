var thisPageSize;
var thisPageRows;
var ownerPhoneNumber;
$(function(){
	if(getCookie('ownerPhoneNumber')){
		ownerPhoneNumber=getCookie('ownerPhoneNumber')
	}else{
		ownerPhoneNumber="18255683932"
	}
});
//function loadCombotree(){
//	$('#lockoperate_combotree').combotree({
//		url:'lockoperate/findDeviceTree.do?ownerPhoneNumber='+ownerPhoneNumber,
//	    multiple:true,
//	    checkbox:true,
//	    prompt:'按网关门锁查询'
//	});
//}
function loadCombotree(data){
	$('#lockoperate_combotree').combotree({
	    multiple:true,
	    checkbox:true,
	    prompt:'按网关门锁查询'
	});
	$('#lockoperate_combotree').combotree('loadData',data);
}
function doSearch(){
	var tree = $('#lockoperate_combotree').combotree('tree'); // 得到树对象
	var idText=$('#cardNumbText').textbox('getValue');
	var pwdText=$('#pwdText').textbox('getValue');
	var nodes = tree.tree('getChecked');
    var nodesArr=new Array();
	
	if(nodes.length>0){
		function getChecked(){	        
	        for (var i = 0; i < nodes.length; i++) {
	            if (nodes[i].children==null){
	            	nodesArr.push(nodes[i]);
	            };
	        }
	        return nodesArr
	    };
		$('#lockoperateDg').datagrid({
			url:'lockoperate/findByNodes.do',
			method:'post',
//			contentType:'application/x-www-form-urlencoded;charset=utf-8',不起作用
			queryParams:{ownerPhoneNumber:ownerPhoneNumber,startTime:dateLongFormat($('#startTime').datebox('getValue')),endTime:dateLongFormat($('#endTime').datebox('getValue')),nodes:JSON.stringify(getChecked())},
			onLoadSuccess:function(data){
				varUrl='lockoperate/findByNodesOnPage.do';
				doPagi(data,varUrl)
			}
		});
	}else if(idText!=''){
		$('#lockoperateDg').datagrid({
			url:'lockoperate/findByID.do',
			method:'post',
			queryParams:{ownerPhoneNumber:ownerPhoneNumber,startTime:dateLongFormat($('#startTime').datebox('getValue')),endTime:dateLongFormat($('#endTime').datebox('getValue')),idText:idText}
		});
	}else if(pwdText!=''){
		$('#lockoperateDg').datagrid({
			url:'lockoperate/findByPwd.do',
			method:'post',
			queryParams:{ownerPhoneNumber:ownerPhoneNumber,startTime:dateLongFormat($('#startTime').datebox('getValue')),endTime:dateLongFormat($('#endTime').datebox('getValue')),pwdText:pwdText}
		});
	}else{
		$('#lockoperateDg').datagrid({
			url:'lockoperate/findByTime.do',
			method:'post',
			queryParams:{ownerPhoneNumber:ownerPhoneNumber,startTime:dateLongFormat($('#startTime').datebox('getValue')),endTime:dateLongFormat($('#endTime').datebox('getValue'))},
			onLoadSuccess:function(data){
				varUrl='lockoperate/findByTimeOnPage.do';
				doPagi(data,varUrl)
			}
		});
	}
}

//函数doPagi的arguments[0]为data，arguments[1]为varUrl
function doPagi(data,varUrl){
	thisPageSize=eval(data).rows.size;
	thisPageRows=eval(data).totals;
	$('#pagi').pagination({
		total:thisPageRows,
		onSelectPage:function(pageNumber,pageSize){
			$('#lockoperateDg').datagrid('options').queryParams.pageNumber=pageNumber;//queryParams是一个js对象。
			$('#lockoperateDg').datagrid('options').queryParams.pageSize=pageSize;
		    $('#lockoperateDg').datagrid('options').url=varUrl;
		    $('#lockoperateDg').datagrid('options').method='post';
		    $('#lockoperateDg').datagrid('reload'); //重新加载表格
		},
	})
}
function dateLongFormat(f){
	var date=new Date(f);
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var now=y+(m<10?('0'+m):m)+(d<10?('0'+d):d);
	return now;	
}