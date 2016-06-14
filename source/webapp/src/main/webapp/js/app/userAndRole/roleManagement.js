$(function() {
	//加载默认页面
	$.ajax({
		url : Main.contextPath + "/roleManagementController/queryDefaultPage.do",
		type : "post",
		async: false, 
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			var datalength = result.length;
			for (var i = 0; i < datalength; i++) {
				var datas = "" + "<option value='"
						+ result[i].HREF_TARGET + "'>"
						+ result[i].TEXT + "</option>"

				$("#defaultPageAdd").append($(datas));
				$("#defaultPageUpdata").append($(datas));
			}
		}
	});
	
	
	// 查询数据
	queryData();
	$("#search").click(queryData);
	
	$("div[class='col-xs-12 col-sm-6']").html($("#addAlterDelete",window.parent.document).html());
	$("#btn_grooup").append('<div class="form-group"><button type="button" class="btn btn-primary" id="roleConfigure"><span class="glyphicon glyphicon-list-alt"> </span> 角色菜单配置</button></div>');
	// 添加按钮
	$("#add").click(function() {
		$("#inputManualAdd").unbind("click").toggle(function(){
			$("#defaultPageAdd").hide();
			$("#inputAdd").show();
			$("#inputManualAdd").text("自动输入");
		},function(){
			$("#inputAdd").hide();
			$("#defaultPageAdd").show();
			$("#inputManualAdd").text("手动输入");
		})
		$("#roleNameAdd").val("");
		$("#roleAliasAdd").val("");
		$("#authorityTypeAdd").val("");
		$("#startTime-1").val("");
		$("#falseTime-1").val("");
		$("#defaultPageAdd").val("");
		$("#remarkAdd").val("");
		$("#proDiolagAdd").show();
		$("#defaultPageAdd").show();
		$("#inputAdd").hide();
		$("#inputAdd").val("");
		$("#inputManualAdd").text("手动输入");
	})
	// 添加保存按钮
	$("#saveAdd").click(function() {
		addData();
	})
	// 修改按钮
	$("#revise").click(queryOldData);
	// 修改保存按钮
	// 修改
	$("#saveRevise").click(function() {
		updateData();
	})
	// 删除按钮
	$("#remove").click(function() {
		delectData();
	});

	// 角色菜单配置
	$("#roleConfigure").click(function() {
		roleConfigure();
	});

});

var datatable, responsiveHelper_datatable_col_reorder, breakpointDefinition = {
	tablet : 1024,
	phone : 480
};
/**
 * 查询角色数据
 */
