var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

//当前用户手机和级别
var ownerPhoneNumber;
// var grade=10;

var PageSize = 10; //每页个数
var Page = 1; //当前页码

$(function () {
    console.log('projectPath : '+projectPath);
    $.ajax({
        type:"GET",
        url:"user/getUserFromSession.do",
        async:false,//设置为同步，即浏览器等待服务器返回数据再执行下一步.
        data:{},
        dataType:'json',//返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,status,xhr){
            ownerPhoneNumber=data.phoneNumber;
            grade=data.grade;
            console.log('{ ownerPhoneNumber:'+ownerPhoneNumber+' ; grade:'+grade+' }');
        },
        error:function(xhr,errorType,error){
            console.log('会话过期,请重新登录');
            window.location.href=('user/login.do');
        }
    });

    showDevices();
});

/**
 * 展示网关与门锁
 */
function  showDevices() {
    var devices=getDevices();
    if(0===devices.length){
        return;
    }
    var Pages=getPageList(devices.length);

    var BeginNO = (Page - 1) * PageSize + 1; //开始编号
    var EndNO = Page * PageSize; //结束编号

    if(EndNO > devices.length) EndNO = devices.length;
    if(EndNO == 0) BeginNO = 0;
    if(!(Page <= Pages)) Page = Pages;

    var showDevices=document.getElementById("showDevices");
    $("#showDevices tr").remove();
    //展示网关
    for(var i=0;i<(EndNO-BeginNO+1);i++){
        // alert("enter show page ");
        var gatewayCode=devices[BeginNO-1+i].gatewayCode;
        var gatewayName=devices[BeginNO-1+i].gatewayName;
        var lockLists=devices[BeginNO-1+i].lockLists;
        var tr=document.createElement("tr");
        var th=document.createElement("th");
        var td=document.createElement("td");
        var th1=document.createElement("th");

        tr.setAttribute("gatewayCode",gatewayCode);
        th.setAttribute("style","width:10%;");
        th.setAttribute("valign","middle");
        th1.setAttribute("style","width:5%;");
        th1.setAttribute("valign","middle");

        th.innerHTML=gatewayName;
        //展示网关下门锁
        for (var j=0;j<lockLists.length;j++){
            var div=document.createElement("div");
            div.setAttribute("class","btn-group");

            var button=document.createElement("button");
            button.setAttribute("type","button");
            button.setAttribute("class","btn btn-default dropdown-toggle");
            button.setAttribute("data-toggle","dropdown");
            button.innerHTML= lockLists[j].lockName;

            var ul=document.createElement("ul");
            ul.setAttribute("class","dropdown-menu");
            ul.setAttribute("role","menu");

            var li1=document.createElement("li");
            var li2=document.createElement("li");
            var li3=document.createElement("li");
            var a1=document.createElement("a");
            var a2=document.createElement("a");
            var a3=document.createElement("a");

            // $('#home_keleyi_com').attr('href','http://keleyi.com');
            // a1.setAttribute('href','#');
            a1.href="javascript:void(0);";
            a1.setAttribute("class","md-trigger");
            a1.setAttribute("data-modal","reply-identity");
            a1.setAttribute("gatewayCode",gatewayCode);
            a1.setAttribute("lockCode",lockLists[j].lockCode);
            a1.setAttribute("onclick","setIDAuthFormAttribute(\'"+gatewayCode+"\',\'"+lockLists[j].lockCode+"\');");
            a1.innerHTML= "身份证授权";

            // a2.setAttribute('href','#');
            a2.href="javascript:void(0);";
            a2.setAttribute("class","md-trigger");//a2.addClass('md-trigger');
            a2.setAttribute("data-modal","reply-password");
            a2.setAttribute("gatewayCode",gatewayCode);
            a2.setAttribute("lockCode",lockLists[j].lockCode);
            a2.setAttribute("onclick","setPwdAuthFormAttribute(\'"+gatewayCode+"\',\'"+lockLists[j].lockCode+"\');");
            a2.innerHTML="密码授权";

            // a3.setAttribute('href','#');
            a3.href="javascript:void(0);";
            a3.setAttribute("class","md-trigger");
            a3.setAttribute("data-modal","reply-unlocking");
            a3.setAttribute("gatewayCode",gatewayCode);
            a3.setAttribute("lockCode",lockLists[j].lockCode);
            a3.setAttribute("onclick","showAuth(\'"+gatewayCode+"\',\'"+lockLists[j].lockCode+"\');");
            a3.innerHTML= "查看授权信息";

            li1.appendChild(a1);
            li2.appendChild(a2);
            li3.appendChild(a3);

            ul.appendChild(li1);
            ul.appendChild(li2);
            ul.appendChild(li3);

            div.appendChild(button);
            div.appendChild(ul);
            td.appendChild(div);
        }

        var a=document.createElement("a");
        var ai=document.createElement("i");
        a.setAttribute("class","label label-danger btn btn-danger btn-xs");
        a.setAttribute("onclick","delGateway(\'"+gatewayCode+"\');");

        ai.setAttribute("class","fa fa-times");
        a.appendChild(ai);
        th1.appendChild(a);
        
        tr.appendChild(th);
        tr.appendChild(td);
        tr.appendChild(th1);
        showDevices.appendChild(tr);
    }
}

