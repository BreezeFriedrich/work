var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

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
    function_roomType : {
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
                    param = datatableSet.function_roomType.getQueryParams(data);
                    tableWrapper.spinModal();
                    $.ajax({
                        type: "POST",
                        // url: 'https://8066da8b-4ae7-4543-97f8-159935ef6889.mock.pstmn.io/lock/room/getRoomType.do',
                        url: 'room/getRoomTypeTableData.do',
                        cache: false,
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
                        data: "roomTypeId",
                        // render: datatableSet.options.RENDER.ELLIPSIS//alt效果
                    },{
                        data: "roomType"
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
                "createdRow": function ( row, data, index ) {
                    //行渲染回调,在这里可以对该行dom元素进行任何操作
                    //给当前行加样式
                    if (data.role) {
                        $(row).addClass("info");
                    }
                    var $btnDel = $('<button type="button" class="btn btn-xs btn-danger btn-del">删除</button>');
                    var $btnEdit = $('<button type="button" class="btn btn-xs btn-danger btn-edit">修改</button>');
                    $('td', row).eq(2).append($btnDel).append($btnEdit);
                },
                "drawCallback": function (settings) {}
            })).api();
            tableElement.on("click", ".btn-del", function () {
                var row = tableInstance.row($(this).closest('tr'));
                var item = row.data();
                datatableSet.function_roomType.deleteItem(item);
                row.remove().draw(false);
            });
            tableElement.on("click", ".btn-edit", function () {
                var row = tableInstance.row($(this).closest('tr'));
                var item = row.data();
                datatableSet.function_roomType.editItem(item);
                tableInstance.draw(false);
            });
        },
        deleteItem: function (item) {
            params = {
                "roomTypeId":item.roomTypeId
            };
            $.ajax({
                type: "POST",
                url: "room/deleteRoomType.do",
                async: false,
                data: params,
                dataType: 'json',
                success: function (data, status, xhr) {
                    alert('deleteRoomType:' + data);
                },
                error: function (xhr, errorType, error) {
                    console.log('错误');
                }
            });
        },
        editItem: function (item) {
            $("form#form-editRoomType input[name='roomTypeId']").val(item.roomTypeId);
            $('#md-editRoomType').niftyModal();
        },
        showItemDetail: function (item) {
            //点击行
            alert("点击" + item.roomTypeId + "  " + item.roomType);
        }
    }
};

$(document).ready(function(){
    $(".navbar-collapse ul:first li:eq(5)").addClass("active");

    tableElement=$("#table-roomType");
    datatableSet.function_roomType.drawtable(tableElement);

    $('#submit-addRoomType').click(function () {
        roomType = $("form#form-addRoomType input[name='roomType']").val();
        params = {
            "roomType": roomType
        };
        $.ajax({
            type: "POST",
            url: "room/addRoomType.do",
            async: false,
            data: params,
            dataType: 'json',
            success: function (data, status, xhr) {
                alert('addRoomType:' + data);
            },
            error: function (xhr, errorType, error) {
                console.log('错误');
            }
        });
    });
    $('#submit-editRoomType').click(function () {
        params = {
            "roomTypeId":$("form#form-editRoomType input[name='roomTypeId']").val(),
            "newRoomType":$("form#form-editRoomType input[name='newRoomType']").val()
        };
        $.ajax({
            type: "POST",
            url: "room/editRoomType.do",
            async: false,
            data: params,
            dataType: 'json',
            success: function (data, status, xhr) {
                alert('editRoomType:' + data);
            },
            error: function (xhr, errorType, error) {
                console.log('错误');
            }
        });
    });

    App.init();
});