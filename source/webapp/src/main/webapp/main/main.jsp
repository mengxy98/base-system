<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commonJSP/page.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn" style="background: #fff;">
<head>
<meta charset="utf-8">
<title>压实联合作业管理平台</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<jsp:include page="/commonJSP/comcss.jsp"></jsp:include>
<script src="<%=request.getContextPath()%>/js/libs/jquery-2.1.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/app/main.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/utils/md5.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/mynav.css" />
<script type="text/javascript">
      var userName='${userName}'; 
      var defaultPage="${user.defaultPage}"; 
      window.onload=function(){
    	  if(defaultPage.length > 0){
    		  if(defaultPage.indexOf("extend:")>=0){
    			  $("#iframePage").attr("src","<%=path%>/"+defaultPage.substr(defaultPage.indexOf(":")+1,defaultPage.length));
    		  }else if(defaultPage.indexOf("toopen:")>=0){
    					window.open(defaultPage.substr(defaultPage.indexOf(":")+1,defaultPage.length),'_blank');
    		  }else{
    			  $("#iframePage").attr("src",defaultPage);
    		  }
    		 
    	  } 
		$("#exist").click(function(){
			$("#quitContainer").show();
			$("#modifySure").click(function(){
				exitSystem();
			});
			$(".cancel").click(function(){
				$("#quitContainer").hide();
			});
			$(".close").click(function(){
				$("#quitContainer").hide();
			});

		});	
		
		$("#hide-menu").click(function(){
			$("#hide-menu i").toggleClass("glyphicon glyphicon-list glyphicon glyphicon-align-justify");
			if($("#hide-menu a").attr("title")=="隐藏菜单"){
				$("#hide-menu a").attr("title","打开菜单");
			}else if($("#hide-menu a").attr("title")=="打开菜单"){
				$("#hide-menu a").attr("title","隐藏菜单");
			}
		});
	};
	
	$(document).ready(function(){
        var left=($(window).width()-$(".content").width())/2;
        $(".content").css("left",left);
        var top=($(window).height()-$(".content").height())/2;
        $(".content").css("top",top);
        
    });
	//当调整浏览器窗口的大小时，发生 resize 事件。
	$(window).resize(function() {
		var left=($(window).width()-$(".content").width())/2;
        $(".content").css("left",left);
        var top=($(window).height()-$(".content").height())/2;
        $(".content").css("top",top);
	});
	var numberCount = 0;
	//判断用户是否已失效
	function existsUser(result){
		if(null != result && result.isexit == "-1"){
			if(numberCount < 1){
				document.getElementById('modifyContent').innerHTML = "当前用户已失效，请重新登录!";
				document.getElementById('modifyCancel').remove();
				document.getElementById('modifyClose').remove();
				document.getElementById('modifySure').style.marginRight="0";
				$("#quitContainer").show();
			//	alert("当前用户已失效，请重新登录!");
			}
			numberCount++;
			$("#modifySure").click(function(){
				window.location.href = "<%=path%>/login.jsp";
			});
			return;
		}
	}
	
	function exitSystem(){
		location.href = Main.contextPath+"/j_spring_security_logout";
		<%-- $.ajax({
			//url:Main.contextPath+"/userController/exit.do",
			url:Main.contextPath+"/j_spring_security_logout",
			type:"post",
			dataType:"json",
			success:function(result){
				if(result){
					window.location.href = "<%=path%>/login.jsp";
				}
			}
		}); --%>
	}
</script>
<style>
#main1 {
	margin-left: 0px;
	padding: 0;
	padding-bottom: 52px;
	min-height: 500px;
	position: absolute;
}
#iframePage {
	border: none;
	width: 100%;
	position: absolute;
	top: 0;
	left: 0;
}

.container {
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 905;
}

.Mask {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #ddd;
	opacity: 0.6;
}

.content {
	position: absolute;
	width: 500px;
	height: 150px;
	background: #fff;
	z-index: 906;
	border-radius: 10px;
	box-shadow: #bbb 5px 5px 5px;
	text-align: center;
	font-size: 18px;
	font-weight: bold;
}

.tip {
	margin-top: 30px;
}

.sure {
	width: 70px;
	margin-right: 100px;
	margin-top: 15px;
}

.cancel {
	width: 70px;
	margin-top: 15px;
}

