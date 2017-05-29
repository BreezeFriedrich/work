$(function(){
    testA();
    testB();
});
function testA() {
    $.ajax({
        type:"POST",
        url:"/moduleStatus/listByStatus.do",
        data:{"status":0},
        dataType:"json",
        async:false,
        success: function(data){
            create_tbody($("#tbody"),data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
        }
    })
}
function testB() {
    alert('testB()');
//            var columnTitles=["deviceid","deviceip","status","timestamp","info","reserved"];

    alert('----------------------');
    $.ajax({
        type:"POST",
        url:"/moduleStatus/listAllWithoutDuplicate.do",
        dataType:"json",
        async:false,
        success: function(data){
            alert('data:'+data);
            create_tbody($("#tbody2"),data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
        }
    })
}