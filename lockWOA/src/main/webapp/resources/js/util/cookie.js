function setCookie(name, value) {
    var Days = 1; //cookie 将被保存两个月
    var exp = new Date(); //获得当前时间
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000); //换成毫秒
    console.log(value);
    document.cookie = name + "=" +  encodeURIComponent(value) + ";expires=" + exp.toGMTString()+";path=/";
}

function getCookie(name) {
    //取出cookie
    var strCookie = document.cookie;
    //cookie的保存格式是 分号加空格 "; "
    var arrCookie = strCookie.split("; ");
    for ( var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (arr[0] == name) {
            return decodeURIComponent(arr[1]);
        }
    }
    return "";
}

function delCookie(name) {
    var exp = new Date(); //当前时间
    exp.setTime(exp.getTime() - 1); //删除cookie 只需将cookie设置为过去的时间
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires="+ exp.toGMTString();
}