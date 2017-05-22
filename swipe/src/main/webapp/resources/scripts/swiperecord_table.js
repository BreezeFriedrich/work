$(function(){
    testA();
    // testB();
});
function testA() {
    $.ajax({
        type:"POST",
        url:"/swipeRecord/listAll.do",
        data:{},
        dataType:"json",
        async:false,
        success: function(data){
            alert("table-ajax-data:"+data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
        }
    })
};
function testB() {
    beginTime="2016-01-01 00:00:01";
    endTime="2017-05-22 17:40:01";
    $.ajax({
        type:"POST",
        url:"/swipeRecord/listByTime.do",
        data:{"beginTime":beginTime,"endTime":endTime},
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