function userManager(){
	//将机构字典表传上去
	findOrgDict();
	//获取角色名字
	findeRoleName();
	
	//查找用户列表
	userManagermentList();
	
	
	$("div[class='col-xs-12 col-sm-6']").html($("#addAlterDelete",window.parent.document).html());
	$("#add").click(function(){
		 $(".txt").text("");
		 $("#paddAddTxt").text("不能少于6个字符(只能包含英文字母、数字、下划线)").css("color","#333");
		 $("#ueserNameAddTxt").text("由英文字母、数字、下划线组成。").css("color","#333");
		
		 $("#ueserNameTxt").text("由英文字母、数字、下划线组成。").css("color","#333");
		 $("#proDiolagAdd").show();	
		 $("#proDiolagRevise").hide();
		 $("#proDiolagAdd").find("input").val("");
		 $("#remarkContent").val("");
	 })
	
	$("#search").click(function(){
		userManagermentList();
	})
	//增加用户
	$("#saveAdd").click(function(){
		addUserManagerment();
	})
	//删除用户
	$("#remove").click(function(){
		if(confirm('确定要删除该用户吗?')){
			deleteUser();
		} 
	})
	//修改用户
	$("#revise").click(function(){
		modifyUser();
	})
	//保存修改的用户数据
	$("#saveRevise").click(function(){
		saveModifyUser();
	})
	//验证用户名是否重复
	$("#ueserNameAdd").blur(function(){
		userRepeat();
	})
	$("#ueserName").blur(function(){
		userModifyRepeat();
	})
	
}

function findOrgDict(){
	/*
	 * 将机构字典表传上去
	 */
	$.ajax({
		url : Main.contextPath+"/UserManagermentController/orgNameDict.do",
		type : "post",
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			var datalength = result.length;
			for (var i = 0; i < datalength; i++) {
				var datas = "" + "<option value='" + result[i].orgcode
						+ "'>" + result[i].orgname + "</option>"
				$("#organizationSelect").append($(datas));
				$("#alertOrg").append($(datas));
				$("#modifyOrg").append($(datas));
			}
		}
	});
}

//获取角色的名字
function findeRoleName(){
	$.ajax({
		url : Main.contextPath+"/UserManagermentController/finderoleName.do",
		type : "post",
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			var datalength = result.length;
			for (var i = 0; i < datalength; i++) {
				var datas = "" + "<option value='" + result[i].roleId
						+ "'>" + result[i].roleName + "</option>"
				$("#alertRoleName").append($(datas));
				$("#modifyAlertOrg").append($(datas));
			}
		}
	});
}


/* 表格部分代码 */
var userManagerment_1, responsiveHelper_datatable_col_reorder, breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
function userManagermentList() {
	$("#loadingRoleManagent").css("display", "block");
	if (userManagerment_1) {
		userManagerment_1.ajax.reload();
	} else {
		userManagerment_1 = $('#datatable_col_reorder_roleManagement').dataTable({
							"searching" : false,
							"ordering" : true,// 关闭排序功能
							"pageLength" : pageSize,// 每页显示数据条数
							"sPaginationType" : 'full_numbers',// 分页格式
							"oLanguage" : oLanguage,
							"aaSorting" : [ [ 3, "asc" ] ],
							serverSide : true,
							"autoWidth" : true,
							"ajax" : {
								url : Main.contextPath+ "/UserManagermentController/userManagermentList.do",
								data : function(d) {
									return $.extend({},d,{
										"orgcode" : $("#organizationSelect").val(),
										"role" : $("#role").val(),
										"user" : $("#user").val()
										});
								},
								dataSrc : function(result) {// 回调函数
									window.parent.existsUser(result);//判断用户是否已失效
									$("#loadingRoleManagent").css("display", "none");
									/*setTimeout(function(){
										userManagerment_1.column( 1 ).visible( false );
									},0);*/
									return result.data;
								}
							},
							columnDefs : [ { // 列定义，定义checkbox在哪个列显示
								orderable : false,
								className : 'select-checkbox',
								targets : 0
							} ],
							select : {
								style : 'os',
								selector : 'td:first-child input' // 定义点击第一列，触发行选中事件
							},
							columns : [{
										"data" : "account_enabled",
										orderable : false,
										"render" : function(data, type, full,
													meta) {// 渲染，修改数据的展现形式
												return "<input style='margin-left:30%' id='ddddddee' type='checkbox' value="+data+">";}
									},{
										"data" : "number",
										orderable : false,
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:center'>"+ data + "</p>";
										}
									},{
										"data" : "user_id",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "user_name",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "user_alias",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "role_name",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},
									{
										"data" : "phone_num",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"+ data + "</p>";
										}
									},
									{
										"data" : "cellphone_num",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"+ data + "</p>";
										}
									},{
										"data" : "start_date",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"+ data + "</p>";
										}
									},{
										"data" : "end_date",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"+ data + "</p>";
										}
									},{
										"data" : "remark",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "operator",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "createDate",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"+ data + "</p>";
										}
									},{
										"data" : "updateDate",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"+ data + "</p>";
										}
									}],
							"aoColumnDefs" : [ {
								"bSortable" : false,
								"aTargets" : [ 0 ]
							} ],
							"sDom" : "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'C>r>"
									+ "t"
									+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
							"autoWidth" : true,
							"preDrawCallback" : function() {
								if (!responsiveHelper_datatable_col_reorder) {
									responsiveHelper_datatable_col_reorder = new ResponsiveDatatablesHelper(
											$('#datatable_col_reorder_roleManagement'),
											breakpointDefinition);
								}
							},
							"rowCallback" : function(nRow) {
								responsiveHelper_datatable_col_reorder
										.createExpandIcon(nRow);
							},
							"drawCallback" : function(oSettings) {
								responsiveHelper_datatable_col_reorder
										.respond();
							}

						}).api();

			// 监听表格行的选中事件
			userManagerment_1.on('select',function(e, dt, type, indexes) {
					// 选中的是行
					if (type === 'row') {
						// 令行中的第一个单元格内的checkbox被选中
						$(dt.rows(indexes).nodes().to$()[0]).find('td:first-child').find('input[type="checkbox"]').attr({checked : true});
					}
			}).on('deselect',function(e, dt, type, indexes) { // 监听表格行的非选中事件
					if (type === 'row') {
						// 令行中的第一个单元格内的checkbox不被选中
						$(dt.rows(indexes).nodes().to$()[0]).find('td:first-child').find('input[type="checkbox"]').attr({checked : false});
					}
			});

			// 监听checkbox,令表格行是否被选中
			userManagerment_1.on('click', 'tbody tr td input[type="checkbox"]',
			function(e) {
				var $checkbox = $(this);
				var tr = $checkbox.parent().parent()[0];
				if (userManagerment_1.row(tr, {selected:true}).any()) {
					userManagerment_1.row(tr).deselect();
				} else {
					userManagerment_1.row(tr).select();
				}
		});
		$("tbody tr td:nth-child(0)").css("vertical-align","middle").css("background","red")
	}
}

