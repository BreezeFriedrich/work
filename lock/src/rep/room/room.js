var datatableSet = {
    options : {
        DEFAULT_OPTION : { //DataTables初始化选项
            language: {
                "sProcessing":   "处理中...",
                "sLengthMenu":   "每页 _MENU_ 项",
                "sZeroRecords":  "没有匹配结果",
                "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":  "",
                "sSearch":       "搜索:",
                "sUrl":          "",
                "sEmptyTable":     "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands":  ",",
                "oPaginate": {
                    "sFirst":    "首页",
                    "sPrevious": "上页",
                    "sNext":     "下页",
                    "sLast":     "末页",
                    "sJump":     "跳转"
                },
                "oAria": {
                    "sSortAscending":  ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            autoWidth: false,   //禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,  //隐藏加载提示,自行处理
            serverSide: true,   //启用服务器端分页
            searching: false,    //禁用原生搜索
            // bProcessing: true,
            // bServerSide: true,
            iDisplayLength : 5,//默认每页数量
            bLengthChange : false, //改变每页显示数据数量,bLengthChange==false会隐藏lengthMenu
            lengthMenu : [5,10,15],
            ordering : true,
            stateSave : true,
            retrieve : true
            //bPaginate: true, //翻页功能
            //bFilter : true, //过滤功能
            // bSort : false, //排序功能
            //bInfo : true,//页脚信息
            //bAutoWidth : true,//自动宽度
            //bPaginate : true,
            //bProcessing: true//服务器端进行分页处理的意思
        },
        COLUMN: {
            CHECKBOX: { //复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {   //常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data||"";
                return '<span title="' + data + '">' + data + '</span>';
            }
        }
    },
    function_room : {
        getQueryParams: function (data) {
            var param = {};
            //组装排序参数
            if (data.order && data.order.length && data.order[0]) {
                switch (data.order[0].column) {
                    case 0:
                        param.orderColumn = "roomTypeId";
                        break;
                    case 1:
                        param.orderColumn = "roomType";
                        break;
                    default:
                        param.orderColumn = "roomTypeId";
                        break;
                }
                //排序方式asc或者desc
                param.orderDir = data.order[0].dir;
            }
            //组装分页参数
            param.startIndex = data.start;
            param.pageSize = data.length;
            param.draw = data.draw;
            return param;
        },
        drawtable: function (element) {
            //element代表table元素. element===$("#table-unlockrecord");
            tableWrapper= element.parent('div .content').eq(0);
            $.fn.dataTable.tables({api: true}).destroy();
            tableInstance = element.dataTable($.extend(true, {}, datatableSet.options.DEFAULT_OPTION, {
                ajax: function (data, callback, settings) {
                    param = datatableSet.function_room.getQueryParams(data);
                    tableWrapper.spinModal();
                    $.ajax({
                        type: "POST",
                        url: 'room/getRoomTableData.do',
                        cache: false,
                        async:false,
                        data: param,
                        dataType: "json",
                        success: function (data) {
                            var returnData = {};
                            if(data.success){
                                if(data.biz.code===0){
                                    var result=data.biz.data;
                                    returnData.draw = result.draw;
                                    returnData.recordsTotal = result.total;
                                    returnData.recordsFiltered = result.total;
                                    returnData.data = result.pageData;
                                }else{
                                    console.log("biz.code:"+data.biz.code+",biz.msg:"+data.biz.msg);
                                }
                            }else{
                                console.log("errmsg:"+data.errmsg);
                            }
                            if(undefined==returnData.data || null==returnData.data){
                                returnData.recordsTotal=0;
                                returnData.recordsFiltered=0;
                                returnData.data=[];
                            }
                            tableWrapper.spinModal(false);
                            callback(returnData);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("查询失败");
                            tableWrapper.spinModal(false);
                        }
                    });
                },
                //绑定数据
                columns: [{
                    data: "roomType"
                },{
                    data: "roomName"
                },{
                    className:"td-operation",
                    data:null,
                    defaultContent:"",
                    orderable:false,
                    width:"100px"
                }
                ],
                "columnDefs": [
                    {
                        "defaultContent": "",
                        "targets": "_all"
                    }
                ],
                "createdRow": function(row,data,index){
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    //给当前行加样式
                    // if (data.role) {
                    //     $(row).addClass("info");
                    // }
                    $(row).attr('roomtypeid',data.roomTypeId);
                    $(row).attr('roomid',data.roomId);
                    var $btnDel = $('<button type="button" class="btn btn-xs btn-danger btn-del">删除</button>');
                    var $btnEdit = $('<button type="button" class="btn btn-xs btn-danger btn-edit">修改</button>');
                    $('td', row).eq(2).append($btnDel).append($btnEdit);
                },
                "drawCallback": function (settings) {}
            })).api();
            tableElement.on("click", ".btn-del", function () {
                var row = tableInstance.row($(this).closest('tr'));
                var item = row.data();
                datatableSet.function_room.deleteItem(item);
                row.remove().draw(false);
            });
            tableElement.on("click", ".btn-edit", function () {
                var row = tableInstance.row($(this).closest('tr'));
                var item = row.data();
                datatableSet.function_room.editItem(item);
                tableInstance.draw(false);
            });
        },
        deleteItem: function (item) {
            params = {
                "roomId":item.roomId
            };
            $.ajax({
                type: "POST",
                url: "room/deleteRoom.do",
                async: false,
                data: params,
                dataType: 'json',
                success: function (data, status, xhr) {
                    alert('deleteRoom:' + data);
                },
                error: function (xhr, errorType, error) {
                    console.log('错误');
                }
            });
        },
        editItem: function (item) {
            $("form#form-editRoom input[name='roomTypeId']").val(item.roomTypeId);
            $("form#form-editRoom input[name='roomId']").val(item.roomId);
            $('#md-editRoom').niftyModal();
        },
        showItemDetail: function (item) {
            //点击行
            alert("点击" + item.roomTypeId + "  " + item.roomType);
        }
    }
};

var lockList=[
    {
        gatewayCode:"GWT001001",
        lockCode:"LCK001001",
        gatewayName:"网关1",
        lockName:"门锁1"
    },{
        gatewayCode:"GWT001001",
        lockCode:"LCK001002",
        gatewayName:"网关1",
        lockName:"门锁2"
    },{
        gatewayCode:"GWT001002",
        lockCode:"LCK002001",
        gatewayName:"网关2",
        lockName:"门锁3"
    },{
        gatewayCode:"GWT001002",
        lockCode:"LCK002002",
        gatewayName:"网关2",
        lockName:"门锁4"
    }
];
function convertDevice(devices) {
    var device;
    var gatewayDevice;
    var gatewayDevices=new Array();
    var gatewayCode;
    var gatewayCodes=new Array();
    var lockCode;
    for(var i=0;i<lockList.length;i++) {
        device=lockList[i];
        gatewayCode=device.gatewayCode;
        lockCode=device.lockCode;
        if(gatewayCodes.indexOf(gatewayCode)==-1){
            gatewayCodes.push(gatewayCode);
        }
    }
    for(var i=0;i<gatewayCodes.length;i++){
        gatewayDevice=new Object();
        gatewayDevice.gatewayCode=gatewayCodes[i];
        gatewayDevices.push(gatewayDevice);
    }
    for(var i=0;i<lockList.length;i++) {
        device = lockList[i];
        gatewayCode = device.gatewayCode;
        lockCode = device.lockCode;
        for(var j=0;j<gatewayDevices.length;j++){
            gatewayDevice=gatewayDevices[j];
            if(gatewayCode===gatewayDevice.gatewayCode){
                gatewayDevice.gatewayName=device.gatewayName;
                if(undefined===gatewayDevice.locks||null===gatewayDevice.locks){
                    gatewayDevice.locks=new Array();
                }
                gatewayDevice.locks.push(device);
                break;
            }
        }
    }
    console.log("gatewayDevices:"+gatewayDevices);
    return gatewayDevices;
}

$(document).ready(function(){
    $(".navbar-collapse ul:first li:eq(6)").addClass("active");

    tableElement=$("#table-room");
    datatableSet.function_room.drawtable(tableElement);

    $('#select-roomType').empty();
    $.ajax({
        type: "POST",
        url: 'room/getRoom.do',
        async: false,
        data: {},
        dataType: "json",
        success: function (data) {
            if(data.success){
                if(data.biz.code===0){
                    var roomTypeCRs=data.biz.data;
                    var roomTypeCR;
                    for(var i=0;i<roomTypeCRs.length;i++){
                        roomTypeCR=roomTypeCRs[i];
                        $('#select-roomType').append("<option value="+roomTypeCR.roomTypeId+">"+roomTypeCR.roomType+"</option>");
                    }
                }else{
                    console.log("biz.code:"+data.biz.code+",biz.msg:"+data.biz.msg);
                }
            }else{
                console.log("errmsg:"+data.errmsg);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("查询失败");
        }
    });

    $.ajax({
        type: "GET",
        url: "room/getUnusedDeviceList.do",
        async: false,
        data: {},
        dataType: 'json',
        success: function (data, status, xhr) {
            console.log('getUnusedDeviceList:' + data);
        },
        error: function (xhr, errorType, error) {
            console.log('错误');
        }
    });
    $('#select-gateway').empty();
    var device;
    // for(var i=0;i<lockList.length;i++){
    //     device=lockList[i];
    //     $('#select-gateway').append("<option value="+device.gatewayCode+">"+device.gatewayName+"</option>");
    // }
    var gatewayDevices=convertDevice(lockList);
    for(var i=0;i<gatewayDevices.length;i++){
        device=gatewayDevices[i];
        $('#select-gateway').append("<option value="+device.gatewayCode+">"+device.gatewayName+"</option>");
    }
    $('#select-gateway').change(function () {
        var gatewayTxt=$('#select-gateway').find("option:selected").text();
        var gatewayVal=$('#select-gateway').val();
        console.log("gatewayTxt:"+gatewayTxt+",gatewayVal:"+gatewayVal);
        $('#select-lock').empty();
        var device;
        for(var i=0;i<lockList.length;i++){
            device=lockList[i];
            if(device.gatewayCode===gatewayVal){
                var option = $("<option>").val(device.lockCode).text(device.lockName);
                $('#select-lock').append(option);
            }
        }
    });
    $('#submit-addRoom').click(function () {
        var roomTypeId=$('#select-roomType').val();
        var gatewayCode=$('#select-gateway').val();
        var lockCode=$('#select-lock').val();
        var roomName = $("form#form-addRoom input[name='roomName']").val();
        params = {
            "roomTypeId":roomTypeId,
            "roomName": roomName,
            "gatewayCode":gatewayCode,
            "lockCode":lockCode
        };
        $.ajax({
            type: "POST",
            url: "room/addRoom.do",
            async: false,
            data: params,
            dataType: 'json',
            success: function (data, status, xhr) {
                alert('addRoom:' + data);
            },
            error: function (xhr, errorType, error) {
                console.log('错误');
            }
        });
    });

    $('#select-lock2').empty();
    $('#select-lock2').append("<option selected='selected'></option>");
    var device;
    for(var i=0;i<lockList.length;i++){
        device=lockList[i];
        $('#select-lock2').append("<option value="+device.lockCode+">"+device.lockName+"</option>");
    }
    $('#submit-editRoom').click(function () {
        params = {
            "roomTypeId":$("form#form-editRoom input[name='roomTypeId']").val(),
            "roomId":$("form#form-editRoom input[name='roomId']").val(),
            "newRoomName":$("form#form-editRoom input[name='newRoom']").val(),
            "newLockCode":$('#select-lock2').val()
        };
        $.ajax({
            type: "POST",
            url: "room/editRoom.do",
            async: false,
            data: params,
            dataType: 'json',
            success: function (data, status, xhr) {
                alert('editRoom:' + data);
            },
            error: function (xhr, errorType, error) {
                console.log('错误');
            }
        });
    });

    App.init({nanoScroller:false,dateTime:false,datepicker:false});
});