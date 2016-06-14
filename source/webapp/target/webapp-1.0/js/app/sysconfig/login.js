window.onload = function(){ 	
	/*********登录框居中显示****************/
	var h1 = document.documentElement.clientHeight;
	var h2 = $(".login_box")[0].offsetHeight;
	$(".login_box")[0].style.marginTop = (h1 - h2) / 2-50 + "px";
	$(".login_box").css("visibility","visible");
	//当调整浏览器窗口的大小时，发生 resize 事件。
	$(window).resize(function() {
		var h3 = document.documentElement.clientHeight;
		var h4 = $(".login_box")[0].offsetHeight;
		$(".login_box")[0].style.marginTop = (h3 - h4) / 2-50 + "px";
		
	});
	
	/************点击登录ajax请求**************/
	var userName_cookie = window.atob(getCookie("userName"));
	var password_cookie = window.atob(getCookie("password"));
	var checked = getCookie("checked");
	if(userName_cookie!=null&&password_cookie!=null&&checked!=null){
		$("#userName").val(userName_cookie);
		$("#userPasswd").val(password_cookie);
		$(".checkbox label input").attr("checked",checked);
		$("#checkNum").val("");
	}else{
		$("#userName").val("");
		$("#userPasswd").val("");
		$(".checkbox label input").attr("checked",false);
		$("#checkNum").val("");
	}
	$("#btn-login").click(function(){
		var userName = $("#userName").val();
		var userPasswd = $("#userPasswd").val();
		var checkNum = $("#checkNum").val();
		var ifCheck = document.getElementById("remember").checked;
		if(userName==""){
			$("#empty_userName").show();
			return false;
		}
		if(userPasswd==""){
			$("#empty_password").show();
			return false;
		}
		if(checkNum==""){
			$("#empty_code").show();
			return false;
		}
		$("#btn-login").attr("disabled","");
		$("#btn-login").text("登录中..");
		$.ajax({
			url : Main.contextPath+"/j_spring_security_check",
			data : {
				"j_username" : userName,
				"j_password" : hex_md5(userPasswd),
				"_spring_security_remember_me" : ifCheck,
				"capatcha":checkNum,
				"ajax":true
			},
			type : "post",
			dataType : "json",
			success : function(result) {
                if(result.success) {
                	if(ifCheck==true){
						var userNameCode = window.btoa(userName);
						var userPasswdCode = window.btoa(userPasswd);
						addCookie("userName",userNameCode,7);
						addCookie("password",userPasswdCode,7);
						addCookie("checked",true,7);
					}else{
						delCookie("userName");
						delCookie("password");
					}
 		   			location.href = result.contents.targetUrl;
 		   		}else{
 		   			if(result.failure){
 		   			    $("#btn-login").text("登录");
						$("#btn-login").removeAttr("disabled");
						$("#msgcode").text(result.contents.error);
						$("#msgcode").show();
						clearInputText();
						$("#checkNum").parent().parent().find("img").click();
 		   				
 		   			}
	 		 
 		   		}
 		   		
                
				
			},
			error : function(e) {
				$("#btn-login").removeAttr("disabled");
				$("#btn-login").text("登录");
				console.log(e);
				console.log("ajax error");
			}
		});
	});
	
	$("#userName").focus(function(){
		$("#empty_userName").hide();
		$("#msgcode").hide();
	});
	$("#userPasswd").focus(function(){
		$("#empty_password").hide();
		$("#msgcode").hide();
	});
	$("#checkNum").focus(function(){
		$("#empty_code").hide();
		$("#msgcode").hide();
	});
}
//清空用户名、密码、验证码输入框
function clearInputText(){
	$("#userName").val("");
	$("#userPasswd").val("");
	$("#checkNum").val("");
}
//点击回车进行登录
function keyLogin(event){
	if(event.keyCode == 13)
		$("#btn-login").click();
}