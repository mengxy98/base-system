function userManager(){
	//查找设备列表
	segmentManagermentList();
	$("#search").click(function(){
		segmentManagermentList();
	})
	
	$("div[class='col-xs-12 col-sm-6']").html($("#addAlterDelete",window.parent.document).html());
	/*//增加用户
	 * 
	 * 
	$("#saveAdd").click(function(){
		addDeviceManagerment();
	})
	$("#add").click(function(){
	 $(".txt").text("");
	 $("#proDiolagAdd").show();	
	 $("#proDiolagRevise").hide();
	 $("#proDiolagAdd").find("input").val("");
	 $("#remarkContent").val("");
 	})
	//删除用户
	$("#remove").click(function(){
		deleteDevice();
	})
	//修改用户
	$("#revise").click(function(){
		modifyDevice();
	})
	//保存修改的用户数据
	$("#saveRevise").click(function(){
		saveModifyDevice();
	})*/
	
}

/* 表格部分代码 */
var userManagerment_1, responsiveHelper_datatable_col_reorder, breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
function segmentManagermentList() {
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
							/*"aaSorting" : [ [ 3, "asc" ] ],*/
							serverSide : true,
							"autoWidth" : true,
							"ajax" : {
								url : Main.contextPath+ "/segmentManagermentController/segmentManagermentList.do",
								data : function(d) {
									return $.extend({},d,{
										"segmentName" : $("#segmentName").val().trim()
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
										"data" : "sid",
										orderable : false,
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
												return "<input style='margin-left:30%' id='ddddddee' type='checkbox' value="+data+">";}
									},{
										"data" : "number",
										orderable : false,
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:center'>"+ data + "</p>";
										}
									},{
										"data" : "sname",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "sContractor",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "sCDescribe",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "sfname",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "BeginStNum",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "EndStNum",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "Remark",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "createTime",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									}
							],
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
								responsiveHelper_datatable_col_reorder.createExpandIcon(nRow);
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
/*
function addDeviceManagerment(){
	var deviceName = $("#addDeviceName").val().trim();
	var operator = $("#operator").val().trim();
	var deviceType = $("#addDeviceType").val();
	var frontDistance = $("#frontDistance").val().trim();
	var height = $("#height").val().trim();
	var width = $("#width").val().trim();
	var ip = $("#ip").val().trim();
	var remark = $("#remarkContent").val().trim();
	var isShake = $("#isShake").val().trim();
	var segment = $("#segment").val().trim();
	var params = JSON.stringify([{
		"sid":segment,
		"deviceName":deviceName,
		"operator":operator,
		"deviceType":deviceType,
		"frontDistance":frontDistance,
		"height":height,
		"width":width,
		"ip":ip,
		"remark":remark,
		"isShake":isShake
	}]);
   if(deviceName == ""){
	   alert("设备名称不能为空!");
   }else if(operator == ""){
	   alert("驾驶员不能为空!");
   }else if(ip == ""){
	   alert("IP不能为空!");
   }else if(segment == ""){
	   alert("标段不能为空!");
   }else if(deviceType < 0){
	   alert("请选择设备类型!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/deviceManagermentController/addDeviceManagerment.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				deviceManagermentList();
				$("#proDiolagAdd").hide();
				alert(result.msg);
			}
		});
   }
}

//删除
function deleteDevice(){
	var datas = userManagerment_1.rows({
		selected : true
	}).data();
	if(datas.length == '1'){
		if(confirm('确定要删除该设备吗?')){
			var deviceId = datas[0].id;
			$.ajax({                     
				url : Main.contextPath+"/deviceManagermentController/deleteDeviceManagerment.do",
				type : "post",
				data:{deviceId:deviceId},
				dataType : "json",
				success : function(result) {
					window.parent.existsUser(result);//判断用户是否已失效
					deviceManagermentList();
					alert(result.msg);
				}
			});
		} 
	}else{
		alert("请选择一条记录");
	}
}

//修改设备
var modifyDeviceId=0;
function modifyDevice(){
	var datas = userManagerment_1.rows({selected : true}).data();
	//将用户数据放上去
	if(datas.length == '1'){
		modifyDeviceId=datas[0].id;
		$("#modifyDeviceName").val(datas[0].deviceName);
		$("#modifyOperator").val(datas[0].operator);
		$("#modifyDeviceType option:contains("+datas[0].name+")").attr("selected","selected");
		$("#modifyFrontDist").val(datas[0].frontDistance);
		$("#modifyHeight").val(datas[0].height);
		$("#modifyWidth").val(datas[0].width);
		$("#modifyIp").val(datas[0].ip);
		$("#modifyRemark").val(datas[0].remark);
		$("#modifySegment").val(datas[0].sid);
		$("#modifyIsShake").val(datas[0].isShake);
		
		$("#proDiolagAdd").hide();
		$("#proDiolagRevise").show();
	}else{
		alert("请选择一条记录");
	}
}
//保存修改的设备
function saveModifyDevice(){
	var deviceName = $("#modifyDeviceName").val().trim();
	var operator = $("#modifyOperator").val().trim();
	var deviceType = $("#modifyDeviceType").val();
	var frontDistance = $("#modifyFrontDist").val().trim();
	var height = $("#modifyHeight").val().trim();
	var width = $("#modifyWidth").val().trim();
	var ip = $("#modifyIp").val().trim();
	var remark = $("#modifyRemark").val().trim();
	var segment = $("#modifySegment").val().trim();
	var params = JSON.stringify([{
		"id":modifyDeviceId,
		"sid":segment,
		"deviceName":deviceName,
		"operator":operator,
		"deviceType":deviceType,
		"frontDistance":frontDistance,
		"height":height,
		"width":width,
		"ip":ip,
		"remark":remark
	}]);
   if(deviceName == ""){
	   alert("设备名称不能为空!");
   }else if(operator == ""){
	   alert("驾驶员不能为空!");
   }else if(ip == ""){
	   alert("IP不能为空!");
   }else if(segment == ""){
	   alert("标段不能为空!");
   }else if(deviceType < 0){
	   alert("请选择设备类型!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/deviceManagermentController/modifyDeviceManagerment.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				deviceManagermentList();
				alert(result.msg);
				$("#proDiolagRevise").hide();
			}
		});
	}
}*/
