$(function(){
    testDB();
});
function testDB() {
    alert('testDB()');
//            var columnTitles=["deviceid","deviceip","status","timestamp","info","reserved"];
    $.ajax({
        type:"POST",
        url:"/moduleStatus/DBtest.do",
        data:{"status":0},
        dataType:"json",
        async:false,
        success: function(data){
            create_tbody(data);
        },
        error:function(XMLHttpRequest){
            alert('ajax-error');
//                    alert(XMLHttpRequest.status);
//                    alert(XMLHttpRequest.readyState);
//                    alert(textStatus);
        }
    })
}