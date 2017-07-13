var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

$(".leftsidebar dt ").css({
	"background-color": "rgb(178.197.258)"
});

// $(".leftsidebar dt img").attr("src", "${pageContext.request.contextPath}/resources/styles/images/left/select_xl01.png");
//!!!该处src路径正确,是因为JSP加载时处理了该EL表达式;而后面js函数中的EL表达式,在JSP初始化时并没用触发处理.
$(".leftsidebar dt img").attr("src", projectName+"/resources/styles/images/left/select_xl01.png");

$(function () {
	leftsideBarInit();
	/*
	if(getCookie("ownerName")){
		document.getElementById("loginStatus").innerHTML=getCookie("ownerName")+"|"+getNowFormat();
	}else{
		document.getElementById("loginStatus").innerHTML="未登录";
	}
	*/
	document.getElementById("loginStatus").style.cssText="line-height:15px;font-size:14px;color:#7f93ad";
	//安全退出
	document.getElementById("safetylogout").onclick=function(){
		clearCookie();
//		window.history.forward(1);
// 		window.location.href="http://localhost/Lock/login.jsp";
        window.location.href="logout";
	};
});

//core_____________________________________________________________________________________________________________

function leftsideBarInit() {
	$(".leftsidebar dd").hide();
	$(".leftsidebar dt").click(function() {
		$(".leftsidebar dt").css({
			"background-color": "rgb(178.197.255)"
		})
		$(this).css({
			"background-color": "rgb(178.197.255)"
		});
		$(this).parent().find('dd').removeClass("menu_option");
		// $(".leftsidebar dt img").attr("src", "../../resources/styles/images/left/select_xl01.png");
        $(".leftsidebar dt img").attr("src", projectName+"/resources/styles/images/left/select_xl01.png");
		$(this).parent().find('img').attr("src", projectName+"/resources/styles/images/left/select_xl.png");
		$(".menu_option").slideUp();
		$(this).parent().find('dd').slideToggle();
		$(this).parent().find('dd').addClass("menu_option");
	});
}

function iframe(page){
    // document.getElementById("iframe").setAttribute("src","dispatcher/"+page);
    document.getElementById("iframe").setAttribute("src",page);
    // $("#iframe").attr("src","dispatcher/"+page);
}