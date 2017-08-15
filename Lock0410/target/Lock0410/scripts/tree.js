var data=new Array();
var ownerPhoneNumber;
var theGateway;var theLock;var lockList;
var name;
var cardNumb;
var unlockData;
var today;
var newDate;

function treeInit() {
    $.fn.zTree.init($("#tree_device"), setting);
}
var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        fontCss:setFontCss,
        showTitle:showTitle,//default value:true
        // dblClickExpand:true,
        // expandSpeed:"fast",
        // nameIsHTML:false,
        // selectedMulti:true,
        // txtSelectedEnable:false
    },
    check: {
        enable: true,
        chkboxType:{"Y":"s","N":"s"},
        chkStyle:"checkbox",
        autoCheckTrigger:false
    },
    data: {
        keep:{
            leaf:true,
            parent:false
        },
        key:{
            // checked:"checked",
            // children:"children",
            // name:"name",
            // title:"",
            // url:"url"
        },
        simpleData: {
            enable: true,
            idKey:"id",
            pIdKey:"pId"
        }
    },
    edit: {
        enable: false
    },
    // async : {
    //     enable : true,
    //     url : "http://127.0.0.1:8080/contact/resource.do?method=getzTreeNodes", // Ajax 获取数据的 URL 地址
    //     autoParam : [ "id", "name" ], //ajax提交的时候，传的是id值
    //     otherParam: ["contactid",function(){
    //         return window.opener.document.getElementById("contactid").value;
    //     }]
    // },
    async:{
        enable:true,
        type:"post",
        url:"deviceManage/getDeviceTree.do",
        // contentType:"application/json",
        autoParam:[],
        otherParam:{"ownerPhoneNumber":"18255683932"},
        dataType:"text",
        dataFilter:null
    },
    callback:{
        onAsyncSuccess:zTreeOnAsyncSuccess,
        onAsyncError:zTreeOnAsyncError,
        onDblClick:zTreeOnDblClick,
        onCheck:zTreeOnCheck,
        onRightClick:null
    }
};

var zNodes = [
    {"id":1,"name":"天字号","pId":0,"title":"888888","isParent":true,"isHidden":false,"open":true,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/gateway.png"},
    {"id":23,"name":"大紫明宫","pId":1,"title":"001007","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":24,"name":"九重天","pId":1,"title":"001005","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":5,"name":"狐狸洞","pId":1,"title":"001006","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":6,"name":"昆仑墟","pId":1,"title":"001003","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":7,"name":"十里桃林","pId":1,"title":"001004","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":8,"name":"盘丝洞","pId":1,"title":"001002","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":9,"name":"水帘洞","pId":1,"title":"001001","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":10,"name":"301","pId":1,"title":"001008","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":11,"name":"生态","pId":1,"title":"851153","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":2,"name":"地字号","pId":0,"title":"777777","isParent":true,"isHidden":false,"open":true,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/gateway.png"},
    {"id":12,"name":"中山陵","pId":2,"title":"002010","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":13,"name":"清凉山","pId":2,"title":"002009","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":14,"name":"明孝陵","pId":2,"title":"002008","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":15,"name":"夫子庙","pId":2,"title":"002007","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":16,"name":"燕子矶","pId":2,"title":"002006","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":17,"name":"美龄宫","pId":2,"title":"002005","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":18,"name":"大报恩寺","pId":2,"title":"002003","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":19,"name":"阅江楼","pId":2,"title":"002004","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":20,"name":"瞻园","pId":2,"title":"002002","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":21,"name":"灵谷寺","pId":2,"title":"002001","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":22,"name":"404","pId":2,"title":"002011","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":3,"name":"天字号","pId":0,"title":"888888","isParent":true,"isHidden":false,"open":true,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/gateway.png"},
    {"id":44,"name":"大紫明宫","pId":3,"title":"001007","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":43,"name":"九重天","pId":3,"title":"001005","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":25,"name":"狐狸洞","pId":3,"title":"001006","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":26,"name":"昆仑墟","pId":3,"title":"001003","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":27,"name":"十里桃林","pId":3,"title":"001004","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":28,"name":"盘丝洞","pId":3,"title":"001002","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":29,"name":"水帘洞","pId":3,"title":"001001","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":30,"name":"301","pId":3,"title":"001008","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":31,"name":"生态","pId":3,"title":"851153","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":4,"name":"地字号","pId":0,"title":"777777","isParent":true,"isHidden":false,"open":true,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/gateway.png"},
    {"id":32,"name":"中山陵","pId":4,"title":"002010","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":33,"name":"清凉山","pId":4,"title":"002009","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":34,"name":"明孝陵","pId":4,"title":"002008","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":35,"name":"夫子庙","pId":4,"title":"002007","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":36,"name":"燕子矶","pId":4,"title":"002006","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":37,"name":"美龄宫","pId":4,"title":"002005","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":38,"name":"大报恩寺","pId":4,"title":"002003","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":39,"name":"阅江楼","pId":4,"title":"002004","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":40,"name":"瞻园","pId":4,"title":"002002","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":41,"name":"灵谷寺","pId":4,"title":"002001","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"},
    {"id":42,"name":"404","pId":4,"title":"002011","isParent":false,"isHidden":false,"open":false,"nocheck":false,"target":"_blank","icon":"./zTree/css/zTreeStyle/img/diy/doorlock.png"}
];

//view
function addHoverDom(treeId,treeNode) {

}
function removeHoverDom(treeId,treeNode) {

}
function setFontCss(treeId,treeNode) {
    // return treeNode.level == 0 ? {color:"red"} : {};
}
function showTitle(treeId, treeNode) {
    // return treeNode.level != 2;
}

//callback
//节点选择事件
function zTreeOnCheck(event, treeId, treeNode) {
    // alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
}
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    // alert(msg);
    // var treeObj = $.fn.zTree.getZTreeObj("tree_device");
    // treeObj.expandAll(true);
    //隐藏节点
}

function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    alert(XMLHttpRequest);
}

function zTreeOnDblClick(event, treeId, treeNode) {
    alert('event:'+event+' ,treeId:'+treeId+' ,treeNode:'+treeNode);
    lockCode=treeNode.title;
    var parentNode=treeNode.getParentNode();
    gatewayCode=parentNode.title;
    alert('gatewayCode:'+gatewayCode+' ,lockCode:'+lockCode);
    open();
}

function open() {
    initAuth();
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
/*
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.addNodes(treeNode, { id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++) });
        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};


//显示菜单
function showMenu() {
    $("#menuContent2").css({ left: "15px", top: "34px" }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}
//隐藏菜单
function hideMenu() {
    $("#menuContent2").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent2" || event.target.id == "txt_ztree" || $(event.target).parents("#menuContent2").length > 0)) {
        hideMenu();
    }
}

//节点点击事件
function onClickNode(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

//节点选择事件
function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    var parentid = "";
    var parentlevel = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
        parentid += nodes[i].id + ",";
        parentlevel += nodes[i].menu_level + ",";
    }
    if (v.length > 0) {
        v = v.substring(0, v.length - 1);
        parentid = parentid.substring(0, parentid.length - 1);
        parentlevel = parentlevel.substring(0, parentlevel.length - 1);
    }
    else {
        return;
    }

    hideMenu();
}
*/