function queryData() {
	if (datatable) {
		datatable.ajax.reload();
	} else {
		datatable = $('#datatable_col_reorder_roleManagement')
				.dataTable(
						{
							"aoColumnDefs" : [ {
								"bSortable" : false,
								"aTargets" : [ 0, 1 ]
							} ],// 禁用第一列排序功能
							"searching" : false,
							"pageLength" : pageSize,// 每页显示数据条数
							"sPaginationType" : 'full_numbers',// 分页格式
							"oLanguage" : oLanguage,
							"aaSorting" : [ [ 2, "asc" ] ],
							serverSide : true,
							"autoWidth" : true,
							"ajax" : {
								url : Main.contextPath
										+ "/roleManagementController/queryData.do",
								async : false,
								type : "post",
								data : function(d) {
									// 显示加载提示框
									$(".loadContainer").show();
									return $.extend({}, d, {
										"role" : $("#role").val()
									});
								},
								dataSrc : function(result) {// 回调函数
									window.parent.existsUser(result);// 判断用户是否已失效
									// 隐藏加载框提示
									$(".loadContainer").hide();
									return result.data;
								}
							},
							columns : [
									{
										"data" : "ROLE_ID",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<input type='checkbox'"
													+ " style='width:13px !important;vertical-align: middle;' value="
													+ data + " >"
										}
									},
									{
										"data" : "id",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"
													+ data + "</p>";
										}
									},
									{
										"data" : "ROLE_ID",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:right'>"
													+ data + "</p>";
										}
									},
									{
										"data" : "ROLE_NAME"
									},
									{
										"data" : "ROLE_ALIAS"
									},
									{
										"data" : "AUTHORITY_TYPE"
									},
									{
										"data" : "START_DATE",
										"render" : function(data, type, full,
												meta) {
											if (data) {
												return "<p style='text-align:right'>"
														+ data + "</p>";
											} else {
												return "<p style='text-align:right'>"
														+ '' + "</p>";
											}

										}
									},
									{
										"data" : "END_DATE",
										"render" : function(data, type, full,
												meta) {
											if (data) {
												return "<p style='text-align:right'>"
														+ data + "</p>";
											} else {
												return "<p style='text-align:right'>"
														+ '' + "</p>";
											}

										}
									},
									{
										"data" : "REMARK",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											if (data)
												return "<p style='text-align:left'>"
														+ data + "</p>";
											else
												return "<p style='text-align:left'>"
														+ '' + "</p>";
										}
									},
									{
										"data" : "OPERATOR",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											if (data)
												return "<p style='text-align:left'>"
														+ data + "</p>";
											else
												return "<p style='text-align:left'>"
														+ '' + "</p>";
										}
									},
									{
										"data" : "DEFAULT_PAGE",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											if (data)
												return "<p style='text-align:left'>"
														+ data + "</p>";
											else
												return "<p style='text-align:left'>"
														+ '' + "</p>";
										}
									},
									{
										"data" : "CREATE_TIME",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											if (data)
												return "<p style='text-align:right'>"
														+ data + "</p>";
											else
												return "<p style='text-align:right'>"
														+ '' + "</p>";
										}
									},
									{
										"data" : "UPDATE_TIME",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											if (data)
												return "<p style='text-align:right'>"
														+ data + "</p>";
											else
												return "<p style='text-align:right'>"
														+ '' + "</p>";
										}
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
		var table = $('#datatable_col_reorder_roleManagement').DataTable();
		table.column(2).visible(false);
		table.column(8).visible(false);
	}
}
/**
 * 添加角色数据
 */
function addData() {
	var reg2 = /^\w{1,}$/;
	if ($("#startTime-1").val().trim() == ""
			|| $("#falseTime-1").val().trim() == "") {
		alert("请输入启用日期和失效日期");
	} else if ($("#roleNameAdd").val().trim() == "") {
		alert("请输入角色名")
	} else if ($("#roleAliasAdd").val().trim() == "") {
		alert("请输入角色含义")
	}else {
		$("#proDiolagAdd").hide();
		var page;
		 if($("#defaultPageAdd").css('display')=="none"){
			 page= $("#inputAdd").val()
		 }else{
			 page= $("#defaultPageAdd").val()
		 }
		var params = JSON.stringify([ {
			"ROLE_NAME" : $("#roleNameAdd").val(),
			"ROLE_ALIAS" : $("#roleAliasAdd").val(),
			"AUTHORITY_TYPE" : $("#authorityTypeAdd").val(),
			"START_DATE" : $("#startTime-1").val(),
			"END_DATE" : $("#falseTime-1").val(),
			"DEFAULT_PAGE" :page,
			"REMARK" : $("#remarkAdd").val(),
			"OPERATOR" : userName
		} ]);
		$.ajax({
			url : Main.contextPath + "/roleManagementController/addData.do",
			type : "post",
			data : {
				"params" : params
			},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);// 判断用户是否已失效
				if (result[0].state == 0) {
					queryData();
				} else {
					alert("添加数据失败");
				}
			}
		});
	}

}

