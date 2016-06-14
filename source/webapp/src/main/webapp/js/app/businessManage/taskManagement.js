function initManager(){
	getDataList();
	$("#search").click(function(){
		getDataList();
	})
	findSegmentList();
	$("div[class='col-xs-12 col-sm-6']").html($("#addAlterDelete",window.parent.document).html());
	
	
	$("#saveAdd").click(function(){
		addData();
	})
	
	$("#add").click(function(){
		 $("#proDiolagAdd").show();	
		 $("#proDiolagRevise").hide();
		 $("#proDiolagAdd").find("input").val("");
		 $("#contianerDetailAdd #remark").val("");
		 $("#contianerDetailAdd #material").val("");
		 $("#contianerDetailAdd #craft").val("");
		 $("#contianerDetailAdd #realityCraft").val("");
 	})
	$("#remove").click(function(){
		deleteData();
	})
	$("#revise").click(function(){
		modifyData();
	})
	$("#saveRevise").click(function(){
		saveModify();
	})
	
}

//父标段的下拉选择
function findSegmentList(){
	$.ajax({
		url : Main.contextPath+"/segmentManagermentController/segmentList.do",
		type : "post",
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			$.each(result,function(index,map){
				var datas = "" + "<option value='" + map.sid
				+ "'>" + map.sname + "</option>";
				$("#contianerDetailAdd #sid").append($(datas));
				$("#contianerDetailRevise #sid").append($(datas));
			});
		}
	});
}
/* 表格部分代码 */
var userManagerment_1, responsiveHelper_datatable_col_reorder, breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
function getDataList() {
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
							"serverSide" : true,
							"autoWidth" : true,
							"ajax" : {
								url : Main.contextPath+ "/taskManagermentController/getDataList.do",
								type: 'POST',
								data : function(d) {
									return $.extend({},d,{
										"taskName" : $("#divtaskName").val().trim()
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
										"data" : "id",
										"orderable" : false,
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
												return "<input style='margin-left:30%' id='ddddddee' type='checkbox' value="+data+">";}
									},{
										"data" : "rownum",
										"orderable" : false,
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:center'>"+ data + "</p>";
										}
									},{
										"data" : "taskName",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "sName",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "shakeTimes",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "staticPressureTimes",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "tier",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "targetCMV",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "targetCount",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "shakeFrequency",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "beginStNum",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "endStNum",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "beginTime",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "endTime",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "carea",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "timeRate",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "CMVRate",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "material",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "craft",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "realityCraft",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "remark",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "createTime",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
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

function addData(){
	var sid = $("#contianerDetailAdd #sid").val();
	var material = $("#contianerDetailAdd  #material").val();
	var taskName = $("#contianerDetailAdd  #taskName").val().trim();
	var shakeTimes = $("#contianerDetailAdd  #shakeTimes").val().trim();
	var staticPressureTimes = $("#contianerDetailAdd #staticPressureTimes").val().trim();
	var craft = $("#contianerDetailAdd  #craft").val();
	var realityCraft = $("#contianerDetailAdd  #realityCraft").val().trim();
	var tier = $("#contianerDetailAdd #tier").val().trim();
	var targetCMV = $("#contianerDetailAdd  #targetCMV").val();
	var targetCount = $("#contianerDetailAdd  #targetCount").val().trim();
	var shakeFrequency = $("#contianerDetailAdd  #shakeFrequency").val().trim();
	var beginStNum = $("#contianerDetailAdd #beginStNum").val().trim();
	var endStNum = $("#contianerDetailAdd  #endStNum").val();
	var beginTime = $("#contianerDetailAdd  #beginTime").val().trim();
	var endTime = $("#contianerDetailAdd  #endTime").val().trim();
	var carea = $("#contianerDetailAdd #carea").val().trim();
	var timeRate = $("#contianerDetailAdd  #timeRate").val();
	var CMVRate = $("#contianerDetailAdd  #CMVRate").val().trim();
	var remark = $("#contianerDetailAdd  #remark").val().trim();
	
	var params = JSON.stringify([{
		    "sid":sid,
			"taskName":taskName,
			"material":material,
			"shakeTimes":shakeTimes,
			"staticPressureTimes":staticPressureTimes,
			"craft":craft,
			"realityCraft":realityCraft,
			"tier":tier,
			"targetCMV":targetCMV,
			"targetCount":targetCount,
			"shakeFrequency":shakeFrequency,
			"beginStNum":beginStNum,
			"endStNum":endStNum,
			"beginTime":beginTime,
			"endTime":endTime,
			"carea":carea,
			"timeRate":timeRate,
			"CMVRate":CMVRate,
			"remark":remark
	}]);
   if(taskName == ""){
	   alert("任务名称不能为空!");
   }else if(sid == ""){
	   alert("请选择标段!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/taskManagermentController/addData.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				getDataList();
				$("#proDiolagAdd").hide();
				alert(result.msg);
			}
		});
   }
}

//删除
function deleteData(){
	var datas = userManagerment_1.rows({
		selected : true
	}).data();
	if(datas.length == '1'){
		if(confirm('确定要删除该任务吗?')){
			var id = datas[0].id;
			$.ajax({                     
				url : Main.contextPath+"/taskManagermentController/deleteData.do",
				type : "post",
				data:{id:id},
				dataType : "json",
				success : function(result) {
					window.parent.existsUser(result);//判断用户是否已失效
					getDataList();
					alert(result.msg);
				}
			});
		} 
	}else{
		alert("请选择一条记录");
	}
}

//修改
var modifyId=0;
function modifyData(){
	var datas = userManagerment_1.rows({selected : true}).data();
	//将用户数据放上去
	if(datas.length == '1'){
		modifyId=datas[0].id;
        $("#contianerDetailRevise  #sid option:contains("+datas[0].sName+")").attr("selected","selected");
		$("#contianerDetailRevise  #material").val(datas[0].material);
		$("#contianerDetailRevise  #taskName").val(datas[0].taskName);
		$("#contianerDetailRevise  #shakeTimes").val(datas[0].shakeTimes);
		$("#contianerDetailRevise  #staticPressureTimes").val(datas[0].staticPressureTimes);
		$("#contianerDetailRevise  #craft").val(datas[0].craft);
		$("#contianerDetailRevise  #realityCraft").val(datas[0].realityCraft);
		$("#contianerDetailRevise  #tier").val(datas[0].tier);
		$("#contianerDetailRevise  #targetCMV").val(datas[0].targetCMV);
		$("#contianerDetailRevise  #targetCount").val(datas[0].targetCount);
		$("#contianerDetailRevise  #shakeFrequency").val(datas[0].shakeFrequency);
		$("#contianerDetailRevise  #beginStNum").val(datas[0].beginStNum);
		$("#contianerDetailRevise  #endStNum").val(datas[0].endStNum);
		$("#contianerDetailRevise  #beginTime").val(datas[0].beginTime);
		$("#contianerDetailRevise  #endTime").val(datas[0].endTime);
		$("#contianerDetailRevise  #carea").val(datas[0].carea);
		$("#contianerDetailRevise  #timeRate").val(datas[0].timeRate);
		$("#contianerDetailRevise  #CMVRate").val(datas[0].CMVRate);
		$("#contianerDetailRevise  #remark").val(datas[0].remark);
		
		$("#proDiolagAdd").hide();
		$("#proDiolagRevise").show();
	}else{
		alert("请选择一条记录");
	}
}
//保存修改的设备
function saveModify(){
	var sid = $("#contianerDetailRevise #sid").val();
	var material = $("#contianerDetailRevise  #material").val();
	var taskName = $("#contianerDetailRevise  #taskName").val().trim();
	var shakeTimes = $("#contianerDetailRevise  #shakeTimes").val().trim();
	var staticPressureTimes = $("#contianerDetailRevise #staticPressureTimes").val().trim();
	var craft = $("#contianerDetailRevise  #craft").val();
	var realityCraft = $("#contianerDetailRevise  #realityCraft").val().trim();
	var tier = $("#contianerDetailRevise #tier").val().trim();
	var targetCMV = $("#contianerDetailRevise  #targetCMV").val();
	var targetCount = $("#contianerDetailRevise  #targetCount").val().trim();
	var shakeFrequency = $("#contianerDetailRevise  #shakeFrequency").val().trim();
	var beginStNum = $("#contianerDetailRevise #beginStNum").val().trim();
	var endStNum = $("#contianerDetailRevise  #endStNum").val();
	var beginTime = $("#contianerDetailRevise  #beginTime").val().trim();
	var endTime = $("#contianerDetailRevise  #endTime").val().trim();
	var carea = $("#contianerDetailRevise #carea").val().trim();
	var timeRate = $("#contianerDetailRevise  #timeRate").val();
	var CMVRate = $("#contianerDetailRevise  #CMVRate").val().trim();
	var remark = $("#contianerDetailRevise  #remark").val().trim();
	
	var params = JSON.stringify([{
			"id":modifyId,
		    "sid":sid,
			"taskName":taskName,
			"material":material,
			"shakeTimes":shakeTimes,
			"staticPressureTimes":staticPressureTimes,
			"craft":craft,
			"realityCraft":realityCraft,
			"tier":tier,
			"targetCMV":targetCMV,
			"targetCount":targetCount,
			"shakeFrequency":shakeFrequency,
			"beginStNum":beginStNum,
			"endStNum":endStNum,
			"beginTime":beginTime,
			"endTime":endTime,
			"carea":carea,
			"timeRate":timeRate,
			"CMVRate":CMVRate,
			"remark":remark
	}]);
   if(taskName == ""){
	   alert("任务名称不能为空!");
   }else if(sid == ""){
	   alert("请选择标段!");
   }else{ 
	   $.ajax({
			url : Main.contextPath+"/taskManagermentController/modifyData.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				getDataList();
				alert(result.msg);
				$("#proDiolagRevise").hide();
			}
		});
	}
}
