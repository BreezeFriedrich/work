var pathName=window.document.location.pathname;
var projectPath=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
//当前用户手机和级别
var ownerPhoneNumber="18255683932";
var grade=10;

var PageSize = 1; //每页个数
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

    if(grade==30){
        showQu();
    }
    if(grade==20){
        showYZ();
    }
    if(grade==10){
        showF();
    }

});

/**
 *得到下级列表
 */
function getJuniorList() {
    var juniort=new Array();
    //获取当前用户下级用户ajax
    $.ajax({
        type:"POST",
        url:"manageController/getjunior.do",
        data:{"ownerPhoneNumber":ownerPhoneNumber,"grade":grade},
        dataType:"json",
        async:false,
        success:function(jsonData){
            var jsonResult=eval(jsonData);
            if(jsonResult.result==0){
                // alert("获取KAI锁数据成功");
                juniort=jsonResult.juniorList;
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
                        juniort=jsonResult.juniorList;
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    alert('操作失败');
                }
            })
        }
    });
    return juniort;
}

/**
 * 得到门锁列表
 */
function getLockList(){
    var devices=new Array();
    var lockList;
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
                // devices=jsonResult.devices;
                //测试用
                devices=jsonResult.devices;
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
    if(devices.length!=0){
        lockList=devices[0].lockLists;
        for(var i=1;i<devices.length;i++){
            lockList=lockList.concat(devices[i].lockLists);
        }
    }
    return lockList;
}


/**
 * 显示区级别内容
 */
