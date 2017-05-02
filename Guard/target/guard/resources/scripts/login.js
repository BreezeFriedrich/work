//登录事件处理
function func_checklogin(){
	
	    //清除提示信息
	    $("#account_msg").text("");
	    $("#password_msg").text("");
		//获取用户名和密码
		var account = $("#account").val().trim();
		var password = $("#pwd").val().trim();
		//数据格式检查
		var check = true;//通过校验
		if(account==""){
			$("#account_msg").text("用户名不能为空");
			check = false;
		}else if(account.length<3||account.length>11){
			$("#account_msg").text("用户名长度3~11");
			check = false;
		}
		if(password==""){
		    $("#password_msg").text("密码不能为空");
		    check = false;
		}else if(password.length<6||password.length>16){
		    $("#password_msg").text("密码长度6~16");
		    check = false;
		}
		//通过检查发送Ajax请求
		if(check){
		  //采用HTTP Basic Autentication模式传输
		  var msg = account+":"+password;
		  //将消息采用base64编码
		  var base_msg = Base64.encode(msg);
		  //将身份信息写入HTTP Header发送ajax请求传输
		 //  alert(88);
		   $.ajax({
		     url:"user/logIn.do",
		     type:"post",
		     dataType:"json",
		     beforeSend:function(xhr){//发送请求前执行
		     	xhr.setRequestHeader("Authorization","Basic "+base_msg);
		     },
		     success:function(result){//result是服务器返回的NoteResult结构的json对象
		        if(result.status==0){
		          //成功,将用户信息写入Cookie
		          var userId = result.data.ownerName;
		          var token = result.data.token;
		          addCookie("ownerName",userId,0.5);
		          addCookie("token",token,0.5);
		          addCookie("ownerPhoneNumber",account,0.5);
		          
		          window.location.href="../../WEB-INF/jsp/main.jsp";
		        }else if(result.status==1){
		          //用户名错误
		          $("#account_msg").text(result.msg);
		        }else if(result.status==2){
		          //密码错误
		          $("#password_msg").text(result.msg);
		        }
		     },
		     error:function(){
		     	alert("系统错误,登录失败");
		     }
		   });
		}
	};
