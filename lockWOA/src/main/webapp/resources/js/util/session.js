var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

//第四步

//用户窗口关闭/或者用户退出的时候，*一定要   request.getSession().invalidate()

//用户窗口关闭js

//关闭窗口时调用此方法
function  invalidateSession(){
    if((window.screenLeft>=10000 && window.screenTop>=10000)||event.altKey){
        //清除当前session,使用jquery 提供的方法
        $.post("projectName/clearSession.do");
        // [ ${base}//clearSession.do]这是一个请求，
        //请求到自己写的ClearSessionServlet
        // 在此ClearSessionServlet中重写doPost方法，
        // 内容为 request.getSession().invalidate()
    }
}