/**
 * 给身份证授权表格添加属性
 * @param gatewayCode
 * @param lockCode
 */
function setIDAuthFormAttribute(gatewayCode,lockCode) {
    var form=document.getElementById("doIDAuth");
    form.setAttribute("gatewayCode",gatewayCode);
    form.setAttribute("lockCode",lockCode);
    $('#reply-identity').niftyModal();
}

/**
 * 给密码授权表格添加属性
 * @param gatewayCode
 * @param lockCode
 */
function setPwdAuthFormAttribute(gatewayCode,lockCode) {
    var form=document.getElementById("doPwdAuth");
    form.setAttribute("gatewayCode",gatewayCode);
    form.setAttribute("lockCode",lockCode);
    $('#reply-password').niftyModal();
}

/**
 * 跳转页码
 * @param num
 */
function gotoPage(num) {
    console.log("enter gotoPage");
    Page=num;
    console.log("Page:"+Page);
    showDevices();
}

/**
 * 跳转前一页
 *
 */
function goUpPage() {
    if(Page!=1){
        gotoPage(Page-1);
    }
}

/**
 * 跳转后一页
 * maxpage 为最后一页
 */
function goDownPage(maxPage) {
    if(Page<maxPage){
        gotoPage(Page+1);
    }
}

/**
 * date类型转成timetag
 * @param date
 * @returns {string}yyyyMMddHHmm
 */
function gettimetag(date) {
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    var day=date.getDate();
    var hour=date.getHours();
    var minute=date.getMinutes();
    var timetag=""+year+month+day+hour+minute;
    return timetag;
}

/**
 *
 * @param gatewayCode
 */
function delGateway(gatewayCode) {
    if(confirm("是否确认删除？")){
        //删除网关ajax
        $.ajax({
            type:"POST",
            url:"manageController/delGateway.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
            dataType:"json",
            async:false,
            success:function(jsonData){
                var jsonResult=eval(jsonData);
                if(jsonResult.result==0){
                    alert("删除成功！");
                    window.location.reload();
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/delGateway.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode},
                    dataType:"json",
                    async:false,
                    success:function(jsonData){
                        var jsonResult=eval(jsonData);
                        if(jsonResult.result==0){
                            alert("删除成功！");
                            window.location.reload();
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        alert('操作失败');
                    }
                })
            }
        });
    }
}

/**
 * 添加网关
 * @param form
 */