.close {
	position: absolute;
	top: 10px;
	right: 15px;
}
</style>
</head>
<body style="background: #fff;">

	<!-- #HEADER -->
	<header id="header"
		style="background-image: url(<%=path%>/images/header.jpg);background-size: 100% 100%;">
		<div id="logo" style="height: 32px;width:40%;">
			<!-- PLACE YOUR LOGO HERE -->
			<a href="javascript:void(0);"
				style="color: #fff; font-size: 1.8em; font-weight: bold;">压实联合作业管理平台</a>
			<!-- END LOGO PLACEHOLDER -->
		</div>
		<div class="pull-right" style="color:white;line-height:1em;height: 32px;">
				<p id="welcome" style="margin-left: 15%; margin-top: 5%;">
					<span class="glyphicon glyphicon-star-empty"></span> 
					<span>登陆时间:</span> 
					<span id="loginWeek"></span>  
					<span id="loginUserTime" style="margin-left: 6px;"></span>&emsp;&emsp;
					<span>欢迎登陆</span>&emsp;
					<span id="loginUserNameCon" class="glyphicon glyphicon-user" style="cursor:pointer"><span id="loginUserName">${userName}</span>&emsp;</span>
					
					<a id="exist"
						style="display: inline-block; float: right; margin-left: 18px;margin-right: 8px; margin-top: 0; color: #fff;"
						href="javascript:void(0);" title="退出登录"> <span
						style="font-size: 1.5em;" class="glyphicon glyphicon-off"> </span>
					</a>
				</p>
		</div>
	</header>
	<header id="header" style="background-size: 100% 100%;">
	    <nav style="width:100%;">
			<ul id="tree" style="float:left;height:26px;color:black;list-style:none;margin-left:14px;">
			</ul>	
		</nav> 
	</header>
	
	
	
	<!-- END HEADER -->

	<!-- #NAVIGATION -->
	<!-- Left panel : Navigation area -->
	<!-- Note: This width of the aside area can be adjusted through LESS variables -->
	<aside id="left-panel" style="background: #1559AC;display:none;min-height:90%">
	</aside>
	
	<!-- END NAVIGATION -->
	<!--弹出框 -->
	<div class="container" id="quitContainer">
		<div class="Mask"></div>
		<div class="content">
			<p class="tip" id="modifyContent">您真的要退出吗？</p>
			<button class="btn btn-primary sure" id="modifySure">确认</button>
			<button class="btn btn-primary cancel" id="modifyCancel">取消</button>
			<button type="button" class="close" id="modifyClose">x</button>
		</div>
	</div>
	<!-- 确定不退出 -->
	<div class="container" id="quitContainer1">
		<div class="Mask"></div>
		<div class="content">
			<p class="tip" id="modifyContentEdit"></p>
			<button class="btn btn-primary sure" id="modifySureEdit"
				style="margin: 25px auto">确定</button>
			<button type="button" class="close" id="modifyCloseEdit">x</button>
		</div>
	</div>
	<!-- 确定退出 -->
	<div class="container" id="quitContainer2">
		<div class="Mask"></div>
		<div class="content">
			<p class="tip" id="modifyContentQuit"></p>
			<button class="btn btn-primary sure" id="modifySureQuit"
				style="margin: 25px auto">确定</button>
			<button type="button" class="close" id="modifyCloseQuit">x</button>
		</div>
	</div>



	<!-- MAIN PANEL -->
	 <div id="main" role="main">
		<iframe src="" name="iframePage" id="iframePage" ></iframe>
	</div>

	<!-- 修改用户信息 -->

	<div class="containerAdd11"
		style="display: none; position: absolute; top: 0; color: #333; left: 0; background: rgba(0, 0, 0, 0.5);; height: 100%; width: 100%; overflow: auto;">
		<div
			style="position: fixed; background: #fff; width: 65%; margin: 10% 17.5% 2% 25.5%; padding-bottom: 1%; height: 65%; overflow: auto;">
			<div
				style="position: fixed; top: 1px; left: 0; z-index: 1000; width: 62%; padding: 1% 0; margin: 10% 27%; background: #fff; border-bottom: 1px solid #ddd;">
				<button id="uesRev" type="button" class="btn btn-primary">
					修改用户信息</button>
				<button id="pswRev" type="button" class="btn btn-primary">
					修改密码</button>
				<span id="cl" class="glyphicon glyphicon-remove close closeRevise"
					style="float: right; margin: 1% 1% 1% 0; cursor: pointer" id=""></span>
			</div>

			<ul id="reMsg"
				style="position: relative; padding: 2em 0; margin-top: 8%; display: block">
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">角色：</span>
						<input id="userRoleMain" disabled="disabled"
							style="float: left; width: 48%" type="text" value="" /> <span
							class="txt" id="ueserRoleTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">用户名：</span>
						<input id="ueserNameMain" disabled="disabled"
							style="float: left; width: 48%" type="text" value="" /> <span
							class="txt" id="ueserNameTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">用户含义：</span>
						<input id="editUserAlias" disabled="disabled"
							style="float: left; width: 48%" type="text" value="" /> <span
							class="txt"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">固定电话：</span>
						<input disabled="disabled" id="telMain"
							style="float: left; width: 48%" type="text" value="" /> <span
							class="txt" id="telTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">移动电话：</span>
						<input disabled="disabled" id="phoneMain"
							style="float: left; width: 48%" type="text" value="" /> <span
							class="txt" id="phoneTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">E-mail：</span>
						<input disabled="disabled" id="emailMain"
							style="float: left; width: 48%" type="text" value="" /> <span
							class="txt" id="emailTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">启用日期：</span>
						<input disabled="disabled"
							style="float: left; width: 48%; opacity: .3; padding-left: .2em"
							class="Wdate time" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime-0\',{m:1});}'})"
							id="startTime-1"> <span class="txt"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">失效日期：</span>
						<input disabled="disabled"
							style="float: left; opacity: .3; width: 48%; padding-left: .2em"
							class="Wdate time" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime-0\',{m:1});}'})"
							id="falseTime-1"> <span class="txt"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>
				<li>
					<p style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">备注：</span>
						<textarea id="editRemark" disabled="disabled"
							style="float: left; width: 48%; overflow: hidden; min-height: 160px">
					
					</textarea>
						<span class="txt"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
					</p>
				</li>

				<li style="height: 120px"></li>
				<li id="ann" style="display: none">
					<div
						style="position: fixed; top: 74.6%; width: 62%; height: 10%; z-index: 1000; background: #fff; left: 26%;">
						<div style="width: 60%; margin-top: 9px; margin-left: 20%;">
							<p
								style="overflow: hidden; width: 60%; text-align: center; margin-left: 20%;">
								<button id="saveRevise1Main" type="button"
									class="btn btn-primary" style="float: left">
									<span class="glyphicon glyphicon-saved"> </span> 保存
								</button>
								<button id="cancleRevise1Main" type="button"
									class="btn btn-primary" style="float: right">
									<span class="glyphicon glyphicon-remove-circle"> </span> 取消
								</button>
							</p>
						</div>
					</div>

				</li>

			</ul>

			<!-- 修改密码部分 -->
			<ul id="rePsw"
				style="position: relative; padding: 2em 0; margin-top: 8%; display: none">
				<li style="min-height: 25px; margin-bottom: 10px">
					<div style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">原密码：</span>
						<p style="float: left; width: 48%; position: relative">
							<input id="oldPassword" style="width: 100%; opacity: 0.7"
								type="password" value="" />
						</p>

						<span class="txt" id="oldPasswordTxt"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em">
						</span>
					</div>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<div style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">新密码：</span>
						<p style="float: left; width: 48%; position: relative">
							<input id="newPasswordMain" style="width: 100%; opacity: 0.7"
								type="password" value="" />
						</p>

						<span id="newPasswordTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em;">
							不能少于6个字符(只能包含英文字母、数字、下划线) </span>
					</div>
				</li>
				<li style="min-height: 25px; margin-bottom: 10px">
					<div style="overflow: hidden; width: 100%;">
						<span style="float: left; width: 20%; text-align: right">确认密码：</span>
						<p style="float: left; width: 48%; position: relative">
							<input id="newPasswordAgainMain"
								style="width: 100%; opacity: 0.7" type="password" value="" />
						</p>
						<span class="txt" id="newPasswordAgainTxtMain"
							style="float: left; width: 30%; margin-left: 1%; font-size: .75em">
						</span>
					</div>
				</li>

				<li>
					<div
						style="position: fixed; top: 76%; width: 64%; height: 50px; z-index: 1000; background: #fff; left: 26%;">
						<div style="width: 60%; margin-top: 9px; margin-left: 20%;">
							<p
								style="overflow: hidden; width: 60%; text-align: center; margin-left: 20%;">
								<button id="saveReviseMain" type="button"
									class="btn btn-primary" style="float: left">
									<span class="glyphicon glyphicon-saved"> </span> 保存
								</button>
								<button id="cancleReviseMain" type="button"
									class="btn btn-primary" style="float: right">
									<span class="glyphicon glyphicon-remove-circle"> </span> 取消
								</button>
							</p>
						</div>
					</div>

				</li>
			</ul>
		</div>
	</div>

 <div id="addAlterDelete" style="display:none">                                                
                                          <form class="form-inline" id="btn_grooup">
                                           	<div class="form-group">
                                                  <button type="button" class="btn btn-primary" id="add">
                                                  	<span class="fa fa-plus"> </span>
                                                  	添加
                                                  </button>
                                              </div>
                                              <div class="form-group">
                                                  <button type="button" class="btn btn-primary" id="revise">
                                                       <span class="glyphicon glyphicon-pencil"> </span>
                                                        	修改
                                                   </button>
                                              </div>                                              
                                              <div class="form-group">
                                                  <button type="button" class="btn btn-primary" id="remove">
                                                  	<span class="fa fa-trash-o"> </span>
                                                  	删除
                                                  </button>
                                              </div>
                                          </form>
        </div>
	<jsp:include page="/commonJSP/commonjs.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			var h = $("#left-panel").height();
			$("#iframePage").height(h);
		});
		//当调整浏览器窗口的大小时，发生 resize 事件。
		$(window).resize(function() {
			var h = $("#left-panel").height();
			$("#iframePage").height(h);
		});
	
		//点击修改箭头指向
		$("#tree").on("click","#li",function(event){
			       //组织冒泡传递给父级别节点上
					event.stopPropagation();
					$("li").removeClass("active");
					$(this).addClass("active");
		});

         
			$("#left-panel li").each(function() {
				$(this).click(function() {
					$(".containerAdd11").css("display", "none")
				})
			})
	
		/*修改用户信息部分  */
		$("#cl").click(function() {
			$(".containerAdd11").css("display", "none")
		})
		$("#cancleRevise1Main").click(function() {
			$(".containerAdd11").css("display", "none")
		})
		$("#cancleReviseMain").click(function() {
			$(".containerAdd11").css("display", "none")
		})

		$("#saveReviseMain").click(function() {
			if (checkPwd() & checkMatchPwd()) {
				if (confirm("真的要修改吗？")) {
					updatePwd();
				}
			}
		})
		$("#saveRevise1Main").click(function() {
			if (checkTel() & checkPhone() & checkEmail()) {
				if (confirm("真的要修改吗？")) {
					updateUserInfo();
				}
			}
		})

		$("#loginUserNameCon").click(
				function() {

					getUserInfo();

					$("#reMsg #telMain,#reMsg #phoneMain,#reMsg #emailMain")
							.attr("disabled", true)
					$(".containerAdd11 .txt").text("");

					$(".containerAdd11").css("display", "block");
					$("#newPasswordTxtMain").text("不能少于6个字符(只能包含英文字母、数字、下划线)")
							.css("color", "#333")
					$("#reMsg").css("display", "block");
					$("#ann").css("display", "none");
					$("#rePsw").css("display", "none");
				})
		$("#uesRev")
				.on(
						"click",
						function() {
							$(
									"#reMsg #telMain,#reMsg #phoneMain,#reMsg #emailMain")
									.attr("disabled", false)

							getUserInfo();

							$(
									"#reMsg #telTxtMain,#reMsg #phoneTxtMain,#reMsg #emailTxtMain")
									.text("")
							$("#reMsg").css("display", "block");
							$("#ann").css("display", "block");

							$("#rePsw").css("display", "none");

						})
		$("#pswRev").on(
				"click",
				function() {
					$("#newPasswordTxtMain").text("不能少于6个字符(只能包含英文字母、数字、下划线)")
							.css("color", "#333")
					$("#reMsg").css("display", "none");
					$("#ann").css("display", "none");

					$("#rePsw").css("display", "block");
				})
		/* 固定电话验证 */
		$("#telMain").blur(function() {
			checkTel();
		})
		$("#telMain").focus(
				function() {
					$("#telTxtMain").text("输入固定电话，由XXX-XXXXXXXX组成").css(
							"color", "#333");
				})
		/* 移动电话验证 */
		$("#phoneMain").blur(function() {
			checkPhone();
		})
		$("#phoneMain").focus(function() {
			$("#phoneTxtMain").text("输入手机号码").css("color", "#333");
		})
		/*E-mail验证  */
		$("#emailMain").blur(function() {
			checkEmail();
		})
		$("#emailMain").focus(function() {
			$("#emailTxtMain").text("输入邮箱").css("color", "#333");
		})
		/*密码验证  */
		$("#newPasswordMain").blur(function() {
			checkPwd();
		})
		$("#newPasswordMain").focus(
				function() {
					$("#newPasswordTxtMain").text("不能少于6个字符(只能包含英文字母、数字、下划线)")
							.css("color", "#333");
				})
		/* 确认密码 */
		$("#newPasswordAgainMain").blur(function() {
			checkMatchPwd()
		})
		$("#newPasswordAgainMain").focus(function() {
			$("#newPasswordAgainTxtMain").text("再次输入密码.").css("color", "#333");
		})
	</script>

</body>

</html>