// 查询要修改的数据
function queryOldData() {
	if ($('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked').length == 0) {
		alert("您没有选中任何选项,请选择!");
	} else if ($('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked').length > 1) {
		alert("你选择的选项过多，请您选择需要修改的一项");
	} else {
		$("#inputManualUpdata").unbind("click").toggle(function(){
			$("#defaultPageUpdata").hide();
			$("#inputUpdata").show();
			$("#inputManualUpdata").text("自动输入");
		},function(){
			$("#inputUpdata").hide();
			$("#defaultPageUpdata").show();
			$("#inputManualUpdata").text("手动输入");
		})
		$("#defaultPageUpdata").show();
		$("#inputUpdata").hide();
		$("#inputUpdata").val("");
		$("#inputManualUpdata").text("手动输入");
		$("#proDiolagRevise").show()
		$.ajax({
			url : Main.contextPath
							+ "/roleManagementController/queryOldData.do",
					type : "post",
					data : {
						"ROLE_ID" : $(
								'#datatable_col_reorder_roleManagement input[type="checkbox"]:checked')
								.val()
					},
					dataType : "json",
					success : function(result) {
						window.parent.existsUser(result);// 判断用户是否已失效
						if (result.length > 0) {
							$("#roleNameUpdata").val(result[0].ROLE_NAME);
							$("#roleAliasUpdata").val(result[0].ROLE_ALIAS);
							$("#authorityTypeUpdata").val(
									result[0].AUTHORITY_TYPE);
							$("#startTime-0").val(result[0].START_DATE);
							$("#falseTime-0").val(result[0].END_DATE);
							$("#remarkUpdata").val(result[0].REMARK);
							var defaultPage="\""+result[0].DEFAULT_PAGE+"\"";
							if($('#defaultPageUpdata option:[value='+defaultPage+']').length>0){
								$("#defaultPageUpdata").val(result[0].DEFAULT_PAGE);
							}else{
								$("#inputUpdata").val(result[0].DEFAULT_PAGE);
								$("#defaultPageUpdata").val(result[0].DEFAULT_PAGE);
							}
						
						}
					}
				});
	}
}

// 保存修改的数据
function updateData() {
	var reg3 = /^\w{1,}$/;
	if ($("#startTime-0").val().trim() == ""
			|| $("#falseTime-0").val().trim() == "") {
		alert("请输入启用日期和失效日期");
	} else if ($("#roleNameUpdata").val().trim() == "") {
		alert("请输入角色名")
	} else if ($("#roleAliasUpdata").val().trim() == "") {
		alert("请输入角色含义")
	}else {
		if (confirm("确定要修改选中项吗？")) {
			$("#proDiolagRevise").hide();
			$("#proDiolagAdd").hide()
			var page;
			 if($("#defaultPageUpdata").css('display')=="none"){
				 page= $("#inputUpdata").val()
			 }else{
				 page= $("#defaultPageUpdata").val()
			 }
		 var remarkUpdata = $("#remarkUpdata").val();
		 if(remarkUpdata==""){
			 remarkUpdata=" ";
		 }
		 
			var params = JSON
					.stringify([ {
						"ROLE_NAME" : $("#roleNameUpdata").val(),
						"ROLE_ALIAS" : $("#roleAliasUpdata").val(),
						"AUTHORITY_TYPE" : $("#authorityTypeUpdata").val(),
						"START_DATE" : $("#startTime-0").val(),
						"END_DATE" : $("#falseTime-0").val(),
						"REMARK" : remarkUpdata,
						"DEFAULT_PAGE" :page,
						"OPERATOR" : userName,
						"ROLE_ID" : $(
								'#datatable_col_reorder_roleManagement input[type="checkbox"]:checked')
								.val()
					} ]);
			$.ajax({
				url : Main.contextPath
						+ "/roleManagementController/updateData.do",
				type : "post",
				data : {
					"params" : params
				},
				dataType : "json",
				success : function(result) {
					window.parent.existsUser(result);// 判断用户是否已失效
					if (result[0].state == 0) {
						queryData();
					} else {
						alert("修改数据失败");
					}
				}
			});
		} else {
			$("#proDiolagRevise").hide();
		}
	}
}

