/*
//tbody所需注入的数据形如:
 var data = [{
 name: "传智播客",
 url: "http://www.itcast.cn",
 type: "IT最强培训机构"
 },{
 name: "黑马程序员",
 url: "http://www.itheima.com",
 type: "大学生IT培训机构"
 },{
 name: "传智前端学院",
 url: "http://web.itcast.cn",
 type: "前端的黄埔军校"
 }];
 */

/*
function create_tbody(data) {
    //第一种：动态创建表格的方式，使用拼接html的方式 （推荐）
    // var html = "";
    // for( var i = 0; i < data.length; i++ ) {
    //     html += "<tr>";
    //     html +=     "<td>" + data[i].name + "</td>"
    //     html +=     "<td>" + data[i].url + "</td>"
    //     html +=     "<td>" + data[i].type + "</td>"
    //     html += "</tr>";
    // }
    // $("#tbody").html(html);

    //第二种： 动态创建表格的方式，使用动态创建dom对象的方式
    //清空所有的子节点
    $("#tbody").empty();

    //自杀
    // $("#tbody").remove();

    for( var i = 0; i < data.length; i++ ) {
        //动态创建一个tr行标签,并且转换成jQuery对象
        var $trTemp = $("<tr></tr>");

        //往行里面追加 td单元格
        $trTemp.append("<td>"+ data[i].name +"</td>");
        $trTemp.append("<td>"+ data[i].url +"</td>");
        $trTemp.append("<td>"+ data[i].type +"</td>");
        // $("#tbody").append($trTemp);
        $trTemp.appendTo("#tbody");
    }
}
*/
function create_tbody(jsonArr) {
    $("#tbody").empty();
    for( i = 0; i < jsonArr.length; i++ ) {
        //动态创建一个tr行标签,并且转换成jQuery对象
        var $trTemp = $("<tr></tr>");

        //往行里面追加 td单元格
        for(var field in jsonArr[i] ) {
            $trTemp.append("<td>"+ jsonArr[i][field] +"</td>");
        }

        // $("#tbody").append($trTemp);
        $trTemp.appendTo("#tbody");
    }
}