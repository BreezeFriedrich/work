var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var orderNumber;
var roomId;
var roomTypeId;
var roomName;
var roomType;
var startTime;
var endTime;
var password;
var checkInTime;
var cardInfoList;
$(function () {

    loadP();
});


function loadP() {
    orderNumber=getQueryString("orderNumber");
    $(document).on('click','.confirm-ok', function () {
        $.confirm('是否确定删除?', function () {
            delOrder();
        });
    });
    getOrderContent();
    document.getElementById("roomtag").setAttribute("placeholder",roomName+"("+roomType+")");
    var addList=document.getElementById("addList");

    var childs = addList.childNodes;
    for(var s = childs.length - 1; s >= 0; s--) {

        addList.removeChild(childs[s]);
    }
    for(var i=0;i<cardInfoList.length;i++){
        var div=document.createElement("div");
        var div1=document.createElement("div");
        var div11=document.createElement("div");
        var div12=document.createElement("div");

        div.setAttribute("class","item-content");
        div1.setAttribute("class","item-inner");
        div11.setAttribute("class","item-title");
        div12.setAttribute("class","item-after");
        if(i==0){
            div11.innerHTML="授权身份证";
        }
        div12.setAttribute("placeholder",cardInfoList[i].cardNumber);
        // div12.innerHTML=cardInfoList[i].cardNumber;

        div1.appendChild(div11);
        div1.appendChild(div12);
        div.appendChild(div1);

        addList.appendChild(div);



    }
    document.getElementById("password").setAttribute("placeholder",password);
    document.getElementById("startTime").setAttribute("placeholder",startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8)+" "+startTime.substring(8,10)+":"+startTime.substring(10,12));
    document.getElementById("endTime").setAttribute("placeholder",endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8)+" "+endTime.substring(8,10)+":"+endTime.substring(10,12));
    document.getElementById("uoda").setAttribute("href","jsp/room/reBook.jsp?orderNumber="+orderNumber);

}
function getOrderContent() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/getOrderContent.action",
        async:false,//异步
        data:{
            "orderNumber":orderNumber
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);

            if(obj.result==0){
                checkInTime=obj.checkInTime;
                startTime=obj.startTime;
                endTime=obj.endTime;
                roomId=obj.roomId;
                roomName=obj.roomName;
                roomTypeId=obj.roomTypeId;
                roomType=obj.roomType;
                cardInfoList=obj.cardInfoList;
                password=obj.password;

            }else{
                $.alert("获取订单失败！");
            }
            // alert("getDatefromSession: "+data);
        },
        error
            :function(xhr,errorType,error){
            alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}

function delOrder() {
    $.ajax({
        type:"POST",
        url:projectPath+"/room/delOrder.action",
        async:false,//异步
        data:{
            "orderNumber":orderNumber
        },
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data){
            var obj=JSON.parse(data);

            if(obj.result==0){
                $.alert('删除订单成功！', function () {
                    window.history.back();
                });
                // $.alert("删除订单成功！");
                // window.history.back();
            }else{
                $.alert("删除订单失败！");
            }
            // alert("getDatefromSession: "+data);
        },
        error:function(xhr,errorType,error){
            $.alert("获取设备请求失败！");
            console.log('ajax错误');
        }
    });
}


//获取链接参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search).substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}