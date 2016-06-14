$(function(){
	$("#modifySureQuit,#modifyCloseQuit").click(function(){
		exitSystem();
	});
	
	$("#modifySureEdit,#modifyCloseEdit").click(function(){
		$("#quitContainer1").hide();
	});
	//生成导航栏
	queryData();
});

//点击修改iframe的url
function changePage(url) {
	if(url.indexOf("toopen")>=0){
		url=url.substr(url.indexOf(":")+1,url.length);
		window.open(url,'_blank');
		return;
	}
	$('iframe').attr('src',url);
}

//查询菜单
function queryData(){
	$.ajax({
		url : Main.contextPath + "/menuManagementController/queryMenuData.do",
		type : "post",
		dataType : "json",
		data:{
			"userName":userName
			},
		success : function(result) {
			var length=result.length;
			if(result.length>0){
				result = result.sort( function(a, b){
				    return a.SORT - b.SORT; //按每个数组项下的sort做排序
				});
				//生成所有的一级目录
				var dara=[];
				for(var i=0;i<length;i++){
					if(result[i].PARENT_ID==4&&result[i].VISIBILITY=="true"){
						if(result[i].LEAF=="true"){
							var defaultCss = "";
							//设置默认选中菜单
							if(result[i].HREF_TARGET == defaultPage){
								defaultCss ='class="active"';
							}
							var href=result[i].HREF_TARGET;
							if(href!=""&&href!=null){
								if(href.indexOf("extend:")>=0){
									href=href.substr(href.indexOf(":")+1,href.length);
									href=Main.contextPath+"/"+href;
								}
							}
							$("#tree").append('<li id="li" '+defaultCss+'  onclick="changePage(\''+href+'\')">'
						            + '<a href="javascript:void(0)">'+result[i].TEXT+'</a></li>');
						}else{
							$("#tree").append('<li><a  href="javascript:void(0)"><span class="menu-item-parent">'+
									result[i].TEXT+'</span></a><ul pid='+result[i].ID+'></ul></li>');
						}
					}else{
						dara.push(result[i]);
					}
				}
				//控制while循环终止的开关
				result = dara;
				var flag = true;
				var dataArr= [];
				var black=[];
				//从一级目录开始向下检索子菜单的循环
				while (flag){
					//判断还有没有子节点的开关，如果检索完毕后仍然是true说明已经没有子节点了结束循环
					var right = true;
					for(var j=0;j<result.length;j++){
						var white=true;
						for(var k=0;k<black.length;k++){
							if(black[k]==result[j].PARENT_ID){
								white=false;
							}
						}
						//判断如果能找到父节点就挂到树上
						if($("#tree ul[pid="+result[j].PARENT_ID+"]").length>0&&white==true&&result[j].VISIBILITY=="true"){
							//判断如果是叶子节点就是菜单
							if(result[j].LEAF=="true"){
								var defaultCss = "";
								//设置默认选中菜单
								if(result[j].HREF_TARGET == defaultPage){
									defaultCss ='class="active"';
								}
								var href=result[j].HREF_TARGET;
								if(href!=""&&href!=null){
									if(href.indexOf("extend:")>=0){
										href=href.substr(href.indexOf(":")+1,href.length);
										href=Main.contextPath+"/"+href;
									}
								}
								$("#tree ul[pid="+result[j].PARENT_ID+"]").append('<li id="li" '+defaultCss+'  onclick="changePage(\''+href+'\')">'
					            + '<a href="javascript:void(0)">'+result[j].TEXT+'</a></li>');
							}else{
								//不是叶子节点就是目录
								$("#tree ul[pid="+result[j].PARENT_ID+"]").append('<li>'
					                       + '<a href="javascript:void(0)">'+result[j].TEXT+'</a><ul pid='+result[j].ID+'></ul></li>');
							}
							right = false;
						}else{
							if(result[j].VISIBILITY=="true"){
								black.push(result[j].PARENT_ID);
							}
							
							//还没挂到树上的节点放到新数组里继续下一轮循环找在找父节点
							dataArr.push(result[j]);
						}
					}
					result = dataArr;
					dataArr=[];
					black=[];
					if(right){
						flag = false;
					}
				  }
				//待导航栏生成后，加载控件
				initApp.leftNav();
				$("#tree ul").hide();
				//默认打开第一个目录
				$("#tree li:eq(0) ul:eq(0)").show();
				
			}
		}
	});
}



/**
 * 获取用户信息
 */