function addGateway(form) {
    var gatewayCode=form.gatewayCode.value;
    var gatewayName=form.gatewayName.value;
    var gatewayLocation=form.gatewayLocation.value;
    var opCode=form.opCode.value;

    if(gatewayName==undefined || gatewayName==null ||gatewayName==""){
        alert("网关名称不能为空！");
    }else if(gatewayCode==undefined || gatewayCode==null ||gatewayCode==""){
        alert("网关编码不能为空！");
    }else if(opCode==undefined || opCode==null ||opCode==""){
        alert("网关验证码不能为空！");
    }else{
        //增加网关ajax
        $.ajax({
            type:"POST",
            url:"manageController/addGateway.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"gatewayLocation":gatewayLocation,"opCode":opCode,"gatewayName":gatewayName},
            dataType:"json",
            async:false,
            success:function(jsonData){
                var jsonResult=eval(jsonData);
                if(jsonResult.result==0){
                    alert("添加成功！");
                    window.location.reload();
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/addGateway.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"gatewayLocation":gatewayLocation,"opCode":opCode,"gatewayName":gatewayName},
                    dataType:"json",
                    async:false,
                    success:function(jsonData){
                        var jsonResult=eval(jsonData);
                        if(jsonResult.result==0){
                            alert("添加成功！");
                            window.location.reload();
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        alert('操作失败');
                    }
                })
            }
        });
    }
}

/**
 * 取消身份证授权
 * @param lockCode
 * @param serviceNumb
 * @param cardNumb
 */
function delIDAuth(lockCode,serviceNumb,cardNumb) {
    if(confirm("是否确认删除？")){
        $.ajax({
            type:"POST",
            url:"manageController/delIDAuth.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"cardNumb":cardNumb,"lockCode":lockCode,"serviceNumb":serviceNumb},
            dataType:"json",
            async:false,
            success:function(jsonData){
                var jsonResult=eval(jsonData);
                if(jsonResult.result==0){
                    alert("删除成功！");
                    window.location.reload();
                    // userList=jsonResult.userList;
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/delIDAuth.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"cardNumb":cardNumb,"lockCode":lockCode,"serviceNumb":serviceNumb},
                    dataType:"json",
                    async:false,
                    success:function(jsonData){
                        var jsonResult=eval(jsonData);
                        if(jsonResult.result==0){
                            alert("删除成功！");
                            window.location.reload();
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        alert('操作失败');
                    }
                })
            }
        });
    }
}

/**
 * 取消密码授权
 * @param gatewayCode
 * @param lockCode
 * @param serviceNumb
 */
function delPwdAuth(gatewayCode,lockCode,serviceNumb) {
    if(confirm("是否确认删除？")) {
        $.ajax({
            type: "POST",
            url: "manageController/delPwdAuth.do",
            data: {
                "ownerPhoneNumber": ownerPhoneNumber,
                "gatewayCode": gatewayCode,
                "lockCode": lockCode,
                "serviceNumb": serviceNumb
            },
            dataType: "json",
            async: false,
            success: function (jsonData) {
                var jsonResult = eval(jsonData);
                if (jsonResult.result == 0) {
                    alert("删除成功！");
                    window.location.reload();
                    // userList=jsonResult.userList;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type: "POST",
                    url: "manageController/delPwdAuth.do",
                    data: {
                        "ownerPhoneNumber": ownerPhoneNumber,
                        "gatewayCode": gatewayCode,
                        "lockCode": lockCode,
                        "serviceNumb": serviceNumb
                    },
                    dataType: "json",
                    async: false,
                    success: function (jsonData) {
                        var jsonResult = eval(jsonData);
                        if (jsonResult.result == 0) {
                            alert("删除成功！");
                            window.location.reload();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert('操作失败');
                    }
                })
            }
        });
    }
}

/**
 * 获取身份证开锁授权
 * @param gatewayCode
 * @param lockCode
 */
function getIDAuth(gatewayCode,lockCode) {
    var userList=new Array();
    var usl=new Array();
    //获取身份证授权信息ajax
    $.ajax({
        type:"POST",
        url:"manageController/getIDAuth.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取KAI锁数据成功");
                userList=jsonResult.userList;
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"manageController/getIDAuth.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        userList=jsonResult.userList;
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });
    var timetag=gettimetag(new Date());
    for(var i=0;i<userList.length;i++){
        if( parseInt(userList[i].endTime) > parseInt(timetag) ){
            usl.push(userList[i]);
        }
    }
    return usl;
}