//添加用户
function addUserManagerment(){
	var roleName = $("#alertRoleName").val();
	var userName = $("#ueserNameAdd").val().trim();
	var userAlias = $("#userAlias").val();
	var orgName = $("#alertOrg").val();
	//密码
	var pss = $("#paddwordRegAdd").val();
	var password = hex_md5(pss);
	//确认密码
	var confirmPsw = $("#passAgainAdd").val();
	var phoneNum = $("#telAdd").val();
	var cellPhoneNum = $("#phoneAdd").val();
	var email = $("#emailAdd").val();
	var startDate = $("#startTime-0").val();
	var endDate = $("#falseTime-0").val();
	var remark = $("#remarkContent").val();
	var params = JSON.stringify([{
		"roleName":roleName,
		"userName":userName,
		"userAlias":userAlias,
		"orgName":orgName,
		"password":password,
		"phoneNum":phoneNum,
		"cellPhoneNum":cellPhoneNum,
		"email":email,
		"startDate":startDate,
		"endDate":endDate,
		"remark":remark,
		"operator":operator
	}]);
   if(startDate == "" || endDate == ""){
	   alert("请输入启用日期和失效日期");
   }else if(addSure == false){
	   alert("您填入的信息格式有误，请修改!");
   }else if(addMimaSure == false){
	   alert("您填入的信息格式有误，请修改!");
   }else if (pss !=confirmPsw){
	   alert("两次输入的密码不一样");
   }else if(userName ==""){
	   alert("用户名不能为空");
   }else if(pss == "" || confirmPsw == ""){
	   alert("请输入密码");
   }else if(userNameFormat == "0"){
	   alert("用户名格式不正确");
   }else if(userRepeat == "0"){
	   alert("用户名重复，请修改用户名");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/UserManagermentController/addUserManagerment.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				userManagermentList();
				$("#proDiolagAdd").hide();
				alert(result.msg);
			}
		});
   }
}

//删除用户数据
function deleteUser(){
	var datas = userManagerment_1.rows({
		selected : true
	}).data();
	//获取用户的id
	var userID = datas[0].user_id;
	if(datas.length == '1'){
		$.ajax({
			url : Main.contextPath+"/UserManagermentController/deleteUserManagerment.do",
			type : "post",
			data:{userID:userID},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				userManagermentList();
				alert(result.msg);
			}
		});
	}else{
		alert("请选择一条记录");
	}
}