function getUserInfo(){
	var userName = $.trim($("#loginUserName").text());
	
	$.ajax({
		url : Main.contextPath+"/userController/getUserByName.do",
		type : 'POST',
		data : {"userName":userName},
		dataType : 'json',
		success : function(result){
			$("#userRoleMain").val(result.ROLE_NAME);
			$("#ueserNameMain").val(result.USER_NAME);
			$("#editUserAlias").val(result.USER_ALIAS);
			$("#telMain").val(result.PHONE_NUM);
			$("#phoneMain").val(result.CELLPHONE_NUM);
			$("#startTime-1").val(result.START_DATE);
			$("#falseTime-1").val(result.END_DATE);
			$("#editRemark").text(result.REMARK);
			$("#emailMain").val(result.EMAIL);
		}
	});
}

/**
 * 修改用户信息
 */
function updateUserInfo(){
	var userName = $.trim($("#loginUserName").text());
	var PHONE_NUM = $.trim($("#telMain").val());
	var CELLPHONE_NUM = $.trim($("#phoneMain").val());
	var EMAIL = $.trim($("#emailMain").val());
	
	$.ajax({
		url : Main.contextPath+"/userController/updateUserInfo.do",
		type : 'POST',
		data : {
			"userName":userName,
			"PHONE_NUM":PHONE_NUM,
			"CELLPHONE_NUM":CELLPHONE_NUM,
			"EMAIL":EMAIL
			},
		dataType : 'json',
		success : function(result){
			if(result.msg=="success"){
				$("#modifyContentEdit").text("用户信息修改成功");
				$("#quitContainer1").show();
			}else if(result.msg=="faild"){
				$("#modifyContentEdit").text("用户信息修改失败，请稍后再试");
				$("#quitContainer1").show();
			}
		}
	});
}

function updatePwd(){
	var userName = $.trim($("#loginUserName").text());
	var pwd = hex_md5($.trim($("#oldPassword").val()));
	var newPwd = hex_md5($.trim($("#newPasswordMain").val()));
	
	$.ajax({
		url : Main.contextPath+"/userController/updatePwd.do",
		type : 'POST',
		data : {"userName":userName,"pwd":pwd,"newPwd":newPwd},
		dataType : 'json',
		success : function(result){
			if(result.msg=="success"){
				$("#modifyContentQuit").text("密码修改成功，点击确定重新登录");
				$("#quitContainer2").show();
			}else if(result.msg=="faild"){
				$("#modifyContentEdit").text("密码修改失败，请稍后再试");
				$("#quitContainer1").show();
			}else if(result.msg=="wrong pwd"){
				$("#modifyContentEdit").text("原密码错误，请重新输入密码");
				$("#quitContainer1").show();
			}
		}
	});
}

function checkTel(){
	var val=$("#telMain").val()
	var reg2 = /^($$\d{3,4}-)|(\d{3,4}-)\d{7,8}$/;
	if(!reg2.test(val)){
		$("#telTxtMain").text("格式有误").css("color","red");
		return false;
	}else{
		$("#telTxtMain").text("验证通过").css("color","green");
		return true;
	}
}

function checkPhone(){
	var val=$("#phoneMain").val()
	var reg3 = /^1[3,4,5,7,8]\d{9}$/;
	if(!reg3.test(val)){
		$("#phoneTxtMain").text("格式有误").css("color","red");
		return false;
	}else{
		$("#phoneTxtMain").text("验证通过").css("color","green");
		return true;
	}
}

function checkEmail(){
	var val=$("#emailMain").val()
	var reg4 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(!reg4.test(val)){
		$("#emailTxtMain").text("格式有误").css("color","red");
		return false;
	}else{
		$("#emailTxtMain").text("验证通过").css("color","green");
		return true;
	}
}

function checkPwd(){
	var val=$("#newPasswordMain").val()
	var reg = /^\w{6,}$/;
	if(!reg.test(val)){
		$("#newPasswordTxtMain").text("格式有误").css("color","red");
		return false;
	}else if(val==""){
		$("#newPasswordTxtMain").text("格式有误").css("color","red");
		return false;
	}
	else{
		$("#newPasswordTxtMain").text("验证通过").css("color","green");
		return true;
	}
}

function checkMatchPwd(){
	var val=$("#newPasswordAgainMain").val()
	
	var reg = $("#newPasswordMain").val();
	if(val!=reg){
		$("#newPasswordAgainTxtMain").text("两次输入不一致").css("color","red");
		return false;
	}else if(""){
		$("#newPasswordTxtMain").text("格式有误").css("color","red");
		return false;
	}
	else{
		$("#newPasswordAgainTxtMain").text("密码正确").css("color","green");
		return true;
	}
}

//function quitSystem(
//	$.ajax({
//		url:Main.contextPath+"/userController/exit.do",
//		type:"post",
//		dataType:"json",
//		success:function(result){
//			if(result){
//				window.location.href = path+"/login.jsp";
//			}
//		}
//	});
//}