/**
 * 获取密码开锁授权
 * @param gatewayCode
 * @param lockCode
 */
function getPwdAuth(gatewayCode,lockCode) {
    var passwordList=new Array();
    var psl=new Array();
//获取身份证授权信息ajax
    $.ajax({
        type:"POST",
        url:"manageController/getPwdAuth.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取KAI锁数据成功");
                passwordList=jsonResult.passwordList;
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"manageController/getPwdAuth.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"gatewayCode":gatewayCode,"lockCode":lockCode},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        passwordList=jsonResult.passwordList;
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });
    var timetag=gettimetag(new Date());

    for(var i=0;i<passwordList.length;i++){
        if( parseInt(passwordList[i].endTime) > parseInt(timetag) ){
            psl.push(passwordList[i]);
        }
    }
    return psl;
}

function doIDAuth(form) {
    var name=form.name.value;
    var cardNumb=form.cardNumb.value;
    var reservation=form.reservation.value;
    if(reservation==undefined || reservation==null || reservation==""){
        alert("时间范围不能为空");
    }else {
        var gatewayCode=document.getElementById("doIDAuth").getAttribute("gatewayCode");
        var lockCode=document.getElementById("doIDAuth").getAttribute("lockCode");
        var st=reservation.split("-")[0];
        var et=reservation.split("-")[1];
        var startTime=changetimeformat(st);
        var endTime=changetimeformat(et);
        if(cardNumb.length==18){
            $.ajax({
                type:"POST",
                url:"manageController/doIDAuth.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"cardNumb":cardNumb,"name":name,"startTime":startTime,"endTime":endTime,"gatewayCode":gatewayCode,"lockCode":lockCode},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        // alert("获取KAI锁数据成功");
                        alert("添加授权成功！");
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    $.ajax({
                        type:"POST",
                        url:"manageController/getIDAuth.do",
                        data:{"ownerPhoneNumber":ownerPhoneNumber,"cardNumb":cardNumb,"name":name,"startTime":startTime,"endTime":endTime,"gatewayCode":gatewayCode,"lockCode":lockCode},
                        dataType:"json",
                        async:false,
                        success:function(jsonData){
                            var jsonResult=eval(jsonData);
                            if(jsonResult.result==0){
                                alert("添加授权成功！");
                            }
                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                            alert('操作失败');
                        }
                    })
                }
            });
        }else {
            alert("请输入正确格式的身份证号码！");
        }
    }
}

function doPwdAuth(form) {
    var password=form.password.value;
    var reservation=form.reservation.value;
    if(reservation==undefined || reservation==null || reservation==""){
        alert("时间范围不能为空");
    }else {
        var gatewayCode = document.getElementById("doPwdAuth").getAttribute("gatewayCode");
        var lockCode = document.getElementById("doPwdAuth").getAttribute("lockCode");
        var st = reservation.split("-")[0];
        var et = reservation.split("-")[1];
        var startTime = changetimeformat(st);
        var endTime = changetimeformat(et);
        $.ajax({
            type: "POST",
            url: "manageController/doPwdAuth.do",
            data: {
                "ownerPhoneNumber": ownerPhoneNumber,
                "password": password,
                "startTime": startTime,
                "endTime": endTime,
                "gatewayCode": gatewayCode,
                "lockCode": lockCode
            },
            dataType: "json",
            async: false,
            success: function (jsonData) {
                var jsonResult = eval(jsonData);
                if (jsonResult.result == 0) {
                    // alert("获取KAI锁数据成功");
                    alert("添加授权成功！");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type: "POST",
                    url: "manageController/getIDAuth.do",
                    data: {
                        "ownerPhoneNumber": ownerPhoneNumber,
                        "cardNumb": cardNumb,
                        "name": name,
                        "startTime": startTime,
                        "endTime": endTime,
                        "gatewayCode": gatewayCode,
                        "lockCode": lockCode
                    },
                    dataType: "json",
                    async: false,
                    success: function (jsonData) {
                        var jsonResult = eval(jsonData);
                        if (jsonResult.result == 0) {
                            alert("添加授权成功！");
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert('操作失败');
                    }
                })
            }
        });
    }
}