//修改用户数据
var userID;
function modifyUser(){
	var datas = userManagerment_1.rows({selected : true}).data();
	//将用户数据放上去
	if(datas.length == '1'){
		//获取用户的id
		userID = datas[0].user_id;
		$("#modifyAlertOrg option:contains("+datas[0].role_name+")").attr("selected","selected");
		$("#ueserName").val(datas[0].user_name);
		$("#modifyUserAlisa").val(datas[0].user_alias);
		var orgName = datas[0].org_name;
		$("#modifyOrg option:contains("+orgName+")").attr("selected","selected");
		//固定电话
		$("#tel").val(datas[0].phone_num);
		$("#phone").val(datas[0].cellphone_num);
		$("#email").val(datas[0].email);
		$("#startTime-1").val(datas[0].start_date);
		$("#falseTime-1").val(datas[0].end_date);
		$("#modifyRemark").val(datas[0].remark);
		$("#paddwordReg").val("");
		
		$("#proDiolagAdd").hide();
		$("#proDiolagRevise").show();
	}
}
//保存修改的用户
function saveModifyUser(){
	var userId = userID;
	var roleName = $("#modifyAlertOrg").val();
	var userName = $("#ueserName").val().trim();
	var userAlias = $("#modifyUserAlisa").val();
	if(userAlias==""){
		userAlias=" ";
	}
	var orgName = $("#modifyOrg").val();
	//密码
	var ps = $("#paddwordReg").val();
	//确认密码
	var comfirmPsw = $("#passAgain").val();
	var password;
	if(ps ==null || ps == undefined || ps == ''|| ps =='undefined'){
		password = null;
	}else{
		password = hex_md5($("#paddwordReg").val());
	} 
	var phoneNum = $("#tel").val();
	if(phoneNum ==""){
		phoneNum=" ";
	}
	var cellPhoneNum = $("#phone").val();
	if(cellPhoneNum == ""){
		cellPhoneNum=" ";
	}
	var email = $("#email").val();
	if(email == ""){
		email=" ";
	}
	var startDate = $("#startTime-1").val();
	var endDate = $("#falseTime-1").val();
	var remark = $("#modifyRemark").val();
	if(remark == ""){
		remark=" ";
	}
	var params = JSON.stringify([{
		"userId":userId,
		"roleName":roleName,
		"userName":userName,
		"userAlias":userAlias,
		"orgName":orgName,
		"password":password,
		"phoneNum":phoneNum,
		"cellPhoneNum":cellPhoneNum,
		"email":email,
		"startDate":startDate,
		"endDate":endDate,
		"remark":remark,
	}]);
	if(userName == ""){
		alert("请输入用户名");
	}else if(startDate == "" || endDate == ""){
		alert("请输入启用日期和失效日期");
	}else if(ps != comfirmPsw){
		alert("两次输入的密码不一样");
	}else if(modifySure == false){
		alert("您填入的信息格式有误，请修改!");
	}else if(modifyMimaSure == false){
		alert("您填入的信息格式有误，请修改!")
	}else if (modifyUserNameFormat == "0"){
		alert("用户名格式不正确！");
	}else{
	   $.ajax({
			url : Main.contextPath+"/UserManagermentController/modifyUserManagerment.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				userManagermentList();
				alert(result.msg);
				$("#proDiolagRevise").hide();
			}
		});
	}
}
var userNameRepeat;
var userNameFormat;
//验证用户名是否重复
//用户名验证 
function userRepeat(){
	var userName = $("#ueserNameAdd").val();
	var reg1 = /^\w{1,}$/;
	if(!reg1.test(userName)){
		$("#ueserNameAddTxt").text("格式有误").css("color","red");
		userNameFormat = "0";
	}else{
		$.ajax({
			url : Main.contextPath+"/UserManagermentController/userRepeat.do",
			type : "post",
			data:{userName:userName},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				if(result.msg=="1"){
					$("#ueserNameAddTxt").text("该用户名已存在").css("color","red");
					userNameRepeat = "0";
				}else{
					$("#ueserNameAddTxt").text("验证通过").css("color","green");
					userNameRepeat = "1";
				}
			}
		});
	 	userNameFormat = "1";
	}
}
var modifyUserNameFormat;
function userModifyRepeat(){
	var datas = userManagerment_1.rows({selected : true}).data();
	var userNameCompare = datas[0].user_name;
	var userName = $("#ueserName").val();
	var reg1 = /^\w{1,}$/;
	if(!reg1.test(userName)){
		$("#ueserNameTxt").text("格式有误").css("color","red");
		modifyUserNameFormat="0";
	}else{
		$.ajax({
			url : Main.contextPath+"/UserManagermentController/userRepeat.do",
			type : "post",
			data:{userName:userName},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				if(result.msg=="1"){
					if(userName != userNameCompare){
						$("#ueserNameTxt").text("该用户名已存在").css("color","red");
					}
				}else{
					$("#ueserNameTxt").text("验证通过").css("color","green");
				}
				
			}
		});
		modifyUserNameFormat = "1";
	}	
}
