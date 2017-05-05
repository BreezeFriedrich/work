var thisPageSize;
var thisPageRows;
var ownerPhoneNumber;
$(function(){
	if(getCookie('ownerPhoneNumber')){
		ownerPhoneNumber=getCookie('ownerPhoneNumber')
	}else{
		ownerPhoneNumber="18255683932"
	}
})
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
        message : '密码格式不正确'
    },
    cardNumb : {// 验证身份证
        validator : function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(trim(value));//trim()自定义的
        },
        message : '身份证号码格式不正确'
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