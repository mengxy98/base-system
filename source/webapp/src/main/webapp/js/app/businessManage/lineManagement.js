function initManager(){
	getDataList();
	$("#search").click(function(){
		getDataList();
	})
	findSegmentList();
	getLineList();
	findLineTypeList();
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
function getLineList(){
	$.ajax({
		url : Main.contextPath+"/lineManagermentController/getLineList.do",
		type : "post",
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			$("#contianerDetailAdd #parentId").empty();
			$("#contianerDetailRevise #parentId").empty();
			var html="<option value='0'></option>";
			$("#contianerDetailAdd #parentId").append(html);
			$("#contianerDetailRevise #parentId").append(html);
			$.each(result,function(index,map){
				var datas = "" + "<option value='" + map.id
				+ "'>" + map.linename + "</option>";
				$("#contianerDetailAdd #parentId").append($(datas));
				$("#contianerDetailRevise #parentId").append($(datas));
			});
		}
	});
}
function findLineTypeList(){
	$.ajax({
		url : Main.contextPath+"/dicManagermentController/typeNameDict.do",
		type : "post",
		data : {"type":"lineType"},
		async : false,
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			$.each(result,function(key,value){
				var datas = "" + "<option value='" + key
				+ "'>" + value + "</option>";
				$("#contianerDetailAdd #lineType").append($(datas));
				$("#contianerDetailRevise #lineType").append($(datas));
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
								url : Main.contextPath+ "/lineManagermentController/getDataList.do",
								type: 'POST',
								data : function(d) {
									return $.extend({},d,{
										"linename" : $("#divlinename").val().trim()
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
										"data" : "linename",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "sName",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "lineType",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "parentLineName",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "mLineX",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "mLineY",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "mLineZ",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "radius",
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
										"data" : "stNum",
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
	var linename = $("#contianerDetailAdd #linename").val();
	var parentId = $("#contianerDetailAdd #parentId").val();
	var mLineX = $("#contianerDetailAdd #mLineX").val().trim();
	var mLineZ = $("#contianerDetailAdd #mLineZ").val().trim();
	var mLineY = $("#contianerDetailAdd #mLineY").val().trim();
	var radius = $("#contianerDetailAdd #radius").val().trim();
	var longitude = $("#contianerDetailAdd #longitude").val().trim();
	var latitude = $("#contianerDetailAdd #latitude").val();
	var elevation = $("#contianerDetailAdd #elevation").val().trim();
	var lineType = $("#contianerDetailAdd #lineType").val();
	var stNum = $("#contianerDetailAdd #stNum").val().trim();
	var params = JSON.stringify([{
		    "sid":sid,
			"linename":linename,
			"parentId":parentId,
			"mLineX":mLineX,
			"mLineY":mLineY,
			"mLineZ":mLineZ,
			"radius":radius,
			"longitude":longitude,
			"latitude":latitude,
			"elevation":elevation,
			"lineType":lineType,
			"stNum":stNum
	}]);
   if(linename == ""){
	   alert("中心线名称不能为空!");
   }else if(sid == ""){
	   alert("请选择标段!");
   }else if(lineType == ""){
	   alert("请选择线型!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/lineManagermentController/addData.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				getDataList();
				getLineList();
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
		if(confirm('确定要删除该路线吗?')){
			var id = datas[0].id;
			$.ajax({                     
				url : Main.contextPath+"/lineManagermentController/deleteData.do",
				type : "post",
				data:{id:id},
				dataType : "json",
				success : function(result) {
					window.parent.existsUser(result);//判断用户是否已失效
					getDataList();
					getLineList();
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
        $("#contianerDetailRevise  #parentId option:contains("+datas[0].parentLineName+")").attr("selected","selected");
        $("#contianerDetailRevise  #lineType option:contains("+datas[0].lineType+")").attr("selected","selected");
		$("#contianerDetailRevise  #linename").val(datas[0].linename);
		$("#contianerDetailRevise  #mLineX").val(datas[0].mLineX);
		$("#contianerDetailRevise  #mLineY").val(datas[0].mLineY);
		$("#contianerDetailRevise  #mLineZ").val(datas[0].mLineZ);
		$("#contianerDetailRevise  #radius").val(datas[0].radius);
		$("#contianerDetailRevise  #longitude").val(datas[0].longitude);
		$("#contianerDetailRevise  #latitude").val(datas[0].latitude);
		$("#contianerDetailRevise  #elevation").val(datas[0].elevation);
		$("#contianerDetailRevise  #stNum").val(datas[0].stNum);
		
		$("#proDiolagAdd").hide();
		$("#proDiolagRevise").show();
	}else{
		alert("请选择一条记录");
	}
}
//保存修改的设备
function saveModify(){
	var sid = $("#contianerDetailRevise #sid").val();
	var linename = $("#contianerDetailRevise  #linename").val();
	var parentId = $("#contianerDetailRevise  #parentId").val();
	var mLineX = $("#contianerDetailRevise  #mLineX").val().trim();
	var mLineZ = $("#contianerDetailRevise #mLineZ").val().trim();
	var mLineY = $("#contianerDetailRevise  #mLineY").val().trim();
	var radius = $("#contianerDetailRevise  #radius").val().trim();
	var longitude = $("#contianerDetailRevise #longitude").val().trim();
	var latitude = $("#contianerDetailRevise  #latitude").val();
	var elevation = $("#contianerDetailRevise  #elevation").val().trim();
	var lineType = $("#contianerDetailRevise  #lineType").val();
	var stNum = $("#contianerDetailRevise #stNum").val().trim();
	var params = JSON.stringify([{
			"id":modifyId,
		    "sid":sid,
			"linename":linename,
			"parentId":parentId,
			"mLineX":mLineX,
			"mLineY":mLineY,
			"mLineZ":mLineZ,
			"radius":radius,
			"longitude":longitude,
			"latitude":latitude,
			"elevation":elevation,
			"lineType":lineType,
			"stNum":stNum
	}]);
   if(linename == ""){
	   alert("中心线名称不能为空!");
   }else if(sid == ""){
	   alert("请选择标段!");
   }else if(lineType == ""){
	   alert("请选择线型!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/lineManagermentController/modifyData.do",
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