/**
 * 改变授权时间格式
 * @param time
 * @returns {string}
 */
function  changetimeformat(time) {
    var time1=time.trim().split(" ")[0];
    var time2=time.trim().split(" ")[1];
    var si=time.trim().split(" ")[2];
    var year=time1.trim().split("/")[2];
    var day =time1.trim().split("/")[1];
    var month=time1.trim().split("/")[0];
    var hour=time2.trim().split(":")[0];
    var minute=time2.trim().split(":")[1];
    if(si=="PM"){
        hour=12+parseInt(hour);
    }
    if(hour<10){
        hour="0"+hour;
    }
    var timetag=""+year+month+day+hour+minute;
    return timetag;
}

/**
 * 查看门锁授权信息
 * @param gatewayCode
 * @param lockCode
 */
function showAuth(gatewayCode,lockCode) {
    var passwordList=getPwdAuth(gatewayCode,lockCode);
    var userList=getIDAuth(gatewayCode,lockCode);
    // alert("authNO"+(passwordList.length+userList.length));
    var showAuth=document.getElementById("showAuth");
    $("#showAuth div").remove();
    var divth=document.createElement("div");
    divth.setAttribute("class","tc-table-th");

    var divth1=document.createElement("div");
    divth1.setAttribute("class","col-sm-1");
    var divth2=document.createElement("div");
    divth2.setAttribute("class","col-sm-3");
    var divth3=document.createElement("div");
    divth3.setAttribute("class","col-sm-2");
    var divth4=document.createElement("div");
    divth4.setAttribute("class","col-sm-5");
    divth1.innerHTML="姓名";
    divth2.innerHTML="身份证";
    divth3.innerHTML="密码";
    divth4.innerHTML="预定时间段";

    divth.appendChild(divth1);
    divth.appendChild(divth2);
    divth.appendChild(divth3);
    divth.appendChild(divth4);

    showAuth.appendChild(divth);

    for(var i=0;i<userList.length;i++){
        var serviceNumb=userList[i].serviceNumb;
        var name=userList[i].name;
        var cardNumb=userList[i].cardNumb;
        var startTime=userList[i].startTime;
        var endTime=userList[i].endTime;
        var divtd=document.createElement("div");
        if(i%2 == 0){
            divtd.setAttribute("class","tc-table-td");
        }else{
            divtd.setAttribute("class","tc-table-td2");
        }
        var divtd1=document.createElement("div");
        divtd1.setAttribute("class","col-sm-1");
        var divtd2=document.createElement("div");
        divtd2.setAttribute("class","col-sm-3");
        var divtd3=document.createElement("div");
        divtd3.setAttribute("class","col-sm-2");
        var divtd4=document.createElement("div");
        divtd4.setAttribute("class","col-sm-5");
        divtd1.innerHTML=name;
        divtd2.innerHTML=cardNumb;
        divtd3.innerHTML="";
        divtd4.innerHTML=startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8)+" "+startTime.substring(8,10)+":" +startTime.substring(10,12)+" ~ "+endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8)+" "+endTime.substring(8,10)+":" +endTime.substring(10,12);
        var a=document.createElement("a");
        var ai=document.createElement("i");
        a.setAttribute("class","label label-danger btn btn-danger btn-xs line-heightin");
        a.setAttribute("onclick","delIDAuth(\""+lockCode+"\",\""+serviceNumb+"\",\""+cardNumb+"\");");
        ai.setAttribute("class","fa fa-times");

        a.appendChild(ai);
        divtd.appendChild(divtd1);
        divtd.appendChild(divtd2);
        divtd.appendChild(divtd3);
        divtd.appendChild(divtd4);
        divtd.appendChild(a);

        showAuth.appendChild(divtd);
        // alert(i);
    }
    for(var j=0;j<passwordList.length;j++){
        // alert("enter password");
        var serviceNumb=passwordList[j].serviceNumb;
        var password=passwordList[j].password;
        var startTime=passwordList[j].startTime;
        var endTime=passwordList[j].endTime;
        var divtd=document.createElement("div");
        if(userList.length%2 == 0){
            if(j%2 == 0){
                divtd.setAttribute("class","tc-table-td");
            }else{
                divtd.setAttribute("class","tc-table-td2");
            }
        }else{
            if(i%2 == 1){
                divtd.setAttribute("class","tc-table-td");
            }else{
                divtd.setAttribute("class","tc-table-td2");
            }
        }

        var divtd1=document.createElement("div");
        divtd1.setAttribute("class","col-sm-1");
        var divtd2=document.createElement("div");
        divtd2.setAttribute("class","col-sm-3");
        var divtd3=document.createElement("div");
        divtd3.setAttribute("class","col-sm-2");
        var divtd4=document.createElement("div");
        divtd4.setAttribute("class","col-sm-5");
        divtd1.innerHTML="";
        divtd2.innerHTML="";
        divtd3.innerHTML=password;
        divtd4.innerHTML=startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8)+" "+startTime.substring(8,10)+":" +startTime.substring(10,12)+" ~ "+endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8)+" "+endTime.substring(8,10)+":" +endTime.substring(10,12);
        var a=document.createElement("a");
        var ai=document.createElement("i");
        a.setAttribute("class","label label-danger btn btn-danger btn-xs line-heightin");
        a.setAttribute("onclick","delPwdAuth(\""+gatewayCode+"\",\""+lockCode+"\",\""+serviceNumb+"\");");

        ai.setAttribute("class","fa fa-times");

        a.appendChild(ai);
        divtd.appendChild(divtd1);
        divtd.appendChild(divtd2);
        divtd.appendChild(divtd3);
        divtd.appendChild(divtd4);
        divtd.appendChild(a);

        showAuth.appendChild(divtd);
    }
    $('#reply-unlocking').niftyModal();
}

