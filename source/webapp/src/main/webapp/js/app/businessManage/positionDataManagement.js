function initManager(){
	getDataList();
	$("#search").click(function(){
		getDataList();
	})
	findDeviceList();
	findTaskList();
	$("div[class='col-xs-12 col-sm-6']").html($("#addAlterDelete",window.parent.document).html());
	
	$("#saveAdd").click(function(){
		addData();
	})
	
	$("#add").click(function(){
		 $("#proDiolagAdd").show();	
		 $("#proDiolagRevise").hide();
		 $("#proDiolagAdd").find("input").val("");
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
function findDeviceList(){
	$.ajax({
		url : Main.contextPath+"/deviceManagermentController/getDeviceList.do",
		type : "post",
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			$.each(result,function(index,map){
				var datas = "" + "<option value='" + map.id
				+ "'>" + map.deviceName + "</option>";
				$("#contianerDetailAdd #deviceId").append($(datas));
				$("#contianerDetailRevise #deviceId").append($(datas));
			});
		}
	});
}
//父标段的下拉选择
function findTaskList(){
	$.ajax({
		url : Main.contextPath+"/taskManagermentController/getTaskList.do",
		type : "post",
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			$.each(result,function(index,map){
				var datas = "" + "<option value='" + map.id
				+ "'>" + map.taskName + "</option>";
				$("#contianerDetailAdd #taskId").append($(datas));
				$("#contianerDetailRevise #taskId").append($(datas));
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
								url : Main.contextPath+ "/positionManagermentController/getDataList.do",
								type: 'POST',
								data : function(d) {
									return $.extend({},d,{
										"taskName" : $("#divtaskname").val().trim(),
										"deviceName" : $("#divdevicename").val().trim()
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
										"data" : "deviceName",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "longitude",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "latitude",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "elevation",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "X",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "Y",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "Z",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "speed",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "satelliteTime",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "direction",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ (data=='1'?'前进':'后退')+ "</p>";
										}
									},{
										"data" : "GPSStatus",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "compactId",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "CMV",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "RMV",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "frequency",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "F1",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "F2",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "F3",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "temperature",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "angle",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "sensor",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "imageAddress",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "serverTime",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "isValid",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ (data=='1'?'有效':'无效') + "</p>";
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
	var taskId = $("#contianerDetailAdd #taskId").val();
	var deviceId = $("#contianerDetailAdd #deviceId").val();
	var longitude = $("#contianerDetailAdd #longitude").val().trim();
	var latitude = $("#contianerDetailAdd #latitude").val().trim();
	var elevation = $("#contianerDetailAdd #elevation").val().trim();
	var X = $("#contianerDetailAdd #X").val().trim();
	var Y = $("#contianerDetailAdd #Y").val().trim();
	var Z = $("#contianerDetailAdd #Z").val().trim();
	var speed = $("#contianerDetailAdd #speed").val().trim();
	var satelliteTime = $("#contianerDetailAdd #satelliteTime").val().trim();
	var direction = $("#contianerDetailAdd #direction").val();
	var GPSStatus = $("#contianerDetailAdd #GPSStatus").val().trim();
	var compactId = $("#contianerDetailAdd #compactId").val().trim();
	var CMV = $("#contianerDetailAdd #CMV").val().trim();
	var RMV = $("#contianerDetailAdd #RMV").val().trim();
	var frequency = $("#contianerDetailAdd #frequency").val().trim();
	var F1 = $("#contianerDetailAdd #F1").val().trim();
	var F2 = $("#contianerDetailAdd #F2").val().trim();
	var F3 = $("#contianerDetailAdd #F3").val().trim();
	var temperature = $("#contianerDetailAdd #temperature").val().trim();
	var angle = $("#contianerDetailAdd #angle").val().trim();
	var sensor = $("#contianerDetailAdd #sensor").val().trim();
	var imageAddress = $("#contianerDetailAdd #imageAddress").val().trim();
	var serverTime = $("#contianerDetailAdd #serverTime").val().trim();
	var isValid = $("#contianerDetailAdd #isValid").val();
	var params = JSON.stringify([{
		'taskId':taskId,
		'deviceId':deviceId,
		'longitude':longitude,
		'latitude':latitude,
		'elevation':elevation,
		'X':X,
		'Y':Y,
		'Z':Z,
		'speed':speed,
		'satelliteTime':satelliteTime,
		'direction':direction,
		'GPSStatus':GPSStatus,
		'compactId':compactId,
		'CMV':CMV,
		'RMV':RMV,
		'frequency':frequency,
		'F1':F1,
		'F2':F2,
		'F3':F3,
		'temperature':temperature,
		'angle':angle,
		'sensor':sensor,
		'imageAddress':imageAddress,
		'serverTime':serverTime,
		'isValid':isValid
	}]);
   if(taskId == ""){
	   alert("请选择任务!");
   }else if(deviceId == ""){
	   alert("请选择 设备!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/positionManagermentController/addData.do",
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
		if(confirm('确定要删除该定位数据吗?')){
			var id = datas[0].id;
			$.ajax({                     
				url : Main.contextPath+"/positionManagermentController/deleteData.do",
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
        $("#contianerDetailRevise #taskId option:contains("+datas[0].taskName+")").attr("selected","selected");
        $("#contianerDetailRevise #deviceId option:contains("+datas[0].deviceName+")").attr("selected","selected");
        $("#contianerDetailRevise #longitude").val(datas[0].longitude);
        $("#contianerDetailRevise #latitude").val(datas[0].latitude);
        $("#contianerDetailRevise #elevation").val(datas[0].elevation);
        $("#contianerDetailRevise #X").val(datas[0].X);
        $("#contianerDetailRevise #Y").val(datas[0].Y);
        $("#contianerDetailRevise #Z").val(datas[0].Z);
        $("#contianerDetailRevise #speed").val(datas[0].speed);
        $("#contianerDetailRevise #satelliteTime").val(datas[0].satelliteTime);
        $("#contianerDetailRevise #direction").val(datas[0].direction);
        $("#contianerDetailRevise #GPSStatus").val(datas[0].GPSStatus);
        $("#contianerDetailRevise #compactId").val(datas[0].compactId);
        $("#contianerDetailRevise #CMV").val(datas[0].CMV);
        $("#contianerDetailRevise #RMV").val(datas[0].RMV);
        $("#contianerDetailRevise #frequency").val(datas[0].frequency);
        $("#contianerDetailRevise #F1").val(datas[0].F1);
        $("#contianerDetailRevise #F2").val(datas[0].F2);
        $("#contianerDetailRevise #F3").val(datas[0].F3);
        $("#contianerDetailRevise #temperature").val(datas[0].temperature);
        $("#contianerDetailRevise #angle").val(datas[0].angle);
        $("#contianerDetailRevise #sensor").val(datas[0].sensor);
        $("#contianerDetailRevise #imageAddress").val(datas[0].imageAddress);
        $("#contianerDetailRevise #serverTime").val(datas[0].serverTime);
        $("#contianerDetailRevise #isValid").val(datas[0].isValid);
		
		$("#proDiolagAdd").hide();
		$("#proDiolagRevise").show();
	}else{
		alert("请选择一条记录");
	}
}
//保存修改的设备
function saveModify(){
	var taskId = $("#contianerDetailRevise #taskId").val();
	var deviceId = $("#contianerDetailRevise #deviceId").val();
	var longitude = $("#contianerDetailRevise #longitude").val().trim();
	var latitude = $("#contianerDetailRevise #latitude").val().trim();
	var elevation = $("#contianerDetailRevise #elevation").val().trim();
	var X = $("#contianerDetailRevise #X").val().trim();
	var Y = $("#contianerDetailRevise #Y").val().trim();
	var Z = $("#contianerDetailRevise #Z").val().trim();
	var speed = $("#contianerDetailRevise #speed").val().trim();
	var satelliteTime = $("#contianerDetailRevise #satelliteTime").val().trim();
	var direction = $("#contianerDetailRevise #direction").val();
	var GPSStatus = $("#contianerDetailRevise #GPSStatus").val().trim();
	var compactId = $("#contianerDetailRevise #compactId").val().trim();
	var CMV = $("#contianerDetailRevise #CMV").val().trim();
	var RMV = $("#contianerDetailRevise #RMV").val().trim();
	var frequency = $("#contianerDetailRevise #frequency").val().trim();
	var F1 = $("#contianerDetailRevise #F1").val().trim();
	var F2 = $("#contianerDetailRevise #F2").val().trim();
	var F3 = $("#contianerDetailRevise #F3").val().trim();
	var temperature = $("#contianerDetailRevise #temperature").val().trim();
	var angle = $("#contianerDetailRevise #angle").val().trim();
	var sensor = $("#contianerDetailRevise #sensor").val().trim();
	var imageAddress = $("#contianerDetailRevise #imageAddress").val().trim();
	var serverTime = $("#contianerDetailRevise #serverTime").val().trim();
	var isValid = $("#contianerDetailRevise #isValid").val();
	var params = JSON.stringify([{
		"id":modifyId,
		'taskId':taskId,
		'deviceId':deviceId,
		'longitude':longitude,
		'latitude':latitude,
		'elevation':elevation,
		'X':X,
		'Y':Y,
		'Z':Z,
		'speed':speed,
		'satelliteTime':satelliteTime,
		'direction':direction,
		'GPSStatus':GPSStatus,
		'compactId':compactId,
		'CMV':CMV,
		'RMV':RMV,
		'frequency':frequency,
		'F1':F1,
		'F2':F2,
		'F3':F3,
		'temperature':temperature,
		'angle':angle,
		'sensor':sensor,
		'imageAddress':imageAddress,
		'serverTime':serverTime,
		'isValid':isValid
	}]);
   if(taskId == ""){
	   alert("请选择任务!");
   }else if(deviceId == ""){
	   alert("请选择 设备!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/positionManagermentController/modifyData.do",
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
