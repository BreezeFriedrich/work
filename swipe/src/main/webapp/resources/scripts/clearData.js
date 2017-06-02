var startTime;
var endTime;
var deviceid;
var status;
$(function () {
    laydate_init();
});

function retrieveData() {
    //取值
    deviceid = $("#deviceid").val();
    status = $('input:radio[name="optionsRadiosinline"]:checked').val();
    alert('deviceid'+deviceid+'status'+status+'endTime'+endTime);
    if(endTime===undefined||endTime===null||endTime===''){
        alert("至少需要填写截止时间");
    }else{
        $.ajax({
            type: "POST",
            url: "/moduleStatus/countByParam.do",
            data: {"deviceid": deviceid,"status":status,"endTime":endTime},
            dataType: "json",
            async: false,
            success: function (result) {
                alert('ajax-success');
                alert("result:"+result);
                if(confirm("您确定删除吗？")){
                    $.ajax({
                        type: "POST",
                        url: "/moduleStatus/deleteByParam.do",
                        data: {"deviceid": deviceid,"status":status,"endTime":endTime},
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            alert(result+'条记录删除成功');
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert('删除数据出错');
                        }
                    })
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert('ajax-error');
            }
        })
    }
}

function laydate_init() {
    var end = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2012-01-01 00:00:01',
        max: laydate.now(),
        istime: true,
        istoday: false,
        festival:true,
        choose: function(time){
            endTime=time;
        }
    };
    laydate.skin('molv');
    laydate(end);
}

function getFormatTime(date) {
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if(2==month&&29==strDate){
        strDate=28;
    }
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
};

// $('form').bootstrapValidator({
//     message: 'This value is not valid',
//     feedbackIcons: {
//         valid: 'glyphicon glyphicon-ok',
//         invalid: 'glyphicon glyphicon-remove',
//         validating: 'glyphicon glyphicon-refresh'
//     },
//     fields: {
//         username: {
//             message: '用户名验证失败',
//             validators: {
//                 notEmpty: {
//                     message: '用户名不能为空'
//                 },
//                 stringLength: {
//                     min: 6,
//                     max: 18,
//                     message: '用户名长度必须在6到18位之间'
//                 },
//                 regexp: {
//                     regexp: /^[a-zA-Z0-9_]+$/,
//                     message: '用户名只能包含大写、小写、数字和下划线'
//                 }
//             }
//         },
//         email: {
//             validators: {
//                 notEmpty: {
//                     message: '邮箱不能为空'
//                 },
//                 emailAddress: {
//                     message: '邮箱地址格式有误'
//                 }
//             }
//         }
//     },
//     submitHandler: function (validator, form, submitButton) {
//         alert("submit");
//     }
// });

// function resetForm() {
//     $("form").reset();
//     $("input[@type=radio]").attr("checked",'2');
// }