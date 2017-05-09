function testDB() {
    $.ajax({
        url:'deviceManage/findDeviceStatus.do',
        type:'POST',
        data:{deviceid:'SHTG1420151201000072'},
        dataType: "json",
        success: function(data){
            loadCombotree(data);
        }
    })
}