// 删除配置数据
function delectData() {
	var datas = $('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked');
	var arr = 0;
	var ROLE_ID = "\'";
	for (var i = 0; i < datas.length; i++) {
		arr++;
		ROLE_ID = ROLE_ID + datas[i].defaultValue + "\'" + "," + "\'";
	}

	ROLE_ID = ROLE_ID.substring(0, ROLE_ID.length - 2);

	if ($('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked').length == 0) {
		alert("您没有选中任何选项！请选择...");
	} else {
		if (confirm("确定要删除选中项吗？")) {
			$.ajax({
				url : Main.contextPath
						+ "/roleManagementController/delectData.do",
				type : "post",
				data : {
					"ROLE_ID" : ROLE_ID
				},
				dataType : "json",
				success : function(result) {
					window.parent.existsUser(result);// 判断用户是否已失效
					if (result[0].state == 0) {
						var wrong="";
						if(result[0].wrong>0){
							wrong=",失败"+result[0].wrong+"个,角色被占用!"
						}
						alert("成功删除"+result[0].right+"个角色"+wrong);
						queryData();
					} else {
						alert("数据删除失败");
					}

				}
			});
		}
	}
}

// 角色配置
function roleConfigure() {
	if ($('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked').length == 0) {
		alert("您没有选中任何选项！请选择...");
	} else if ($('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked').length > 1) {
		alert("你选择的选项过多，请您选择需要重新配置的一项");
	} else {
		$(".content_wrap").show();
	}

	var setting = {
		view : {
			selectedMulti : false
		},
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onCheck : onCheck
		}
	};

	var zNodes = [];
	$.ajax({
		url : Main.contextPath + "/menuManagementController/queryData.do",
		type : "post",
		dataType : "json",
		async : false,
		success : function(result) {
			window.parent.existsUser(result);// 判断用户是否已失效
			if (result.length > 0) {
				for (var i = 0; i < result.length; i++) {
					if (result[i].id == 4) {
						var zogn = {
							"id" : result[i].id,
							"pId" : result[i].parentID,
							"name" : result[i].text,
							"open" : true
						};
					} else {
						var zogn = {
							"id" : result[i].id,
							"pId" : result[i].parentID,
							"name" : result[i].text
						};
					}
					zNodes.push(zogn);
				}
			}
		}
	});
	var clearFlag = false;
	function onCheck(e, treeId, treeNode) {
		count();
		if (clearFlag) {
			clearCheckedOldNodes();
		}
	}
	function clearCheckedOldNodes() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
				.getChangeCheckedNodes();
		for (var i = 0, l = nodes.length; i < l; i++) {
			nodes[i].checkedOld = nodes[i].checked;
		}
	}
	function count() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), checkCount = zTree
				.getCheckedNodes(true).length, nocheckCount = zTree
				.getCheckedNodes(false).length, changeCount = zTree
				.getChangeCheckedNodes().length;
		$("#checkCount").text(checkCount);
		$("#nocheckCount").text(nocheckCount);
		$("#changeCount").text(changeCount);

	}
	function createTree() {

		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		count();
		clearFlag = $("#last").attr("checked");
	}

	$(document)
			.ready(
					function() {
						createTree()
						$("#init").bind("change", createTree);
						$("#last").bind("change", createTree);
						$
								.ajax({
									url : Main.contextPath
											+ "/roleManagementController/queryMenuState.do",
									type : "post",
									data : {
										"ROLE_ID" : $(
												'#datatable_col_reorder_roleManagement input[type="checkbox"]:checked')
												.val()
									},
									dataType : "json",
									success : function(result) {
										window.parent.existsUser(result);// 判断用户是否已失效
										var treeObj = $.fn.zTree
												.getZTreeObj("treeDemo");
										for (var i = 0; i < result.length; i++) {
											var nodes = treeObj.getNodeByParam(
													"id", result[i].MENU_ID,
													null);
											nodes.checked = true;
											treeObj.updateNode(nodes);
										}
									}
								});
					});

}