function showQu() {
    var juniorList;
    juniorList=getJuniorList();
    if(0===juniorList.length){
        return;
    }
    // alert("getjunior:"+juniorList.length);
    var tbody=document.getElementById("showQ");
    // for(var item in juniorList){
    for(var i=0;i<juniorList.length;i++){
        // console.log();
        var juniorPhoneNumber=juniorList[i].juniorPhoneNumber;
        var juniorName=juniorList[i].juniorName;
        var juniorLocation=juniorList[i].juniorLocation;
        var tr=document.createElement("tr");
        var td1=document.createElement("td");
        var td2=document.createElement("td");
        var td3=document.createElement("td");
        tr.setAttribute("juniorPhoneNumber",juniorPhoneNumber);
        td1.setAttribute("style","width:30%;");
        // td1.innerHTML=juniorName;
        td1.innerHTML=juniorName;

        td2.innerHTML=juniorLocation;

        td3.setAttribute("class","text-center");
        var a=document.createElement("a");
        var ai=document.createElement("i");
        a.setAttribute("class","label label-danger btn btn-danger btn-xs");
        a.setAttribute("onclick","delJunior("+juniorPhoneNumber+");");

        // a.onclick=delJunior();
        ai.setAttribute("class","fa fa-times");
        a.appendChild(ai);
        td3.appendChild(a);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tbody.appendChild(tr);
    }

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
 * 显示业主级别内容
 */
function showYZ() {
    // alert("enter showyz");
    var juniorList=getJuniorList();
    if(0===juniorList.length){
        return;
    }
    // alert("get 10 data");

    var Pages=getPageList(juniorList.length);
    var BeginNO = (Page - 1) * PageSize + 1; //开始编号
    var EndNO = Page * PageSize; //结束编号

    if(EndNO > juniorList.length) EndNO = juniorList.length;
    if(EndNO == 0) BeginNO = 0;
    if(!(Page <= Pages)) Page = Pages;
    //展示下级用户
    var showY=document.getElementById("showY");
    $("#showY tr").remove();
    for(var i=0;i<(EndNO-BeginNO+1);i++){
        // alert("enter show page ");
        var juniorPhoneNumber=juniorList[BeginNO-1+i].juniorPhoneNumber;
        var juniorName=juniorList[BeginNO-1+i].juniorName;
        var juniorLocation=juniorList[BeginNO-1+i].juniorLocation;
        var tr=document.createElement("tr");
        var td1=document.createElement("td");
        var td2=document.createElement("td");
        var td3=document.createElement("td");
        tr.setAttribute("juniorPhoneNumber",juniorPhoneNumber);
        td1.setAttribute("style","width:30%;");
        // td1.innerHTML=juniorName;
        td1.innerHTML=juniorName;

        td2.innerHTML=juniorLocation;
        td3.setAttribute("class","text-center");
        var a=document.createElement("a");
        var ai=document.createElement("i");
        a.setAttribute("class","label label-danger btn btn-danger btn-xs");
        a.setAttribute("onclick","delJunior("+juniorPhoneNumber+");");

        // a.onclick=delJunior();
        ai.setAttribute("class","fa fa-times");
        a.appendChild(ai);
        td3.appendChild(a);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        showY.appendChild(tr);
    }
}

/**
 * 显示房间级别内容
 */
function showF() {
    var lockList=getLockList();
    if(0===lockList.length){
        return;
    }
    //得到总页数并生成page列表
    var Pages=getPageList(lockList.length);
    var BeginNO = (Page - 1) * PageSize + 1; //开始编号
    var EndNO = Page * PageSize; //结束编号

    if(EndNO > lockList.length) EndNO = lockList.length;
    if(EndNO == 0) BeginNO = 0;
    if(!(Page <= Pages)) Page = Pages;

    var showFJ=document.getElementById("showFJ");
    $("#showFJ tr").remove();
    var html;
    for(var i=0;i<(EndNO-BeginNO+1);i++){
        // alert("enter show page ");
        var lockName=lockList[BeginNO-1+i].lockName;
        var lockCode=lockList[BeginNO-1+i].lockCode;
        var lockLocation=lockList[BeginNO-1+i].lockLocation;

        html="";
        html += '<tr lockCode='+lockCode+'>';
        html +=     '<td style="width:30%;">'+lockName+'</td>';
        html +=     '<td>'+lockCode+'</td>';
        html +=     '<td>lockLocation</td>';
        html +=     '<td class="text-center"><a class="label label-danger btn btn-danger btn-xs" href="javascript:void(0);" onclick="delRoom(\"'+lockCode+'\");"><i class="fa fa-times"></i></a></td>';
        html += '</tr>';
        showFJ.innerHTML=html;
    }
}
/*
function showF() {
    var lockList=getLockList();
    if(0===lockList.length){
        return;
    }
    //得到总页数并生成page列表
    var Pages=getPageList(lockList.length);
    var BeginNO = (Page - 1) * PageSize + 1; //开始编号
    var EndNO = Page * PageSize; //结束编号

    if(EndNO > lockList.length) EndNO = lockList.length;
    if(EndNO == 0) BeginNO = 0;
    if(!(Page <= Pages)) Page = Pages;

    var showFJ=document.getElementById("showFJ");
    $("#showFJ tr").remove();
    var html;
    for(var i=0;i<(EndNO-BeginNO+1);i++){
        // alert("enter show page ");
        var lockName=lockList[BeginNO-1+i].lockName;
        var lockCode=lockList[BeginNO-1+i].lockCode;
        var lockLocation=lockList[BeginNO-1+i].lockLocation;
        var tr=document.createElement("tr");
        var td1=document.createElement("td");
        var td2=document.createElement("td");
        var td3=document.createElement("td");
        var td4=document.createElement("td");
        tr.setAttribute("lockCode",lockCode);
        td1.setAttribute("style","width:30%;");
        td1.innerHTML=lockName;

        td2.innerHTML=lockCode;
        td3.innerHTML=lockLocation;
        td4.setAttribute("class","text-center");
        var a=document.createElement("a");

        //<td><a class="label label-danger btn btn-danger btn-xs" onclick="delRoom(\""+lockCode+"\");"><i class="fa fa-times"></i></a></td>
        //<a class="label label-danger btn btn-danger btn-xs" href="#"><i class="fa fa-times"></i></a>
        //<a href="javascript:void(0);" onclick="javascript:window.location.href=encodeURI('${pageContext.request.contextPath}/user/dispatcherHouseStatus.do');">房  态</a>

        var ai=document.createElement("i");
        a.setAttribute("class","label label-danger btn btn-danger btn-xs");
        // a.href("javascript:void(0);");
        a.setAttribute("href","javascript:void(0);");
        a.setAttribute("onclick","delRoom(\""+lockCode+"\");");
        ai.setAttribute("class","fa fa-times");
        a.appendChild(ai);
        td4.appendChild(a);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        showFJ.appendChild(tr);
    }
}
*/
/**
 *添加房间时，加载网关编码
 */
function loadGatewayCode() {
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
                // devices=jsonResult.devices;
                //测试用
                devices=jsonResult.devices;
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
    var gatewayCode=document.getElementById("gwc");
    for(var i=0;i<devices.length;i++){
        var option=document.createElement("option");
        option.setAttribute("value",devices[i].gatewayCode);
        option.innerHTML=devices[i].gatewayCode;
        gatewayCode.appendChild(option);
    }
}

/**
 * 新增房间
 * @param form
 */
function addRoom(form) {
    var gatewayCode=form.gatewayCode.value;
    var lockName=form.lockName.value;
    var lockCode=form.lockCode.value;
    var lockLocation=form.lockLocation.value;
    if(lockCode==undefined || lockCode==null ||lockCode==""){
        alert("门锁编码不能为空！");
    }else if(gatewayCode==undefined || gatewayCode==null ||gatewayCode==""){
        alert("网关编码不能为空！");
    }else{
        //判断门锁是否已被添加ajax
        $.ajax({
            type:"POST",
            url:"manageController/judgeLock.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode},
            dataType:"json",
            async:false,
            success:function(jsonData){
                var jsonResult=eval(jsonData);
                if(jsonResult.result==0){
                    //添加当前门锁ajax
                    $.ajax({
                        type:"POST",
                        url:"manageController/addLock.do",
                        data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode,"gatewayCode":gatewayCode,"lockName":lockName,"lockLocation":lockLocation},
                        dataType:"json",
                        async:false,
                        success:function(jsonData){
                            var jsonResult=eval(jsonData);
                            if(jsonResult.result==0){
                                alert("添加成功！");
                                window.location.reload();

                            }else if(jsonResult.result==1){
                                alert("添加失败！");
                            }
                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown) {
                            $.ajax({
                                type:"POST",
                                url:"manageController/addLock.do",
                                data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode,"gatewayCode":gatewayCode,"lockName":lockName,"lockLocation":lockLocation},
                                dataType:"json",
                                async:false,
                                success:function(jsonData){
                                    var jsonResult=eval(jsonData);
                                    if(jsonResult.result==0){
                                        alert("添加成功！");
                                        window.location.reload();


                                    }else if(jsonResult.result==1){
                                        alert("添加失败！");
                                    }

                                },
                                error:function(XMLHttpRequest, textStatus, errorThrown){
                                    alert('操作失败');
                                }
                            })
                        }
                    });
                }else if(jsonResult.result==1){
                    alert("该门锁已被"+jsonResult.alreadyPhoneNumber+"添加！");
                }else if(jsonResult.result==2){
                    alert("该门锁不存在！");
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/judgeLock.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode},
                    dataType:"json",
                    async:false,
                    success:function(jsonData){
                        var jsonResult=eval(jsonData);
                        if(jsonResult.result==0){
                            //添加当前门锁ajax
                            $.ajax({
                                type:"POST",
                                url:"manageController/addLock.do",
                                data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode,"gatewayCode":gatewayCode,"lockName":lockName,"lockLocation":lockLocation},
                                dataType:"json",
                                async:false,
                                success:function(result){

                                    if(result==0){
                                        alert("添加成功！");
                                        window.location.reload();


                                    }else if(result==1){
                                        alert("添加失败！");
                                    }
                                },
                                error:function(XMLHttpRequest, textStatus, errorThrown) {
                                    $.ajax({
                                        type:"POST",
                                        url:"manageController/addLock.do",
                                        data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode,"gatewayCode":gatewayCode,"lockName":lockName,"lockLocation":lockLocation},
                                        dataType:"json",
                                        async:false,
                                        success:function(result){
                                            if(result==0){
                                                alert("添加成功！");
                                                window.location.reload();


                                            }else if(result==1){
                                                alert("添加失败！");
                                            }

                                        },
                                        error:function(XMLHttpRequest, textStatus, errorThrown){
                                            alert('操作失败');
                                        }
                                    })
                                }
                            });
                        }else if(jsonResult.result==1){
                            alert("该门锁已被"+jsonResult.alreadyPhoneNumber+"添加！");
                        }else if(jsonResult.result==2){
                            alert("该门锁不存在！");
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
 * 删除房间
 * @param lockCode 对应的门锁编码
 */
function delRoom(lockCode) {
    if(confirm("是否确定删除？")){
        //删除当前门锁ajax
        $.ajax({
            type:"POST",
            url:"manageController/delLock.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode},
            dataType:"json",
            async:false,
            success:function(jsonData){
                var jsonResult=eval(jsonData);
                if(jsonResult.result==0){
                    alert("删除成功！");
                    window.location.reload();
                }else if(jsonResult.result==1){
                    alert("删除失败！");
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/delLock.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"lockCode":lockCode},
                    dataType:"json",
                    async:false,
                    success:function(jsonData){
                        var jsonResult=eval(jsonData);
                        if(jsonResult.result==0){
                            alert("删除成功！");
                            window.location.reload();
                        }else if(jsonResult.result==1){
                            alert("删除失败！");
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
 * 删除当前用户的指定下级用户
 * @param juniorPhoneNumber
 */
function delJunior(juniorPhoneNumber) {
    if(confirm("是否确定删除？")){ //获取当前用户下级用户ajax
        $.ajax({
            type:"POST",
            url:"manageController/deljunior.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"grade":grade,"juniorPhoneNumber":juniorPhoneNumber},
            dataType:"json",
            async:false,
            success:function(jsonData){
                var jsonResult=eval(jsonData);
                if(jsonResult.result==0){
                    alert("删除成功！");
                    window.location.reload();
                }else if(jsonResult.result==1){
                    alert("删除失败！");
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/deljunior.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"grade":grade,"juniorPhoneNumber":juniorPhoneNumber},
                    dataType:"json",
                    async:false,
                    success:function(jsonData){
                        var jsonResult=eval(jsonData);
                        if(jsonResult.result==0){
                            alert("删除成功！");
                            window.location.reload();
                        }else if(jsonResult.result==1){
                            alert("删除失败！");
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
 * 添加下级用户
 * @param juniorPhoneNumber
 * @param juniorName
 * @param juniorLocation
 */
function addJunior(form) {
    var juniorPhoneNumber=form.juniorPhoneNumber.value;
    var juniorName=form.juniorName.value;
    var juniorLocation=form.juniorLocation.value;
    if(juniorPhoneNumber==undefined || juniorPhoneNumber==null ||juniorPhoneNumber==""){
        alert("下级用户电话不能为空！");
    }else if(juniorName==undefined || juniorName==null ||juniorName==""){
        alert("下级用户名称不能为空！");
    }else{
        $.ajax({
            type:"POST",
            url:"manageController/addjunior.do",
            data:{"ownerPhoneNumber":ownerPhoneNumber,"grade":grade,"juniorPhoneNumber":juniorPhoneNumber,"juniorName":juniorName,"juniorLocation":juniorLocation},
            dataType:"json",
            async:false,
            success:function(dataa){
                var data=eval(dataa);
                if(data.result==0){
                    alert("操作成功！");
                    window.location.reload();
                }if(data.result==2){
                    alert("该用户已被"+data.superiorPhoneNumber+"添加为下级");
                }if(data.result==3){
                    alert("该用户不存在");
                }if(data.result==4){
                    alert("该用户不能被添加");
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                $.ajax({
                    type:"POST",
                    url:"manageController/addjunior.do",
                    data:{"ownerPhoneNumber":ownerPhoneNumber,"grade":grade,"juniorPhoneNumber":juniorPhoneNumber,"juniorName":juniorName,"juniorLocation":juniorLocation},
                    dataType:"json",
                    async:false,
                    success:function(dataa){
                        var data=eval(dataa);
                        if(data.result==0){
                            alert("操作成功！");
                            window.location.reload();
                        }if(data.result==2){
                            alert("该用户已被"+data.superiorPhoneNumber+"添加为下级");
                        }if(data.result==3){
                            alert("该用户不存在");
                        }if(data.result==4){
                            alert("该用户不能被添加");
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
 * 跳转页码
 * @param num
 */
function gotoPage(num) {
    console.log("enter gotoPage");
    Page=num;
    console.log("Page:"+Page);
    if(grade==20){
        showYZ();
    }
    if(grade==10){
        showF();
    }
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