/**
 * 生成分页列并返回总页数
 * @param dataLength 需分页数据length
 * @returns {number} 总页数
 */
function getPageList(dataLength) {
    var Pages = Math.floor((dataLength - 1) / PageSize) + 1;
    if(Page < 1)Page = 1; //如果当前页码小于1
    if(Page > Pages)Page = Pages; //如果当前页码大于总数

    //生成页码LIST
    var pageList=document.getElementById("pageList");
    $("#pageList li").remove();
    var bli=document.createElement("li");
    var ba=document.createElement("a");
    ba.setAttribute("onclick","goUpPage();");
    ba.innerHTML="&laquo;";
    bli.appendChild(ba);
    pageList.appendChild(bli);
    for(var i=1;i<=Pages;i++){
        // alert("enter page list");
        var li=document.createElement("li");
        var a=document.createElement("a");
        a.innerHTML=i;
        a.setAttribute("onclick","gotoPage("+i+");");
        li.appendChild(a);
        if(i==Page){
            li.setAttribute("class","active");
        }
        pageList.appendChild(li);
    }
    var ali=document.createElement("li");
    var aa=document.createElement("a");
    aa.setAttribute("onclick","goDownPage("+Pages+");");
    aa.innerHTML="&raquo;";
    ali.appendChild(aa);
    pageList.appendChild(ali);
    return Pages;
}

/**
 * 获取设备
 * @returns {Array}
 */
function getDevices() {
    var devices=new Array();
    //得到设备信息ajax
    $.ajax({
        type:"POST",
        url:"manageController/getdevices.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取KAI锁数据成功");
                devices=jsonResult.devices;
                //测试用
                // lockList=jsonResult.lockLists;
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            $.ajax({
                type:"POST",
                url:"manageController/getjunior.do",
                data:{"ownerPhoneNumber":ownerPhoneNumber,"grade":grade},
                dataType:"json",
                async:false,
                success:function(jsonData){
                    var jsonResult=eval(jsonData);
                    if(jsonResult.result==0){
                        // alert("获取锁数据成功");
                        devices=jsonResult.devices;
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });
    return devices;
}